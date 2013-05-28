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

import org.openmali.vecmath2.Vector3f;

import java.net.URL;
import java.util.Iterator;
import java.util.Vector;


/*****************************************************************************/
/** The core mesh class.
 *****************************************************************************/
public class CalCoreMesh{
    protected String name;
    public String skin;
    public URL material;
    protected Vector<CalCoreSubmesh> vectorCoreSubmesh = new Vector<CalCoreSubmesh>();
    
    /*****************************************************************************/
    /** Constructs the core mesh instance.
     *
     * This function is the default constructor of the core mesh instance.
     *****************************************************************************/
    
    public  CalCoreMesh() {
    }
    
    
    /*****************************************************************************/
    /** Adds a core submesh.
     *
     * This function adds a core submesh to the core mesh instance.
     *
     * @param coreSubmesh A pointer to the core submesh that should be added.
     *
     * @return the assigned submesh ID of the added core submesh
     *****************************************************************************/
    
    public int addCoreSubmesh(CalCoreSubmesh coreSubmesh) {
        // get next bone id
        int submeshId = vectorCoreSubmesh.size();
        
        vectorCoreSubmesh.add(coreSubmesh);
        
        return submeshId;
    }
    
    /*****************************************************************************/
    /** Adds a core submesh as a morph target.
     *
     * This function adds a core mesh as a blend target.
     * It adds appropriate CalCoreSubMorphTargets to each of the core sub meshes.
     *
     * @param coreMesh A pointer to the core mesh that shoulb become a blend target.
     *
     * @return the assigned morph target ID of the added blend target
     *****************************************************************************/
    
    public int addAsMorphTarget(CalCoreMesh coreMesh) {
        //Check if the numbers of vertices allow a blending
        Vector<CalCoreSubmesh> otherVectorCoreSubmesh = coreMesh.getVectorCoreSubmesh();
        
        if (vectorCoreSubmesh.size() != otherVectorCoreSubmesh.size())
            throw new IllegalArgumentException("Morph target must have same number of submeshes");
        
        if (vectorCoreSubmesh.size() == 0)
            throw new IllegalArgumentException("Morph target must have at least one submesh");
        
        Iterator<CalCoreSubmesh> iteratorCoreSubmesh = vectorCoreSubmesh.iterator();
        Iterator<CalCoreSubmesh> otherIteratorCoreSubmesh = otherVectorCoreSubmesh.iterator();
        int subMorphTargetID = vectorCoreSubmesh.firstElement().getCoreSubMorphTargetCount();
        while(iteratorCoreSubmesh.hasNext()) {
            if(iteratorCoreSubmesh.next().getVertexCount() != otherIteratorCoreSubmesh.next().getVertexCount())
                throw new IllegalArgumentException("Submesh in morph target must have same number of vertices");
        }
        
        //Adding the blend targets to each of the core sub meshes
        iteratorCoreSubmesh = vectorCoreSubmesh.iterator();
        otherIteratorCoreSubmesh = otherVectorCoreSubmesh.iterator();
        while (iteratorCoreSubmesh.hasNext()) {
            CalCoreSubmesh thisSubmesh = iteratorCoreSubmesh.next();
            CalCoreSubmesh otherSubmesh = otherIteratorCoreSubmesh.next();
            int vertexCount = otherSubmesh.getVertexCount();
            
            CalCoreSubMorphTarget calCoreSubMorphTarget = new CalCoreSubMorphTarget(vertexCount);
            
            Vector3f blendVertexPosition = new Vector3f();
            Vector3f blendVertexNormal = new Vector3f();
            for(int i = 0; i<vertexCount; i++) {
                otherSubmesh.getVertexPosition(i, blendVertexPosition);
                otherSubmesh.getVertexNormal(i, blendVertexNormal);
                
                calCoreSubMorphTarget.setBlendVertex(i, blendVertexPosition, blendVertexNormal);
            }
            thisSubmesh.addCoreSubMorphTarget(calCoreSubMorphTarget);
        }
        
        return subMorphTargetID;
    }
    
    /*****************************************************************************/
    /** Provides access to a core submesh.
     *
     * This function returns the core submesh with the given ID.
     *
     * @param id The ID of the core submesh that should be returned.
     *
     * @return One of the following values:
     *         \li a pointer to the core submesh
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalCoreSubmesh getCoreSubmesh(int id) {
        return vectorCoreSubmesh.elementAt(id);
    }
    
    
    /*****************************************************************************/
    /** Returns the number of core submeshes.
     * 
     * This function returns the number of core submeshes in the core mesh
     * instance.
     *
     * @return The number of core submeshes.
     *****************************************************************************/
    
    public int getCoreSubmeshCount() {
        return vectorCoreSubmesh.size();
    }
    
    
    /*****************************************************************************/
    /** Returns the core submesh vector.
     *
     * This function returns the vector that contains all core submeshes of the
     * core mesh instance.
     *
     * @return A reference to the core submesh vector.
     *****************************************************************************/
    
    public Vector<CalCoreSubmesh> getVectorCoreSubmesh() {
        return vectorCoreSubmesh;
    }
    
    
    
    /** The mesh's name is mostly used as a resouce handle.
     */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
