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
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.ControllerFactory;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.InputState;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * LWJGL implementation of a Controller.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLController extends Controller
{
    private final org.lwjgl.input.Controller implController;
    
    private static int[] indexMap = null;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceRegistered( InputSystem inputSystem ) throws InputSystemException
    {
        if ( indexMap == null )
        {
            indexMap = new int[ org.lwjgl.input.Controllers.getControllerCount() ];
            java.util.Arrays.fill( indexMap, -1 );
        }
        
        indexMap[ this.getIndex() ] = inputSystem.getControllersCount() - 1;
        
        consumePendingEvents( inputSystem, null, -1 );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeviceUnregistered( InputSystem inputSystem ) throws InputSystemException
    {
        indexMap[ this.getIndex() ] = -1;
    }
    
    protected final org.lwjgl.input.Controller getController()
    {
        return ( implController );
    }
    
    protected final int getIndex()
    {
        return ( implController.getIndex() );
    }
    
    protected final void collectOrFireEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( !getSourceWindow().receivesInputEvents() )
            return;
        
        final boolean isQueued = ( eventQueue != null );
        
        try
        {
            //controller.poll();
            
            for ( int i = 0; i < getAxesCount(); i++ )
            {
                final ControllerAxis axis = getAxis( i );
                final float oldValue = axis.getFloatValue();
                final float newValue = implController.getAxisValue( i );
                
                if ( newValue != oldValue )
                {
                    final int oldIntValue = axis.getIntValue();
                    axis.setValue( newValue );
                    
                    final ControllerAxisChangedEvent axisEv = prepareControllerAxisChanged( axis, newValue - oldValue, nanoTime );
                    
                    is.notifyInputStatesManagers( this, axis, axis.getIntValue(), axis.getIntValue() - oldIntValue, nanoTime );
                    
                    if ( axisEv == null )
                        continue;
                    
                    if ( isQueued )
                        eventQueue.enqueue( axisEv );
                    else
                        fireOnControllerAxisChanged( axisEv, true );
                }
            }
            
            for ( int i = 0; i < getButtonsCount(); i++ )
            {
                final ControllerButton button = getButton( i );
                final boolean oldState = button.getBooleanState();
                final boolean newState = implController.isButtonPressed( i );
                
                if ( newState != oldState )
                {
                    if ( newState )
                    {
                        button.setState( InputState.POSITIVE );
                        
                        final ControllerButtonPressedEvent pressedEv = prepareControllerButtonPressed( button, nanoTime );
                        
                        is.notifyInputStatesManagers( this, button, 1, +1, nanoTime );
                        
                        if ( pressedEv == null )
                            continue;
                        
                        if ( isQueued )
                            eventQueue.enqueue( pressedEv );
                        else
                            fireOnControllerButtonPressed( pressedEv, true );
                    }
                    else
                    {
                        button.setState( InputState.NEGATIVE );
                        
                        final ControllerButtonReleasedEvent releasedEv = prepareControllerButtonReleased( button, nanoTime );
                        
                        is.notifyInputStatesManagers( this, button, 0, -1, nanoTime );
                        
                        if ( releasedEv == null )
                            continue;
                        
                        if ( isQueued )
                            eventQueue.enqueue( releasedEv );
                        else
                            fireOnControllerButtonReleased( releasedEv, true );
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
        //collectOrFireEvents( is, null, nanoTime );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        if ( eventQueue == null )
            throw new InputSystemException( "EventQueue must not be null here!" );
        
        collectOrFireEvents( is, eventQueue, nanoTime );
    }
    
    private static long lastUpdateTime = -1L;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        //collectOrFireEvents( is, null, nanoTime );
        
        if ( nanoTime > lastUpdateTime )
        {
            try
            {
                org.lwjgl.input.Controllers.poll();
                
                while ( org.lwjgl.input.Controllers.next() )
                {
                    final int controllerIndex = org.lwjgl.input.Controllers.getEventSource().getIndex();
                    final int realIndex = indexMap[ controllerIndex ];
                    
                    if ( realIndex != -1 )
                    {
                        LWJGLController ctrl = (LWJGLController)is.getController( realIndex );
                        
                        ctrl.collectOrFireEvents( is, null, nanoTime );
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
        
        getEventQueue().dequeueAndFire( is, InputEvent.Type.CONTROLLER_EVENT );
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
            if ( controller.isCreated() )
            {
                controller.destroy();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t );
            
            if ( t instanceof Error )
                throw (Error)t );
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t );
            
            throw new InputSystemException( t ) );
        }
        */
    }
    
    @Override
    protected final LWJGLControllerAxis[] createAxesArray( Object implObj )
    {
        final org.lwjgl.input.Controller controller = (org.lwjgl.input.Controller)implObj;
        
        LWJGLControllerAxis[] axes = new LWJGLControllerAxis[ controller.getAxisCount() ];
        
        for ( int i = 0; i < controller.getAxisCount(); i++ )
        {
            axes[ i ] = new LWJGLControllerAxis( this, controller, i );
        }
        
        return ( axes );
    }
    
    @Override
    protected final ControllerButton[] createButtonsArray( Object implObj )
    {
        final org.lwjgl.input.Controller controller = (org.lwjgl.input.Controller)implObj;
        
        ControllerButton[] buttons = new ControllerButton[ controller.getButtonCount() ];
        
        for ( int i = 0; i < controller.getButtonCount(); i++ )
        {
            buttons[ i ] = new ControllerButton( this, i, controller.getButtonName( i ) );
        }
        
        return ( buttons );
    }
    
    protected LWJGLController( ControllerFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue, org.lwjgl.input.Controller implController ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, implController.getName(), implController );
        
        this.implController = implController;
    }
}
