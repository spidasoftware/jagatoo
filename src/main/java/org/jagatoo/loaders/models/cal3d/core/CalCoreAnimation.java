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
import java.util.Iterator;
import java.util.List;


/*****************************************************************************/
/** The core animation class.
 *****************************************************************************/
public class CalCoreAnimation{
    protected String name, resourceName;
    protected float duration;
    protected List<CalCoreTrack> listCoreTrack = new ArrayList<CalCoreTrack>();
    
    /*****************************************************************************/
    /** Constructs the core animation instance.
     *
     * This function is the default constructor of the core animation instance.
     *****************************************************************************/
    
    public CalCoreAnimation() {
    }
    
    /*****************************************************************************/
    /** Adds a core track.
     *
     * This function adds a core track to the core animation instance.
     *
     * @param coreTrack A pointer to the core track that should be added.
     *****************************************************************************/
    
    public void addCoreTrack(CalCoreTrack coreTrack) {
        listCoreTrack.add(coreTrack);
    }
    
    /*****************************************************************************/
    /** Provides access to a core track.
     *
     * This function returns the core track for a given bone ID.
     *
     * @param coreBoneId The core bone ID of the core track that should be
     *                   returned.
     *
     * @return the core track or null if not found
     *****************************************************************************/
    
    public CalCoreTrack getCoreTrack(int coreBoneId) {
        // loop through all core bones
        for (CalCoreTrack coreTrack : listCoreTrack) {
            // check if we found the matching core bone
            if(coreTrack.getCoreBoneId() == coreBoneId)
                return coreTrack;
        }
        
        // no match found
        return null;
    }
    
    /** Removes a core track for a given bone ID - useful for pruning unwanted bones
     */
    public void removeCoreTrack(int coreBoneId) {
        // loop through all core bones
        for (Iterator<CalCoreTrack> iteratorCoreTrack = listCoreTrack.iterator(); iteratorCoreTrack.hasNext(); ) {
            // get the core bone
            CalCoreTrack coreTrack = iteratorCoreTrack.next();
            
            // check if we found the matching core bone
            if(coreTrack.getCoreBoneId() == coreBoneId) {
                iteratorCoreTrack.remove(); return;
            }
        }
    }
    
    /*****************************************************************************/
    /** Scale the core animation.
     *
     * This function rescale all the skeleton data that are in the core animation instance
     *
     * @param factor A float with the scale factor
     *
     *****************************************************************************/
    
    public void scale(float factor) {
        // loop through all core track
        for(CalCoreTrack coreTrack : listCoreTrack) {
            coreTrack.scale(factor);
        }
    }
    
    /*****************************************************************************/
    /** Returns the duration.
     * 
     * This function returns the duration of the core animation instance.
     *
     * @return The duration in seconds.
     *****************************************************************************/
    
    public float getDuration() {
        return duration;
    }
    
    
    /*****************************************************************************/
    /** Returns the core track list.
     *
     * This function returns the list that contains all core tracks of the core
     * animation instance.
     *
     * @return A reference to the core track list.
     *****************************************************************************/
    
    public List<CalCoreTrack> getListCoreTrack() {
        return listCoreTrack;
    }
    
    
    /*****************************************************************************/
    /** Sets the duration.
     *
     * This function sets the duration of the core animation instance.
     *
     * @param duration The duration in seconds that should be set.
     *****************************************************************************/
    
    public void setDuration(float duration) {
        this.duration = duration;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getResourceName() {
        return resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}

