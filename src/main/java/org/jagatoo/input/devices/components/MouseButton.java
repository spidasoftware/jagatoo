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

/**
 * Simple abstraction of mouse-buttons.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class MouseButton extends DigitalDeviceComponent
{
    private static int nextButtonIDX = 0;
    private static int nextButtonMaskValue = 1;
    
    private final int index;
    private final int maskValue;
    
    /**
     * @return this button's index.
     */
    public final int getIndex()
    {
        return ( index );
    }
    
    /**
     * @return a value, that identifies this button in a bit-mask.
     */
    public final int getMaskValue()
    {
        return ( maskValue );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        return ( this.getClass().getSimpleName() + " \"" + getName() + "\"" );
    }
    
    /**
     * Create a new MouseButton instance.
     * 
     * @param name the button's name
     */
    MouseButton( String name )
    {
        super( Type.MOUSE_BUTTON, name );
        
        this.index = nextButtonIDX++;
        this.maskValue = nextButtonMaskValue;
        nextButtonMaskValue *= 2;
        
        MouseButtons.buttonsMap[ this.index ] = this;
    }
}
