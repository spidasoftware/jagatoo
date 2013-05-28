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


/*****************************************************************************/
/** The core keyframe class.
 *****************************************************************************/
public class CalCoreKeyframe {
    protected float time;
    protected Vector3f translation = new Vector3f();
    protected Quaternion4f rotation = new Quaternion4f();
    
    
    /*****************************************************************************/
    /** Constructs the core keyframe instance.
     *
     * This function is the default constructor of the core keyframe instance.
     *****************************************************************************/
    
    public  CalCoreKeyframe() {
        time = 0.0f;
    }
    
    /*****************************************************************************/
    /** Returns the rotation.
     * 
     * This function returns the rotation of the core keyframe instance.
     *
     * @return The rotation as quaternion.
     *****************************************************************************/
    
    public Quaternion4f getRotation() {
        return rotation;
    }
    
    
    /*****************************************************************************/
    /** Returns the time.
     *
     * This function returns the time of the core keyframe instance.
     *
     * @return The time in seconds.
     *****************************************************************************/
    
    public float getTime() {
        return time;
    }
    
    
    /*****************************************************************************/
    /** Returns the translation.
     *
     * This function returns the translation of the core keyframe instance.
     *
     * @return The translation as vector.
     *****************************************************************************/
    
    public Vector3f getTranslation() {
        return translation;
    }
    
    
    /*****************************************************************************/
    /** Sets the rotation.
     *
     * This function sets the rotation of the core keyframe instance.
     *
     * @param rotation The rotation as quaternion.
     *****************************************************************************/
    
    public void setRotation(Quaternion4f rotation) {
        this.rotation = rotation;
    }
    
    
    /*****************************************************************************/
    /** Sets the time.
     *
     * This function sets the time of the core keyframe instance.
     *
     * @param time The time in seconds.
     *****************************************************************************/
    
    public void setTime(float time) {
        this.time = time;
    }
    
    
    /*****************************************************************************/
    /** Sets the translation.
     *
     * This function sets the translation of the core keyframe instance.
     *
     * @param translation The translation as vector.
     *****************************************************************************/
    
    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }
}

