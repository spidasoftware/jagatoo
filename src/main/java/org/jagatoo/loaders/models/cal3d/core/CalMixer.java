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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;


/*****************************************************************************/
/** The mixer handles two tasks: scheduling and
 * blending. Scheduling refers to everything related to time such
 * as when an animation must run or when it must stop. Blending
 * defines how concurrent animations influence each other: for
 * instance walking and waving.
 *****************************************************************************/
public class CalMixer {
    public CalModel model;
    public Map<String, CalAnimation> mapAnimation = new HashMap<String, CalAnimation>();
    public List<CalAnimationAction> listAnimationAction = new ArrayList<CalAnimationAction>();
    public List<CalAnimationCycle>  listAnimationCycle  = new ArrayList<CalAnimationCycle>();
    public List<CalAnimationRelative> additiveAnimationCycles = new ArrayList<CalAnimationRelative>();
    protected float animationTime = 0;
    protected float animationDuration = 0;
    public float timeFactor = 1;
    
    /*****************************************************************************/
    /** Constructs the mixer instance.
     *
     * This function is the default constructor of the mixer instance.
     * @param model the model that should be managed with this mixer instance.
     *****************************************************************************/
    
    public  CalMixer(CalModel model) {
        this.model = model;
    }
    
    
    /*****************************************************************************/
    /** Blends an animation cycle in.
     *
     * This function interpolates the weight of an animation cycle to a new value
     * in a given amount of time. If the specified animation cycle is not active
     * yet, it is activated.
     *
     * @param id The ID of the animation cycle that should be blended.
     * @param weight The weight to interpolate the animation cycle to.
     * @param delay The time in seconds until the new weight should be reached.
     *****************************************************************************/
    
    public synchronized CalAnimationCycle blendCycle(String id, float weight, float delay) {
        // get the animation for the given id
        CalAnimation animation = mapAnimation.get(id);
        
        // create a new animation instance if it is not active yet
        if(animation == null) {
            // take the fast way out if we are trying to clear an inactive animation
            if(weight == 0.0f) return null;
            
            // get the core animation
            CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
            
            if (coreAnimation == null)
                throw new IllegalArgumentException("Animation does not exist: "+id);
            
            // allocate a new animation cycle instance
            CalAnimationCycle animationCycle = new CalAnimationCycle(coreAnimation);
            
            // insert new animation into the tables
            mapAnimation.put(id, animationCycle);
            listAnimationCycle.add(0, animationCycle);
            
            // blend the animation
            animationCycle.blend(weight, delay);
            
            return animationCycle;
        }
        
        // check if this is really a animation cycle instance
        if(animation.getType() != CalAnimation.TYPE_CYCLE)
            throw new IllegalArgumentException("Animation \""+id+"\" is not a cycle");
        
        // clear the animation cycle from the active map if the target weight is zero
        if(weight == 0.0f) {
            mapAnimation.remove(id);
        }
        
        // cast it to an animation cycle
        CalAnimationCycle animationCycle = (CalAnimationCycle) animation;
        
        // blend the animation cycle
        animationCycle.blend(weight, delay);
        
        return animationCycle;
    }
    
    /** Scrubs an animation cycle to a position (fraction through animation).
     *  This is achieved internally using an animation cycle.
     */
    public final CalAnimation scrubToPosition(String id, float position) {
        return scrubToPosition(id, position, 1);
    }
    
    /** Scrubs an animation cycle to a position (fraction through animation).
     *  This is achieved internally using an animation cycle.
     */
    public synchronized CalAnimation scrubToPosition(String id, float position, float weight) {
        CalAnimation animation = mapAnimation.get(id);
        
        // create a new animation instance if it is not active yet
        if(animation == null) {
            // get the core animation
            CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
            
            if (coreAnimation == null)
                throw new IllegalArgumentException("Animation does not exist: "+id);
            
            // allocate a new animation cycle instance
            CalAnimationCycle animationCycle = new CalAnimationCycle(coreAnimation);
            
            // insert new animation into the tables
            mapAnimation.put(id, animationCycle);
            listAnimationCycle.add(0, animationCycle);
            
            animation = animationCycle;
        }
        
        CalCoreAnimation coreAnim = animation.getCoreAnimation();
        
        float time = position * coreAnim.getDuration();
        
        animation.freeze(time, weight);
        
        return animation;
    }
    
    /** Scrubs an animation cycle to a specific time.
     *  This is achieved internally using an animation cycle.
     */
    public final CalAnimation scrubToTime(String id, float time) {
        return scrubToTime(id, time, 1);
    }
    
