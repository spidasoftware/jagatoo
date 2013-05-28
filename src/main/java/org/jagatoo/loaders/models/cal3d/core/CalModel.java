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

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/** An instance of a core model with its own positions for the bones.
 *  The Cal3d structure is very open but it is my intention to close this off and
 *  move much of the data into native storage so an optimised mechanism can do the
 *  bone transforms - either using the vector co-pro or on the graphics hardware
 *  with a vertex program. In the meantime avoid manipulating CalBones directly.
 *
 *  For multithreading purposes this is usually the object to synchronize on.
 */
public class CalModel {
    protected CalCoreModel coreModel;
    protected CalSkeleton skeleton;
    protected CalMixer mixer;
    protected CalPhysique physique;
    protected CalSpringSystem springSystem;
    protected Object userData;
    protected List<CalMesh> meshes = new Vector<CalMesh>();
    protected String currentMaterialSet;
    
    
    
    
    //****************************************************************************//
    
    /*****************************************************************************/
    /** Constructs the model instance.
     *
     * This function is the default constructor of the model instance.
     *****************************************************************************/
    
    public CalModel(CalCoreModel coreModel) {
        if (coreModel == null)
            throw new IllegalArgumentException();
        
        this.coreModel = coreModel;
        
        // create the skeleton from the core skeleton
        skeleton = new CalSkeleton(coreModel.getCoreSkeleton());
        
        // create a new mixer instance acting on this model
        mixer = new CalMixer(this);
        
        // create the physique from this model
        physique = new CalPhysique(this);
        
        // create the spring system from this model
        springSystem = new CalSpringSystem(this);
        
        // initialize the user data
        userData = null;
    }
    
    /** Constructs a near clone of the supplied model.
     *  The active meshes and current materials will be reproduced but
     *  internal state will be back to an initial state.
     */
    public CalModel(CalModel other) {
        if (other == null)
            throw new IllegalArgumentException();
        
        this.coreModel = other.getCoreModel();
        
        // create the skeleton from the core skeleton
        skeleton = new CalSkeleton(coreModel.getCoreSkeleton());
        
        // create a new mixer instance acting on this model
        mixer = new CalMixer(this);
        
        // create the physique from this model
        physique = new CalPhysique(this);
        
        // create the spring system from this model
        springSystem = new CalSpringSystem(this);
        
        // initialize the user data
        userData = null;
        
        for (CalMesh activeMesh : other.meshes) {
            attachMesh(activeMesh.getCoreMesh().getName());
        }
        
        setMaterialSet(other.currentMaterialSet);
    }
    
    
    /*****************************************************************************/
    /** Destructs the model instance.
     *
     * This function is the destructor of the model instance.
     *****************************************************************************/
    
    // NOTE: C++ destructor mapped to finalize
    @Override
    public void finalize() {
        assert meshes.isEmpty();
    }
    
    
    /*****************************************************************************/
    /** Attachs a mesh.
     *
     * This function attachs a mesh to the model instance.
     *
     * @param coreMeshId The ID of the mesh that should be attached.
     *****************************************************************************/
    
    public synchronized void attachMesh(String coreMeshId) {
        // get the core mesh
        CalCoreMesh coreMesh = coreModel.getCoreMesh(coreMeshId);
        
        // check if the mesh is already attached
        for (int meshId = 0; meshId < meshes.size(); meshId++) {
            // check if we found the matching mesh
            if ((meshes.get(meshId)).getCoreMesh() == coreMesh) {
                // mesh is already active -> do nothing
                return;
            }
        }
        
        // allocate a new mesh instance
        CalMesh mesh = new CalMesh(coreMesh);
        
        // set model in the mesh instance
        mesh.setModel(this);
        
        // insert the new mesh into the active list
        meshes.add(mesh);
    }
    
    
    /*****************************************************************************/
    /** Detaches a mesh.
     *
     * This function detaches a mesh from the model instance.
     *
     * @param coreMeshId The ID of the mesh that should be detached.
     *
     * @return One of the following values:
     *<ul>
     *         <li> true if successful
     *         <li> false if the mesh was not attached
     *</ul>
     *****************************************************************************/
    
    public synchronized boolean detachMesh(String coreMeshId) {
        // get the core mesh
        CalCoreMesh coreMesh = coreModel.getCoreMesh(coreMeshId);
        
        // find the mesh for the given id
        for (Iterator<CalMesh> iteratorMesh = meshes.iterator(); iteratorMesh.hasNext(); ) {
            // get the mesh
            CalMesh mesh = iteratorMesh.next();
            
            // check if we found the matching mesh
            if (mesh.getCoreMesh() == coreMesh) {
                // erase the mesh out of the active mesh list
                iteratorMesh.remove();
                
                return true;
            }
        }
        
        return false;
    }
    
