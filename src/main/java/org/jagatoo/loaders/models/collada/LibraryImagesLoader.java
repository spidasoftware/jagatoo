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
package org.jagatoo.loaders.models.collada;

import java.util.Collection;
import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.images.LibraryImages;
import org.jagatoo.loaders.models.collada.stax.XMLImage;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryImages;
import org.jagatoo.logging.JAGTLog;

/**
 * Loader for LibraryImages
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryImagesLoader
{
    /**
     * Loads LibraryImages.
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libImages
     *            The JAXB data to load from
     */
    static void loadLibraryImages( AssetFolder colladaFile, XMLLibraryImages libImages )
    {
        LibraryImages colLibImages = colladaFile.getLibraryImages();
        HashMap<String, String> colImages = colLibImages.getImages();
        
        Collection<XMLImage> images = libImages.images.values();
        
        JAGTLog.increaseIndentation();
        for ( XMLImage image : images )
        {
            JAGTLog.debug( "TT] Found image [", image.id, ":", image.initFrom, "]" );
            colImages.put( image.id, image.initFrom );
        }
        
        JAGTLog.decreaseIndentation();
    }
}