    /** Scrubs an animation cycle to a specific time.
     *  This is achieved internally using an animation cycle.
     */
    public synchronized CalAnimation scrubToTime(String id, float time, float weight) {
        CalAnimation animation = mapAnimation.get(id);
        
        // create a new animation instance if it is not active yet
        if(animation == null) {
            // get the core animation
            CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
            
            if (coreAnimation == null)
                throw new IllegalArgumentException("Animation does not exist: "+id);
            
            // allocate a new animation cycle instance
            CalAnimationCycle animationCycle = new CalAnimationCycle(coreAnimation);
            
            // insert new animation into the tables
            mapAnimation.put(id, animationCycle);
            listAnimationCycle.add(0, animationCycle);
            
            animation = animationCycle;
        }
        
        CalCoreAnimation coreAnim = animation.getCoreAnimation();
        
        time = time % coreAnim.getDuration();
        if (time < 0) time += coreAnim.getDuration();
        
        animation.freeze(time, weight);
        
        return animation;
    }
    
    /** Applies an additive animation cycle to the skeleton.
     *  Behaves like a scrub so it doesn't need to interact with the CalMixer's update...
     *  This is experimental!
     */
    public synchronized CalAnimation applyAdditiveCycle(String id, float time) {
        CalAnimation animation = mapAnimation.get(id);
        
        // create a new animation instance if it is not active yet
        if(animation == null) {
            // get the core animation
            CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
            
            if (coreAnimation == null)
                throw new IllegalArgumentException("Animation does not exist: "+id);
            
            // allocate a new additive animation instance
            CalAnimationRelative animationCycle = new CalAnimationRelative(coreAnimation);
            
            // insert new animation into the tables
            mapAnimation.put(id, animationCycle);
            additiveAnimationCycles.add(0, animationCycle);
            
            animation = animationCycle;
        }
        
        CalCoreAnimation coreAnim = animation.getCoreAnimation();
        
        time = time % coreAnim.getDuration();
        
        animation.freeze(time, 1);
        
        return animation;
    }
    
    /** Applies an additive animation cycle to the skeleton.
     *  Behaves like a scrub so it doesn't need to interact with the CalMixer's update...
     *  This is experimental!
     */
    public synchronized CalAnimation applyAdditiveCycle(String id, String baseId, float time) {
        CalAnimation animation = mapAnimation.get(id);
        
        // create a new animation instance if it is not active yet
        if(animation == null) {
            // get the core animation
            CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
            CalCoreAnimation basePose      = model.getCoreModel().getCoreAnimation(baseId);
            
            if (coreAnimation == null)
                throw new IllegalArgumentException("Animation does not exist: "+id);
            if (basePose == null)
                throw new IllegalArgumentException("Animation does not exist: "+baseId);
            
            // allocate a new additive animation instance
            CalAnimationRelative animationCycle = new CalAnimationRelative(coreAnimation, basePose);
            
            // insert new animation into the tables
            mapAnimation.put(id, animationCycle);
            additiveAnimationCycles.add(0, animationCycle);
            
            animation = animationCycle;
        }
        
        CalCoreAnimation coreAnim = animation.getCoreAnimation();
        
        time = time % coreAnim.getDuration();
        
        animation.freeze(time, 1);
        
        return animation;
    }
    
    /*****************************************************************************/
    /** Fades an animation cycle out.
     *
     * This function fades an animation cycle out in a given amount of time.
     *
     * @param id The ID of the animation cycle that should be faded out.
     * @param delay The time in seconds until the the animation cycle is
     *              completely removed.
     *****************************************************************************/
    
    public synchronized void clearCycle(String id, float delay) {
        // get the animation for the given id
        CalAnimation animation = mapAnimation.get(id);
        
        // we can only clear cycles that are active
        if(animation == null) return;
        
        // check if this is really a animation cycle instance
        if(animation.getType() != CalAnimation.TYPE_CYCLE)
            throw new IllegalArgumentException("Animation \""+id+"\" is not a cycle");
        
        // clear the animation cycle from the active map
        mapAnimation.remove(id);
        
        // cast it to an animation cycle
        CalAnimationCycle animationCycle = (CalAnimationCycle) animation;
        
        // set animation cycle to async state
        animationCycle.setAsync(animationTime, animationDuration);
        
        // blend the animation cycle
        animationCycle.blend(0.0f, delay);
    }
    
    public synchronized void clearAdditiveCycle(String id) {
        // get the animation for the given id
        CalAnimation animation = mapAnimation.get(id);
        
        // we can only clear cycles that are active
        if(animation == null) return;
        
        // clear the animation cycle from the active map
        mapAnimation.remove(id);
        
        additiveAnimationCycles.remove(animation);
    }
    
