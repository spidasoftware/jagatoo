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
package org.jagatoo.loaders.models.tds.chunks;

import java.util.HashMap;

/**
 * The IDs of all the chunks support
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public enum TDSChunkType
{
    M3DMAGIC( 0x4d4d ),
    MMAGIC( 0x3d3d ),
    MESH_VERSION( 0x0001 ),
    M3D_VERSION( 0x0002 ),
    
    COLOR_F( 0x0010 ),
    COLOR_24( 0x0011 ),
    INT_PERCENTAGE( 0x0030 ),
    FLOAT_PERCENTAGE( 0x0031 ),
    
    MASTER_SCALE( 0x0100 ),
    
    BIT_MAP( 0x1100 ),
    USE_BIT_MAP( 0x1101 ),
    SOLID_BGND( 0x1200 ),
    USE_SOLID_BGND( 0x1201 ),
    V_GRADIENT( 0x1300 ),
    USE_V_GRADIENT( 0x1301 ),
    
    LO_SHADOW_BIAS( 0x1400 ),
    HI_SHADOW_BIAS( 0x1410 ),
    SHADOW_MAP_SIZE( 0x1420 ),
    SHADOW_SAMPLES( 0x1430 ),
    SHADOW_RANGE( 0x1440 ),
    
    AMBIENT_LIGHT( 0x2100 ),
    
    FOG( 0x2200 ),
    USE_FOG( 0x2201 ),
    FOG_BGND( 0x2210 ),
    DISTANCE_CUE( 0x2300 ),
    USE_DISTANCE_CUE( 0x2301 ),
    DCUE_BGND( 0x2310 ),
    
    DEFAULT_VIEW( 0x3000 ),
    VIEW_TOP( 0x3010 ),
    VIEW_BOTTOM( 0x3020 ),
    VIEW_LEFT( 0x3030 ),
    VIEW_RIGHT( 0x3040 ),
    VIEW_FRONT( 0x3050 ),
    VIEW_BACK( 0x3060 ),
    VIEW_USER( 0x3070 ),
    VIEW_CAMERA( 0x3080 ),
    VIEW_WINDOW( 0x3090 ),
    
    NAMED_OBJECT( 0x4000 ),
    OBJ_HIDDEN( 0x4010 ),
    OBJ_VIS_LOFTER( 0x4011 ),
    OBJ_DOESNT_CAST( 0x4012 ),
    OBJ_MATTE( 0x4013 ),
    
    N_TRI_OBJECT( 0x4100 ),
    
    POINT_ARRAY( 0x4110 ),
    POINT_FLAG_ARRAY( 0x4111 ),
    FACE_ARRAY( 0x4120 ),
    MSH_MAT_GROUP( 0x4130 ),
    TEX_VERTS( 0x4140 ),
    SMOOTH_GROUP( 0x4150 ),
    MESH_MATRIX( 0x4160 ),
    
    N_DIRECT_LIGHT( 0x4600 ),
    DL_SPOTLIGHT( 0x4610 ),
    DL_OFF( 0x4620 ),
    DL_SHADOWED( 0x4630 ),
    N_CAMERA( 0x4700 ),
    
    // Material file Chunk IDs
    MAT_ENTRY( 0xafff ),
    MAT_NAME( 0xa000 ),
    MAT_AMBIENT( 0xa010 ),
    MAT_DIFFUSE( 0xa020 ),
    MAT_SPECULAR( 0xa030 ),
    MAT_SHININESS( 0xa040 ),
    MAT_SHININESS_STRENGTH( 0xa041 ),
    MAT_TRANSPARENCY( 0xa050 ),
    MAT_WIRE( 0xa085 ),
    MAT_WIRESIZE( 0xa087 ),
    MAT_SELF_ILLUM( 0xa080 ),
    MAT_TWO_SIDE( 0xa081 ),
    MAT_DECAL( 0xa082 ),
    MAT_ADDITIVE( 0xa083 ),
    MAT_SHADING( 0xa100 ),
    MAT_TEXMAP( 0xa200 ),
    MAT_OPACMAP( 0xa210 ),
    MAT_REFLMAP( 0xa220 ),
    MAT_BUMPMAP( 0xa230 ),
    MAT_MAPNAME( 0xa300 ),
    
    // Reverse engineered hierarchy information
    HIERARCHY( 0xb000 ),
    HIERARCHY_HEADER( 0xb00a ),
    HIERARCHY_NODE( 0xb002 ),
    HIERARCHY_FRAMES( 0xb008 ),
    CURRENT_FRAME( 0xb009 ),
    HIERARCHY_LINK( 0xb010 ),
    INSTANCE_NAME( 0xb011 ),
    PIVOT( 0xb013 ),
    POS_TRACK_TAG( 0xb020 ),
    ROT_TRACK_TAG( 0xb021 ),
    SCL_TRACK_TAG( 0xb022 ),
    NODE_ID( 0xb030 ),
    OBJECT_LINK_NULL( 0xffff ),
    
    // Dummy Chunk ID
    DUMMY_CHUNK( 0xffff ),
    
    // These chunks are found in the .PRJ file (only as far as I know)
    PROJECT_FILE( 0xc23d ),
    MAPPING_RETILE( 0xc4b0 ),
    MAPPING_CENTRE( 0xc4c0 ),
    MAPPING_SCALE( 0xc4d0 ),
    MAPPING_ORIENTATION( 0xc4e1 ),
    
    ;
    
    private static final class StaticMembersWrapper
    {
        private static final HashMap< Integer, TDSChunkType > reverseMap = new HashMap< Integer, TDSChunkType >();
    }
    
    private final int id;
    
    public final int getID()
    {
        return ( id );
    }
    
    public static final TDSChunkType valueOf( int id )
    {
        return ( StaticMembersWrapper.reverseMap.get( id ) );
    }
    
    private TDSChunkType( int id )
    {
        this.id = id;
        
        StaticMembersWrapper.reverseMap.put( id, this );
    }
}
