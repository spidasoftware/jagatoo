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
import org.jagatoo.input.devices.components.DeviceComponent;

/**
 * Stores the details associated with a mouse event.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class MouseEvent extends InputEvent
{
    public enum SubType
    {
        BUTTON_PRESSED,
        BUTTON_RELEASED,
        BUTTON_CLICKED,
        WHEEL_MOVED,
        MOVED,
        STOPPED;
    }
    
    /**
     * The MouseDevice, that caused the event.
     */
    private Mouse mouse;
    
    private SubType subType;
    
    
    /**
     * The MouseDevice, that caused the event.
     */
    public final Mouse getMouse()
    {
        return ( mouse );
    }
    
    public final SubType getSubType()
    {
        return ( subType );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String toString();
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected void set( Mouse mouse, SubType subType, DeviceComponent component, long when, long lastWhen )
    {
        super.set( component, when, lastWhen );
        
        this.mouse = mouse;
        this.subType = subType;
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected void set( MouseEvent e )
    {
        super.set( e );
        
        this.mouse = e.mouse;
        this.subType = e.subType;
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    public MouseEvent( Mouse mouse, SubType subType, DeviceComponent component, long when, long lastWhen )
    {
        super( Type.MOUSE_EVENT, component, when, lastWhen );
        
        this.subType = subType;
        this.mouse = mouse;
    }
    
    /**
     * Creates a MouseEvent with default values.
     */
    protected MouseEvent( SubType subType )
    {
        super( Type.MOUSE_EVENT, null, -1L, -1L );
        
        this.subType = subType;
    }
}
