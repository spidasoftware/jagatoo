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
package org.jagatoo.loaders.models.bsp.lumps;

import org.jagatoo.loaders.models.bsp.BSPVersionDataLoader;

/**
 * TODO: Insert package comments here.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class BSPDirectory
{
    private final int version;
    
    /**
     * Stores player/object positions, etc...
     */
    public final int kEntities;
    
    /**
     * Stores texture information
     */
    public final int kTextures;
    
    /**
     * Stores the splitting planes
     */
    public final int kPlanes;
    
    /**
     * Stores the BSP nodes
     */
    public final int kNodes;
    
    /**
     * Stores the leafs of the nodes
     */
    public final int kLeafs;
    
    /**
     * Stores the leaf's indices into the faces
     */
    public final int kLeafFaces;
    
    /**
     * Stores the leaf's indices into the brushes
     */
    public final int kLeafBrushes;
    
    /**
     * Stores the info of world models
     */
    public final int kModels;
    
    /**
     * Stores the brushes info (for collision)
     */
    public final int kBrushes;
    
    /**
     * Stores the brush surfaces info
     */
    public final int kBrushSides;
    
    /**
     * Stores the level vertices
     */
    public final int kVertices;
    
    /**
     * Stores the model vertices offsets
     */
    public final int kMeshVerts;
    
    /**
     * Stores the shader files (blending, anims..)
     */
    public final int kShaders;
    
    /**
     * Stores the faces for the level
     */
    public final int kFaces;
    
    /**
     * Stores the lightmaps for the level
     */
    public final int kLightmaps;
    
    /**
     * Stores extra world lighting information
     */
    public final int kLightVolumes;
    
    /**
     * Stores PVS and cluster info (visibility)
     */
    public final int kVisData;
    
    public final int kSurfEdges;
    
    /**
     * A constant to store the number of lumps
     */
    public final int kMaxLumps;
    
    public final int getVersion()
    {
        return ( version );
    }
    
    /**
     * @return a {@link BSPVersionDataLoader}, that loads the lump-data
     * in the way, the version wants it.
     */
    public abstract BSPVersionDataLoader getDataLoader();
    
    public BSPDirectory( int version,
                         int kEntities,
                         int kTextures,
                         int kPlanes,
                         int kNodes,
                         int kLeafs,
                         int kLeafFaces,
                         int kLeafBrushes,
                         int kModels,
                         int kBrushes,
                         int kBrushSides,
                         int kVertices,
                         int kMeshVerts,
                         int kShaders,
                         int kFaces,
                         int kLightmaps,
                         int kLightVolumes,
                         int kVisData,
                         int kSurfEdges,
                         int kMaxLumps
                       )
    {
        this.version = version;
        
        this.kEntities = kEntities;
        this.kTextures = kTextures;
        this.kPlanes = kPlanes;
        this.kNodes = kNodes;
        this.kLeafs = kLeafs;
        this.kLeafFaces = kLeafFaces;
        this.kLeafBrushes = kLeafBrushes;
        this.kModels = kModels;
        this.kBrushes = kBrushes;
        this.kBrushSides = kBrushSides;
        this.kVertices = kVertices;
        this.kMeshVerts = kMeshVerts;
        this.kShaders = kShaders;
        this.kFaces = kFaces;
        this.kLightmaps = kLightmaps;
        this.kLightVolumes = kLightVolumes;
        this.kVisData = kVisData;
        this.kSurfEdges = kSurfEdges;
        this.kMaxLumps = kMaxLumps;
    }
}
