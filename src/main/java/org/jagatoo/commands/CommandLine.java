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
package org.jagatoo.commands;

import java.util.ArrayList;

/**
 * Simple CommandLine class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class CommandLine
{
    private final StringBuffer buff = new StringBuffer();
    
    private       String              key    = null;
    private final ArrayList< String > params = new ArrayList< String >();
    
    /**
     * @return the CommandLine's key.
     */
    public final String getKey()
    {
        return ( key );
    }
    
    /**
     * @return the CommandLine's parameters as Strings.
     */
    public final ArrayList< String > getParameters()
    {
        return ( params );
    }
    
    public CommandLine parse( String line )
    {
        buff.setLength( 0 );
        key = null;
        params.clear();
        
        for ( int i = 0; i < line.length(); i++ )
        {
            final char ch = line.charAt( i );
            final boolean isWhitespace = Character.isWhitespace( ch );
            final boolean isControlChar = isWhitespace || ( ch == ',' ) || ( ch == '(' ) || ( ch == ')' );
            
            if ( !isControlChar )
            {
                buff.append( ch );
            }
            
            if ( isControlChar || ( i == line.length() - 1 ) )
            {
                if ( buff.length() > 0 )
                {
                    if ( key == null )
                        key = buff.toString();
                    else
                        params.add( buff.toString() );
                    
                    buff.setLength( 0 );
                }
            }
        }
        
        return ( this );
    }
    
    public CommandLine()
    {
    }
    
    public CommandLine( String line )
    {
        this();
        
        parse( line );
    }
}
