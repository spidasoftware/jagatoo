/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.tds.internal;

import org.openmali.vecmath2.Vector3f;
import java.util.ArrayList;

/**
 * A transform group that maintains a postion interpolated across a 
 * set of key frames
 *
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class PosTransform
{
    class PosFrame
    {
        public final int frameIndex;
        public final float time;
        public final Vector3f offset;
        
        public PosFrame( int frameIndex, float time, Vector3f pos )
        {
            this.frameIndex = frameIndex;
            this.time = time;
            this.offset = pos;
        }
    }
    
    final ArrayList< PosFrame > frames = new ArrayList< PosFrame >();
    
    public void addKeyFrame( int frameIndex, float time, Vector3f pos )
    {
        frames.add( new PosFrame( frameIndex, time, pos ) );
    }
    
    public int getFrameByFrameNumber( int frameNumber )
    {
        for ( int i = 0; i < frames.size(); i++ )
        {
            if ( frames.get( i ).frameIndex == frameNumber )
            {
                return ( i );
            }
        }
        
        return ( -1 );
    }
    
    /*
    private org.xith3d.scenegraph.Shape3D getShape()
    {
        TransformGroup tg = (TransformGroup)getChild( 0 );
        tg = (TransformGroup)tg.getChild( 0 );
        tg = (TransformGroup)tg.getChild( 0 );
        
        return ( (org.xith3d.scenegraph.Shape3D)tg.getChild( 0 ) );
    }
    */
    
    public void getTranslationAtTime( float time, Vector3f translation )
    {
        if ( frames.size() == 1 )
        {
            if ( frames.get( 0 ).offset == null )
                translation.setZero();
            else
                translation.set( frames.get( 0 ).offset );
            
            return;
        }
        
        PosFrame prevFrame = null;
        PosFrame nextFrame = null;
        
        int i;
        for ( i = 0; i < frames.size(); i++ )
        {
            prevFrame = frames.get( i );
            
            if ( i < frames.size() - 1 )
            {
                nextFrame = frames.get( i + 1 );
                
                if ( ( time >= prevFrame.time ) && ( time < nextFrame.time ) )
                {
                    break;
                }
            }
            else
            {
                nextFrame = frames.get( 0 );
            }
        }
        
        float deltaTime = nextFrame.time - prevFrame.time;
        if ( i == frames.size() - 1 )
        {
            deltaTime = 1 - prevFrame.time;
        }
        
        float delta = ( time - prevFrame.time ) / deltaTime;
        
        translation.set( ( nextFrame.offset.getX() * delta ) + ( prevFrame.offset.getX() * ( 1f - delta ) ),
                         ( nextFrame.offset.getY() * delta ) + ( prevFrame.offset.getY() * ( 1f - delta ) ),
                         ( nextFrame.offset.getZ() * delta ) + ( prevFrame.offset.getZ() * ( 1f - delta ) )
                       );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        String s = this.getClass().getSimpleName() + " ( ";
        
        for ( int i = 0; i < frames.size(); i++ )
        {
            if ( i > 0 )
                s += ", ";
            
            s += frames.get( i ).time + ": " + frames.get( i ).offset;
        }
        
        s += " )";
        
        return ( s );
    }
    
    public PosTransform()
    {
    }
}
