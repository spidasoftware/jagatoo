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
package org.jagatoo.loaders.models.bsp;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models.bsp.BSPEntitiesParser.BSPEntity;
import org.jagatoo.loaders.models.bsp.lumps.*;
import org.jagatoo.loaders.textures.AbstractTexture;

/**
 * Contains all data of a whole loaded BSP scene.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class BSPScenePrototype
{
    public final int        sourceBSPVersion;
    
    /**
     * 
     */
    public BSPEntity[]      entities;
    
    /**
     * 
     */
    public WADFile[]        wadFiles;
    
    /**
     * 
     */
    public AbstractTexture[][]  baseTextures;
    
    /**
     * Half-Life only Lump
     */
    public BSPTexInfo[]     texInfos;
    
    /**
     * 
     */
    public BSPPlane[]       planes;
    
    /**
     * 
     */
    public BSPNode[]        nodes;
    
    /**
     * 
     */
    public BSPLeaf[]        leafs;
    
    /**
     * 
     */
    public int[]            leafFaces;
    
    /**
     * 
     */
    public BSPModel[]       models;
    
    /**
     * 
     */
    public NamedObject[][]  geometries;
    
    /**
     * 
     */
    public BSPVertex[]      vertices;
    
    /**
     * 
     */
    public int[]            meshVertices;
    
    /**
     * Half-Life only Lump
     */
    public BSPEdge[]    edges;
    
    /**
     * Half-Life only Lump
     */
    public int[]    surfEdges;
    
    /**
     * 
     */
    public BSPFace[]        faces;
    
    public byte[]           lightMapData;
    
    /**
     * 
     */
    public AbstractTexture[]  lightMaps;
    
    /**
     * 
     */
    public BSPBrush[]       brushes;
    
    /**
     * 
     */
    public BSPBrushSide[]   brushSides;
    
    /**
     * 
     */
    public BSPVisData       visData;
    
    public BSPScenePrototype( int sourceBSPVersion )
    {
        this.sourceBSPVersion = sourceBSPVersion;
    }
}