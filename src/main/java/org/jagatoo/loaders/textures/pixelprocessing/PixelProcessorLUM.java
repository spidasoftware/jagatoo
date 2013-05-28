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
package org.jagatoo.loaders.textures.pixelprocessing;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.nio.ByteBuffer;

import org.jagatoo.opengl.enums.TextureImageFormat;

/**
 * Luminance Pixel Processor
 * 
 * @author Matthias Mann
 * @author Marvin Froehlich (aka Qudus);
 */
public class PixelProcessorLUM extends PixelProcessor
{
    private static final PixelProcessorLUM instance = new PixelProcessorLUM();
    
    public static PixelProcessorLUM getInstance()
    {
        return ( instance );
    }
    
    @Override
    public final TextureImageFormat getTextureImageFormat()
    {
        return ( TextureImageFormat.LUMINANCE );
    }
    
    @Override
    public int readImageData( BufferedImage img, int startX, int startY, int width, int height, ByteBuffer trg, int trgOffset, boolean flipVertically )
    {
        ColorModel cm = img.getColorModel();
        Raster r = img.getRaster();
        
        final int trgOffset0 = trgOffset;
        trg.position( trgOffset0 );
        trg.limit( trg.capacity() );
        
        switch ( img.getType() )
        {
            case BufferedImage.TYPE_BYTE_GRAY:
            {
                byte[] data = new byte[ width ];
                
                for ( int y = 0; y < height; y++ )
                {
                    int realY = startY + ( flipVertically ? ( height - 1 - y ) : y );
                    
                    r.getDataElements( startX, realY, width, 1, data );
                    trg.position( trgOffset );
                    trg.put( data, 0, width );
                    
                    trgOffset += width;
                }
                break;
            }
                
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_RGB:
            {
                int[] data = new int[ width ];
                for ( int y = 0; y < height; ++y )
                {
                    int trgOffsetX = trgOffset;
                    int realY = startY + ( flipVertically ? ( height - 1 - y ) : y );
                    
                    r.getDataElements( startX, realY, width, 1, data );
                    
                    for ( int x = 0; x < width; ++x )
                    {
                        int rgb = data[x];
                        byte value = (byte)((((rgb >> 16) & 0xFF) + ((rgb >> 8) & 0xFF) + ((rgb) & 0xFF)) / 3);
                        
                        trg.put( trgOffsetX++, value );
                    }
                    
                    trgOffset += width;
                }
                break;
            }
                
            default:
            {
                Object data = null;
                for ( int y = 0; y < height; ++y )
                {
                    int trgOffsetX = trgOffset;
                    int realY = startY + ( flipVertically ? ( height - 1 - y ) : y );
                    
                    for ( int x = 0; x < width; ++x )
                    {
                        data = r.getDataElements( startX + x, realY, 1, 1, data );
                        
                        /*
                         * don't use getRGB() because it will itself only call
                         * getRed(), getGreen(), getBlue() and getAlpha() ! and
                         * we don't need alpha. This has been proven as the
                         * fasted method
                         */
                        byte value = (byte)( ( cm.getRed( data ) + cm.getGreen( data ) + cm.getBlue( data ) ) / 3 );
                        
                        trg.put( trgOffsetX++, value );
                    }
                    
                    trgOffset += width;
                }
            }
        }
        
        int dataSize = height * 1;
        
        trg.position( 0 );
        trg.limit( trgOffset0 + dataSize );
        
        return ( dataSize );
    }
    
    /**
     * Creates a new instance of PixelProcessorLUM
     */
    private PixelProcessorLUM()
    {
        super( 1 );
    }
}
