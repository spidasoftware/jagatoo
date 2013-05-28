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
package org.jagatoo.input.impl.jinput;

import java.util.HashMap;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.KeyboardFactory;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.KeyPressedEvent;
import org.jagatoo.input.events.KeyReleasedEvent;
import org.jagatoo.input.events.KeyTypedEvent;
import org.jagatoo.input.localization.KeyboardLocalizer;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * JInput implementation of the Keyboard class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class JInputKeyboard extends Keyboard
{
    private final net.java.games.input.Keyboard keyboard;
    
    private final HashMap< net.java.games.input.Component, Key > keyMap = new HashMap< net.java.games.input.Component, Key >();
    
    private final char getKeyChar( Key key, char char0, char charShift, int modifierMask )
    {
        if ( modifierMask == 0 )
            return ( char0 );
        
        if ( modifierMask == Keys.MODIFIER_SHIFT )
        {
            if ( charShift != '\0' )
                return ( charShift );
        }
        
        return ( KeyboardLocalizer.getMapping().getModifiedChar( key, char0, modifierMask ) );
    }
    
    private final char getKeyChar( Key key, int modifierMask )
    {
        switch ( key.getKeyID() )
        {
            case _0:
                return ( getKeyChar( key, '0', '\0', modifierMask ) );
            case _1:
                return ( getKeyChar( key, '1', '\0', modifierMask ) );
            case _2:
                return ( getKeyChar( key, '2', '\0', modifierMask ) );
            case _3:
                return ( getKeyChar( key, '3', '\0', modifierMask ) );
            case _4:
                return ( getKeyChar( key, '4', '\0', modifierMask ) );
            case _5:
                return ( getKeyChar( key, '5', '\0', modifierMask ) );
            case _6:
                return ( getKeyChar( key, '6', '\0', modifierMask ) );
            case _7:
                return ( getKeyChar( key, '7', '\0', modifierMask ) );
            case _8:
                return ( getKeyChar( key, '8', '\0', modifierMask ) );
            case _9:
                return ( getKeyChar( key, '9', '\0', modifierMask ) );
            
            case A:
                return ( getKeyChar( key, 'a', 'A', modifierMask ) );
            case B:
                return ( getKeyChar( key, 'b', 'B', modifierMask ) );
            case C:
                return ( getKeyChar( key, 'c', 'C', modifierMask ) );
            case D:
                return ( getKeyChar( key, 'd', 'D', modifierMask ) );
            case E:
                return ( getKeyChar( key, 'e', 'E', modifierMask ) );
            case F:
                return ( getKeyChar( key, 'f', 'F', modifierMask ) );
            case G:
                return ( getKeyChar( key, 'g', 'G', modifierMask ) );
            case H:
                return ( getKeyChar( key, 'h', 'H', modifierMask ) );
            case I:
                return ( getKeyChar( key, 'i', 'I', modifierMask ) );
            case J:
                return ( getKeyChar( key, 'j', 'J', modifierMask ) );
            case K:
                return ( getKeyChar( key, 'k', 'K', modifierMask ) );
            case L:
                return ( getKeyChar( key, 'l', 'L', modifierMask ) );
            case M:
                return ( getKeyChar( key, 'm', 'M', modifierMask ) );
            case N:
                return ( getKeyChar( key, 'n', 'N', modifierMask ) );
            case O:
                return ( getKeyChar( key, 'o', 'O', modifierMask ) );
            case P:
                return ( getKeyChar( key, 'p', 'P', modifierMask ) );
            case Q:
                return ( getKeyChar( key, 'q', 'Q', modifierMask ) );
            case R:
                return ( getKeyChar( key, 'r', 'R', modifierMask ) );
            case S:
                return ( getKeyChar( key, 's', 'S', modifierMask ) );
            case T:
                return ( getKeyChar( key, 't', 'T', modifierMask ) );
            case U:
                return ( getKeyChar( key, 'u', 'U', modifierMask ) );
            case V:
                return ( getKeyChar( key, 'v', 'V', modifierMask ) );
            case W:
                return ( getKeyChar( key, 'w', 'W', modifierMask ) );
            case X:
                return ( getKeyChar( key, 'x', 'X', modifierMask ) );
            case Y:
                return ( getKeyChar( key, 'y', 'Y', modifierMask ) );
            case Z:
                return ( getKeyChar( key, 'z', 'Z', modifierMask ) );
            
            case TAB:
                return ( '\t' );
            case SPACE:
                return ( ' ' );
            case BACK_SPACE:
                return ( '\b' );
            case ENTER:
                return ( '\r' );
            
            case DELETE:
                return ( (char)127 );
            
            case NUMPAD_DIVIDE:
                return ( '/' );
            case NUMPAD_MULTIPLY:
                return ( '*' );
            case NUMPAD_SUBTRACT:
                return ( '-' );
            case NUMPAD_ADD:
                return ( '+' );
            case NUMPAD_ENTER:
                return ( '\r' );
            
            case NUMPAD0:
                return ( '0' );
            case NUMPAD1:
                return ( '1' );
            case NUMPAD2:
                return ( '2' );
            case NUMPAD3:
                return ( '3' );
            case NUMPAD4:
                return ( '4' );
            case NUMPAD5:
                return ( '5' );
            case NUMPAD6:
                return ( '6' );
            case NUMPAD7:
                return ( '7' );
            case NUMPAD8:
                return ( '8' );
            case NUMPAD9:
                return ( '9' );
            
            case CIRCUMFLEX:
            case NUMPAD_DECIMAL:
            case LOCAL_KEY1:
            case LOCAL_KEY2:
            case LOCAL_KEY3:
            case LOCAL_KEY4:
            case LOCAL_KEY5:
            case LOCAL_KEY6:
            case LOCAL_KEY7:
            case LOCAL_KEY8:
            case LOCAL_KEY9:
            case LOCAL_KEY10:
            case LOCAL_KEY11:
                return ( KeyboardLocalizer.getMapping().getModifiedChar( key, '\0', modifierMask ) );
        }
        
        return ( '\0' );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasKeyStateChanged( Key key, boolean keyState )
    {
        return ( true );
    }
    
    private final net.java.games.input.Event event = new net.java.games.input.Event();
    
    protected final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime, boolean acceptsEvents ) throws InputSystemException
    {
        final boolean isQueued = ( eventQueue != null );
        
        try
        {
            keyboard.poll();
            
            while ( keyboard.getEventQueue().getNextEvent( event ) )
            {
                if ( !acceptsEvents )
                    continue;
                
                Key key = keyMap.get( event.getComponent() );
                
                if ( key == null )
                    continue;
                
                switch ( key.getKeyID() )
                {
                    case Y:
                        key = KeyboardLocalizer.getMapping().getUpperYZKey();
                        break;
                    case Z:
                        key = KeyboardLocalizer.getMapping().getLowerYZKey();
                        break;
                }
                
                final boolean keyState = (int)event.getValue() != 0;
                
                final int modifierMask = applyModifier( key, keyState );
                
                if ( keyState )
                {
                    final KeyPressedEvent pressedEv = prepareKeyPressedEvent( key, modifierMask, nanoTime, 0L );
                    
                    is.notifyInputStatesManagers( this, key, 1, +1, nanoTime );
                    
                    if ( pressedEv == null )
                        continue;
                    
                    if ( isQueued )
                        eventQueue.enqueue( pressedEv );
                    else
                        fireOnKeyPressed( pressedEv, true );
                    
                    final char keyChar = getKeyChar( key, modifierMask );
                    
                    if ( keyChar != '\0' )
                    {
                        final KeyTypedEvent typedEv = prepareKeyTypedEvent( keyChar, modifierMask, nanoTime, 0L );
                        
                        if ( typedEv == null )
                            continue;
                        
                        if ( isQueued )
                            eventQueue.enqueue( typedEv );
                        else
                            fireOnKeyTyped( typedEv, true );
                    }
                }
                else
                {
                    final KeyReleasedEvent releasedEv = prepareKeyReleasedEvent( key, modifierMask, nanoTime, 0L );
                    
                    is.notifyInputStatesManagers( this, key, 0, -1, nanoTime );
                    
                    if ( releasedEv == null )
                        continue;
                    
                    if ( isQueued )
                        eventQueue.enqueue( releasedEv );
                    else
                        fireOnKeyReleased( releasedEv, true );
                }
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        collectOrFireEvents( is, eventQueue, nanoTime, false );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( eventQueue == null )
            throw new InputSystemException( "EventQueue must not be null here!" );
        
        final boolean acceptEvents = ( isEnabled() && getSourceWindow().receivesInputEvents() );
        
        collectOrFireEvents( is, eventQueue, nanoTime, acceptEvents );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        collectOrFireEvents( is, null, nanoTime, true );
        
        getEventQueue().dequeueAndFire( is, InputEvent.Type.KEYBOARD_EVENT );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceRegistered( InputSystem inputSystem ) throws InputSystemException
    {
        consumePendingEvents( inputSystem, null, -1L );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        /*
        try
        {
            if ( org.lwjgl.input.Keyboard.isCreated() )
            {
                org.lwjgl.input.Keyboard.destroy();
            }
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t ) );
        }
        */
    }
    
    protected JInputKeyboard( KeyboardFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue, net.java.games.input.Keyboard keyboard ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, keyboard.getName() );
        
        this.keyboard = keyboard;
        
        for ( net.java.games.input.Component comp: keyboard.getComponents() )
        {
            if ( comp.getName().equals( "Escape" ) )
                keyMap.put( comp, Keys.ESCAPE );
            
            else if ( comp.getName().equals( "F1" ) )
                keyMap.put( comp, Keys.F1 );
            else if ( comp.getName().equals( "F2" ) )
                keyMap.put( comp, Keys.F2 );
            else if ( comp.getName().equals( "F3" ) )
                keyMap.put( comp, Keys.F3 );
            else if ( comp.getName().equals( "F4" ) )
                keyMap.put( comp, Keys.F4 );
            else if ( comp.getName().equals( "F5" ) )
                keyMap.put( comp, Keys.F5 );
            else if ( comp.getName().equals( "F6" ) )
                keyMap.put( comp, Keys.F6 );
            else if ( comp.getName().equals( "F7" ) )
                keyMap.put( comp, Keys.F7 );
            else if ( comp.getName().equals( "F8" ) )
                keyMap.put( comp, Keys.F8 );
            else if ( comp.getName().equals( "F9" ) )
                keyMap.put( comp, Keys.F9 );
            else if ( comp.getName().equals( "F10" ) )
                keyMap.put( comp, Keys.F10 );
            else if ( comp.getName().equals( "F11" ) )
                keyMap.put( comp, Keys.F11 );
            else if ( comp.getName().equals( "F12" ) )
                keyMap.put( comp, Keys.F12 );
            
            else if ( comp.getName().equals( "SysRq" ) )
                keyMap.put( comp, Keys.PRINTSCREEN );
            else if ( comp.getName().equals( "Scroll Lock" ) )
                keyMap.put( comp, Keys.SCROLL_LOCK );
            else if ( comp.getName().equals( "Pause" ) )
                keyMap.put( comp, Keys.PAUSE );
            
            else if ( comp.getName().equals( "~" ) )
                keyMap.put( comp, Keys.CIRCUMFLEX );
            
            else if ( comp.getName().equals( "0" ) )
                keyMap.put( comp, Keys._0 );
            else if ( comp.getName().equals( "1" ) )
                keyMap.put( comp, Keys._1 );
            else if ( comp.getName().equals( "2" ) )
                keyMap.put( comp, Keys._2 );
            else if ( comp.getName().equals( "3" ) )
                keyMap.put( comp, Keys._3 );
            else if ( comp.getName().equals( "4" ) )
                keyMap.put( comp, Keys._4 );
            else if ( comp.getName().equals( "5" ) )
                keyMap.put( comp, Keys._5 );
            else if ( comp.getName().equals( "6" ) )
                keyMap.put( comp, Keys._6 );
            else if ( comp.getName().equals( "7" ) )
                keyMap.put( comp, Keys._7 );
            else if ( comp.getName().equals( "8" ) )
                keyMap.put( comp, Keys._8 );
            else if ( comp.getName().equals( "9" ) )
                keyMap.put( comp, Keys._9 );
            
            else if ( comp.getName().equals( "A" ) )
                keyMap.put( comp, Keys.A );
            else if ( comp.getName().equals( "B" ) )
                keyMap.put( comp, Keys.B );
            else if ( comp.getName().equals( "C" ) )
                keyMap.put( comp, Keys.C );
            else if ( comp.getName().equals( "D" ) )
                keyMap.put( comp, Keys.D );
            else if ( comp.getName().equals( "E" ) )
                keyMap.put( comp, Keys.E );
            else if ( comp.getName().equals( "F" ) )
                keyMap.put( comp, Keys.F );
            else if ( comp.getName().equals( "G" ) )
                keyMap.put( comp, Keys.G );
            else if ( comp.getName().equals( "H" ) )
                keyMap.put( comp, Keys.H );
            else if ( comp.getName().equals( "I" ) )
                keyMap.put( comp, Keys.I );
            else if ( comp.getName().equals( "J" ) )
                keyMap.put( comp, Keys.J );
            else if ( comp.getName().equals( "K" ) )
                keyMap.put( comp, Keys.K );
            else if ( comp.getName().equals( "L" ) )
                keyMap.put( comp, Keys.L );
            else if ( comp.getName().equals( "M" ) )
                keyMap.put( comp, Keys.M );
            else if ( comp.getName().equals( "N" ) )
                keyMap.put( comp, Keys.N );
            else if ( comp.getName().equals( "O" ) )
                keyMap.put( comp, Keys.O );
            else if ( comp.getName().equals( "P" ) )
                keyMap.put( comp, Keys.P );
            else if ( comp.getName().equals( "Q" ) )
                keyMap.put( comp, Keys.Q );
            else if ( comp.getName().equals( "R" ) )
                keyMap.put( comp, Keys.R );
            else if ( comp.getName().equals( "S" ) )
                keyMap.put( comp, Keys.S );
            else if ( comp.getName().equals( "T" ) )
                keyMap.put( comp, Keys.T );
            else if ( comp.getName().equals( "U" ) )
                keyMap.put( comp, Keys.U );
            else if ( comp.getName().equals( "V" ) )
                keyMap.put( comp, Keys.V );
            else if ( comp.getName().equals( "W" ) )
                keyMap.put( comp, Keys.W );
            else if ( comp.getName().equals( "X" ) )
                keyMap.put( comp, Keys.X );
            else if ( comp.getName().equals( "Y" ) )
                keyMap.put( comp, Keys.Y );
            else if ( comp.getName().equals( "Z" ) )
                keyMap.put( comp, Keys.Z );
            
            else if ( comp.getName().equals( "Tab" ) )
                keyMap.put( comp, Keys.TAB );
            else if ( comp.getName().equals( " " ) )
                keyMap.put( comp, Keys.SPACE );
            else if ( comp.getName().equals( "Back" ) )
                keyMap.put( comp, Keys.BACK_SPACE );
            else if ( comp.getName().equals( "Return" ) )
                keyMap.put( comp, Keys.ENTER );
            
            else if ( comp.getName().equals( "Left Shift" ) )
                keyMap.put( comp, Keys.LEFT_SHIFT );
            else if ( comp.getName().equals( "Right Shift" ) )
                keyMap.put( comp, Keys.RIGHT_SHIFT );
            else if ( comp.getName().equals( "Left Control" ) )
                keyMap.put( comp, Keys.LEFT_CONTROL );
            else if ( comp.getName().equals( "Right Control" ) )
                keyMap.put( comp, Keys.RIGHT_CONTROL );
            else if ( comp.getName().equals( "Left Alt" ) )
                keyMap.put( comp, Keys.ALT );
            else if ( comp.getName().equals( "Right Alt" ) )
                keyMap.put( comp, Keys.ALT_GRAPH );
            else if ( comp.getName().equals( "Caps Lock" ) )
                keyMap.put( comp, Keys.CAPS_LOCK );
            
            else if ( comp.getName().equals( "Insert" ) )
                keyMap.put( comp, Keys.INSERT );
            else if ( comp.getName().equals( "Delete" ) )
                keyMap.put( comp, Keys.DELETE );
            else if ( comp.getName().equals( "Home" ) )
                keyMap.put( comp, Keys.HOME );
            else if ( comp.getName().equals( "End" ) )
                keyMap.put( comp, Keys.END );
            else if ( comp.getName().equals( "Pg Up" ) )
                keyMap.put( comp, Keys.PAGE_UP );
            else if ( comp.getName().equals( "Pg Down" ) )
                keyMap.put( comp, Keys.PAGE_DOWN );
            
            else if ( comp.getName().equals( "Right" ) )
                keyMap.put( comp, Keys.RIGHT );
            else if ( comp.getName().equals( "Left" ) )
                keyMap.put( comp, Keys.LEFT );
            else if ( comp.getName().equals( "Up" ) )
                keyMap.put( comp, Keys.UP );
            else if ( comp.getName().equals( "Down" ) )
                keyMap.put( comp, Keys.DOWN );
            
            else if ( comp.getName().equals( "Num Lock" ) )
                keyMap.put( comp, Keys.NUM_LOCK );
            else if ( comp.getName().equals( "Num /" ) )
                keyMap.put( comp, Keys.NUMPAD_DIVIDE );
            else if ( comp.getName().equals( "Multiply" ) )
                keyMap.put( comp, Keys.NUMPAD_MULTIPLY );
            else if ( comp.getName().equals( "Num -" ) )
                keyMap.put( comp, Keys.NUMPAD_SUBTRACT );
            else if ( comp.getName().equals( "Num +" ) )
                keyMap.put( comp, Keys.NUMPAD_ADD );
            else if ( comp.getName().equals( "Num Enter" ) )
                keyMap.put( comp, Keys.NUMPAD_ENTER );
            else if ( comp.getName().equals( "Num ." ) )
                keyMap.put( comp, Keys.NUMPAD_DECIMAL );
            
            else if ( comp.getName().equals( "Num 0" ) )
                keyMap.put( comp, Keys.NUMPAD0 );
            else if ( comp.getName().equals( "Num 1" ) )
                keyMap.put( comp, Keys.NUMPAD1 );
            else if ( comp.getName().equals( "Num 2" ) )
                keyMap.put( comp, Keys.NUMPAD2 );
            else if ( comp.getName().equals( "Num 3" ) )
                keyMap.put( comp, Keys.NUMPAD3 );
            else if ( comp.getName().equals( "Num 4" ) )
                keyMap.put( comp, Keys.NUMPAD4 );
            else if ( comp.getName().equals( "Num 5" ) )
                keyMap.put( comp, Keys.NUMPAD5 );
            else if ( comp.getName().equals( "Num 6" ) )
                keyMap.put( comp, Keys.NUMPAD6 );
            else if ( comp.getName().equals( "Num 7" ) )
                keyMap.put( comp, Keys.NUMPAD7 );
            else if ( comp.getName().equals( "Num 8" ) )
                keyMap.put( comp, Keys.NUMPAD8 );
            else if ( comp.getName().equals( "Num 9" ) )
                keyMap.put( comp, Keys.NUMPAD9 );
            
            else if ( comp.getName().equals( "-" ) )
                keyMap.put( comp, Keys.LOCAL_KEY1 );
            else if ( comp.getName().equals( "=" ) )
                keyMap.put( comp, Keys.LOCAL_KEY2 );
            else if ( comp.getName().equals( "[" ) )
                keyMap.put( comp, Keys.LOCAL_KEY3 );
            else if ( comp.getName().equals( "]" ) )
                keyMap.put( comp, Keys.LOCAL_KEY4 );
            else if ( comp.getName().equals( ";" ) )
                keyMap.put( comp, Keys.LOCAL_KEY5 );
            else if ( comp.getName().equals( "'" ) )
                keyMap.put( comp, Keys.LOCAL_KEY6 );
            else if ( comp.getName().equals( "\\" ) )
                keyMap.put( comp, Keys.LOCAL_KEY7 );
            else if ( comp.getName().equals( "," ) )
                keyMap.put( comp, Keys.LOCAL_KEY8 );
            else if ( comp.getName().equals( "." ) )
                keyMap.put( comp, Keys.LOCAL_KEY9 );
            else if ( comp.getName().equals( "/" ) )
                keyMap.put( comp, Keys.LOCAL_KEY10 );
            
            /*
            else if ( comp.getName().equals( "Num =" ) )
                keyMap.put( comp, Keys.LOCAL_KEY );
            */
        }
    }
}
