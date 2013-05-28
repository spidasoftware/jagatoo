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

import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;

import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.tds.TDSFile;
import org.jagatoo.loaders.models.tds.internal.ModelContext;
import org.jagatoo.loaders.models.tds.internal.PosTransform;
import org.jagatoo.logging.JAGTLog;

/**
 * A chunk to process "PosTrack" chunks
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class PosTrackProcessor extends TransformTrackProcessor
{
    public static final int ANIM_TYPE_SINGLE = 0;
    public static final int ANIM_TYPE_REPEAT = 2;
    public static final int ANIM_TYPE_LOOP = 3;
    
    /**
     * This only reads the spline data and should be part
     * of the track header when it gets invented.
     * 
     * The possible spline values are are
     * <ol>
     * <li> Tension
     * <li> Continuity
     * <li> Bias
     * <li> EaseTo
     * <li> EaseFrom
     * </ol>
     * 
     * @param accelerationData an integer representing the bits that
     * determine which of the five possible spline terms are present in the
     * data and should be read.
     * @param file
     */
    private void getSplineTerms( final int accelerationData, TDSFile file ) throws IOException
    {
        if ( accelerationData == 0 )
            return;
        
        int bits = accelerationData;
        
        for ( int i = 0; i < 5; i++ )
        {
            bits = bits >>> i;
            if ( ( bits & 1 ) == 1 )
            {
                file.readFloat();
            }
        }
    }
    
    @Override
    public void process( TDSFile file, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, ModelContext context, int length ) throws IOException
    {
        /*int flags = */file.readUnsignedShort();
        //int animType = flags & 0x03; // should be one of 0, 2, 3.
        //boolean lockX = ( ( flags & 8 ) != 0 );
        //boolean lockY = ( ( flags & 16 ) != 0 );
        //boolean lockZ = ( ( flags & 32 ) != 0 );
        //boolean unlinkX = ( ( flags & 64 ) != 0 );
        //boolean unlinkY = ( ( flags & 128 ) != 0 );
        //boolean unlinkZ = ( ( flags & 256 ) != 0 );
        //System.out.println( "flags: " + Integer.toBinaryString( flags ) );
        file.skipBytes( 8 ); // four unknown ushorts.
        int numKeys = file.readUnsignedInt();
        
        context.translation = new PosTransform();
        
        JAGTLog.debug( "Translation key frames: ", numKeys );
        
        for ( int i = 0; i < numKeys; i++ )
        {
            int frameNumber = file.readUnsignedInt();
            int accelerationData = file.readUnsignedShort();
            
            getSplineTerms( accelerationData, file );
            
            float x = file.readFloat();
            float y = file.readFloat();
            float z = file.readFloat();
            
            Vector3f translation;
            if ( ( numKeys == 1 ) && ( x == 0f ) && ( y == 0f ) && ( z == 0f ) )
                translation = null;
            else
                translation = new Vector3f( x, y, z );
                //translation = new Vector3f( x, z, -y );
            
            if ( context.pivot == null )
            {
                context.pivot = new Point3f( 0f, 0f, 0f );
            }
            
            JAGTLog.debug( "\tTranslation key frame: ", frameNumber + " : ", translation );
            
            context.translation.addKeyFrame( frameNumber, (float)frameNumber / (float)context.framesCount, translation );
        }
        
        checkAndApplyTransform( context, false, nodeFactory, animFactory, siHandler, file.convertZup2Yup() );
    }
    
    public PosTrackProcessor()
    {
    }
}
