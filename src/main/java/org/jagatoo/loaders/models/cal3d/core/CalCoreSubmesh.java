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

import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;

import org.jagatoo.loaders.models.cal3d.buffer.ScalarfBuffer;
import org.jagatoo.loaders.models.cal3d.buffer.TexCoord2fBuffer;
import org.jagatoo.loaders.models.cal3d.buffer.Vector3fBuffer;

import java.util.Vector;


/*****************************************************************************/
/** The core submesh class.
 *****************************************************************************/
public class CalCoreSubmesh {
    protected VertexInfo [ ] vectorVertexInfo;
    protected Vector3fBuffer vertexPositions;
    protected Vector3fBuffer vertexNormals;
    
    protected boolean [ ] tangentsEnabled;
    protected Vector3fBuffer [ ] vertexTangents;
    protected ScalarfBuffer [ ] vertexTangentCrossFactors;
    
    protected TexCoord2fBuffer [ ] textureCoordinateBuffers;
    
    protected float [ ] vectorPhysicalProperty;
    protected Face [ ] vectorFace;
    protected Spring [ ] vectorSpring;
    
    protected Vector<CalCoreSubMorphTarget> coreSubMorphTargets;
    
    protected int coreMaterialThreadId;
    protected int lodCount;
    
    
    /*****************************************************************************/
    /** Constructs the core submesh instance.
     *
     * This function is the default constructor of the core submesh instance.
     *****************************************************************************/
    
    public  CalCoreSubmesh() {
        coreMaterialThreadId = 0;
        lodCount = 0;
    }
    
    /*****************************************************************************/
    /** Returns the ID of the core material thread.
     *
     * This function returns the ID of the core material thread of this core
     * submesh instance.
     *
     * @return The ID of the core material thread.
     *****************************************************************************/
    
