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
package org.jagatoo.util.image;

import org.jagatoo.image.DirectBufferedImage;
import org.jagatoo.image.SharedBufferedImage;
import org.jagatoo.logging.JAGTLog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mostly static methods to do some image manipulations.
 * 
 * @author David Yazel
 */
public class ImageUtility
{
    private static Map< Integer, List< int[] >> intArrays = new HashMap< Integer, List< int[] >>();
    private static Map< String, BufferedImage > images = new HashMap< String, BufferedImage >();
    
    private static synchronized int[] getArray( int size )
    {
        Integer key = new Integer( size );
        List< int[] > list = intArrays.get( key );
        
        if ( ( list == null ) || ( list.size() == 0 ) )
        {
            return ( new int[ size ] );
        }
        
        return ( list.remove( 0 ) );
    }
    
    private static synchronized void putArray( int[] a )
    {
        Integer key = new Integer( a.length );
        List< int[] > list = intArrays.get( key );
        
        if ( list == null )
        {
            list = new ArrayList< int[] >();
            intArrays.put( key, list );
        }
        
        list.add( a );
    }
    
    /**
     * Uses integer box filter to downsample an image two one half its
     * size.  This is used for making excellent mipmaps.
     * 
     * @param source
     * 
     * @return the down-sampled image
     */
    public static BufferedImage downSampleRGB( BufferedImage source )
    {
        int j;
        final int sourceWidth = source.getWidth();
        final int sourceHeight = source.getHeight();
        final int destWidth = ( sourceWidth > 1 ) ? ( sourceWidth / 2 ) : 1;
        final int destHeight = ( sourceHeight > 1 ) ? ( sourceHeight / 2 ) : 1;
        
        BufferedImage dest = new BufferedImage( destWidth, destHeight, BufferedImage.TYPE_INT_RGB );
        int[] spix = getArray( sourceWidth * sourceHeight );
        int[] dpix = getArray( destWidth * destHeight );
        source.getRGB( 0, 0, sourceWidth, sourceHeight, spix, 0, sourceWidth );
        
        for ( j = 0; j < destHeight; j++ )
        {
            int i;
            
            for ( i = 0; i < destWidth; i++ )
            {
                // calculate the pixel location
                int s0 = getIndex( i * 2, j * 2, sourceWidth, sourceHeight );
                int s1 = getIndex( ( i * 2 ) + 1, j * 2, sourceWidth, sourceHeight );
                int s2 = getIndex( i * 2, ( j * 2 ) + 1, sourceWidth, sourceHeight );
                int s3 = getIndex( ( i * 2 ) + 1, ( j * 2 ) + 1, sourceWidth, sourceHeight );
                
                int red = ( ( spix[ s0 ] >> 16 ) & 0xff ) + ( ( spix[ s1 ] >> 16 ) & 0xff ) + ( ( spix[ s2 ] >> 16 ) & 0xff ) + ( ( spix[ s3 ] >> 16 ) & 0xff );
                
                int green = ( ( spix[ s0 ] >> 8 ) & 0xff ) + ( ( spix[ s1 ] >> 8 ) & 0xff ) + ( ( spix[ s2 ] >> 8 ) & 0xff ) + ( ( spix[ s3 ] >> 8 ) & 0xff );
                
                int blue = ( spix[ s0 ] & 0xff ) + ( spix[ s1 ] & 0xff ) + ( spix[ s2 ] & 0xff ) + ( spix[ s3 ] & 0xff );
                
                red /= 4;
                green /= 4;
                blue /= 4;
                
                dpix[ i + ( destWidth * j ) ] = ( ( red << 16 ) | ( green << 8 ) | blue );
            }
        }
        
        dest.setRGB( 0, 0, destWidth, destHeight, dpix, 0, destWidth );
        putArray( dpix );
        putArray( spix );
        
        return ( dest );
    }
    
