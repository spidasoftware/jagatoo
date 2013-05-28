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
package org.jagatoo.input.localization.mappings.german;

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
 * This the {@link Mapping} implementation for germany.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MappingGermany implements Mapping
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
                    return ( '=' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '}' );
                break;
            case _1:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '!' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)185 );
                break;
            case _2:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '"' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)178 );
                break;
            case _3:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)167 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)179 );
                break;
            case _4:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '$' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)188 );
                break;
            case _5:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '%' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)189 );
                break;
            case _6:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '&' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)172 );
                break;
            case _7:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '/' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '{' );
                break;
            case _8:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '(' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '[' );
                break;
            case _9:
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( ')' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( ']' );
                break;
            
            case A:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)230 );
                break;
            case B:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8221 );
                break;
            case C:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)162 );
                break;
            case D:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)240 );
                break;
            case E:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8364 );
                break;
            case F:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)273 );
                break;
            case G:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)331 );
                break;
            case H:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)295 );
                break;
            case I:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8594 );
                break;
            case J:
                break;
            case K:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)312 );
                break;
            case L:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)322 );
                break;
            case M:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)181 );
                break;
            case N:
                break;
            case O:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)248 );
                break;
            case P:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)254 );
                break;
            case Q:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '@' );
                break;
            case R:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)182 );
                break;
            case S:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)223 );
                break;
            case T:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)359 );
                break;
            case U:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8595 );
                break;
            case V:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8220 );
                break;
            case W:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)322 );
                break;
            case X:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)187 );
                break;
            case Y:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)171 );
                break;
            case Z:
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)8592 );
                break;
            
            case CIRCUMFLEX:
                if ( modifierMask == 0 )
                    return ( '^' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)176 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)172 );
                break;
                
            case NUMPAD_DECIMAL:
                return ( ',' );
                
            case LOCAL_KEY1:
                if ( modifierMask == 0 )
                    return ( (char)223 );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '?' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '\\' );
                break;
            case LOCAL_KEY2:
                if ( modifierMask == 0 )
                    return ( '\'' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)96 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)184 );
                break;
            case LOCAL_KEY3:
                if ( modifierMask == 0 )
                    return ( (char)252 );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( 220 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)168 );
                break;
            case LOCAL_KEY4:
                if ( modifierMask == 0 )
                    return ( '+' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '*' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '~' );
                break;
            case LOCAL_KEY5:
                if ( modifierMask == 0 )
                    return ( (char)246 );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)214 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)733 );
                break;
            case LOCAL_KEY6:
                if ( modifierMask == 0 )
                    return ( (char)228 );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( (char)196 );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '^' );
                break;
            case LOCAL_KEY7:
                if ( modifierMask == 0 )
                    return ( '#' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '\'' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)96 );
                break;
            case LOCAL_KEY8:
                if ( modifierMask == 0 )
                    return ( ',' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( ';' );
                break;
            case LOCAL_KEY9:
                if ( modifierMask == 0 )
                    return ( '.' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( ':' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( (char)183 );
                break;
            case LOCAL_KEY10:
                if ( modifierMask == 0 )
                    return ( '-' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '_' );
                break;
            case LOCAL_KEY11:
                if ( modifierMask == 0 )
                    return ( '<' );
                if ( modifierMask == Keys.MODIFIER_SHIFT )
                    return ( '>' );
                if ( modifierMask == Keys.MODIFIER_ALT_GRAPH )
                    return ( '|' );
                break;
        }
        
        return ( '\0' );
    }
    
    public Key getLocalizedKey( char keyChar )
    {
        switch ( (int)keyChar )
        {
            case 223:
            case 63:
            case 92:
                return ( GermanKeys.SZ );
            //case 39:
            //case 96:
            case 184:
                return ( GermanKeys.APOSTROPH );
            case 228:
            case 196:
            case 94:
                return ( GermanKeys.AE );
            case 246:
            case 214:
            case 733:
                return ( GermanKeys.OE );
            case 252:
            case 220:
            case 168:
                return ( GermanKeys.UE );
            case 42:
            case 43:
            case 126:
                return ( GermanKeys.ADD );
            case 35:
            case 39:
            case 96:
                return ( GermanKeys.DIAMOND );
            case 60:
            case 62:
            case 124:
                return ( GermanKeys.RELATION );
            case 44:
            case 59:
                return ( GermanKeys.COMMA );
            case 46:
            case 48:
            case 183:
                return ( GermanKeys.PERIOD );
            case 45:
            case 95:
                return ( GermanKeys.SUBTRACT );
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
                final String filename = "/" + packageName + "/keynames.german";
                
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
    
    public MappingGermany()
    {
    }
}
