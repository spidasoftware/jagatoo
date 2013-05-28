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
package org.jagatoo.loaders.models.bsp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.loaders.models.bsp.lumps.BSPLump;
import org.jagatoo.util.streams.StreamUtils;

/**
 * Represents a BSP source file.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
class BSPFile
{
    private final URL                  baseURL;
    private final BufferedInputStream  in;
    
    private final String               filename;
    private char[]                     ID;
    private int                        version;
    
    /**
     * An array of all lumps in this directory.
     */
    protected BSPLump[] lumps;
    
    public final String getName()
    {
        return ( filename );
    }
    
    public final URL getBaseURL()
    {
        return ( baseURL );
    }
    
    public final BufferedInputStream getInputStrema()
    {
        return ( in );
    }
    
    public final void resetPointer() throws IOException
    {
        in.reset();
    }
    
    public final void seek( int lump ) throws IOException
    {
        in.reset();
        
        long toSkip = lumps[ lump ].offset;
        
        StreamUtils.skipBytes( in, toSkip );
    }
    
    public final void skipBytes( int numBytes ) throws IOException
    {
        StreamUtils.skipBytes( in, numBytes );
    }
    
    public final byte readByte() throws IOException
    {
        return ( (byte)in.read() );
    }
    
    public final char readChar() throws IOException
    {
        return ( (char)readByte() );
    }
    
    public final int readInt() throws IOException
    {
        return ( StreamUtils.readSwappedInt( in ) );
    }
    
    public final short readShort() throws IOException
    {
        return ( StreamUtils.readSwappedShort( in ) );
    }
    
    public final int readUnsignedShort() throws IOException
    {
        return ( StreamUtils.readSwappedUnsignedShort( in ) );
    }
    
    public final float readFloat() throws IOException
    {
        return ( Float.intBitsToFloat( StreamUtils.readSwappedInt( in ) ) );
    }
    
    public final void readFully( byte[] data ) throws IOException
    {
        in.read( data );
    }
    
    public final void readFully( byte[] data, int offset, int length ) throws IOException
    {
        in.read( data, offset, length );
    }
    
    public final byte[] readFully( int count ) throws IOException
    {
        byte[] data = new byte[ count ];
        
        in.read( data );
        
        return ( data );
    }
    
    public final void readFully( char[] data, int count ) throws IOException
    {
        for ( int i = 0; i < count; i++ )
        {
            data[ i ] = readChar();
        }
    }
    
    protected final void readDirectory() throws IOException
    {
        final int lumpCount;
        switch ( version )
        {
            case 46:
                lumpCount = 17;
                break;
            default:
                lumpCount = 15;
                break;
        }
        
        this.lumps = new BSPLump[ lumpCount ];
        
        for ( int i = 0; i < lumpCount; i++ )
        {
            lumps[ i ] = new BSPLump();
            lumps[ i ].offset = readInt();
            lumps[ i ].length = readInt();
            
            //Output Lump Offset and Length
            //System.err.println( i + " - " + lumps[ i ].offset + " - " + lumps[ i ].length);
        }
    }
    
    public final void close() throws IOException
    {
        in.close();
    }
    
    public final char[] getID()
    {
        return ( ID );
    }
    
    public final int getVersion()
    {
        return ( version );
    }
    
    private void checkVersion( int version ) throws IncorrectFormatException
    {
        /*
        if ( version != 0x2e )
            throw new IncorrectFormatException( "Invalid Quake 3 BSP file" ) );
        */
        
        if ( ( version != 46 ) && ( version != 30 ) )
        {
            String errorCodeString = "Unable to read ";
            
            switch ( version ) 
            {
                case 29:
                    errorCodeString += "Quake1 BSP file. version = " + version;
                    break;
                case 38:
                    errorCodeString += "Quake2 BSP file. ID = " + new String( ID ) +" - version = " + version;
                    break;
                /*
                case 30:
                    errorCodeString += "Half-Life1 BSP file. version = " + version;
                    break;
                */
                case 19:   
                case 20:
                    errorCodeString += "Half-Life2 BSP file. ID = " + new String( ID ) + " - version = " + version;
                    break;
                default:
                    errorCodeString = "Unknown BSP version number or BSP format.";
                    break;
            }
            
            throw new IncorrectFormatException( errorCodeString );
        }
    }
    
    private void checkHeader() throws IOException, IncorrectFormatException
    {
        // read bsp "magic number" ( "IBSP" or "VBSP" )
        this.ID = new char[ 4 ];
        this.ID[ 0 ] = readChar();
        this.ID[ 1 ] = readChar();
        this.ID[ 2 ] = readChar();
        this.ID[ 3 ] = readChar();
        
        if ( !new String( ID ).substring( 1 ).equals( "BSP" ) )
        {
            //throw new IncorrectFormatException( "The read file is not a valid BSP level file." );
            
            /*
             * We need to return the file-pointer to the beginning of the file
             * to load the version, since HalfLife files don't have the
             * "magic number"!
             */
            resetPointer();
        }
        
        // read bsp version
        this.version = readInt();
        
        checkVersion( version );
    }
    
    protected BSPFile( InputStream in, String filename, URL baseURL ) throws IOException
    {
        super();
        
        this.filename = filename;
        this.baseURL = baseURL;
        
        if ( in instanceof BufferedInputStream )
            this.in = (BufferedInputStream)in;
        else
            this.in = new BufferedInputStream( in );
        
        this.in.mark( Integer.MAX_VALUE );
        
        checkHeader();
        
        readDirectory();
    }
}
