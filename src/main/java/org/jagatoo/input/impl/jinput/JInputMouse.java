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
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.MouseFactory;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * JInput implementation of the Mouse class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class JInputMouse extends Mouse
{
    private static final HashMap< net.java.games.input.Component, DeviceComponent > compMap = new HashMap< net.java.games.input.Component, DeviceComponent >();
    
    public static final MouseButton convertButton( net.java.games.input.Component jinputButton )
    {
        DeviceComponent comp = compMap.get( jinputButton );
        
        if ( ( comp == null ) || ( !( comp instanceof MouseButton ) ) )
            return ( null );
        
        return ( (MouseButton)comp );
    }
    
    private final net.java.games.input.Mouse mouse;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition( int x, int y ) throws InputSystemException
    {
        super.setPosition( x, y );
        
        try
        {
            if ( isAbsolute() )
            {
                //mouse.setPosition( x, y );
            }
            else
            {
                storePosition( x, y );
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
    public void centerMouse() throws InputSystemException
    {
        int centerX = 0;
        int centerY = 0;
        try
        {
            centerX = getSourceWindow().getWidth() / 2;
            centerY = getSourceWindow().getHeight() / 2;
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
        
        setPosition( centerX, centerY );
    }
    
    private net.java.games.input.Event event = new net.java.games.input.Event();
    
    private final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime, boolean acceptsEvents ) throws InputSystemException
    {
        final boolean isQueued = ( eventQueue != null );
        
        try
        {
            getEventQueue().dequeueAndFire( is, InputEvent.Type.MOUSE_EVENT );
            
            mouse.poll();
            
            int dx = 0;
            int dy = 0;
            
            while ( mouse.getEventQueue().getNextEvent( event ) )
            {
                if ( !acceptsEvents )
                    continue;
                
                final DeviceComponent comp = compMap.get( event.getComponent() );
                
                switch ( comp.getType() )
                {
                    case MOUSE_AXIS:
                        final MouseAxis axis = (MouseAxis)comp;
                        
                        switch ( axis.getID() )
                        {
                            case 'X':
                                final int dx_ = (int)event.getComponent().getPollData();
                                dx += dx_;
                                if ( isAbsolute() )
                                    storePosition( getCurrentX() + dx_, getCurrentY() );
                                break;
                                
                            case 'Y':
                                final int dy_ = (int)event.getComponent().getPollData();
                                dy += dy_;
                                if ( isAbsolute() )
                                    storePosition( getCurrentX(), getCurrentY() + dy_ );
                                break;
                        }
                        break;
                        
                    case MOUSE_BUTTON:
                        MouseButton button = (MouseButton)comp;
                        
                        if ( !isButtonPressed( button ) )
                        {
                            final MouseButtonPressedEvent pressedEv = prepareMouseButtonPressedEvent( button, nanoTime );
                            
                            is.notifyInputStatesManagers( this, button, 1, +1, nanoTime );
                            
                            if ( pressedEv == null )
                                continue;
                            
                            if ( isQueued )
                                eventQueue.enqueue( pressedEv );
                            else
                                fireOnMouseButtonPressed( pressedEv, true );
                        }
                        else
                        {
                            final MouseButtonReleasedEvent releasedEv = prepareMouseButtonReleasedEvent( button, nanoTime );
                            
                            is.notifyInputStatesManagers( this, button, 0, -1, nanoTime );
                            
                            if ( releasedEv == null )
                                continue;
                            
                            if ( isQueued )
                                eventQueue.enqueue( releasedEv );
                            else
                                fireOnMouseButtonReleased( releasedEv, true );
                        }
                        break;
                        
                    case MOUSE_WHEEL:
                        final int wheelDelta = (int)event.getValue();
                        
                        final MouseWheelEvent wheelEv = prepareMouseWheelMovedEvent( wheelDelta, false, nanoTime );
                        
                        is.notifyInputStatesManagers( this, this.getWheel(), this.getWheel().getIntValue(), wheelDelta, nanoTime );
                        
                        if ( wheelEv == null )
                            continue;
                        
                        if ( isQueued )
                            eventQueue.enqueue( wheelEv );
                        else
                            fireOnMouseWheelMoved( wheelEv, true );
                        break;
                }
            }
            
            if ( ( dx != 0 ) || ( dy != 0 ) )
            {
                final MouseMovedEvent movedEv = prepareMouseMovedEvent( getCurrentX(), getCurrentY(), dx, dy, nanoTime );
                
                if ( dx != 0 )
                    is.notifyInputStatesManagers( this, this.getXAxis(), getCurrentX(), dx, nanoTime );
                
                if ( dy != 0 )
                    is.notifyInputStatesManagers( this, this.getYAxis(), getCurrentY(), dy, nanoTime );
                
                if ( movedEv != null )
                {
                    if ( isQueued )
                        eventQueue.enqueue( movedEv );
                    else
                        fireOnMouseMoved( movedEv, true );
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
    public void updateMouse( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        collectOrFireEvents( is, null, nanoTime, true );
        
        getEventQueue().dequeueAndFire( is, InputEvent.Type.MOUSE_EVENT );
        
        handleClickedEvents( nanoTime, is.getMouseButtonClickThreshold() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void setAbsoluteImpl( boolean absolute ) throws InputSystemException
    {
        try
        {
            /*
            org.lwjgl.input.Mouse.setGrabbed( !absolute );
            
            if ( absolute )
            {
                org.lwjgl.input.Mouse.setCursorPosition( lastAbsoluteX, getSourceWindow().getHeight() - lastAbsoluteY );
            }
            */
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
            if ( org.lwjgl.input.Mouse.isCreated() )
            {
                org.lwjgl.input.Mouse.destroy();
            }
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t ) );
        }
        */
    }
    
    private static int init_getNumButtons( net.java.games.input.Mouse mouse ) throws InputSystemException
    {
        net.java.games.input.Component[] comps = mouse.getComponents();
        
        int extIndex = 3;
        
        int oldCount = compMap.size();
        
        for ( int i = 0; i < comps.length; i++ )
        {
            if ( comps[ i ].getIdentifier().getClass().getSimpleName().equals( "Button" ) )
            {
                if ( comps[ i ].getName().equals( "Left" ) )
                    compMap.put( comps[ i ], MouseButtons.LEFT_BUTTON );
                else if ( comps[ i ].getName().equals( "Right" ) )
                    compMap.put( comps[ i ], MouseButtons.RIGHT_BUTTON );
                else if ( comps[ i ].getName().equals( "Middle" ) )
                    compMap.put( comps[ i ], MouseButtons.MIDDLE_BUTTON );
                else
                    compMap.put( comps[ i ], MouseButtons.getByIndex( extIndex++ ) );
            }
        }
        
        return ( compMap.size() - oldCount );
    }
    
    private static boolean init_hasWheel( net.java.games.input.Mouse mouse ) throws InputSystemException
    {
        net.java.games.input.Component[] comps = mouse.getComponents();
        
        boolean hasWheel = false;
        
        for ( int i = 0; i < comps.length; i++ )
        {
            if ( comps[ i ].getName().equals( "z" ) )
            {
                //compMap.put( comps[ i ], MouseWheel );
                hasWheel = true;
            }
        }
        
        return ( hasWheel );
    }
    
    protected JInputMouse( MouseFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue, net.java.games.input.Mouse mouse ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, mouse.getName(), init_getNumButtons( mouse ), init_hasWheel( mouse ) );
        
        this.mouse = mouse;
        
        if ( getWheel() != null )
        {
            net.java.games.input.Component[] comps = mouse.getComponents();
            
            for ( int i = 0; i < comps.length; i++ )
            {
                if ( comps[ i ].getName().equals( "x" ) )
                {
                    compMap.put( comps[ i ], getXAxis() );
                }
                else if ( comps[ i ].getName().equals( "y" ) )
                {
                    compMap.put( comps[ i ], getYAxis() );
                }
                else if ( comps[ i ].getName().equals( "z" ) )
                {
                    compMap.put( comps[ i ], getWheel() );
                }
            }
        }
    }
}
