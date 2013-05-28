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
package org.jagatoo.loaders.models.collada.datastructs.geometries;

/**
 * A COLLADA Mesh : it's a collection of vertices and optionally
 * vertex indices, normals (and indices), colors (and indices),
 * UV coordinates (and indices).
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class MeshSources
{
    /** Vertices. Format : X,Y,Z May NOT be null */
    private float[] vertices = null;
    
    /** Normals. Format : X,Y,Z. May be null */
    private float[] normals = null;
    
    /** Colors. Format : R,G,B,A. May be null */
    private float[] colors = null;
    
    /** UV coordinates. 2-dimensional arrays because there can be several texture sets. May be null */
    private float[][] uvs = null;
    
    public void setVertices( float[] vertices )
    {
        this.vertices = vertices;
    }
    
    public final float[] getVertices()
    {
        return ( vertices );
    }
    
    public void setNormals( float[] normals )
    {
        this.normals = normals;
    }
    
    public final float[] getNormals()
    {
        return ( normals );
    }
    
    public void setColors( float[] colors )
    {
        this.colors = colors;
    }
    
    public final float[] getColors()
    {
        return ( colors );
    }
    
    public void setUVs( float[][] uvs )
    {
        this.uvs = uvs;
    }
    
    public void addUV( float[] uv )
    {
        if ( uvs == null )
        {
            uvs = new float[][] { uv };
        }
        else
        {
            float[][] newUVs = new float[ uvs.length + 1 ][];
            System.arraycopy( uvs, 0, newUVs, 0, uvs.length );
            uvs = newUVs;
            uvs[ uvs.length - 1 ] = uv;
        }
    }
    
    public final float[][] getUVs()
    {
        return ( uvs );
    }
    
    /**
     * @return a copy of these sources.
     */
    public MeshSources copy()
    {
        MeshSources newMS = new MeshSources();
        
        newMS.vertices = new float[ this.vertices.length ];
        System.arraycopy( this.vertices, 0, newMS.vertices, 0, this.vertices.length );
        
        if ( this.normals != null )
        {
            newMS.normals = new float[ this.normals.length ];
            System.arraycopy( this.normals, 0, newMS.normals, 0, this.normals.length );
        }
        
        if ( this.colors != null )
        {
            newMS.colors = new float[ this.colors.length ];
            System.arraycopy( this.colors, 0, newMS.colors, 0, this.colors.length );
        }
        
        if ( this.uvs != null )
        {
            newMS.uvs = new float[ this.uvs.length ][];
            for ( int i = 0; i < uvs.length; i++ )
            {
                final float[] uv = this.uvs[ i ];
                
                float[] newUV = new float[ uv.length ];
                System.arraycopy( uv, 0, newUV, 0, uv.length );
                newMS.uvs[ i ] = newUV;
            }
            
            System.arraycopy( this.uvs, 0, newMS.uvs, 0, this.uvs.length );
        }
        
        return ( newMS );
    }
    
    /**
     * Create a new COLLADA Mesh
     */
    public MeshSources()
    {
        // Do nothing, we're just a data-structure, you know :)
    }
}
