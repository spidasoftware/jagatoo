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

/**
 * Base abstract class for all GeomData implementations.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class GeomNioData
{
    private final int maxElems;
    private final int maxSize;
    private final int elemSize;
    private final int stride;
    private final boolean reversed;
    
    private int freeIndex = 0;
    
    protected boolean dirty = true;
    
    public final int getMaxElements()
    {
        return ( maxElems );
    }
    
    public final int getMaxSize()
    {
        return ( maxSize );
    }
    
    public final int getElemSize()
    {
        return ( elemSize );
    }
    
    public final int getStride()
    {
        return ( stride );
    }
    
    public final boolean isReversed()
    {
        return ( reversed );
    }
    
    /**
     * Used by the renderer to set the data as non-dirty, plus for any function
     * which wishes to dirty the data.
     * 
     * @param dirty
     */
    protected void setDirty( boolean dirty )
    {
        this.dirty = dirty;
    }
    
    /**
     * Allocates elements to be written to. The number returned is the index
     * that can be written to.
     * 
     * @param size
     */
    int alloc( int size )
    {
        final int a;
        
        if ( reversed )
        {
            a = freeIndex - size;
            freeIndex = a;
        }
        else
        {
            a = freeIndex;
            freeIndex += size;
        }
        
        return ( a );
    }
    
    /**
     * Resets the buffer to zero and prepares to receive data from the various
     * set() calls. In some implementations this will destroy data.
     */
    public void start()
    {
        if ( reversed )
        {
            freeIndex = maxSize;
        }
        else
        {
            freeIndex = 0;
        }
    }
    
    public int getInitialIndex()
    {
        if ( reversed )
        {
            return ( freeIndex );
        }
        
        return ( 0 );
    }
    
    public int getCount()
    {
        return ( maxSize - freeIndex );
    }
    
    public GeomNioData( int maxElems, int maxSize, int elemSize, int stride, boolean reversed )
    {
        this.maxElems = maxElems;
        this.maxSize = maxSize;
        this.elemSize = elemSize;
        this.stride = stride;
        this.reversed = reversed;
    }
}
