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
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.ControllerFactory;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.InputState;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * JInput implementation of a Controller.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class JInputController extends Controller
{
    private final net.java.games.input.Controller controller;
    
    private final HashMap< net.java.games.input.Component, DeviceComponent > deviceMap = new HashMap< net.java.games.input.Component, DeviceComponent >();
    
    protected final net.java.games.input.Controller getController()
    {
        return ( controller );
    }
    
    final net.java.games.input.Event event = new net.java.games.input.Event();
    
    protected final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime, boolean acceptsEvents ) throws InputSystemException
    {
        if ( !getSourceWindow().receivesInputEvents() )
            return;
        
        final boolean isQueued = ( eventQueue != null );
        
        try
        {
            controller.poll();
            
            while( controller.getEventQueue().getNextEvent( event ) )
            {
                if ( !acceptsEvents )
                    continue;
                
                final DeviceComponent devComp = deviceMap.get( event.getComponent() );
                if ( devComp == null )
                    continue;
                
                final float value = event.getValue();
                
                if ( devComp instanceof JInputControllerAxis )
                {
                    final JInputControllerAxis axis = (JInputControllerAxis)devComp;
                    
                    final float oldValue = axis.getFloatValue();
                    
                    if ( value != oldValue )
                    {
                        final int oldIntValue = axis.getIntValue();
                        axis.setValue( value );
                        
                        final ControllerAxisChangedEvent axisEv = prepareControllerAxisChanged( axis, value - oldValue, nanoTime );
                        
                        is.notifyInputStatesManagers( this, axis, axis.getIntValue(), axis.getIntValue() - oldIntValue, nanoTime );
                        
                        if ( isQueued )
                            eventQueue.enqueue( axisEv );
                        else
                            fireOnControllerAxisChanged( axisEv, true );
                    }
                }
                else if ( devComp instanceof JInputControllerButton )
                {
                    final JInputControllerButton button = (JInputControllerButton)devComp;
                    
                    final boolean oldState = button.getBooleanState();
                    final boolean newState = ( value != 0f );
                    
                    if ( newState != oldState )
                    {
                        if ( newState )
                        {
                            button.setState( InputState.POSITIVE );
                            
                            is.notifyInputStatesManagers( this, button, 1, +1, nanoTime );
                            
                            final ControllerButtonPressedEvent pressedEv = prepareControllerButtonPressed( button, nanoTime );
                            
                            if ( isQueued )
                                eventQueue.enqueue( pressedEv );
                            else
                                fireOnControllerButtonPressed( pressedEv, true );
                        }
                        else
                        {
                            button.setState( InputState.NEGATIVE );
                            
                            is.notifyInputStatesManagers( this, button, 0, -1, nanoTime );
                            
                            final ControllerButtonReleasedEvent releasedEv = prepareControllerButtonReleased( button, nanoTime );
                            
                            if ( isQueued )
                                eventQueue.enqueue( releasedEv );
                            else
                                fireOnControllerButtonReleased( releasedEv, true );
                        }
                    }
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
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        collectOrFireEvents( is, null, nanoTime, true );
        
        getEventQueue().dequeueAndFire( is, InputEvent.Type.CONTROLLER_EVENT );
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
    }
    
    @Override
    protected final JInputControllerAxis[] createAxesArray( Object implObj )
    {
        final net.java.games.input.Controller controller = (net.java.games.input.Controller)implObj;
        
        JInputControllerAxis[] axes0 = new JInputControllerAxis[ controller.getComponents().length ];
        int numAxes = 0;
        
        for ( int i = 0; i < controller.getComponents().length; i++ )
        {
            net.java.games.input.Component component = controller.getComponents()[ i ];
            if ( component.getIdentifier() instanceof net.java.games.input.Component.Identifier.Axis )
            {
                int index = numAxes++;
                axes0[ index ] = new JInputControllerAxis( this, component, index );
            }
        }
        
        JInputControllerAxis[] axes1 = new JInputControllerAxis[ numAxes ];
        System.arraycopy( axes0, 0, axes1, 0, numAxes );
        
        return ( axes1 );
    }
    
    @Override
    protected final JInputControllerButton[] createButtonsArray( Object implObj )
    {
        final net.java.games.input.Controller controller = (net.java.games.input.Controller)implObj;
        
        JInputControllerButton[] buttons0 = new JInputControllerButton[ controller.getComponents().length ];
        int numButtons = 0;
        
        for ( int i = 0; i < controller.getComponents().length; i++ )
        {
            net.java.games.input.Component component = controller.getComponents()[ i ];
            if ( component.getIdentifier() instanceof net.java.games.input.Component.Identifier.Button )
            {
                int index = numButtons++;
                buttons0[ index ] = new JInputControllerButton( this, component, index );
            }
        }
        
        JInputControllerButton[] buttons1 = new JInputControllerButton[ numButtons ];
        System.arraycopy( buttons0, 0, buttons1, 0, numButtons );
        
        return ( buttons1 );
    }
    
    protected JInputController( ControllerFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue, net.java.games.input.Controller controller ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, controller.getName(), controller );
        
        this.controller = controller;
        
        for ( int i = 0; i < getAxesCount(); i++ )
        {
            final JInputControllerAxis axis = (JInputControllerAxis)getAxis( i );
            deviceMap.put( axis.getAxis(), axis );
        }
        
        for ( int i = 0; i < getButtonsCount(); i++ )
        {
            final JInputControllerButton button = (JInputControllerButton)getButton( i );
            deviceMap.put( button.getButton(), button );
        }
    }
}
