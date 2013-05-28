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
package org.jagatoo.loaders.models.tds.chunks.processors;

import java.io.IOException;

import org.openmali.vecmath2.Quaternion4f;

import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.tds.TDSFile;
import org.jagatoo.loaders.models.tds.internal.ModelContext;
import org.jagatoo.loaders.models.tds.internal.RotTransform;
import org.jagatoo.logging.JAGTLog;

/**
 * A processor to handle the "RotTrack" chunk
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class RotTrackProcessor extends TransformTrackProcessor
{
    @Override
    public void process( TDSFile file, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, ModelContext context, int length ) throws IOException
    {
        /*int flags = */file.readUnsignedShort();
        //System.out.println( "flags: " + Integer.toBinaryString( flags ) );
        file.skipBytes( 8 ); // four unknown ushorts.
        int numKeys = file.readUnsignedInt();
        
        context.orientation = new RotTransform();
        
        JAGTLog.debug( "Rotation key frames: ", numKeys );
        
        Quaternion4f prevRot = null;
        Quaternion4f tmp = new Quaternion4f();
        
        for ( int i = 0; i < numKeys; i++ )
        {
            int frameNumber = file.readUnsignedInt();
            /*int accelerationData = */file.readUnsignedShort();
            
            float angle = file.readFloat();
            float axisX = file.readFloat();
            float axisY = file.readFloat();
            float axisZ = file.readFloat();
            
            if ( ( angle == 0f ) || ( ( axisX == 0f ) && ( axisY == 0f ) && ( axisZ == 0f ) ) )
            {
                if ( numKeys == 1 )
                    context.orientation.addKeyFrame( frameNumber, (float)frameNumber / (float)context.framesCount, null );
                else
                    context.orientation.addKeyFrame( frameNumber, (float)frameNumber / (float)context.framesCount, Quaternion4f.IDENTITY );
            }
            else if ( prevRot == null )
            {
                Quaternion4f rot = new Quaternion4f();
                rot.setFromAxisAngle( axisX, axisY, axisZ, angle );
                //rot.mul( Quaternion4f.Z_UP_TO_Y_UP );
                
                context.orientation.addKeyFrame( frameNumber, (float)frameNumber / (float)context.framesCount, rot );
                
                prevRot = rot;
            }
            else
            {
                tmp.setFromAxisAngle( axisX, axisY, axisZ, angle );
                
                Quaternion4f rot = new Quaternion4f( prevRot );
                rot.mul( tmp );
                
                context.orientation.addKeyFrame( frameNumber, (float)frameNumber / (float)context.framesCount, rot );
                
                prevRot = rot;
            }
            
            //X3DLog.debug( "\tRotation key frame: ", frameNumber, " : ", axes );
        }
        
        checkAndApplyTransform( context, false, nodeFactory, animFactory, siHandler, file.convertZup2Yup() );
    }
    
    public RotTrackProcessor()
    {
    }
}
