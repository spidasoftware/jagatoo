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
package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Mesh;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshDataInfo;
import org.jagatoo.loaders.models.collada.datastructs.geometries.MeshSources;
import org.jagatoo.loaders.models.collada.datastructs.geometries.TrianglesGeometry;
import org.jagatoo.loaders.models.collada.stax.XMLGeometry;
import org.jagatoo.loaders.models.collada.stax.XMLInput;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryGeometries;
import org.jagatoo.loaders.models.collada.stax.XMLMesh;
import org.jagatoo.loaders.models.collada.stax.XMLSource;
import org.jagatoo.loaders.models.collada.stax.XMLTriangles;
import org.jagatoo.logging.JAGTLog;

/**
 * Loader for LibraryGeometries
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryGeometriesLoader
{
    /**
     * Loads LibraryGeometries.
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libGeoms
     *            The JAXB data to load from
     */
    static LibraryGeometries loadLibraryGeometries( AssetFolder colladaFile, XMLLibraryGeometries libGeoms )
    {
        // LibraryGeometries
        LibraryGeometries colladaLibGeoms = colladaFile.getLibraryGeometries();
        
        Collection<XMLGeometry> geoms = libGeoms.geometries.values();
        JAGTLog.debug( "There ", ( geoms.size() > 1 ? "are" : "is" ), " ", geoms.size(), " geometr", ( geoms.size() > 1 ? "ies" : "y" ), " in this file." );
        
        int i = 0;
        for ( XMLGeometry geom : geoms )
        {
            JAGTLog.debug( "Handling geometry ", i++ );

            ArrayList<Geometry> loadedGeom = LibraryGeometriesLoader.loadGeom( geom );
            for (int j=0; j< loadedGeom.size(); j++)
            {
            	Geometry subGeom = loadedGeom.get(j);
                colladaLibGeoms.put( subGeom.getId(), subGeom );
            }
        }

        return( colladaLibGeoms );
    }
    
    /**
     * Loads a specific geometry.
     * 
     * @param geom
     */
    static ArrayList<Geometry> loadGeom( XMLGeometry geom )
    {

    	ArrayList<Geometry> geometries = new ArrayList<Geometry>();

        XMLMesh mesh = geom.mesh;

        String verticesSource = mesh.vertices.inputs.get( 0 ).source;

        // First we get all the vertices/normal data we need
        List<XMLSource> sources = mesh.sources;
        HashMap<String, XMLSource> sourcesMap = LibraryGeometriesLoader.getSourcesMap( mesh, verticesSource, sources );

        // Supported types :
        if (mesh.triangles != null)
        {
	        for (int i=0; i<mesh.triangles.size(); i++)
	        {
	            Geometry colGeom = null;
	            XMLTriangles tris = mesh.triangles.get(i);

	            // Unsupported types :
	            //Polygons polys = mesh.polygons;

	            // Try triangles
	            if ( tris != null )
	            {
	                JAGTLog.debug( "TT] Primitives of type triangles" );
	                JAGTLog.debug( "TT] Polygon count = ", tris.count );


	                colGeom = LibraryGeometriesLoader.loadTriangles( geom, tris, sourcesMap );
	                geometries.add(colGeom);
	            }
	        }
        } else if (mesh.lines != null)
        {
//        	Geometry colGeom = null;
//        	XMLLines lines = mesh.lines;
//            colGeom = loadTriangles(geom, lines, sourcesMap);
//            geometries.add(colGeom);

        }
        
        // Try polys
        /*
        else if ( !polys.isEmpty() )
        {
            JAGTLog.debug( "TT] Primitives of type polygons" );
            JAGTLog.debug( "TT] Polygon count = ", polys.count );
            
            colGeom = loadPolygons( geom, polys, sourcesMap );
        }
        */

        // Note that Lines are not supported either.
        // TODO: implement Lines

        // Well, no luck
        else
        {
            JAGTLog.debug( "EE] Can't load object : ", geom.name,
                           " because couldn't find a supported element type...",
                           "\n (note that the only well supported type is triangles, so e.g.",
                           "\n in Blender, activate the appropriate option in the export script"
                         );
        }

        return( geometries );
    }
    
    /**
     * Creates the sources map from the information provided by JAXB.
     * 
     * @param mesh
     * @param verticesSource
     * @param sources
     * 
     * @return The sources map
     */
    static HashMap<String, XMLSource> getSourcesMap( XMLMesh mesh, String verticesSource, List<XMLSource> sources )
    {
        HashMap<String, XMLSource> sourcesMap;
        sourcesMap = new HashMap<String, XMLSource>();
        for ( int i = 0; i < sources.size(); i++ )
        {
            XMLSource source = sources.get( i );
            
            // FIXME There may be more
            if ( verticesSource.equals( source.id ) )
            {
                sourcesMap.put( mesh.vertices.id, source );
                JAGTLog.debug( "TT] Source ", i, " ID = ", mesh.vertices.id );
            }
            else
            {
                // FIXME: Are there other special cases ?
                sourcesMap.put( source.id, source );
                JAGTLog.debug( "TT] Source ", i, " ID = ", source.id );
            }
        }
        
        return ( sourcesMap );
    }
    
    /**
     * @param geom
     * @param sourcesMap
     * @param tris
     * 
     * @return the loaded geometry
     */
    static TrianglesGeometry loadTriangles( XMLGeometry geom, XMLTriangles tris, HashMap<String, XMLSource> sourcesMap )
    {
        TrianglesGeometry trianglesGeometry = new TrianglesGeometry( null, geom.id, geom.name, geom, tris.material );

        MeshSources sources = new MeshSources();
        MeshDataInfo meshDataInfo = LibraryGeometriesLoader.getMeshDataInfo( tris.inputs, sourcesMap, sources );

        Mesh mesh = new Mesh( sources );
        trianglesGeometry.setMesh( mesh );

        int[] indices = tris.p;
        int count = 0;
        int indexCount = indices.length / meshDataInfo.maxOffset;

        if ( meshDataInfo.vertexOffset != -1 )
        {
            mesh.setVertexIndices( new int[indexCount] );
        }

        if ( meshDataInfo.normalOffset != -1 )
        {
            mesh.setNormalIndices( new int[indexCount] );
        }

        if ( meshDataInfo.colorOffset != -1 )
        {
            mesh.setColorIndices( new int[indexCount] );
        }

        if ( meshDataInfo.uvOffsets != null )
        {
            int[][] uvIndices = new int[ meshDataInfo.uvOffsets.length ][];

            for ( int i = 0; i < meshDataInfo.uvOffsets.length; i++ )
            {
                uvIndices[ i ] = new int[ indexCount ];
            }

            mesh.setUVIndices( uvIndices );
        }

        /**
         * FILLING
         */
        for ( int k = 0; k < indices.length; k += meshDataInfo.maxOffset )
        {
            if ( meshDataInfo.vertexOffset != -1 )
            {
                mesh.getVertexIndices()[count] = indices[k + meshDataInfo.vertexOffset];
            }

            if ( meshDataInfo.normalOffset != -1 )
            {
                mesh.getNormalIndices()[count] = indices[k + meshDataInfo.normalOffset];
            }

            if ( meshDataInfo.colorOffset != -1 )
            {
                mesh.getColorIndices()[count] = indices[k + meshDataInfo.colorOffset];
            }

            if ( meshDataInfo.uvOffsets != null )
            {
                for ( int i = 0; i < meshDataInfo.uvOffsets.length; i++ )
                {
                    mesh.getUVIndices()[i][count] = indices[k + meshDataInfo.uvOffsets[i]];
                }
            }

            count++;
        }

        /**
         * FILLING
         */

        return( trianglesGeometry );
    }

    /**
     * Gets the mesh data info and fill the sources.
     *
     * @param inputs
     * @param sourcesMap
     *            The sources map precedently filled by
     * @param sources
     *            The sources which are going to be filled. May NOT be null.
     * @return The mesh data info
     */
    static MeshDataInfo getMeshDataInfo( List<XMLInput> inputs, HashMap<String, XMLSource> sourcesMap, MeshSources sources )
    {
        MeshDataInfo meshDataInfo = new MeshDataInfo();

        sources.setUVs( null );
        int[] uvOffsets = new int[ inputs.size() ];
        int numUVOffsets = 0;

        JAGTLog.debug( "TT] Parsing semantics...." );

        for ( int j = 0; j < inputs.size(); j++ )
        {
            XMLInput input = inputs.get( j );
            JAGTLog.debug( "TT] Input semantic ", input.semantic, ", offset ", input.offset, ", from source = ", input.source );

            if ( input.offset > meshDataInfo.maxOffset )
            {
                meshDataInfo.maxOffset = input.offset;
            }

            if ( input.semantic.equals( "VERTEX" ) )
            {
                meshDataInfo.vertexOffset = input.offset;
                XMLSource source = sourcesMap.get( input.source );
                sources.setVertices( source.floatArray.floats );
            }
            else if ( input.semantic.equals( "NORMAL" ) )
            {
                meshDataInfo.normalOffset = input.offset;
                XMLSource source = sourcesMap.get( input.source );
                sources.setNormals( source.floatArray.floats );
            }
            else if ( input.semantic.equals( "TEXCOORD" ) )
            {
                uvOffsets[numUVOffsets++] = input.offset;
                XMLSource source = sourcesMap.get( input.source );
                sources.addUV( source.floatArray.floats );
            }
            else if ( input.semantic.equals( "COLOR" ) )
            {
                meshDataInfo.colorOffset = input.offset;
                XMLSource source = sourcesMap.get( input.source );
                sources.setColors( source.floatArray.floats );
            }
            else
            {
                JAGTLog.debug( "EE] We don't know that semantic :", input.semantic, " ! Ignoring.." );
            }
        }

        if ( numUVOffsets > 0 )
        {
            meshDataInfo.uvOffsets = new int[ numUVOffsets ];
            System.arraycopy( uvOffsets, 0, meshDataInfo.uvOffsets, 0, numUVOffsets );
        }
        else
        {
            meshDataInfo.uvOffsets = null;
        }

        // It says it needs to be max + 1
        meshDataInfo.maxOffset += 1;

        return( meshDataInfo );
    }

