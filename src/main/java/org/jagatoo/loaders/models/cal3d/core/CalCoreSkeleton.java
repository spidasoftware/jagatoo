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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/** The core skeleton class.
 **/
public class CalCoreSkeleton {
    protected String name;
    protected Vector<CalCoreBone> vectorCoreBone = new Vector<CalCoreBone>();
    protected Map<String, CalCoreBone> coreBonesByName = new HashMap<String, CalCoreBone>();
    protected List<Integer> listRootCoreBoneId = new ArrayList<Integer>();
    
    /*****************************************************************************/
    /** Constructs the core skeleton instance.
     *
     * This function is the default constructor of the core skeleton instance.
     *****************************************************************************/
    
    public  CalCoreSkeleton() {
    }
    
    
    
    /*****************************************************************************/
    /** Adds a core bone.
     *
     * This function adds a core bone to the core skeleton instance.
     * In doing so it sets the core bone's skeleton to this core skeleton instance (in contrast to the original Cal3D).
     *
     * @param coreBone A pointer to the core bone that should be added.
     *
     * @return One of the following values:
     *         \li the assigned bone \b ID of the added core bone
     *         \li \b -1 if an error happend
     *****************************************************************************/
    
    public int addCoreBone(CalCoreBone coreBone) {
        coreBone.setCoreSkeleton(this);
        
        // get next bone id
        int boneId = vectorCoreBone.size();
        
        coreBone.id = boneId;
        vectorCoreBone.add(coreBone);
        coreBonesByName.put(coreBone.getName(), coreBone);
        
        // if necessary, add the core bone to the root bone list
        if(coreBone.getParentId() == -1) {
            listRootCoreBoneId.add(new Integer(boneId));
        }
        
        return boneId;
    }
    
    
    /*****************************************************************************/
    /** Calculates the current state.
     *
     * This function calculates the current state of the core skeleton instance by
     * calculating all the core bone states.
     *****************************************************************************/
    
    public void calculateState() {
        // calculate all bone states of the skeleton
        for(int rootCoreBoneId : listRootCoreBoneId) {
            vectorCoreBone.get(rootCoreBoneId).calculateState();
        }
    }
    
    /** Calculates the bone spaces by inversion from the absolute transforms.
     *  Useful for importing from model formats that don't include the bone spaces.
     */
    public void calculateBoneSpaces() {
        for (CalCoreBone coreBone : vectorCoreBone) {
            Quaternion4f absRot = coreBone.getRotationAbsolute();
            Vector3f absDisp = coreBone.getTranslationAbsolute();
            Vector3f boneDisp = new Vector3f(absDisp);
            boneDisp.negate();
            Quaternion4f invRot = new Quaternion4f(absRot); invRot.invert();
            CalCoreBone.transform(boneDisp, invRot);
            
            coreBone.setRotationBoneSpace(invRot);
            coreBone.setTranslationBoneSpace(boneDisp);
        }
    }
    
    /*****************************************************************************/
    /** Provides access to a core bone.
     *
     * This function returns the core bone with the given ID.
     *
     * @param coreBoneId The ID of the core bone that should be returned.
     *
     * @return One of the following values:
     *         \li a pointer to the core bone
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalCoreBone getCoreBone(int coreBoneId) {
        return vectorCoreBone.elementAt(coreBoneId);
    }
    
    
    /*****************************************************************************/
    /** @return the ID of a specified core bone.
     * 
     * This function returns the ID of a specified core bone.
     *
     * @param strName The name of the core bone that should be returned.
     *
     * @return One of the following values:
     *         \li the \b ID of the core bone
     *         \li \b -1 if an error happend
     *****************************************************************************/
    
    public int getCoreBoneId(String strName) {
        for(int boneId = 0; boneId < vectorCoreBone.size(); boneId++) {
            if(vectorCoreBone.get(boneId).getName().equals(strName))
                return boneId;
        }
        
        return -1;
    }
    
    /** Gets the core bone specified by name.
     */
    public CalCoreBone getCoreBone(String name) {
        return coreBonesByName.get(name);
    }
    
    /*****************************************************************************/
    /** @return the root core bone id list.
     *
     * This function returns the list that contains all root core bone IDs of the
     * core skeleton instance.
     *
     * @return A reference to the root core bone id list.
     *****************************************************************************/
    
    public List<Integer> getListRootCoreBoneId() {
        return listRootCoreBoneId;
    }
    
    
    /*****************************************************************************/
    /** @return the core bone vector.
     *
     * This function returns the vector that contains all core bones of the core
     * skeleton instance.
     *
     * @return A reference to the core bone vector.
     *****************************************************************************/
    
    public Vector<CalCoreBone> getCoreBones() {
        return vectorCoreBone;
    }
    
    /** Reconnects the children of each bone based on the parent ids. Useful for loaders and importers.
     */
    public void reconnectChildren() {
        for (CalCoreBone child : vectorCoreBone) {
            if (child.parentId != -1) {
                CalCoreBone parent = getCoreBone(child.parentId);
                parent.addChildId(child.id);
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String toStringAsTree() {
        StringBuffer buffer = new StringBuffer();
        
        for (int root : listRootCoreBoneId)
            toStringAsTree(buffer, 0, vectorCoreBone.get(root));
        
        return buffer.toString();
    }
    
    void toStringAsTree(StringBuffer buffer, int depth, CalCoreBone bone) {
        for (int n = 0; n < depth; n++) buffer.append("  ");
        buffer.append(bone.getName());
        buffer.append("\n");
        
        for (int child : bone.getListChildId())
            toStringAsTree(buffer, depth+1, vectorCoreBone.get(child));
    }
}