    public static BufferedImage downSampleRGBA( BufferedImage source )
    {
        int j;
        final int sourceWidth = source.getWidth();
        final int sourceHeight = source.getHeight();
        final int destWidth = ( sourceWidth > 1 ) ? ( sourceWidth / 2 ) : 1;
        final int destHeight = ( sourceHeight > 1 ) ? ( sourceHeight / 2 ) : 1;
        
        BufferedImage dest = new BufferedImage( destWidth, destHeight, BufferedImage.TYPE_INT_ARGB );
        int[] spix = getArray( sourceWidth * sourceHeight );
        int[] dpix = getArray( destWidth * destHeight );
        source.getRGB( 0, 0, sourceWidth, sourceHeight, spix, 0, sourceWidth );
        
        for ( j = 0; j < destHeight; j++ )
        {
            int i;
            
            for ( i = 0; i < destWidth; i++ )
            {
                // calculate the pixel location
                int s0 = getIndex( i * 2, j * 2, sourceWidth, sourceHeight );
                int s1 = getIndex( ( i * 2 ) + 1, j * 2, sourceWidth, sourceHeight );
                int s2 = getIndex( i * 2, ( j * 2 ) + 1, sourceWidth, sourceHeight );
                int s3 = getIndex( ( i * 2 ) + 1, ( j * 2 ) + 1, sourceWidth, sourceHeight );
                
                int alpha = ( ( spix[ s0 ] >> 24 ) & 0xff ) + ( ( spix[ s1 ] >> 24 ) & 0xff ) + ( ( spix[ s2 ] >> 24 ) & 0xff ) + ( ( spix[ s3 ] >> 24 ) & 0xff );
                
                int red = ( ( spix[ s0 ] >> 16 ) & 0xff ) + ( ( spix[ s1 ] >> 16 ) & 0xff ) + ( ( spix[ s2 ] >> 16 ) & 0xff ) + ( ( spix[ s3 ] >> 16 ) & 0xff );
                
                int green = ( ( spix[ s0 ] >> 8 ) & 0xff ) + ( ( spix[ s1 ] >> 8 ) & 0xff ) + ( ( spix[ s2 ] >> 8 ) & 0xff ) + ( ( spix[ s3 ] >> 8 ) & 0xff );
                
                int blue = ( spix[ s0 ] & 0xff ) + ( spix[ s1 ] & 0xff ) + ( spix[ s2 ] & 0xff ) + ( spix[ s3 ] & 0xff );
                
                red /= 4;
                green /= 4;
                blue /= 4;
                alpha /= 4;
                
                dpix[ i + ( destWidth * j ) ] = ( ( alpha << 24 ) | ( red << 16 ) | ( green << 8 ) | blue );
            }
        }
        
        dest.setRGB( 0, 0, destWidth, destHeight, dpix, 0, destWidth );
        
        putArray( dpix );
        putArray( spix );
        
        return ( dest );
    }
    
    /**
     * finds the maximum image bounds for the non-alpha masked object.  This
     * assumes that the image has portions of it with an alpha of zero.
     */
    public static Rectangle alphaBounds( BufferedImage image )
    {
        Rectangle r = new Rectangle();
        
        int width = image.getWidth();
        int height = image.getHeight();
        
        // start with max bounds
        int lx = 0;
        int ly = 0;
        int ux = width - 1;
        int uy = height - 1;
        
        // mark the bounds as "not found"
        boolean left = false;
        boolean top = false;
        boolean bottom = false;
        boolean right = false;
        
        // pull the data out
        int[] pixels = new int[ width * height ];
        image.getRGB( 0, 0, width, height, pixels, 0, width );
        
        // now scan for the X bounds
        for ( int x = 0; x < width; x++ )
        {
            // scan from top to bottom
            for ( int y = 0; y < height; y++ )
            {
                int alphaleft = ( pixels[ ( y * width ) + x ] >> 24 );
                int alpharight = ( pixels[ ( y * width ) + ( width - x - 1 ) ] >> 24 );
                
                if ( ( !left ) && ( alphaleft != 0 ) )
                {
                    lx = x;
                    left = true;
                }
                
                if ( ( !right ) && ( alpharight != 0 ) )
                {
                    ux = width - x - 1;
                    right = true;
                }
            }
        }
        
        // now scan for the Y bounds
        for ( int y = 0; y < height; y++ )
        {
            // scan from left to right
            for ( int x = 0; x < width; x++ )
            {
                int alphatop = ( pixels[ ( y * width ) + x ] >> 24 );
                int alphabottom = ( pixels[ ( ( height - y - 1 ) * width ) + x ] >> 24 );
                
                if ( ( !top ) && ( alphatop != 0 ) )
                {
                    ly = y;
                    top = true;
                }
                
                if ( ( !bottom ) && ( alphabottom != 0 ) )
                {
                    uy = height - y - 1;
                    bottom = true;
                }
            }
        }
        
        JAGTLog.debug( "Image alpha bounds : ", lx, ",", ly, " -> ", ux, ",", uy );
        r.setBounds( lx, ly, ux - lx + 1, uy - ly + 1 );
        
        return ( r );
    }
    
