package org.jagatoo.loaders.textures.formats;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.jagatoo.image.BufferedImageFactory;
import org.jagatoo.image.SharedBufferedImage;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.loaders.textures.TextureFactory;
import org.jagatoo.util.image.ImageUtility;
import org.jagatoo.util.streams.StreamUtils;

/**
 * Loads SGI images to texture objects.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class TextureImageFormatLoaderSGI implements TextureImageFormatLoader
{
    public static final int HEADER_SIZE = 512;
    public static final short MAGIC_NUMBER = 474;
    
    private static class SGIHeader
    {
        public static final int IMAGE_NAME_SIZE = 80;
        
        public byte storage;
        public byte bpc;
        //public int dimension; // unsigned short (used int to avoid sign bit)
        public int xSize; // unsigned short
        public int ySize; // unsigned short
        public int zSize; // unsigned short
        //public int pixMin;
        //public int pixMax;
        //public byte[] imageName = new byte[ IMAGE_NAME_SIZE ];
        //public int colorMap;
        
        public SGIHeader( BufferedInputStream in ) throws IOException
        {
            this.storage = StreamUtils.readByte( in );
            this.bpc = StreamUtils.readByte( in );
            
            //this.dimension = StreamUtils.readUnsignedShort( in );
            in.skip( 2 );
            this.xSize = StreamUtils.readUnsignedShort( in );
            this.ySize = StreamUtils.readUnsignedShort( in );
            this.zSize = StreamUtils.readUnsignedShort( in );
            
            //this.pixMin = StreamUtils.readInt( in );
            //this.pixMax = StreamUtils.readInt( in );
            in.skip( 8 );
            
            in.skip( 4 );
            
            //StreamUtils.readBytes( in, IMAGE_NAME_SIZE, imageName, 0 );
            in.skip( IMAGE_NAME_SIZE );
            
            //this.colorMap = StreamUtils.readInt( in );
            in.skip( 4 );
            
            in.skip( 404 ); // HEADER_SIZE - 2 - 106
        }
    }
    
    private static void transferScaledBytes( byte[] unscaledData, int bytesPerPixel, ByteBuffer bb, int orgWidth, int orgHeight, int width, int height )
    {
        SharedBufferedImage sbi = BufferedImageFactory.createSharedBufferedImage( orgWidth, orgHeight, bytesPerPixel, null, unscaledData );
        
        SharedBufferedImage sbi_scaled = ImageUtility.scaleImage( sbi, width, height, ( bytesPerPixel == 4 ) );
        
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
        if ( in.available() < HEADER_SIZE ) // check header size.
            return ( null );
        
        final short magicNumber = StreamUtils.readShort( in );
        
        if ( magicNumber != MAGIC_NUMBER )
            return ( null );
        
        SGIHeader header = new SGIHeader( in );
        
        final int orgWidth = header.xSize;
        final int orgHeight = header.ySize;
        final int width = ImageUtility.roundUpPower2( orgWidth );
        final int height = ImageUtility.roundUpPower2( orgHeight );
        
        final int bytesPerPixel = header.zSize;
        
        final boolean isTransparent = ( bytesPerPixel == 4 );
        
        final boolean isCompressed = ( header.storage == (byte)1 );
        
        //final int numPixels = orgWidth * orgHeight;
        
        AbstractTextureImage image = texFactory.createTextureImage( width, height, orgWidth, orgHeight, ( isTransparent && acceptAlpha ) ? 4 : 3 );
        
        ByteBuffer bb = image.getDataBuffer();
        //bb.position( 0 );
        bb.limit( bb.capacity() );
        
        final int byteOffset0 = bb.position();
        
        int dstBytesPerPixel = image.getPixelSize();
        
        byte[] imageData = null;
        if ( ( ( width != orgWidth ) || ( height != orgHeight ) ) && allowStreching )
        {
            imageData = new byte[ orgWidth * orgHeight * dstBytesPerPixel ];
        }
        
        if ( isCompressed )
        {
            final int numScanLines = orgHeight * bytesPerPixel; // table length is the number of scan lines
            final int[] scanlineOffsets = new int[ numScanLines ];
            final int[] scanlineLengths = new int[ numScanLines ];
            
            // Read in RLE tables:
            for ( int i = 0; i < numScanLines; i++ )
            {
                scanlineOffsets[i] = StreamUtils.readInt( in ) - HEADER_SIZE - ( 8 * numScanLines );
            }
            
            for ( int i = 0; i < numScanLines; i++ )
            {
                scanlineLengths[i] = StreamUtils.readInt( in );
            }
            
            // Read in the image data:
            if ( header.bpc == 1 ) // Image data written as chars (bytes).
            {
                // Find the size of the RLE data:
                int compressedImageSize = scanlineOffsets[0] + scanlineLengths[0];
                for ( int nIndex = 1; nIndex < numScanLines; nIndex++ )
                {
                    int reqBytes = scanlineOffsets[nIndex] + scanlineLengths[nIndex];
                    if ( reqBytes > compressedImageSize )
                        compressedImageSize = reqBytes;
                }
                
                // Allocate RLE data buffer:
                final byte[] compressedImageData = new byte[ compressedImageSize ];
                
                int numBytesSoFar = 0;
                
                // Loop over each channel...
                for ( int c = 0; c < bytesPerPixel; c++ )
                {
                    int pixelIndex = 0;
                    
                    // Loop over each scan line (counting backwards because origin of RGB image coords is in lower left corner):
                    for ( int y = orgHeight - 1; y >= 0; y-- )
                    {
                        int rleDataIndex = y + c * orgHeight;
                        
                        int offsetRLE = scanlineOffsets[rleDataIndex];
                        
                        // Load the data if we don't have it already:
                        int reqBytes = offsetRLE + scanlineLengths[rleDataIndex];
                        int numBytesRead;
                        while ( ( reqBytes > numBytesSoFar ) && ( numBytesRead = in.read( compressedImageData, numBytesSoFar, reqBytes - numBytesSoFar ) ) != -1 )
                            numBytesSoFar += numBytesRead;
                        
                        
                        int offset;
                        if ( flipVertically )
                            offset = y * orgWidth;
                        else
                            offset = 0;
                        
                        int x = 0;
                        
                        while ( true )
                        {
                            byte start = compressedImageData[offsetRLE++];
                            byte count;
                            if ( ( count = (byte)( start & 0x7F ) ) == 0 ) // Lowest 7 bits of start byte give the count.
                                break; // End of compressed data
                            
                            // First bit of start byte says to copy next <count> bytes
                            if ( ( start & 0x80 ) == 0x80 )
                            {
                                while ( ( count-- ) > 0 )
                                {
                                    int pos;
                                    if ( flipVertically )
                                        pos = ( offset + x ) * dstBytesPerPixel + c;
                                    else
                                        pos = pixelIndex * dstBytesPerPixel + c;
                                    
                                    if ( imageData == null )
                                        bb.put( byteOffset0 + pos, compressedImageData[offsetRLE++] );
                                    else
                                        imageData[pos] = compressedImageData[offsetRLE++];
                                    
                                    pixelIndex++;
                                    x++;
                                }
                            }
                            else // First bit of start byte says to repeat next byte <bytCount> times
                            {
                                byte b = compressedImageData[offsetRLE++];
                                
                                while ( ( count-- ) > 0 )
                                {
                                    int pos;
                                    if ( flipVertically )
                                        pos = ( offset + x ) * dstBytesPerPixel + c;
                                    else
                                        pos = pixelIndex * dstBytesPerPixel + c;
                                    
                                    if ( imageData == null )
                                        bb.put( byteOffset0 + pos, b );
                                    else
                                        imageData[pos] = b;
                                    
                                    pixelIndex++;
                                    x++;
                                }
                            }
                        }
                    }
                }
            }
        }
        else // not compressed
        {
            if ( header.bpc == 1 )
            {
                final int srcScanlineSize = orgWidth * bytesPerPixel;
                final int trgScanlineSize = orgWidth * dstBytesPerPixel;
                
                for ( int c = 0; c < bytesPerPixel; c++ )
                {
                    for ( int y = 0; y < orgHeight; y++ )
                    {
                        int offset;
                        if ( flipVertically )
                            offset = y * trgScanlineSize;
                        else
                            offset = ( orgHeight - y - 1 ) * trgScanlineSize;
                        
                        //for ( int x = 0; x < orgWidth; x++ )
                        for ( int x = 0; x < srcScanlineSize; x += bytesPerPixel )
                        {
                            //int pos = offset + ( x * bytesPerPixel ) + c;
                            int pos = offset + x + c;
                            
                            if ( imageData == null )
                                bb.put( byteOffset0 + pos, StreamUtils.readByte( in ) );
                            else
                                imageData[pos] = StreamUtils.readByte( in );
                        }
                    }
                }
            }
        }
        
        if ( imageData != null )
        {
            transferScaledBytes( imageData, dstBytesPerPixel, bb, orgWidth, orgHeight, width, height );
        }
        
        bb.position( 0 );
        bb.limit( byteOffset0 + width * height * dstBytesPerPixel );
        
        return ( image );
    }
    
    public TextureImageFormatLoaderSGI()
    {
    }
}
