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
package org.jagatoo.loaders.models.collada.datastructs.controllers;

import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameTuple3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.util.Interpolation;

/**
 * This class is used to interpolate Tuple3f from
 * keyframe data.
 * 
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class KeyFrameComputer
{
    /**
     * Interpolates a Tuple3f between two keyframes.
     * 
     * @param currentTime The time in the animation
     * @param keyFrames The keyframes of the animation (translation or scaling)
     * @param toInterpolate
     */
    public static void computeTuple3f( long currentTime, List<KeyFrameTuple3f> keyFrames, Tuple3f toInterpolate )
    {
        int frame = 0;
        
        if ( ( keyFrames == null ) || ( frame >= keyFrames.size() ) )
            return;
        
        frame = KeyFrame.searchNextFrame( keyFrames, currentTime );
        
        if ( frame == 0 )
        {
            /*
             * Case 1 : we're before the first keyframe
             * Solution : take the first keyframe
             */
            toInterpolate.set( keyFrames.get( frame ).getValue() );
        }
        else if ( frame == keyFrames.size() )
        {
            /*
             * Case 2 : we're after the last keyframe
             * Solution : take the last keyframe
             */
            toInterpolate.set( keyFrames.get( frame - 1 ).getValue() );
        }
        else
        {
            /*
             * Case 3 : we're between two keyframes
             * Solution : interpolate
             */
            JAGTLog.debug( "Interpolating..." );
            KeyFrameTuple3f prevFrame = keyFrames.get( frame - 1 );
            KeyFrameTuple3f nextFrame = keyFrames.get( frame );
            
            // time distance beetween both frames
            long timeDist = nextFrame.time - prevFrame.time;
            // compute the "delta" = value in the [0-1] range that
            // represents our "position" between the two frames.
            float delta = ( currentTime - prevFrame.time ) / timeDist;
            
            // Finally, interpolate
            Interpolation.interpolate( prevFrame.getValue(), nextFrame.getValue(), delta, toInterpolate );
        }
    }
    
    /**
     * Interpolate a Quaternion4f between two keyframes
     * @param currentTime The time in the animation
     * @param keyFrames The keyframes of the animation (translation or scaling)
     * @param toInterpolate
     */
    public static void computeQuaternion4f( long currentTime, List<KeyFrameQuat4f> keyFrames, Quaternion4f toInterpolate )
    {
        int frame = KeyFrame.searchNextFrame( keyFrames, currentTime );
        JAGTLog.debug( "Frame = ", frame );
        
        if ( frame == 0 )
        {
            /*
             * Case 1 : we're before the first keyframe
             * Solution : take the first keyframe
             */
            toInterpolate.set( keyFrames.get( frame ).getValue() );
        }
        else if ( frame == keyFrames.size() )
        {
            /*
             * Case 2 : we're after the last keyframe
             * Solution : take the last keyframe
             */
            toInterpolate.set( keyFrames.get( frame - 1 ).getValue() );
        }
        else
        {
            /*
             * Case 3 : we're between two keyframes
             * Solution : interpolate
             */
            KeyFrameQuat4f prevFrame = keyFrames.get( frame - 1 );
            KeyFrameQuat4f nextFrame = keyFrames.get( frame );
            
            // time distance beetween both frames
            long timeDist = nextFrame.time - prevFrame.time;
            // compute the "delta" = value in the [0-1] range that
            // represents our "position" between the two frames.
            float delta = ( currentTime - prevFrame.time ) / timeDist;
            
            // Finally, interpolate
            Interpolation.nlerp( prevFrame.getValue(), nextFrame.getValue(), delta, toInterpolate );
        }
    }
}
