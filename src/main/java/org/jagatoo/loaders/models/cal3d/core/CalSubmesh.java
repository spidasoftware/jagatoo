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

import org.jagatoo.loaders.models.cal3d.buffer.IndexBuffer;
import org.jagatoo.loaders.models.cal3d.buffer.ScalarfBuffer;
import org.jagatoo.loaders.models.cal3d.buffer.Vector3fBuffer;


//****************************************************************************//
// Class declaration                                                          //
//****************************************************************************//

/*****************************************************************************/
/** The submesh class.
 *****************************************************************************/
public class CalSubmesh {
    protected CalCoreSubmesh coreSubmesh;
    protected Vector3fBuffer vertexPositions;
    protected Vector3fBuffer vertexNormals;
    protected Vector3fBuffer [ ] vertexTangents;
    protected ScalarfBuffer [ ] vertexTangentCrossFactors;
    protected IndexBuffer faceIndices;
    protected float [ ] vectorMorphTargetWeight;
    protected PhysicalProperty [ ] vectorPhysicalProperty;
    protected int vertexCount;
    protected int faceCount;
    protected CalCoreMaterial coreMaterial;
    protected boolean internalData;
    
    /*****************************************************************************/
    /** Creates a submesh based on a core submesh.
     *
     * @param coreSubmesh A pointer to the core submesh on which this submesh
     *                     instance should be based on.
     *****************************************************************************/
    
    public CalSubmesh(CalCoreSubmesh coreSubmesh) {
        if(coreSubmesh == null)
            throw new IllegalArgumentException();
        
        this.coreSubmesh = coreSubmesh;
        
        // reserve memory for the face vector
        faceIndices = new IndexBuffer(3*coreSubmesh.getFaceCount());
        
        // set the initial lod level
        setLodLevel(1.0f);
        
        // set the initial material id
        coreMaterial = null;
        
        //Setting the morph target weights
        vectorMorphTargetWeight = new float [coreSubmesh.getCoreSubMorphTargetCount()];
        
        // check if the submesh instance must handle the vertex and normal data internally
        if(coreSubmesh.getSpringCount() > 0) {
            vertexPositions = new Vector3fBuffer(coreSubmesh.getVertexCount());
            vertexNormals = new Vector3fBuffer(coreSubmesh.getVertexCount());
            
            vertexTangents = new Vector3fBuffer [coreSubmesh.getTextureCoordinates().length];
            vertexTangentCrossFactors = new ScalarfBuffer [coreSubmesh.getTextureCoordinates().length];
            
            vectorPhysicalProperty = new PhysicalProperty [coreSubmesh.getVertexCount()];
            for (int n = 0; n < vectorPhysicalProperty.length; n++)
                vectorPhysicalProperty [n] = new PhysicalProperty();
            
            // get the vertex vector of the core submesh
            Vector3fBuffer coreVertexPositions = coreSubmesh.getVertexPositions();
            Vector3fBuffer coreVertexNormals = coreSubmesh.getVertexNormals();
            
            Vector3f position = new Vector3f();
            Vector3f normal = new Vector3f();
            
            // copy the data from the core submesh as default values
            for(int vertexId = 0; vertexId < coreSubmesh.getVertexCount(); vertexId++) {
                // copy the vertex data
                coreVertexPositions.get(vertexId, position);
                vertexPositions.put(vertexId, position);
                vectorPhysicalProperty[vertexId].position.set(position);
                vectorPhysicalProperty[vertexId].positionOld.set(position);
                
                // copy the normal data
                coreVertexNormals.get(vertexId, normal);
                vertexNormals.put(vertexId, normal);
            }
            
            internalData = true;
        } else {
            internalData = false;
        }
    }
    
    
    /*****************************************************************************/
    /** @return the core material.
     * 
     * This function returns the core material of the submesh instance.
     *****************************************************************************/
    
