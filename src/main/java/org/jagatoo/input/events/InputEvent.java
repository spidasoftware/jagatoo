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

import org.jagatoo.input.devices.components.DeviceComponent;

/**
 * This is the abstract base class for all input events
 * like keyboard-, mouse- and controller-events.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class InputEvent
{
    public static enum Type
    {
        KEYBOARD_EVENT,
        MOUSE_EVENT,
        CONTROLLER_EVENT;
    }
    
    private final Type type;
    
    private DeviceComponent component;
    
    public final Type getType()
    {
        return ( type );
    }
    
    /**
     * The time that the event occured (if a timestamps are being used).
     */
    private long when;
    
    /**
     * The time the last event occured (if a timestamps are being used).
     */
    private long lastWhen;
    
    /**
     * @return the {@link DeviceComponent}, that triggered this event.
     */
    public final DeviceComponent getComponent()
    {
        return ( component );
    }
    
    /**
     * Sets the time that the event occured (if a timestamps are being used).
     */
    final void setWhen( long when )
    {
        this.when = when;
    }
    
    /**
     * @return the time that the event occured (if a timestamps are being used).
     */
    public final long getWhen()
    {
        return ( when );
    }
    
    /**
     * The time the last event occured (if a timestamps are being used).
     */
    public final long getLastWhen()
    {
        return ( lastWhen );
    }
    
    /**
     * @return in formation about this {@link InputEvent} as a String.
     */
    @Override
    public abstract String toString();
    
    protected void set( DeviceComponent component, long when, long lastWhen )
    {
        this.component = component;
        this.when = when;
        this.lastWhen = lastWhen;
    }
    
    protected void set( InputEvent e )
    {
        this.component = e.getComponent();
        this.when = e.getWhen();
        this.lastWhen = e.getLastWhen();
    }
    
    public InputEvent( Type type, DeviceComponent component, long when, long lastWhen )
    {
        this.type = type;
        this.component = component;
        this.when = when;
        this.lastWhen = lastWhen;
    }
}
