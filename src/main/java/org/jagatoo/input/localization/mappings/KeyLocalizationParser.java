/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
package org.jagatoo.input.localization.mappings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.jagatoo.input.InputSystem;
import org.jagatoo.logging.Log;

/**
 * This class parses key/value pairs from a text-file for
 * key-name-localizations.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class KeyLocalizationParser
{
    private static final StringBuffer stringBuffer = new StringBuffer();
    
    private static String parseLine( String line, Map< String, String > map )
    {
        /*
         * 'phase' indicates, what is currently done/expected
         * in the parsed line.
         * 
         * 0 = initilization. expecting key/double-quote.
         * 1 = parsing key. expecting white-space/double-quote.
         * 2 = after key. expecting equal sign (=).
         * 3 = after equal sign (=). expecting translation/double-quote.
         * 4 = parsing translation. expecting eol/white-space/double-quote
         */
        int phase = 0;
        
        boolean isInQuotes = false;
        
        String key = null;
        String value = null;
        
        stringBuffer.setLength( 0 );
        
        for ( int i = 0; i < line.length(); i++ )
        {
            final char ch = line.charAt( i );
            
            switch ( phase )
            {
                case 0:
                {
                    if ( ch == '"' )
                    {
                        isInQuotes = true;
                    }
                    else
                    {
                        stringBuffer.setLength( 0 );
                        stringBuffer.append( ch );
                    }
                    
                    phase++;
                }
                break;
                
                case 1:
                {
                    if ( isInQuotes )
                    {
                        if ( ch != '"' )
                        {
                            stringBuffer.append( ch );
                        }
                        else
                        {
                            if ( stringBuffer.length() > 0 )
                            {
                                key = stringBuffer.toString();
                                
                                phase++;
                            }
                            else
                            {
                                return ( null );
                            }
                            
                            isInQuotes = false;
                        }
                    }
                    else
                    {
                        if ( Character.isWhitespace( ch ) )
                        {
                            if ( stringBuffer.length() > 0 )
                            {
                                key = stringBuffer.toString();
                                
                                phase++;
                            }
                        }
                        else if ( ch == '=' )
                        {
                            if ( stringBuffer.length() > 0 )
                            {
                                key = stringBuffer.toString();
                                
                                phase += 2;
                            }
                            else
                            {
                                return ( null );
                            }
                        }
                        else if ( ch == '"' )
                        {
                            return ( null );
                        }
                        else
                        {
                            stringBuffer.append( ch );
                        }
                    }
                }
                break;
                
                case 2:
                {
                    if ( Character.isWhitespace( ch ) )
                    {
                        continue;
                    }
                    else if ( ch == '=' )
                    {
                        phase++;
                    }
                    else
                    {
                        return ( null );
                    }
                }
                break;
                
                case 3:
                {
                    if ( key == null )
                        return ( null );
                    
                    if ( Character.isWhitespace( ch ) )
                    {
                        continue;
                    }
                    
                    stringBuffer.setLength( 0 );
                    
                    if ( ch == '"' )
                    {
                        isInQuotes = true;
                    }
                    else
                    {
                        stringBuffer.append( ch );
                    }
                    
                    phase++;
                }
                break;
                
                case 4:
                {
                    if ( isInQuotes )
                    {
                        if ( ch == '"' )
                        {
                            isInQuotes = false;
                            
                            if ( stringBuffer.length() > 0 )
                            {
                                value = stringBuffer.toString();
                                
                                map.put( key, value );
                                
                                return ( key );
                            }
                            
                            return ( null );
                        }
                        
                        stringBuffer.append( ch );
                    }
                    else
                    {
                        if ( Character.isWhitespace( ch ) )
                        {
                            if ( stringBuffer.length() > 0 )
                            {
                                value = stringBuffer.toString();
                                
                                map.put( key, value );
                                
                                return ( key );
                            }
                            
                            return ( null );
                        }
                        
                        if ( ch == '"' )
                        {
                            return ( null );
                        }
                        
                        stringBuffer.append( ch );
                    }
                }
                break;
            }
        }
        
        if ( ( key == null ) || ( phase < 4 ) )
            return ( null );
        
        if ( ( value == null ) && ( stringBuffer.length() > 0 ) )
        {
            value = stringBuffer.toString();
        }
        
        if ( value != null )
        {
            map.put( key, value );
            
            return ( key );
        }
        
        return ( null );
    }
    
    public static int parse( InputStream in, Map< String, String > map ) throws IOException
    {
        BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
        
        int numValidLines = 0;
        
        int lineNum = 0;
        
        String line = null;
        while( ( line = br.readLine() ) != null )
        {
            line = line.trim();
            lineNum++;
            
            if ( line.length() == 0 )
                continue;
            if ( line.startsWith( "#" ) )
                continue;
            
            String key = parseLine( line, map );
            
            if ( key != null )
            {
                //System.out.println( "Parsed valid line #" + lineNum );
                Log.debug( InputSystem.LOG_CHANNEL, "Parsed valid line #", lineNum );
                numValidLines++;
            }
            else
            {
                //System.out.println( "Invalid line #" + lineNum );
                Log.debug( InputSystem.LOG_CHANNEL, "Invalid line #", lineNum );
            }
        }
        
        /*
        for ( String s: map.keySet() )
        {
            System.out.println( "\"" + s + "\" = \"" + map.get( s ) + "\"" );
        }
        */
        
        return ( numValidLines );
    }
    
    public static int parse( URL resource, Map< String, String > map ) throws IOException
    {
        return ( parse( resource.openStream(), map ) );
    }
}
