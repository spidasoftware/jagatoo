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
package org.jagatoo.loaders.models.md2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.spatial.bounds.BoundsType;

/**
 * A Loader to load Quake 2 model MD2 model files.
 * 
 * If the texture filename is not provided, assumes that it is the same as the
 * MD2 filename but with the filename extension ".jpg", e.g., example.md2 and
 * example.jpg.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2File
{
    private final LittleEndianDataInputStream in;
    
    private final MD2Header header;
    
    private final HashMap<String, NamedObject> skinsCache = new HashMap<String, NamedObject>();
    
    static final String fixPath( String path )
    {
        if ( path.indexOf( '\\' ) >= 0 )
        {
            path = path.replace( '\\', '/' );
        }
        
        if ( path.indexOf( ' ' ) >= 0 )
        {
            path = path.replace( " ", "%20" );
        }
        
        return ( path );
    }
    
    private AbstractTexture loadTexture( String texName, URL baseURL, AppearanceFactory appFactory )
    {
        // First try to load the texture by the given name...
        AbstractTexture texture = appFactory.loadOrGetTexture( texName, baseURL, true, true, true, true, true );
        
        if ( !appFactory.isFallbackTexture( texture ) )
            return ( texture );
        
        int lastSlashPos = texName.lastIndexOf( '/' );
        if ( lastSlashPos >= 0 )
        {
            String texBaseName = texName.substring( lastSlashPos + 1 );
            
            // Try without path...
            texture = appFactory.loadOrGetTexture( texBaseName, baseURL, true, true, true, true, true );
            
            if ( !appFactory.isFallbackTexture( texture ) )
                return ( texture );
        }
        
        /*
         * The texture wasn't found at all.
         * Log the fail and return fallback-texture!
         */
        
        JAGTLog.printlnEx( "Couldn't find texture resource \"", texName, "\"!" );
        
        return ( texture );
    }
    
    private NamedObject createSkin( String skinName, URL baseURL, AppearanceFactory appFactory ) throws IOException, IncorrectFormatException, ParsingException
    {
        NamedObject skin = skinsCache.get( skinName );
        
        if ( skin == null )
        {
            AbstractTexture texture;
            if ( ( skinName == null ) || ( skinName.length() == 0 ) )
                texture = appFactory.getFallbackTexture();
            else
                texture = loadTexture( skinName, baseURL, appFactory );
            
            skin = appFactory.createStandardAppearance( "", texture, 0 );
            
            skinsCache.put( skinName, skin );
        }
        
        return ( skin );
    }
    
    private NamedObject[] readSkins( int skinsOffset, int numSkins, URL baseURL, AppearanceFactory appFactory ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 skins..." );
        
        in.skipBytes( skinsOffset - in.getPointer() );
        
        NamedObject[] skins = new NamedObject[ numSkins ];
        
        for ( int i = 0; i < numSkins; i++ )
        {
            String skinName = fixPath( in.readCString( 64, true ) );
            
            skins[ i ] = createSkin( skinName, baseURL, appFactory );
        }
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( skins );
    }
    
    private float[] readTextureCoordinates( int textureCoordsOffset, int numTexCoords, int skinWidth, int skinHeight ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 texture-coordinates..." );
        
        in.skipBytes( textureCoordsOffset - in.getPointer() );
        
        /*
         * Since texture-coordinates are read from GLCommands (frame-data),
         * we don't need to interprete them from here, which would be more
         * expensive.
         * We can simply skip this block.
         */
        in.skipBytes( numTexCoords * 4 );
        float[] data = null;
        
        /*
        float[] data = new float[ 2 * numTexCoords ];
        
        float scaleS = 1f / (float)skinWidth;
        float scaleT = 1f / (float)skinHeight;
        
        for ( int i = 0; i < numTexCoords; i++ )
        {
            data[i * 2 + 0] = (float)in.readUnsignedShort() * scaleS;
            data[i * 2 + 1] = (float)in.readUnsignedShort() * scaleT;
        }
        */
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( data );
    }
    
    private int[][] readTriangles( int trianglesOffset, int numTriangles ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 triangles..." );
        
        in.skipBytes( trianglesOffset - in.getPointer() );
        
        /*
         * Since triangles are read from GLCommands (frame-data),
         * we don't need to interprete them from here, which would be more
         * expensive.
         * We can simply skip this block.
         */
        in.skipBytes( numTriangles * 12 );
        int[][] data = null;
        
        /*
        int[] coordIndices = new int[ 3 * numTriangles ];
        int[] texCoordIndices = new int[ 3 * numTriangles ];
        
        for ( int i = 0; i < numTriangles; i++ )
        {
            coordIndices[i * 3 + 0] = in.readUnsignedShort();
            coordIndices[i * 3 + 1] = in.readUnsignedShort();
            coordIndices[i * 3 + 2] = in.readUnsignedShort();
            
            texCoordIndices[i * 3 + 0] = in.readUnsignedShort();
            texCoordIndices[i * 3 + 1] = in.readUnsignedShort();
            texCoordIndices[i * 3 + 2] = in.readUnsignedShort();
        }
        
        int[][] data = new int[][] { coordIndices, texCoordIndices };
        */
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( data );
    }
    
    private Object[][] readFrames( int framesOffset, int numFrames, int numVertices, boolean convertZup2Yup, float scale ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 frames..." );
        
        in.skipBytes( framesOffset - in.getPointer() );
        
        Object[][] frames = new Object[ numFrames ][];
        
        for ( int i = 0; i < numFrames; i++ )
        {
            float scaleX = in.readFloat();
            float scaleY = in.readFloat();
            float scaleZ = in.readFloat();
            
            float translateX = in.readFloat();
            float translateY = in.readFloat();
            float translateZ = in.readFloat();
            
            String name = in.readCString( 16, true );
            //System.out.println( name );
            
            float[] coords = new float[ 3 * numVertices ];
            short[] normalIndices = new short[ numVertices ];
            
            for ( int j = 0; j < numVertices; j++ )
            {
                int x = in.readUnsignedByte();
                int y = in.readUnsignedByte();
                int z = in.readUnsignedByte();
                
                if ( convertZup2Yup )
                {
                    coords[j * 3 + 0] = ( translateX + ( (float)x * scaleX ) ) * scale;
                    coords[j * 3 + 1] = ( translateZ + ( (float)z * scaleZ ) ) * scale;
                    coords[j * 3 + 2] = -( translateY + ( (float)y * scaleY ) ) * scale;
                }
                else
                {
                    coords[j * 3 + 0] = ( translateX + ( (float)x * scaleX ) ) * scale;
                    coords[j * 3 + 1] = ( translateY + ( (float)y * scaleY ) ) * scale;
                    coords[j * 3 + 2] = ( translateZ + ( (float)z * scaleZ ) ) * scale;
                }
                
                normalIndices[j] = (short)in.readUnsignedByte();
            }
            
            frames[i] = new Object[] { name, coords, normalIndices };
        }
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( frames );
    }
    
    private static final String getAnimName( String frameName )
    {
        int i = frameName.length() - 1;
        while ( ( frameName.charAt( i ) >= '0' ) && ( frameName.charAt( i ) <= '9' ) )
        {
            i--;
        }
        
        return ( frameName.substring( 0, i + 1 ) );
    }
    
    private void readGLCommands( int commandsOffset, Object[][] framesData, GeometryFactory geomFactory, boolean convertZup2Yup, AnimationFactory animFactory, AppearanceFactory appFactory, URL baseURL, NodeFactory nodeFactory, NamedObject rootGroup, NamedObject[] skins, String skin, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 GL-commands..." );
        
        in.skipBytes( commandsOffset - in.getPointer() );
        
        int numSets = 0;
        
        int stripCount = 0;
        int stripVertexCount = 0;
        int fanCount = 0;
        int fanVertexCount = 0;
        
        ArrayList<GeometryFactory.GeometryType> geomTypes = new ArrayList<GeometryFactory.GeometryType>();
        ArrayList<float[]> texCoords = new ArrayList<float[]>();
        ArrayList<int[]> vertexIndices = new ArrayList<int[]>();
        
        /*
         * Pass one:
         * Collect all GLCommand-data.
         * Count the number of vertexs for each type of primitive.
         */
        
        //for ( int i = 0; i < numCommands; i++ )
        while ( true )
        {
            int indicator = in.readInt();
            
            if ( indicator == 0 )
                break;
            
            int num = Math.abs( indicator );
            
            if ( indicator < 0 )
            {
                geomTypes.add( GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY );
                
                fanVertexCount += num;
                fanCount++;
            }
            else
            {
                geomTypes.add( GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY );
                
                stripVertexCount += num;
                stripCount++;
            }
            
            float[] st = new float[num * 2];
            int[] idxs = new int[num];
            
            for ( int j = 0; j < num; j++ )
            {
                st[j * 2 + 0] = in.readFloat();        // s
                st[j * 2 + 1] = 1.0f - in.readFloat(); // t
                
                idxs[j] = in.readInt();
            }
            
            texCoords.add( st );
            vertexIndices.add( idxs );
            
            numSets++;
        }
        
        
        /*
         * Pass two:
         * Count up the number of vertices for each fan/strip.
         */
        
        int[] fanCounts = new int[ fanCount ];
        int fanIndex = 0;
        int[] stripCounts = new int[ stripCount ];
        int stripIndex = 0;
        
        for ( int i = 0; i < numSets; i++ )
        {
            GeometryFactory.GeometryType geomType = geomTypes.get( i );
            int numVertices_ = vertexIndices.get( i ).length;
            
            if ( geomType == GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY )
            {
                fanCounts[fanIndex++] = numVertices_;
            }
            else// if ( geomType == GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY )
            {
                stripCounts[stripIndex++] = numVertices_;
            }
        }
        
        
        /*
         * Pass three:
         * Create the geometry.
         */
        
        NamedObject fanArray = geomFactory.createGeometry( "MD2-TriangleFanArray",
                                                           GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY, 3,
                                                           fanVertexCount, 0, fanCounts
                                                         );
        
        NamedObject stripArray = geomFactory.createGeometry( "MD2-TriangleStripArray",
                                                             GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY, 3,
                                                             stripVertexCount, 0, stripCounts
                                                           );
        
        NamedObject fanShape = null;
        NamedObject stripShape = null;
        
        ArrayList<Object[]> frames = new ArrayList<Object[]>();
        String lastAnimName = null;
        
        for ( int f = 0; f < framesData.length; f++ )
        {
            String name = (String)framesData[f][0];
            float[] coords = (float[])framesData[f][1];
            short[] normalIndices = (short[])framesData[f][2];
            
            String animName = getAnimName( name );
            
            if ( ( lastAnimName != null ) && !lastAnimName.equals( animName ) )
            {
                Object[] fanFrames = (Object[])Array.newInstance( frames.get( 0 )[0].getClass(), frames.size() );
                Object[] stripFrames = (Object[])Array.newInstance( frames.get( 0 )[1].getClass(), frames.size() );
                for ( int i = 0; i < frames.size(); i++ )
                {
                    Object[] o = frames.get( i );
                    
                    fanFrames[i] = o[0];
                    stripFrames[i] = o[1];
                }
                
                Object fanAnimController = animFactory.createMeshDeformationKeyFrameController( fanFrames, fanShape );
                Object stripAnimController = animFactory.createMeshDeformationKeyFrameController( stripFrames, stripShape );
                
                Object[] animControllers = (Object[])Array.newInstance( fanAnimController.getClass(), 2 );
                animControllers[0] = fanAnimController;
                animControllers[1] = stripAnimController;
                
                Object animation = animFactory.createAnimation( lastAnimName, frames.size(), 9f, animControllers, null );
                siHandler.addAnimation( animation );
                
                frames.clear();
            }
            
            lastAnimName = animName;
            
            float[] frameFanCoords = new float[ fanVertexCount * 3 ];
            float[] frameStripCoords = new float[ stripVertexCount * 3 ];
            
            float[] frameFanNormals = new float[ fanVertexCount * 3 ];
            float[] frameStripNormals = new float[ stripVertexCount * 3 ];
            
            stripIndex = 0;
            fanIndex = 0;
            
            for ( int i = 0; i < numSets; i++ )
            {
                GeometryFactory.GeometryType geomType = geomTypes.get( i );
                float[] st = texCoords.get( i );
                int[] vertexIndices_ = vertexIndices.get( i );
                int numVertices = vertexIndices_.length;
                
                if ( geomType == GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY )
                {
                    for ( int j = 0; j < numVertices; j++ )
                    {
                        int vertexIndex = vertexIndices_[j];
                        int normalIndex = normalIndices[j];
                        
                        if ( f == 0 )
                        {
                            //geomFactory.setCoordinates( fanArray, geomType, fanIndex, coords, vertexIndex * 3, 1 );
                            geomFactory.setTexCoords( fanArray, geomType, 0, 2, fanIndex, st, j * 2, 1 );
                            /*
                            if ( convertZup2Yup )
                                geomFactory.setNormals( fanArray, geomType, fanIndex, MD2Normals.dataYup, normalIndex * 3, 1 );
                            else
                                geomFactory.setNormals( fanArray, geomType, fanIndex, MD2Normals.dataZup, normalIndex * 3, 1 );
                            */
                        }
                        
                        System.arraycopy( coords, vertexIndex * 3, frameFanCoords, fanIndex * 3, 3 );
                        if ( convertZup2Yup )
                            System.arraycopy( MD2Normals.dataYup, normalIndex * 3, frameFanNormals, fanIndex * 3, 3 );
                        else
                            System.arraycopy( MD2Normals.dataZup, normalIndex * 3, frameFanNormals, fanIndex * 3, 3 );
                        
                        fanIndex++;
                    }
                }
                else// if ( geomType == GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY )
                {
                    for ( int j = 0; j < numVertices; j++ )
                    {
                        int vertexIndex = vertexIndices_[j];
                        int normalIndex = normalIndices[j];
                        
                        if ( f == 0 )
                        {
                            //geomFactory.setCoordinates( stripArray, geomType, stripIndex, coords, vertexIndex * 3, 1 );
                            geomFactory.setTexCoords( stripArray, geomType, 0, 2, stripIndex, st, j * 2, 1 );
                            /*
                            if ( convertZup2Yup )
                                geomFactory.setNormals( stripArray, geomType, stripIndex, MD2Normals.dataYup, normalIndex * 3, 1 );
                            else
                                geomFactory.setNormals( stripArray, geomType, stripIndex, MD2Normals.dataZup, normalIndex * 3, 1 );
                            */
                        }
                        
                        System.arraycopy( coords, vertexIndex * 3, frameStripCoords, stripIndex * 3, 3 );
                        if ( convertZup2Yup )
                            System.arraycopy( MD2Normals.dataYup, normalIndex * 3, frameStripNormals, stripIndex * 3, 3 );
                        else
                            System.arraycopy( MD2Normals.dataZup, normalIndex * 3, frameStripNormals, stripIndex * 3, 3 );
                        
                        stripIndex++;
                    }
                }
            }
            
            if ( f == 0 )
            {
                geomFactory.setCoordinates( fanArray, GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY, 0, frameFanCoords, 0, fanIndex );
                geomFactory.setNormals( fanArray, GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY, 0, frameFanNormals, 0, fanIndex );
                geomFactory.setCoordinates( stripArray, GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY, 0, frameStripCoords, 0, stripIndex );
                geomFactory.setNormals( stripArray, GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY, 0, frameStripNormals, 0, stripIndex );
                
                geomFactory.finalizeGeometry( fanArray, GeometryFactory.GeometryType.TRIANGLE_FAN_ARRAY, 0, fanVertexCount, 0, 0 );
                geomFactory.finalizeGeometry( stripArray, GeometryFactory.GeometryType.TRIANGLE_STRIP_ARRAY, 0, stripVertexCount, 0, 0 );
                
                NamedObject appearance;
                if ( ( skin != null ) || ( skins == null ) || ( skins.length == 0 ) )
                {
                    appearance = createSkin( skin, baseURL, appFactory );
                }
                else
                {
                    appearance = skins[0];
                }
                
                fanShape = nodeFactory.createShape( "MD2-TriangleFanArray", fanArray, appearance, BoundsType.SPHERE );
                nodeFactory.addNodeToGroup( fanShape, rootGroup );
                siHandler.addSpecialItem( SpecialItemType.SHAPE, fanShape.getName(), fanShape );
                
                stripShape = nodeFactory.createShape( "MD2-TriangleStripArray", stripArray, appearance, BoundsType.SPHERE );
                nodeFactory.addNodeToGroup( stripShape, rootGroup );
                siHandler.addSpecialItem( SpecialItemType.SHAPE, stripShape.getName(), stripShape );
            }
            
            Object fanFrame = animFactory.createMeshDeformationKeyFrame( frameFanCoords, frameFanNormals );
            Object stripFrame = animFactory.createMeshDeformationKeyFrame( frameStripCoords, frameStripNormals );
            
            frames.add( new Object[] { fanFrame, stripFrame } );
        }
        
        if ( ( framesData.length > 1 ) && ( lastAnimName != null ) && ( frames.size() > 0 ) )
        {
            Object[] fanFrames = (Object[])Array.newInstance( frames.get( 0 )[0].getClass(), frames.size() );
            Object[] stripFrames = (Object[])Array.newInstance( frames.get( 0 )[1].getClass(), frames.size() );
            for ( int i = 0; i < frames.size(); i++ )
            {
                Object[] o = frames.get( i );
                
                fanFrames[i] = o[0];
                stripFrames[i] = o[1];
            }
            
            Object fanAnimController = animFactory.createMeshDeformationKeyFrameController( fanFrames, fanShape );
            Object stripAnimController = animFactory.createMeshDeformationKeyFrameController( stripFrames, stripShape );
            
            Object[] animControllers = (Object[])Array.newInstance( fanAnimController.getClass(), 2 );
            animControllers[0] = fanAnimController;
            animControllers[1] = stripAnimController;
            
            Object animation = animFactory.createAnimation( lastAnimName, frames.size(), 9f, animControllers, null );
            siHandler.addAnimation( animation );
            
            frames.clear();
        }
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
    }
    
    private MD2File( InputStream in, URL baseURL, AppearanceFactory appFactory, String skin, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        this.in = new LittleEndianDataInputStream( in );
        
        try
        {
            this.header = MD2Header.readHeader( this.in );
            
            NamedObject[] skins = readSkins( header.offsetSkins, header.numSkins, baseURL, appFactory );
            /*float[] texCoords = */readTextureCoordinates( header.offsetTexCoords, header.numTexCoords, header.skinWidth, header.skinHeight );
            /*int[][] triangles = */readTriangles( header.offsetTriangles, header.numTriangles );
            Object[][] frames = readFrames( header.offsetFrames, header.numFrames, header.numVertices, convertZup2Yup, scale );
            readGLCommands( header.offsetGlCommands, frames, geomFactory, convertZup2Yup, animFactory, appFactory, baseURL, nodeFactory, rootGroup, skins, skin, siHandler );
        }
        finally
        {
            try
            {
                in.close();
            }
            catch ( Throwable t )
            {
                t.printStackTrace();
            }
        }
    }
    
    public static final void load( InputStream in, URL baseURL, AppearanceFactory appFactory, String skin, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        new MD2File( in, baseURL, appFactory, skin, geomFactory, convertZup2Yup, scale, nodeFactory, animFactory, siHandler, rootGroup );
    }
}
