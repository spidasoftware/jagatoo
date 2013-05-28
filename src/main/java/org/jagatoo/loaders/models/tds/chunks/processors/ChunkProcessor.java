/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
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
package org.jagatoo.loaders.models.tds.chunks.processors;

import java.io.IOException;
import java.util.HashMap;

import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.tds.TDSFile;
import org.jagatoo.loaders.models.tds.chunks.TDSChunkType;
import org.jagatoo.loaders.models.tds.internal.ModelContext;

/**
 * An object to process a single chunk from the 3DS file
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ChunkProcessor
{
    private static HashMap< Integer, ChunkProcessor > chunkProcessors = new HashMap< Integer, ChunkProcessor >();
    
    protected static final void storeChunkProcessorSingleton( TDSChunkType ct, ChunkProcessor cp )
    {
        chunkProcessors.put( ct.getID(), cp );
    }
    
    static
    {
        storeChunkProcessorSingleton( TDSChunkType.MESH_MATRIX, new MeshMatrixProcessor() );
        
        storeChunkProcessorSingleton( TDSChunkType.POINT_ARRAY, new PointArrayProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.FACE_ARRAY, new FaceArrayProcessor() );
        MagicProcessor magicProc = new MagicProcessor();
        storeChunkProcessorSingleton( TDSChunkType.N_TRI_OBJECT, magicProc );
        storeChunkProcessorSingleton( TDSChunkType.M3DMAGIC, magicProc );
        storeChunkProcessorSingleton( TDSChunkType.MMAGIC, magicProc );
        storeChunkProcessorSingleton( TDSChunkType.HIERARCHY_NODE, magicProc );
        storeChunkProcessorSingleton( TDSChunkType.MAT_ENTRY, magicProc );
        
        storeChunkProcessorSingleton( TDSChunkType.HIERARCHY_FRAMES, new HFramesProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.HIERARCHY_HEADER, new HierarchyHeaderProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.HIERARCHY_LINK, new HLinkProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.HIERARCHY, new HierarchyProcessor() );
        
        storeChunkProcessorSingleton( TDSChunkType.INSTANCE_NAME, new InstanceNameProcessor() );
        
        storeChunkProcessorSingleton( TDSChunkType.NAMED_OBJECT, new NamedObjectProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.NODE_ID, new NodeIDProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.PIVOT, new PivotProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.POS_TRACK_TAG, new PosTrackProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.ROT_TRACK_TAG, new RotTrackProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.SCL_TRACK_TAG, new SclTrackProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.SMOOTH_GROUP, new SmoothGroupProcessor() );
        
        storeChunkProcessorSingleton( TDSChunkType.TEX_VERTS, new TexCoordProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_TEXMAP, new TexMapProcessor() );
        
        storeChunkProcessorSingleton( TDSChunkType.MAT_AMBIENT, new AmbientProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_DIFFUSE, new DiffuseProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_SPECULAR, new SpecularProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_SHININESS, new ShininessProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_SHININESS_STRENGTH, new ShininessStrengthProcessor() );
        storeChunkProcessorSingleton( TDSChunkType.MAT_TRANSPARENCY, new TransparencyProcessor() );
        MeshMaterialProcessor matProc = new MeshMaterialProcessor();
        storeChunkProcessorSingleton( TDSChunkType.MSH_MAT_GROUP, matProc );
        //storeChunkProcessorSingleton( TDSChunkType.MAT_NAME, matProc );
        MaterialProcessor meshMatProc = new MaterialProcessor();
        storeChunkProcessorSingleton( TDSChunkType.MAT_NAME, meshMatProc );
        //storeChunkProcessorSingleton( TDSChunkType.MSH_MAT_GROUP, meshMatProc );
        storeChunkProcessorSingleton( TDSChunkType.MAT_TWO_SIDE, new TwoSideProcessor() );
    }
    
    public static final ChunkProcessor getChunkProcessor( int id )
    {
        return ( chunkProcessors.get( id ) );
    }
    
    public static final ChunkProcessor getChunkProcessor( TDSChunkType chunkType )
    {
        return ( chunkProcessors.get( chunkType.getID() ) );
    }
    
    public abstract void process( TDSFile file, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, ModelContext context, int length ) throws IOException;
}
