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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.devices.components.DeviceComponent;

/**
 * This is a generic input-bindings manager.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputBindingsManager< A extends InputAction >
{
    protected static final int NUM_KEY_SETS = InputBindingsSet.values().length;
    
    private final HashMap< DeviceComponent, A > keyBindings;
    protected final DeviceComponent[][] boundKeys;
    
    private final ArrayList< InputBindingListener< A > > listeners = new ArrayList< InputBindingListener< A > >();
    
    public final void addInputBindingListener( InputBindingListener< A > l )
    {
        listeners.add( l );
    }
    
    public final void removeInputBindingListener( InputBindingListener< A > l )
    {
        listeners.remove( l );
    }
    
    protected final void notifyBound( DeviceComponent key, InputBindingsSet set, A action )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onInputComponentBound( key, set, action );
        }
    }
    
    protected final void notifyUnbound( DeviceComponent key, InputBindingsSet set, A action )
    {
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onInputComponentUnbound( key, set, action );
        }
    }
    
    public final int getNumActions()
    {
        return ( boundKeys.length );
    }
    
    public final Set< DeviceComponent > getBoundInputComponents()
    {
        return ( keyBindings.keySet() );
    }
    
    public final int getBoundInputComponents( DeviceComponent[] result )
    {
        if ( result.length < keyBindings.size() )
        {
            throw new ArrayIndexOutOfBoundsException( "result array smaller than " + keyBindings.size() );
        }
        
        int i = 0;
        for ( DeviceComponent comp : keyBindings.keySet() )
        {
            result[i++] = comp;
        }
        
        return ( i );
    }
    
    public final Collection< A > getBoundActions()
    {
        return ( keyBindings.values() );
    }
    
    public final void getBoundActions( DeviceComponent[] components, InputAction[] result )
    {
        for ( int i = 0; i < components.length; i++ )
        {
            A action = getBoundAction( components[ i ] );
            
            result[i] = action;
        }
    }
    
    /**
     * Unbinds a specific key.
     * 
     * @param comp the component to unbind
     * 
     * @return the {@link InputAction}, this key was previously bound to
     */
    public final A unbind( DeviceComponent comp )
    {
        final A prevBound = keyBindings.remove( comp );
        
        if ( prevBound != null )
        {
            final int ordinal = prevBound.ordinal();
            
            if ( boundKeys[ ordinal ] != null )
            {
                for ( int i = 0; i < NUM_KEY_SETS; i++ )
                {
                    if ( boundKeys[ ordinal ][ i ] == comp )
                    {
                        /*
                        for ( int j = i + 1; j < NUM_KEY_SETS; j++ )
                        {
                            boundKeys[ ordinal ][ j - 1 ] = boundKeys[ ordinal ][ j ];
                            boundKeys[ ordinal ][ j ] = 0;
                        }
                        */
                        boundKeys[ ordinal ][ i ] = null;
                        
                        notifyUnbound( comp, InputBindingsSet.values()[ i ], prevBound );
                        
                        return ( prevBound );
                    }
                }
            }
        }
        
        return ( null );
    }
    
    /**
     * Unbinds a key from a specific KeyCommand.
     * 
     * @param action the InputAction to un-map from its key
     * 
     * @return the key, that was previously bound to this command.
     */
    public final DeviceComponent unbind( A action )
    {
        DeviceComponent[] bounds = boundKeys[ action.ordinal() ];
        
        if ( bounds == null )
            return ( null );
        
        DeviceComponent prev = null;
        DeviceComponent[] prevs = new DeviceComponent[ NUM_KEY_SETS ];
        for ( int i = 0; i < NUM_KEY_SETS; i++ )
        {
            if ( ( prev == null ) && ( bounds[ i ] != null ) )
                prev = bounds[ i ];
            
            prevs[ i ] = bounds[ i ];
        }
        
        for ( int i = 0; i < prevs.length; i++ )
        {
            if ( prevs[ i ] != null )
                unbind( prevs[ i ] );
        }
        
        return ( prev );
    }
    
    /**
     * @param comp the key of the requested InputAction
     * @param set the {@link InputBindingsSet} (may be null for an arbitrary set)
     * 
     * @return the InputAction, that is currently bound to the given key.
     */
    public final A getBoundAction( DeviceComponent comp, InputBindingsSet set )
    {
        if ( set == null )
            return ( keyBindings.get( comp ) );
        
        final A result = keyBindings.get( comp );
        
        if ( ( boundKeys[ result.ordinal() ] != null ) && ( boundKeys[ result.ordinal() ][ set.ordinal() ] == comp ) )
            return ( result );
        
        return ( null );
    }
    
    /**
     * @param comp the key of the requested InputAction
     * 
     * @return the InputAction, that is currently bound to the given key.
     */
    public final A getBoundAction( DeviceComponent comp )
    {
        return ( getBoundAction( comp, null ) );
    }
    
    /**
     * @param action
     * @param set
     * 
     * @return the key, the given action is bound to.
     */
    public final DeviceComponent getBoundComponent( A action, InputBindingsSet set )
    {
        final DeviceComponent[] keys = boundKeys[ action.ordinal() ];
        
        if ( keys == null )
            return ( null );
        
        return ( keys[ set.ordinal() ] );
    }
    
    /**
     * Binds a key to a specific InputAction.
     * 
     * @param comp the key to bind
     * @param action the InputAction to map to the given key
     * @param set the {@link InputBindingsSet} the key is to be bound at. (may be null to take the next free set or the PRIMARY)
     * 
     * @return the {@link InputBindingsSet}, the key has been bound at.
     */
    public final InputBindingsSet bind( DeviceComponent comp, A action, InputBindingsSet set )
    {
        DeviceComponent[] keys = boundKeys[ action.ordinal() ];
        
        if ( keys == null )
        {
            keys = new DeviceComponent[ NUM_KEY_SETS ];
            boundKeys[ action.ordinal() ] = keys;
            for ( int i = 0; i < NUM_KEY_SETS; i++ )
                keys[ i ] = null;
        }
        
        if ( set == null )
        {
            for ( int j = 0; j < NUM_KEY_SETS; j++ )
            {
                if ( keys[ j ] == null )
                {
                    set = InputBindingsSet.values()[ j ];
                    break;
                }
            }
            
            if ( set == null )
                set = InputBindingsSet.PRIMARY;
        }
        
        if ( keys[ set.ordinal() ] != null )
        {
            unbind( comp );
        }
        
        keys[ set.ordinal() ] = comp;
        keyBindings.put( comp, action );
        
        notifyBound( comp, set, action );
        
        return ( set );
    }
    
    /**
     * Binds a key to a specific InputAction at the fist free {@link InputBindingsSet} or {@link InputBindingsSet#PRIMARY}.
     *
     * @param comp the key to bind
     * @param action the InputAction to map to the given key
     * 
     * @return the {@link InputBindingsSet}, the key has been bound at.
     */
    public final InputBindingsSet bind( DeviceComponent comp, A action )
    {
        return ( bind( comp, action, null ) );
    }
    
    /**
     * Clears the key bindings Map.
     * 
     * @param set
     */
    public final void unbindAll( InputBindingsSet set )
    {
        for ( int i = 0; i < boundKeys.length; i++ )
        {
            if ( boundKeys[ i ] != null )
            {
                if ( set == null )
                {
                    for ( int j = 0; j < NUM_KEY_SETS; j++ )
                    {
                        if ( boundKeys[ i ][ j ] != null )
                        {
                            notifyUnbound( boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], keyBindings.get( boundKeys[ i ][ j ] ) );
                            
                            keyBindings.remove( boundKeys[ i ][ j ] );
                            boundKeys[ i ][ j ] = null;
                        }
                    }
                }
                else
                {
                    final int j = set.ordinal();
                    if ( boundKeys[ i ][ j ] != null )
                    {
                        notifyUnbound( boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], keyBindings.get( boundKeys[ i ][ j ] ) );
                        
                        keyBindings.remove( boundKeys[ i ][ j ] );
                        boundKeys[ i ][ j ] = null;
                    }
                }
            }
        }
    }
    
    /**
     * Clears the key bindings Map.
     */
    public final void unbindAll()
    {
        unbindAll( null );
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void setInputBindings( Map< DeviceComponent, ? extends A > inputBindings, boolean clearBefore )
    {
        if ( clearBefore )
            unbindAll();
        
        for ( DeviceComponent key: inputBindings.keySet() )
        {
            bind( key, inputBindings.get( key ) );
        }
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void setInputBindings( Map< DeviceComponent, ? extends A > inputBindings )
    {
        setInputBindings( inputBindings, true );
    }
    
    /**
     * @return a Map with the KeyCodes mapped to InputActions.
     */
    public Map< DeviceComponent, A > getInputBindingsMap()
    {
        return ( keyBindings );
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void set( InputBindingsManager< ? extends A > bindings, boolean clearBefore )
    {
        if ( clearBefore )
            unbindAll();
        
        final int n = Math.min( this.boundKeys.length, bindings.boundKeys.length );
        
        for ( int i = 0; i < n; i++ )
        {
            if ( bindings.boundKeys[ i ] == null )
            {
                if ( this.boundKeys[ i ] != null )
                {
                    for ( int j = 0; j < NUM_KEY_SETS; j++ )
                        this.boundKeys[ i ][ j ] = null;
                }
            }
            else
            {
                if ( this.boundKeys[ i ] == null )
                    this.boundKeys[ i ] = new DeviceComponent[ NUM_KEY_SETS ];
                
                for ( int j = 0; j < NUM_KEY_SETS; j++ )
                {
                    this.boundKeys[ i ][ j ] = bindings.boundKeys[ i ][ j ];
                    
                    if ( bindings.boundKeys[ i ][ j ] != null )
                    {
                        notifyBound( bindings.boundKeys[ i ][ j ], InputBindingsSet.values()[ j ], bindings.keyBindings.get( bindings.boundKeys[ i ][ j ] ) );
                    }
                }
            }
        }
        
        for ( DeviceComponent comp: bindings.keyBindings.keySet() )
        {
            this.keyBindings.put( comp, bindings.keyBindings.get( comp ) );
        }
    }
    
    /**
     * Sets the key-bindings Map with the KeyCodes mapped to InputActions.
     */
    public final void set( InputBindingsManager< ? extends A > bindings )
    {
        set( bindings, true );
    }
    
    public InputBindingsManager( int numCommands )
    {
        this.keyBindings = new HashMap< DeviceComponent, A >();
        this.boundKeys = new DeviceComponent[ numCommands ][];
    }
}
