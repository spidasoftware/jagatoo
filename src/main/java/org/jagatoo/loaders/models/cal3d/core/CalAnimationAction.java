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
/** The animation action class.
 *****************************************************************************/
public class CalAnimationAction extends CalAnimation {
    protected float delayIn;
    protected float delayOut;
    protected float delayTarget;
    protected float weightTarget;
    protected boolean  autoLock;
    
    
    /*****************************************************************************/
    /** Constructs an animation action instance based on a core
     * animation.
     *
     * This function is the default constructor of the animation action instance.
     *****************************************************************************/
    
    public  CalAnimationAction(CalCoreAnimation coreAnimation) {
        type = TYPE_ACTION;
        
        if(coreAnimation == null)
            throw new IllegalArgumentException();
        
        this.coreAnimation = coreAnimation;
    }
    
    
    /*****************************************************************************/
    /** Executes the animation action instance.
     *
     * This function executes the animation action instance.
     *
     * @param delayIn The time in seconds until the animation action instance
     *                reaches the full weight from the beginning of its execution.
     * @param delayOut The time in seconds in which the animation action instance
     *                 reaches zero weight at the end of its execution.
     *****************************************************************************/
    
    public void execute(float delayIn, float delayOut) { execute(delayIn, delayOut, 1, false); }
    
    /*****************************************************************************/
    /** Executes the animation action instance.
     *
     * This function executes the animation action instance.
     *
     * @param delayIn The time in seconds until the animation action instance
     *                reaches the full weight from the beginning of its execution.
     * @param delayOut The time in seconds in which the animation action instance
     *                 reaches zero weight at the end of its execution.
     * @param weightTarget No comment for this. FIXME.
     * @param autoLock     This prevents the Action from being reset and removed
     *                     on the last keyframe if true.
     *****************************************************************************/
    
    public void execute(float delayIn, float delayOut, float weightTarget, boolean autoLock) {
        state = STATE_IN;
        weight = 0.0f;
        time = 0.0f;
        this.delayIn = delayIn;
        this.delayOut = delayOut;
        this.weightTarget = weightTarget;
        this.autoLock = autoLock;
    }
    
    
    /*****************************************************************************/
    /** Updates the animation action instance.
     *
     * This function updates the animation action instance for a given amount of
     * time.
     *
     * @param deltaTime The elapsed time in seconds since the last update.
     * @return
     * <ul>
     *         <li> true if the animation action instance is still active
     *         <li> false if the execution of the animation action instance has
     *             ended
     * </ul>
     *****************************************************************************/
    
    public boolean update(float deltaTime) {
        // update animation action time
        if (state != STATE_STOPPED)
            time += deltaTime * timeFactor;
        
        // handle IN phase
        if(state == STATE_IN) {
            // cehck if we are still in the IN phase
            if(time < delayIn) {
                weight = time / delayIn * weightTarget;
            } else {
                state = STATE_STEADY;
                weight = weightTarget;
            }
        }
        
        // handle STEADY
        if (state == STATE_STEADY) {
            // cehck if we reached OUT phase
            if (!autoLock && time >= coreAnimation.getDuration() - delayOut) {
                state = STATE_OUT;
            } else if (autoLock && time >= coreAnimation.getDuration()) {
                state = STATE_STOPPED;
                time = coreAnimation.getDuration();
            }
        }
        
        // handle OUT phase
        if (state == STATE_OUT) {
            // cehck if we are still in the OUT phase
            if(time < coreAnimation.getDuration()) {
                weight = (coreAnimation.getDuration() - time) / delayOut * weightTarget;
            } else {
                // we reached the end of the action animation
                weight = 0.0f;
                return false;
            }
        }
        
        return true;
    }
}

