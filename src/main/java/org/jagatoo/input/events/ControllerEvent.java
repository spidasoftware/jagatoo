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
import org.jagatoo.input.devices.components.DeviceComponent;

/**
 * Abstract base class for all {@link Controller} events.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ControllerEvent extends InputEvent
{
    public enum SubType
    {
        BUTTON_PRESSED,
        BUTTON_RELEASED,
        AXIS_CHANGED;
    }
    
    private SubType subType;
    
    /**
     * The Controller device, that caused the event.
     */
    private Controller controller;
    
    
    public final SubType getSubType()
    {
        return ( subType );
    }
    
    /**
     * The Controller device, that caused the event.
     */
    public final Controller getController()
    {
        return ( controller );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String toString();
    
    /**
     * Sets the fields of this ControllerEvent.
     */
    protected void set( Controller controller, SubType subType, DeviceComponent component, long when, long lastWhen )
    {
        super.set( component, when, lastWhen );
        
        this.controller = controller;
        this.subType = subType;
    }
    
    /**
     * Sets the fields of this ControllerEvent to match the given ControllerEvent.
     */
    protected void set( ControllerEvent e )
    {
        if ( e.getType() != this.getType() )
            throw new IllegalArgumentException( "mismatching types" );
        
        super.set( e.getComponent(), e.getWhen(), e.getLastWhen() );
        
        this.controller = e.controller;
        this.subType = e.subType;
    }
    
    /**
     * Create a new event.
     * 
     * @param controller
     * @param subType
     * @param component
     * @param when
     * @param lastWhen
     */
    protected ControllerEvent( Controller controller, SubType subType, DeviceComponent component, long when, long lastWhen )
    {
        super( Type.CONTROLLER_EVENT, component, when, lastWhen );
        
        this.controller = controller;
        this.subType = subType;
    }
    
    /**
     * Creates a ControllerEvent with default values.
     */
    protected ControllerEvent( SubType subType )
    {
        super( Type.CONTROLLER_EVENT, null, -1L, -1L );
        
        this.subType = subType;
    }
}
