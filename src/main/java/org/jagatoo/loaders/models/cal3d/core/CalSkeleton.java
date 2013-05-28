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
package org.jagatoo.loaders.models.cal3d.core;

import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;
import java.util.Vector;


/*****************************************************************************/
/** The skeleton class.
 *****************************************************************************/

/** An instance of a core skeleton with its own positions for the bones.
 *  The Cal3d structure is very open but it is my intention to close this off and
 *  move much of the data into native storage so an optimised mechanism can do the
 *  bone transforms - either using the vector co-pro or on the graphics hardware
 *  with a vertex program. In the meantime avoid manipulating CalBones directly.
 *
 *  @author Transcribed from the original C by Dave Lloyd, 2003.
 */
public class CalSkeleton {
    protected CalCoreSkeleton coreSkeleton;
    protected CalBone [ ] vectorBone;
    
    
    /*****************************************************************************/
    /** Constructs the skeleton instance based on a core skeleton.
     *
     * This function is the default constructor of the skeleton instance.
     *
     * @param coreSkeleton the core skeleton on which this skeleton
     *                      instance should be based on.
     *****************************************************************************/
    
    public  CalSkeleton(CalCoreSkeleton coreSkeleton) {
        this.coreSkeleton = coreSkeleton;
        
        // clone the skeleton structure of the core skeleton
        Vector<CalCoreBone> vectorCoreBone = coreSkeleton.getCoreBones();
        
        // get the number of bones
        int boneCount = vectorCoreBone.size();
        
        // reserve space in the bone vector
        vectorBone = new CalBone [boneCount];
        
        // clone every core bone
        for(int boneId = 0; boneId < boneCount; boneId++) {
            CalBone bone = new CalBone(vectorCoreBone.elementAt(boneId));
            
            // set skeleton in the bone instance
            bone.setSkeleton(this);
            
            // insert bone into bone vector
            vectorBone [boneId] = bone;
        }
    }
    
    
    /*****************************************************************************/
    /** Calculates the state of the skeleton instance.
     *
     * This function calculates the state of the skeleton instance by recursively
     * calculating the states of its bones.
     *****************************************************************************/
    
    public void calculateState() {
        // calculate all bone states of the skeleton
        
        for (int rootBoneId : coreSkeleton.getListRootCoreBoneId()) {
            vectorBone [rootBoneId].calculateState();
        }
    }
    
    
    /*****************************************************************************/
    /** Clears the state of the skeleton instance.
     *
     * This function clears the state of the skeleton instance by recursively
     * clearing the states of its bones.
     *****************************************************************************/
    
    public void clearState() {
        // clear all bone states of the skeleton
        for (int n = 0; n < vectorBone.length; n++) {
            vectorBone [n].clearState();
        }
    }
    
    
    /*****************************************************************************/
    /** Provides access to a bone.
     *
     * This function returns the bone with the given ID.
     *
     * @param boneId The ID of the bone that should be returned.
     *
     * @return the bone
     *****************************************************************************/
    
    public CalBone getBone(int boneId) {
        return vectorBone[boneId];
    }
    
    /** Gets the translation needed to go from the skeleton origin to the bone end.
     */
    
    public Vector3f getBoneTranslation(CalCoreBone coreBone) {
        return vectorBone[coreBone.id].getTranslationAbsolute();
    }
    
    /** Gets the rotation needed to go from the skeleton origin to the bone end.
     */
    
    public Quaternion4f getBoneRotation(CalCoreBone coreBone) {
        return vectorBone[coreBone.id].getRotationAbsolute();
    }
    
    
    /*****************************************************************************/
    /** Provides access to the core skeleton.
     *
     * This function returns the core skeleton on which this skeleton instance is
     * based on.
     *
     * @return One of the following values:
     *         \li a pointer to the core skeleton
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalCoreSkeleton getCoreSkeleton() {
        return coreSkeleton;
    }
    
    
    /*****************************************************************************/
    /** @return the bone vector.
     * 
     * This function returns the vector that contains all bones of the skeleton
     * instance.
     *
     * @return A reference to the bone vector.
     *****************************************************************************/
    
    public CalBone [ ] getBones() {
        return vectorBone;
    }
    
    
    /*****************************************************************************/
    /** Locks the state of the skeleton instance.
     *
     * This function locks the state of the skeleton instance by recursively
     * locking the states of its bones.
     *****************************************************************************/
    
    public void lockState() {
        // lock all bone states of the skeleton
        for (int n = 0; n < vectorBone.length; n++) {
            vectorBone [n].lockState();
        }
    }
    
    
    //****************************************************************************//
}