    public int getCoreMaterialThreadId() {
        return coreMaterialThreadId;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of faces.
     *
     * This function returns the number of faces in the core submesh instance.
     *
     * @return The number of faces.
     *****************************************************************************/
    
    public int getFaceCount() {
        return vectorFace.length;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of LOD steps.
     *
     * This function returns the number of LOD steps in the core submesh instance.
     *
     * @return The number of LOD steps.
     *****************************************************************************/
    
    public int getLodCount() {
        return lodCount;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of springs.
     *
     * This function returns the number of springs in the core submesh instance.
     *
     * @return The number of springs.
     *****************************************************************************/
    
    public int getSpringCount() {
        return vectorSpring == null ? 0 : vectorSpring.length;
    }
    
    
    /*****************************************************************************/
    /** Returns the face vector.
     *
     * This function returns the vector that contains all faces of the core submesh
     * instance.
     *
     * @return A reference to the face vector.
     *****************************************************************************/
    
    public Face [ ] getVectorFace() {
        return vectorFace;
    }
    
    
    /*****************************************************************************/
    /** Returns the physical property vector.
     *
     * This function returns the vector that contains all physical properties of
     * the core submesh instance.
     *
     * @return A reference to the physical property vector.
     *****************************************************************************/
    
    public float [ ] getVectorPhysicalProperty() {
        return vectorPhysicalProperty;
    }
    
    
    /*****************************************************************************/
    /** Returns the spring vector.
     *
     * This function returns the vector that contains all springs of the core
     * submesh instance.
     *
     * @return A reference to the spring vector.
     *****************************************************************************/
    
    public Spring [ ] getVectorSpring() {
        return vectorSpring;
    }
    
    
    /*****************************************************************************/
    /** Returns an array of texture coordinate buffers - one for each texture map.
     *
     * @return an array of texture coordinate buffers.
     *****************************************************************************/
    
    public TexCoord2fBuffer [ ] getTextureCoordinates() {
        return textureCoordinateBuffers;
    }
    
    
    /*****************************************************************************/
    /** Returns the vertex vector.
     *
     * This function returns the vector that contains all vertices of the core
     * submesh instance.
     *
     * @return A reference to the vertex vector.
     *****************************************************************************/
    
    public VertexInfo [ ] getVectorVertexInfo() {
        return vectorVertexInfo;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of vertices.
     *
     * This function returns the number of vertices in the core submesh instance.
     *
     * @return The number of vertices.
     *****************************************************************************/
    
    public int getVertexCount() {
        return vectorVertexInfo.length;
    }
    
    
    /*****************************************************************************/
    /** Reserves memory for the vertices, faces and texture coordinates.
     *
     * This function reserves memory for the vertices, faces, texture coordinates
     * and springs of the core submesh instance.
     *
     * @param vertexCount The number of vertices that this core submesh instance
     *                    should be able to hold.
     * @param textureCoordinateCount The number of texture coordinates that this
     *                               core submesh instance should be able to hold.
     * @param faceCount The number of faces that this core submesh instance should
     *                  be able to hold.
     * @param springCount The number of springs that this core submesh instance
     *                  should be able to hold.
     *****************************************************************************/
    
    public void reserve(int vertexCount, int textureCoordinateCount, int faceCount, int springCount) {
        // reserve the space needed in all the vectors
        vectorVertexInfo = new VertexInfo [vertexCount];
        vertexPositions = new Vector3fBuffer(vertexCount);
        vertexNormals = new Vector3fBuffer(vertexCount);
        
        textureCoordinateBuffers = new TexCoord2fBuffer [textureCoordinateCount];
        
        for(int textureCoordinateId = 0; textureCoordinateId < textureCoordinateCount; textureCoordinateId++) {
            textureCoordinateBuffers [textureCoordinateId] = new TexCoord2fBuffer(vertexCount);
        }
        
        tangentsEnabled = new boolean [textureCoordinateCount];
        vertexTangents = new Vector3fBuffer [textureCoordinateCount];
        vertexTangentCrossFactors = new ScalarfBuffer [textureCoordinateCount];
        
        vectorFace = new Face [faceCount];
        
        // reserve the space for the physical properties if we have springs in the core submesh instance
        if(springCount > 0) {
            vectorSpring = new Spring [springCount];
            vectorPhysicalProperty = new float [vertexCount];
        }
    }
    
    
    /*****************************************************************************/
    /** Sets the ID of the core material thread.
     *
     * This function sets the ID of the core material thread of the core submesh
     * instance.
     *
     * @param coreMaterialThreadId The ID of the core material thread that should
     *                             be set.
     *****************************************************************************/
    
    public void setCoreMaterialThreadId(int coreMaterialThreadId) {
        this.coreMaterialThreadId = coreMaterialThreadId;
    }
    
    
    /*****************************************************************************/
    /** Sets a specified face.
     *
     * This function sets a specified face in the core submesh instance.
     *
     * @param faceId  The ID of the face.
     * @param face The face that should be set.
     *****************************************************************************/
    
    public void setFace(int faceId, Face face) {
        vectorFace[faceId] = face;
    }
    
    
    /*****************************************************************************/
    /** Sets the number of LOD steps.
     *
     * This function sets the number of LOD steps of the core submesh instance.
     *
     * @param lodCount The number of LOD steps that should be set.
     *****************************************************************************/
    
    public void setLodCount(int lodCount) {
        this.lodCount = lodCount;
    }
    
    
    /*****************************************************************************/
    /** Sets a specified physical property.
     *
     * This function sets a specified physical property in the core submesh
     * instance.
     *
     * @param vertexId  The ID of the vertex.
     * @param physicalProperty The physical property that should be set.
     *****************************************************************************/
    
    public void setPhysicalProperty(int vertexId, float physicalProperty) {
        vectorPhysicalProperty[vertexId] = physicalProperty;
    }
    
    
    /*****************************************************************************/
    /** Sets a specified spring.
     *
     * This function sets a specified spring in the core submesh instance.
     *
     * @param springId  The ID of the spring.
     * @param spring The spring that should be set.
     *****************************************************************************/
    
    public void setSpring(int springId, Spring spring) {
        vectorSpring[springId] = spring;
    }
    
    
    /*****************************************************************************/
    /** Sets a specified texture coordinate.
     *
     * This function sets a specified texture coordinate in the core submesh
     * instance.
     *
     * @param vertexId  The ID of the vertex.
     * @param textureCoordinateId  The ID of the texture coordinate.
     * @param textureCoordinate The texture coordinate that should be set.
     *****************************************************************************/
    
    public void setTextureCoordinate(int vertexId, int textureCoordinateId, Vector2f textureCoordinate) {
        textureCoordinateBuffers[textureCoordinateId].put(vertexId, textureCoordinate);
    }
    
    
    /*****************************************************************************/
    /** Sets a specified vertex.
     *
     * This function sets a specified vertex in the core submesh instance.
     *
     * @param vertexId  The ID of the vertex.
     * @param vertex The vertex that should be set.
     *****************************************************************************/
    
    public void setVertex(int vertexId, VertexInfo vertex, Vector3f position, Vector3f normal) {
        vectorVertexInfo[vertexId] = vertex;
        vertexPositions.put(vertexId, position);
        vertexNormals.put(vertexId, normal);
    }
    
    /*****************************************************************************/
    /** Sets the tangent vector associated with a specified texture coordinate pair.
     *
     * This function sets the tangent vector associated with a specified
     * texture coordinate pair in the core submesh instance.
     *
     * @param vertexId  The ID of the vertex.
     * @param textureCoordinateId The ID of the texture coordinate channel.
     * @param tangent   The tangent vector that should be stored.
     * @param crossFactor The cross-product factor that should be stored.
     *****************************************************************************/
    
    public void setTangentSpace(int vertexId, int textureCoordinateId, Vector3f tangent, float crossFactor) {
        if(!tangentsEnabled[textureCoordinateId])
            throw new IllegalArgumentException("Tangents have not been enabled for texture coordinate id");
        
        vertexTangents [textureCoordinateId].put(vertexId, tangent);
        vertexTangentCrossFactors [textureCoordinateId].put(vertexId, crossFactor);
    }
    
    /**
     * @return the vertex normals buffer.
     */
    public Vector3fBuffer getVertexNormals() {
        return vertexNormals;
    }
    
    public void getVertexNormal(int vertexId, Vector3f normal) {
        vertexNormals.get(vertexId, normal);
    }
    
    /**
     * @return the vertex positions buffer.
     */
    public Vector3fBuffer getVertexPositions() {
        return vertexPositions;
    }
    
    public void getVertexPosition(int vertexId, Vector3f position) {
        vertexPositions.get(vertexId, position);
    }
    
    public Vector3fBuffer getVertexTangents(int mapId) {
        return vertexTangents [mapId];
    }
    
    public ScalarfBuffer getVertexTangentCrossFactors(int mapId) {
        return vertexTangentCrossFactors [mapId];
    }
    
    /** @return true if tangent vectors are enabled for the specified mapId.
     */
    public boolean isTangentsEnabled(int mapId) {
        if (tangentsEnabled == null || mapId < 0 || mapId >= tangentsEnabled.length)
            return false;
        else
            return tangentsEnabled [mapId];
    }
    
    public void setTangentsEnabled(int mapId, boolean enabled) {
        if (tangentsEnabled == null || mapId < 0 || mapId >= tangentsEnabled.length)
            throw new IllegalArgumentException("Texture map id to enable tangents does not exist");
        
        tangentsEnabled [mapId] = enabled;
        
        if (enabled) {
            vertexTangents [mapId] = new Vector3fBuffer(getVertexCount());
            vertexTangentCrossFactors [mapId] = new ScalarfBuffer(getVertexCount());
            
            for (int n = 0; n < getVertexCount(); n++) {
                vertexTangents [mapId].put(n, 0, 0, 0);
                vertexTangentCrossFactors [mapId].put(n, 1);
            }
            
            for (Face face : vectorFace) {
                updateTangentVector(face.vertexId[0], face.vertexId[1], face.vertexId[2], mapId);
                updateTangentVector(face.vertexId[1], face.vertexId[2], face.vertexId[0], mapId);
                updateTangentVector(face.vertexId[2], face.vertexId[0], face.vertexId[1], mapId);
            }
            
            Vector3f tangent = new Vector3f();
            for (int n = 0; n < getVertexCount(); n++) {
                vertexTangents [mapId].get(n, tangent);
                tangent.normalize();
                vertexTangents [mapId].put(n, tangent);
            }
        } else {
            vertexTangents [mapId] = null;
            vertexTangentCrossFactors [mapId] = null;
        }
    }
    
    public void updateTangentVector(int v0, int v1, int v2, int mapId) {
        TexCoord2fBuffer vtex = textureCoordinateBuffers [mapId];
        
        // Step 1. Compute the approximate tangent vector.
        float vtex0u = vtex.getX(v0), vtex0v = vtex.getY(v0);
        float vtex1u = vtex.getX(v1), vtex1v = vtex.getY(v1);
        float vtex2u = vtex.getX(v2), vtex2v = vtex.getY(v2);
        
        float du1 = vtex1u - vtex0u;
        float dv1 = vtex1v - vtex0v;
        float du2 = vtex2u - vtex0u;
        float dv2 = vtex2v - vtex0v;
        
        float prod1 = (du1*dv2-dv1*du2);
        float prod2 = (du2*dv1-dv2*du1);
        if (Math.abs(prod1) < 0.000001 || Math.abs(prod2) < 0.000001)
            return;
        
        float x = dv2/prod1;
        float y = dv1/prod2;
        
        Vector3f vec0 = new Vector3f(), vec1 = new Vector3f(), vec2 = new Vector3f();
        vertexPositions.get(v0, vec0);
        vertexPositions.get(v1, vec1);
        vertexPositions.get(v2, vec2);
        vec1.sub(vec0); 
        vec1.scale(x);
        vec2.sub(vec0);
        vec2.scale(y);
        
        Vector3f tangent = new Vector3f();
        tangent.add(vec1, vec2);
        // Step 2. Orthonormalize the tangent.
        Vector3f norm0 = new Vector3f();
        vertexNormals.get(v0, norm0);
        
        float component = tangent.dot(norm0);
        norm0.scale(component);
        tangent.sub(norm0);
        tangent.normalize();
        
        // Step 3: Add the estimated tangent to the overall estimate for the vertex.
        
        vertexTangents[mapId].add(v0, tangent);
    }
    
    /** Add a core sub morph target to the submesh.
     */
    public int addCoreSubMorphTarget(CalCoreSubMorphTarget subMorphTarget) {
        if (coreSubMorphTargets == null)
            coreSubMorphTargets = new Vector<CalCoreSubMorphTarget>();
        
        int subMorphTargetId = coreSubMorphTargets.size();
        
        coreSubMorphTargets.add(subMorphTarget);
        
        return subMorphTargetId;
    }
    
    /** Gets a core sub morph target by id.
     */
    public CalCoreSubMorphTarget getCoreSubMorphTarget(int id) {
        return coreSubMorphTargets.get(id);
    }
    
    public Vector<CalCoreSubMorphTarget> getCoreSubMorphTargets() {
        return coreSubMorphTargets;
    }
    
    public int getCoreSubMorphTargetCount() {
        return coreSubMorphTargets == null ? 0 : coreSubMorphTargets.size();
    }
    
    
    /*****************************************************************************/
    /** Scale the Submesh.
     *
     * This function rescale all the data that are in the core submesh instance.
     *
     * @param factor A float with the scale factor
     *
     *****************************************************************************/
    
    public void scale(float factor) {
        // rescale all vertices
        vertexPositions.scale(factor, factor, factor);
        
        if(vectorSpring != null) {
            // There is a problem when we resize and that there is
            // a spring system, I was unable to solve this
            // problem, so I disable the spring system
            // if the scale are too big
            
            if (Math.abs(factor - 1.0f) > 0.10) {
                vectorSpring = null;
                vectorPhysicalProperty = null;
            }
/*
            for(vertexId = 0; vertexId < m_vectorVertex.size() ; vertexId++)
            {
                //m_vectorPhysicalProperty[vertexId].weight *= factor;
                m_vectorPhysicalProperty[vertexId].weight *= factor*factor;
                //m_vectorPhysicalProperty[vertexId].weight *= 0.5f;
            }
 
 
            int springId;
            for(springId = 0; springId < m_vectorVertex.size() ; springId++)
            {
                //m_vectorSpring[springId].idleLength*=factor;
                CalVector distance = m_vectorVertex[m_vectorSpring[springId].vertexId[1]].position - m_vectorVertex[m_vectorSpring[springId].vertexId[0]].position;
 
                m_vectorSpring[springId].idleLength = distance.length();
            }
 
 */
        }
    }
    
    
    
    /// The core submesh Influence.
//    static class Influence
//    {
//        int boneId;
//        float weight;
//    };
    
    
    /// The core submesh PhysicalProperty.
    static class PhysicalProperty {
        public float weight;
    };
    
    
    /// The core submesh Vertex.
    static class VertexInfo {
//        Influence [ ] vectorInfluence;
        public int [] influenceBoneIds;
        public float [] influenceWeights;
        public int collapseId;
        public int faceCollapseCount;
    };
    
    
    /// The core submesh Face.
    public static class Face {
        public int [] vertexId = new int [3];
    };
    
    
    /// The core submesh Spring.
    static class Spring {
        int vertexId0;
        int vertexId1;
        float springCoefficient;
        float idleLength;
    };
    
}

