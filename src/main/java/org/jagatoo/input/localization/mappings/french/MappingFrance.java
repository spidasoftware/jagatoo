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
package org.jagatoo.input.localization.mappings.french;

import java.net.URL;
import java.util.HashMap;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.localization.mappings.KeyLocalizationParser;
import org.jagatoo.input.localization.mappings.Mapping;
import org.jagatoo.logging.Log;

/**
 * This the {@link Mapping} implementation for France.
 * 
 * @author Yoann Meste (aka Mancer)
 */
public class MappingFrance implements Mapping
{
    private HashMap< String, String > localizedKeyNamesMap = null;
    
    /**
     * {@inheritDoc}
     */
    public final Key getUpperYZKey()
    {
        return ( Keys.Z );
    }
    
    /**
     * {@inheritDoc}
     */
    public final Key getLowerYZKey()
    {
        return ( Keys.Y );
    }
    
    /**
     * {@inheritDoc}
     */
    public char getModifiedChar( Key key, char unmodChar, int modifierMask )
    {
        switch ( key.getKeyID() )
        {
            case _0:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '0' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '@' );
                break;
            case _1:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '&' );
                break;
            case _2:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)233 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '~' );
                break;
            case _3:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '"' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '#' );
                break;
            case _4:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)39 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '{' );
                break;
            case _5:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '(' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '[' );
                break;
            case _6:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '-' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '|' );
                break;
            case _7:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)232 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '`' );
                break;
            case _8:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '_' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '\\' );
                break;
            case _9:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)231 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '^' );
                break;
            
            case E:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8364 );
                break;
            
            case CIRCUMFLEX:
                if ( modifierMask == 0 )
                    return ( '^' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)168 );
                break;
            
            case NUMPAD_DECIMAL:
                return ( '.' );
                
            case LOCAL_KEY1:
                if ( modifierMask == 0 )
                    return ( (char)178 );
                break;
            case LOCAL_KEY2:
                if ( modifierMask == 0 )
                    return ( ')' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ((char)176 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( ']' );
                break;
            case LOCAL_KEY3:
                if ( modifierMask == 0 )
                    return ( '$' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)163 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)164 );
                break;
            case LOCAL_KEY4:
                if ( modifierMask == 0 )
                    return ( (char)249 );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '%' );
                break;
            case LOCAL_KEY5:
                if ( modifierMask == 0 )
                    return ( '*' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)181 );
                break;
            case LOCAL_KEY6:
                if ( modifierMask == 0 )
                    return ( ',' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '?' );
                break;
            case LOCAL_KEY7:
                if ( modifierMask == 0 )
                    return ( ';' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '.' );
                break;
            case LOCAL_KEY8:
                if ( modifierMask == 0 )
                    return ( ':' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '/' );
                break;
            case LOCAL_KEY9:
                if ( modifierMask == 0 )
                    return ( '!' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)167 );
                break;
            case LOCAL_KEY10:
                if ( modifierMask == 0 )
                    return ( '^' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)168 );
                break;
            case LOCAL_KEY11:
                if ( modifierMask == 0 )
                    return ( '=' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '+' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '}' );
                break;
        }
        
        return ( '\0' );
    }
    
    public Key getLocalizedKey( char keyChar )
    {
        System.out.println( "key char =" + (int)keyChar ); // TODO: Don't fotget me ;)
        switch ( (int)keyChar )
        {
            case 178:
                return FrenchKeys.SUPERSCRIPT_TWO;
            case 36:
            case 163:
                return FrenchKeys.DOLLAR;
            case 94:
            case 168:
                return Keys.CIRCUMFLEX;
            case 42:
            case 181:
                return FrenchKeys.ASTERISK;
            case 249:
            case 37:
                return FrenchKeys.U_ACCENT;
            case 33:
            case 167:
                return FrenchKeys.EXCLAMATION;
            case 58:
            case 47:
                return FrenchKeys.COLON;
            case 59:
            case 46:
                return FrenchKeys.SEMICOLON;
            case 44:
            case 63:
                return FrenchKeys.COMMA;
            case 61:
            case 43:
            case 125:
                return FrenchKeys.EQUAL;
            case 41:
            case 176:
            case 93:
                return FrenchKeys.PARENTHESIS_CLOSE;
            case 60:
            case 62:
                return FrenchKeys.UPPER;
                
        }
        
        return ( null );
    }
    
    public String getLocalizedKeyName( DeviceComponent key )
    {
        if ( localizedKeyNamesMap == null )
        {
            localizedKeyNamesMap = new HashMap< String, String >();
            
            try
            {
                String packageName = this.getClass().getPackage().getName().replace( '.', '/' );
                final String filename = "/" + packageName + "/keynames.french";
                
                URL resource = this.getClass().getResource( filename );
                if ( resource != null )
                {
                    Log.debug( InputSystem.LOG_CHANNEL, "Parsing key-localization file ", filename, "..." );
                    int numValidLines = KeyLocalizationParser.parse( resource, localizedKeyNamesMap );
                    Log.debug( InputSystem.LOG_CHANNEL, "parsing done. Found ", numValidLines, " valid lines." );
                }
            }
            catch ( Throwable t )
            {
                Log.print( InputSystem.LOG_CHANNEL, t );
                t.printStackTrace();
            }
        }
        
        String locName = localizedKeyNamesMap.get( key.getName() );
        
        if ( locName == null )
            return ( key.getName() );
        
        return ( locName );
    }
    
    public MappingFrance()
    {
    }
}
