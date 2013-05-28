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
 * Java MD3 Model Viewer - A Java based Quake 3 model viewer.
 * Copyright (C) 1999  Erwin 'KLR8' Vervaet
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jagatoo.util.streams;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Data input class with low level binary IO operations. The
 * methods in this class read little endian data and return Java
 * big endian data.
 * 
 * <p>Reading of bytes is unaffected. Also, the char io methods
 * act the same as in the standard DataInputStream class. So in
 * the case that multiple bytes are read in readLine() or readUTF(),
 * the endian order is not switched!
 * 
 * @see java.io.DataInputStream
 * 
 * @author Erwin Vervaet (klr8@fragland.net)
 * @author Marvin Froehlich (aka Qudus)
 */
public class LittleEndianDataInputStream extends FilterInputStream implements DataInput
{
    private final DataInputStream din;
    
    private int pointer = 0;
    
    public final int getPointer()
    {
        return ( pointer );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException
    {
        int i = super.read();
        
        pointer++;
        
        return ( i );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read( byte[] b, int off, int len ) throws IOException
    {
        int result = super.read( b, off, len );
        
        if ( result > 0 )
            pointer += result;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public void readFully( byte[] b ) throws IOException
    {
        din.readFully( b );
        
        pointer += b.length;
    }
    
    /**
     * {@inheritDoc}
     */
    public void readFully( byte[] b, int off, int len ) throws IOException
    {
        din.readFully( b, off, len );
        
        pointer += len;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long skip( long n ) throws IOException
    {
        long result = super.skip( n );
        
        if ( result > 0L )
            pointer += (int)result;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public int skipBytes( int n ) throws IOException
    {
        int result = din.skipBytes( n );
        
        if ( result > 0 )
            pointer += n;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean readBoolean() throws IOException
    {
        boolean result = din.readBoolean();
        
        pointer++;;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public byte readByte() throws IOException
    {
        byte result = din.readByte();
        
        pointer++;;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public int readUnsignedByte() throws IOException
    {
        int result = din.readUnsignedByte();
        
        pointer++;;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public short readShort() throws IOException
    {
        int low = din.read();
        int high = din.read();
        
        pointer += 2;
        
        return ( (short)( ( high << 8 ) | ( low & 0xFF ) ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public int readUnsignedShort() throws IOException
    {
        int low = din.read();
        int high = din.read();
        
        pointer += 2;
        
        return ( ( ( high & 0xFF ) << 8 ) | ( low & 0xFF ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public char readChar() throws IOException
    {
        char result = din.readChar();
        
        pointer += 2;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public int readInt() throws IOException
    {
        int i3 = din.read();
        int i2 = din.read();
        int i1 = din.read();
        int i0 = din.read();
        
        pointer += 4;
        
        return ( ( ( i0 & 0xFF ) << 24 ) | ( ( i1 & 0xFF ) << 16 ) | ( ( i2 & 0xFF ) << 8 ) | ( i3 & 0xFF ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public long readLong() throws IOException
    {
        int i7 = din.read();
        int i6 = din.read();
        int i5 = din.read();
        int i4 = din.read();
        int i3 = din.read();
        int i2 = din.read();
        int i1 = din.read();
        int i0 = din.read();
        
        pointer += 8;
        
        return ( ( (long)( i0 & 0xff ) << 56 ) |
                ( (long)( i1 & 0xff ) << 48 ) |
                ( (long)( i2 & 0xff ) << 40 ) |
                ( (long)( i3 & 0xff ) << 32 ) |
                ( (long)( i4 & 0xff ) << 24 ) |
                ( (long)( i5 & 0xff ) << 16 ) |
                ( (long)( i6 & 0xff ) <<  8 ) |
                ( (long)( i7 & 0xff ) )
              );
    }
    
    /**
     * {@inheritDoc}
     */
    public float readFloat() throws IOException
    {
        return ( Float.intBitsToFloat( readInt() ) );
    }
    
    /**
     * {@inheritDoc}
     */
    public double readDouble() throws IOException
    {
        return ( Double.longBitsToDouble( readLong() ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "deprecation" )
    public final String readLine() throws IOException
    {
        String result = din.readLine();
        
        pointer += result.length() + 1;
        
        return ( result );
    }
    
    /**
     * {@inheritDoc}
     */
    public String readUTF() throws IOException
    {
        // The pointer offset cannot be predicted!
        
        return ( din.readUTF() );
    }
    
    /**
     * Reads a String from the InputStream.
     * The string is expected to be 0-terminated.
     * 
     * @param in
     * @param maxLength
     * @param alwaysReadMaxLength
     * 
     * @return the read String.
     * 
     * @throws IOException
     */
    public final String readCString( int maxLength, boolean alwaysReadMaxLength ) throws IOException
    {
        String result = StreamUtils.readCString( this, maxLength, alwaysReadMaxLength );
        
        /*
        if ( alwaysReadMaxLength )
            pointer += maxLength;
        else
            pointer += Math.min( result.length() + 1, maxLength );
        */
        
        return ( result );
    }
    
    public LittleEndianDataInputStream( InputStream in )
    {
        super( in );
        
        this.din = new DataInputStream( in );
    }
}