    private static int getIndex( int x, int y, int maxX, int maxY )
    {
        if ( x < 0 )
        {
            x = 0;
        }
        
        if ( x >= maxX )
        {
            x = maxX - 1;
        }
        
        if ( y < 0 )
        {
            y = 0;
        }
        
        if ( y >= maxY )
        {
            y = maxY - 1;
        }
        
        return ( ( y * maxX ) + x );
    }
    
    /**
     * Takes the buffered image and builds a new one which is centered and scaled.
     * Alpha blended edges are discarded.  A margin is in pixels around the image.
     */
    public static BufferedImage centerAndScale( BufferedImage image, int width, int height, int margin )
    {
        Rectangle r = alphaBounds( image );
        JAGTLog.debug( "   Alpha bounds : ", (int)r.getWidth(), "+", (int)r.getHeight() );
        JAGTLog.debug( "      min : ", (int)r.getMinX(), ",", (int)r.getMinY() );
        JAGTLog.debug( "      max : ", (int)r.getMaxX(), ",", (int)r.getMaxY() );
        
        int w;
        int h;
        int lx;
        int ly;
        
        if ( r.getWidth() < r.getHeight() )
        {
            double scale = r.getWidth() / r.getHeight();
            h = height - ( margin * 2 );
            w = (int)( h * scale );
        }
        else
        {
            double scale = r.getHeight() / r.getWidth();
            w = width - ( margin * 2 );
            h = (int)( w * scale );
        }
        
        JAGTLog.debug( "   New width ", w );
        JAGTLog.debug( "   New height ", h );
        
        lx = ( ( width / 2 ) - ( w / 2 ) );
        ly = ( ( height / 2 ) - ( h / 2 ) );
        
        BufferedImage bi = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = (Graphics2D)bi.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
        g.drawImage( image, lx, ly, ( lx + w ) - 1, ( ly + h ) - 1, (int)r.getMinX(), (int)r.getMinY(), (int)r.getMaxX(), (int)r.getMaxY(), null );
        
        return ( bi );
    }
    
