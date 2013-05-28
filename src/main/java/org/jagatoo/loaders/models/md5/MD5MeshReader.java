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
package org.jagatoo.loaders.models.md5;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.util.strings.SimpleStringTokenizer;
import org.jagatoo.util.strings.StringUtils;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;
import org.openmali.vecmath2.util.FloatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Loads MD5MESH files and pushes them through JAGaToo's factory interfaces
 * to generate model data on the implementing scenegraph's side.
 * 
 * Some basic ideas are taken from The kman's MD5 loader.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD5MeshReader
{
    private class MD5Bone
    {
        @SuppressWarnings( "unused" )
        public final String name;
        public final Vector3f translation;
        public final Quaternion4f rotation;
        
        public MD5Bone( String name, Vector3f translation, Quaternion4f rotation )
        {
            this.name = name;
            this.translation = translation;
            this.rotation = rotation;
        }
    }
    
    private class MD5Mesh
    {
        @SuppressWarnings( "unused" )
        public String name;
        
        public NamedObject shader;
        
        public int numVertices;
        public int numTriangles;
        
        public int[] boneWeightBones = null;
        public float[] boneWeightWeights = null;
        public Vector3f[] boneWeightOffsets = null;
        public int[] firstWeights = null;
        public int[] weightCounts = null;
        
        public Object[][] boneWeights = null;
        
        public int[] triangles = null;
        
        public NamedObject geom = null;
    }
    
    private static final GeometryType GEOM_TYPE = GeometryType.INDEXED_TRIANGLE_ARRAY;
    
    private final HashMap<String, NamedObject> shaderCache = new HashMap<String, NamedObject>();
    
    private MD5Bone[] skeleton;
    private MD5Mesh[] meshes;
    
    private MD5Bone parseBone( SimpleStringTokenizer st, boolean convertZup2Yup )
    {
        String boneName = StringUtils.unquoteString( st.getLastToken() );
        st.skipToken(); // skip parent bone-index
        
        st.skipToken(); // skip "("
        
        Vector3f translation = new Vector3f();
        translation.setX( Float.parseFloat( st.nextToken() ) );
        translation.setY( Float.parseFloat( st.nextToken() ) );
        translation.setZ( Float.parseFloat( st.nextToken() ) );
        
        st.skipToken(); // skip ")"
        st.skipToken(); // skip "("
        
        float q1 = Float.parseFloat( st.nextToken() );
        float q2 = Float.parseFloat( st.nextToken() );
        float q3 = Float.parseFloat( st.nextToken() );
        Quaternion4f rotation = new Quaternion4f( q1, q2, q3, 0f ).computeD();
        
        //str.nextToken(); // skip ")"
        
        if ( convertZup2Yup )
        {
            //rotation.mul( Quaternion4f.Z_UP_TO_Y_UP, rotation );
        }
        
        return ( new MD5Bone( boneName, translation, rotation ) );
    }
    
    private NamedObject createShader( String shaderName, AppearanceFactory appFactory, URL baseURL )
    {
        if ( shaderName.startsWith( ":\\", 1 ) )
        {
            shaderName = "file://" + shaderName.replace( '\\', '/' );
        }
        else
        {
            shaderName = shaderName.replace( '\\', '/' );
        }
        
        NamedObject shader = shaderCache.get( shaderName );
        
        if ( shader != null )
        {
            return ( shader );
        }
        
        shader = appFactory.createStandardAppearance( shaderName, shaderName, baseURL, 0 );
        
        shaderCache.put( shaderName, shader );
        
        return ( shader );
    }
    
    private MD5Mesh compileMesh( String meshName, NamedObject shader, ArrayList< String > vertDefs, ArrayList< String > triDefs, ArrayList< String > weightDefs, GeometryFactory geomFactory, float scale, AnimationFactory animFactory )
    {
        MD5Mesh result = new MD5Mesh();
        
        result.name = meshName;
        result.shader = shader;
        
        result.numVertices = vertDefs.size();
        result.numTriangles = triDefs.size();
        
        result.geom = geomFactory.createGeometry( meshName, GEOM_TYPE, 3, vertDefs.size(), triDefs.size() * 3, null );
        
        result.boneWeightBones = new int[ weightDefs.size() ];
        result.boneWeightWeights = new float[ weightDefs.size() ];
        result.boneWeightOffsets = new Vector3f[ weightDefs.size() ];
        
        SimpleStringTokenizer st = new SimpleStringTokenizer( "" );
        
        for ( int i = 0; i < weightDefs.size(); i++ )
        {
            st.setString( weightDefs.get( i ) );
            
            st.skipToken(); // skip index
            
            int bone = Integer.parseInt( st.nextToken() );
            float weight = Float.parseFloat( st.nextToken() );
            
            st.skipToken();
            
            Vector3f offset = new Vector3f();
            offset.setX( Float.parseFloat( st.nextToken() ) );
            offset.setY( Float.parseFloat( st.nextToken() ) );
            offset.setZ( Float.parseFloat( st.nextToken() ) );
            
            result.boneWeightBones[i] = bone;
            result.boneWeightWeights[i] = weight * scale;
            result.boneWeightOffsets[i] = offset;
            
            //str.nextToken();
        }
        
        result.firstWeights = new int[ vertDefs.size() ];
        result.weightCounts = new int[ vertDefs.size() ];
        
        Object[][] boneWeights = null;
        
        for ( int i = 0; i < vertDefs.size(); i++ )
        {
            st.setString( vertDefs.get( i ) );
            
            st.skipToken(); // skip index
            
            st.skipToken();
            
            float s = Float.parseFloat( st.nextToken() );
            float t = 1.0f - Float.parseFloat( st.nextToken() );
            geomFactory.setTexCoord( result.geom, GEOM_TYPE, 0, i, s, t );
            
            st.skipToken();
            
            int firstWeight = Integer.parseInt( st.nextToken() );
            int weightCount = Integer.parseInt( st.nextToken() );
            
            result.firstWeights[i] = firstWeight;
            result.weightCounts[i] = weightCount;
            
            if ( boneWeights != null )
            {
                boneWeights[i] = null;
            }
            
            for ( int w = 0; w < weightCount; w++ )
            {
                Object bw = animFactory.createBoneWeight( result.boneWeightBones[firstWeight + w], result.boneWeightWeights[firstWeight + w], result.boneWeightOffsets[firstWeight + w] );
                
                if ( boneWeights == null )
                {
                    Object[] bws = (Object[])Array.newInstance( bw.getClass(), weightCount );
                    
                    boneWeights = (Object[][])Array.newInstance( bws.getClass(), result.numVertices );
                    
                    boneWeights[i] = bws;
                }
                else if ( boneWeights[i] == null )
                {
                    boneWeights[i] = (Object[])Array.newInstance( bw.getClass(), weightCount );
                }
                
                boneWeights[i][w] = bw;
            }
        }
        
        result.boneWeights = boneWeights;
        
        int[] triangles = new int[ result.numTriangles * 3 ];
        
        for ( int i = 0; i < triDefs.size(); i++ )
        {
            st.setString( triDefs.get( i ) );
            
            st.skipToken(); // skip index
            
            triangles[i * 3 + 0] = Integer.parseInt( st.nextToken() );
            triangles[i * 3 + 1] = Integer.parseInt( st.nextToken() );
            triangles[i * 3 + 2] = Integer.parseInt( st.nextToken() );
        }
        
        result.triangles = triangles;
        
        geomFactory.setIndex( result.geom, GEOM_TYPE, 0, triangles, 0, triangles.length );
        
        return ( result );
    }
    
    private void readMeshFile( InputStream in, URL baseURL, String skin, AppearanceFactory appFactory, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, AnimationFactory animFactory ) throws IOException
    {
        int numJoints = 0;
        int numMeshes = 0;
        
        MD5Bone[] skeleton = null;
        ArrayList<MD5Mesh> meshes = null;
        
        BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
        String line;
        SimpleStringTokenizer st = new SimpleStringTokenizer( "" );
        String token = null;
        while ( ( line = br.readLine() ) != null )
        {
            st.setString( line );
            token = st.nextToken();
            
            if ( token == null )
            {
                continue;
            }
            
            if ( token.equals( "MD5Version" ) )
            {
                int version = Integer.parseInt( st.nextToken() );
                if ( version != 10 )
                {
                    br.close();
                    
                    throw new IncorrectFormatException( "MD5 version " + version + " is not supported. Expected 10." );
                }
            }
            /*
            else if ( token.equals( "commandline" ) )
            {
                // ignored!
            }
            */
            else if ( token.equals( "numJoints" ) )
            {
                numJoints = Integer.parseInt( st.nextToken() );
            }
            else if ( token.equals( "numMeshes" ) )
            {
                numMeshes = Integer.parseInt( st.nextToken() );
                meshes = new ArrayList<MD5Mesh>( numMeshes );
            }
            else if ( token.equals( "joints" ) )
            {
                ArrayList< MD5Bone > bones = new ArrayList< MD5Bone >( numJoints );
                
                while ( ( line = br.readLine() ) != null )
                {
                    st.setString( line );
                    token = st.nextToken();
                    
                    if ( token == null )
                    {
                        continue;
                    }
                    
                    if ( token.equals( "}" ) )
                    {
                        break;
                    }
                    
                    bones.add( parseBone( st, convertZup2Yup ) );
                }
                
                skeleton = bones.toArray( new MD5Bone[ bones.size() ] );
            }
            else if ( token.equals( "mesh" ) )
            {
                String meshName = "";
                NamedObject shader = null;
                int numVertices = 0;
                int numTriangles = 0;
                int numWeights = 0;
                
                ArrayList< String > vertDefs = null;
                ArrayList< String > triDefs = null;
                ArrayList< String > weightDefs = null;
                
                // parse mesh-name
                {
                    meshName = st.nextToken();
                    if ( meshName.equals( "{" ) )
                        meshName = "";
                }
                
                while ( ( line = br.readLine() ) != null )
                {
                    st.setString( line );
                    token = st.nextToken();
                    
                    if ( token == null )
                    {
                        continue;
                    }
                    
                    if ( token.equals( "numverts" ) )
                    {
                        numVertices = Integer.parseInt( st.getRest() );
                        vertDefs = new ArrayList< String >( numVertices );
                    }
                    else if ( token.equals( "vert" ) )
                    {
                        vertDefs.add( st.getRest() );
                    }
                    else if ( token.equals( "numtris" ) )
                    {
                        numTriangles = Integer.parseInt( st.getRest() );
                        triDefs = new ArrayList< String >( numTriangles );
                    }
                    else if ( token.equals( "tri" ) )
                    {
                        triDefs.add( st.getRest() );
                    }
                    else if ( token.equals( "numweights" ) )
                    {
                        numWeights = Integer.parseInt( st.getRest() );
                        weightDefs = new ArrayList< String >( numWeights );
                    }
                    else if ( token.equals( "weight" ) )
                    {
                        weightDefs.add( st.getRest() );
                    }
                    else if ( token.equals( "shader" ) )
                    {
                        String shaderName = st.getUnquotedRest();
                        
                        if ( skin == null )
                        {
                            shader = createShader( shaderName, appFactory, baseURL );
                        }
                    }
                    else if ( token.equals( "}" ) )
                    {
                        break;
                    }
                }
                
                meshes.add( compileMesh( meshName, shader, vertDefs, triDefs, weightDefs, geomFactory, scale, animFactory ) );
            }
        }
        
        br.close();
        
        this.skeleton = skeleton;
        this.meshes = meshes.toArray( new MD5Mesh[ meshes.size() ] );
    }
    
    private static void computeNormals( float[] coords, int[] triangles, NamedObject geom, GeometryFactory geomFactory )
    {
        int i3;
        float coordAx;
        float coordAy;
        float coordAz;
        float coordBx;
        float coordBy;
        float coordBz;
        float coordCx;
        float coordCy;
        float coordCz;
        
        float vecACx;
        float vecACy;
        float vecACz;
        float vecABx;
        float vecABy;
        float vecABz;
        
        Vector3f tmp = new Vector3f();
        
        int numTriangles = triangles.length / 3;
        // Go though all of the faces of this object
        for ( int i = 0; i < numTriangles; i++ )
        {
            i3 = i * 3;
            coordAx = coords[triangles[i3 + 0] * 3 + 0];
            coordAy = coords[triangles[i3 + 0] * 3 + 1];
            coordAz = coords[triangles[i3 + 0] * 3 + 2];
            coordBx = coords[triangles[i3 + 1] * 3 + 0];
            coordBy = coords[triangles[i3 + 1] * 3 + 1];
            coordBz = coords[triangles[i3 + 1] * 3 + 2];
            coordCx = coords[triangles[i3 + 2] * 3 + 0];
            coordCy = coords[triangles[i3 + 2] * 3 + 1];
            coordCz = coords[triangles[i3 + 2] * 3 + 2];
            
            vecACx = coordCx - coordAx;
            vecACy = coordCy - coordAy;
            vecACz = coordCz - coordAz;
            vecABx = coordBx - coordAx;
            vecABy = coordBy - coordAy;
            vecABz = coordBz - coordAz;
            
            FloatUtils.cross( vecACx, vecACy, vecACz, vecABx, vecABy, vecABz, tmp );
            tmp.normalize();
            
            geomFactory.setNormal( geom, GEOM_TYPE, triangles[i3 + 0], tmp.getX(), tmp.getY(), tmp.getZ() );
            geomFactory.setNormal( geom, GEOM_TYPE, triangles[i3 + 1], tmp.getX(), tmp.getY(), tmp.getZ() );
            geomFactory.setNormal( geom, GEOM_TYPE, triangles[i3 + 2], tmp.getX(), tmp.getY(), tmp.getZ() );
        }
    }
    
    private static void computeTriMesh( MD5Mesh mesh, MD5Bone[] bones, GeometryFactory geomFactory )
    {
        float[] coords = new float[ mesh.numVertices * 3 ];
        Point3f tmp = new Point3f();
        
        for ( int i = 0; i < mesh.numVertices; i++ )
        {
            coords[i * 3 + 0] = 0f;
            coords[i * 3 + 1] = 0f;
            coords[i * 3 + 2] = 0f;
            
            // calculate final vertex to draw with weights
            for ( int j = 0; j < mesh.weightCounts[i]; j++ )
            {
                int weightIndex = mesh.firstWeights[i] + j;
                MD5Bone bone = bones[mesh.boneWeightBones[weightIndex]];
                
                bone.rotation.transform( mesh.boneWeightOffsets[weightIndex], tmp );
                float weight = mesh.boneWeightWeights[weightIndex];
                tmp.add( bone.translation );
                tmp.mul( weight );
                Quaternion4f.Z_UP_TO_Y_UP.transform( tmp );
                coords[i * 3 + 0] += tmp.getX();
                coords[i * 3 + 1] += tmp.getY();
                coords[i * 3 + 2] += tmp.getZ();
            }
        }
        
        geomFactory.setCoordinates( mesh.geom, GEOM_TYPE, 0, coords, 0, mesh.numVertices );
        
        computeNormals( coords, mesh.triangles, mesh.geom, geomFactory );
        
        geomFactory.finalizeGeometry( mesh.geom, GEOM_TYPE, 0, mesh.numVertices, 0, mesh.triangles.length );
    }
    
    public static Object[][][] load( InputStream in, URL baseURL, AppearanceFactory appFactory, String skin, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        MD5MeshReader reader = new MD5MeshReader();
        
        reader.readMeshFile( in, baseURL, skin, appFactory, geomFactory, convertZup2Yup, scale, animFactory );
        
        Object[][][] weights = new Object[ reader.meshes.length ][][];
        
        for ( int m = 0; m < reader.meshes.length; m++ )
        {
            MD5Mesh mesh = reader.meshes[m];
            
            //String meshName = ( ( mesh.name == null ) || mesh.name.equals( "" ) ) ? "MD5Mesh" + m : mesh.name;
            // TODO: The name should be set to the geometry or shape
            
            computeTriMesh( mesh, reader.skeleton, geomFactory );
            NamedObject shader = mesh.shader;
            if ( skin != null )
            {
                shader = reader.createShader( skin, appFactory, baseURL );
            }
            NamedObject shape = nodeFactory.createShape( mesh.geom.getName(), mesh.geom, shader, BoundsType.SPHERE );
            
            siHandler.addSpecialItem( SpecialItemType.SHAPE, shape.getName(), shape );
            
            nodeFactory.addNodeToGroup( shape, rootGroup );
            
            weights[m] = mesh.boneWeights;
        }
        
        return ( weights );
    }
}
