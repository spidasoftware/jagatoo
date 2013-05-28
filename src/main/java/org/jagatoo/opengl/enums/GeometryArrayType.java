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
 * This enum abstracts all the available basic types of geometry array,
 * that are supported by OpenGL.
 * Each member provides the OpenGL constant for the appropriate type.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public enum GeometryArrayType
{
    /**
     * A simple array of points (one vertex per point)..
     */
    POINTS( 1, OGL.GL_POINTS ),
    
    /**
     * A simple array of lines (two vertices per line).
     */
    LINES( 2, OGL.GL_LINES ),
    
    /**
     * A strip of lines.
     * first two vertices build the first line.
     * (i-1)-th and (i)-th vertex build the following lines.
     */
    LINE_STRIP( 1, OGL.GL_LINE_STRIP ),
    
    /**
     * A simple array of triangles (three vertices per triangle).
     */
    TRIANGLES( 3, OGL.GL_TRIANGLES ),
    
    /**
     * A strip of triangles.
     * first three vertices build the first triangle.
     * (i-2)-th, (i-1)-th and (i)-th vertex build the following triangles.
     */
    TRIANGLE_STRIP( 2, OGL.GL_TRIANGLE_STRIP ),
    
    /**
     * A fan of triangles.
     * The first vertex is pert of each triangle.
     * The following triangles behave like a strip.
     */
    TRIANGLE_FAN( 2, OGL.GL_TRIANGLE_FAN ),
    
    /**
     * A simple array of quads (four vertices per quad).
     */
    QUADS( 4, OGL.GL_QUADS ),
    
    /**
     * A strip of quads.
     * first four vertices build the first quad.
     * (i-3)-th, (i-2)-th, (i-1)-th and (i)-th vertex build the following quads.
     */
    QUAD_STRIP( 3, OGL.GL_QUAD_STRIP ),
    ;
    
    private final int faceSize;
    private final int glValue;
    
    /**
     * @return the number of vertices per face for this type.
     */
    public final int getFaceSize()
    {
        return ( faceSize );
    }
    
    public final boolean isStrip()
    {
        return ( ( this == LINE_STRIP ) || ( this == TRIANGLE_STRIP ) || ( this == TRIANGLE_FAN ) || ( this == QUAD_STRIP ) );
    }
    
    public final int toOpenGL()
    {
        return ( glValue );
    }
    
    private GeometryArrayType( int faceSize, int glValue )
    {
        this.faceSize = faceSize;
        this.glValue = glValue;
    }
}
