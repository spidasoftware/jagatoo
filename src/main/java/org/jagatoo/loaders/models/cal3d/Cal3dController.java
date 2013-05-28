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
package org.jagatoo.loaders.models.cal3d;

import java.util.Iterator;

import org.jagatoo.loaders.models.cal3d.core.CalAnimationCycle;
import org.jagatoo.loaders.models.cal3d.core.CalModel;

/**
 * @author Dave Lloyd
 * @author kman
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public class Cal3dController
{
    private final CalModel calModel;
    
    @SuppressWarnings("unused")
    private String    activeAnimation;
    
    @SuppressWarnings("unused")
    private float     activeDuration = 0f; ;
    
    @SuppressWarnings("unused")
    private float     lastTime       = 0f;
    
    /** Creates a new instance of Xith3dCal3dController */
    public Cal3dController(CalModel calModel)
    {
        this.calModel = calModel;
    }
    
    private CalModel getInternalModel()
    {
        return ( calModel );
    }
    
    public void blendAnim(String anim, float weight, float delay)
    {
        if (animationExists(anim))
        {
            getInternalModel().getMixer().blendCycle(anim, weight, delay);
        }
    }
    
    public void clearAnim(String anim)
    {
        getInternalModel().getMixer().clearCycle(anim, 0f);
    }
    
    public void setAnimation(String anim, float delay, float duration)
    {
        if (animationExists(anim))
        {
            getInternalModel().getMixer().executeAction(anim, 0f, 0f);
        }
    }
    
    public void setAnimation(String anim)
    {
        if (animationExists(anim))
        {
            clearAnimations();
            activeAnimation = anim;
            CalAnimationCycle cycle = getInternalModel().getMixer().blendCycle(anim, 1f, 0);
            cycle.setAsync(0, 0);
        }
    }
    
    public void clearAnimations()
    {
        activeAnimation = null;
        Iterator<String> it = getInternalModel().getMixer().mapAnimation.keySet().iterator();
        while (it.hasNext())
        {
            getInternalModel().getMixer().clearCycle(it.next(), 0f);
        }
    }
    
    private boolean animationExists(String anim)
    {
        if (getInternalModel().getCoreModel().getCoreAnimation(anim) != null)
            return true;
        
        return false;
    }
    
}