    public CalCoreMaterial getCoreMaterial() {
        return coreMaterial;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the core submesh.
     *
     * This function returns the core submesh on which this submesh instance is
     * based on.
     *****************************************************************************/
    
    public CalCoreSubmesh getCoreSubmesh() {
        return coreSubmesh;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of faces.
     *
     * This function returns the number of faces in the submesh instance.
     *
     * @return The number of faces.
     *****************************************************************************/
    
    public int getFaceCount() {
        return faceCount;
    }
    
    /*****************************************************************************/
    /** Provides access to the face data.
     *
     * This function returns the face data (vertex indices) of the submesh
     * instance. The LOD setting of the submesh instance is taken into account.
     *
     * @return The number of faces written to the buffer.
     *****************************************************************************/
    public IndexBuffer getFaceIndices() {
        return faceIndices;
    }
    
    
    /*****************************************************************************/
    /** Returns the normal vector.
     *
     * This function returns the vector that contains all normals of the submesh
     * instance.
     *
     * @return A reference to the normal vector.
     *****************************************************************************/
    
    public Vector3fBuffer getVertexNormals() {
        return vertexNormals;
    }
    
    public Vector3fBuffer getVertexTangents(int mapId) {
        return vertexTangents == null ? null : vertexTangents [mapId];
    }
    
    public int getVertexTangentsMapCount() {
        return vertexTangents == null ? 0 : vertexTangents.length;
    }
    
    
    /*****************************************************************************/
    /** Returns the physical property vector.
     *
     * This function returns the vector that contains all physical properties of
     * the submesh instance.
     *
     * @return A reference to the physical property vector.
     *****************************************************************************/
    
    public PhysicalProperty [ ] getVectorPhysicalProperty() {
        return vectorPhysicalProperty;
    }
    
    
    /*****************************************************************************/
    /** Returns the vertex vector.
     *
     *
     * This function returns the vector that contains all vertices of the submesh
     * instance.
     *
     * @return A reference to the vertex vector.
     *****************************************************************************/
    
    public Vector3fBuffer getVertexPositions() {
        return vertexPositions;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of vertices.
     *
     * This function returns the number of vertices in the submesh instance.
     *
     * @return The number of vertices.
     *****************************************************************************/
    
    public int getVertexCount() {
        return vertexCount;
    }
    
    
    /*****************************************************************************/
    /** Returns if the submesh instance handles vertex data internally.
     *
     * This function returns wheter the submesh instance handles vertex data
     * internally.
     *
     * @return true if vertex data is handled internally, false if not
     *****************************************************************************/
    
    public boolean hasInternalData() {
        return internalData;
    }
    
    /*****************************************************************************/
    /** Disable internal data (and thus springs system)
     *
     *****************************************************************************/
    
    public void disableInternalData() {
        if(internalData) {
            vertexPositions = null;
            vertexNormals   = null;
            vertexTangents  = null;
            vertexTangentCrossFactors = null;
            vectorPhysicalProperty = null;
            
            internalData=false;
        }
    }
    
    /*****************************************************************************/
    /** Returns true if tangent vectors are enabled.
     *
     * This function returns true if the submesh contains tangent vectors.
     *
     * @return True if tangent vectors are enabled.
     *****************************************************************************/
    
    public boolean isTangentsEnabled(int mapId) {
        return coreSubmesh.isTangentsEnabled(mapId);
    }
    
    /*****************************************************************************/
    /** Enables (and calculates) or disables the storage of tangent spaces.
     *
     * This function enables or disables the storage of tangent space bases.
     *****************************************************************************/
    
    public void setTangentsEnabled(int mapId, boolean enabled) {
        coreSubmesh.setTangentsEnabled(mapId, enabled);
        
        if(!internalData)
            return;
        
        if(!enabled) {
            vertexTangents  = null;
            vertexTangentCrossFactors = null;
            return;
        }
        
        vertexTangents [mapId] = new Vector3fBuffer(coreSubmesh.getVertexCount());
        vertexTangentCrossFactors [mapId] = new ScalarfBuffer(coreSubmesh.getVertexCount());
        
        // copy the data from the core submesh as default values
        vertexTangents [mapId].set(coreSubmesh.getVertexTangents(mapId));
        vertexTangentCrossFactors [mapId].set(coreSubmesh.getVertexTangentCrossFactors(mapId));
    }
    
    
    /*****************************************************************************/
    /** Sets the core material ID.
     *
     * This function sets the core material ID of the submesh instance.
     *
     * @param coreMaterial The core material ID that should be set.
     *****************************************************************************/
    
    public void setCoreMaterial(CalCoreMaterial coreMaterial) {
        this.coreMaterial = coreMaterial;
    }
    
    
    /*****************************************************************************/
    /** Sets the LOD level.
     *
     * This function sets the LOD level of the submesh instance.
     *
     * @param lodLevel The LOD level in the range [0.0, 1.0].
     *****************************************************************************/
    
    public void setLodLevel(float lodLevel) {
        // clamp the lod level to [0.0, 1.0]
        if(lodLevel < 0.0f) lodLevel = 0.0f;
        if(lodLevel > 1.0f) lodLevel = 1.0f;
        
        // get the lod count of the core submesh
        int lodCount = coreSubmesh.getLodCount();
        
        // calculate the target lod count
        lodCount = (int)((1.0f - lodLevel) * lodCount);
        
        // calculate the new number of vertices
        vertexCount = coreSubmesh.getVertexCount() - lodCount;
        
        // get face vector of the core submesh
        CalCoreSubmesh.Face [ ] vectorFace = coreSubmesh.getVectorFace();
        
        // get face vector of the core submesh
        CalCoreSubmesh.VertexInfo [ ] vectorVertex = coreSubmesh.getVectorVertexInfo();
        
        // calculate the new number of faces
        faceCount = vectorFace.length;
        
        for (int vertexId = vectorVertex.length - 1; vertexId >= vertexCount; vertexId--) {
            faceCount -= vectorVertex [vertexId].faceCollapseCount;
        }
        
        // fill the face vector with the collapsed vertex ids
        for (int faceId = 0; faceId < faceCount; faceId++) {
            CalCoreSubmesh.Face face = vectorFace [faceId];
            for (int vertexId = 0; vertexId < 3; vertexId++) {
                // get the vertex id
                int collapsedVertexId = face.vertexId[vertexId];
                
                // collapse the vertex id until it fits into the current lod level
                while(collapsedVertexId >= vertexCount)
                    collapsedVertexId = vectorVertex [collapsedVertexId].collapseId;
                
                // store the collapse vertex id in the submesh face vector
                faceIndices.put(3*faceId+vertexId, collapsedVertexId);
            }
        }
    }
    
    /*****************************************************************************/
    /** Sets weight of a morph target with the given id.
     *
     * @param blendId The morph target id.
     * @param weight The weight to be set.
     *****************************************************************************/
    
    public void setMorphTargetWeight(int blendId, float weight) {
        vectorMorphTargetWeight[blendId] = weight;
    }
    
    /*****************************************************************************/
    /** Gets weight of a morph target with the given id.
     *
     * @param blendId The morph target id.
     * @return The weight of the morph target.
     *****************************************************************************/
    
    public float getMorphTargetWeight(int blendId) {
        return vectorMorphTargetWeight[blendId];
    }
    
    /*****************************************************************************/
    /** Gets weight of the base vertices.
     *
     * @return The weight of the base vertices.
     *****************************************************************************/
    
    public float getBaseWeight() {
        float baseWeight = 1.0f;
        int morphTargetCount = getMorphTargetWeightCount();
        for(int morphTargetId=0; morphTargetId < morphTargetCount; morphTargetId++) {
            baseWeight -= vectorMorphTargetWeight[morphTargetId];
        }
        return baseWeight;
    }
    
    public float [ ] getVectorMorphTargetWeight() {
        return vectorMorphTargetWeight;
    }
    
    public int getMorphTargetWeightCount() {
        return vectorMorphTargetWeight.length;
    }
    
    /// The submesh PhysicalProperty.
    public class PhysicalProperty {
        Vector3f position = new Vector3f();
        Vector3f positionOld = new Vector3f();
        Vector3f force = new Vector3f();
    }
}

