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


//****************************************************************************//
// Class declaration                                                          //
//****************************************************************************//

/*****************************************************************************/
/** The animation class.
 *****************************************************************************/
public class CalAnimation {
    // BEGIN Enumeration constants for Type
    public static final int TYPE_NONE = 0;
    public static final int TYPE_CYCLE = 1;
    public static final int TYPE_POSE = 2;
    public static final int TYPE_ACTION = 3;
    // END Enumeration constants for Type
    
    // BEGIN Enumeration constants for State
    public static final int STATE_NONE = 0;
    public static final int STATE_SYNC = 1;
    public static final int STATE_ASYNC = 2;
    public static final int STATE_IN = 3;
    public static final int STATE_STEADY = 4;
    public static final int STATE_OUT = 5;
    public static final int STATE_STOPPED = 5;
    // END Enumeration constants for State
    
    protected CalCoreAnimation coreAnimation;
    protected int type;
    protected int state;
    protected float time;
    protected float timeFactor;
    protected float weight;
    
    Vector3f rootDisplacement;
    Quaternion4f rootRotation;
    
    /*****************************************************************************/
    /** Constructs the animation instance.
     *
     * This function is the default constructor of the animation instance.
     *****************************************************************************/
    
    public  CalAnimation() {
        coreAnimation = null;
        type = TYPE_NONE;
        state = STATE_NONE;
        time = 0.0f;
        timeFactor = 1.0f;
        weight = 0.0f;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the core animation.
     *
     * This function returns the core animation on which this animation instance
     * is based on.
     *
     * @return One of the following values:
     *         \li a pointer to the core animation
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalCoreAnimation getCoreAnimation() {
        return coreAnimation;
    }
    
    
    /*****************************************************************************/
    /**
     * Returns the state.
     *
     * This function returns the state of the animation instance.
     *
     * @return One of the following states:
     *         \li \b STATE_NONE
     *         \li \b STATE_SYNC
     *         \li \b STATE_ASYNC
     *         \li \b STATE_IN
     *         \li \b STATE_STEADY
     *         \li \b STATE_OUT
     *****************************************************************************/
    
    public int getState() {
        return state;
    }
    
    
    /*****************************************************************************/
    /** Returns the time.
     *
     * This function returns the time of the animation instance.
     *
     * @return The time in seconds.
     *****************************************************************************/
    
    public float getTime() {
        return time;
    }
    
    
    /*****************************************************************************/
    /** Returns the type.
     *
     * This function returns the type of the animation instance.
     *
     * @return One of the following types:
     *         \li \b TYPE_NONE
     *         \li \b TYPE_CYCLE
     *         \li \b TYPE_POSE
     *         \li \b TYPE_ACTION
     *****************************************************************************/
    
    public int getType() {
        return type;
    }
    
    
    /*****************************************************************************/
    /** Returns the weight.
     *
     * This function returns the weight of the animation instance.
     *
     * @return The weight.
     *****************************************************************************/
    
    public float getWeight() {
        return weight;
    }
    
    public void freeze(float time, float weight) {
        this.time = time;
        this.weight = weight;
        this.state = STATE_NONE;
    }
    
    /** Get the time factor.
     *
     * @return the time factor of the animation instance.
     */
    public float getTimeFactor() {
        return timeFactor;
    }
    
    /** Set the time factor. This time factor affects only sync animation
     *
     *  @param timeFactor the time factor of the animation instance.
     *
     */
    public void setTimeFactor(float timeFactor) {
        this.timeFactor = timeFactor;
    }
    
    /** Applies a displacement to the root bone throughout - useful for aligning anims in different reference frames.
     */
    public void setRootDisplacement(Vector3f displacement) {
        rootDisplacement = displacement;
    }
    
    /** Applies a rotation to the root bone throughout - useful for aligning anims in different reference frames.
     */
    public void setRootRotation(Quaternion4f rotation) {
        rootRotation = rotation;
    }
}

