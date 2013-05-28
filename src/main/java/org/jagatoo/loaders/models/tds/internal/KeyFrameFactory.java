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
package org.jagatoo.loaders.models.tds.internal;

import java.lang.reflect.Array;

import org.jagatoo.loaders.models._util.AnimationFactory;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class KeyFrameFactory
{
    public static Object[] createKeyFrames( Matrix4f masterTransform, int framesCount, PosTransform posTransform, RotTransform rotTransform, ScaleTransform scaleTransform, AnimationFactory animFactory )
    {
        Object[] frames = null;
        
        Vector3f translation = new Vector3f();
        Quaternion4f rotation = new Quaternion4f();
        Tuple3f scale = new Tuple3f();
        
        for ( int i = 0; i <= framesCount; i++ )
        {
            float frameTime = (float)i / (float)framesCount;
            
            if ( ( posTransform != null ) && ( posTransform.frames.size() > 0 ) )
                posTransform.getTranslationAtTime( frameTime, translation );
            else
                translation.setZero();
            if ( ( rotTransform != null ) && ( rotTransform.frames.size() > 0 ) )
                rotTransform.getRotationAtTime( frameTime, rotation );
            else
                rotation.setIdentity();
            if ( ( scaleTransform != null ) && ( scaleTransform.frames.size() > 0 ) )
                scaleTransform.getScaleAtTime( frameTime, scale );
            else
                scale.set( 1f, 1f, 1f );
            
            Object frame = animFactory.createMeshTransformKeyFrame( frameTime, translation, rotation, scale );
            
            if ( frames == null )
            {
                frames = (Object[])Array.newInstance( frame.getClass(), framesCount + 1 );
            }
            
            frames[i] = frame;
            
            if ( masterTransform != null )
            {
                animFactory.transformMeshTransformKeyFrame( masterTransform, frame );
            }
        }
        
        return ( frames );
    }
}
