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
/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.ac3d;

/**
 * Contains all the information for a surface
 * The vertex references and texture coordinates are returned in arrays, these
 * arrays are ordered the same, so index i in the vertex reference array referes
 * to the same vertex as as index i in the texture coordinate array.
 * 
 * @author Jeremy
 * @author Marvin Froehlich (aka Qudus)
 * 
 * @version 1.1
 */
public class AC3DSurface
{
    /** polygon type*/
    public static final int  POLYGON = 0;
    /** closed line type*/
    public static final int  CLOSED_LINE = 1;
    /** open line type*/
    public static final int  LINE = 2;
    
    /** The type of this surface */
    private int             type;
    /** is two sided?*/
    private boolean         twoSided;
    /** is shaded */
    private boolean         shaded;
    /** materials index */
    private int             material;
    /** The vertecies on this surface */
    private int[]           surfVerts;
    /** The texture coordiantes for each vertex */
    private float[]         textCoords;
    
    private Object          userObject = null;
    
    /**
     * @return The type of this surface
     */
    public int getType()
    {
        return ( type );
    }
    
    /**
     * Gets the vertex count, used to verify that this surface is valid
     * e.g. that asa poly it must have more than 3 vertecies
     * 
     * @return The number of vertecies on this surface
     */
    public int getVertexReferenceCount()
    {
        return ( surfVerts.length );
    }
    
    /**
     * Gets vertecies as reference to vertecies in the parent AC3DObject
     * 
     * @return The vertex references
     */
    public int[] getVertexReferences()
    {
        return ( surfVerts );
    }
    
    public boolean hasTextureCoordinates()
    {
        return ( textCoords != null );
    }
    
    /**
     * @return The texture coordinates
     */
    public float[] getTextureCoordinates()
    {
        return ( textCoords );
    }
    
    /**
     * Is this surface a line?
     * 
     * @return True if this surface is a line
     */
    public boolean isLine()
    {
        if ( type == POLYGON )
            return ( false );
        
        return ( true );
    }
    
    /**
     * Is this surface two sided?
     * 
     * @return True if this surface is two sided
     */
    public boolean isTwoSided()
    {
        return ( twoSided );
    }
    
    /**
     * @return True if this surface is a line
     */
    public boolean isShaded()
    {
        return ( shaded );
    }
    
    /**
     * Gets the material id of this surface
     * 
     * @return The material ID
     */
    public int getMaterialIndex()
    {
        return ( material );
    }
    
    public Object getUserObject()
    {
        return ( userObject );
    }
    
    /**
     * Creates new AC3DSurface 
     * 
     * @param type The type of this object
     * @param twoSided Indication of twosided/singlesided
     * @param shaded Indication of shadinging on/off
     * @param material The index to the material to use
     * @param surfVerts The index of the vertex
     * @param textCoords The texture coordinates (unmodified)
     */
    public AC3DSurface( int type, boolean twoSided, boolean shaded, int material, int[] surfVerts, float[] textCoords )
    {
        this.type        = type;
        this.twoSided    = twoSided;
        this.shaded      = shaded;
        this.material    = material;
        this.surfVerts   = surfVerts;
        this.textCoords  = textCoords;
        
        //System.out.println( "Created surface, type: " + type + " twosided: " + twoSided + " shaded: " + shaded + " with " + surfVerts.length + " vertecies" );
    }
}
