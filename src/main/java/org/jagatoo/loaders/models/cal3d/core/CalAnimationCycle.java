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


//****************************************************************************//
// Class declaration                                                          //
//****************************************************************************//

/*****************************************************************************/
/** The animation cycle class.
 *****************************************************************************/
public class CalAnimationCycle extends CalAnimation {
    protected float targetDelay;
    protected float targetWeight;
    
    /*****************************************************************************/
    /** Constructs the animation cycle instance.
     *
     * This function is the default constructor of the animation cycle instance.
     *****************************************************************************/
    
    public  CalAnimationCycle(CalCoreAnimation coreAnimation) {
        if(coreAnimation == null)
            throw new IllegalArgumentException();
        
        this.coreAnimation = coreAnimation;
        
        type = TYPE_CYCLE;
        state = STATE_SYNC;
        // set default weights and delay
        weight = 0.0f;
        targetDelay = 0.0f;
        targetWeight = 0.0f;
    }
    
    
    /*****************************************************************************/
    /** Interpolates the weight of the animation cycle instance.
     *
     * This function interpolates the weight of the animation cycle instance to a
     * new value in a given amount of time.
     *
     * @param weight The weight to interpolate the animation cycle instance to.
     * @param delay The time in seconds until the new weight should be reached.
     *****************************************************************************/
    
    public void blend(float weight, float delay) {
        targetWeight = weight;
        targetDelay = delay;
    }
    
    
    /*****************************************************************************/
    /** Puts the animation cycle instance into async state.
     *
     * This function puts the animation cycle instance into async state, which
     * means that it will end after the current running cycle.
     *
     * @param time The time in seconds at which the animation cycle instance was
     *             unlinked from the global mixer animation cycle.
     * @param duration The current duration of the global mixer animation cycle in
     *                 seconds at the time of the unlinking.
     *****************************************************************************/
    
    public void setAsync(float time, float duration) {
        // check if thie animation cycle is already async
        if(state != STATE_ASYNC) {
            if(duration == 0.0f) {
                timeFactor = 1.0f;
                time = 0.0f;
            } else {
                timeFactor = coreAnimation.getDuration() / duration;
                time = time * timeFactor;
            }
            
            state = STATE_ASYNC;
        }
    }
    
    /** Puts the animation cycle into async state starting from animation time 0 with the default duration.
     */
    public void setAsync() { setAsync(0, coreAnimation.getDuration()); }
    
    
    /*****************************************************************************/
    /** Updates the animation cycle instance.
     *
     * This function updates the animation cycle instance for a given amount of
     * time.
     *
     * @param deltaTime The elapsed time in seconds since the last update.
     *
     * @return One of the following values:
     * <ul>
     *     <li> true if the animation cycle instance is still active
     *     <li> false if the execution of the animation cycle instance has
     *             ended
     * </ul>
     *****************************************************************************/
    
    public boolean update(float deltaTime) {
        if (state == STATE_NONE)
            return true;
        
        if(targetDelay <= deltaTime) {
            // we reached target delay, set to full weight
            weight = targetWeight;
            targetDelay = 0.0f;
            
            // check if we reached the cycles end
            if(weight == 0.0f) {
                return false;
            }
        } else {
            // not reached target delay yet, interpolate between current and target weight
            float factor;
            factor = deltaTime / targetDelay;
            weight = (1.0f - factor) * weight + factor * targetWeight ;
            targetDelay -= deltaTime;
        }
        
        // update animation cycle time if it is in async state
        if(state == STATE_ASYNC) {
            time += deltaTime * timeFactor;
            
            if(time >= coreAnimation.getDuration()) {
                time = time % coreAnimation.getDuration();
            }
            
            if (time < 0)
                time += coreAnimation.getDuration();
        }
        
        return true;
    }
}

