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

import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.components.Key;

/**
 * Stores the details associated with a keyboard event.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class KeyboardEvent extends InputEvent
{
    public enum SubType
    {
        PRESSED,
        RELEASED,
        TYPED;
    }
    
    /**
     * The state of the key (true if it is pressed, false if it isn't).
     */
    private final SubType subType;
    
    /**
     * The Keyboard, that caused the event.
     */
    private Keyboard keyboard;
    
    private Key key;
    
    private int modifierMask;
    
    /**
     * The state of the key (true if it is pressed, false if it isn't).
     */
    public final SubType getSubType()
    {
        return ( subType );
    }
    
    /**
     * The Keyboarde, that caused the event.
     */
    public final Keyboard getKeyboard()
    {
        return ( keyboard );
    }
    
    /**
     * The key related to this event.
     */
    public final Key getKey()
    {
        return ( key );
    }
    
    /**
     * @return a bitmask with all held modifiers.
     */
    public final int getModifierMask()
    {
        return ( modifierMask );
    }
    
    /**
     * The key related to this event.
     * 
     * @see KeyCode
     */
    public final int getKeyCode()
    {
        if ( key == null )
            return ( 0 );
        
        return ( key.getKeyCode() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( this.getClass().getSimpleName() + "( keyCode = " + getKeyCode() + ", keyChar = " + getKeyCode() + ", when = " + getWhen() + " )" );
    }
    
    /**
     * Initialises this KeyboardEvent using the given values.
     * 
     * @param keyboard
     * @param key the key whose state changed
     * @param modifierMask the mask of all held modifiers
     * @param when the timestamp of the KeyboardEvent 
     * @param lastWhen
     */
    protected void set( Keyboard keyboard, Key key, int modifierMask, long when, long lastWhen )
    {
        super.set( key, when, lastWhen );
        
        this.keyboard = keyboard;
        this.key = key;
        this.modifierMask = modifierMask;
    }
    
    /**
     * Creates a new KeyboardEvent with the default settings
     */
    protected KeyboardEvent( SubType subType )
    {
        super( Type.KEYBOARD_EVENT, null, 0L, 0L );
        
        this.subType = subType;
    }
    
    /**
     * Initialises the new KeyboardEvent using the given values.
     * 
     * @param keyboard
     * @param subType the kind of KeyboardEvent
     * @param key the key whose state changed
     * @param modifierMask the mask of all held modifiers
     * @param when the timestamp of the KeyboardEvent
     * @param lastWhen 
     */
    protected KeyboardEvent( Keyboard keyboard, SubType subType, Key key, int modifierMask, long when, long lastWhen )
    {
        this( subType );
        
        set( keyboard, key, modifierMask, when, lastWhen );
    }
}
