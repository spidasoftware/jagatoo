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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import org.jagatoo.util.streams.RestrictedLengthInputStream;
import org.jagatoo.util.streams.StreamUtils;

/**
 * An archive of files in Quake 1/2 PAK File format
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class PAKArchive
{
    /** The file listing of this pak */
    private PAKListing listing;
    /** The physical PAK file */
    private byte[] data;
    
    /**
     * Retrieve access to a specified file in this pak
     * 
     * @return The name of the file to retrieve access to
     */
    public InputStream getFile( String filename ) throws IOException
    {
        int offset = listing.getFileOffset( filename );
        int length = listing.getFileLength( filename );
        
        if ( offset == -1 )
            throw new FileNotFoundException( "File " + filename + " not found in PAK" );
        
        InputStream in = new ByteArrayInputStream( data );
        in.skip( offset );
        
        return ( new RestrictedLengthInputStream( in, length ) );
    }
    
    /**
     * @return a List of the names of all contained files in the PAKArchive
     */
    public List<String> getFilenames()
    {
        return ( listing.getFilenames() );
    }
    
    /** 
     * Creates new PAKArchive.
     * 
     * @param in the InputStream to read as a PAK
     */
    public PAKArchive( InputStream in ) throws IOException
    {
        this.data = StreamUtils.buildByteArray( in );
        
        PAKHeader header = new PAKHeader( data );
        listing = new PAKListing( header, data );        
    }
    
    /** 
     * Creates new PAKArchive.
     * 
     * @param url the URL to read as a PAK
     */
    public PAKArchive( URL url ) throws IOException
    {
        this( url.openStream() );
    }
    
    /** 
     * Creates new PAKArchive.
     * 
     * @param filename The file to read as a PAK
     */
    public PAKArchive( String filename ) throws IOException
    {
        this( new FileInputStream( filename ) );
    }
}
