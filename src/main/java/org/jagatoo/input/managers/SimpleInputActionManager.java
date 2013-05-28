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
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.listeners.InputStateListener;

/**
 * The {@link SimpleInputActionManager} can be used to bind
 * input {@link DeviceComponent}s to simple input-actions (like Strings).<br>
 * <br>
 * You need to add it to the {@link InputSystem} or one of its registered
 * {@link InputDevice}s as an {@link InputStateListener} to make it work.<br>
 * <br>
 * You can use it as a singleton. The singleton instance is always automatically
 * added to the InputSystem as a listener.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class SimpleInputActionManager implements InputStateListener
{
    private static SimpleInputActionManager instance = null;
    
    private final HashMap< DeviceComponent, Object > actionMap = new HashMap< DeviceComponent, Object >();
    
    private final ArrayList< SimpleInputActionListener > listeners = new ArrayList< SimpleInputActionListener >();
    
    public static final SimpleInputActionManager getInstance()
    {
        if ( instance == null )
        {
            instance = new SimpleInputActionManager();
        }
        
        return ( instance );
    }
    
    public void addActionListener( SimpleInputActionListener l )
    {
        if ( listeners.contains( l ) )
            return;
        
        listeners.add( l );
    }
    
    public void removeActionListener( SimpleInputActionListener l )
    {
        listeners.remove( l );
    }
    
    public final int getNumBoundActions()
    {
        return ( actionMap.size() );
    }
    
    public void bindAction( DeviceComponent comp, Object action )
    {
        if ( comp == null )
            throw new IllegalArgumentException( "comp must not be null" );
        
        if ( action == null )
            throw new IllegalArgumentException( "action must not be null" );
        
        actionMap.put( comp, action );
        
        if ( this == instance )
        {
            if ( this.getNumBoundActions() == 1 )
            {
                InputSystem.getInstance().addInputStateListener( this );
            }
        }
    }
    
    public void unbindAction( DeviceComponent comp )
    {
        if ( comp == null )
            throw new IllegalArgumentException( "comp must not be null" );
        
        actionMap.remove( comp );
        
        if ( this == instance )
        {
            if ( this.getNumBoundActions() == 0 )
            {
                InputSystem.getInstance().removeInputStateListener( this );
            }
        }
    }
    
    public void unbindAction( Object action )
    {
        if ( action == null )
            throw new IllegalArgumentException( "action must not be null" );
        
        DeviceComponent mappedComp = null;
        
        for ( DeviceComponent comp: actionMap.keySet() )
        {
            Object mappedAction = actionMap.get( comp );
            
            if ( mappedAction != null )
            {
                mappedComp = comp;
                break;
            }
        }
        
        if ( mappedComp != null )
        {
            unbindAction( mappedComp );
        }
    }
    
    public final Object getBoundAction( DeviceComponent comp )
    {
        return ( actionMap.get( comp ) );
    }
    
    protected void notifyListeners( Object action, int delta, int state )
    {
        if ( action == null )
            return;
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onActionInvoked( action, delta, state );
        }
    }
    
    private final boolean checkMouseWheel( DeviceComponent comp, int delta, int state )
    {
        final MouseWheel wheel = (MouseWheel)comp;
        
        Object action = getBoundAction( MouseWheel.GLOBAL_WHEEL );
        
        if ( action != null )
        {
            notifyListeners( action, delta, state );
            
            return ( true );
        }
        
        if ( delta > 0 )
        {
            action = getBoundAction( MouseWheel.GLOBAL_WHEEL.getUp() );
            
            if ( action == null )
                action = getBoundAction( wheel.getUp() );
            
            if ( action != null )
            {
                notifyListeners( action, 1, 1 );
                
                return ( true );
            }
        }
        else
        {
            action = getBoundAction( MouseWheel.GLOBAL_WHEEL.getDown() );
            
            if ( action == null )
                action = getBoundAction( wheel.getDown() );
            
            if ( action != null )
            {
                notifyListeners( action, 1, 1 );
                
                return ( true );
            }
        }
        
        return ( false );
    }
    
    public void onInputStateChanged( InputEvent e, DeviceComponent comp, int delta, int state )
    {
        if ( comp == null )
            return;
        
        Object action = getBoundAction( comp );
        
        if ( ( action == null ) && ( comp != null ) && ( comp.getType() == DeviceComponent.Type.MOUSE_WHEEL ) )
        {
            if ( checkMouseWheel( comp, delta, state ) )
                return;
        }
        
        if ( action != null )
        {
            notifyListeners( action, delta, state );
        }
    }
}
