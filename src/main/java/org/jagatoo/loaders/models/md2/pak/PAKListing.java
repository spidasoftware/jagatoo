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
package org.jagatoo.loaders.models.md2.pak;

import java.io.IOException;
import java.io.DataInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;

import org.jagatoo.util.streams.LittleEndianDataInputStream;

/**
 * The directory listing from inside a Quake 2 PAK file.
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class PAKListing
{
    private class FileIndex
    {
        /** The index into the pak file of this file */
        public int offset;
        /** The length of this file */
        public int length;
        
        /**
         * Creates a new file index.
         * 
         * @param offset The offset into the pak file of this file
         * @param length The length of the file
         */
        public FileIndex( int offset, int length )
        {
            this.offset = offset;
            this.length = length;
        }
    }
    
    
    private List<String> filenames = new ArrayList<String>();
    
    /** The map from file name to file index */
    private Map<String, FileIndex> map = new HashMap<String, FileIndex>();
    
    /**
     * @return a List of the names of all contained files in the PAKArchive
     */
    public List<String> getFilenames()
    {
        return ( filenames );
    }
    
    /**
     * Retrieves the offset to a given file name. 
     * 
     * @return The offset to the given file or -1 if file not found
     */
    public int getFileOffset( String name )
    {
        FileIndex index = map.get( name );
        
        if ( index == null )
            return ( -1 );
        
        return ( index.offset );
    }
    
    /**
     * Retrieves the length of a given file.
     * 
     * @return The length of the file or -1 if file not found
     */
    public int getFileLength( String name )
    {
        FileIndex index = map.get( name );
        
        if ( index == null )
            return ( -1 );
        
        return ( index.length );
    }
    
    /**
     * Reads a single file index and add it to this listing.
     * 
     * @param in The input stream to read the lisitng from
     */
    private void readFileIndex( LittleEndianDataInputStream in ) throws IOException
    {
        byte[] name = new byte[ 56 ];
        in.read( name );
        
        int i;
        for ( i = 0; i < 56; i++ )
        {
            if (name[ i ] == 0)
                break;
        }
        
        String filename = new String( name, 0, i );
        FileIndex index = new FileIndex( in.readInt(), in.readInt() );
        
        filenames.add( filename );
        map.put( filename, index );
    }
    
    /** 
     * Creates new PAKListing. 
     * 
     * @param header The PAK header of this file
     * @param totalData The file to read the listing from
     */
    public PAKListing( PAKHeader header, byte[] totalData ) throws IOException
    {
        ByteArrayInputStream fin = new ByteArrayInputStream( totalData );
        byte[] data = new byte[ header.getListingLength() ];
        
        fin.skip( header.getListingOffset() );        
        fin.read( data );
        fin.close();
        
        LittleEndianDataInputStream in = new LittleEndianDataInputStream( new DataInputStream( new ByteArrayInputStream( data ) ) );
        
        for (int i = 0; i < header.getListingLength() / 64; i++)
        {
            readFileIndex( in );
        }
        
        in.close();
    }
}
