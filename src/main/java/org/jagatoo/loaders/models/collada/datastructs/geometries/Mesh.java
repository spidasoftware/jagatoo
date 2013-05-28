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
public class Mesh
{
    /** Mesh sources : vertices, normals, colors, UVs. May NOT be null */
    private MeshSources sources = null;
    
    /** Vertex indices. May NOT be null */
    private int[] vertexIndices = null;
    
    /** Normal indices. May be null */
    private int[] normalIndices = null;
    
    /** Color indices. May be null */
    private int[] colorIndices = null;
    
    /** UV coordinate indices. May be null */
    private int[][] uvIndices = null;
    
    public void setSources( MeshSources sources )
    {
        this.sources = sources;
    }
    
    public final MeshSources getSources()
    {
        return ( sources );
    }
    
    public final void setVertexIndices( int[] vertexIndices )
    {
        this.vertexIndices = vertexIndices;
    }
    
    public final int[] getVertexIndices()
    {
        return ( vertexIndices );
    }
    
    public final void setNormalIndices( int[] normalIndices )
    {
        this.normalIndices = normalIndices;
    }
    
    public final int[] getNormalIndices()
    {
        return ( normalIndices );
    }
    
    public final void setColorIndices( int[] colorIndices )
    {
        this.colorIndices = colorIndices;
    }
    
    public final int[] getColorIndices()
    {
        return ( colorIndices );
    }
    
    public final void setUVIndices( int[][] uvIndices )
    {
        this.uvIndices = uvIndices;
    }
    
    public final int[][] getUVIndices()
    {
        return ( uvIndices );
    }
    
    /**
     * @return a copy of this mesh
     */
    public Mesh copy() {

        Mesh newMesh = new Mesh( sources.copy() );
        
        newMesh.vertexIndices = vertexIndices;
        newMesh.normalIndices = normalIndices;
        newMesh.colorIndices = colorIndices;
        newMesh.uvIndices = uvIndices;
        
        return ( newMesh );
    }
    
    /**
     * Create a new COLLADA Mesh
     * @param sources The sources which should be used by this mesh.
     */
    public Mesh( MeshSources sources )
    {
        this.sources = sources;
    }
}
