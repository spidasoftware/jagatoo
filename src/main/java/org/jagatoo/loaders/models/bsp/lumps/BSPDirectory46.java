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
package org.jagatoo.loaders.models.bsp.lumps;

import org.jagatoo.loaders.models.bsp.BSPVersionDataLoader;
import org.jagatoo.loaders.models.bsp.BSPVersionDataLoader46;

/**
 * Insert type comment here.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class BSPDirectory46 extends BSPDirectory
{
    public static final int v46_kEntities      = 0;
    public static final int v46_kTextures      = 1;
    public static final int v46_kPlanes        = 2;
    public static final int v46_kNodes         = 3;
    public static final int v46_kLeafs         = 4;
    public static final int v46_kLeafFaces     = 5;
    public static final int v46_kLeafBrushes   = 6;
    public static final int v46_kModels        = 7;
    public static final int v46_kBrushes       = 8;
    public static final int v46_kBrushSides    = 9;
    public static final int v46_kVertices      = 10;
    public static final int v46_kMeshVerts     = 11;
    public static final int v46_kShaders       = 12;
    public static final int v46_kFaces         = 13;
    public static final int v46_kLightmaps     = 14;
    public static final int v46_kLightVolumes  = 15;
    public static final int v46_kVisData       = 16;
    public static final int v46_kMaxLumps      = 17;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BSPVersionDataLoader getDataLoader()
    {
        return ( new BSPVersionDataLoader46() );
    }
    
    public BSPDirectory46()
    {
        super(
               46,
               v46_kEntities,
               v46_kTextures,
               v46_kPlanes,
               v46_kNodes,
               v46_kLeafs,
               v46_kLeafFaces,
               v46_kLeafBrushes,
               v46_kModels,
               v46_kBrushes,
               v46_kBrushSides,
               v46_kVertices,
               v46_kMeshVerts,
               v46_kShaders,
               v46_kFaces,
               v46_kLightmaps,
               v46_kLightVolumes,
               v46_kVisData,
               -1, // kSurfEdges
               v46_kMaxLumps
             );
    }
}
