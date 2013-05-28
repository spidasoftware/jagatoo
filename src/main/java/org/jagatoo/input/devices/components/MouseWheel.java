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
package org.jagatoo.input.devices.components;

import org.jagatoo.input.devices.Mouse;

/**
 * This is a simple abstaction of a mouse-wheel.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseWheel extends AnalogDeviceComponent
{
    /**
     * To allow for using the MouseWheel's up/down state-changes
     * like Key/Button events we need this simple {@link DeviceComponent}.
     * 
     * @author Marvin Froehlich (aka Qudus)
     */
    public class WheelUpDownComponent extends DeviceComponent
    {
        private final MouseWheel wheel;
        
        private final int intValue;
        
        /**
         * @return the {@link MouseWheel}, this component belongs to.
         */
        public final MouseWheel getWheel()
        {
            return ( wheel );
        }
        
        /**
         * @return the value, this component forwards to input-events and -actions.
         */
        public final int getIntValue()
        {
            return ( intValue );
        }
        
        protected WheelUpDownComponent( Type type, String name, MouseWheel wheel, int intValue )
        {
            super( type, name );
            
            this.wheel = wheel;
            this.intValue = intValue;
        }
    }
    
    public static final MouseWheel GLOBAL_WHEEL = new MouseWheel( (Mouse)null );
    
    private final WheelUpDownComponent up = new WheelUpDownComponent( Type.MOUSE_WHEEL, "Mouse Wheel UP", this, 1 );
    private final WheelUpDownComponent down = new WheelUpDownComponent( Type.MOUSE_WHEEL, "Mouse Wheel DOWN", this, -1 );
    
    private final Mouse mouse;
    
    /**
     * @return the Mouse, this wheel belongs to.
     * This can be <code>null</code> for the global mouse-wheel.
     */
    public final Mouse getMouse()
    {
        return ( mouse );
    }
    
    /**
     * @return the special {@link DeviceComponent}, that can be used
     * like a key/button for input bindings.
     */
    public final WheelUpDownComponent getUp()
    {
        return ( up );
    }
    
    /**
     * @return the special {@link DeviceComponent}, that can be used
     * like a key/button for input bindings.
     */
    public final WheelUpDownComponent getDown()
    {
        return ( down );
    }
    
    /**
     * Creates a new MouseWheel instance.
     * 
     * @param mouse the mouse, this wheel belongs to
     * @param name the wheel's name
     */
    public MouseWheel( Mouse mouse, String name )
    {
        super( Type.MOUSE_WHEEL, name );
        
        this.mouse = mouse;
    }
    
    /**
     * Creates a new MouseWheel instance.
     * 
     * @param mouse the mouse, this wheel belongs to
     */
    public MouseWheel( Mouse mouse )
    {
        this( mouse, "Mouse Wheel" );
    }
}
