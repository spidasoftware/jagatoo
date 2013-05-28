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
package org.jagatoo.input.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Abstraction of a Mouse cursor.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Cursor
{
    public static final Cursor DEFAULT_CURSOR = new Cursor( "DEFAULT_CURSOR" );
    
    private final BufferedImage[] images;
    private final int[] delays;
    
    private int width;
    private int height;
    
    private int hotSpotX;
    private int hotSpotY;
    
    private final String name;
    
    private boolean dirty = true;
    
    private Object cursorImpl = null;
    
    public void markDirty()
    {
        this.dirty = true;
    }
    
    public void markClean()
    {
        this.dirty = false;
    }
    
    public final boolean isDirty()
    {
        return ( dirty );
    }
    
    public final String getName()
    {
        return ( name );
    }
    
    public final int getWidth()
    {
        return ( width );
    }
    
    public final int getHeight()
    {
        return ( height );
    }
    
    public final int getHotSpotX()
    {
        return ( hotSpotX );
    }
    
    public final int getHotSpotY()
    {
        return ( hotSpotY );
    }
    
    /**
     * @return the number of images in this Cursor.
     */
    public final int getImagesCount()
    {
        return ( images.length );
    }
    
    /**
     * @param index
     * 
     * @return the index'th image from this Cursor.
     */
    public final BufferedImage getImage( int index )
    {
        return ( images[ index ] );
    }
    
    /**
     * @param index
     * 
     * @return the delay assotiated with the index'th image.
     */
    public final int getDelay( int index )
    {
        return ( delays[ index ] );
    }
    
    public void setCursorObject( Object cursorImpl )
    {
        this.cursorImpl = cursorImpl;
    }
    
    public Object getCursorObject()
    {
        return ( cursorImpl );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( this.getClass().getName() + " \"" + ( ( getName() == null ) ? "NO_NAME" : getName() ) + "\"" );
    }
    
    public Cursor( BufferedImage[] images, int[] delays, int hotSpotX, int hotSpotY, String name )
    {
        if ( images.length == 0 )
        {
            throw new IllegalArgumentException( "You must provide at least one image." );
        }
        
        if ( images.length != delays.length )
        {
            throw new IllegalArgumentException( "The images array and the delays array must have the same length." );
        }
        
        if ( images[ 0 ] == null )
        {
            throw new IllegalArgumentException( "None of the images must be null." );
        }
        
        final int width = images[ 0 ].getWidth();
        final int height = images[ 0 ].getHeight();
        
        for ( int i = 0; i < images.length; i++ )
        {
            if ( images[ i ] == null )
            {
                throw new IllegalArgumentException( "None of the images must be null." );
            }
            
            if ( ( images[ i ].getWidth() != width ) || ( images[ i ].getHeight() != height ) )
            {
                throw new IllegalArgumentException( "All images must have the same size." );
            }
        }
        
        this.images = new BufferedImage[ images.length ];
        System.arraycopy( images, 0, this.images, 0, images.length );
        
        this.delays = new int[ delays.length ];
        System.arraycopy( delays, 0, this.delays, 0, delays.length );
        
        this.width = width;
        this.height = height;
        this.hotSpotX = hotSpotX;
        this.hotSpotY = hotSpotY;
        
        this.name = name;
    }
    
    public Cursor( BufferedImage[] images, int[] delays, int hotSpotX, int hotSpotY )
    {
        this( images, delays, hotSpotX, hotSpotY, null );
    }
    
    public Cursor( BufferedImage image, int hotSpotX, int hotSpotY, String name )
    {
        this( new BufferedImage[] { image }, new int[] { 0 }, hotSpotX, hotSpotY, name );
    }
    
    public Cursor( BufferedImage image, int hotSpotX, int hotSpotY )
    {
        this( image, hotSpotX, hotSpotY, null );
    }
    
    private static BufferedImage[] createImagesArray( URL[] imageResources ) throws IOException
    {
        BufferedImage[] images = new BufferedImage[ imageResources.length ];
        
        for ( int i = 0; i < imageResources.length; i++ )
        {
            images[ i ] = ImageIO.read( imageResources[ i ] );
        }
        
        return ( images );
    }
    
    public Cursor( URL[] imageResources, int[] delays, int hotSpotX, int hotSpotY, String name ) throws IOException
    {
        this( createImagesArray( imageResources ), delays, hotSpotX, hotSpotY, name );
    }
    
    public Cursor( URL[] imageResources, int[] delays, int hotSpotX, int hotSpotY ) throws IOException
    {
        this( imageResources, delays, hotSpotX, hotSpotY, null );
    }
    
    public Cursor( URL imageResource, int hotSpotX, int hotSpotY, String name ) throws IOException
    {
        this( new URL[] { imageResource }, new int[] { 0 }, hotSpotX, hotSpotY, name );
    }
    
    public Cursor( URL imageResource, int hotSpotX, int hotSpotY ) throws IOException
    {
        this( imageResource, hotSpotX, hotSpotY, null );
    }
    
    private Cursor( String name )
    {
        this.images = null;
        this.delays = null;
        this.name = name;
    }
}