    /** An abrupt way to clear all animations - actions or cycles.
     */
    public synchronized void clearAllAnims() {
        mapAnimation.clear();
        listAnimationAction.clear();
        listAnimationCycle.clear();
        additiveAnimationCycles.clear();
    }
    
    /*****************************************************************************/
    /** Executes an animation action.
     *
     * This function executes an animation action.
     *
     * @param id The ID of the animation cycle that should be blended.
     * @param delayIn The time in seconds until the animation action reaches the
     *                full weight from the beginning of its execution.
     * @param delayOut The time in seconds in which the animation action reaches
     *                 zero weight at the end of its execution.
     *****************************************************************************/
    
    public synchronized CalAnimationAction executeAction(String id, float delayIn, float delayOut) {
        return executeAction(id, delayIn, delayOut, 1, false);
    }
    
    /*****************************************************************************/
    /** Executes an animation action.
     *
     * This function executes an animation action.
     *
     * @param id The ID of the animation cycle that should be blended.
     * @param delayIn The time in seconds until the animation action reaches the
     *                full weight from the beginning of its execution.
     * @param delayOut The time in seconds in which the animation action reaches
     *                 zero weight at the end of its execution.
     * @param weightTarget The weight to interpolate the animation action to.
     * @param autoLock     This prevents the Action from being reset and removed
     *****************************************************************************/
    
    public synchronized CalAnimationAction executeAction(String id, float delayIn, float delayOut, float weightTarget, boolean autoLock) {
        // get the core animation
        CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
        
        // allocate a new animation action instance
        CalAnimationAction animationAction = new CalAnimationAction(coreAnimation);
        
        // insert new animation into the table
        listAnimationAction.add(0, animationAction);
        
        // execute the animation
        animationAction.execute(delayIn, delayOut, weightTarget, autoLock);
        
        return animationAction;
    }
    
    /*****************************************************************************/
    /** Clears an active animation action.
     *
     * This function removes an animation action from the blend list.  This is
     * particularly useful with auto-locked actions on their last frame.
     *
     * @param id The ID of the animation action that should be removed.
     *****************************************************************************/
    public void clearAction(String id) {
        // get the core animation
        CalCoreAnimation coreAnimation = model.getCoreModel().getCoreAnimation(id);
        
        // remove any active animation actions of this model
        for (Iterator<CalAnimationAction> i = listAnimationAction.iterator(); i.hasNext(); ) {
            if (i.next().getCoreAnimation() == coreAnimation)
                i.remove();
        }
    }
    
    
    /*****************************************************************************/
    /** Updates all active animations.
     * Notifies the instance that updateAnimation was last called
     * deltaTime seconds ago. The internal scheduler of the instance
     * will terminate animations and update the timing information of
     * active animations accordingly. It does not blend animations
     * together or otherwise modify the CalModel associated to these
     * animations.
     *
     * The CalModel.update() method will call updateSkeleton immediately
     * after updateAnimation
     *
     * @param deltaTime The elapsed time in seconds since the last call.
     *
     *****************************************************************************/
    
    public synchronized void updateAnimation(float deltaTime) {
        // update the current animation time
        if(animationDuration == 0.0f) {
            animationTime = 0.0f;
        } else {
            animationTime += deltaTime * timeFactor;
            if(animationTime >= animationDuration) {
                animationTime = animationTime % animationDuration;
            }
            if (animationTime < 0)
                animationTime += animationDuration;
        }
        
        // update all active animation actions of this model
        for (Iterator<CalAnimationAction> iteratorAnimationAction = listAnimationAction.iterator(); iteratorAnimationAction.hasNext(); ) {
            CalAnimationAction animationAction = iteratorAnimationAction.next();
            
            // update and check if animation action is still active
            if(animationAction.update(deltaTime)) {
            } else {
                // animation action has ended, remove it from the animation list
                iteratorAnimationAction.remove();
            }
        }
        
        // todo: update all active animation poses of this model
        
        // update the weight of all active animation cycles of this model
        
        float accumulatedWeight = 0.0f, accumulatedDuration = 0.0f;
        
        for (Iterator<CalAnimationCycle> iteratorAnimationCycle = listAnimationCycle.iterator(); iteratorAnimationCycle.hasNext(); ) {
            CalAnimationCycle animationCycle = iteratorAnimationCycle.next();
            
            // update and check if animation cycle is still active
            if(animationCycle.update(deltaTime)) {
                // check if it is in sync. if yes, update accumulated weight and duration
                if(animationCycle.getState() == CalAnimation.STATE_SYNC) {
                    accumulatedWeight += animationCycle.getWeight();
                    accumulatedDuration += animationCycle.getWeight() * animationCycle.getCoreAnimation().getDuration();
                }
            } else {
                // animation cycle has ended, remove it from the animation list
                iteratorAnimationCycle.remove();
            }
        }
        
        // adjust the global animation cycle duration
        animationDuration = accumulatedWeight > 0.0f ? accumulatedDuration / accumulatedWeight : 0.0f;
    }
    
    
    /*****************************************************************************/
    /** Updates the skeleton of the corresponding CalModel to match the current animation state (as
     * updated by the last call to updateAnimation).  The tracks of each
     * active animation are blended to compute the position and
     * orientation of each bone of the skeleton. The updateAnimation
     * method should be called just before calling updateSkeleton to
     * define the set of active animations.
     *
     * The CalModel.update() method will call updateSkeleton immediately
     * after updateAnimation
     *****************************************************************************/
    
