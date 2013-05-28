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

import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.MouseButton;

/**
 * Stores details assotiated with a mouse-clicked-event.
 * This may be a single-click or a double-click or what ever.
 * Refer to the {@link #getClickCount()} method to know about it.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseButtonClickedEvent extends MouseButtonEvent
{
    private int clickCount = 0;
    
    /**
     * Incremenets the clickCount of this event.
     * 
     * @param when
     */
    final void incClickCount( long when )
    {
        this.clickCount++;
        this.setWhen( when );
    }
    
    /**
     * @return the number of clicks.
     */
    public final int getClickCount()
    {
        return ( clickCount );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( "MouseButtonClickedEvent( pos = (" + getX() + ", " + getY() + "), " +
                "button = " + getButton() + ", " +
                "buttonsState = " + getMouse().getButtonsState() + ", " +
                "clickCount = " + getClickCount() + ", " +
                "when = " + getWhen() + ", " +
                "lastWhen = " + getLastWhen() +
                " )"
              );
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected void set( Mouse mouse, MouseButton button, int clickCount, long when, long lastWhen )
    {
        super.set( mouse, SubType.BUTTON_CLICKED, button, when, lastWhen );
        
        this.clickCount = clickCount;
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    public MouseButtonClickedEvent( Mouse mouse, MouseButton button, int clickCount, long when, long lastWhen )
    {
        super( mouse, SubType.BUTTON_CLICKED, button, when, lastWhen );
        
        this.clickCount = clickCount;
    }
    
    /**
     * Creates a MouseEvent with default values.
     */
    protected MouseButtonClickedEvent()
    {
        super( SubType.BUTTON_CLICKED );
    }
}
