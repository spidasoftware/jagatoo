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
package org.jagatoo.input.impl.mixed;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.InputDeviceFactory;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.impl.jinput.JInputController;
import org.jagatoo.input.impl.jinput.JInputInputDeviceFactory;
import org.jagatoo.input.impl.lwjgl.LWJGLInputDeviceFactory;
import org.jagatoo.input.impl.lwjgl.LWJGLKeyboard;
import org.jagatoo.input.impl.lwjgl.LWJGLMouse;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * This {@link InputDeviceFactory} is backed by an {@link LWJGLInputDeviceFactory}
 * for Keyboards and Mouses and a {@link JInputInputDeviceFactory} for Controllers.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLJInputInputDeviceFactory extends InputDeviceFactory
{
    private final LWJGLInputDeviceFactory lwjglFactory;
    private final JInputInputDeviceFactory jInputFactory;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected synchronized final void flushCache( boolean keyboards, boolean mouses, boolean controllers )
    {
        flushCache( lwjglFactory, keyboards, mouses, false );
        flushCache( jInputFactory, false, false, controllers );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected LWJGLMouse[] initMouses() throws InputSystemException
    {
        return ( (LWJGLMouse[])initMouses( lwjglFactory ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected LWJGLKeyboard[] initKeyboards() throws InputSystemException
    {
        return ( (LWJGLKeyboard[])initKeyboards( lwjglFactory ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JInputController[] initControllers() throws InputSystemException
    {
        return ( (JInputController[])initControllers( jInputFactory ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void destroyImpl( InputSystem inputSystem ) throws InputSystemException
    {
        lwjglFactory.destroy( inputSystem );
        jInputFactory.destroy( inputSystem );
    }
    
    public LWJGLJInputInputDeviceFactory( InputSourceWindow sourceWindow, EventQueue eventQueue )
    {
        super( null, true, sourceWindow, eventQueue );
        
        this.lwjglFactory = new LWJGLInputDeviceFactory( this, sourceWindow, eventQueue );
        this.jInputFactory = new JInputInputDeviceFactory( this, sourceWindow, eventQueue );
    }
}
