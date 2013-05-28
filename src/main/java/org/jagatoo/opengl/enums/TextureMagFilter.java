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
package org.jagatoo.opengl.enums;

import org.jagatoo.opengl.OGL;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum TextureMagFilter
{
    /**
     * Selects the nearest texel in the level 0 texture map.
     * <p>
     * Uses the value of the texture element that is nearest (in Manhattan
     * distance) to the center of the pixel being textured.
     */
    POINT( OGL.GL_NEAREST ),
    
    /**
     * Performs a bilinear interpolation on the four nearest texels in the level
     * 0 texture map.
     * <p>
     * Uses the weighted average of the four texture elements that are closest
     * to the center of the pixel being textured. These can include border
     * texture elements, depending on the values of GL_TEXTURE_WRAP_S and
     * GL_TEXTURE_WRAP_T, and on the exact mapping.
     */
    BILINEAR( OGL.GL_LINEAR ),
    ;
    
    /**
     * Uses the fastest available method for processing geometry
     */
    public static final TextureMagFilter FASTEST = POINT;
    
    /**
     * Uses the nicest available method for processing geometry
     */
    public static final TextureMagFilter NICEST = BILINEAR;
    
    private final int glValue;
    
    public final int toOpenGL()
    {
        return ( glValue );
    }
    
    private TextureMagFilter( int glValue )
    {
        this.glValue = glValue;
    }
}
