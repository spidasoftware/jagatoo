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
package org.jagatoo.geometry;

import java.util.List;

import org.jagatoo.opengl.enums.GeometryArrayType;
import org.openmali.spatial.VertexContainer;
import org.openmali.spatial.polygons.Triangle;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vertex3f;

/**
 * This is a simple geometry data container, that contains only coordinates and
 * indices. This can be useful for physical data, that doesn't need or know
 * normals, colors, texture-coordinates or vertex-attributes.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class SimpleGeometryDataContainer implements VertexContainer
{
    public static final int COORDINATES            = 1;  // equals OpenMaLi's Vertex3f.COORDINATES
    public static final int BY_REFERENCE = 512;
    public static final int INTERLEAVED = 1024;
    
    protected GeomNioFloatData coords = null;
    protected GeomNioIntData indexData = null;
    
    protected GeomNioFloatData interleavedData = null;
    
    private int[] stripCounts = null;
    
    private final int coordsSize;
    
    protected boolean isInterleaved = false;
    
    private final GeometryArrayType type;
    private final boolean isStrip;
    private final boolean hasIndex;
    private final int faceSize;
    protected final int maxVertices;
    protected int numVertices;
    protected int numIndices;
    private int initialVertexIndex = 0;
    protected long coordsOffset = 0L;
    protected static final boolean reversed = false;
    
    /**
     * @return this Geometry's basic type (used by OpenGL).
     */
    public final GeometryArrayType getType()
    {
        return ( type );
    }
    
    /**
     * @return <code>true</code>, if this Geometry is a Strip
     */
    public final boolean isStrip()
    {
        return ( isStrip );
    }
    
    /**
     * @return the number of vertices per face (3 for triangles).
     */
    public final int getFaceSize()
    {
        return ( faceSize );
    }
    
    /**
     * {@inheritDoc}
     */
    public final int getVertexCount()
    {
        return ( maxVertices );
    }
    
    /**
     * @return <code>true</code>, if this Geometry is constructed by interleaved data
     * (one ByteBuffer for all data except index).
     */
    public final boolean isInterleaved()
    {
        return ( isInterleaved );
    }
    
    /**
     * @return <code>true</code>, if this Geometry has an Index.
     */
    public final boolean hasIndex()
    {
        return ( hasIndex );
    }
    
    /**
     * Directly sets the coordinates data buffer.
     * 
     * @param data
     */
    public void setCoordinateData( GeomNioFloatData data )
    {
        if ( isInterleaved() )
            throw new Error( "This method cannot be used for interleaved data." );
        
        if ( data == null )
            throw new Error( "coordinate data must not be null" );
        
        coords = data;
        //hasCoordinates = true;
    }
    
    /**
     * @return the data buffer for coordinate data.
     */
    public final GeomNioFloatData getCoordinatesData()
    {
        return ( coords );
    }
    
    /**
     * @return 3 for 3D-coordinates, etc.
     */
    public final int getCoordinatesSize()
    {
        return ( coordsSize );
    }
    
    /**
     * @return the offset in the data buffer, if this is interleaved data.
     */
    public final long getCoordinatesOffset()
    {
        return ( coordsOffset );
    }
    
    /**
     * @return the data buffer for interleaved data. If this Geometry is not
     * interleaved, an error is thrown.
     */
    public final GeomNioFloatData getInterleavedData()
    {
        if ( !isInterleaved() )
            throw new Error( "This data is not interleaved." );
        
        return ( interleavedData );
    }
    
    /**
     * Sets the index of the first vertex which will be rendered from this
     * geometry array. The extact vertices which will be rendered is from
     * InitialVertexIndex to InitialVertexIndex + ValidVertexCount-1.
     * 
     * @param initialIndex
     */
    public void setInitialIndex( int initialIndex )
    {
        this.initialVertexIndex = initialIndex;
    }
    
    /**
     * @return the index of the first vertex which will be rendered from this
     *         geometry array. The extact vertices which will be rendered is
     *         from InitialVertexIndex to InitialVertexIndex +
     *         ValidVertexCount-1.
     */
    public final int getInitialIndex()
    {
        return ( initialVertexIndex );
    }
    
    /**
     * Sets the number of vertices which will be rendered from this geometry
     * array. The extact vertices which will be rendered is from
     * InitialVertexIndex to InitialVertexIndex + ValidVertexCount-1.
     * 
     * @param count
     */
    public void setValidVertexCount( int count )
    {
        if ( count == this.numVertices )
            return;
        
        this.numVertices = count;
    }
    
    /**
     * @return the number of vertices which will be rendered from this geometry
     *         array. The extact vertices which will be rendered is from
     *         InitialVertexIndex to InitialVertexIndex + ValidVertexCount-1.
     */
    public final int getValidVertexCount()
    {
        return ( numVertices );
    }
    
    /**
     * @return the maximum number of vertices, this geometry can hold.
     */
    public final int getMaxVertexCount()
    {
        return ( maxVertices );
    }
    
    /**
     * @param maxElements
     * @param elemSize
     * @param stride
     * @param reversed
     * 
     * @return a new instance
     */
    protected GeomNioFloatData newNioFloatData( int maxElements, int elemSize, int stride, boolean reversed )
    {
        return ( new GeomNioFloatData( maxElements, elemSize, reversed ) );
    }
    
    protected void createCoordinates()
    {
        this.coords = newNioFloatData( maxVertices, coordsSize, 0, reversed );
    }
    
    protected final void checkCoordsExistence( int coordsSize )
    {
        if ( coordsSize != this.coordsSize )
            throw new IllegalArgumentException( "given coordinate has wrong size. Found " + coordsSize + ", expected " + this.coordsSize + "." );
        
        if ( !isInterleaved && ( coords == null ) )
        {
            createCoordinates();
        }
    }
    
    
    /**
     * Sets the coordinates of the specified vertex. The coordinates should
     * occupy the first three indices of the given array.
     */
    public void setCoordinate( int vertexIndex, float[] floatArray )
    {
        checkCoordsExistence( 3 );
        
        assert ( floatArray.length >= 3 );
        coords.set( vertexIndex, coordsSize, coordsOffset / 4L, floatArray, 0, coordsSize );
    }
    
    /**
     * Sets the coordinates of the vertex at the given index
     * 
     * @param vertexIndex The index of the vertex to modify
     * @param point3f The new coordinates
     */
    public void setCoordinate( int vertexIndex, Tuple3f point3f )
    {
        checkCoordsExistence( 3 );
        
        coords.set( vertexIndex, coordsOffset / 4L, point3f );
    }
    
    /**
     * Sets the coordinates of the vertices starting at the specified index.
     * 
     * @param vertexIndex The index of the first vertex to be modified.
     * @param floatArray The new coordinates. The size of the array must be a
     *            multiple of 3.
     * @param startIndex The index of the first coordinate in the given array.
     *            The first read item of the array will be startIndex*3.
     * @param length The number of vertices to copy
     */
    public void setCoordinates( int vertexIndex, float[] floatArray, int startIndex, int length )
    {
        checkCoordsExistence( 3 );
        
        //assert ( floatArray.length % 3 == 0 );
        coords.set( vertexIndex, coordsSize, coordsOffset / 4L, floatArray, startIndex * coordsSize, length * coordsSize );
    }
    
    /**
     * Sets the coordinates of the vertices starting at the specified index
     * 
     * @param vertexIndex The index of the first vertex to be modified.
     * @param point3fArray The new coordinates
     * @param startIndex The index of the first coordinate in the given array
     * @param length The number of coordinates to copy
     */
    public void setCoordinates( int vertexIndex, Tuple3f[] point3fArray, int startIndex, int length )
    {
        checkCoordsExistence( 3 );
        
        for ( int i = startIndex; i < startIndex + length; i++ )
            coords.set( vertexIndex++, coordsOffset / 4L, point3fArray[ i ] );
    }
    
    /**
     * Sets the coordinates of the vertices starting at the specified index.
     * 
     * @param vertexIndex The index of the first vertex to modify
     * @param floatArray The new coordinates. The size of the array must be a
     *            multiple of 3.
     */
    public void setCoordinates( int vertexIndex, float[] floatArray )
    {
        checkCoordsExistence( 3 );
        
        if ( floatArray.length % coordsSize != 0 )
        {
            throw new IllegalArgumentException( "the size of the coordinate array must be a multiple of 3" );
        }
        coords.set( vertexIndex, coordsSize, coordsOffset / 4L, floatArray );
    }
    
    /**
     * Sets the coordinates of the vertices starting at the specified index.
     * 
     * @param vertexIndex The index of the first vertex to modify
     * @param point3fArray The new coordinates.
     */
    public void setCoordinates( int vertexIndex, Tuple3f[] point3fArray )
    {
        checkCoordsExistence( 3 );
        
        for ( int i = 0; i < point3fArray.length; i++ )
            coords.set( vertexIndex + i, coordsOffset / 4L, point3fArray[ i ] );
    }
    
    public void setCoordinates( int vertexIndex, List<Tuple3f> point3fList )
    {
        checkCoordsExistence( 3 );
        
        for ( int i = 0; i < point3fList.size(); i++ )
            coords.set( vertexIndex + i, coordsOffset / 4L, point3fList.get( i ) );
    }
    
    /**
     * Sets the coordinates of the vertex at the given index
     * 
     * @param vertexIndex The index of the vertex to modify
     * @param x The new coordinates
     * @param y The new coordinates
     * @param z The new coordinates
     */
    public void setCoordinate( int vertexIndex, float x, float y, float z )
    {
        checkCoordsExistence( 3 );
        
        coords.set( vertexIndex, coordsOffset / 4L, x, y, z );
    }
    
    public void getCoordinate( int vertexIndex, float[] floatArray )
    {
        checkCoordsExistence( coordsSize );
        
        coords.get( vertexIndex, coordsSize, coordsOffset / 4L, floatArray, 0, coordsSize );
    }
    
    public <T extends Tuple3f> T getCoordinate( int index, T point )
    {
        checkCoordsExistence( 3 );
        
        coords.get( index, coordsOffset / 4L, point );
        
        return ( point );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean getVertex( int i, Tuple3f pos )
    {
        if ( i >= getVertexCount() )
            return ( false );
        
        getCoordinate( i, pos );
        
        return ( true );
    }
    
    public void getCoordinates( int vertexIndex, float[] floatArray )
    {
        checkCoordsExistence( coordsSize );
        
        int numReqVerts = floatArray.length / coordsSize;
        for ( int i = 0; i < numReqVerts; i++ )
        {
            coords.get( vertexIndex + i, coordsSize, coordsOffset / 4L, floatArray, i * coordsSize, coordsSize );
        }
    }
    
    public void getCoordinates( int vertexIndex, Tuple3f[] point3fArray )
    {
        checkCoordsExistence( 3 );
        
        for ( int i = point3fArray.length - 1; i > -1; i-- )
            coords.get( ( i - vertexIndex ), coordsOffset / 4L, point3fArray[ i ] );
    }
    
    /**
     * Applies the the n-th Triangle to the GeometryArray.
     * This method must be overridden by concrete classes to fix the vertex-index (e.g. for an IndexedTriangleArray)
     * 
     * @param i0 the first triangle's vertex-index
     * @param i1 the second triangle's vertex-index
     * @param i2 the third triangle's vertex-index
     * @param triangle
     * 
     * @return true, if the triangle could be applied
     */
    public boolean setTriangle( int i0, int i1, int i2, Triangle triangle )
    {
        if ( triangle.hasFeature( Vertex3f.COORDINATES ) )
        {
            // write triangle data
            setCoordinate( i0, triangle.getVertexCoordA() );
            setCoordinate( i1, triangle.getVertexCoordB() );
            setCoordinate( i2, triangle.getVertexCoordC() );
        }
        
        return ( true );
    }
    
    /**
     * Applies the the n-th Triangle to the GeometryArray.
     * This method must be overridden by concrete classes to fix the vertex-index (e.g. for an IndexedTriangleArray)
     * 
     * @param triangle
     * 
     * @return true, if the triangle could be applied
     */
    public final boolean setTriangle( Triangle triangle )
    {
        return ( setTriangle( triangle.getVertexIndexA(), triangle.getVertexIndexB(), triangle.getVertexIndexC(), triangle ) );
    }
    
    /**
     * Retrieves the the n-th Triangle from the GeometryArray.
     * This method must be overridden by concrete classes to fix the vertex-index (e.g. for an IndexedTriangleArray)
     * 
     * @param i0 the first triangle's vertex-index
     * @param i1 the second triangle's vertex-index
     * @param i2 the third triangle's vertex-index
     * @param triangle
     * 
     * @return true, if the triangle could be retrieved
     */
    public boolean getTriangle( int i0, int i1, int i2, Triangle triangle )
    {
        //if ( this.hasFeature( SimpleGeometryDataContainer.COORDINATES ) && triangle.hasFeature( Vertex3f.COORDINATES ) )
        {
            // read triangle data
            getVertex( i0, triangle.getVertexCoordA() );
            getVertex( i1, triangle.getVertexCoordB() );
            getVertex( i2, triangle.getVertexCoordC() );
        }
        
        triangle.setVertexIndices( i0, i1, i2 );
        
        return ( true );
    }
    
    /**
     * @return the number of index values. This is the number of generated/rendered vertices.
     */
    public final int getIndexCount()
    {
        return ( indexData.getMaxElements() );
    }
    
    /**
     * Sets all index values.
     * 
     * @param indices
     */
    public void setIndex( int[] indices )
    {
        indexData.set( 0, indices );
    }
    
    /**
     * Sets the i-th index value.
     * 
     * @param i
     * @param idx
     */
    public void setIndex( int i, int idx )
    {
        indexData.set( i, idx );
    }
    
    /**
     * Gets all index values.
     */
    public final void getIndex( int[] index )
    {
        indexData.get( 0, index );
    }
    
    /**
     * @return the i-th index value.
     */
    public final int getIndex( int i )
    {
        return ( indexData.get( i ) );
    }
    
    /**
     * @return the data buffer for the index.
     */
    public final GeomNioIntData getIndexData()
    {
        return ( indexData );
    }
    
    /**
     * @return the number of strips.
     */
    public final int getNumStrips()
    {
        return ( this.stripCounts.length );
    }
    
    /**
     * Sets the counts for each strip.
     * 
     * @param stripCounts
     */
    public void setStripCounts( int[] stripCounts )
    {
        this.stripCounts = stripCounts;
    }
    
    /**
     * Gets the counts for each strip.
     */
    public final int[] getStripCounts()
    {
        return ( stripCounts );
    }
    
    /**
     * Gets the strip counts.
     * 
     * @param sCounts
     */
    public void getStripCounts( int[] sCounts )
    {
        System.arraycopy( stripCounts, 0, sCounts, 0, stripCounts.length );
    }
    
    public float[] getCoordRefFloat()
    {
        return ( ( coords == null ) ? null : coords.getData() );
    }
    
    /**
     * Sets up an NIO buffer for each type of data needed for the geometry.
     */
    protected void setNioBuffers()
    {
        /*
        if ( !hasFeature( SimpleGeometryDataContainer.COORDINATES ) )
        {
            throw new Error( "No coordinates defined!" );
        }
        */
        
        this.coords = newNioFloatData( maxVertices, 3, 0, reversed );
    }
    
    /**
     * Copies details from the given container.
     * 
     * @param o
     * @param forceDuplicate
     */
    protected void copyFrom( SimpleGeometryDataContainer o, boolean forceDuplicate )
    {
        //this.maxVertices = o.maxVertices;
        this.numVertices = o.numVertices;
        this.coordsOffset = o.coordsOffset;
        this.numIndices = o.numIndices;
        this.initialVertexIndex = o.initialVertexIndex;
        
        if ( hasIndex() )
        {
            this.indexData = o.indexData.duplicateGeomData( true );
        }
        
        this.isInterleaved = o.isInterleaved;
        
        if ( o.isInterleaved )
        {
            this.interleavedData = o.interleavedData.duplicateGeomData( true );
        }
//        else
//        {
            if ( o.coords != null )
            {
                this.coords = o.coords.duplicateGeomData( true );
            }
//        }
        
        if ( o.stripCounts != null )
            this.stripCounts = o.stripCounts.clone();
        else
            this.stripCounts = null;
    }
    
    protected GeomNioIntData newNioIntData( int maxElems, int elemSize, boolean reversed )
    {
        return ( new GeomNioIntData( maxElems, elemSize, reversed ) );
    }
    
    protected SimpleGeometryDataContainer( GeometryArrayType type, boolean hasIndex, int coordsSize, int vertexCount, int[] stripCounts, int indexCount )
    {
        this.type = type;
        this.faceSize = type.getFaceSize();
        this.coordsSize = coordsSize;
        this.maxVertices = vertexCount;
        
        this.isStrip = type.isStrip();
        this.hasIndex = hasIndex;
        
        if ( hasIndex )
        {
            this.indexData = newNioIntData( indexCount, 1, false );
            this.numVertices = indexCount;
        }
        else
        {
            this.numVertices = vertexCount;
        }
        
        if ( isStrip )
        {
            if ( stripCounts == null )
                this.stripCounts = new int[] { vertexCount };
            else
                this.stripCounts = stripCounts;
        }
        else
        {
            this.stripCounts = null;
        }
        
        setNioBuffers();
    }
    
    public SimpleGeometryDataContainer( GeometryArrayType type, int coordsSize, int vertexCount )
    {
        this( type, false, coordsSize, vertexCount, null, 0 );
    }
    
    public SimpleGeometryDataContainer( GeometryArrayType type, int coordsSize, int vertexCount, int indexCount )
    {
        this( type, true, coordsSize, vertexCount, null, indexCount );
    }
    
    public SimpleGeometryDataContainer( GeometryArrayType type, int coordsSize, int vertexCount, int[] stripCounts )
    {
        this( type, false, coordsSize, vertexCount, stripCounts, 0 );
    }
    
    public SimpleGeometryDataContainer( GeometryArrayType type, int coordsSize, int vertexCount, int[] stripCounts, int indexCount )
    {
        this( type, true, coordsSize, vertexCount, stripCounts, indexCount );
    }
}
