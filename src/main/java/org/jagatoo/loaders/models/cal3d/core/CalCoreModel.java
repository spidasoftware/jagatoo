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

import org.openmali.vecmath2.Colorf;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;


/** The core model or prototype for animated character models.
 */
public class CalCoreModel {
    protected String name;
    protected CalCoreSkeleton coreSkeleton;
    protected Map<String,CalCoreAnimation>            mapCoreAnimation = new HashMap<String,CalCoreAnimation>();
    protected Map<String,CalCoreMorphAnimation>       mapCoreMorphAnimation = new HashMap<String,CalCoreMorphAnimation>();
    protected Map<String,CalCoreMesh>                 mapCoreMesh = new HashMap<String,CalCoreMesh>();
    protected Map<String,CalCoreMaterial>             mapCoreMaterial = new HashMap<String,CalCoreMaterial>();
    protected Vector<Map<String, CalCoreMaterial>> mapCoreMaterialThread = new Vector<Map<String, CalCoreMaterial>>();
    protected Object userData;
    protected Properties properties;
    
    
    /**
     * Constructs the core model instance.
     *
     * This function is the default constructor of the core model instance.
     */
    
    public CalCoreModel(String name) {
        this.name = name;
        coreSkeleton = null;
        userData = null;
    }
    
    /** Constructs a core model with the supplied skeleton.
     */
    public CalCoreModel(String name, CalCoreSkeleton skeleton) {
        this.name = name;
        coreSkeleton = skeleton;
        userData = null;
    }
    
//    public void shareMaps (Map<String, CalCoreMesh> coreMeshes, Map<String, CalCoreMaterial> coreMaterials, Map<String, CalCoreAnimation> coreAnimations)
//    {
//        mapCoreMesh = coreMeshes;
//        mapCoreMaterial = coreMaterials;
//        mapCoreAnimation = coreAnimations;
//    }
    
    /**
    /** Adds a core mesh.
     *
     * This function adds a core mesh to the core model instance.
     *
     * @param tag A name to refer to this subsequently
     * @param coreMesh A pointer to the core mesh that should be added.
     */
    
    public void addCoreMesh(String tag, CalCoreMesh coreMesh) {
        mapCoreMesh.put(tag, coreMesh);
    }
    
    /** Gets all the core meshes in a single map
     */
    public Map<String,CalCoreMesh> getCoreMeshes() {
        return mapCoreMesh;
    }
    
    public Collection<String> getCoreMeshIds() {
        return mapCoreMesh.keySet();
    }
    
    
    /**
    /** Adds a core animation.
     *
     * This function adds a core animation to the core model instance.
     *
     * @param tag A name to refer to this subsequently
     * @param coreAnimation the core animation that should be added.
     */
    
    public void addCoreAnimation(String tag, CalCoreAnimation coreAnimation) {
        mapCoreAnimation.put(tag, coreAnimation);
    }
    
    /** Removes a core animation from the core model.
     *  @param tag The name of the animation to remove.
     */
    public void removeCoreAnimation(String tag) {
        mapCoreAnimation.remove(tag);
    }
    
    public Map<String,CalCoreAnimation> getCoreAnimations() {
        return mapCoreAnimation;
    }
    
    public Collection<String> getCoreAnimationIds() {
        return mapCoreAnimation.keySet();
    }
    
    
    /**
    /** Adds a core morph animation.
     *
     * This function adds a core morph animation to the core model instance.
     *
     * @param tag A name to refer to this subsequently
     * @param coreAnimation the core morph animation that should be added.
     */
    
    public void addCoreMorphAnimation(String tag, CalCoreMorphAnimation coreAnimation) {
        mapCoreMorphAnimation.put(tag, coreAnimation);
    }
    
    /** Removes a core morph animation from the core model.
     *  @param tag The name of the animation to remove.
     */
    public void removeCoreMorphAnimation(String tag) {
        mapCoreMorphAnimation.remove(tag);
    }
    
    
    /**
    /** Adds a core material.
     *
     * This function adds a core material to the core model instance.
     *
     * @param tag A name to refer to this subsequently
     * @param coreMaterial A pointer to the core material that should be added.
     */
    
    public void addCoreMaterial(String tag, CalCoreMaterial coreMaterial) {
        // get the id of the core material
        mapCoreMaterial.put(tag, coreMaterial);
    }
    
