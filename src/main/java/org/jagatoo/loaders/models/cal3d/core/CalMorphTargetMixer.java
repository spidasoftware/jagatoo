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

public class CalMorphTargetMixer {
    @SuppressWarnings("unused")
	private CalModel model;
    private float [] vectorCurrentWeight;
    private float [] vectorEndWeight;
    private float [] vectorDuration;
    
    public  CalMorphTargetMixer(CalModel model) {
        int morphAnimationCount = model.getCoreModel().getCoreMorphAnimationCount();
        
        if (morphAnimationCount > 0) {
            vectorCurrentWeight = new float [morphAnimationCount];
            vectorEndWeight = new float [morphAnimationCount];
            vectorDuration = new float [morphAnimationCount];
        }
    }
    
    /*****************************************************************************/
    /** Interpolates the weight of a morph target.
     *
     * This function interpolates the weight of a morph target a new value
     * in a given amount of time.
     *
     * @param id The ID of the morph target that should be blended.
     * @param weight The weight to interpolate the morph target to.
     * @param delay The time in seconds until the new weight should be reached.
     *****************************************************************************/
    public void blend(int id, float weight, float delay) {
        vectorEndWeight[id] = weight;
        vectorDuration[id] = delay;
    }
    
    /*****************************************************************************/
    /** Fades a morph target out.
     *
     * This function fades a morph target out in a given amount of time.
     *
     * @param id The ID of the morph target that should be faded out.
     * @param delay The time in seconds until the the morph target is
     *              completely removed.
     *****************************************************************************/
    
    public void clear(int id, float delay) {
        vectorEndWeight[id] = 0.0f;
        vectorDuration[id] = delay;
    }
    
    /*****************************************************************************/
    /** Get the weight of a morph target.
     *
     * @param id The id of the morph target which weight you want.
     *
     * @return The weight of the morph target with the given id.
     *****************************************************************************/
    public float getCurrentWeight(int id) {
        return vectorCurrentWeight [id];
    }
    
    /*****************************************************************************/
    /** Get the weight of the base vertices.
     *
     * @return The weight of the base vertices.
     *****************************************************************************/
    public float getCurrentWeightBase() {
        float currentWeight = 1.0f;
        for (float weight : vectorCurrentWeight)
            currentWeight -= weight;
        return currentWeight;
    }
    
    /*****************************************************************************/
    /** Returns the number of morph targets this morph target mixer mixes.
     * 
     * @return The number of morph targets this morph target mixer mixes.
     *****************************************************************************/
    public int getMorphTargetCount() {
        return vectorCurrentWeight.length;
    }
    
    /*****************************************************************************/
    /** Updates all morph targets.
     *
     * This function updates all morph targets of the mixer instance for a
     * given amount of time.
     *
     * @param deltaTime The elapsed time in seconds since the last update.
     *****************************************************************************/
    public void update(float deltaTime) {
        for (int n = 0; n < vectorCurrentWeight.length; n++) {
            if(deltaTime >= vectorDuration [n]) {
                vectorCurrentWeight [n] = vectorEndWeight [n];
                vectorDuration [n] = 0.0f;
            } else {
                vectorCurrentWeight [n] += (vectorEndWeight [n] - vectorCurrentWeight [n]) * deltaTime / vectorDuration [n];
                vectorDuration [n] -= deltaTime;
            }
        }
        
//        for (CalCoreMorphAnimation coreMorphAnimation : model.getCoreModel ().getCoreMorphAnimations ();)
//        int morphAnimationID = 0;
//        while(morphAnimationID<getMorphTargetCount ())
//        {
//            CalCoreMorphAnimation* pCoreMorphAnimation =
//                    m_pModel->getCoreModel ()->getCoreMorphAnimation (morphAnimationID);
//            std::vector<int>& vectorCoreMeshID = pCoreMorphAnimation->getVectorCoreMeshID ();
//            std::vector<int>& vectorMorphTargetID = pCoreMorphAnimation->getVectorMorphTargetID ();
//            size_t meshIterator = 0;
//            while(meshIterator<vectorCoreMeshID.size ())
//            {
//                std::vector<CalSubmesh *> &vectorSubmesh =
//                        m_pModel->getMesh (vectorCoreMeshID[meshIterator])->getVectorSubmesh ();
//                int submeshCount = vectorSubmesh.size ();
//                int submeshId;
//                for(submeshId=0;submeshId<submeshCount;++submeshId)
//                {
//                    vectorSubmesh[submeshId]->setMorphTargetWeight
//                            (vectorMorphTargetID[meshIterator],m_vectorCurrentWeight[morphAnimationID]);
//                }
//                ++meshIterator;
//            }
//            ++morphAnimationID;
//        }
    }
}