    public static void writeImage( BufferedImage image, String filename )
    {
        try
        {
            FileOutputStream out = new FileOutputStream( filename );
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );
//            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam( image );
//
			ImageIO.write(image, "JPG", out);

            out.close();
        }
        catch ( Exception e )
        {
			e.printStackTrace();
        }
    }
    
    public static void writeImage( BufferedImage image, String filename, float compression )
    {
        try
        {
            FileOutputStream out = new FileOutputStream( filename );
			ImageIO.write(image, "JPG", out);

			out.close();
        }
        catch ( Exception e )
        {
        }
    }
    
    public static void writeAlphaImage( BufferedImage image, String filename )
    {
        try
        {
            File f = new File( filename );
            ImageIO.write( image, "png", f );
        }
        catch ( Exception e )
        {
            JAGTLog.print( e );
        }
    }
    
    public static BufferedImage readImage( String filename ) throws IOException
    {
        File f = new File( filename );
        
        return ( ImageIO.read( f ) );
    }
    
    public static BufferedImage readImage( String name, Object c ) throws IOException
    {
        BufferedImage image = images.get( name );
        
        if ( image != null )
        {
            return ( image );
        }
        
        ClassLoader classloader = c.getClass().getClassLoader();
        URL url = classloader.getResource( name );
        
        if ( url == null )
        {
            throw new IOException( "Cannot find file " + name + " on classpath" );
        }
        
        BufferedImage i = ImageIO.read( url );
        images.put( name, i );
        
        return ( i );
    }
    
    /**
     * Flips the given image vertically and returns the modified version
     * 
     * @param bi the image to flip
     * @return the modified version
     */
    public static DirectBufferedImage flipImageVertical( BufferedImage bi )
    {
        AffineTransform tx = AffineTransform.getScaleInstance( 1.0, -1.0 );
        tx.translate( 0.0, -bi.getHeight( null ) );
        AffineTransformOp op = new AffineTransformOp( tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
        
        DirectBufferedImage bi2;
        if ( bi.getColorModel().hasAlpha() )
            bi2 = DirectBufferedImage.makeDirectImageRGBA( bi.getWidth(), bi.getHeight() );
        else
            bi2 = DirectBufferedImage.makeDirectImageRGB( bi.getWidth(), bi.getHeight() );
        
        op.filter( bi, bi2 );
        
        return ( bi2 );
    }
    
    /**
     * Converts a BufferedImage to a ByteBuffer.
     * 
     * @throws IOException 
     */
    public static byte[] toByteArray( BufferedImage img ) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( img, "pnm", baos );
        
        return ( baos.toByteArray() );
    }
    
    /**
     * Converts a BufferedImage to a ByteBuffer.
     * 
     * @throws IOException 
     */
    public static ByteBuffer[] toByteBuffer( BufferedImage img ) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( img, "pnm", baos );
        ByteBuffer bb = ByteBuffer.allocateDirect( 1 );
        bb.put( baos.toByteArray() );
        bb.rewind();
        
        return ( new ByteBuffer[] { bb } );
    }
    
    /**
     * Scales an Image to the size of trgImage and draws it onto trgImage.<br>
     * <br>
     * This uses AWT!
     * 
     * @param srcImage The Image that should be scaled.
     * @param trgImage the target image.
     * 
     * @return The target image.
     */
    public static <BufferedImage_ extends BufferedImage> BufferedImage_ scaleImage( Image srcImage, BufferedImage_ trgImage )
    {
        Graphics2D g2 = trgImage.createGraphics();
        
        g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
        
        try
        {
            g2.drawImage( srcImage, 0, 0, trgImage.getWidth(), trgImage.getHeight(), null );
        }
        finally
        {
            g2.dispose();
        }
        
        return ( trgImage );
    }
    
    /**
     * Scales an Image and creates a BufferedImage in the desired format. This
     * uses AWT.
     * 
     * @param image The Image that should be scaled.
     * @param width The desired image width.
     * @param height The desired image height.
     * @param alpha create an alpha-channel for the new image?
     * @return The scaled image as a SharedBufferedImage.
     */
    public static SharedBufferedImage scaleImage( Image image, int width, int height, boolean alpha )
    {
        SharedBufferedImage newImg = SharedBufferedImage.create( width, height, ( alpha ? 4 : 3 ), alpha, null, null );
        
        return ( scaleImage( image, newImg ) );
    }
    
    /**
     * @param v the value to be rounded
     * @return the next power of two greater or equal to <i>v</i>
     */
    public static final int roundUpPower2( int v )
    {
        switch ( Integer.bitCount( v ) )
        {
            case 0:
                return ( 1 );
            case 1:
                return ( v );
            default:
                return ( Integer.highestOneBit( v ) << 1 );
        }
    }
    
    /*
    public static void main( String[] args ) throws IOException
    {
        String filename = new File( new File( "." ), "human_male.png" ).getAbsolutePath();
        BufferedImage i = readImage( filename );
        BufferedImage i2 = downSampleRGB( i );
        BufferedImage i3 = downSampleRGB( i2 );
        BufferedImage i4 = downSampleRGB( i3 );
        BufferedImage i5 = downSampleRGB( i4 );
        
        writeAlphaImage( i2, filename );
        writeAlphaImage( i3, filename );
        writeAlphaImage( i4, filename );
        writeAlphaImage( i5, filename );
    }
    */
}
