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
package org.jagatoo.input.impl.lwjgl;

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
 * LWJGL implementation of the Keyboard class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLKeyboard extends Keyboard
{
    private static final Key[] keyMap = new Key[ org.lwjgl.input.Keyboard.KEYBOARD_SIZE ];
    static
    {
        keyMap[ org.lwjgl.input.Keyboard.KEY_ESCAPE ] = Keys.ESCAPE;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_F1 ]     = Keys.F1;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F2 ]     = Keys.F2;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F3 ]     = Keys.F3;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F4 ]     = Keys.F4;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F5 ]     = Keys.F5;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F6 ]     = Keys.F6;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F7 ]     = Keys.F7;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F8 ]     = Keys.F8;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F9 ]     = Keys.F9;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F10 ]    = Keys.F10;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F11 ]    = Keys.F11;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F12 ]    = Keys.F12;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_PAUSE ] = Keys.PAUSE;
        keyMap[ org.lwjgl.input.Keyboard.KEY_SCROLL ] = Keys.SCROLL_LOCK;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_CIRCUMFLEX ] = Keys.CIRCUMFLEX;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_0 ] = Keys._0;
        keyMap[ org.lwjgl.input.Keyboard.KEY_1 ] = Keys._1;
        keyMap[ org.lwjgl.input.Keyboard.KEY_2 ] = Keys._2;
        keyMap[ org.lwjgl.input.Keyboard.KEY_3 ] = Keys._3;
        keyMap[ org.lwjgl.input.Keyboard.KEY_4 ] = Keys._4;
        keyMap[ org.lwjgl.input.Keyboard.KEY_5 ] = Keys._5;
        keyMap[ org.lwjgl.input.Keyboard.KEY_6 ] = Keys._6;
        keyMap[ org.lwjgl.input.Keyboard.KEY_7 ] = Keys._7;
        keyMap[ org.lwjgl.input.Keyboard.KEY_8 ] = Keys._8;
        keyMap[ org.lwjgl.input.Keyboard.KEY_9 ] = Keys._9;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_A ] = Keys.A;
        keyMap[ org.lwjgl.input.Keyboard.KEY_B ] = Keys.B;
        keyMap[ org.lwjgl.input.Keyboard.KEY_C ] = Keys.C;
        keyMap[ org.lwjgl.input.Keyboard.KEY_D ] = Keys.D;
        keyMap[ org.lwjgl.input.Keyboard.KEY_E ] = Keys.E;
        keyMap[ org.lwjgl.input.Keyboard.KEY_F ] = Keys.F;
        keyMap[ org.lwjgl.input.Keyboard.KEY_G ] = Keys.G;
        keyMap[ org.lwjgl.input.Keyboard.KEY_H ] = Keys.H;
        keyMap[ org.lwjgl.input.Keyboard.KEY_I ] = Keys.I;
        keyMap[ org.lwjgl.input.Keyboard.KEY_J ] = Keys.J;
        keyMap[ org.lwjgl.input.Keyboard.KEY_K ] = Keys.K;
        keyMap[ org.lwjgl.input.Keyboard.KEY_L ] = Keys.L;
        keyMap[ org.lwjgl.input.Keyboard.KEY_M ] = Keys.M;
        keyMap[ org.lwjgl.input.Keyboard.KEY_N ] = Keys.N;
        keyMap[ org.lwjgl.input.Keyboard.KEY_O ] = Keys.O;
        keyMap[ org.lwjgl.input.Keyboard.KEY_P ] = Keys.P;
        keyMap[ org.lwjgl.input.Keyboard.KEY_Q ] = Keys.Q;
        keyMap[ org.lwjgl.input.Keyboard.KEY_R ] = Keys.R;
        keyMap[ org.lwjgl.input.Keyboard.KEY_S ] = Keys.S;
        keyMap[ org.lwjgl.input.Keyboard.KEY_T ] = Keys.T;
        keyMap[ org.lwjgl.input.Keyboard.KEY_U ] = Keys.U;
        keyMap[ org.lwjgl.input.Keyboard.KEY_V ] = Keys.V;
        keyMap[ org.lwjgl.input.Keyboard.KEY_W ] = Keys.W;
        keyMap[ org.lwjgl.input.Keyboard.KEY_X ] = Keys.X;
        keyMap[ org.lwjgl.input.Keyboard.KEY_Y ] = Keys.Y;
        keyMap[ org.lwjgl.input.Keyboard.KEY_Z ] = Keys.Z;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_TAB ]       = Keys.TAB;
        keyMap[ org.lwjgl.input.Keyboard.KEY_SPACE ]     = Keys.SPACE;
        keyMap[ org.lwjgl.input.Keyboard.KEY_BACK ]      = Keys.BACK_SPACE;
        keyMap[ org.lwjgl.input.Keyboard.KEY_RETURN ]    = Keys.ENTER;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_LSHIFT ] = Keys.LEFT_SHIFT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_RSHIFT ] = Keys.RIGHT_SHIFT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_LCONTROL ] = Keys.LEFT_CONTROL;
        keyMap[ org.lwjgl.input.Keyboard.KEY_RCONTROL ] = Keys.RIGHT_CONTROL;
        keyMap[ org.lwjgl.input.Keyboard.KEY_LMENU ] = Keys.ALT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_RMENU ] = Keys.ALT_GRAPH;
        keyMap[ org.lwjgl.input.Keyboard.KEY_LMETA ] = Keys.LEFT_META;
        keyMap[ org.lwjgl.input.Keyboard.KEY_RMETA ] = Keys.RIGHT_META;
        keyMap[ org.lwjgl.input.Keyboard.KEY_CAPITAL ] = Keys.CAPS_LOCK;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_DELETE ] = Keys.DELETE;
        keyMap[ org.lwjgl.input.Keyboard.KEY_INSERT ] = Keys.INSERT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_END ] = Keys.END;
        keyMap[ org.lwjgl.input.Keyboard.KEY_HOME ] = Keys.HOME;
        keyMap[ org.lwjgl.input.Keyboard.KEY_PRIOR ] = Keys.PAGE_UP;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NEXT ] = Keys.PAGE_DOWN;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_RIGHT ] = Keys.RIGHT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_LEFT ] = Keys.LEFT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_UP ] = Keys.UP;
        keyMap[ org.lwjgl.input.Keyboard.KEY_DOWN ] = Keys.DOWN;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMLOCK ]      = Keys.NUM_LOCK;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_DIVIDE ]       = Keys.NUMPAD_DIVIDE;
        keyMap[ org.lwjgl.input.Keyboard.KEY_MULTIPLY ]     = Keys.NUMPAD_MULTIPLY;
        keyMap[ org.lwjgl.input.Keyboard.KEY_SUBTRACT ]     = Keys.NUMPAD_SUBTRACT;
        keyMap[ org.lwjgl.input.Keyboard.KEY_ADD ]          = Keys.NUMPAD_ADD;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPADENTER ]  = Keys.NUMPAD_ENTER;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPADCOMMA ]  = Keys.NUMPAD_DECIMAL;
        
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD0 ] = Keys.NUMPAD0;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD1 ] = Keys.NUMPAD1;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD2 ] = Keys.NUMPAD2;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD3 ] = Keys.NUMPAD3;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD4 ] = Keys.NUMPAD4;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD5 ] = Keys.NUMPAD5;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD6 ] = Keys.NUMPAD6;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD7 ] = Keys.NUMPAD7;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD8 ] = Keys.NUMPAD8;
        keyMap[ org.lwjgl.input.Keyboard.KEY_NUMPAD9 ] = Keys.NUMPAD9;
    }
    
    public static final Key convertKey( int lwjglKey, char keyChar )
    {
        Key key = keyMap[ lwjglKey ];
        
        if ( key == null )
            key = KeyboardLocalizer.getMapping().getLocalizedKey( keyChar );
        
        if ( ( key == Keys.DELETE ) && ( keyChar != '\0' ) )
            key = Keys.NUMPAD_DECIMAL;
        
        return ( key );
    }
    
    /*
     * Support for continuous key-typed events!
     */
    private Key lastPressedKey = null;
    private char typedChar = '\0';
    private long nextContTypedTime = -1L;
    private static final long CONTINUOUS_TYPED_GAP = 200000000L;
    private static final long CONTINUOUS_TYPED_DELTA = 50000000L;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasKeyStateChanged( Key key, boolean keyState )
    {
        return ( true );
    }
    
    private boolean triggerTyped( char keyChar, int modifierMask, long nanoTime, long lastNanoTime, EventQueue eventQueue )
    {
        final KeyTypedEvent typedEv = prepareKeyTypedEvent( keyChar, modifierMask, nanoTime, lastNanoTime );
        
        if ( typedEv == null )
            return ( false );
        
        if ( eventQueue != null )
            eventQueue.enqueue( typedEv );
        else
            fireOnKeyTyped( typedEv, true );
        
        return ( true );
    }
    
    protected final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime, boolean acceptsEvents ) throws InputSystemException
    {
        final boolean isQueued = ( eventQueue != null );
        
        if ( !org.lwjgl.opengl.Display.isCreated() )
            throw new InputSystemException( "Display is not created." );
        
        if ( !org.lwjgl.input.Keyboard.isCreated() )
            throw new InputSystemException( "Mouse is not created." );
        
        try
        {
            LWJGLMessageProcessor.processMessages( nanoTime );
            
            org.lwjgl.input.Keyboard.poll();
            
            while ( org.lwjgl.input.Keyboard.next() )
            {
                if ( !acceptsEvents )
                    continue;
                
                final int keyCode = org.lwjgl.input.Keyboard.getEventKey();
                char keyChar = org.lwjgl.input.Keyboard.getEventCharacter();
                
                final boolean keyState = org.lwjgl.input.Keyboard.getEventKeyState();
                
                final Key key = convertKey( keyCode, keyChar );
                
                final int modifierMask = applyModifier( key, keyState );
                
                if ( keyState )
                {
                    final KeyPressedEvent pressedEv = prepareKeyPressedEvent( key, modifierMask, nanoTime, 0L );
                    
                    is.notifyInputStatesManagers( this, key, 1, +1, nanoTime );
                    
                    if ( pressedEv == null )
                        continue;
                    
                    lastPressedKey = key;
                    nextContTypedTime = nanoTime + CONTINUOUS_TYPED_GAP;
                    
                    if ( isQueued )
                        eventQueue.enqueue( pressedEv );
                    else
                        fireOnKeyPressed( pressedEv, true );
                    
                    if ( key == Keys.DELETE )
                        keyChar = (char)127;
                    
                    typedChar = keyChar;
                    
                    if ( keyChar != '\0' )
                    {
                        triggerTyped( keyChar, modifierMask, nanoTime, 0L, eventQueue );
                    }
                    else
                    {
                        lastPressedKey = null;
                    }
                }
                else
                {
                    final KeyReleasedEvent releasedEv = prepareKeyReleasedEvent( key, modifierMask, nanoTime, 0L );
                    
                    is.notifyInputStatesManagers( this, key, 0, -1, nanoTime );
                    
                    if ( releasedEv == null )
                        continue;
                    
                    lastPressedKey = null;
                    typedChar = '\0';
                    
                    if ( isQueued )
                        eventQueue.enqueue( releasedEv );
                    else
                        fireOnKeyReleased( releasedEv, true );
                }
            }
            
            if ( ( lastPressedKey != null ) && ( nanoTime >= nextContTypedTime ) )
            {
                triggerTyped( typedChar, getModifierMask(), nanoTime, 0L, eventQueue );
                
                nextContTypedTime += CONTINUOUS_TYPED_DELTA;
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
        collectOrFireEvents( is, null, nanoTime, false );
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
        try
        {
            if ( org.lwjgl.input.Keyboard.isCreated() )
            {
                org.lwjgl.input.Keyboard.destroy();
            }
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
    }
    
    protected LWJGLKeyboard( KeyboardFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Keyboard" );
        
        try
        {
            if ( !org.lwjgl.input.Keyboard.isCreated() )
                org.lwjgl.input.Keyboard.create();
        }
        catch ( org.lwjgl.LWJGLException e )
        {
            throw new InputSystemException( e );
        }
    }
}
