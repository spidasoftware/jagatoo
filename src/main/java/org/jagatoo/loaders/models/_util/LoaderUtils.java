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
package org.jagatoo.loaders.models._util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LoaderUtils
{
    public static final URL extractBaseURL( URL url ) throws IOException
    {
        String file = url.getFile();
        String proto = url.toExternalForm().substring( 0, url.toExternalForm().length() - file.length() );
        
        int lastSlashPos = file.lastIndexOf( '/' );
        if ( lastSlashPos < 0 )
            return ( null );
        else if ( lastSlashPos == file.length() - 1 )
            throw new IllegalArgumentException( "You cannot pass a directory as the url parameter!" );
        else
            return ( new URL( proto + file.substring( 0, lastSlashPos + 1 ) ) );
    }
    
    public static final URL extractBaseURL( File file ) throws IOException
    {
        if ( file.isDirectory() )
            throw new IllegalArgumentException( "You cannot pass a directory as the file parameter!" );
        
        return ( file.getAbsoluteFile().getParentFile().toURI().toURL() );
    }
    
    public static final URL extractBaseURL( String filename ) throws IOException
    {
        return ( extractBaseURL( new File( filename ) ) );
    }
    
    public static final String extractFilenameWithoutExt( URL url, String ext )
    {
        String filename = url.getFile();
        
        //return ( filename.substring( filename.lastIndexOf( '/' ) + 1, filename.length() - ext.length() - 1 ) );
        
        int lastSlashPos = filename.lastIndexOf( '/' );
        if ( lastSlashPos < 0 )
        {
            return ( filename.substring( 0, filename.length() - ext.length() - 1 ) );
        }
        
        return ( filename.substring( lastSlashPos + 1, filename.length() - ext.length() - 1 ) );
    }
    
    public static final String extractFilenameWithoutExt( URL url )
    {
        String filename = url.getFile();
        
        int lastSlashPos = filename.lastIndexOf( '/' );
        if ( lastSlashPos < 0 )
        {
            int lastDotPos = filename.lastIndexOf( '.' );
            
            if ( lastDotPos < 0 )
                return ( filename );
            
            return ( filename.substring( 0, lastDotPos ) );
        }
        
        int lastDotPos = filename.lastIndexOf( '.' );
        
        if ( lastDotPos < 0 )
            return ( filename.substring( lastSlashPos + 1, filename.length() ) );
        
        return ( filename.substring( lastSlashPos + 1, lastDotPos ) );
    }
}
