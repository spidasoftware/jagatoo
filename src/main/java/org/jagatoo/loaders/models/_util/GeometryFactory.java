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
package org.jagatoo.loaders.models._util;

import org.jagatoo.datatypes.NamedObject;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoordf;
import org.openmali.vecmath2.Vector3f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface GeometryFactory
{
    public static enum GeometryType
    {
        TRIANGLE_ARRAY,
        TRIANGLE_STRIP_ARRAY,
        INDEXED_TRIANGLE_ARRAY,
        INDEXED_TRIANGLE_STRIP_ARRAY,
        TRIANGLE_FAN_ARRAY,
        INDEXED_TRIANGLE_FAN_ARRAY,
        ;
    }
    
    public NamedObject createGeometry( String name, GeometryType type, int coordSize, int numVertices, int numIndices, int[] numStrips );
    
    public NamedObject createInterleavedGeometry( String name, GeometryType type, int coordSize, int numVertices, int numIndices, int[] numStrips, int features, boolean colorAlpha, int[] tuSizes, int[] vaSizes );
    
    public void setCoordinate( NamedObject geometry, GeometryType type, int vertexIndex, float x, float y, float z );
    
    public void setCoordinates( NamedObject geometry, GeometryType type, int vertexIndex, float[] data, int offset, int num );
    
    public void setCoordinates( NamedObject geometry, GeometryType type, int vertexIndex, Point3f[] data, int offset, int num );
    
    public void setNormal( NamedObject geometry, GeometryType type, int vertexIndex, float x, float y, float z );
    
    public void setNormals( NamedObject geometry, GeometryType type, int vertexIndex, float[] data, int offset, int num );
    
    public void setNormals( NamedObject geometry, GeometryType type, int vertexIndex, Vector3f[] data, int offset, int num );
    
    public void setTexCoord( NamedObject geometry, GeometryType type, int textureUnit, int vertexIndex, float s, float t );
    
    public void setTexCoords( NamedObject geometry, GeometryType type, int textureUnit, int texCoordSize, int vertexIndex, float[] data, int offset, int num );
    
    public void setTexCoords( NamedObject geometry, GeometryType type, int textureUnit, int texCoordSize, int vertexIndex, TexCoordf<?>[] data, int offset, int num );
    
    public void setColors( NamedObject geometry, GeometryType type, int colorSize, int vertexIndex, float[] data, int offset, int num );
    
    public void setColors( NamedObject geometry, GeometryType type, int colorSize, int vertexIndex, Colorf[] data, int offset, int num );
    
    public void setVertexAttribs( NamedObject geometry, GeometryType type, int attribIndex, int attribSize, int vertexIndex, float[] data, int offset, int num );
    
    public void setIndex( NamedObject geometry, GeometryType type, int vertexIndex, int[] data, int offset, int num );
    
    public void finalizeGeometry( NamedObject geometry, GeometryType type, int initialVertexIndex, int numValidVertices, int initialIndexIndex, int numValidIndices );
}
