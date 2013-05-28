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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.jagatoo.image.BufferedImageFactory;
import org.jagatoo.image.SharedBufferedImage;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.loaders.textures.TextureFactory;
import org.jagatoo.util.image.ImageUtility;

/**
 * Handles dealing with windows bitmap files.
 * This class doesn't handle palettized files.
 * +--------------------------------------+
 * | Bitmap File Header                   |
 * +--------------------------------------+
 * | Bitmap Information Header            |
 * +--------------------------------------+
 * | Palette Data (only in 8 bit files)   |
 * +--------------------------------------+
 * | Bitmap Data                          |
 * +--------------------------------------+
 * 
 * @author Scott Shaver
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderBMP implements TextureImageFormatLoader
{
    private static final short readShort( BufferedInputStream in ) throws IOException
    {
        int s1 = ( in.read() & 0xFF );
        int s2 = ( in.read() & 0xFF ) << 8;
        
        return ( (short)( s1 | s2 ) );
    }
    
    private static final int readInt( BufferedInputStream in ) throws IOException
    {
        int i1 = ( in.read() & 0xFF );
        int i2 = ( in.read() & 0xFF ) << 8;
        int i3 = ( in.read() & 0xFF ) << 16;
        int i4 = ( in.read() & 0xFF ) << 24;
        
        return ( i1 | i2 | i3 | i4 );
    }
    
    private static final void readPixel( BufferedInputStream in, byte[] pixel, boolean readAlpha ) throws IOException
    {
        // Swap R and B, since BMP stores them swapped!
        pixel[ 2 ] = (byte)in.read();
        pixel[ 1 ] = (byte)in.read();
        pixel[ 0 ] = (byte)in.read();
        
        if ( readAlpha )
            pixel[ 3 ] = (byte)in.read();
    }
    
    /*
    public void printHeaders()
    {
        System.out.println( "-----------------------------------" );
        System.out.println( "File Header" );
        System.out.println( "-----------------------------------" );
        System.out.println( "            File Size:" + FHsize );
        System.out.println( "           Reserved 1:" + FHreserved1 );
        System.out.println( "           Reserved 2:" + FHreserved2 );
        System.out.println( "          Data offset:" + FHoffsetBytes );
        System.out.println( "-----------------------------------" );
        System.out.println( "Info Header" );
        System.out.println( "-----------------------------------" );
        System.out.println( "     Info Header Size:" + IHsize );
        System.out.println( "                Width:" + IHwidth );
        System.out.println( "               Height:" + IHheight );
        System.out.println( "               Planes:" + IHplanes );
        System.out.println( "                  BPP:" + IHbitCount );
        System.out.println( "          Compression:" + IHcompression );
        System.out.println( "           Image size:" + IHsizeImage );
        System.out.println( "     Pels Per Meter X:" + IHxpelsPerMeter );
        System.out.println( "     Pels Per Meter Y:" + IHypelsPerMeter );
        System.out.println( "     # of Colors Used:" + IHcolorsUsed );
        System.out.println( "# of Important Colors:" + IHcolorsImportant );
    }
    */
    
    private static void transferScaledBytes( byte[] unscaledData, int bytesPerPixel, ByteBuffer bb, int orgWidth, int orgHeight, int width, int height )
    {
        SharedBufferedImage sbi = BufferedImageFactory.createSharedBufferedImage( orgWidth, orgHeight, bytesPerPixel, null, unscaledData );
        
        SharedBufferedImage sbi_scaled = ImageUtility.scaleImage( sbi, width, height, (bytesPerPixel == 4 ) );
        
        byte[] scaledData = sbi_scaled.getSharedData();
        
        for ( int i = 0; i < scaledData.length; i += bytesPerPixel )
        {
            // Swap R and B.
            bb.put( scaledData[ i + 2 ] );
            bb.put( scaledData[ i + 1 ] );
            bb.put( scaledData[ i + 0 ] );
            
            if ( bytesPerPixel == 4 )
                bb.put( scaledData[ i + 3 ] );
        }
    }
    
    private static byte[][] readPalette( BufferedInputStream in, int IHbitCount, int IHcolorsUsed ) throws IOException
    {
        byte[][] palette;
        
        if ( IHcolorsUsed == 0 )
        {
            if ( ( IHbitCount == 1 ) || ( IHbitCount == 4 ) || ( IHbitCount == 8 ) )
            {
                int paletteSize = ( 1 << IHbitCount );
                palette = new byte[ paletteSize ][ 4 ];
                
                for ( int i = 0; i < paletteSize; i++ )
                {
                    palette[ i ] = new byte[ 4 ];
                    in.read( palette[ i ], 0, 4 );
                    
                    byte tmp = palette[ i ][ 0 ];
                    palette[ i ][ 0 ] = palette[ i ][ 2 ];
                    palette[ i ][ 2 ] = tmp;
                }
            }
            else
            {
                palette = null;
            }
        }
        else
        {
            palette = new byte[ IHcolorsUsed ][ 4 ];
            
            for ( int i = 0; i < IHcolorsUsed; i++ )
            {
                palette[ i ] = new byte[ 4 ];
                in.read( palette[ i ], 0, 4 );
                
                byte tmp = palette[ i ][ 0 ];
                palette[ i ][ 0 ] = palette[ i ][ 2 ];
                palette[ i ][ 2 ] = tmp;
            }
        }
        
        return ( palette );
    }
    
    /**
     * {@inheritDoc}
     */
    public AbstractTextureImage loadTextureImage( BufferedInputStream in, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        try
        {
            short magicNumber = readShort( in );
            
            // FHtype
            // make sure it's windows bitmap file
            if ( magicNumber != 19778 )
            {
                return ( null );
            }
            
            // read the file header
            /*int FHsize = */readInt( in );
            /*short FHreserved = */readInt( in );
            /*int FHoffsetBytes = */readInt( in );
            
            // read the info header
            /*int IHsize = */readInt( in );
            int IHwidth = readInt( in );
            int IHheight = readInt( in );
            /*short IHplanes = */readShort( in );
            short IHbitCount = readShort( in );
            int IHcompression = readInt( in );
            int IHsizeImage = readInt( in );
            /*int IHxpelsPerMeter = */readInt( in );
            /*int IHypelsPerMeter = */readInt( in );
            int IHcolorsUsed = readInt( in );
            /*int IHcolorsImportant = */readInt( in );
            
            int orgWidth = IHwidth;
            int orgHeight = Math.abs( IHheight );
            int bytesPerPixel = IHbitCount / 8;
            
            boolean palettized;
            switch ( IHbitCount )
            {
                case 1:
                    palettized = true;
                    break;
                case 4:
                    palettized = true;
                    break;
                case 8:
                    palettized = true;
                    break;
                case 16:
                    palettized = false;
                    // Not currently supported!
                    return ( null );
                case 24:
                    palettized = false;
                    break;
                case 32:
                    palettized = false;
                    break;
                default:
                    throw new Error( "Illegal color-size detected: " + IHbitCount );
            }
            
            int imageDataLength = IHsizeImage;
            
            byte[][] palette = null;
            if ( palettized )
            {
                palette = readPalette( in, IHbitCount, IHcolorsUsed );
                
                if ( palette == null )
                    palettized = false;
                else
                    bytesPerPixel = 3;
                
                // Not currently supported!
                //return ( null );
            }
            else
            {
                imageDataLength = IHwidth * IHheight * bytesPerPixel;
            }
            
            switch ( IHcompression )
            {
                case 0: // BI_RGB
                    break;
                case 1: // BI_RLE8
                    // Not currently supported!
                    return ( null );
                case 2: // BI_RLE4
                    // Not currently supported!
                    return ( null );
                case 3: // BI_BITFIELDS
                    // Not currently supported!
                    return ( null );
            }
            
            int width = ImageUtility.roundUpPower2( orgWidth );
            int height = ImageUtility.roundUpPower2( orgHeight );
            
            AbstractTextureImage image = texFactory.createTextureImage( width, height, orgWidth, orgHeight, ( acceptAlpha ? bytesPerPixel : 3 ) );
            ByteBuffer bb = image.getDataBuffer();
            //bb.position( 0 );
            bb.limit( bb.capacity() );
            
            final int byteOffset0 = bb.position();
            
            final int dstBytesPerPixel = image.getFormat().getPixelSize();
            
            byte[] imageData = null;
            int dstByteOffset = 0;
            if ( ( ( width != orgWidth ) || ( height != orgHeight ) ) && allowStreching )
            {
                imageData = new byte[ orgWidth * orgHeight * bytesPerPixel ];
            }
            
            byte[] pixel;
            if ( palettized )
                pixel = new byte[ 3 ];
            else
                pixel = new byte[ bytesPerPixel ];
            
            int readStep = bytesPerPixel;
            if ( IHbitCount <= 8 )
                readStep = 1;
            
            for ( int i = 0; i < imageDataLength; i += readStep )
            {
                switch ( IHbitCount )
                {
                    case 1:
                        int v1 = in.read() & 1;
                        System.arraycopy( palette[ v1 ], 0, pixel, 0, 4 );
                        break;
                    case 4:
                        int v4 = in.read() & 15;
                        System.arraycopy( palette[ v4 ], 0, pixel, 0, 4 );
                        break;
                    case 8:
                        int v8 = in.read() & 255;
                        System.arraycopy( palette[ v8 ], 0, pixel, 0, 3 );
                        break;
                    case 16:
                        break;
                    case 24:
                        readPixel( in, pixel, false );
                        break;
                    case 32:
                        readPixel( in, pixel, true );
                        break;
                }
                
                if ( imageData == null )
                {
                    bb.put( pixel[ 0 ] );
                    bb.put( pixel[ 1 ] );
                    bb.put( pixel[ 2 ] );
                    
                    if ( ( bytesPerPixel == 4 ) && acceptAlpha )
                    {
                        bb.put( pixel[ 3 ] );
                    }
                }
                else
                {
                    // Swap R ang B back again, since we want to use it in a BufferedImage!
                    imageData[ dstByteOffset + 0 ] = pixel[ 2 ];
                    imageData[ dstByteOffset + 1 ] = pixel[ 1 ];
                    imageData[ dstByteOffset + 2 ] = pixel[ 0 ];
                    
                    if ( ( bytesPerPixel == 4 ) && acceptAlpha )
                    {
                        imageData[ dstByteOffset + 3 ] = pixel[ 3 ];
                    }
                    
                    dstByteOffset += bytesPerPixel;
                }
            }
            
            if ( imageData != null )
            {
                transferScaledBytes( imageData, dstBytesPerPixel, bb, orgWidth, orgHeight, width, height );
            }
            
            bb.position( 0 );
            bb.limit( byteOffset0 + ( width * height * dstBytesPerPixel ) );
            
            return ( image );
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
        
        return ( null );
    }
}
