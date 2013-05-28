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

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;


/*****************************************************************************/
/** The core track class.
 *****************************************************************************/
public class CalCoreTrack {
    protected int coreBoneId;
    
    protected SortedSet<CalCoreKeyframe> coreKeyframes = new TreeSet<CalCoreKeyframe>(new Comparator<Object>() {
        public int compare(Object o1, Object o2) {
            if (o1 instanceof Float) {
                if (o2 instanceof Float)
                    return Float.compare(((Float) o1).floatValue(), ((Float) o2).floatValue());
                else
                    return Float.compare(((Float) o1).floatValue(), ((CalCoreKeyframe) o2).time);
            } else if (o2 instanceof Float)
                return Float.compare(((CalCoreKeyframe) o1).time, ((Float) o2).floatValue());
            else
                return Float.compare(((CalCoreKeyframe) o1).time, ((CalCoreKeyframe) o2).time);
        }
        @SuppressWarnings("unused")
		public boolean equals(Object o1, Object o2) { return compare(o1, o2) == 0; }
    }); // sorted by time
    
    /*****************************************************************************/
    /** Constructs the core track instance.
     *
     * This function is the default constructor of the core track instance.
     *****************************************************************************/
    
    public  CalCoreTrack() {
        coreBoneId = -1;
    }
    
    public CalCoreTrack(int coreBoneId) {
        this.coreBoneId = coreBoneId;
    }
    
    
    /*****************************************************************************/
    /** Adds a core keyframe.
     *
     * This function adds a core keyframe to the core track instance.
     *
     * @param coreKeyframe A pointer to the core keyframe that should be added.
     *****************************************************************************/
    
    public void addCoreKeyframe(CalCoreKeyframe coreKeyframe) {
        coreKeyframes.add(coreKeyframe);
    }
    
    
    /*****************************************************************************/
    /** Returns the ID of the core bone.
     * 
     * This function returns the ID of the core bone to which the core track
     * instance is attached to.
     *
     * @return One of the following values:
     *         \li the \b ID of the core bone
     *         \li \b -1 if an error happend
     *****************************************************************************/
    
    public int getCoreBoneId() {
        return coreBoneId;
    }
    
    
    /*****************************************************************************/
    /** Returns the core keyframe map.
     *
     * This function returns the map that contains all core keyframes of the core
     * track instance.
     *
     * @return A reference to the core keyframe map.
     *****************************************************************************/
    
    public SortedSet<CalCoreKeyframe> getCoreKeyFrames() {
        return coreKeyframes;
    }
    
    
    /*****************************************************************************/
    /** Returns a specified state.
     *
     * This function returns the state (translation and rotation of the core bone)
     * for the specified time and duration.
     *
     * @param time The time in seconds at which the state should be returned.
     * @param translation A reference to the translation reference that will be
     *                    filled with the specified state.
     * @param rotation A reference to the rotation reference that will be filled
     *                 with the specified state.
     *****************************************************************************/
    
    @SuppressWarnings("unchecked")
	public void getState(float time, Vector3f translation, Quaternion4f rotation) {
        // This is rewritten from Cal3d for java.util.SortedMap semantics
        
        // partition the keyframe map before the requested one
        SortedSet<CalCoreKeyframe> setCoreKeyframeBefore = ((SortedSet) coreKeyframes).headSet(new Float(time));
        
        // partition the keyframe map after the requested time
        SortedSet<CalCoreKeyframe> setCoreKeyframeAfter = ((SortedSet) coreKeyframes).tailSet(new Float(time));
        
        // check if the time is after the last keyframe
        if (setCoreKeyframeAfter.isEmpty() && !setCoreKeyframeBefore.isEmpty()) {
            // return the last keyframe state
            CalCoreKeyframe coreKeyframeBefore = setCoreKeyframeBefore.last();
            rotation.set(coreKeyframeBefore.getRotation());
            translation.set(coreKeyframeBefore.getTranslation());
            
            return;
        }
        
        // check if the time is before the first keyframe
        if (setCoreKeyframeBefore.isEmpty() && !setCoreKeyframeAfter.isEmpty()) {
            // return the first keyframe state
            CalCoreKeyframe coreKeyframeAfter = setCoreKeyframeAfter.first();
            rotation.set(coreKeyframeAfter.getRotation());
            translation.set(coreKeyframeAfter.getTranslation());
            
            return;
        }
        
        try {
        CalCoreKeyframe coreKeyframeBefore = setCoreKeyframeBefore.last();
        CalCoreKeyframe coreKeyframeAfter = setCoreKeyframeAfter.first();
        
        // calculate the blending factor between the two keyframe states
        float blendFactor = (time - coreKeyframeBefore.getTime()) / (coreKeyframeAfter.getTime() - coreKeyframeBefore.getTime());
        
        // blend between the two keyframes
        translation.set(coreKeyframeBefore.getTranslation());
        translation.interpolate(coreKeyframeAfter.getTranslation(), blendFactor);
        
        rotation.set(coreKeyframeBefore.getRotation());
        rotation.interpolate(coreKeyframeAfter.getRotation(), blendFactor);
        //QuatInterpolator.interpolate(rotation,coreKeyframeAfter.getRotation(), blendFactor);
        } catch (NoSuchElementException e) {
        	System.out.println("empty cal core track");
        }
    }
    
    
    /*****************************************************************************/
    /** Sets the ID of the core bone.
     *
     * This function sets the ID of the core bone to which the core track instance
     * is attached to.
     *
     * @param coreBoneId The ID of the bone to which the core track instance should
     *                   be attached to.
     *****************************************************************************/
    
    public void setCoreBoneId(int coreBoneId) {
        if(coreBoneId < 0)
            throw new IllegalArgumentException();
        
        this.coreBoneId = coreBoneId;
    }
    
    /*****************************************************************************/
    /** Scale the core track.
     *
     * This function rescale all the data that are in the core track instance.
     *
     * @param factor A float with the scale factor
     *
     *****************************************************************************/
    
    public void scale(float factor) {
        for (CalCoreKeyframe keyFrame : coreKeyframes) {
            keyFrame.getTranslation().scale(factor);
        }
    }
}