    public Collection<String> getCoreMaterialIds() {
        return mapCoreMaterial.keySet();
    }
    
    
    /**
    /** Gets the core animation for a specified id tag.
     *
     * @param coreAnimationId The ID of the core animation that should be returned.
     *
     * @return the core animation or null if not found
     */
    
    public CalCoreAnimation getCoreAnimation(String coreAnimationId) {
        return mapCoreAnimation.get(coreAnimationId);
    }
    
    
    /**
    /** The number of core animations.
     *
     * @return The number of core animations.
     */
    
    public int getCoreAnimationCount() {
        return mapCoreAnimation.size();
    }
    
    /**
    /** Gets the morph core animation for a specified id tag.
     *
     * @param coreAnimationId The ID of the core animation that should be returned.
     *
     * @return the core animation or null if not found
     */
    
    public CalCoreMorphAnimation getCoreMorphAnimation(String coreAnimationId) {
        return mapCoreMorphAnimation.get(coreAnimationId);
    }
    
    public Map<String,CalCoreMorphAnimation> getCoreMorphAnimations() {
        return mapCoreMorphAnimation;
    }
    
    /**
    /** The number of core animations.
     *
     * @return The number of core animations.
     */
    
    public int getCoreMorphAnimationCount() {
        return mapCoreMorphAnimation.size();
    }
    
    
    /**
    /** Provides access to a core material.
     *
     * This function returns the core material with the given ID.
     *
     * @param coreMaterialId The ID of the core material that should be returned.
     *
     * @return One of the following values:
     *         \li a pointer to the core material
     *         \li \b 0 if an error happend
     */
    
    public CalCoreMaterial getCoreMaterial(String coreMaterialId) {
        return mapCoreMaterial.get(coreMaterialId);
    }
    
    public Map<String,CalCoreMaterial> getCoreMaterials() {
        return mapCoreMaterial;
    }
    
    /**
     * This function returns the number of core materials in the core model
     * instance.
     *
     * @return The number of core materials.
     */
    
    public int getCoreMaterialCount() {
        return mapCoreMaterial.size();
    }
    
    /** 
     *  Counts the number of core materials needed for this model.
     *  This is not the same as getting the count from the material set mapping.
     *  It does not deal with sparse numbering of threads though and simply
     *  reports the highest thread Id encountered (plus one).
     */
    public int countMaterialThreadsNeeded() {
        int maxThreadId = 0;
        
        for (CalCoreMesh mesh : mapCoreMesh.values()) {
            for (CalCoreSubmesh submesh : mesh.vectorCoreSubmesh) {
                maxThreadId = Math.max(maxThreadId, submesh.coreMaterialThreadId);
            }
        }
        
        return 1 + maxThreadId;
    }
    
    /** Returns a specified core material ID.
     *
     * This function returns the core material ID for a specified core material
     * thread / core material set pair.
     *
     * @param coreMaterialThreadId The ID of the core material thread.
     * @param coreMaterialSetId The ID of the core material set.
     *
     * @return One of the following values:
     *         \li the \b ID of the core material
     *         \li throws an IllegalArgumentException if an error happend
     */
    
    public CalCoreMaterial getCoreMaterial(int coreMaterialThreadId, String coreMaterialSetId) {
        if (coreMaterialThreadId < 0 || coreMaterialThreadId >= mapCoreMaterialThread.size())
            return defaultMaterial;
        
        // get the core material thread
        Map<String, CalCoreMaterial> coreMaterialThread = mapCoreMaterialThread.elementAt(coreMaterialThreadId);
        if (coreMaterialThread == null)
            return defaultMaterial;
        
        // find the material id for the given set
        CalCoreMaterial coreMaterial = coreMaterialThread.get(coreMaterialSetId);
        if (coreMaterial == null)
            return defaultMaterial;
        
        return coreMaterial;
    }
    
    CalCoreMaterial defaultMaterial = new CalCoreMaterial();
    
