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

import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.components.ControllerButton;

/**
 * Stores the details associated with a controller event.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ControllerButtonPressedEvent extends ControllerButtonEvent
{
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( "ControllerButtonPressedEvent { " +
                "when = " + getWhen() + ", " +
                "lastWhen = " + getLastWhen() +
                " }"
              );
    }
    
    /**
     * @param controller
     * @param button
     * @param when
     * @param lastWhen
     */
    public void set( Controller controller, ControllerButton button, long when, long lastWhen )
    {
        super.set( controller, SubType.BUTTON_PRESSED, button, when, lastWhen );
        
        this.button = button;
    }
    
    /**
     * Sets the fields of this ControllerButtonPressedEvent to match the given event.
     */
    public void set( ControllerButtonPressedEvent e )
    {
        super.set( e );
    }
    
    /**
     * Creates a ControllerButtonPressedEvent with default values.
     */
    public ControllerButtonPressedEvent()
    {
        super( SubType.BUTTON_PRESSED );
    }
    
    /**
     * Create a new event.
     * 
     * @param controller
     * @param button
     * @param when
     * @param lastWhen
     */
    public ControllerButtonPressedEvent( Controller controller, ControllerButton button, long when, long lastWhen )
    {
        super( controller, SubType.BUTTON_PRESSED, button, when, lastWhen );
        
        this.button = button;
    }
}
