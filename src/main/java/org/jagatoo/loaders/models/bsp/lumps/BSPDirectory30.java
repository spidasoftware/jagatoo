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
import org.jagatoo.loaders.models.bsp.BSPVersionDataLoader30;

/**
 * List of Half-Life 1 BSP Lumps
 * 
 * @author Sebastian Thiele (aka SETIssl)
 */
public class BSPDirectory30 extends BSPDirectory
{
    public static final int v30_kEntities = 0;
    public static final int v30_kPlanes = 1;
    public static final int v30_kTextures = 2;
    public static final int v30_kVertices = 3;
    public static final int v30_kVisData = 4;
    public static final int v30_kNodes = 5;
    //public static final int v30_kLeafFaces = 6;     // TEXINFO
    public static final int v30_kTexInfo = 6;     // TEXINFO
    public static final int v30_kFaces = 7;
    public static final int v30_kLightmaps = 8;
    public static final int v30_kBrushes = 9;       // CLIPNODES
    public static final int v30_kLeafs = 10;
    public static final int v30_kLeafBrushes = 11;  // MARKSURFACES
    public static final int v30_kBrushSides = 12;   // EDGES
    public static final int v30_kSurfEdges = 13;
    public static final int v30_kModels = 14;
    public static final int v30_kMaxLumps = 15;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BSPVersionDataLoader getDataLoader()
    {
        return ( new BSPVersionDataLoader30() );
    }
    
    public BSPDirectory30()
    {
        super(
               30,
               v30_kEntities,
               v30_kTextures,
               v30_kPlanes,
               v30_kNodes,
               v30_kLeafs,
               v30_kTexInfo, //v30_kLeafFaces,
               v30_kLeafBrushes,
               v30_kModels,
               v30_kBrushes,
               v30_kBrushSides,
               v30_kVertices,
               -1, // kMeshVerts
               -1, // kShaders
               v30_kFaces,
               v30_kLightmaps,
               -1, // kLightVolumes
               v30_kVisData,
               v30_kSurfEdges,
               v30_kMaxLumps
             );
    }
}
