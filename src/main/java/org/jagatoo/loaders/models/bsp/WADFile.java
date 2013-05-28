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
package org.jagatoo.loaders.models.bsp;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.jagatoo.image.BufferedImageFactory;
import org.jagatoo.image.SharedBufferedImage;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models.bsp.BSPEntitiesParser.BSPEntity;
import org.jagatoo.loaders.models.bsp.BSPEntitiesParser.BSPEntity_worldspawn;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.opengl.enums.TextureImageFormat;
import org.jagatoo.util.image.ImageUtility;
import org.jagatoo.util.streams.StreamUtils;
import org.openmali.FastMath;

/**
 * Represents a Half-Life WAD source file.
 * 
 * @author Sebastian Thiele (aka SETIssl)
 * @author Marvin Froehlich (aka Qudus)
 */
public class WADFile
{
    private static final int MAGIC_NUMBER_WAD3 = 0x57414433;
    
    // wad directory, contains info for integrated files
    private static class WADDirectoryEntry 
    {
        public long    offset          = 0;    // - Offset 
        public int     compFileSize    = 0;    // - Compressed File Size 
        public int     uncompFileSize  = 0;    // - Uncompressed File Size 
        public byte    fileType        = 0;    // - File Type 
        public byte    compType        = 0;    // - Compression Type 
        public byte[]  padding         = null; // - Padding 
        public String  fileName        = null; // - Filename (null-terminated)
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString()
        {
            return ( this.getClass().getSimpleName() + " { " +
                    "name: \"" + fileName + "\"" +
                    "\t | offset: " + offset +
                    "\t | compFileSize: " + compFileSize +
                    "\t | uncompFileSize: " + uncompFileSize +
                    "\t | fileType: " + fileType +
                    "\t | compType: " + compType +
                    "\t | pad1: " + padding[0] + "\t | pad2: " + padding[1] +
                    " }"
                  );
        }
    }
    
    private final URL     wadResource;
    private final String  wadFilename;
    private int           magicNumber;
    private String        wadType;
    
    private final HashMap<String, WADDirectoryEntry> wadDir;
    
    public final String getWADFilename()
    {
        return ( wadFilename );
    }
    
    public final String getWadType()
    {
        return ( wadType );
    }
    
    public final int getLumpCount()
    {
        return ( wadDir.size() );
    }
    
    public final String[] getWADResources()
    {
        String[] result = new String[ wadDir.size() ];
        
        int i = 0;
        for ( String s : wadDir.keySet() )
        {
            result[ i ] = s;
        }
        
        java.util.Arrays.sort( result );
        
        return ( result );
    }
    
    public final boolean containsResource( String resName )
    {
        return ( wadDir.containsKey( resName.toLowerCase() ) );
    }
    
    public final BufferedInputStream getResourceAsStream( String resName ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            return ( null );
        }
        
