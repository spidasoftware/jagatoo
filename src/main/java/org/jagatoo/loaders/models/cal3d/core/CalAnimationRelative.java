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

/** A variant of CalAnimationCycle that allows for additive blending.
 *
 * @author Dave, (c) Short Fuze Ltd, 13 January 2006
 */
public class CalAnimationRelative extends CalAnimationCycle {
    CalCoreAnimation basePose;
    
    public CalAnimationRelative(CalCoreAnimation coreAnimation) {
        super(coreAnimation);
    }
    
    public CalAnimationRelative(CalCoreAnimation coreAnimation, CalCoreAnimation basePose) {
        super(coreAnimation);
        this.basePose = basePose;
    }
    
    public void getBaseState(CalCoreTrack coreTrack, Vector3f translation0, Quaternion4f rotation0) {
        if (basePose != null) {
            basePose.getCoreTrack(coreTrack.getCoreBoneId()).getState(0, translation0, rotation0);
        } else {
            // get the initial translation and rotation from the first frame of the anim we're running
            coreTrack.getState(0, translation0, rotation0);
        }
    }
}
