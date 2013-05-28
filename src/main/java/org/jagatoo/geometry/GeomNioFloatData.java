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

import java.nio.FloatBuffer;

import org.jagatoo.util.nio.BufferUtils;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.TexCoordf;
import org.openmali.vecmath2.TupleNf;

/**
 * GeomDataInterface implementation for NIO buffers
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class GeomNioFloatData extends GeomNioData
{
    protected FloatBuffer buffer;
    
    private final int floatStride;
    
    /**
     * Sets the NIO float buffer underlying this.  If the underlying
     * format is not a float buffer then it will be converted.
     */
    public final void setBuffer( FloatBuffer buffer )
    {
        this.buffer = buffer;
    }
    
    /**
     * @return the NIO float buffer underlying this.  If the underlying
     * format is not a float buffer then it will be converted.
     */
    public final FloatBuffer getBuffer()
    {
        return ( buffer );
    }
    
    public final int getElemStride()
    {
        if ( floatStride == 0 )
            return ( getElemSize() );
        
        return ( floatStride );
    }
    
    /**
     * @return an array with all the floats assigned in the last draw cycle.
     * In some implementations this might return a copy, so only use if you
     * have to.  The data should not be changed once it is retrieved and should
     * be considered read only.
     */
    public final float[] getData()
    {
        float f[] = new float[ buffer.limit() ];
        buffer.rewind();
        buffer.get( f );
        
        return ( f );
    }
    
    /**
     * Gets an array with all the floats assigned in the last draw cycle.
     * In some implementations this might return a copy, so only use if you
     * have to.  The data should not be changed once it is retrieved and should
     * be considered read only.
     * 
     * @param buffer the buffer array to write values to (must be of sufficient size)
     */
    public final void getData( float[] outBuffer )
    {
        buffer.rewind();
        buffer.get( outBuffer );
    }
    
    /**
     * Gets an array with all the floats assigned in the last draw cycle.
     * In some implementations this might return a copy, so only use if you
     * have to.  The data should not be changed once it is retrieved and should
     * be considered read only.
     * 
     * @param index
     * @param elemOffset
     * @param outBuffer the buffer array to write values to (must be of sufficient size)
     * @param start
     * @param length
     */
    public final void getData( int index, long elemOffset, float[] outBuffer, int start, int length )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        buffer.get( outBuffer, 0, length/* * getElemStride()*/ );
    }
    
    /**
     * Resets the buffer to zero and prepares to receive data from the various
     * set() calls. In some implementations this will destroy data.
     */
    @Override
    public final void start()
    {
        buffer.clear();
        setDirty( true );
    }
    
    /**
     * Locks the data and sets the size()
     */
    public final void end()
    {
    }
    
    /**
     * Special optimized way to set a bulk amount of floats right into
     * the data, starting at the index specified.
     * 
     * @param a
     * @param start
     * @param length
     */
    public final void set( float[] a, int start, int length )
    {
        buffer.put( a, start, length );
        
        setDirty( true );
    }
    
    public final void set( float x )
    {
        buffer.put( x );
        
        setDirty( true );
    }
    
    public final void set( float x, float y )
    {
        buffer.put( x );
        buffer.put( y );
        
        setDirty( true );
    }
    
    public final void set( float x, float y, float z )
    {
        buffer.put( x );
        buffer.put( y );
        buffer.put( z );
        
        setDirty( true );
    }
    
    public final void set( float x, float y, float z, float w )
    {
        buffer.put( x );
        buffer.put( y );
        buffer.put( z );
        buffer.put( w );
        
        setDirty( true );
    }
    
    
    
    public final void set( int index, int elemSize, long elemOffset, float[] a, int start, int length )
    {
        int pos = index * getElemStride() + (int)elemOffset;
        for ( int i = 0; i < length; i += elemSize )
        {
            buffer.position( pos );
            buffer.put( a, start + i, elemSize );
            pos += getElemStride();
        }
        
        setDirty( true );
    }
    
    public final void set( int index, int elemSize, long elemOffset, float[] a )
    {
        set( index, elemSize, elemOffset, a, 0, a.length );
    }
    
    public final void set( int index, long elemOffset, float x )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        buffer.put( x );
        
        setDirty( true );
    }
    
    public final void set( int index, long elemOffset, float x, float y )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        buffer.put( x );
        buffer.put( y );
        
        setDirty( true );
    }
    
    public final void set( int index, long elemOffset, float x, float y, float z )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        buffer.put( x );
        buffer.put( y );
        buffer.put( z );
        
        setDirty( true );
    }
    
    public final void set( int index, long elemOffset, float x, float y, float z, float w )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        buffer.put( x );
        buffer.put( y );
        buffer.put( z );
        buffer.put( w );
        
        setDirty( true );
    }
    
    public final void get( int index, int elemSize, long elemOffset, float[] values, int offset, int numValues )
    {
        int pos = index * getElemStride() + (int)elemOffset;
        for ( int i = 0; i < numValues; i += elemSize )
        {
            buffer.position( pos );
            buffer.get( values, offset + i, elemSize );
            pos += getElemStride();
        }
    }
    
    public final void get( int index, int elemSize, long elemOffset, float[] values )
    {
        get( index, elemSize, elemOffset, values, 0, values.length );
    }
    
    public final float get( int index, long elemOffset )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        return ( buffer.get() );
    }
    
    public final void set( TupleNf<?> tuple )
    {
        tuple.writeToBuffer( buffer, false, false );
        
        setDirty( true );
    }
    
    public final void set( boolean alpha, Colorf color )
    {
        buffer.put( color.getRed() );
        buffer.put( color.getGreen() );
        buffer.put( color.getBlue() );
        
        if ( alpha )
            buffer.put( color.getAlpha() );
        
        setDirty( true );
    }
    
    public final void set( TexCoordf<?> texCoord )
    {
        texCoord.writeToBuffer( buffer, false, false );
        
        setDirty( true );
    }
    
    public final void set( int index, long elemOffset, TupleNf<?> tuple )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        tuple.writeToBuffer( buffer, false, false );
        
        setDirty( true );
    }
    
    public final void set( int index, int elemSize, long elemOffset, Colorf color )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        buffer.put( color.getRed() );
        buffer.put( color.getGreen() );
        buffer.put( color.getBlue() );
        
        if ( elemSize == 4 )
            buffer.put( color.getAlpha() );
        
        setDirty( true );
    }
    
    public final void set( int index, long elemOffset, TexCoordf<?> texCoord )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        texCoord.writeToBuffer( buffer, false, false );
        
        setDirty( true );
    }
    
    public final void get( int index, long elemOffset, TupleNf<?> values )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        values.readFromBuffer( buffer );
    }
    
    public final void get( int index, int elemSize, long elemOffset, Colorf color )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        color.setRed( buffer.get() );
        color.setGreen( buffer.get() );
        color.setBlue( buffer.get() );
        
        if ( elemSize == 4 )
            color.setAlpha( buffer.get() );
    }
    
    public final void get( int index, long elemOffset, TexCoordf<?> texCoord )
    {
        buffer.position( index * getElemStride() + (int)elemOffset );
        
        texCoord.readFromBuffer( buffer );
    }
    
    
    protected GeomNioFloatData newInstance( int maxElements, int elemSize, int stride, boolean reversed )
    {
        return ( new GeomNioFloatData( maxElements, elemSize, stride, reversed ) );
    }
    
    /**
     * Creates same type of geometry data.
     * 
     * @param copy true if data should be copied
     * 
     * @return new created geom data
     */
    public GeomNioFloatData duplicateGeomData( boolean copy )
    {
        GeomNioFloatData data = newInstance( getMaxElements(), getElemSize(), getStride(), isReversed() );
        
        if ( copy )
        {
            data.buffer.clear();
            this.buffer.rewind();
            data.buffer.put( this.buffer );
            data.buffer.rewind();
            this.buffer.rewind();
        }
        
        return ( data );
    }
    
    public GeomNioFloatData( int maxElements, int elemSize, int stride, boolean reversed )
    {
        super( maxElements, maxElements * elemSize, elemSize, stride, reversed );
        
        this.floatStride = stride / 4;
        
        this.buffer = BufferUtils.createFloatBuffer( maxElements * elemSize );
    }
    
    public GeomNioFloatData( int maxElements, int elemSize, boolean reversed )
    {
        this( maxElements, elemSize, 0, reversed );
    }
}
