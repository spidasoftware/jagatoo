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

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class was designed on the base of Roedy Green LEDataInputStream
 * 
 * Thanks to Roedy Green, Canadian Mind Products mailto:roedy@mindprod.com
 * http://mindprod.com
 * 
 * @author Yann RICHET
 * @author Marvin Froehlich (aka Qudus)
 */
public class LittleEndianDataOutputStream implements DataOutput
{
    private DataOutputStream d;
    
    private byte[] w;
    
    public LittleEndianDataOutputStream( OutputStream out )
    {
        this.d = new DataOutputStream( out );
        this.w = new byte[ 8 ];
    }
    
    public final void writeShort(int v) throws IOException
    {
        w[ 0 ] = (byte)v;
        w[ 1 ] = (byte)( v >> 8 );
        
        d.write( w, 0, 2 );
    }
    
    public final void writeChar( int v ) throws IOException
    {
        w[ 0 ] = (byte)v;
        w[ 1 ] = (byte)( v >> 8 );
        
        d.write( w, 0, 2 );
    }
    
    public final void writeInt( int v ) throws IOException
    {
        w[ 0 ] = (byte)v;
        w[ 1 ] = (byte)( v >> 8 );
        w[ 2 ] = (byte)( v >> 16 );
        w[ 3 ] = (byte)( v >> 24 );
        
        d.write( w, 0, 4 );
    }
    
    public final void writeLong( long v ) throws IOException
    {
        w[ 0 ] = (byte)v;
        w[ 1 ] = (byte)( v >> 8 );
        w[ 2 ] = (byte)( v >> 16 );
        w[ 3 ] = (byte)( v >> 24 );
        w[ 4 ] = (byte)( v >> 32 );
        w[ 5 ] = (byte)( v >> 40 );
        w[ 6 ] = (byte)( v >> 48 );
        w[ 7 ] = (byte)( v >> 56 );
        
        d.write( w, 0, 8 );
    }
    
    public final void writeFloat( float v ) throws IOException
    {
        writeInt( Float.floatToIntBits( v ) );
    }
    
    public final void writeDouble( double v ) throws IOException
    {
        writeLong( Double.doubleToLongBits( v ) );
    }
    
    public final void writeChars( String s ) throws IOException
    {
        int len = s.length();
        for ( int i = 0; i < len; i++ )
        {
            writeChar( s.charAt( i ) );
        }
    }
    
    public final synchronized void write( int b ) throws IOException
    {
        d.write( b );
    }
    
    public final synchronized void write( byte[] b, int off, int len ) throws IOException
    {
        d.write( b, off, len );
    }
    
    public void flush() throws IOException
    {
        d.flush();
    }
    
    public final void writeBoolean( boolean v ) throws IOException
    {
        d.writeBoolean( v );
    }
    
    public final void writeByte( int v ) throws IOException
    {
        d.writeByte( v );
    }
    
    public final void writeBytes( String s ) throws IOException
    {
        d.writeBytes( s );
    }
    
    public final void writeUTF( String str ) throws IOException
    {
        d.writeUTF( str );
    }
    
    public final int size()
    {
        return ( d.size() );
    }
    
    public final void write( byte[] b ) throws IOException
    {
        d.write( b, 0, b.length );
    }
    
    public final void close() throws IOException
    {
        d.close();
    }
}