    public synchronized void detachAllMeshes() {
        meshes.clear();
    }
    
    
    /*****************************************************************************/
    /** Provides access to the core model.
     *
     * This function returns the core model on which this model instance is based
     * on.
     *
     * @return One of the following values:
     *         \li a pointer to the core model
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalCoreModel getCoreModel() {
        return coreModel;
    }
    
    
    /*****************************************************************************/
    /** Provides access to an attached mesh.
     *
     * This function returns the attached mesh with the given core mesh ID.
     *
     * @param coreMeshId The core mesh ID of the mesh that should be returned.
     *
     * @return One of the following values:
     *         \li a pointer to the mesh
     *         \li \b null if an error happend
     *****************************************************************************/
    
    public CalMesh getMesh(String coreMeshId) {
        // get the core mesh
        CalCoreMesh coreMesh = coreModel.getCoreMesh(coreMeshId);
        
        // search the mesh
        for (int meshId = 0; meshId < meshes.size(); meshId++) {
            CalMesh mesh = meshes.get(meshId);
            
            // check if we found the matching mesh
            if (mesh.getCoreMesh() == coreMesh) {
                return mesh;
            }
        }
        
        return null;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the mixer.
     *
     * This function returns the mixer.
     *
     * @return One of the following values:
     *         \li a pointer to the mixer
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalMixer getMixer() {
        return mixer;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the physique.
     *
     * This function returns the physique.
     *
     * @return One of the following values:
     *         \li a pointer to the physique
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalPhysique getPhysique() {
        return physique;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the skeleton.
     *
     * This function returns the skeleton.
     *
     * @return One of the following values:
     *         \li a pointer to the skeleton
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalSkeleton getSkeleton() {
        return skeleton;
    }
    
    /** Sets the skeleton to a new instance of the coreSkeleton.
     *  This method may be of use if a model has several compatible skeletons
     *  such as a simple body and a body with full facial rig.
     *  Otherwise it is principally of use during the design phase.
     */
    public synchronized void setCoreSkeleton(CalCoreSkeleton coreSkeleton) {
        // create the skeleton from the core skeleton
        skeleton = new CalSkeleton(coreModel.getCoreSkeleton());
    }
    
    
    /*****************************************************************************/
    /** Provides access to the spring system.
     *
     * This function returns the spring system.
     *
     * @return One of the following values:
     *         \li a pointer to the spring system
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public CalSpringSystem getSpringSystem() {
        return springSystem;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the user data.
     *
     * This function returns the user data stored in the model instance.
     *
     * @return The user data stored in the model instance.
     *****************************************************************************/
    
    public Object getUserData() {
        return userData;
    }
    
    
    /*****************************************************************************/
    /** Returns the mesh vector.
     * 
     * This function returns the vector that contains all attached meshes of the
     * model instance.
     *
     * @return A reference to the mesh vector.
     *****************************************************************************/
    
    public List<CalMesh> getMeshes() {
        return meshes;
    }
    
    
    /*****************************************************************************/
    /** Sets the LOD level.
     *
     * This function sets the LOD level of all attached meshes.
     *
     * @param lodLevel The LOD level in the range [0.0, 1.0].
     *****************************************************************************/
    
    public synchronized void setLodLevel(float lodLevel) {
        // set the lod level in all meshes
        for (CalMesh mesh: meshes) {
            // set the lod level in the mesh
            mesh.setLodLevel(lodLevel);
        }
    }
    
    
    /*****************************************************************************/
    /** Sets the material set.
     *
     * This function sets the material set of all attached meshes.
     *
     * @param setId The ID of the material set.
     *****************************************************************************/
    
    public synchronized void setMaterialSet(String setId) {
        currentMaterialSet = setId;
        
        for (CalMesh mesh : meshes) {
            mesh.setMaterialSet(setId);
        }
    }
    
    public synchronized void setMaterialSet(List<CalCoreMaterial> materials) {
        currentMaterialSet = null;
        
        for (CalMesh mesh : meshes) {
            mesh.setMaterialSet(materials);
        }
    }
    
    public String getMaterialSet() {
        return currentMaterialSet;
    }
    
    
    /*****************************************************************************/
    /** Stores user data.
     *
     * This function stores user data in the model instance.
     *
     * @param userData The user data that should be stored.
     *****************************************************************************/
    
    public void setUserData(Object userData) {
        this.userData = userData;
    }
    
    
    /*****************************************************************************/
    /** Updates the model instance.
     *
     * This function updates the model instance for a given amount of time.
     *
     * @param deltaTime The elapsed time in seconds since the last update.
     *****************************************************************************/
    
    public synchronized void update(float deltaTime) {
        mixer.updateAnimation(deltaTime);
        mixer.updateSkeleton();
        physique.update();
        springSystem.update(deltaTime);
    }
}

