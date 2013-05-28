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

import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.loaders.textures.TextureFactory;
import org.jagatoo.util.streams.StreamUtils;

/**
 * (Tries to) load PCX images.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderPCX implements TextureImageFormatLoader
{
    /**
     * The header data block
     */
    @SuppressWarnings( "unused" )
    private static class PCXHeader
    {
        public byte    manufacturer;
        public byte    version;
        public byte    encoding;
        public byte    bitsPerPixel;
        public int     xMin;
        public int     yMin;
        public int     xMax;
        public int     yMax;
        public int     hDPI;
        public int     vDPI;
        public byte[]  colorMap = new byte[ 48 ];
        public byte    reserved;
        public byte    colorPanes;
        public int     bytesPerLine;
        public int     colorInterpretation;
        public int     horizScreenSize;
        public int     vertScreenSize;
        
        public int getWidth()
        {
            return ( xMax - xMin + 1 );
        }
        
        public int getHeight()
        {
            return ( yMax - yMin + 1 );
        }
        
        public static boolean checkFirstFourBytes( byte[] data )
        {
            // manufacturer: must be 10
            if ( data[ 0 ] != 10 )
                return ( false );
            
            // version: must be 0, 2, 3, 4 or 5
            switch ( data[ 1 ] )
            {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                    break;
                default:
                    return ( false );
            }
            
            // encoding: must be 1
            if ( data[ 2 ] != 1 )
                return ( false );
            
            // bits-per-pixel: must be 1, 2, 4 or 8
            switch ( data[ 3 ] )
            {
                case 1:
                case 2:
                case 4:
                case 8:
                    break;
                default:
                    return ( false );
            }
            
            return ( true );
        }
        
        private int readUnsignedShort( byte[] data, int i ) throws IOException
        {
            int low = (int)data[ i + 0 ];
            int high = (int)data[ i + 1 ];
            
            return ( ( ( high & 0xff ) << 8 ) | ( low & 0xff ) );
        }
        
        public PCXHeader( byte[] data ) throws IOException
        {
            int i = 0;
            
            this.manufacturer = data[ i++ ];
            this.version = data[ i++ ];
            this.encoding = data[ i++ ];
            this.bitsPerPixel = data[ i++ ];
            
            this.xMin = readUnsignedShort( data, i );
            i += 2;
            this.yMin = readUnsignedShort( data, i );
            i += 2;
            this.xMax = readUnsignedShort( data, i );
            i += 2;
            this.yMax = readUnsignedShort( data, i );
            i += 2;
            this.hDPI = readUnsignedShort( data, i );
            i += 2;
            this.vDPI = readUnsignedShort( data, i );
            i += 2;
            
            System.arraycopy( data, i, colorMap, 0, colorMap.length );
            i += colorMap.length;
            
            this.reserved = data[ i++ ];
            this.colorPanes = data[ i++ ];
            this.bytesPerLine = readUnsignedShort( data, i );
            i += 2;
            this.colorInterpretation = readUnsignedShort( data, i );
            i += 2;
            this.horizScreenSize = readUnsignedShort( data, i );
            i += 2;
            this.vertScreenSize = readUnsignedShort( data, i );
            i += 2;
        }
    }
    
    public static class PCXPalette
    {
        private byte[][] cols = new byte[ 256 ][ 3 ];
        
        /** 
         * Creates new PCX Palette 
         *
         * @param data the byte-data of the input
         */
        public PCXPalette( byte[] data ) throws IOException
        {
            final int palSize = 3 * 256;
            
            final int offset = data.length - palSize;
            for ( int i = 0; i < 256; i++ )
            {
                cols[ i ][ 0 ] = data[ offset + ( i * 3 ) + 0 ];
                cols[ i ][ 1 ] = data[ offset + ( i * 3 ) + 1 ];
                cols[ i ][ 2 ] = data[ offset + ( i * 3 ) + 2 ];
            }
        }
        
        /**
         * Get a color by a specified index.
         *
         * @param index The index of the color to retrieve
         */
        public final void getColor( int index, byte[] pixel )
        {
            if ( index < 0 )
                index = 255 + index;
            
            pixel[ 0 ] = cols[ index ][ 0 ];
            pixel[ 1 ] = cols[ index ][ 1 ];
            pixel[ 2 ] = cols[ index ][ 2 ];
            
            /*
            if ( pixel.length >= 4 )
            {
                pixel[ 3 ] = (byte)255;
            }
            */
            
            //return ( ( 255 << 24 ) + ( cols[ i ][ 0 ] << 16 ) + ( cols[ i ][ 1 ] << 8 ) + ( cols[ i ][ 2 ] ) );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public AbstractTextureImage loadTextureImage( BufferedInputStream in, boolean acceptAlpha, boolean flipVertically, boolean allowStreching, TextureFactory texFactory ) throws IOException
    {
        final int headerSize = 128;
        
        if ( in.available() < headerSize )
        {
            return ( null );
        }
        
        final byte[] first4Bytes = new byte[ 4 ];
        in.read( first4Bytes, 0, 4 );
        final byte[] headerData = new byte[ headerSize ];
        System.arraycopy( first4Bytes, 0, headerData, 0, 4 );
        if ( !PCXHeader.checkFirstFourBytes( headerData ) )
            return ( null );
        in.read( headerData, 4, headerSize - 4 );
        
        PCXHeader header;
        try
        {
            header = new PCXHeader( headerData );
        }
        catch ( Throwable t )
        {
            return ( null );
        }
        
        byte[] data = StreamUtils.buildByteArray( in );
        PCXPalette pal = new PCXPalette( data );
        
        final int width = header.getWidth();
        final int height = header.getHeight();
        final int bytesPerPixel = 3;
        
        int xp = 0;
        int yp = 0;
        int count;
        
        AbstractTextureImage image = texFactory.createTextureImage( width, height, width, height, 3 );
        
        ByteBuffer bb = image.getDataBuffer();
        //bb.position( 0 );
        bb.limit( bb.capacity() );
        
        final int byteOffset0 = bb.position();
        
        final int dstBytesPerPixel = image.getFormat().getPixelSize();
        final int trgLineSize = width * dstBytesPerPixel;
        //final int trgImageSize = height * trgLineSize;
        
        int dstByteOffset = 0;
        
        byte[] pixel = new byte[ bytesPerPixel ];
        
        int i = 0;
        while ( yp < height )
        {
            int colorIndex = ( data[ i++ ] & 0xFF );
            // if the byte has the top two bits set
            if ( colorIndex >= 192 )
            {
                count = ( colorIndex - 192 );
                colorIndex = ( data[ i++ ] & 0xFF );
            }
            else
            {
                count = 1;
            }
            
            // update data
            for ( int j = 0; j < count; j++ )
            {
                if ( xp < width )
                {
                    pal.getColor( colorIndex, pixel );
                    
                    int actualByteOffset = dstByteOffset;
                    if ( flipVertically )
                        actualByteOffset = ( ( height - yp - 1 ) * trgLineSize ) + ( xp * dstBytesPerPixel );
                    
                    bb.put( byteOffset0 + actualByteOffset + 0, pixel[ 0 ] );
                    bb.put( byteOffset0 + actualByteOffset + 1, pixel[ 1 ] );
                    bb.put( byteOffset0 + actualByteOffset + 2, pixel[ 2 ] );
                    
                    dstByteOffset += 3;
                }
                
                xp++;
                if ( xp == header.bytesPerLine )
                {
                    xp = 0;
                    yp++;
                    break;
                }
            }
        }
        
        /*
        if ( imageData != null )
        {
            transferScaledBytes( imageData, dstBytesPerPixel, bb, orgWidth, orgHeight, width, height );
        }
        */
        
        bb.position( 0 );
        bb.limit( byteOffset0 + ( width * height * dstBytesPerPixel ) );
        
        return ( image );
    }
}
