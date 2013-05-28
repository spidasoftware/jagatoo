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

/**
 * This type of eevnt is fired when a printable key was typed.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class KeyTypedEvent extends KeyboardEvent
{
    private char keyChar;
    
    /**
     * The key related to this event.
     */
    public final char getKeyChar()
    {
        return ( keyChar );
    }
    
    /**
     * Initialises the new KeyboardEvent using the given values.
     * 
     * @param keyChar the char of the typed key
     * @param when the timestamp of the KeyboardEvent 
     */
    protected void set( Keyboard keyboard, char keyChar, int modifierMask, long when, long lastWhen )
    {
        set( keyboard, null, modifierMask, when, lastWhen );
        
        this.keyChar = keyChar;
    }
    
    /**
     * Creates a new KeyboardEvent with the default settings
     */
    protected KeyTypedEvent()
    {
        super( SubType.TYPED );
    }
    
    /**
     * Initialises the new KeyboardEvent using the given values.
     * 
     * @param keyChar the char of the typed key
     * @param when the timestamp of the KeyboardEvent 
     */
    protected KeyTypedEvent( Keyboard keyboard, char keyChar, int modifierMask, long when, long lastWhen )
    {
        this();
        
        set( keyboard, keyChar, modifierMask, when, lastWhen );
    }
}
