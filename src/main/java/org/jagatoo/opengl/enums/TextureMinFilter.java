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
public enum TextureMinFilter
{
    /**
     * Selects the nearest texel in the level 0 texture map.
     * <p>
     * Uses the value of the texture element that is nearest (in Manhattan
     * distance) to the center of the pixel being textured.
     */
    POINT( OGL.GL_NEAREST, OGL.GL_NEAREST_MIPMAP_NEAREST, 1 ),
    
    /**
     * Performs a bilinear interpolation on the four nearest texels in the level
     * 0 texture map.
     * <p>
     * Uses the weighted average of the four texture elements that are closest
     * to the center of the pixel being textured. These can include border
     * texture elements, depending on the values of GL_TEXTURE_WRAP_S and
     * GL_TEXTURE_WRAP_T, and on the exact mapping.
     */
    BILINEAR( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_NEAREST, 1 ),
    
    /**
     * Performs a trilinear interpolation of the texels between four texels each
     * from the two nearest mipmap levels.
     * <p>
     * Chooses the two mipmaps that most closely match the size of the pixel
     * being textured and uses the BASE_LEVEL_LINEAR criterion (a weighted
     * average of the four texture elements that are closest to the center of
     * the pixel) to produce a texture value from each mipmap. The final texture
     * value is a weighted average of those two values.
     * <p>
     * Falls back to BILINEAR, if mipmapping is not used!
     */
    TRILINEAR( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_LINEAR, 1 ),
    
    /**
     * Uses {@link #TRILINEAR} as base filter and additionally applies
     * a 2x anisotropic filter, if available.
     * <p>
     * Falls back to {@link #TRILINEAR} only, if not available.
     */
    ANISOTROPIC_2( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_LINEAR, 2 ),
    
    /**
     * Uses {@link #TRILINEAR} as base filter and additionally applies
     * a 4x anisotropic filter, if available.
     * <p>
     * Falls back to {@link #ANISOTROPIC_2}, if 4x is not available
     * and to {@link #TRILINEAR} only, if no anisotropic filter is available.
     */
    ANISOTROPIC_4( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_LINEAR, 4 ),
    
    /**
     * Uses {@link #TRILINEAR} as base filter and additionally applies
     * an 8x anisotropic filter, if available.
     * <p>
     * Falls back to lower anisotropics, if 8x is not available
     * and to {@link #TRILINEAR} only, if no anisotropic filter is available.
     */
    ANISOTROPIC_8( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_LINEAR, 8 ),
    
    /**
     * Uses {@link #TRILINEAR} as base filter and additionally applies
     * a 16x anisotropic filter, if available.
     * <p>
     * Falls back to lower anisotropics, if 16x is not available
     * and to {@link #TRILINEAR} only, if no anisotropic filter is available.
     */
    ANISOTROPIC_16( OGL.GL_LINEAR, OGL.GL_LINEAR_MIPMAP_LINEAR, 16 ),
    ;
    
    /**
     * Uses the fastest available method for processing geometry.
     */
    public static final TextureMinFilter FASTEST = POINT;
    
    /**
     * Uses the nicest available method for processing geometry (excluding anisotropy).
     */
    public static final TextureMinFilter NICER = TRILINEAR;
    
    /**
     * Uses the nicest available method for processing geometry.
     */
    public static final TextureMinFilter NICEST = ANISOTROPIC_16;
    
    private final int glValue_no_mipmapping;
    private final int glValue_mipmapping;
    private final int anisotropicLevel;
    
    public final int getOpenGLBaseFilter( boolean mipmapping )
    {
        if ( mipmapping )
            return ( glValue_mipmapping );
        
        return ( glValue_no_mipmapping );
    }
    
    public final int getAnisotropicLevel()
    {
        return ( anisotropicLevel );
    }
    
    private TextureMinFilter( int glValue_no_mipmapping, int glValue_mipmapping, int anisotropicLevel )
    {
        this.glValue_no_mipmapping = glValue_no_mipmapping;
        this.glValue_mipmapping = glValue_mipmapping;
        this.anisotropicLevel = anisotropicLevel;
    }
}
