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

import java.util.BitSet;

import org.jagatoo.loaders.models.bsp.lumps.BSPLeaf;
import org.jagatoo.loaders.models.bsp.lumps.BSPNode;
import org.jagatoo.loaders.models.bsp.lumps.BSPPlane;
import org.jagatoo.loaders.models.bsp.lumps.BSPVisData;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.util.FloatUtils;

/**
 * This class is used to manage the visibility of all the BSP clusters.
 * 
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class BSPClusterManager implements BSPVisibilityUpdater
{
    private final BSPVisData       bspVisData;
    private final boolean          hasVisBitset;
    
    protected final BitSet         shapeBitset;
    private final int[][][]        clusterLeafs;
    private final int[]            leafToCluster;
    private final float[]          planes;
    private final int[]            nodes;
    
    private   boolean        usePVS = true;
    private   boolean        lastUsePVS = true;
    
    public final BitSet getBitSet()
    {
        return ( shapeBitset );
    }
    
    /**
     * Calculates which cluster the camera position is in
     * 
     * @param camPos the position of the camera (View)
     */
    private int getCluster( Tuple3f camPos )
    {
        int index = 0;
        
        while ( index >= 0 )
        {
            int node = index * 3;
            int planeIndex = nodes[ node + 0 ] * 4;
            float nx = planes[ planeIndex + 0 ];
            float ny = planes[ planeIndex + 1 ];
            float nz = planes[ planeIndex + 2 ];
            float d = planes[ planeIndex + 3 ];
            
            // Distance from point to a plane
            float dot = FloatUtils.dot( nx, ny, nz, camPos.getX(), camPos.getY(), camPos.getZ() );
            float distance = dot - d;
            
            if ( distance > 0.0001f )
                index = nodes[ node + 1 ];
            else
                index = nodes[ node + 2 ];
        }
        
        return ( leafToCluster[ -( index + 1 ) ] );
    }
    
    private final boolean isClusterVisible( int camCluster, int testCluster )
    {
        int i = ( camCluster * bspVisData.bytesPerCluster ) + ( testCluster / 8 );
        
        return ( ( bspVisData.pBitsets[ i ] & ( 1 << ( testCluster & 0x07 ) ) ) != 0 );
        
        /*
        int i = ( camCluster * bspVisData.bytesPerCluster * 8 ) + testCluster;
        
        return ( visData[ i ] );
        */
    }
    
    /**
     * Enables or disables PVS usage.
     * 
     * @param enabled if true the PVS is used to disable hidden geometry, false and everything is enabled
     */
    public void setPVSUsage( boolean enabled )
    {
        this.usePVS = enabled;
    }
    
    /**
     * @return <b>true</b>, if the PVS is used to disable hidden geometry. <b>false</b>, and everything is enabled
     */
    public boolean isPVSUsed()
    {
        return ( usePVS );
    }
    
    private int lastCluster = -2;
    
    /**
     * Disables geometry that is invisible according to the PVS
     * 
     * @param camPos the position of the camera
     * 
     * @return true, if PVS has changed.
     */
    public boolean updateVisibility( Matrix4f cameraTransform )
    {
        Point3f camPos = Point3f.fromPool();
        
        cameraTransform.get( camPos );
        
        boolean usePVSChanged = ( usePVS != lastUsePVS );
        
        lastUsePVS = usePVS;
        
        if ( !usePVS )
        {
            shapeBitset.set( 0, shapeBitset.size() - 1 );
            
            Point3f.toPool( camPos );
            
            return ( true );
        }
        
        int camCluster = getCluster( camPos );
        if ( lastCluster == camCluster && !usePVSChanged )
        {
            Point3f.toPool( camPos );
            
            return ( false );
        }
        
        lastCluster = camCluster;
        
        boolean allClustersVisible = !hasVisBitset || ( camCluster < 0 );
        
        shapeBitset.clear();
        
        for ( int i = 0; i < bspVisData.numOfClusters; i++ )
        {
            int[][] clusterLeafs_i = clusterLeafs[ i ];
            
            if ( ( clusterLeafs_i != null ) && ( allClustersVisible || isClusterVisible( camCluster, i ) ) )
            {
                for ( int j = 0; j < clusterLeafs_i.length; j++ )
                {
                    int[] clusterLeafs_i_j = clusterLeafs_i[ j ];
                    
                    if ( clusterLeafs_i_j.length > 0 )
                    {
                        for ( int k = 0; k < clusterLeafs_i_j.length; k++ )
                        {
                            int clusterLeaf = clusterLeafs_i_j[ k ];
                            
                            if ( !shapeBitset.get( clusterLeaf ) )
                            {
                                shapeBitset.set( clusterLeaf );
                            }
                        }
                    }
                }
            }
        }
        
        Point3f.toPool( camPos );
        
        return ( true );
    }
    
    /*
    private static final BitSet generateBitSet( byte[] visData )
    {
        BitSet bitset = new BitSet( visData.length * 8 );
        
        for ( int byteIndex = 0; byteIndex < visData.length; byteIndex++ )
        {
            int bitIndex0 = byteIndex * 8;
            byte byt = visData[ byteIndex ];
            
            for ( int bitIndex = 0; bitIndex < 8; bitIndex++ )
            {
                boolean bitValue = ( byt & ( 1 << bitIndex ) ) != 0;
                
                bitset.set( bitIndex0 + bitIndex, bitValue );
            }
        }
        
        return ( bitset );
    }
    */
    
    /*
    private static final boolean[] decompressVisData( byte[] compressed )
    {
        boolean[] decompressed = new boolean[ compressed.length * 8 ];
        
        for ( int byteIndex = 0; byteIndex < compressed.length; byteIndex++ )
        {
            int bitIndex0 = byteIndex * 8;
            byte byt = compressed[ byteIndex ];
            
            for ( int bitIndex = 0; bitIndex < 8; bitIndex++ )
            {
                boolean bitValue = ( byt & ( 1 << bitIndex ) ) != 0;
                
                decompressed[bitIndex0 + bitIndex] = bitValue;
            }
        }
        
        return ( decompressed );
    }
    */
    
    public BSPClusterManager( BSPVisData bspVisData, int[][][] clusterLeafs, int[] leafToCluster, float[] planes, int[] nodes, BitSet shapeBitset )
    {
        this.bspVisData     = bspVisData;
        this.hasVisBitset   = ( bspVisData.pBitsets != null );
        this.clusterLeafs   = clusterLeafs;
        this.leafToCluster  = leafToCluster;
        this.planes         = planes;
        this.nodes          = nodes;
        this.shapeBitset    = shapeBitset;
        
        //System.out.println( bspVisData.pBitsets.length + ", " + bspVisData.bytesPerCluster + ", " + bspVisData.numOfClusters );
        //System.out.println( clusterLeafs.length + ", " + leafToCluster.length + ", " + planes.length );
    }
    
    public BSPClusterManager( BSPClusterManager template )
    {
        this.bspVisData     = template.bspVisData;
        this.hasVisBitset   = template.hasVisBitset;
        this.clusterLeafs   = template.clusterLeafs;
        this.leafToCluster  = template.leafToCluster;
        this.planes         = template.planes;
        this.nodes          = template.nodes;
        this.shapeBitset    = template.shapeBitset;
    }
    
    
    /**
     * converts the nodes for the BSP
     */
    private static int[] convertNodes( BSPNode[] bspNodes )
    {
        int[] nodes = new int[ bspNodes.length * 3 ];
        for ( int i = 0; i < bspNodes.length; i++ )
        {
            final int j = i * 3;
            nodes[ j + 0 ] = bspNodes[ i ].plane;
            nodes[ j + 1 ] = bspNodes[ i ].front;
            nodes[ j + 2 ] = bspNodes[ i ].back;
        }
        
        return ( nodes );
    }
    
    /**
     * Takes all the faces in the leaf and adds them to the cluster
     * 
     * @param leaf 
     */
    private static int[] convertLeaf( BSPLeaf leaf, int[] leafFaces, int[][][] clusterLeafs, int[] clusterLeaf )
    {
        if ( leaf.numOfLeafFaces == 0 )
        {
            //if (Xith3DDefaults.getLocalDebug( BSPLoader.DEBUG ))
            //System.out.println( "no faces, but " + leaf.numOfLeafBrushes + " brushes" );
            return ( new int[] {} );
        }
        
        int[] leafFaces2 = new int[ leaf.numOfLeafFaces ];
        for ( int i = 0; i < leaf.numOfLeafFaces; i++ )
        {
            leafFaces2[ i ] = leafFaces[ i + leaf.leafFace ];
        }
        
        clusterLeaf[ leaf.cluster ]++;
        
        if ( clusterLeafs[ leaf.cluster ] == null )
        {
            clusterLeafs[ leaf.cluster ] = new int[ 1 ][];
        }
        else
        {
            int[][] tmp = new int[ clusterLeafs[ leaf.cluster ].length + 1 ][];
            System.arraycopy( clusterLeafs[ leaf.cluster ], 0, tmp, 0, clusterLeafs[ leaf.cluster ].length );
            clusterLeafs[ leaf.cluster ] = tmp;
            
        }
        
        clusterLeafs[ leaf.cluster ][ clusterLeafs[ leaf.cluster ].length - 1 ] = leafFaces2;
        
        return ( leafFaces2 );
        
        //if (Xith3DDefaults.getLocalDebug( BSPLoader.DEBUG ))
        //System.out.println( "there are " + smap.size() + " unique textures in " + leaf.numOfLeafFaces + " faces" );
    }
    
    /**
     * converts the planes for the BSP
     */
    private static float[] convertPlanes( BSPPlane[] bspPlanes )
    {
        // now convert the planes
        float[] planes = new float[ bspPlanes.length * 4 ];
        for ( int i = 0; i < bspPlanes.length; i++ )
        {
            final int j = i * 4;
            //planes[i].normal.normalize();
            planes[ j + 0 ] = bspPlanes[ i ].normal.getX();
            planes[ j + 1 ] = bspPlanes[ i ].normal.getZ();
            planes[ j + 2 ] = -bspPlanes[ i ].normal.getY();
            planes[ j + 3 ] = bspPlanes[ i ].d;
        }
        
        return ( planes );
    }
    
    /**
     * Creates a BSPClusterManager from the visdata contained in the prototype.
     * 
     * @param prototype
     * @param faceBitset
     */
    public static BSPClusterManager create( BSPScenePrototype prototype, BitSet faceBitset )
    {
        int[] clusterLeaf = new int[ prototype.leafs.length ];
        int[][][] clusterLeafs = new int[ prototype.visData.numOfClusters ][][];
        
        int[][] leafs = new int[ prototype.leafs.length ][];
        int[] leafToCluster = new int[ prototype.leafs.length ];
        
        for ( int i = 0; i < prototype.leafs.length; i++ )
        {
            leafs[ i ] = convertLeaf( prototype.leafs[ i ], prototype.leafFaces, clusterLeafs, clusterLeaf );
            leafToCluster[ i ] = prototype.leafs[ i ].cluster;
        }
        
        JAGTLog.debug( "Converting nodes..." );
        
        int[] nodes = convertNodes( prototype.nodes );
        float[] planes = convertPlanes( prototype.planes );
        
        
        int numLeaves = 0;
        for ( int i = 0; i < clusterLeaf.length; i++ )
            numLeaves += clusterLeaf[ i ];
        
        JAGTLog.debug( "total referenced leaves = ", numLeaves );
        JAGTLog.debug( "total leaves = ", prototype.leafs.length );
        JAGTLog.debug( "total faces = ", prototype.geometries[0].length );
        
        return ( new BSPClusterManager( prototype.visData, clusterLeafs, leafToCluster, planes, nodes, faceBitset ) );
    }
}
