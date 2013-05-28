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

import java.nio.IntBuffer;

import org.jagatoo.util.nio.BufferUtils;

/**
 * An implementation of the float data holder which uses a direct
 * NIO buffer.
 * 
 * @author David Yazel
 */
public class GeomNioIntData extends GeomNioData
{
    private final IntBuffer buffer;
    
    /**
     * @return the NIO int buffer underlying this. If the underlying
     * format is not a float buffer then it will be converted.
     */
    public final IntBuffer getBuffer()
    {
        return ( buffer );
    }
    
    /**
     * @return an array with all the ints assigned in the last draw cycle.
     * In some implementations this might return a copy, so only use if you
     * have to. The data should not be changed once it is retrieved and should
     * be considered read only.
     */
    public final int[] getData()
    {
        int f[] = new int[ buffer.limit() ];
        buffer.rewind();
        buffer.get( f );
        
        return ( f );
    }
    
    @Override
    public final void start()
    {
        buffer.clear();
        setDirty( true );
    }
    
    public final void end()
    {
    }
    
    /**
     * Special optimized way to set a bulk amount of ints right into
     * the data, starting at the index specified.
     */
    public final void set( int[] a, int start, int length )
    {
        buffer.put( a, start, length );
        buffer.rewind();
        setDirty( true );
    }
    
    public final void set( int index, int[] a, int start, int length )
    {
        buffer.position( index );
        buffer.put( a, start, length );
        buffer.position( index ); // JSR231 back to where we were
        setDirty( true );
    }
    
    public final void set( int index, int[] a )
    {
        buffer.position( index );
        buffer.put( a );
        buffer.position( index ); // JSR231 back to where we were
        setDirty( true );
    }
    
    public final void set( int index, int i )
    {
        buffer.position( index );
        buffer.put( i );
        buffer.position( index ); // JSR231 back to where we were
        setDirty( true );
    }
    
    /**
     * Gets 3 values from the underlying buffer starting
     * at position (3 * index).
     * 
     * @param index
     * @param value
     */
    public final int get( int index )
    {
        buffer.position( index );
        return ( buffer.get() );
    }
    
    /**
     * Gets 3 values from the underlying buffer starting
     * at position (3 * index).
     * 
     * @param index
     * @param value
     */
    public final void get( int i0, int[] index )
    {
        buffer.position( i0 );
        buffer.get( index );
    }
    
    protected GeomNioIntData newInstance( int maxElems, int elemSize, int stride, boolean reversed )
    {
        return ( new GeomNioIntData( maxElems, elemSize, stride, reversed ) );
    }
    
    /**
     * Create same type of geometry data.
     * 
     * @param copy true if data should be copied
     * 
     * @return new created geom data
     */
    GeomNioIntData duplicateGeomData( boolean copy )
    {
        GeomNioIntData data = newInstance( getMaxElements(), getElemSize(), getStride(), isReversed() );
        if ( copy )
        {
            data.buffer.clear();
            data.buffer.put( this.buffer );
            data.buffer.rewind();
            this.buffer.rewind();
        }
        
        return ( data );
    }
    
    public GeomNioIntData( int maxElems, int elemSize, int stride, boolean reversed )
    {
        super( maxElems, maxElems * elemSize, elemSize, stride, reversed );
        
        this.buffer = BufferUtils.createIntBuffer( maxElems * elemSize );
    }
    
    public GeomNioIntData( int maxElems, int elemSize, boolean reversed )
    {
        this( maxElems, elemSize, 0, reversed );
    }
}
