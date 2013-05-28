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
package org.jagatoo.input.util;

import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.components.ControllerAxis;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.events.ControllerAxisChangedEvent;
import org.jagatoo.input.events.ControllerButtonEvent;
import org.jagatoo.input.events.ControllerButtonPressedEvent;
import org.jagatoo.input.events.ControllerButtonReleasedEvent;
import org.jagatoo.input.listeners.ControllerListener;

/**
 * This is a utility class, that may help you calibrating
 * a Controller.<br>
 * It simply listens for axis-changed-events and adjusts
 * the min- and max-values of the axes.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class ControllerCalibrator
{
    private static Controller controller = null;
    
    private static class Worker implements ControllerListener
    {
        public void onControllerAxisChanged( ControllerAxisChangedEvent e, ControllerAxis axis, float axisDelta )
        {
            if ( axis.getFloatValue() < axis.getMinValue() )
                axis.setMinValue( axis.getFloatValue() );
            else if ( axis.getFloatValue() > axis.getMaxValue() )
                axis.setMaxValue( axis.getFloatValue() );
        }
        
        public void onControllerButtonPressed( ControllerButtonPressedEvent e, ControllerButton button )
        {
        }
        
        public void onControllerButtonReleased( ControllerButtonReleasedEvent e, ControllerButton button )
        {
        }
        
        public void onControllerButtonStateChanged( ControllerButtonEvent e, ControllerButton button, boolean state )
        {
        }
    }
    
    private static Worker worker = new Worker();
    
    public static void start( Controller controller )
    {
        if ( ControllerCalibrator.controller != null )
        {
            ControllerCalibrator.controller.removeControllerListener( worker );
        }
        
        ControllerCalibrator.controller = controller;
        controller.addControllerListener( worker );
    }
    
    public static void stop()
    {
        if ( ControllerCalibrator.controller == null )
            return;
        
        ControllerCalibrator.controller.removeControllerListener( worker );
        ControllerCalibrator.controller = null;
    }
}
