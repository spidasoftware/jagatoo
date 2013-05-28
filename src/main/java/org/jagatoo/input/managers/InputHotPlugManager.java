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
package org.jagatoo.input.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.ControllerFactory;
import org.jagatoo.input.devices.InputDeviceFactory;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.KeyboardFactory;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.MouseFactory;
import org.jagatoo.logging.Log;
import org.jagatoo.util.arrays.ArrayUtils;

/**
 * The {@link InputHotPlugManager} polls for hot-plugged {@link InputDevice}s
 * and notifies listeners when devices are plugged in or out.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputHotPlugManager implements Runnable
{
    private KeyboardFactory[] keyboardFactories = null;
    private MouseFactory[] mouseFactories = null;
    private ControllerFactory[] controllerFactories = null;
    
    private final HashMap< KeyboardFactory, Keyboard[] > cachedKeyboardsMap = new HashMap< KeyboardFactory, Keyboard[] >();
    private final HashMap< MouseFactory, Mouse[] > cachedMousesMap = new HashMap< MouseFactory, Mouse[] >();
    private final HashMap< ControllerFactory, Controller[] > cachedControllersMap = new HashMap< ControllerFactory, Controller[] >();
    
    private final ArrayList< InputHotPlugListener > listeners = new ArrayList< InputHotPlugListener >();
    
    private Thread thread = null;
    private boolean running = false;
    
    private void refreshCachedKeyboards( KeyboardFactory factory ) throws InputSystemException
    {
        Keyboard[] keyboards = factory.getKeyboards( true );
        
        cachedKeyboardsMap.put( factory, keyboards );
    }
    
    private void refreshCachedMouses( MouseFactory factory ) throws InputSystemException
    {
        Mouse[] mouses = factory.getMouses( true );
        
        cachedMousesMap.put( factory, mouses );
    }
    
    private void refreshCachedControllers( ControllerFactory factory ) throws InputSystemException
    {
        Controller[] controllers = factory.getControllers( true );
        
        cachedControllersMap.put( factory, controllers );
    }
    
    /**
     * Registers a {@link KeyboardFactory} to be polled for hot-plugged devices.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void registerKeyboardFactory( KeyboardFactory factory ) throws InputSystemException
    {
        if ( keyboardFactories == null )
        {
            keyboardFactories = new KeyboardFactory[] { factory };
        }
        else if ( ArrayUtils.contains( keyboardFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is already registered." );
        }
        else
        {
            KeyboardFactory[] newArray = new KeyboardFactory[ keyboardFactories.length + 1 ];
            System.arraycopy( keyboardFactories, 0, newArray, 0, keyboardFactories.length );
            newArray[ newArray.length - 1 ] = factory;
            keyboardFactories = newArray;
        }
        
        //refreshCachedKeyboards( factory );
    }
    
    /**
     * Deregisters a {@link KeyboardFactory}.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void deregisterKeyboardFactory( KeyboardFactory factory ) throws InputSystemException
    {
        if ( ( keyboardFactories == null ) || !ArrayUtils.contains( keyboardFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is not registered." );
        }
        
        final int index = ArrayUtils.indexOf( keyboardFactories, factory, true );
        
        KeyboardFactory[] newArray = new KeyboardFactory[ keyboardFactories.length - 1 ];
        System.arraycopy( keyboardFactories, 0, newArray, 0, index );
        System.arraycopy( keyboardFactories, index + 1, newArray, index, keyboardFactories.length - index - 1 );
        keyboardFactories = newArray;
    }
    
    /**
     * Registers a {@link MouseFactory} to be polled for hot-plugged devices.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void registerMouseFactory( MouseFactory factory ) throws InputSystemException
    {
        if ( mouseFactories == null )
        {
            mouseFactories = new MouseFactory[] { factory };
        }
        else if ( ArrayUtils.contains( mouseFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is already registered." );
        }
        else
        {
            MouseFactory[] newArray = new MouseFactory[ mouseFactories.length + 1 ];
            System.arraycopy( mouseFactories, 0, newArray, 0, mouseFactories.length );
            newArray[ newArray.length - 1 ] = factory;
            mouseFactories = newArray;
        }
        
        //refreshCachedMouses( factory );
    }
    
    /**
     * Deregisters a {@link MouseFactory}.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void deregisterMouseFactory( MouseFactory factory ) throws InputSystemException
    {
        if ( ( mouseFactories == null ) || !ArrayUtils.contains( mouseFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is not registered." );
        }
        
        final int index = ArrayUtils.indexOf( mouseFactories, factory, true );
        
        MouseFactory[] newArray = new MouseFactory[ mouseFactories.length - 1 ];
        System.arraycopy( mouseFactories, 0, newArray, 0, index );
        System.arraycopy( mouseFactories, index + 1, newArray, index, mouseFactories.length - index - 1 );
        mouseFactories = newArray;
    }
    
    /**
     * Registers a {@link ControllerFactory} to be polled for hot-plugged devices.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void registerControllerFactory( ControllerFactory factory ) throws InputSystemException
    {
        if ( controllerFactories == null )
        {
            controllerFactories = new ControllerFactory[] { factory };
        }
        else if ( ArrayUtils.contains( controllerFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is already registered." );
        }
        else
        {
            ControllerFactory[] newArray = new ControllerFactory[ controllerFactories.length + 1 ];
            System.arraycopy( controllerFactories, 0, newArray, 0, controllerFactories.length );
            newArray[ newArray.length - 1 ] = factory;
            controllerFactories = newArray;
        }
        
        //refreshCachedControllers( factory );
    }
    
    /**
     * Deregisters a {@link ControllerFactory}.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void deregisterControllerFactory( ControllerFactory factory ) throws InputSystemException
    {
        if ( ( controllerFactories == null ) || !ArrayUtils.contains( controllerFactories, factory, true ) )
        {
            throw new InputSystemException( "This factory is not registered." );
        }
        
        final int index = ArrayUtils.indexOf( controllerFactories, factory, true );
        
        ControllerFactory[] newArray = new ControllerFactory[ controllerFactories.length - 1 ];
        System.arraycopy( controllerFactories, 0, newArray, 0, index );
        System.arraycopy( controllerFactories, index + 1, newArray, index, controllerFactories.length - index - 1 );
        controllerFactories = newArray;
    }
    
    /**
     * Registers a {@link InputDeviceFactory} to be polled for hot-plugged devices.<br>
     * This simply calls {@link #registerKeyboardFactory(KeyboardFactory)},
     * {@link #registerMouseFactory(MouseFactory)} and {@link #registerControllerFactory(ControllerFactory)}.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void registerDeviceFactory( InputDeviceFactory factory ) throws InputSystemException
    {
        registerKeyboardFactory( factory );
        registerMouseFactory( factory );
        registerControllerFactory( factory );
    }
    
    /**
     * Deregisters a {@link InputDeviceFactory}.<br>
     * This simply calls {@link #deregisterKeyboardFactory(KeyboardFactory)},
     * {@link #deregisterMouseFactory(MouseFactory)} and {@link #deregisterControllerFactory(ControllerFactory)}.
     * 
     * @param factory
     * 
     * @throws InputSystemException
     */
    public void deregisterDeviceFactory( InputDeviceFactory factory ) throws InputSystemException
    {
        deregisterKeyboardFactory( factory );
        deregisterMouseFactory( factory );
        deregisterControllerFactory( factory );
    }
    
    /**
     * Adds a new {@link InputHotPlugListener} to the list of notifed listeners.
     * 
     * @param l
     */
    public void addHotPlugListener( InputHotPlugListener l )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            if ( listeners.get( i ) == l )
            {
                return;
            }
        }
        
        listeners.add( l );
    }
    
    /**
     * Removes a {@link InputHotPlugListener} from the list of notifed listeners.
     * 
     * @param l
     */
    public void removeHotPlugListener( InputHotPlugListener l )
    {
        listeners.remove( l );
    }
    
    protected void notifyListeners( InputDevice[] devices0, InputDevice[] devices1 )
    {
        for ( int i = 0; i < devices1.length; i++ )
        {
            final InputDevice device1 = devices1[ i ];
            
            if ( !ArrayUtils.contains( devices0, device1, true ) )
            {
                for ( int j = 0; j < listeners.size(); j++ )
                {
                    listeners.get( j ).onInputDevicePluggedIn( device1 );
                }
            }
        }
        
        for ( int i = 0; i < devices0.length; i++ )
        {
            final InputDevice device0 = devices0[ i ];
            
            if ( !ArrayUtils.contains( devices1, device0, true ) )
            {
                for ( int j = 0; j < listeners.size(); j++ )
                {
                    listeners.get( j ).onInputDevicePluggedOut( device0 );
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void run()
    {
        running = true;
        
        while ( thread != null )
        {
            try
            {
                if ( keyboardFactories != null )
                {
                    for ( int i = 0; i < keyboardFactories.length; i++ )
                    {
                        final KeyboardFactory factory = keyboardFactories[ i ];
                        
                        Keyboard[] cachedKeyboards0 = cachedKeyboardsMap.get( factory );
                        refreshCachedKeyboards( factory );
                        if ( cachedKeyboards0 != null )
                        {
                            Keyboard[] cachedKeyboards1 = cachedKeyboardsMap.get( factory );
                            
                            if ( cachedKeyboards0.length != cachedKeyboards1.length )
                            {
                                notifyListeners( cachedKeyboards0, cachedKeyboards1 );
                            }
                        }
                    }
                }
                
                if ( mouseFactories != null )
                {
                    for ( int i = 0; i < mouseFactories.length; i++ )
                    {
                        final MouseFactory factory = mouseFactories[ i ];
                        
                        Mouse[] cachedMouses0 = cachedMousesMap.get( factory );
                        refreshCachedMouses( factory );
                        if ( cachedMouses0 != null )
                        {
                            Mouse[] cachedMouses1 = cachedMousesMap.get( factory );
                            
                            if ( cachedMouses0.length != cachedMouses1.length )
                            {
                                notifyListeners( cachedMouses0, cachedMouses1 );
                            }
                        }
                    }
                }
                
                if ( controllerFactories != null )
                {
                    for ( int i = 0; i < controllerFactories.length; i++ )
                    {
                        final ControllerFactory factory = controllerFactories[ i ];
                        
                        Controller[] cachedControllers0 = cachedControllersMap.get( factory );
                        refreshCachedControllers( factory );
                        if ( cachedControllers0 != null )
                        {
                            Controller[] cachedControllers1 = cachedControllersMap.get( factory );
                            
                            if ( cachedControllers0.length != cachedControllers1.length )
                            {
                                notifyListeners( cachedControllers0, cachedControllers1 );
                            }
                        }
                    }
                }
            }
            catch ( InputSystemException e )
            {
                Log.print( InputSystem.LOG_CHANNEL, e );
                e.printStackTrace();
            }
            
            
            try
            {
                Thread.sleep( 1000L );
            }
            catch ( InterruptedException e )
            {
                Log.print( InputSystem.LOG_CHANNEL, e );
                e.printStackTrace();
            }
        }
        
        running = false;
    }
    
    public final boolean isRunning()
    {
        return ( ( thread != null ) || running );
    }
    
    /**
     * Starts the {@link InputHotPlugManager} in a new Thread.
     */
    public void start()
    {
        this.thread = new Thread( this );
        
        thread.start();
    }
    
    /**
     * Stops the {@link InputHotPlugManager}.
     * 
     * @param wait if <code>true</code>, the method will not terminate
     * until the manager's Thread is stopped.
     */
    public void stop( boolean wait )
    {
        this.thread = null;
        
        if ( wait )
        {
            while ( running )
            {
                try
                {
                    Thread.sleep( 10L );
                }
                catch ( InterruptedException e )
                {
                    Log.print( InputSystem.LOG_CHANNEL, e );
                    e.printStackTrace();
                }
            }
        }
    }
    
    public InputHotPlugManager()
    {
    }
}
