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
package org.jagatoo.input.impl.awt;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.SwingUtilities;
import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.KeyboardFactory;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.KeyID;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.KeyPressedEvent;
import org.jagatoo.input.events.KeyReleasedEvent;
import org.jagatoo.input.events.KeyTypedEvent;
import org.jagatoo.input.localization.KeyboardLocalizer;
import org.jagatoo.input.render.InputSourceWindow;
import org.jagatoo.logging.Log;

/**
 * AWT implementation of the Keyboard class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class AWTKeyboard extends Keyboard
{
    private static final boolean IS_WINDOWS = ( System.getenv( "windir" ) != null );
    
    private static final Key convertKey( int awtKey )
    {
        switch ( awtKey )
        {
            case java.awt.event.KeyEvent.VK_ESCAPE: return ( Keys.ESCAPE );
            
            case java.awt.event.KeyEvent.VK_F1: return ( Keys.F1 );
            case java.awt.event.KeyEvent.VK_F2: return ( Keys.F2 );
            case java.awt.event.KeyEvent.VK_F3: return ( Keys.F3 );
            case java.awt.event.KeyEvent.VK_F4: return ( Keys.F4 );
            case java.awt.event.KeyEvent.VK_F5: return ( Keys.F5 );
            case java.awt.event.KeyEvent.VK_F6: return ( Keys.F6 );
            case java.awt.event.KeyEvent.VK_F7: return ( Keys.F7 );
            case java.awt.event.KeyEvent.VK_F8: return ( Keys.F8 );
            case java.awt.event.KeyEvent.VK_F9: return ( Keys.F9 );
            case java.awt.event.KeyEvent.VK_F10: return ( Keys.F10 );
            case java.awt.event.KeyEvent.VK_F11: return ( Keys.F11 );
            case java.awt.event.KeyEvent.VK_F12: return ( Keys.F12 );
            
            case java.awt.event.KeyEvent.VK_PAUSE: return ( Keys.PAUSE );
            case java.awt.event.KeyEvent.VK_SCROLL_LOCK: return ( Keys.SCROLL_LOCK );
            
            case 130:
                if ( IS_WINDOWS )
                {
                    // On Windows the CIRCUMFLEX (^) Key is mapped to the wrong code!
                    return ( Keys.CIRCUMFLEX );
                }
                
                return ( null );
                
            case java.awt.event.KeyEvent.VK_CIRCUMFLEX: return ( Keys.CIRCUMFLEX );
            
            case java.awt.event.KeyEvent.VK_0: return ( Keys._0 );
            case java.awt.event.KeyEvent.VK_1: return ( Keys._1 );
            case java.awt.event.KeyEvent.VK_2: return ( Keys._2 );
            case java.awt.event.KeyEvent.VK_3: return ( Keys._3 );
            case java.awt.event.KeyEvent.VK_4: return ( Keys._4 );
            case java.awt.event.KeyEvent.VK_5: return ( Keys._5 );
            case java.awt.event.KeyEvent.VK_6: return ( Keys._6 );
            case java.awt.event.KeyEvent.VK_7: return ( Keys._7 );
            case java.awt.event.KeyEvent.VK_8: return ( Keys._8 );
            case java.awt.event.KeyEvent.VK_9: return ( Keys._9 );
            
            case java.awt.event.KeyEvent.VK_A: return ( Keys.A );
            case java.awt.event.KeyEvent.VK_B: return ( Keys.B );
            case java.awt.event.KeyEvent.VK_C: return ( Keys.C );
            case java.awt.event.KeyEvent.VK_D: return ( Keys.D );
            case java.awt.event.KeyEvent.VK_E: return ( Keys.E );
            case java.awt.event.KeyEvent.VK_F: return ( Keys.F );
            case java.awt.event.KeyEvent.VK_G: return ( Keys.G );
            case java.awt.event.KeyEvent.VK_H: return ( Keys.H );
            case java.awt.event.KeyEvent.VK_I: return ( Keys.I );
            case java.awt.event.KeyEvent.VK_J: return ( Keys.J );
            case java.awt.event.KeyEvent.VK_K: return ( Keys.K );
            case java.awt.event.KeyEvent.VK_L: return ( Keys.L );
            case java.awt.event.KeyEvent.VK_M: return ( Keys.M );
            case java.awt.event.KeyEvent.VK_N: return ( Keys.N );
            case java.awt.event.KeyEvent.VK_O: return ( Keys.O );
            case java.awt.event.KeyEvent.VK_P: return ( Keys.P );
            case java.awt.event.KeyEvent.VK_Q: return ( Keys.Q );
            case java.awt.event.KeyEvent.VK_R: return ( Keys.R );
            case java.awt.event.KeyEvent.VK_S: return ( Keys.S );
            case java.awt.event.KeyEvent.VK_T: return ( Keys.T );
            case java.awt.event.KeyEvent.VK_U: return ( Keys.U );
            case java.awt.event.KeyEvent.VK_V: return ( Keys.V );
            case java.awt.event.KeyEvent.VK_W: return ( Keys.W );
            case java.awt.event.KeyEvent.VK_X: return ( Keys.X );
            case java.awt.event.KeyEvent.VK_Y: return ( Keys.Y );
            case java.awt.event.KeyEvent.VK_Z: return ( Keys.Z );
            
            case java.awt.event.KeyEvent.VK_TAB: return ( Keys.TAB );
            case java.awt.event.KeyEvent.VK_SPACE: return ( Keys.SPACE );
            case java.awt.event.KeyEvent.VK_BACK_SPACE: return ( Keys.BACK_SPACE );
            
            case java.awt.event.KeyEvent.VK_ALT: return ( Keys.ALT );
            case java.awt.event.KeyEvent.VK_ALT_GRAPH: return ( Keys.ALT_GRAPH );
            case java.awt.event.KeyEvent.VK_CAPS_LOCK: return ( Keys.CAPS_LOCK );
            
            case java.awt.event.KeyEvent.VK_DELETE: return ( Keys.DELETE );
            case java.awt.event.KeyEvent.VK_INSERT: return ( Keys.INSERT );
            case java.awt.event.KeyEvent.VK_END: return ( Keys.END );
            case java.awt.event.KeyEvent.VK_HOME: return ( Keys.HOME );
            case java.awt.event.KeyEvent.VK_PAGE_UP: return ( Keys.PAGE_UP );
            case java.awt.event.KeyEvent.VK_PAGE_DOWN: return ( Keys.PAGE_DOWN );
            
            case java.awt.event.KeyEvent.VK_RIGHT: return ( Keys.RIGHT );
            case java.awt.event.KeyEvent.VK_LEFT: return ( Keys.LEFT );
            case java.awt.event.KeyEvent.VK_UP: return ( Keys.UP );
            case java.awt.event.KeyEvent.VK_DOWN: return ( Keys.DOWN );
            
            case java.awt.event.KeyEvent.VK_NUM_LOCK      : return ( Keys.NUM_LOCK );
            
            case java.awt.event.KeyEvent.VK_DIVIDE      : return ( Keys.NUMPAD_DIVIDE );
            case java.awt.event.KeyEvent.VK_MULTIPLY   : return ( Keys.NUMPAD_MULTIPLY );
            case java.awt.event.KeyEvent.VK_SUBTRACT   : return ( Keys.NUMPAD_SUBTRACT );
            case java.awt.event.KeyEvent.VK_ADD         : return ( Keys.NUMPAD_ADD );
            //case java.awt.event.KeyEvent.VK_NUM : return ( Keys.NUMPAD_DECIMAL );
            
            case java.awt.event.KeyEvent.VK_NUMPAD0: return ( Keys.NUMPAD0 );
            case java.awt.event.KeyEvent.VK_NUMPAD1: return ( Keys.NUMPAD1 );
            case java.awt.event.KeyEvent.VK_NUMPAD2: return ( Keys.NUMPAD2 );
            case java.awt.event.KeyEvent.VK_NUMPAD3: return ( Keys.NUMPAD3 );
            case java.awt.event.KeyEvent.VK_NUMPAD4: return ( Keys.NUMPAD4 );
            case java.awt.event.KeyEvent.VK_NUMPAD5: return ( Keys.NUMPAD5 );
            case java.awt.event.KeyEvent.VK_NUMPAD6: return ( Keys.NUMPAD6 );
            case java.awt.event.KeyEvent.VK_NUMPAD7: return ( Keys.NUMPAD7 );
            case java.awt.event.KeyEvent.VK_NUMPAD8: return ( Keys.NUMPAD8 );
            case java.awt.event.KeyEvent.VK_NUMPAD9: return ( Keys.NUMPAD9 );
        }
        
        return ( null );
    }
    
    public static final Key convertKey( int awtKey, int keyLocation, char keyChar )
    {
        Key key;
        switch ( awtKey )
        {
            case java.awt.event.KeyEvent.VK_SHIFT:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_SHIFT;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_SHIFT;
                break;
            case java.awt.event.KeyEvent.VK_CONTROL:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_CONTROL;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_CONTROL;
                break;
            case java.awt.event.KeyEvent.VK_META:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_LEFT )
                    key = Keys.LEFT_META;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_RIGHT )*/
                    key = Keys.RIGHT_META;
                break;
            case java.awt.event.KeyEvent.VK_ENTER:
                if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_NUMPAD )
                    key = Keys.NUMPAD_ENTER;
                else /*if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_STANDARD )*/
                    key = Keys.ENTER;
                break;
            case java.awt.event.KeyEvent.VK_DECIMAL:
                //if ( keyLocation == java.awt.event.KeyEvent.KEY_LOCATION_NUMPAD )
                    key = Keys.NUMPAD_DECIMAL;
                break;
            default:
                key = convertKey( awtKey );
        }
        
        if ( key == null )
            key = KeyboardLocalizer.getMapping().getLocalizedKey( keyChar );
        
        if ( ( key == Keys.DELETE ) && ( keyChar != '\0' ) )
            key = Keys.NUMPAD_DECIMAL;
        
        return ( key );
    }
    
    private int firstUnusedEntry = 0;
    private boolean deferredEventsProcessed = true;
    private final BitSet lastPressedKeys = new BitSet();
    private final BitSet pressedKeys = new BitSet();
    private final BitSet changedKeyStates = new BitSet();
    private final KeyEvent[] eventQueue = new KeyEvent[128];
    private final Runnable deferredEventProcessor = new Runnable()
    {
        public void run()
        {
            synchronized ( changedKeyStates )
            {
                assert ( firstUnusedEntry > 0 );
                
                // process all events in the queue
                for ( int i = 0; i < firstUnusedEntry; i++ )
                {
                    KeyEvent keyEvent = eventQueue[i];
                    eventQueue[i] = null;
                    
                    if ( keyEvent.getID() != KeyEvent.KEY_TYPED )
                    {
                        final Key key = convertKey( keyEvent.getKeyCode(), keyEvent.getKeyLocation(), keyEvent.getKeyChar() );
                        if ( key == null )
                        {
                            dumpKeyConversionFailed( keyEvent );
                            continue;
                        }
                        
                        pressedKeys.set( key.getKeyCode() - 1, keyEvent.getID() == KeyEvent.KEY_PRESSED );
                    }
                    else
                    {
                        final int modifierMask = getModifierMask();
                        
                        char keyChar = keyEvent.getKeyChar();
                        if ( keyEvent.getKeyChar() == 65452 )
                        {
                            keyChar = KeyboardLocalizer.getMapping().getModifiedChar( Keys.NUMPAD_DECIMAL, '\0', 0 );
                        }
                        
                        KeyTypedEvent e = prepareKeyTypedEvent( keyChar, modifierMask, lastUpdateTime, 0L );
                        
                        if ( e != null )
                        {
                            getEventQueue().enqueue( e );
                        }
                    }
                }
                
                // first check for newly pressed keys and adjust the changed bitset
                for ( int keyIndex = pressedKeys.nextSetBit( 0 ); keyIndex >= 0; keyIndex = pressedKeys.nextSetBit( keyIndex + 1 ) )
                {
                    if ( !lastPressedKeys.get( keyIndex ) )
                    {
                        changedKeyStates.set( keyIndex );
                        lastPressedKeys.set( keyIndex );
                    }
                }
                
                // then check, if there where keys released recently and again adjust the changed bitset
                for ( int keyIndex = lastPressedKeys.nextSetBit( 0 ); keyIndex >= 0; keyIndex = lastPressedKeys.nextSetBit( keyIndex + 1 ) )
                {
                    if ( !pressedKeys.get( keyIndex ) )
                    {
                        changedKeyStates.set( keyIndex );
                        lastPressedKeys.clear( keyIndex );
                    }
                }
                firstUnusedEntry = 0;
                deferredEventsProcessed = true;
            }
        }
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasKeyStateChanged( Key key, boolean keyState )
    {
        /*
        final int keyIndex = key.getKeyCode() - 1;
        return ( keyState != tmpKeyStates[ keyIndex ] );
        */
        
        return ( true );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    /*
    private final void notifyStatesManagersFromQueue( InputSystem is, EventQueue eventQueue, long nanoTime )
    {
        synchronized ( EventQueue.LOCK )
        {
            if ( eventQueue.getNumEvents() == 0 )
                return;
            
            for ( int i = 0; i < eventQueue.getNumEvents(); i++ )
            {
                final InputEvent event = eventQueue.getEvent( i );
                
                System.out.println( event );
                if ( event.getType() == InputEvent.Type.KEYBOARD_EVENT )
                {
                    final KeyboardEvent kbEvent = (KeyboardEvent)event;
                    
                    switch( kbEvent.getSubType() )
                    {
                        case PRESSED:
                            is.notifyInputStatesManagers( this, kbEvent.getComponent(), 1, +1, nanoTime );
                            break;
                        case RELEASED:
                            is.notifyInputStatesManagers( this, kbEvent.getComponent(), 0, -1, nanoTime );
                            break;
                    }
                }
            }
        }
    }
    */
    
    private long lastUpdateTime = 0L;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        try
        {
            //notifyStatesManagersFromQueue( is, eventQueue, nanoTime );
            
            getEventQueue().dequeueAndFire( is, InputEvent.Type.KEYBOARD_EVENT );
            
            synchronized ( changedKeyStates )
            {
                final int n = changedKeyStates.length();
                for ( int i = changedKeyStates.nextSetBit( 0 ); i >= 0 && i < n; i = changedKeyStates.nextSetBit( i + 1 ) )
                {
                    final Key key = KeyID.values()[ i ].getKey();
                    
                    if ( pressedKeys.get( i ) )
                    {
                        final int modifierMask = applyModifier( key, true );
                        
                        KeyPressedEvent e = prepareKeyPressedEvent( key, modifierMask, nanoTime, 0L );
                        
                        is.notifyInputStatesManagers( this, key, 1, +1, nanoTime );
                        
                        if ( e != null )
                        {
                            fireOnKeyPressed( e, true );
                        }
                    }
                    else
                    {
                        final int modifierMask = applyModifier( key, false );
                        
                        KeyReleasedEvent e = prepareKeyReleasedEvent( key, modifierMask, nanoTime, 0L );
                        
                        is.notifyInputStatesManagers( this, key, 0, -1, nanoTime );
                        
                        if ( e != null )
                        {
                            fireOnKeyReleased( e, true );
                        }
                    }
                    
                    changedKeyStates.clear( i );
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
        
        lastUpdateTime = nanoTime;
    }
    
    private static final void dumpKeyConversionFailed( java.awt.event.KeyEvent e )
    {
        String message = "Key-conversion failed for AWT key " + e.getKeyCode() + ". Please check localization (" + KeyboardLocalizer.getCurrentMappingName() + ")";
        
        //System.err.println( message );
        Log.printlnEx( InputSystem.LOG_CHANNEL, message );
    }
    
    private void processKeyEvent( java.awt.event.KeyEvent keyEvent )
    {
        if ( !isEnabled() || !getSourceWindow().receivesInputEvents() )
            return;
        
        if ( firstUnusedEntry < eventQueue.length - 1 )
            eventQueue[firstUnusedEntry++] = keyEvent;
        
        // defer the event processing to a later point in time (the end of the AWT Evene-Queue), so events with the same timestamp
        // are processed in a block to skip KEY_RELEASED events which are caused by the key auto-repeat in linux.
        if ( deferredEventsProcessed )
        {
            deferredEventsProcessed = false;
            SwingUtilities.invokeLater( deferredEventProcessor );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        try
        {
            //java.awt.Toolkit.getDefaultToolkit().removeAWTEventListener( eventListener );
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
    }
    
    protected AWTKeyboard( KeyboardFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Keyboard" );
        
        try
        {
            /*
            final java.awt.Component component = (java.awt.Component)sourceWindow.getDrawable();
            component.addKeyListener( new java.awt.event.KeyListener()
            {
                public void keyPressed( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
                
                public void keyReleased( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
                
                public void keyTyped( java.awt.event.KeyEvent e )
                {
                    processKeyEvent( e );
                }
            } );
            */
            
            Toolkit.getDefaultToolkit().addAWTEventListener( new AWTEventListener()
            {
                public void eventDispatched( AWTEvent event )
                {
                    if ( event instanceof KeyEvent ) processKeyEvent( (KeyEvent) event);
                }
            }, AWTEvent.KEY_EVENT_MASK );
        }
        catch ( Throwable e )
        {
            throw new InputSystemException( e );
        }
    }
}