    public synchronized void updateSkeleton() {
        // get the skeleton we need to update
        CalSkeleton skeleton = model.getSkeleton();
        
        // clear the skeleton state
        skeleton.clearState();
        
        // get the bone vector of the skeleton
        CalBone [ ] vectorBone = skeleton.getBones();
        
        // loop through all animation actions
        Vector3f translation = new Vector3f();
        Quaternion4f rotation = new Quaternion4f();
        for(CalAnimationAction animationAction : listAnimationAction) {
            // get the core animation instance
            CalCoreAnimation coreAnimation = animationAction.getCoreAnimation();
            
            // loop through all core tracks of the core animation
            for (CalCoreTrack coreTrack : coreAnimation.getListCoreTrack()) {
                // get the appropriate bone of the track
                CalBone bone = vectorBone [coreTrack.getCoreBoneId()];
                
                // get the current translation and rotation
                coreTrack.getState(animationAction.getTime(), translation, rotation);
                
                // blend the bone state with the new state
                bone.blendState(animationAction.getWeight(), translation, rotation);
            }
        }
        
        // lock the skeleton state
        skeleton.lockState();
        
        // loop through all animation cycles
        for(CalAnimationCycle animationCycle : listAnimationCycle) {
            // get the core animation instance
            CalCoreAnimation coreAnimation = animationCycle.getCoreAnimation();
            
            // calculate adjusted time
            float animationTime = 0.0f;
            if(animationCycle.getState() == CalAnimation.STATE_SYNC) {
                animationTime = animationDuration > 0.0f ? this.animationTime * coreAnimation.getDuration() / animationDuration : 0.0f;
            } else {
                animationTime = animationCycle.getTime();
            }
            
            // loop through all core tracks of the core animation
            for(CalCoreTrack coreTrack : coreAnimation.getListCoreTrack()) {
                // get the appropriate bone of the track
                CalBone bone = vectorBone [coreTrack.getCoreBoneId()];
                
                // get the current translation and rotation
                coreTrack.getState(animationTime, translation, rotation);
                
                if (coreTrack.getCoreBoneId() == 0) {
                    if (animationCycle.rootDisplacement != null){
                        translation.sub(animationCycle.rootDisplacement);
                    }
                    if (animationCycle.rootRotation != null){
                        rotation.mulInverse(animationCycle.rootRotation);
                    }
                }
                
                // blend the bone state with the new state
                bone.blendState(animationCycle.getWeight(), translation, rotation);
            }
        }
        
        // lock the skeleton state
        skeleton.lockState();
        
        // Now do all the additive blends
        
        for(CalAnimationRelative animation : additiveAnimationCycles) {
            // loop through all core tracks of the core animation
            for(CalCoreTrack coreTrack : animation.getCoreAnimation().getListCoreTrack()) {
                // get the appropriate bone of the track
                CalBone bone = vectorBone [coreTrack.getCoreBoneId()];
                
                Vector3f translation0 = new Vector3f();
                Quaternion4f   rotation0    = new Quaternion4f();
                
                animation.getBaseState(coreTrack, translation0, rotation0);
                
                // get the current translation and rotation
                coreTrack.getState(animation.getTime(), translation, rotation);
                
                // find the deltas - for now (just in case) set translation to (0,0,0)
                translation.set(0, 0, 0);
                rotation.mulInverse(rotation0);
                // blend the bone state with the new state
                bone.applyState(animation.getWeight(), translation, rotation);
            }
        }
        
        // let the skeleton calculate its final state
        skeleton.calculateState();
    }
}

