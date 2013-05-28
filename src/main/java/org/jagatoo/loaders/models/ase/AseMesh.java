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
package org.jagatoo.loaders.models.ase;

import org.jagatoo.logging.JAGTLog;

/**
 * The ASE mesh object holds the information for a single mesh. It is parsed a
 * little differently from other ASE nodes because I don't want to create
 * seperate nodes for every vertex and UV mapping. So to be a little more
 * efficient the geometry will be stored in big arrays. Arrays which can be used
 * to populate a geometry array.
 * 
 * An unfortunate storage difference makes ASE indexed format different than
 * Java3d indexed format. The normals are stored separate from the vertex list.
 * This means that for the same vertex, there could be several sets of normals,
 * depending on what face they belong to.
 * 
 * @author David Yazel
 * @author Amos Wenger (aka BlueSky) [display lists usage]
 */
public class AseMesh extends AseNode
{
    private static final int MAX_MATERIALS = 100;
    
    public int numVertices = 0;
    public int numFaces = 0;
    public int numTexVertices = 0;
    public int numTexFaces = 0;
    // data storage for mesh
    public float[] vertices = null;
    public int[] faces = null;
    public float[] texVertices = null;
    public int[] texFaces = null;
    public float[] normals = null;
    public int[] normalsIndices = null;
    public float[] faceNormals = null;
    public int[] faceNormalsIndices = null;
    public int[] faceMat = null;
    public int[] totals = null;
    public boolean convertMeshCoordinates = true;
    
    /**
     * Parses a mesh vertex list. There should be numVertices lines to define
     * this
     */
    public void parseVertexList( AseReader in )
    {
        vertices = new float[ numVertices * 3 ];
        
        int n = 0;
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( in.blockEnd )
            {
                break;
            }
            
            if ( line.getType() != AseFileLine.Type.MESH_VERTEX )
            {
                throw new Error( "Expecting *MESH_VERTEX at line " + line.getNumber() + " instead of " + line.getKey() );
            }
            
            vertices[ n++ ] = Float.parseFloat( line.getParameter( 1 ) );
            vertices[ n++ ] = Float.parseFloat( line.getParameter( 2 ) );
            vertices[ n++ ] = Float.parseFloat( line.getParameter( 3 ) );
        }
        
        if ( ( n / 3 ) != numVertices )
        {
            throw new Error( "Vertex list does not match declared amount : " + in.getLastLineNumber() );
        }
        
