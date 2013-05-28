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

import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * A pool for KeyboardEvent instances.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class KeyboardEventPool
{
    private static ArrayList< KeyPressedEvent > instances_pressed = new ArrayList< KeyPressedEvent >( 64 );
    private static ArrayList< KeyReleasedEvent > instances_released = new ArrayList< KeyReleasedEvent >( 64 );
    private static ArrayList< KeyTypedEvent > instances_typed = new ArrayList< KeyTypedEvent >( 64 );
    private static int n_pressed = 0;
    private static int n_released = 0;
    private static int n_typed = 0;
    
    private static final Object LOCK_pressed = new Object();
    private static final Object LOCK_released = new Object();
    private static final Object LOCK_typed = new Object();
    
    public static KeyPressedEvent allocPressed()
    {
        synchronized ( LOCK_pressed )
        {
            if ( n_pressed > 0 )
            {
                KeyPressedEvent e = instances_pressed.remove( --n_pressed );
                
                return ( e );
            }
            
            return ( new KeyPressedEvent() );
        }
    }
    
    public static KeyPressedEvent allocPressed( Keyboard keyboard, Key key, int modifierMask, long when, long lastWhen )
    {
        final KeyPressedEvent e = allocPressed();
        
        e.set( keyboard, key, modifierMask, when, lastWhen );
        
        return ( e );
    }
    
    public static void freePressed( KeyPressedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_pressed )
        {
            instances_pressed.add( e );
            n_pressed++;
        }
    }
    
    public static KeyReleasedEvent allocReleased()
    {
        synchronized ( LOCK_released )
        {
            if ( n_released > 0 )
            {
                KeyReleasedEvent e = instances_released.remove( --n_released );
                
                return ( e );
            }
            
            return ( new KeyReleasedEvent() );
        }
    }
    
    public static KeyReleasedEvent allocReleased( Keyboard keyboard, Key key, int modifierMask, long when, long lastWhen )
    {
        final KeyReleasedEvent e = allocReleased();
        
        e.set( keyboard, key, modifierMask, when, lastWhen );
        
        return ( e );
    }
    
    public static void freeReleased( KeyReleasedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_released )
        {
            instances_released.add( e );
            n_released++;
        }
    }
    
    public static KeyTypedEvent allocTyped()
    {
        synchronized ( LOCK_typed )
        {
            if ( n_typed > 0 )
            {
                KeyTypedEvent e = instances_typed.remove( --n_typed );
                
                return ( e );
            }
            
            return ( new KeyTypedEvent() );
        }
    }
    
    public static KeyTypedEvent allocTyped( Keyboard keyboard, char keyChar, int modifierMask, long when, long lastWhen )
    {
        final KeyTypedEvent e = allocTyped();
        
        e.set( keyboard, keyChar, modifierMask, when, lastWhen );
        
        return ( e );
    }
    
    public static void freeTyped( KeyTypedEvent e )
    {
        if ( e == null )
            return;
        
        synchronized ( LOCK_typed )
        {
            instances_typed.add( e );
            n_typed++;
        }
    }
    
    public static void cleanup( InputSourceWindow sourceWindow )
    {
        if ( sourceWindow == null )
        {
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
            
            synchronized ( LOCK_typed )
            {
                instances_typed.clear();
                n_typed = 0;
            }
        }
        else
        {
            synchronized ( LOCK_pressed )
            {
                for ( int i = instances_pressed.size() - 1; i >= 0; i-- )
                {
                    if ( instances_pressed.get( i ).getKeyboard().getSourceWindow() == sourceWindow )
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
                    if ( instances_released.get( i ).getKeyboard().getSourceWindow() == sourceWindow )
                    {
                        instances_released.remove( i );
                        n_released--;
                    }
                }
            }
            
            synchronized ( LOCK_typed )
            {
                for ( int i = instances_typed.size() - 1; i >= 0; i-- )
                {
                    if ( instances_typed.get( i ).getKeyboard().getSourceWindow() == sourceWindow )
                    {
                        instances_typed.remove( i );
                        n_typed--;
                    }
                }
            }
        }
    }
    
    private KeyboardEventPool()
    {
    }
}
