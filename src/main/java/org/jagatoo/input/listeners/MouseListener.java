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
package org.jagatoo.input.listeners;

import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.events.MouseButtonClickedEvent;
import org.jagatoo.input.events.MouseButtonEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseWheelEvent;

/**
 * Listens for generic mouse events by any complying mouse input API.
 * Events include button presses and releases and mouse positions.
 * Exclusive mode is also supported where the mouse does not have a position,
 * but rather mouse movements (deltas) on a given axis are reported.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface MouseListener
{
    /**
     * Invoked when a button press was registered by the mouse device.
     * 
     * @param e the MouseEvent with all assotiated data
     * @param button
     */
    public void onMouseButtonPressed( MouseButtonPressedEvent e, MouseButton button );
    
    /**
     * Invoked when a button release was registered by the mouse device.
     * 
     * @param e the MouseEvent with all assotiated data
     * @param button
     */
    public void onMouseButtonReleased( MouseButtonReleasedEvent e, MouseButton button );
    
    /**
     * Invoked when a button click was registered by the mouse device.
     * 
     * @param e the MouseEvent with all assotiated data
     * @param button
     * @param clickCount
     */
    public void onMouseButtonClicked( MouseButtonClickedEvent e, MouseButton button, int clickCount );
    
    /**
     * Invoked when a button state-change was registered by the mouse device.
     * 
     * @param e the MouseEvent with all assotiated data
     * @param button
     * @param state
     */
    public void onMouseButtonStateChanged( MouseButtonEvent e, MouseButton button, boolean state );
    
    /**
     * Invoked when a change of the mouse's position was registered by the mouse device
     * while in non-exclusive mode.
     * 
     * @param e the MouseEvent with all assotiated data
     * @param x
     * @param y
     * @param dx
     * @param dy
     */
    public void onMouseMoved( MouseMovedEvent e, int x, int y, int dx, int dy );
    
    /**
     * Invoked when the mouse wheel has been moved
     * 
     * @param e the MouseEvent with all assotiated data
     * @param wheelDelta
     */
    public void onMouseWheelMoved( MouseWheelEvent e, int wheelDelta );
}