//    Static LinesGeometry loadGeometry( XMLGeometry geom, XMLLines lines, HashMap<String, XMLSource> sourcesMap )
//    {
//        LinesGeometry linesGeometry = new LinesGeometry( null, geom.id, geom.name, geom, tris.material );
//
//        MeshSources sources = new MeshSources();
//        MeshDataInfo meshDataInfo = LibraryGeometriesLoader.getMeshDataInfo( tris.inputs, sourcesMap, sources );
//
//        Mesh mesh = new Mesh( sources );
//        trianglesGeometry.setMesh( mesh );
//
//        int[] indices = tris.p;
//        int count = 0;
//        int indexCount = indices.length / meshDataInfo.maxOffset;
//
//        if ( meshDataInfo.vertexOffset != -1 )
//        {
//            mesh.setVertexIndices( new int[indexCount] );
//        }
//
//        if ( meshDataInfo.normalOffset != -1 )
//        {
//            mesh.setNormalIndices( new int[indexCount] );
//        }
//
//        if ( meshDataInfo.colorOffset != -1 )
//        {
//            mesh.setColorIndices( new int[indexCount] );
//        }
//
//        if ( meshDataInfo.uvOffsets != null )
//        {
//            int[][] uvIndices = new int[ meshDataInfo.uvOffsets.length ][];
//
//            for ( int i = 0; i < meshDataInfo.uvOffsets.length; i++ )
//            {
//                uvIndices[ i ] = new int[ indexCount ];
//            }
//
//            mesh.setUVIndices( uvIndices );
//        }
//
//        /**
//         * FILLING
//         */
//        for ( int k = 0; k < indices.length; k += meshDataInfo.maxOffset )
//        {
//            if ( meshDataInfo.vertexOffset != -1 )
//            {
//                mesh.getVertexIndices()[count] = indices[k + meshDataInfo.vertexOffset];
//            }
//
//            if ( meshDataInfo.normalOffset != -1 )
//            {
//                mesh.getNormalIndices()[count] = indices[k + meshDataInfo.normalOffset];
//            }
//
//            if ( meshDataInfo.colorOffset != -1 )
//            {
//                mesh.getColorIndices()[count] = indices[k + meshDataInfo.colorOffset];
//            }
//
//            if ( meshDataInfo.uvOffsets != null )
//            {
//                for ( int i = 0; i < meshDataInfo.uvOffsets.length; i++ )
//                {
//                    mesh.getUVIndices()[i][count] = indices[k + meshDataInfo.uvOffsets[i]];
//                }
//            }
//
//            count++;
//        }
//
//        /**
//         * FILLING
//         */
//
//        return( trianglesGeometry );
//    }
}
