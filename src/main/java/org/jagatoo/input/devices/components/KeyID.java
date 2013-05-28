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
package org.jagatoo.input.devices.components;

/**
 * This class provides a static final access to the standard
 * keys' key-IDs.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum KeyID
{
    ESCAPE,
    
    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7,
    F8,
    F9,
    F10,
    F11,
    F12,
    
    PRINTSCREEN,
    PAUSE,
    SCROLL_LOCK,
    
    CIRCUMFLEX,
    
    _0,
    _1,
    _2,
    _3,
    _4,
    _5,
    _6,
    _7,
    _8,
    _9,
    
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    
    TAB,
    SPACE,
    BACK_SPACE,
    ENTER,
    
    LEFT_SHIFT,
    RIGHT_SHIFT,
    LEFT_CONTROL,
    RIGHT_CONTROL,
    ALT,
    ALT_GRAPH,
    LEFT_META,
    RIGHT_META,
    CAPS_LOCK,
    
    DELETE,
    INSERT,
    END,
    HOME,
    PAGE_UP,
    PAGE_DOWN,
    
    RIGHT,
    LEFT,
    UP,
    DOWN,
    
    NUM_LOCK,
    
    NUMPAD_DIVIDE,
    NUMPAD_MULTIPLY,
    NUMPAD_SUBTRACT,
    NUMPAD_ADD,
    NUMPAD_ENTER,
    NUMPAD_DECIMAL,
    
    NUMPAD0,
    NUMPAD1,
    NUMPAD2,
    NUMPAD3,
    NUMPAD4,
    NUMPAD5,
    NUMPAD6,
    NUMPAD7,
    NUMPAD8,
    NUMPAD9,
    
    /**
     * @see Keys#LOCAL_KEY1
     */
    LOCAL_KEY1,
    
    /**
     * @see Keys#LOCAL_KEY2
     */
    LOCAL_KEY2,
    
    /**
     * @see Keys#LOCAL_KEY3
     */
    LOCAL_KEY3,
    
    /**
     * @see Keys#LOCAL_KEY4
     */
    LOCAL_KEY4,
    
    /**
     * @see Keys#LOCAL_KEY5
     */
    LOCAL_KEY5,
    
    /**
     * @see Keys#LOCAL_KEY6
     */
    LOCAL_KEY6,
    
    /**
     * @see Keys#LOCAL_KEY7
     */
    LOCAL_KEY7,
    
    /**
     * @see Keys#LOCAL_KEY8
     */
    LOCAL_KEY8,
    
    /**
     * @see Keys#LOCAL_KEY9
     */
    LOCAL_KEY9,
    
    /**
     * @see Keys#LOCAL_KEY10
     */
    LOCAL_KEY10,
    
    /**
     * @see Keys#LOCAL_KEY11
     */
    LOCAL_KEY11,
    ;
    
    
    /**
     * @return the corresponding {@link Key}.
     */
    public final Key getKey()
    {
        switch ( this )
        {
            case ESCAPE:
                return ( Keys.ESCAPE );
            
            case F1:
                return ( Keys.F1 );
            case F2:
                return ( Keys.F2 );
            case F3:
                return ( Keys.F3 );
            case F4:
                return ( Keys.F4 );
            case F5:
                return ( Keys.F5 );
            case F6:
                return ( Keys.F6 );
            case F7:
                return ( Keys.F7 );
            case F8:
                return ( Keys.F8 );
            case F9:
                return ( Keys.F9 );
            case F10:
                return ( Keys.F10 );
            case F11:
                return ( Keys.F11 );
            case F12:
                return ( Keys.F12 );
            
            case PRINTSCREEN:
                return ( Keys.PRINTSCREEN );
            case PAUSE:
                return ( Keys.PAUSE );
            case SCROLL_LOCK:
                return ( Keys.SCROLL_LOCK );
            
            case CIRCUMFLEX:
                return ( Keys.CIRCUMFLEX );
            
            case _0:
                return ( Keys._0 );
            case _1:
                return ( Keys._1 );
            case _2:
                return ( Keys._2 );
            case _3:
                return ( Keys._3 );
            case _4:
                return ( Keys._4 );
            case _5:
                return ( Keys._5 );
            case _6:
                return ( Keys._6 );
            case _7:
                return ( Keys._7 );
            case _8:
                return ( Keys._8 );
            case _9:
                return ( Keys._9 );
            
            case A:
                return ( Keys.A );
            case B:
                return ( Keys.B );
            case C:
                return ( Keys.C );
            case D:
                return ( Keys.D );
            case E:
                return ( Keys.E );
            case F:
                return ( Keys.F );
            case G:
                return ( Keys.G );
            case H:
                return ( Keys.H );
            case I:
                return ( Keys.I );
            case J:
                return ( Keys.J );
            case K:
                return ( Keys.K );
            case L:
                return ( Keys.L );
            case M:
                return ( Keys.M );
            case N:
                return ( Keys.N );
            case O:
                return ( Keys.O );
            case P:
                return ( Keys.P );
            case Q:
                return ( Keys.Q );
            case R:
                return ( Keys.R );
            case S:
                return ( Keys.S );
            case T:
                return ( Keys.T );
            case U:
                return ( Keys.U );
            case V:
                return ( Keys.V );
            case W:
                return ( Keys.W );
            case X:
                return ( Keys.X );
            case Y:
                return ( Keys.Y );
            case Z:
                return ( Keys.Z );
            
            case TAB:
                return ( Keys.TAB );
            case SPACE:
                return ( Keys.SPACE );
            case BACK_SPACE:
                return ( Keys.BACK_SPACE );
            case ENTER:
                return ( Keys.ENTER );
            
            case LEFT_SHIFT:
                return ( Keys.LEFT_SHIFT );
            case RIGHT_SHIFT:
                return ( Keys.RIGHT_SHIFT );
            case LEFT_CONTROL:
                return ( Keys.LEFT_CONTROL );
            case RIGHT_CONTROL:
                return ( Keys.RIGHT_CONTROL );
            case ALT:
                return ( Keys.ALT );
            case ALT_GRAPH:
                return ( Keys.ALT_GRAPH );
            case LEFT_META:
                return ( Keys.LEFT_META );
            case RIGHT_META:
                return ( Keys.RIGHT_META );
            case CAPS_LOCK:
                return ( Keys.CAPS_LOCK );
            
            case DELETE:
                return ( Keys.DELETE );
            case INSERT:
                return ( Keys.INSERT );
            case END:
                return ( Keys.END );
            case HOME:
                return ( Keys.HOME );
            case PAGE_UP:
                return ( Keys.PAGE_UP );
            case PAGE_DOWN:
                return ( Keys.PAGE_DOWN );
            
            case RIGHT:
                return ( Keys.RIGHT );
            case LEFT:
                return ( Keys.LEFT );
            case UP:
                return ( Keys.UP );
            case DOWN:
                return ( Keys.DOWN );
            
            case NUM_LOCK:
                return ( Keys.NUM_LOCK );
            
            case NUMPAD_DIVIDE:
                return ( Keys.NUMPAD_DIVIDE );
            case NUMPAD_MULTIPLY:
                return ( Keys.NUMPAD_MULTIPLY );
            case NUMPAD_SUBTRACT:
                return ( Keys.NUMPAD_SUBTRACT );
            case NUMPAD_ADD:
                return ( Keys.NUMPAD_ADD );
            case NUMPAD_ENTER:
                return ( Keys.NUMPAD_ENTER );
            case NUMPAD_DECIMAL:
                return ( Keys.NUMPAD_DECIMAL );
            
            case NUMPAD0:
                return ( Keys.NUMPAD0 );
            case NUMPAD1:
                return ( Keys.NUMPAD1 );
            case NUMPAD2:
                return ( Keys.NUMPAD2 );
            case NUMPAD3:
                return ( Keys.NUMPAD3 );
            case NUMPAD4:
                return ( Keys.NUMPAD4 );
            case NUMPAD5:
                return ( Keys.NUMPAD5 );
            case NUMPAD6:
                return ( Keys.NUMPAD6 );
            case NUMPAD7:
                return ( Keys.NUMPAD7 );
            case NUMPAD8:
                return ( Keys.NUMPAD8 );
            case NUMPAD9:
                return ( Keys.NUMPAD9 );
            
            case LOCAL_KEY1:
                return ( Keys.LOCAL_KEY1 );
            case LOCAL_KEY2:
                return ( Keys.LOCAL_KEY2 );
            case LOCAL_KEY3:
                return ( Keys.LOCAL_KEY3 );
            case LOCAL_KEY4:
                return ( Keys.LOCAL_KEY4 );
            case LOCAL_KEY5:
                return ( Keys.LOCAL_KEY5 );
            case LOCAL_KEY6:
                return ( Keys.LOCAL_KEY6 );
            case LOCAL_KEY7:
                return ( Keys.LOCAL_KEY7 );
            case LOCAL_KEY8:
                return ( Keys.LOCAL_KEY8 );
            case LOCAL_KEY9:
                return ( Keys.LOCAL_KEY9 );
            case LOCAL_KEY10:
                return ( Keys.LOCAL_KEY10 );
            case LOCAL_KEY11:
                return ( Keys.LOCAL_KEY11 );
        }
        
        return ( null );
    }
    
    /**
     * @param key
     * 
     * @return the KeyID, that corresponds to the given Key.
     */
    public static final KeyID valueOf( Key key )
    {
        final String keyName = key.getName();
        
        if ( keyName.equals( "0" ) )
            return ( KeyID._0 );
        if ( keyName.equals( "1" ) )
            return ( KeyID._1 );
        if ( keyName.equals( "2" ) )
            return ( KeyID._2 );
        if ( keyName.equals( "3" ) )
            return ( KeyID._3 );
        if ( keyName.equals( "4" ) )
            return ( KeyID._4 );
        if ( keyName.equals( "5" ) )
            return ( KeyID._5 );
        if ( keyName.equals( "6" ) )
            return ( KeyID._6 );
        if ( keyName.equals( "7" ) )
            return ( KeyID._7 );
        if ( keyName.equals( "8" ) )
            return ( KeyID._8 );
        if ( keyName.equals( "9" ) )
            return ( KeyID._9 );
        
        return ( KeyID.valueOf( keyName ) );
    }
}
