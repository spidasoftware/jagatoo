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
import org.jagatoo.loaders.textures.formats.TextureImageFormatLoader;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.util.image.ImageUtility;

/**
 * Loads TextureImages from GIF images.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderGIF implements TextureImageFormatLoader
{
    private static final int HEADER_FORMAT_INVALID = 0;
    private static final int HEADER_FORMAT_GIF87a = 1;
    private static final int HEADER_FORMAT_GIF89a = 2;
    
    private static final int[] LZW_CODE_MASK = { 0x001, 0x003, 0x007, 0x00F, 0x01F, 0x03F, 0x07F, 0x0FF, 0x1FF, 0x3FF, 0x7FF, 0xFFF };
    
    private final class GIFWorkData
    {
        public int bitsRemaining = 0;
        public int bytesAvailable = 0;
        public int tempByte = 0;
        public int bufferIndex = 0;
    }
    
    private static final int checkFormatHeader( BufferedInputStream in ) throws IOException
    {
        // GIF87a = 71, 73, 70, 56, 55, 97
        // GIF89a = 71, 73, 70, 56, 57, 97
        
        int result = HEADER_FORMAT_INVALID;
        
        if ( (byte)in.read() != (byte)71 )
            return ( HEADER_FORMAT_INVALID );
        
        if ( (byte)in.read() != (byte)73 )
            return ( HEADER_FORMAT_INVALID );
        
        if ( (byte)in.read() != (byte)70 )
            return ( HEADER_FORMAT_INVALID );
        
        if ( (byte)in.read() != (byte)56 )
            return ( HEADER_FORMAT_INVALID );
        
        switch ( (byte)in.read() )
        {
            case (byte)55:
                result = HEADER_FORMAT_GIF87a;
                break;
            case (byte)57:
                result = HEADER_FORMAT_GIF89a;
                break;
            default:
                return ( HEADER_FORMAT_INVALID );
        }
        
        if ( (byte)in.read() != (byte)97 )
            return ( HEADER_FORMAT_INVALID );
        
        return ( result );
    }
    
    private static final byte parseGlobalFlags( BufferedInputStream in ) throws IOException
    {
        byte[] globalHeader = new byte[ 7 ];
        
        in.read( globalHeader, 0, globalHeader.length );
        
        //short screenWidth = (short)( ( globalHeader[0] & 0xFF ) | ( ( globalHeader[1] & 0xFF ) << 8 ) );
        //short screenHeight = (short)( ( globalHeader[2] & 0xFF ) | ( ( globalHeader[3] & 0xFF ) << 8 ) );
        byte flags = globalHeader[4];
        //byte bgcolor = globalHeader[5];
        //byte aspectRatio = globalHeader[6];
        
        return ( flags );
    }
    
    private static byte[][] readColorPalette( BufferedInputStream in, int numColors ) throws IOException
    {
        byte[][] colorPalette = new byte[ numColors ][];
        
        for ( int i = 0; i < numColors; i++ )
        {
            colorPalette[i] = new byte[]
            {
                (byte)( in.read() & 0xFF ),
                (byte)( in.read() & 0xFF ),
                (byte)( in.read() & 0xFF )
            };
        }
        
        return ( colorPalette );
    }
    
    private static final int parseTransparentColorIndex( BufferedInputStream in ) throws IOException
    {
        int transparentColorIndex = -1;
        
        int imageSeparator = 0;
        
        do
        {
            imageSeparator = in.read();
            
            if ( imageSeparator == 0x21 ) // Extension Block
            {
                int func = in.read();
                int len = in.read();
                
                if ( func == 0xF9 ) // Graphic Control Label 
                // Identifies the current block as a Graphic Control Extension
                {
                    if ( ( in.read() & 0x01 ) == 0x01 )
                    {
                        in.skip( 2 );
                        JAGTLog.debug( "Found transparent GIF" );
                        transparentColorIndex = ( in.read() & 0xFF );
                        len = in.read(); // len = 0, block terminator!
                    }
                    else
                    {
                        in.skip( 3 );
                        len = in.read(); // len = 0, block terminator!
                    }
                }
                
                /*
                 * GIF87a specification mentions the repeatation of mutiple length
                 * blocks while GIF89a gives no specific description.
                 * For safety a while loop is used here to check for block terminator!
                 */
                while ( len != 0 )
                {
                    in.skip( len );
                    len = in.read(); // len = 0, block terminator!
                }
            }
        }
        while ( imageSeparator != 0x2c );
        
        return ( transparentColorIndex );
    }
    
    private int readLZWCode( BufferedInputStream in, GIFWorkData workData, int codeLen, int codeEndOfImage, byte[] bytesBuff ) throws IOException
    {
        int temp = ( workData.tempByte >> ( 8 - workData.bitsRemaining ) );
        
        while ( codeLen > workData.bitsRemaining )
        {
            if ( workData.bytesAvailable == 0 ) // find another data block available
            {
                workData.bytesAvailable = in.read(); // start a new image data subblock if possible!
                // the blocksize bytes_available is no bigger than 0xFF
                if ( workData.bytesAvailable > 0 )
                {
                    in.read( bytesBuff, 0, workData.bytesAvailable );
                    workData.bufferIndex = 0;
                }
                else if ( workData.bytesAvailable == 0 )
                {
                    return ( codeEndOfImage );
                }
                else
                {
                    JAGTLog.exception( "bad image format" );
                    
                    throw new Error( "Bad image format" );
                    
                    //return ( endOfImage );
                }
            }
            
            workData.tempByte = bytesBuff[workData.bufferIndex++] & 0xFF;
            workData.bytesAvailable--;
            temp |= ( workData.tempByte << workData.bitsRemaining );
            workData.bitsRemaining += 8;
        }
        
        workData.bitsRemaining -= codeLen;
        
        return ( temp & LZW_CODE_MASK[codeLen - 1] );
    }
    
    private static final void writePixel( byte[][] colorPalette, int colorIndex, ByteBuffer bb, int byteOffset0, byte[] imageData, int byteOffset, int width, int height, int lineSize, int dstBytesPerPixel, boolean flipVertically )
    {
        final boolean writeAlpha = ( dstBytesPerPixel == 4 );
        
        if ( flipVertically )
        {
            int lineByteOffset = byteOffset % lineSize;
            byteOffset = ( width * height * dstBytesPerPixel ) - ( byteOffset - lineByteOffset ) - lineSize + lineByteOffset;
        }
        
        final byte[] color = colorPalette[ colorIndex ];
        
        if ( imageData == null )
        {
            bb.position( byteOffset0 + byteOffset );
            
            bb.put( color, 0, 3 );
            
            if ( writeAlpha )
            {
                if ( color.length == 4 )
                    bb.put( (byte)0 );
                else
                    bb.put( (byte)255 );
            }
        }
        else
        {
            System.arraycopy( color, 0, imageData, byteOffset, 3 );
            
            if ( writeAlpha )
            {
                if ( color.length == 4 )
                    imageData[byteOffset + 3] = (byte)0;
                else
                    imageData[byteOffset + 3] = (byte)255;
            }
        }
    }
    
    private void decodeLZWInterlacedGIFData( BufferedInputStream in, GIFWorkData workData, byte[][] colorPalette, ByteBuffer bb, byte[] imageData, int width, int height, int dstBytesPerPixel, boolean flipVertically ) throws IOException
    {
        int code = 0;
        int tempcode = 0;
        int index = 0;
        int[] pass = { 0, 8, 4, 8, 2, 4, 1, 2 };
        int passIndex = 0;
        int[] buff = new int[ 4097 ];
        int[] prefix = new int[ 4097 ];
        int[] suffix = new int[ 4097 ];
        byte[] bytesBuff = new byte[ 256 ];
        
        int incr = width * ( pass[passIndex + 1] - 1 );
        int numPixels = width * height;
        
        int min_code_size = in.read(); // The length of the root
        int clearCode = ( 1 << min_code_size );
        int codeEndOfImage = clearCode + 1;
        int first_code_index = codeEndOfImage + 1;
        // Initialize the string table, just in case there is no 
        // clearCode at the beginning of the image
        int codeLen = min_code_size + 1;
        int codeIndex = codeEndOfImage + 1;
        
        // Snipplet to output the first pix of the image
        code = readLZWCode( in, workData, codeLen, codeEndOfImage, bytesBuff );
        while ( code == clearCode )
        {
            code = readLZWCode( in, workData, codeLen, codeEndOfImage, bytesBuff );
        }
        
        final int byteOffset0 = bb.position();
        int byteOffset = 0;
        
        final int lineSize = width * dstBytesPerPixel;
        
        //pix[index++] = colorPalette[code & 0xFF];
        writePixel( colorPalette, ( code & 0xFF ), bb, byteOffset0, imageData, byteOffset, width, height, lineSize, dstBytesPerPixel, flipVertically );
        byteOffset += dstBytesPerPixel;
        
        int first_char = code;
        int oldcode = code;
        
        label: do
        {
            int i = 0;
            code = readLZWCode( in, workData, codeLen, codeEndOfImage, bytesBuff );
            tempcode = code;
            
            if ( code == clearCode )
            { // Initialize the string table
                // The codeIndex is one less than the actual value used
                // which should be endOfImage+1
                codeLen = min_code_size + 1;
                codeIndex = codeEndOfImage;
            }
            else if ( code == codeEndOfImage )
            {
                break;
            }
            else
            {
                if ( code >= codeIndex )
                {
                    tempcode = oldcode;
                    buff[i++] = first_char;
                }
                while ( tempcode >= first_code_index )
                {
                    buff[i++] = suffix[tempcode];
                    tempcode = prefix[tempcode];
                }
                buff[i++] = tempcode;
                
                for ( int j = i - 1; j >= 0; j-- )
                {
                    if ( index % width == 0 )
                    {
                        index += incr;
                        if ( index >= numPixels )
                        {
                            if ( ( passIndex += 2 ) <= pass.length )
                            {
                                index = width * pass[passIndex];
                                incr = width * ( pass[passIndex + 1] - 1 );
                            }
                            else
                            {
                                break label;
                            }
                        }
                    }
                    
                    writePixel( colorPalette, ( buff[j] & 0xFF ), bb, byteOffset0, imageData, byteOffset, width, height, lineSize, dstBytesPerPixel, flipVertically );
                    byteOffset += dstBytesPerPixel;
                }
                
                suffix[codeIndex] = first_char = tempcode;
                prefix[codeIndex++] = oldcode;
                oldcode = code;
                
                if ( ( codeIndex > ( ( 1 << codeLen ) - 1 ) ) && ( codeLen < 12 ) )
                {
                    codeLen += 1;
                }
            }
        }
        while ( true );
    }
    
    private void decodeLZWGIFData( BufferedInputStream in, GIFWorkData workData, byte[][] colorPalette, ByteBuffer bb, byte[] imageData, int width, int height, int dstBytesPerPixel, boolean flipVertically ) throws IOException
    {
        int oldcode = 0;
        int code = 0;
        int tempcode = 0;
        int i = 0;
        int index = 0;
        int[] buff = new int[ 4097 ];
        int[] prefix = new int[ 4097 ];
        int[] suffix = new int[ 4097 ];
        byte[] bytesBuff = new byte[ 256 ];
        
        int numPixels = width * height;
        
        int min_code_size = in.read();//The length of the root
        int clearCode = ( 1 << min_code_size );
        int codeEndOfImage = clearCode + 1;
        int first_code_index = codeEndOfImage + 1;
        // Initialize the string table
        // The codeIndex is one less than the actual value used
        // which should be endOfImage+1
        int codeLen = min_code_size + 1;
        int codeIndex = codeEndOfImage;
        int first_char = 0;
        
        final int byteOffset0 = bb.position();
        int byteOffset = 0;
        
        final int lineSize = width * dstBytesPerPixel;
        
        label: do
        {
            i = 0;
            code = readLZWCode( in, workData, codeLen, codeEndOfImage, bytesBuff );
            tempcode = code;
            
            if ( code == clearCode )
            { // Initialize the string table
                codeLen = min_code_size + 1;
                codeIndex = codeEndOfImage;
            }
            
            else if ( code == codeEndOfImage )
            {
                break;
            }
            
            else
            {
                if ( code >= codeIndex )
                {
                    tempcode = oldcode;
                    buff[i++] = first_char;
                }
                
                while ( tempcode >= first_code_index )
                {
                    buff[i++] = suffix[tempcode];
                    tempcode = prefix[tempcode];
                }
                buff[i++] = tempcode;
                
                for ( int j = i - 1; j >= 0; j-- )
                {
                    if ( index >= numPixels )
                    {
                        break label;
                    }
                    
                    //pix[index++] = colorPalette[buf[j] & 0xFF];
                    writePixel( colorPalette, ( buff[j] & 0xFF ), bb, byteOffset0, imageData, byteOffset, width, height, lineSize, dstBytesPerPixel, flipVertically );
                    byteOffset += dstBytesPerPixel;
                }
                
                suffix[codeIndex] = first_char = tempcode;
                prefix[codeIndex++] = oldcode;
                oldcode = code;
                
                if ( ( codeIndex > ( ( 1 << codeLen ) - 1 ) ) && ( codeLen < 12 ) )
                {
                    codeLen += 1;
                }
            }
        }
        while ( true );
    }
    
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
    
    public AbstractTextureImage loadTextureImage( BufferedInputStream in, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        final int headerFormat = checkFormatHeader( in );
        
        if ( headerFormat == HEADER_FORMAT_INVALID )
        {
            return ( null );
        }
        
        final int flags = parseGlobalFlags( in );
        
        final boolean hasGlobalPalette = ( ( flags & 0x80 ) == 0x80 );
        byte[][] globalColorPalette = null;
        byte[][] colorPalette = null;
        
        int bitsPerPixel = 0;
        int colorsUsed = 0;
        
        if ( hasGlobalPalette ) //a global color map is present 
        {
            bitsPerPixel = ( flags & 0x07 ) + 1;
            colorsUsed = ( 1 << bitsPerPixel );
            
            JAGTLog.debug( "GIF has global color palette with ", colorsUsed, " colors" );
            
            // bits of color resolution, insignificant 
            //int bitsPerColor = ( ( flags & 0x70 ) >> 4 ) + 1;
            
            globalColorPalette = readColorPalette( in, colorsUsed );
            colorPalette = globalColorPalette;
        }
        
        
        final int transparentColorIndex = parseTransparentColorIndex( in );
        final boolean isTransparent = ( transparentColorIndex >= 0 );
        
        
        // Image descriptor...
        byte[] descriptor = new byte[ 9 ];
        
        in.read( descriptor, 0, descriptor.length );
        
        //final int image_x = ( descriptor[0] & 0xFF ) | ( ( descriptor[1] & 0xFF ) << 8 );
        //final int image_y = ( descriptor[2] & 0xFF ) | ( ( descriptor[3] & 0xFF ) << 8 );
        final int orgWidth = ( descriptor[4] & 0xFF ) | ( ( descriptor[5] & 0xFF ) << 8 );
        final int orgHeight = ( descriptor[6] & 0xFF ) | ( ( descriptor[7] & 0xFF ) << 8 );
        final int flags2 = descriptor[8];
        
        final boolean hasLocalPalette = ( ( flags2 & 0x80 ) == 0x80 );
        byte[][] localColorPalette = null;
        
        if ( hasLocalPalette )
        {
            bitsPerPixel = ( flags2 & 0x07 ) + 1;
            colorsUsed = ( 1 << bitsPerPixel );
            
            JAGTLog.debug( "GIF has local color palette with ", colorsUsed, " colors" );
            
            localColorPalette = readColorPalette( in, colorsUsed );
            colorPalette = localColorPalette;
        }
        
        if ( isTransparent )
        {
            colorPalette[transparentColorIndex] = new byte[] { (byte)0, (byte)0, (byte)0, (byte)0 };
        }
        
        final int width = ImageUtility.roundUpPower2( orgWidth );
        final int height = ImageUtility.roundUpPower2( orgHeight );
        
        AbstractTextureImage image = texFactory.createTextureImage( width, height, orgWidth, orgHeight, ( isTransparent && acceptAlpha ) ? 4 : 3 );
        
        ByteBuffer bb = image.getDataBuffer();
        //bb.position( 0 );
        bb.limit( bb.capacity() );
        
        final int byteOffset0 = bb.position();
        
        int dstBytesPerPixel = image.getPixelSize();
        
        final boolean isInterlaced = ( ( flags2 & 0x40 ) == 0x40 );
        
        byte[] imageData = null;
        if ( ( ( width != orgWidth ) || ( height != orgHeight ) ) && allowStreching )
        {
            imageData = new byte[ orgWidth * orgHeight * dstBytesPerPixel ];
        }
        
        GIFWorkData workData = new GIFWorkData();
        
        if ( isInterlaced )
        {
            decodeLZWInterlacedGIFData( in, workData, colorPalette, bb, imageData, orgWidth, orgHeight, dstBytesPerPixel, flipVertically );
        }
        else
        {
            decodeLZWGIFData( in, workData, colorPalette, bb, imageData, orgWidth, orgHeight, dstBytesPerPixel, flipVertically );
        }
        
        if ( imageData != null )
        {
            transferScaledBytes( imageData, dstBytesPerPixel, bb, orgWidth, orgHeight, width, height );
        }
        
        bb.position( 0 );
        bb.limit( byteOffset0 + width * height * dstBytesPerPixel );
        
        return ( image );
    }
}
