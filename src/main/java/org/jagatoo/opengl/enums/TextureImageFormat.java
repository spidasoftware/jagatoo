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
 * The {@link TextureImageFormat} is an enum-abstraction of the possible
 * formats of a texture image (one mipmap level of a texture), that
 * OpenGL supports.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum TextureImageFormat
{
    /**
     * Texture contains only intensity values.
     */
    INTENSITY( 1, false, false, OGL.GL_INTENSITY ),
    
    /**
     * Texture contains only luminance values.
     */
    LUMINANCE( 1, false, false, OGL.GL_LUMINANCE ),
    
    /**
     * Texture contains only alpha values.
     */
    ALPHA( 1, true, false, OGL.GL_ALPHA ),
    
    /**
     * Texture contains luminance and alpha values.
     */
    LUMINANCE_ALPHA( 2, true, false, OGL.GL_LUMINANCE_ALPHA ),
    
    /**
     * Texture contains 24 bit depth.
     */
    DEPTH( 3, false, false, OGL.GL_DEPTH_COMPONENT ),
    
    /**
     * Texture contains red, green and blue color values.
     */
    RGB( 3, false, false, OGL.GL_RGB ),
    
    /**
     * Texture contains red, green, blue and alpha color values.
     */
    RGBA( 4, true, false, OGL.GL_RGBA ),
    
    /**
     * compressed texture format. Uses S3TC_DXT1 compression.
     */
    RGBA_DXT1( -1, true, true, OGL.GL_RGBA ),
    
    /**
     * compressed texture format. Uses S3TC_DXT3 compression.
     */
    RGBA_DXT3( -1, true, true, OGL.GL_RGBA ),
    
    /**
     * compressed texture format. Uses S3TC_DXT5 compression.
     */
    RGBA_DXT5( -1, true, true, OGL.GL_RGBA ),
    
    /**
     * compressed texture format. Uses S3TC_DXT1 compression.
     */
    RGB_DXT1( -1, false, true, OGL.GL_RGB );
    
    private final int pixelSize;
    private final boolean hasAlpha;
    private final boolean isCompressed;
    private final int glValue;
    
    public final int getPixelSize()
    {
        return ( pixelSize );
    }
    
    public final boolean hasAlpha()
    {
        return ( hasAlpha );
    }
    
    public final boolean isCompressed()
    {
        return ( isCompressed );
    }
    
    public final int toOpenGL()
    {
        return ( glValue );
    }
    
    private TextureImageFormat( int pixelSize, boolean hasAlpha, boolean isCompressed, int glValue )
    {
        this.pixelSize = pixelSize;
        this.hasAlpha = hasAlpha;
        this.isCompressed = isCompressed;
        this.glValue = glValue;
    }
}