        in.blockEnd = false;
    }
    
    /**
     * Parses a mesh face list. There should be numFaces lines to define this
     */
    public void parseFaceList( AseReader in )
    {
        faces = new int[ numFaces * 3 ];
        faceMat = new int[ numFaces ];
        totals = new int[ MAX_MATERIALS ];
        
        int n = 0;
        int f = 0;
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( in.blockEnd )
            {
                break;
            }
            
            if ( line.getType() != AseFileLine.Type.MESH_FACE )
            {
                throw new Error( "Expecting *MESH_FACE at line " + line.getNumber() );
            }
            
            faces[ n++ ] = Integer.parseInt( line.getParameter( 2 ) );
            faces[ n++ ] = Integer.parseInt( line.getParameter( 4 ) );
            faces[ n++ ] = Integer.parseInt( line.getParameter( 6 ) );
            
            // scan and get the material ID
            int matID = 0;
            
            for ( int i = 0; i < line.getParametersCount(); i++ )
            {
                if ( line.getParameter( i ).equalsIgnoreCase( "*MESH_MTLID" ) )
                {
                    matID = Integer.parseInt( line.getParameter( i + 1 ) );
                    
                    break;
                }
            }
            
            faceMat[ f++ ] = matID;
            
            if ( matID >= MAX_MATERIALS )
            {
                throw new Error( "Invalid MatID " + matID + " on line " + line.getNumber() );
            }
            
            totals[ matID ]++;
        }
        
        if ( ( n / 3 ) != numFaces )
        {
            throw new Error( "Face list does not match declared amount : " + in.getLastLineNumber() );
        }
        
        in.blockEnd = false;
        
        for ( int i = 0; i < 10; i++ )
        {
            if ( totals[ i ] != 0 )
            {
                JAGTLog.debug( totals[ i ] + " faces use material " + i );
            }
        }
    }
    
    /**
     * Parses a texture vertex list. There should be numTexVertices lines to
     * define this
     */
    public void parseTexVertexList( AseReader in )
    {
        texVertices = new float[ numTexVertices * 2 ];
        
        int n = 0;
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( in.blockEnd )
            {
                break;
            }
            
            if ( line.getType() != AseFileLine.Type.MESH_TEX_VERT )
            {
                throw new Error( "Expecting *MESH_FACE at line " + line.getNumber() );
            }
            
            texVertices[ n++ ] = Float.parseFloat( line.getParameter( 1 ) );
            texVertices[ n++ ] = Float.parseFloat( line.getParameter( 2 ) );
        }
        
        if ( ( n / 2 ) != numTexVertices )
        {
            throw new Error( "Texture vertex list does not match declared amount : " + in.getLastLineNumber() );
        }
        
        in.blockEnd = false;
    }
    
    /**
     * Parses a mesh tex face list. There should be numFaces lines to define
     * this
     */
    public void parseTexFaceList( AseReader in )
    {
        if ( numTexFaces != numFaces )
        {
            throw new Error( "Number of tex faces does not equal number of faces " + in.getLastLineNumber() );
        }
        
        texFaces = new int[ numTexFaces * 3 ];
        
        int n = 0;
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( in.blockEnd )
            {
                break;
            }
            
            if ( line.getType() != AseFileLine.Type.MESH_TEX_FACE )
            {
                throw new Error( "Expecting *MESH_TFACE at line " + line.getNumber() );
            }
            
            texFaces[ n++ ] = Integer.parseInt( line.getParameter( 1 ) );
            texFaces[ n++ ] = Integer.parseInt( line.getParameter( 2 ) );
            texFaces[ n++ ] = Integer.parseInt( line.getParameter( 3 ) );
        }
        
        if ( ( n / 3 ) != numTexFaces )
        {
            throw new Error( "tex face list does not match declared amount : " + in.getLastLineNumber() );
        }
        
        in.blockEnd = false;
    }
    
    /**
     * Reads in a single normal and its face and put their into the normals and
     * normalIndices arrays
     */
    public void parseNormal( AseReader in, int n )
    {
        AseFileLine line = in.readAseLine();
        
        if ( line.getType() != AseFileLine.Type.MESH_VERTEXNORMAL )
        {
            throw new Error( "Expecting *MESH_VERTEXNORMAL at line " + line.getNumber() );
        }
        
        normalsIndices[ n / 3 ] = Integer.parseInt( line.getParameter( 0 ) );
        
        normals[ n++ ] = Float.parseFloat( line.getParameter( 1 ) );
        normals[ n++ ] = Float.parseFloat( line.getParameter( 2 ) );
        normals[ n++ ] = Float.parseFloat( line.getParameter( 3 ) );
    }
    
    /**
     * Parses a mesh normal list. There is one normal per vertex in each face.
     * This equates to 9 floats per vertex for the normals. Also determines
     * faceNormals and faceNormalsIndices values. There is one faceNormal per
     * face.
     */
    public void parseNormalsList( AseReader in )
    {
        normals = new float[ numFaces * 9 ];
        normalsIndices = new int[ numFaces * 3 ];
        faceNormals = new float[ numFaces * 3 ];
        faceNormalsIndices = new int[ numFaces ];
        
        int n = 0;
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( in.blockEnd )
            {
                break;
            }
            
            if ( line.getType() != AseFileLine.Type.MESH_FACENORMAL )
            {
                throw new Error( "Expecting *MESH_FACENORMAL at line " + line.getNumber() );
            }
            
            faceNormalsIndices[ n / 9 ] = Integer.parseInt( line.getParameter( 0 ) );
            faceNormals[ n / 3 ] = Float.parseFloat( line.getParameter( 1 ) );
            faceNormals[ n / 3 + 1 ] = Float.parseFloat( line.getParameter( 2 ) );
            faceNormals[ n / 3 + 2 ] = Float.parseFloat( line.getParameter( 3 ) );
            
            parseNormal( in, n );
            n += 3;
            parseNormal( in, n );
            n += 3;
            parseNormal( in, n );
            n += 3;
        }
        
        if ( ( n / 9 ) != numFaces )
        {
            throw new Error( "normal list does not match declared amount : " + in.getLastLineNumber() );
        }
        
        in.blockEnd = false;
    }
    
    /**
     * Override the default parse method because we are going to parse the
     * entire mesh in thos node, rather than recusing into further node types.
     */
    @Override
    public void parse( AseReader in )
    {
        this.convertMeshCoordinates = in.convertMeshCoordinates;
        
        AseFileLine line;
        
        // for this to work, blocks have to open on the same line as the
        // property definition.
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( !parseProperty( in, line ) )
            {
                // check for the various special types
                if ( line.getType() == AseFileLine.Type.MESH_VERTEX_LIST )
                {
                    parseVertexList( in );
                }
                if ( line.getType() == AseFileLine.Type.MESH_FACE_LIST )
                {
                    parseFaceList( in );
                }
                if ( line.getType() == AseFileLine.Type.MESH_TEX_VERT_LIST )
                {
                    parseTexVertexList( in );
                }
                if ( line.getType() == AseFileLine.Type.MESH_TEX_FACE_LIST )
                {
                    parseTexFaceList( in );
                }
                if ( line.getType() == AseFileLine.Type.MESH_NORMALS )
                {
                    parseNormalsList( in );
                }
                if ( in.blockStart )
                {
                    trashBlock( in );
                }
            }
            
            if ( in.blockEnd )
            {
                break;
            }
        }
        
        in.blockEnd = false;
    }
    
    public AseMesh()
    {
        properties.put( "*MESH_NUMVERTEX", "numVertices" );
        properties.put( "*MESH_NUMFACES", "numFaces" );
        properties.put( "*MESH_NUMTVFACES", "numTexFaces" );
        properties.put( "*MESH_NUMTVERTEX", "numTexVertices" );
    }
}
