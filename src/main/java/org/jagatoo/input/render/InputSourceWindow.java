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
package org.jagatoo.input.render;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.devices.InputDeviceFactory;
import org.jagatoo.input.devices.Mouse;

/**
 * This is an abstraction of the link between the input system
 * and the render canvas.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface InputSourceWindow
{
    /**
     * @return the object to draw on and to attach the cursor to.
     */
    public Object getDrawable();
    
    /**
     * @return the DeviceFactory, that provides methods to retrieve
     * InputDevices for the specified implementation.
     * 
     * @param inputSystem
     */
    public InputDeviceFactory getInputDeviceFactory( InputSystem inputSystem );
    
    /**
     * @return must return <code>true</code>, for the InputSystem to
     * accept events from this source.
     */
    public boolean receivesInputEvents();
    
    /**
     * @return the window's width.
     */
    public int getWidth();
    
    /**
     * @return the window's height.
     */
    public int getHeight();
    
    /**
     * Sets the new Cursor for this Mouse.
     * Use <code>null</code> for an invisible Cursor.
     * Use {@link Cursor#DEFAULT_CURSOR} for the system's default Cursor.
     * 
     * @param cursor
     */
    public void setCursor( Cursor cursor );
    
    /**
     * Refreshes the Cursor on this window.
     * 
     * @param mouse the mouse, that triggers this method.
     */
    public void refreshCursor( Mouse mouse );
    
    /**
     * @return the currently used Cursor.
     */
    public Cursor getCursor();
}
