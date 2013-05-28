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
package org.jagatoo.loaders.textures.formats;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.loaders.textures.TextureFactory;
import org.jagatoo.loaders.textures.pixelprocessing.PixelProcessor;
import org.jagatoo.util.image.ImageUtility;

/**
 * This {@link TextureImageFormatLoader} is used as a fallback-loader,
 * if not other loader was capable of loading the requested image format.
 * 
 * @author Matthias Mann
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderImageIOBufferedImage implements TextureImageFormatLoader
{
    private static BufferedImage loadFromStream( InputStream in ) throws IOException
    {
        BufferedImage result = ImageIO.read( in );
        
        return ( result );
    }
    
    private static AbstractTextureImage createTextureImage( BufferedImage img, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        final int orgWidth = img.getWidth();
        final int orgHeight = img.getHeight();
        
        final int width;
        final int height;
        
        if ( allowStreching )
        {
            width = ImageUtility.roundUpPower2( img.getWidth() );
            height = ImageUtility.roundUpPower2( img.getHeight() );
        }
        else
        {
            width = img.getWidth();
            height = img.getHeight();
        }
        
        final boolean alpha = img.getColorModel().hasAlpha() && acceptAlpha;
        
        if ( ( orgWidth != width ) || ( orgHeight != height ) )
        {
            img = ImageUtility.scaleImage( img, width, height, alpha );
        }
        
        //AbstractTexture.Format format = ( alpha ? AbstractTexture.Format.RGBA : AbstractTexture.Format.RGB );
        
        final PixelProcessor pp = PixelProcessor.selectPixelProcessor( img, acceptAlpha );
        //AbstractTextureImage ti = pp.createTextureImage( img, orgWidth, orgHeight, format, flipVertically, texFactory );
        
        AbstractTextureImage ti = texFactory.createTextureImage( width, height, orgWidth, orgHeight, pp.getPixelSize() );
        
        ByteBuffer bb = ti.getDataBuffer();
        bb.limit( bb.capacity() );
        
        pp.readImageData( img, 0, 0, width, height, bb, bb.position(), flipVertically );
        
        return ( ti );
    }
    
    /**
     * {@inheritDoc}
     */
    public AbstractTextureImage loadTextureImage( BufferedInputStream in, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        BufferedImage img = loadFromStream( in );
        
        AbstractTextureImage ti = createTextureImage( img, acceptAlpha, flipVertically, allowStreching, texFactory );
        
        return ( ti );
    }
    
}
