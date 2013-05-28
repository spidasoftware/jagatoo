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

import java.util.HashMap;

import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;

/**
 * A COLLADA model. This interface should be implemented by
 * the game engine, that wants to use JAGaToo for model loading.
 * 
 * It contains mostly methods used for controlling animations.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public interface AnimatableModel
{
    /**
     * Get the prototype model (Jagatoo side) used by this model
     * (Game engine side).
     * @return the prototype model
     */
    public ColladaProtoypeModel getPrototypeModel();
    
    /**
     * @return true if this model has skeletal animations (called "actions"),
     * or false if it's a static model.
     */
    public boolean hasActions();
    
    /**
     * @return the number of actions this model has, or 0 if it isn't animated
     * at all (static model)
     */
    public int numActions();
    
    /**
     * @return the map of all the actions thie model has, or null if this model
     * isn't animated.
     */
    public HashMap<String, COLLADAAction> getActions();
    
    /**
     * Get a specific action
     * @param id The ID string of this action
     * @return The action which has the specified ID string.
     */
    public COLLADAAction getAction(String id);
    
    /**
     * Begin to play a particular action, whatever the model
     * was doing (we don't mind if it was paused or what..)
     * @param action The action to play
     * @param loop true if we should loop the animation
     */
    public void play(COLLADAAction action, boolean loop);
    
    /**
     * Begin to play a particular action, whatever the model
     * was doing (we don't mind if it was paused or what..)
     * @param actionId The ID String of the action to play
     * @param loop true if we should loop the animation
     */
    public void play(String actionId, boolean loop);
    
    /**
     * Pause the model (stop to play an animation). The model
     * will remind which animation it was playing, and where it
     * was in this animation, so that you can resume() whenever
     * you want.
     */
    public void pause();
    
    /**
     * Pause the model (stop to play an animation). The model
     * will remind which animation it was playing, and where it
     * was in this animation, so that you can resume() whenever
     * you want.
     */
    public void resume();
    
    /**
     * @return true if the model is playing an animation, or
     * false if the model isn't playing any animation, ex :
     * - The model has no animation
     * - No animation has been played since the model loading
     * - The last played animation has been ended and it wasn't looped
     * - The model has been paused manually (pause() method).
     */
    public boolean isPlaying();
    
    /**
     * @return true if the model is looping. It's the case if
     * an animation has been played with the boolean argument
     * "loop" set to "true"
     */
    public boolean isLooping();
}
