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

import java.util.List;
import java.util.Vector;


// ****************************************************************************//
// Class declaration //
// ****************************************************************************//

/*******************************************************************************
 * The mesh class.
 ******************************************************************************/
public class CalMesh {
    protected CalModel model;
    protected CalCoreMesh coreMesh;
    protected List<CalSubmesh> submeshes = new Vector<CalSubmesh>();
    
    /***************************************************************************
     * Creates a mesh based on a core mesh.
     * 
     * This function is the default constructor of the mesh instance.
     **************************************************************************/
    
    public CalMesh(CalCoreMesh coreMesh) {
        model = null;
        
        if (coreMesh == null)
            throw new IllegalArgumentException();
        
        this.coreMesh = coreMesh;
        
        // clone the mesh structure of the core mesh
        // clone every core submesh
        for (CalCoreSubmesh coreSubmesh : coreMesh.getVectorCoreSubmesh()) {
            CalSubmesh submesh = new CalSubmesh(coreSubmesh);
            
            // insert submesh into submesh vector
            submeshes.add(submesh);
        }
    }
    
    /***************************************************************************
     * Provides access to the core mesh.
     * 
     * This function returns the core mesh on which this mesh instance is based
     * on.
     * 
     * @return One of the following values: \li a pointer to the core mesh \li
     *         \b 0 if an error happend
     **************************************************************************/
    
    public CalCoreMesh getCoreMesh() {
        return coreMesh;
    }
    
    /***************************************************************************
     * Provides access to a submesh.
     * 
     * This function returns the submesh with the given ID.
     * 
     * @param id
     *                The ID of the submesh that should be returned.
     * 
     * @return One of the following values: \li a pointer to the submesh \li \b
     *         null if an error happend
     **************************************************************************/
    
    public CalSubmesh getSubmesh(int id) {
        return submeshes.get(id);
    }
    
    /***************************************************************************
     * Returns the number of submeshes.
     * 
     * This function returns the number of submeshes in the mesh instance.
     * 
     * @return The number of submeshes.
     **************************************************************************/
    
    public int getSubmeshCount() {
        return submeshes.size();
    }
    
    /***************************************************************************
     * Returns the submesh vector.
     * 
     * This function returns the vector that contains all submeshes of the mesh
     * instance.
     * 
     * @return A reference to the submesh vector.
     **************************************************************************/
    
    public List<CalSubmesh> getSubmeshes() {
        return submeshes;
    }
    
    /***************************************************************************
     * Sets the LOD level.
     * 
     * This function sets the LOD level of the mesh instance.
     * 
     * @param lodLevel
     *                The LOD level in the range [0.0, 1.0].
     **************************************************************************/
    
    public void setLodLevel(float lodLevel) {
        // change lod level of every submesh
        for (CalSubmesh submesh : submeshes) {
            // set the lod level in the submesh
            submesh.setLodLevel(lodLevel);
        }
    }
    
    /***************************************************************************
     * Sets the material set.
     * 
     * This function sets the material set of the mesh instance.
     * 
     * @param setId
     *                The ID of the material set.
     **************************************************************************/
    
    public void setMaterialSet(String setId) {
        // change material of every submesh
        for (CalSubmesh submesh : submeshes) {
            
            try {
                // get the core material thread id of the submesh
                int coreMaterialThreadId = submesh.getCoreSubmesh()
                        .getCoreMaterialThreadId();
                
                // get the core material id for the given set id in the material
                // thread
                CalCoreMaterial coreMaterial = model.getCoreModel()
                        .getCoreMaterial(coreMaterialThreadId, setId);
                
                // set the new core material id in the submesh
                submesh.setCoreMaterial(coreMaterial);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    /***************************************************************************
     * Sets the material set.
     * 
     * This function sets the material set of the mesh instance.
     * 
     * @param materials
     *                the materials in ID order
     **************************************************************************/
    
    public void setMaterialSet(List<CalCoreMaterial> materials) {
        for (CalSubmesh submesh : submeshes) {
            
            try {
                // get the core material thread id of the submesh
                int coreMaterialThreadId = submesh.getCoreSubmesh()
                        .getCoreMaterialThreadId();
                
                // get the core material id for the given set id in the material
                // thread
                CalCoreMaterial coreMaterial = materials.get(Math.max(0,
                        Math.min(coreMaterialThreadId, materials.size() - 1)));
                
                // set the new core material id in the submesh
                submesh.setCoreMaterial(coreMaterial);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                System.err.println("Size was = "+materials.size());
            }
        }
    }
    
    /***************************************************************************
     * Sets the model.
     * 
     * This function sets the model to which the mesh instance is attached to.
     * 
     * @param model
     *                The model to which the mesh instance should be attached
     *                to.
     **************************************************************************/
    
    public void setModel(CalModel model) {
        this.model = model;
    }
}
