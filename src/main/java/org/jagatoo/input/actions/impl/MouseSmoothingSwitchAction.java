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
package org.jagatoo.input.actions.impl;

import org.jagatoo.input.actions.AbstractLabeledInvokableInputAction;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.handlers.InputHandler;

/**
 * This {@link LabeledInvokableInputAction} handles disabling and enabling of an
 * {@link InputHandler}'s mouse smoothing flag.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseSmoothingSwitchAction extends AbstractLabeledInvokableInputAction
{
    public static final String SUCCESS = "ok";
    
    private final InputHandler< ? > ih;
    
    public String invokeAction( InputDevice device, DeviceComponent comp, int delta, int state, long nanoTime )
    {
        ih.setMouseSmoothingEnabled( !ih.isMouseSmoothingEnabled() );
        //System.out.println( ih.isMouseSmoothingEnabled() );
        
        return ( SUCCESS );
    }
    
    public MouseSmoothingSwitchAction( int ordinal, String text, InputHandler< ? > ih )
    {
        super( ordinal, text );
        
        this.ih = ih;
    }
    
    public MouseSmoothingSwitchAction( int ordinal, InputHandler< ? > ih )
    {
        this( ordinal, "Switch mouse smoothing", ih );
    }
}
