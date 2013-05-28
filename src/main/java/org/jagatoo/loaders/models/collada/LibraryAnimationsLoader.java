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
package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Bone;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrame;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameTuple3f;
import org.jagatoo.loaders.models.collada.datastructs.animation.KeyFrameQuat4f;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.stax.XMLAnimation;
import org.jagatoo.loaders.models.collada.stax.XMLChannel;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryAnimations;
import org.jagatoo.loaders.models.collada.stax.XMLChannel.ChannelType;
import org.jagatoo.logging.JAGTLog;

/**
 * Library animations loader.
 *
 * @author Matias Leone (aka Maguila)
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryAnimationsLoader
{
    private static final int MAX_ANIMATIONS_PER_BONE = 4;
    
    /**
     * Joins all the rotation key frames which has the same frame time.
     * 
     * @param bone
     */
    private static void compressRotationKeyFrames( Bone bone )
    {
        //Map<Float, List<KeyFrame>> framesMap = new HashMap<Float, List<KeyFrame>>();
        
        // TODO: Comming soon
        /*
        float currentTime;
        List<KeyFrame> frames;
        KeyFrame frame;
        String name;
        
        // loop through each frame
        for ( int i = 0; i < bone.rotKeyFrames.size(); i++ )
        {
            name = bone.getName();
            // see if it's a new frame time...
            if ( !framesMap.containsKey( name ) )
            {
                frames = new ArrayList<KeyFrame>();
                framesMap.put( name, frames );
                currentTime = bone.rotKeyFrames.get( i ).getTime();
                
                // search other two key frames with the same time, and add them to the map list...
                for (int j = i + 1; j < bone.rotKeyFrames.size() ; j++)
                {
                    frame = bone.rotKeyFrames.get( j );
                    if( frame.getTime() == currentTime )
                    {
                        frames.add( frame );
                    }
                }
            }
        }
        */
        
        // build the new compress frames
    }
    
    /**
     * Loads the library of animations.
     * 
     * @param colladaFile
     * @param libAnim
     */
    public static void loadLibraryAnimations( AssetFolder colladaFile, XMLLibraryAnimations libAnim )
    {
        Collection<XMLAnimation> anims = libAnim.animations.values();
        JAGTLog.debug( "There ", ( anims.size() > 1 ? "are" : "is" ), " ", anims.size(), " animation", ( anims.size() > 1 ? "s" : "" ), " in this file." );
        
        HashMap<String, COLLADAAction> colAnims = colladaFile.getLibraryAnimations().getAnimations();
        
        /*
         * for( all Skeletons ) {
         *
         * But we must know what skeleton belongs each bone
         *
         */
        
        for ( Iterator<Skeleton> iterator = colladaFile.getLibraryVisualsScenes().getSkeletons().values().iterator(); iterator.hasNext(); )
        {
            Skeleton skeleton = iterator.next();
            //create new Action
            skeleton.resetIterator();
            JAGTLog.debug( "Creating new COLLADAAction with ID of ", skeleton.getRootBone().getName(), "-action." );
            COLLADAAction currAction = new COLLADAAction( skeleton.getRootBone().getName() + "-action" );
            currAction.setSkeleton( skeleton );
            
            // loop through each bone
            int animCount;
            KeyFrame keyFrame;
            Iterator<Bone> it = currAction.getSkeleton().iterator();
            while ( it.hasNext() )
            {
                Bone bone = it.next();
                
                JAGTLog.debug( "Loading animations for bone ", bone.getName() );
                
                JAGTLog.increaseIndentation();
                
                // search the animations for each bone, max 4 ( 3 rots and 1 trans )
                animCount = 0;
                ArrayList<KeyFrameQuat4f> rotAnims = new ArrayList<KeyFrameQuat4f>();
                for (XMLAnimation animation : anims)
                {
                    if ( animCount < MAX_ANIMATIONS_PER_BONE && animation.getTargetBone().equals( bone.getName() ) )
                    {
                        JAGTLog.debug( "Loading animation ", animation.name, " of type ", animation.getType(),
                                       ( animation.getType() == ChannelType.ROTATE ? ( " and of axis " + animation.getRotationAxis() ) : "" ) );
                        if ( animation.getType() == null )
                            animation.channels.get( 0 ).type = XMLChannel.ChannelType.SCALE;
                        
                        switch ( animation.getType() )
                        {
                            case TRANSLATE:
                                
                                // it's a translation key frame
                                JAGTLog.debug( "Translation key frame..." );
                                for ( int j = 0, k = 0; j < animation.getInput().length; j++, k += 3 )
                                {
                                    keyFrame = KeyFrame.buildPoint3fKeyFrame( animation.getInput()[j],
                                                                              animation.getOutput(),
                                                                              k
                                                                            );
                                    //currAction.getSkeleton().transKeyFrames.add( (KeyFramePoint3f)keyFrame );
                                }
                                
                                break;
                                
                            case ROTATE :
                                
                                // it's a rotation key frame
                                for ( int j = 0; j < animation.getInput().length; j++ )
                                {
                                    keyFrame = KeyFrame.buildQuaternion4fKeyFrame(
                                            animation.getInput()[j],
                                            animation.getOutput()[j],
                                            animation.getRotationAxis()
                                            );
                                    rotAnims.add( (KeyFrameQuat4f)keyFrame );
                                }
                                
                                break;
                                
                            case SCALE :
                                
                                // it's a scale key frame
                                for ( int j = 0, k = 0; j < animation.getInput().length; j++, k += 3 )
                                {
                                    keyFrame = KeyFrame.buildPoint3fKeyFrame(
                                            animation.getInput()[j],
                                            animation.getOutput(),
                                            k
                                            );
                                    bone.scaleKeyFrames.add( (KeyFrameTuple3f)keyFrame );
                                }
                                
                                break;
                        } // end of animation type switch
                        
                        // update total anims for the bone
                        animCount++;
                    } //end of bone validation if loop
                } // end of animation iterator
                
                compressRotationKeyFrames( bone );
                currAction.rotKeyFrames.put(bone, rotAnims);
            } // end of bone iterator
            
            // add current Action
            currAction.setSkeleton( skeleton );
            colAnims.put( currAction.getId(), currAction );
            JAGTLog.decreaseIndentation(); 
        } // end of skeleton iterator
        
        for ( Iterator<Controller> iterator = colladaFile.getLibraryControllers().getControllers().values().iterator(); iterator.hasNext(); )
        {
            Controller name = iterator.next();
            
			if ( name instanceof SkeletalController )
			{
				( (SkeletalController)name ).libAnims = colladaFile.getLibraryAnimations();
			}
        }
    }
}