        InputStream in = wadResource.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        return ( (BufferedInputStream)in );
    }
    
    public final void exportResource( String resName, String filename ) throws IOException
    {
        WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
        
        if ( entry == null )
        {
            throw new IOException( "The resource was not found in this WAD file." );
        }
        
        InputStream in = wadResource.openStream();
        if ( !( in instanceof BufferedInputStream ) )
        {
            in = new BufferedInputStream( in );
        }
        
        in.skip( entry.offset );
        
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( filename ) );
        for ( int i = 0; i < entry.compFileSize; i++ )
        {
            out.write( (byte)in.read() );
        }
        out.close();
        
        in.close();
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
    
    private void readTransparentTexture( DataInputStream din, byte[][] palette, int orgWidth, int orgHeight, int width, int height, ByteBuffer bb, byte[] pixelData ) throws IOException
    {
        final boolean needsPostScaling = ( pixelData != null );
        
        byte r, g, b, a;
        
        // convert the mip palette based bitmap to RGB format...
        int size = orgWidth * orgHeight;
        int pos = 0;
        for ( int i = 0; i < size; i++ )
        {
            int palIdx = din.read();
            
            r = palette[palIdx][0];
            g = palette[palIdx][1];
            b = palette[palIdx][2];
            a = (byte)255;
            
            if ( ( r == (byte)0 ) && ( g == (byte)0 ) && ( b == (byte)255 ) )
            {
                r = (byte)0;
                b = (byte)0;
                g = (byte)0;
                a = (byte)0;
            }
            
            if ( !needsPostScaling )
            {
                bb.put( r );
                bb.put( g );
                bb.put( b );
                bb.put( a );
            }
            else
            {
                pixelData[pos + 2] = r;
                pixelData[pos + 1] = g;
                pixelData[pos + 0] = b;
                pixelData[pos + 3] = a;
                
                pos += 4;
            }
        }
        
        if ( needsPostScaling )
        {
            transferScaledBytes( pixelData, 4, bb, orgWidth, orgHeight, width, height );
        }
    }
    
    private void readGlassTexture( DataInputStream din, byte[][] palette, int orgWidth, int orgHeight, int width, int height, ByteBuffer bb, byte[] pixelData ) throws IOException
    {
        final boolean needsPostScaling = ( pixelData != null );
        
        byte r, g, b, a;
        
        // convert the mip palette based bitmap to RGB format...
        int size = orgWidth * orgHeight;
        int pos = 0;
        for ( int i = 0; i < size; i++ )
        {
            int palIdx = din.read();
            
            r = palette[palIdx][0];
            g = palette[palIdx][1];
            b = palette[palIdx][2];
            a = (byte)127;
            
            if ( !needsPostScaling )
            {
                bb.put( r );
                bb.put( g );
                bb.put( b );
                bb.put( a );
            }
            else
            {
                pixelData[pos + 2] = r;
                pixelData[pos + 1] = g;
                pixelData[pos + 0] = b;
                pixelData[pos + 3] = a;
                
                pos += 4;
            }
        }
        
        if ( needsPostScaling )
        {
            transferScaledBytes( pixelData, 4, bb, orgWidth, orgHeight, width, height );
        }
    }
    
    private void flipTextureHorizontally( AbstractTextureImage ti )
    {
        int pixelSize = ti.getPixelSize();
        int w = ti.getWidth();
        int wh = w / 2;
        int lineSize = w * pixelSize;
        int h = ti.getHeight();
        
        ByteBuffer bb = ti.getDataBuffer();
        final int pos0 = bb.position();
        final int limit0 = bb.limit();
        
        byte[] pixel1 = new byte[ pixelSize ];
        byte[] pixel2 = new byte[ pixelSize ];
        
        for ( int y = 0; y < h; y++ )
        {
            int posBase = y * lineSize;
            
            for ( int x = 0; x < wh; x++ )
            {
                int pos1 = posBase + x * pixelSize;
                int pos2 = posBase + lineSize - x * pixelSize - pixelSize;
                
                bb.position( pos0 + pos1 );
                bb.get( pixel1, 0, pixelSize );
                
                bb.position( pos0 + pos2 );
                bb.get( pixel2, 0, pixelSize );
                
                bb.position( pos0 + pos1 );
                bb.put( pixel2, 0, pixelSize );
                
                bb.position( pos0 + pos2 );
                bb.put( pixel1, 0, pixelSize );
            }
        }
        
        bb.position( pos0 );
        bb.limit( limit0 );
    }
    
    private void rotateTextureBy90Degree( AbstractTextureImage ti )
    {
        int pixelSize = ti.getPixelSize();
        int w = ti.getWidth();
        int lineSize = w * pixelSize;
        int h = ti.getHeight();
        
        ByteBuffer bb = ti.getDataBuffer();
        final int pos0 = bb.position();
        final int limit0 = bb.limit();
        
        byte[] pixel = new byte[ pixelSize ];
        byte[] bytes = new byte[ h * lineSize ];
        
        for ( int y = 0; y < h; y++ )
        {
            int posBase = y * lineSize;
            
            for ( int x = 0; x < w; x++ )
            {
                int pos1 = posBase + x * pixelSize;
                
                int x2 = y;
                int y2 = h - 1 - x;
                int pos2 = y2 * lineSize + x2 * pixelSize;
                
                bb.position( pos0 + pos1 );
                bb.get( pixel, 0, pixelSize );
                
                System.arraycopy( pixel, 0, bytes, pos2, pixelSize );
            }
        }
        
        bb.position( pos0 );
        bb.put( bytes, 0, h * lineSize );
        
        bb.position( pos0 );
        bb.limit( limit0 );
    }
    
    private void rotateTextureByMinus90Degree( AbstractTextureImage ti )
    {
        int pixelSize = ti.getPixelSize();
        int w = ti.getWidth();
        int lineSize = w * pixelSize;
        int h = ti.getHeight();
        
        ByteBuffer bb = ti.getDataBuffer();
        final int pos0 = bb.position();
        final int limit0 = bb.limit();
        
        byte[] pixel = new byte[ pixelSize ];
        byte[] bytes = new byte[ h * lineSize ];
        
        for ( int y = 0; y < h; y++ )
        {
            int posBase = y * lineSize;
            
            for ( int x = 0; x < w; x++ )
            {
                int pos1 = posBase + x * pixelSize;
                
                int x2 = w - 1 - y;
                int y2 = x;
                int pos2 = y2 * lineSize + x2 * pixelSize;
                
                bb.position( pos0 + pos1 );
                bb.get( pixel, 0, pixelSize );
                
                System.arraycopy( pixel, 0, bytes, pos2, pixelSize );
            }
        }
        
        bb.position( pos0 );
        bb.put( bytes, 0, h * lineSize );
        
        bb.position( pos0 );
        bb.limit( limit0 );
    }
    
    private AbstractTexture[] readSkyTextures( BSPEntity[] entities, AbstractTexture sampleTexture, AppearanceFactory appFactory, URL baseURL ) throws IOException
    {
        BSPEntity_worldspawn entity_worlspawn = null;
        for ( int i = 0; i < entities.length; i++ )
        {
            //if ( entities[i].className2.equals( "worldspawn" ) )
            if ( entities[i] instanceof BSPEntity_worldspawn )
            {
                entity_worlspawn = (BSPEntity_worldspawn)entities[i];
                break;
            }
        }
        
        String skyName = null;
        
        if ( entity_worlspawn != null )
        {
            // look for a worldspawn "skyname" key...
            skyName = entity_worlspawn.skyName;
            // didn't find it?  then look for a "message" key and use that as skyname
            if ( skyName == null )
                skyName = entity_worlspawn.message;
        }
        
        if ( skyName != null )
        {
            final boolean FLIP_SKYBOX_TEXTURES = true;
            
            String[] skys = { "ft", "rt", "bk", "lf", "up", "dn" };
            
            AbstractTexture[] textures = new AbstractTexture[ 6 ];
            
            for ( int sky_index = 0; sky_index < 6; sky_index++ )
            {
                String skyFilename = "gfx/env/" + skyName + skys[sky_index];
                
                /*
                // see if sky texture is in a MOD directory first...
                for ( int mod_index = 0; mod_index < config.num_mods; mod_index++ )
                {
                    String skyPathname = config.mods[mod_index].dir + skyFilename;
                    
                    textures[sky_index] = appFactory.loadOrGetTexture( skyPathname + ".tga", baseURL, FLIP_SKYBOX_TEXTURES, false, false, true, false );
                    if ( textures[sky_index] == null )
                        textures[sky_index] = appFactory.loadOrGetTexture( skyPathname + ".jpg", baseURL, FLIP_SKYBOX_TEXTURES, false, false, true, false );
                    
                    if ( textures[sky_index] != null )
                    {
                        break;  // break out of for loop
                    }
                }
                */
                
                if ( textures[sky_index] == null )
                {
                    textures[sky_index] = appFactory.loadOrGetTexture( skyFilename + ".tga", baseURL, FLIP_SKYBOX_TEXTURES, false, false, true, false );
                    if ( textures[sky_index] == null )
                        textures[sky_index] = appFactory.loadOrGetTexture( skyFilename + ".jpg", baseURL, FLIP_SKYBOX_TEXTURES, false, false, true, false );
                }
                
                if ( textures[sky_index] == null )
                {
                    textures[sky_index] = sampleTexture;
                    
                    System.err.println( "missing sky-texture: " + skyFilename + " (.tga / .jpg)" );
                }
                else
                {
                    /*
                     * HL-SkyBoxes-Textures are designed as if the SkyBox was
                     * viewed from outside. This is dumb! The textures need to
                     * be transformed to become actual SkyBox-Textures.
                     */
                    
                    flipTextureHorizontally( textures[sky_index].getImage( 0 ) );
                    
                    if ( sky_index < 4 )
                        ;//flipTextureHorizontally( textures[sky_index].getImage( 0 ) );
                    else if ( sky_index < 5 )
                        rotateTextureByMinus90Degree( textures[sky_index].getImage( 0 ) );
                    else
                        rotateTextureBy90Degree( textures[sky_index].getImage( 0 ) );
                }
            }
            
            return ( textures );
        }
        
        AbstractTexture[] textures = new AbstractTexture[ 6 ];
        
        for ( int sky_index = 0; sky_index < 6; sky_index++ )
        {
            textures[sky_index] = sampleTexture;
        }
        
        return ( textures );
    }
    
    private void readSpecialTexture( DataInputStream din, byte[][] palette, int orgWidth, int orgHeight, int width, int height, ByteBuffer bb, byte[] pixelData ) throws IOException
    {
        final boolean needsPostScaling = ( pixelData != null );
        
        final byte special_texture_transparency = (byte)0;
        
        // convert the mip palette based bitmap to RGB format...
        int size = orgWidth * orgHeight;
        int pos = 0;
        for ( int i = 0; i < size; i++ )
        {
            int palIdx = din.read();
            
            if ( !needsPostScaling )
            {
                bb.put( palette[palIdx][0] );
                bb.put( palette[palIdx][1] );
                bb.put( palette[palIdx][2] );
                
                bb.put( special_texture_transparency );
            }
            else
            {
                pixelData[pos + 2] = palette[palIdx][0];
                pixelData[pos + 1] = palette[palIdx][1];
                pixelData[pos + 0] = palette[palIdx][2];
                
                pixelData[pos + 3] = special_texture_transparency;
                
                pos += 4;
            }
        }
        
        if ( needsPostScaling )
        {
            transferScaledBytes( pixelData, 4, bb, orgWidth, orgHeight, width, height );
        }
    }
    
    private void readRegularTexture( DataInputStream din, byte[][] palette, int orgWidth, int orgHeight, int width, int height, boolean changeGamma, ByteBuffer bb, byte[] pixelData ) throws IOException
    {
        final boolean needsPostScaling = ( pixelData != null );
        
        if ( changeGamma )
        {
            float gamma = 1.0f;
            float f, inf;
            
            for ( int i = 0; i < palette.length; i++ )
            {
                for ( int j = 0; j < 3; j++ )
                {
                    f = (float)FastMath.pow( ( ( palette[i][j] & 0xFF ) + 1 ) / 256f, gamma );
                    inf = f * 255.0f + 0.5f;
                    if ( inf < 0 )
                        inf = 0f;
                    if ( inf > 255f )
                        inf = 255f;
                    palette[i][j] = (byte)inf;
                }
            }
        }
        
        // convert the mip palette based bitmap to RGB format...
        int size = orgWidth * orgHeight;
        int pos = 0;
        for ( int j = 0; j < size; j++ )
        {
            int palIdx = din.read();
            
            if ( !needsPostScaling )
            {
                bb.put( palette[palIdx][0] );
                bb.put( palette[palIdx][1] );
                bb.put( palette[palIdx][2] );
            }
            else
            {
                pixelData[pos + 2] = palette[palIdx][0];
                pixelData[pos + 1] = palette[palIdx][1];
                pixelData[pos + 0] = palette[palIdx][2];
                
                pos += 3;
            }
        }
        
        if ( needsPostScaling )
        {
            transferScaledBytes( pixelData, 3, bb, orgWidth, orgHeight, width, height );
        }
    }
    
    private final AbstractTexture createNewTexture( AbstractTextureImage mipmap0, byte[] nameBytes, AppearanceFactory appFactory, boolean isSkyTexture )
    {
        AbstractTexture texture = appFactory.createTexture( mipmap0, true && !isSkyTexture );
        if ( !isSkyTexture )
        {
            //texture.setImage( 1, mipmaps[1] );
            //texture.setImage( 2, mipmaps[2] );
            //texture.setImage( 3, mipmaps[3] );
        }
        
        String name = new String( nameBytes ).trim();
        texture.setName( name );
        
        // Xith-specific name-setter!
        try
        {
            String resName2 = this.getWadType() + "://" + this.getWADFilename() + "/" + name;
            
            Method m = texture.getClass().getMethod( "setResourceName", String.class );
            m.invoke( texture, resName2 );
        }
        catch ( Throwable t )
        {
            //t.printStackTrace();
        }
        
        return ( texture );
    }
    
    private final AbstractTexture[] readTexture( String resName, AppearanceFactory appFactory, URL baseURL, BSPEntity[] entities, boolean ignoreAnimations ) throws IOException
    {
        if ( magicNumber == MAGIC_NUMBER_WAD3 )
        {
            WADDirectoryEntry entry = wadDir.get( resName.toLowerCase() );
            
            if ( entry == null )
            {
                throw new IOException( "The resource was not found in this WAD file." );
            }
            
            InputStream in = wadResource.openStream();
            if ( !( in instanceof BufferedInputStream ) )
            {
                in = new BufferedInputStream( in );
            }
            
            DataInputStream din = new DataInputStream( in );
            
            din.skip( entry.offset );
            
            byte[] nameBytes = new byte[ 16 ];
            din.read( nameBytes );
            //System.out.println( new String( name ).trim() );
            
            int orgWidth = Integer.reverseBytes( din.readInt() );
            int orgHeight = Integer.reverseBytes( din.readInt() );
            
            int width = ImageUtility.roundUpPower2( orgWidth );
            int height = ImageUtility.roundUpPower2( orgHeight );
            
            //System.out.println( orgWidth + "x" + orgHeight + ", " + width + "x" + height );
            
            int[] offsets = new int[ 4 ];
            for ( int i = 0; i < 4; i++ )
            {
                offsets[i] = Integer.reverseBytes( din.readInt() );
                //System.out.println( offsets[i] );
            }
            
            int imgDataSize0 = offsets[1] - offsets[0];
            int imgDataSize = imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            imgDataSize0 = imgDataSize0 >> 2;
            imgDataSize += imgDataSize0;
            
            byte[] imgData = new byte[ imgDataSize ];
            din.read( imgData );
            
            /*
             * Read the palette first...
             */
            int paletteSize = StreamUtils.readSwappedShort( din );
            byte[][] palette = new byte[paletteSize][];
            for ( int i = 0; i < paletteSize; i++ )
            {
                palette[i] = new byte[] { (byte)in.read(), (byte)in.read(), (byte)in.read() };
            }
            din.close();
            
            boolean isAnimatedTexture = resName.startsWith( "+" );
            boolean isTransparentTexture = resName.startsWith( "{" );
            boolean isGlassTexture = resName.startsWith( "glass" );
            boolean isSkyTexture = resName.startsWith( "sky" );
            boolean isSpecialTexture = resName.startsWith( "clip" ) || resName.startsWith( "origin" ) || resName.startsWith( "aatrigger" );
            
            din = new DataInputStream( new ByteArrayInputStream( imgData ) );
            
            int numMipmaps = isSkyTexture ? 1 : 4;
            AbstractTextureImage[] mipmaps = new AbstractTextureImage[ numMipmaps ];
            
            for ( int i = 0; i < numMipmaps; i++ )
            {
                //System.out.println( width + ", " + height );
                
                if ( isTransparentTexture || isGlassTexture || isSpecialTexture )
                    mipmaps[i] = appFactory.createTextureImage( TextureImageFormat.RGBA, orgWidth, orgHeight, width, height );
                else
                    mipmaps[i] = appFactory.createTextureImage( TextureImageFormat.RGB, orgWidth, orgHeight, width, height );
                ByteBuffer bb = mipmaps[i].getDataBuffer();
                
                byte[] pixelData = null;
                if ( ( orgWidth != width ) || ( orgHeight != height ) )
                {
                    pixelData = new byte[ orgWidth * orgHeight * mipmaps[i].getPixelSize() ];
                }
                
                if ( isTransparentTexture )
                    readTransparentTexture( din, palette, orgWidth, orgHeight, width, height, bb, pixelData );
                else if ( isGlassTexture )
                    readGlassTexture( din, palette, orgWidth, orgHeight, width, height, bb, pixelData );
                else if ( isSkyTexture )
                    readRegularTexture( din, palette, orgWidth, orgHeight, width, height, false, bb, pixelData );
                else if ( isSpecialTexture )
                    readSpecialTexture( din, palette, orgWidth, orgHeight, width, height, bb, pixelData );
                else
                    readRegularTexture( din, palette, orgWidth, orgHeight, width, height, true, bb, pixelData );
                
                bb.position( 0 );
                if ( mipmaps[i].getFormat().hasAlpha() )
                    bb.limit( width * height * 4 );
                else
                    bb.limit( width * height * 3 );
                
                orgWidth = orgWidth >> 1;
                orgHeight = orgHeight >> 1;
                width = width >> 1;
                height = height >> 1;
            }
            
            din.close();
            
            AbstractTexture texture = createNewTexture( mipmaps[0], nameBytes, appFactory, isSkyTexture );
            
            if ( isAnimatedTexture && !ignoreAnimations )
            {
                AbstractTexture[] animFrames = new AbstractTexture[ 10 ];
                AbstractTexture offFrame = null;
                int numFrames = 0;
                if ( ( entry.fileName.charAt( 1 ) == 'A' ) || ( entry.fileName.charAt( 1 ) == 'a' ) )
                {
                    offFrame = texture;
                }
                else
                {
                    animFrames[0] = texture;
                    numFrames = 1;
                }
                
                for ( WADDirectoryEntry entry2 : wadDir.values() )
                {
                    if ( entry2.fileName.substring( 2 ).equalsIgnoreCase( entry.fileName.substring( 2 ) ) )
                    {
                        if ( entry2.fileName.charAt( 1 ) == entry.fileName.charAt( 1 ) )
                        {
                            // we already have this frame collected!
                        }
                        else if ( ( entry2.fileName.charAt( 1 ) == 'A' ) || ( entry2.fileName.charAt( 1 ) == 'a' ) )
                        {
                            offFrame = readTexture( entry2.fileName, appFactory, baseURL, entities, true )[0];
                        }
                        else
                        {
                            animFrames[numFrames++] = readTexture( entry2.fileName, appFactory, baseURL, entities, true )[0];
                        }
                    }
                }
                
                AbstractTexture[] result = new AbstractTexture[ numFrames + 1 ];
                System.arraycopy( animFrames, 0, result, 0, numFrames );
                result[result.length - 1] = offFrame;
                
                //System.out.println( resName + ": " + result.length );
                
                return ( result );
            }
            else if ( isSkyTexture )
            {
                AbstractTexture[] skyTextures = readSkyTextures( entities, texture, appFactory, baseURL );
                
                return ( new AbstractTexture[] { texture, skyTextures[0], skyTextures[1], skyTextures[2], skyTextures[3], skyTextures[4], skyTextures[5] } );
            }
            
            return ( new AbstractTexture[] { texture } );
        }
        
        return ( null );
    }
    
    public final AbstractTexture[] readTexture( String resName, AppearanceFactory appFactory ) throws IOException
    {
        return ( readTexture( resName, appFactory, null, null, false ) );
    }
    
    public final AbstractTexture[] readSkyTextures( String resName, AppearanceFactory appFactory, URL baseURL, BSPEntity[] entities ) throws IOException
    {
        return ( readTexture( resName, appFactory, baseURL, entities, false ) );
    }
    
    private HashMap<String, WADDirectoryEntry> readWADDirectory( URL wadFile ) throws IOException, IncorrectFormatException, ParsingException
    {      
        try
        {
            DataInputStream in = new DataInputStream( new BufferedInputStream( wadFile.openStream() ) );
            
            // read WAD header
            int magicNumber = in.readInt();
            if ( magicNumber != MAGIC_NUMBER_WAD3 )
            {
                throw new IncorrectFormatException( "This is not a WAD3 file!" );
            }
            
            this.magicNumber = magicNumber;
            this.wadType = "WAD3";
            
            int lumpCount = Integer.reverseBytes( in.readInt() ); // - Number of files
            int dirOffset = Integer.reverseBytes( in.readInt() ); // - Directory offset
            
            /*
            System.out.println( "WadFile: " + wadFile );
            System.out.print( " | lumps: " + lumpCount ); 
            System.out.println( " | offset: " + dirOffset );
            */
            
            HashMap<String, WADDirectoryEntry> wadDir = new HashMap<String, WADDirectoryEntry>( lumpCount );
            
            // read Lump Dir
            in.skipBytes( dirOffset - ( 3 * 4 ) );
            
            byte[] bytes16 = new byte[ 16 ];
            
            for ( int i = 0; i < lumpCount; i++ ) 
            {
                WADDirectoryEntry entry = new WADDirectoryEntry();
                
                entry.offset = Integer.reverseBytes( in.readInt() );
                entry.compFileSize = Integer.reverseBytes( in.readInt() );
                entry.uncompFileSize = Integer.reverseBytes( in.readInt() );
                
                entry.fileType = in.readByte();
                entry.compType = in.readByte();
                entry.padding = new byte[] { in.readByte(), in.readByte() };
                
                in.read( bytes16, 0, 16 );
                entry.fileName = new String( bytes16 ).trim();
                
                wadDir.put( entry.fileName.toLowerCase(), entry );
                
                //if ( getWADFilename().equals( "cs_dust.wad" ) )
                //    System.out.println( entry.fileName );
                
                //System.out.println( entry );
            }
            
            in.close();
            
            return ( wadDir );
        }
        catch ( IOException ioe )
        {
            throw ioe;
        }
        catch ( Throwable t )
        {
            throw new ParsingException( t );
        }
    }
    
    private static final String getWADFileSimpleName( URL wadResource )
    {
        String filePath = wadResource.getFile();
        
        int lastSlashPos = filePath.lastIndexOf( '/' );
        
        if ( lastSlashPos == -1 )
            return ( filePath );
        
        return ( filePath.substring( lastSlashPos + 1 ) );
    }
    
    public WADFile( URL wadResource ) throws IOException, IncorrectFormatException, ParsingException
    {
        super();
        
        this.wadResource = wadResource;
        this.wadFilename = getWADFileSimpleName( wadResource );
        
        this.wadDir = readWADDirectory( wadResource );
    }
}
