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
package org.jagatoo.input.events;

import java.util.ArrayList;

import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * A pool for ControllerEvent instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class ControllerEventPool
{
    private static ArrayList< ControllerAxisChangedEvent > instances_axis = new ArrayList< ControllerAxisChangedEvent >( 64 );
    private static ArrayList< ControllerButtonPressedEvent > instances_pressed = new ArrayList< ControllerButtonPressedEvent >( 64 );
    private static ArrayList< ControllerButtonReleasedEvent > instances_released = new ArrayList< ControllerButtonReleasedEvent >( 64 );
    private static int n_axis = 0;
    private static int n_pressed = 0;
    private static int n_released = 0;
    
    private static final Object LOCK_axis = new Object();
    private static final Object LOCK_pressed = new Object();
    private static final Object LOCK_released = new Object();
    
    public static ControllerAxisChangedEvent allocAxis()
    {
        synchronized ( LOCK_axis )
        {
            if ( n_axis > 0 )
            {
                ControllerAxisChangedEvent e = instances_axis.remove( --n_axis );
                
                return ( e );
            }
            
            return ( new ControllerAxisChangedEvent() );
        }
    }
    
    public static ControllerAxisChangedEvent allocAxis( Controller controller, ControllerAxis axis, float axisDelta, long when, long lastWhen )
    {
        final ControllerAxisChangedEvent e = allocAxis();
        
        e.set( controller, axis, axisDelta, when, lastWhen );
        
        return ( e );
    }
    
    public static void freeAxis( ControllerAxisChangedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_axis )
        {
            instances_axis.add( e );
            n_axis++;
        }
    }
    
    public static ControllerButtonPressedEvent allocPressed()
    {
        synchronized ( LOCK_pressed )
        {
            if ( n_pressed > 0 )
            {
                ControllerButtonPressedEvent e = instances_pressed.remove( --n_pressed );
                
                return ( e );
            }
            
            return ( new ControllerButtonPressedEvent() );
        }
    }
    
    public static ControllerButtonPressedEvent allocPressed( Controller controller, ControllerButton button, long when, long lastWhen )
    {
        final ControllerButtonPressedEvent e = allocPressed();
        
        e.set( controller, button, when, lastWhen );
        
        return ( e );
    }
    
    public static void freePressed( ControllerButtonPressedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_pressed )
        {
            instances_pressed.add( e );
            n_pressed++;
        }
    }
    
    public static ControllerButtonReleasedEvent allocReleased()
    {
        synchronized ( LOCK_released )
        {
            if ( n_released > 0 )
            {
                ControllerButtonReleasedEvent e = instances_released.remove( --n_released );
                
                return ( e );
            }
            
            return ( new ControllerButtonReleasedEvent() );
        }
    }
    
    public static ControllerButtonReleasedEvent allocReleased( Controller controller, ControllerButton button, long when, long lastWhen )
    {
        final ControllerButtonReleasedEvent e = allocReleased();
        
        e.set( controller, button, when, lastWhen );
        
        return ( e );
    }
    
    public static void freeReleased( ControllerButtonReleasedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_released )
        {
            instances_released.add( e );
            n_released++;
        }
    }
    
    public static void cleanup( InputSourceWindow sourceWindow )
    {
        if ( sourceWindow == null )
        {
            synchronized ( LOCK_axis )
            {
                instances_axis.clear();
                n_axis = 0;
            }
            
            synchronized ( LOCK_pressed )
            {
                instances_pressed.clear();
                n_pressed = 0;
            }
            
            synchronized ( LOCK_released )
            {
                instances_released.clear();
                n_released = 0;
            }
        }
        else
        {
            synchronized ( LOCK_axis )
            {
                for ( int i = instances_axis.size() - 1; i >= 0; i-- )
                {
                    if ( instances_axis.get( i ).getController().getSourceWindow() == sourceWindow )
                    {
                        instances_axis.remove( i );
                        n_axis--;
                    }
                }
            }
            
            synchronized ( LOCK_pressed )
            {
                for ( int i = instances_pressed.size() - 1; i >= 0; i-- )
                {
                    if ( instances_pressed.get( i ).getController().getSourceWindow() == sourceWindow )
                    {
                        instances_pressed.remove( i );
                        n_pressed--;
                    }
                }
            }
            
            synchronized ( LOCK_released )
            {
                for ( int i = instances_released.size() - 1; i >= 0; i-- )
                {
                    if ( instances_released.get( i ).getController().getSourceWindow() == sourceWindow )
                    {
                        instances_released.remove( i );
                        n_released--;
                    }
                }
            }
        }
    }
    
    private ControllerEventPool()
    {
    }
}