    {
        defaultMaterial.setAmbientColor(new Colorf(1, 0, 0, 1));
        defaultMaterial.setDiffuseColor(new Colorf(1, 0, 0, 1));
        defaultMaterial.setSpecularColor(new Colorf(1, 0, 0, 1));
    }
    
    
    /** Provides access to a core mesh.
     *
     * This function returns the core mesh with the given ID.
     *
     * @param coreMeshId The ID of the core mesh that should be returned.
     *
     * @return One of the following values:
     *         \li a pointer to the core mesh
     *         \li \b 0 if an error happend
     */
    
    public CalCoreMesh getCoreMesh(String coreMeshId) {
        return mapCoreMesh.get(coreMeshId);
    }
    
    
    /** Returns the number of core meshes.
     *
     * This function returns the number of core meshes in the core model instance.
     *
     * @return The number of core meshes.
     */
    
    public int getCoreMeshCount() {
        return mapCoreMesh.size();
    }
    
    /** Provides access to the core skeleton.
     *
     * This function returns the core skeleton.
     *
     * @return One of the following values:
     *         \li a pointer to the core skeleton
     *         \li \b 0 if an error happend
     */
    
    public CalCoreSkeleton getCoreSkeleton() {
        return coreSkeleton;
    }
    
    /** Provides access to the user data.
     *
     * This function returns the user data stored in the core model instance.
     *
     * @return The user data stored in the core model instance.
     */
    
    public Object getUserData() {
        return userData;
    }
    
    
    /** 
     * Sets a core material ID.
     *
     * This function sets a core material ID for a core material thread / core
     * material set pair.
     *
     * @param coreMaterialThreadId The ID of the core material thread.
     * @param coreMaterialSetId The ID of the core maetrial set.
     * @param coreMaterialId The ID of the core maetrial.
     */
    
    public void setCoreMaterial(int coreMaterialThreadId, String coreMaterialSetId, String coreMaterialId) {
        // find the core material thread
        if (coreMaterialThreadId >= mapCoreMaterialThread.size())
            mapCoreMaterialThread.setSize(coreMaterialThreadId+1);
        
        Map<String, CalCoreMaterial> coreMaterialThread = mapCoreMaterialThread.elementAt(coreMaterialThreadId);
        if(coreMaterialThread == null) {
            coreMaterialThread = new HashMap<String, CalCoreMaterial>();
            mapCoreMaterialThread.set(coreMaterialThreadId, coreMaterialThread);
        }
        
        CalCoreMaterial coreMaterial = mapCoreMaterial.get(coreMaterialId);
        if(coreMaterial == null)
            throw new IllegalArgumentException();
        
        // remove a possible entry in the core material thread
        coreMaterialThread.remove(coreMaterialSetId);
        
        // set the given set id in the core material thread to the given core material
        coreMaterialThread.put(coreMaterialSetId, coreMaterial);
    }
    
    /** 
     * Gets the number of core material threads.
     */
    
    public int getNumCoreMaterialThreads() {
        return mapCoreMaterialThread.size();
    }
    
    /** 
     * Gets the vector of maps from core material set to core material indexed by thread.
     */
    
    public Vector<Map<String, CalCoreMaterial>> getCoreMaterialThreadMaps() {
        return mapCoreMaterialThread;
    }
    
    /** Sets the core skeleton.
     *
     * This function sets the core skeleton of the core model instance..
     *
     * @param coreSkeleton The core skeleton that should be set.
     */
    
    public void setCoreSkeleton(CalCoreSkeleton coreSkeleton) {
        this.coreSkeleton = coreSkeleton;
    }
    
    
    
    
    /**
     * Stores user data.
     *
     * This function stores user data in the core model instance.
     *
     * @param userData The user data that should be stored.
     */
    
    public void setUserData(Object userData) {
        this.userData = userData;
    }
    
    /** Gets the properties (as key/value pairs) for this model supplied when it was loaded.
     *  These properties may include further details about how the model was generated,
     *  or how it should be used.
     */
    public java.util.Properties getProperties() {
        return properties;
    }
    
    public String getProperty(String property) {
        return properties != null ? properties.getProperty(property) : null;
    }
    
    /**
     *  Sets the properties for this model - usually when it is loaded.
     *  These properties may include further details about how the model was generated,
     *  or how it should be used. The properties will also usually include the source file names
     *  for the various components which form the model.
     */
    public void setProperties(java.util.Properties properties) {
        this.properties = properties;
    }
    
    public String getName() {
        return name;
    }

}

