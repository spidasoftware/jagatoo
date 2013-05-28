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
package org.jagatoo.util.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * An Input Stream, that allows access only to a defined number of bytes.
 * 
 * @author Kevin Glass
 */
public class RestrictedLengthInputStream extends InputStream
{
    /** The number of bytes to allows access to */
    private int length;
    /** The input stream that this stream reads from */
    private InputStream in;
    
    /**
     * Construct a new input stream
     * 
     * @param in The input stream to read from
     * @param length The number of bytes to allow access to
     */
    public RestrictedLengthInputStream( InputStream in, int length ) throws IOException
    {
        this.in = in;
        if ( length > in.available() )
            this.length = in.available();
        else
            this.length = length;
    }
    
    /**
     * Read a single byte from the stream
     * 
     * @return The byte read
     */
    @Override
    public int read() throws IOException
    {
        length--;
        
        return ( in.read() );
    }
    
    /**
     * Check how many bytes are available on this stream
     * 
     * @return The number of bytes available on this stream
     */
    @Override
    public int available() throws IOException
    {
        return ( length );
    }
}
