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

import org.jagatoo.loaders.models.cal3d.buffer.Vector3fBuffer;

import java.util.Vector;


/*******************************************************************************
 * The physique class. This synchronizes on the model for which it is providing
 * the physique.
 ******************************************************************************/
public class CalPhysique
{
    protected CalModel model;
    
    protected boolean  normalize = true;
    
    /**
     * Constructs the physique instance.
     * 
     * This function is the default constructor of the physique instance.
     * 
     * @param model
     *            A pointer to the model that should be managed with this
     *            physique instance.
     */
    
    public CalPhysique( CalModel model )
    {
        this.model = model;
        
        if( model.getCoreModel() == null ) throw new IllegalArgumentException();
    }
    
    /**
     * Sets the normalization flag to true or false.
     * 
     * This function sets the normalization flag on or off. If off, the normals
     * calculated by Cal3D will not be normalized. Instead, this transform is
     * left up to the user.
     */
    public void setNormalization( boolean normalize )
    {
        this.normalize = normalize;
    }
    
    /**
     * Calculates the transformed vertex data.
     * 
     * This function calculates and returns the transformed vertex data of a
     * specific submesh.
     * 
     * @param submesh
     *            A pointer to the submesh from which the vertex data should be
     *            calculated and returned.
     * @param vertexBuffer
     *            A pointer to the user-provided buffer where the vertex data is
     *            written to.
     * 
     * @return The number of vertices written to the buffer.
     */
    
    public int calculateVertices( CalSubmesh submesh, Vector3fBuffer vertexBuffer )
    {
        synchronized( model )
        {
            // get bone vector of the skeleton
            CalBone[] vectorBone = model.getSkeleton().getBones();
            
            // get vertex vector of the core submesh
            Vector3fBuffer vertexPositions = submesh.getCoreSubmesh().getVertexPositions();
            CalCoreSubmesh.VertexInfo[] vectorVertex = submesh.getCoreSubmesh().getVectorVertexInfo();
            
            // get physical property vector of the core submesh
            float[] vectorPhysicalProperty = submesh.getCoreSubmesh().getVectorPhysicalProperty();
            
            // get the number of vertices
            int vertexCount = submesh.getVertexCount();
            
            // get the sub morph target vector from the core sub mesh
            Vector<CalCoreSubMorphTarget> vectorSubMorphTarget = submesh.getCoreSubmesh().getCoreSubMorphTargets();
            
            // calculate the base weight
            float baseWeight = submesh.getBaseWeight();
            
            // get the number of morph targets
            int morphTargetCount = submesh.getMorphTargetWeightCount();
            
            // calculate all submesh vertices
            // loop temps
            Vector3f v = new Vector3f(), position = new Vector3f();
            for( int vertexId = 0; vertexId < vertexCount; vertexId ++ )
            {
                // get the vertex
                CalCoreSubmesh.VertexInfo vertex = vectorVertex[ vertexId ];
                vertexPositions.get( vertexId, v );
                
                // blend the morph targets
                if( baseWeight == 1.0f )
                {
                    position.set( v );;
                }
                else
                {
                    position.setX( baseWeight * v.getX() );
                    position.setY( baseWeight * v.getY() );
                    position.setZ( baseWeight * v.getZ() );
                    for( int morphTargetId = 0; morphTargetId < morphTargetCount; ++ morphTargetId )
                    {
                        vectorSubMorphTarget.get( morphTargetId ).getBlendVertexPosition( vertexId, v );
                        float currentWeight = submesh.getMorphTargetWeight( morphTargetId );
                        position.addX( currentWeight * v.getX() );
                        position.addY( currentWeight * v.getY() );
                        position.addZ( currentWeight * v.getZ() );
                    }
                }
                
                // initialize vertex
                float x = 0, y = 0, z = 0;
                
                // blend together all vertex influences
                int influenceCount = vertex.influenceBoneIds.length;
                if( influenceCount == 0 )
                {
                    x = position.getX();
                    y = position.getY();
                    z = position.getZ();
                }
                else
                {
                    for( int influenceId = 0; influenceId < influenceCount; influenceId ++ )
                    {
                        // get the influence
                        int boneId = vertex.influenceBoneIds[ influenceId ];
                        float weight = vertex.influenceWeights[ influenceId ];
                        
                        // get the bone of the influence vertex
                        CalBone bone = vectorBone[ boneId ];
                        
                        // transform vertex with current state of the bone
                        v.set( position );
                        CalCoreBone.transform( v, bone.getRotationBoneSpace() );
                        v.add( bone.getTranslationBoneSpace() );
                        
                        x += weight * v.getX();
                        y += weight * v.getY();
                        z += weight * v.getZ();
                    }
                }
                
                // save vertex position
                if( submesh.getCoreSubmesh().getSpringCount() > 0 && submesh.hasInternalData() )
                {
                    // get the pgysical property of the vertex
                    float physicalProperty = vectorPhysicalProperty[ vertexId ];
                    
                    // assign new vertex position if there is no vertex weight
                    if( physicalProperty == 0.0f )
                    {
                        vertexBuffer.put( vertexId, x, y, z );
                    }
                }
                else
                {
                    vertexBuffer.put( vertexId, x, y, z );
                }
            }
            return vertexCount;
        }
    }
    
    /**
     * Calculates the transformed normal data.
     * 
     * This function calculates and returns the transformed normal data of a
     * specific submesh.
     * 
     * @param submesh
     *            A pointer to the submesh from which the normal data should be
     *            calculated and returned.
     * @param normalBuffer
     *            A pointer to the user-provided buffer where the normal data is
     *            written to.
     * 
     * @return The number of normals written to the buffer.
     */
    
    public int calculateNormals( CalSubmesh submesh, Vector3fBuffer normalBuffer )
    {
        synchronized( model )
        {
            // get bone vector of the skeleton
            CalBone[] vectorBone = model.getSkeleton().getBones();
            
            // get vertex vector of the submesh
            Vector3fBuffer vertexNormals = submesh.getCoreSubmesh().getVertexNormals();
            CalCoreSubmesh.VertexInfo[] vectorVertex = submesh.getCoreSubmesh().getVectorVertexInfo();
            
            // get the number of vertices
            int vertexCount = submesh.getVertexCount();
            
            // get the sub morph target vector from the core sub mesh
            Vector<CalCoreSubMorphTarget> vectorSubMorphTarget = submesh.getCoreSubmesh().getCoreSubMorphTargets();
            
            // calculate the base weight
            float baseWeight = submesh.getBaseWeight();
            
            // get the number of morph targets
            int morphTargetCount = submesh.getMorphTargetWeightCount();
            
            // calculate normal for all submesh vertices
            // loop temps
            Vector3f v = new Vector3f(), normal = new Vector3f();
            for( int vertexId = 0; vertexId < vertexCount; vertexId ++ )
            {
                // get the vertex
                CalCoreSubmesh.VertexInfo vertex = vectorVertex[ vertexId ];
                
                vertexNormals.get( vertexId, v );
                
                // blend the morph targets
                if( baseWeight == 1.0f )
                {
                    normal.set( v );
                }
                else
                {
                    normal.setX( baseWeight * v.getX() );
                    normal.setY( baseWeight * v.getY() );
                    normal.setZ( baseWeight * v.getZ() );
                    for( int morphTargetId = 0; morphTargetId < morphTargetCount; morphTargetId ++ )
                    {
                        vectorSubMorphTarget.get( morphTargetId ).getBlendVertexNormal( vertexId, v );
                        float currentWeight = submesh.getMorphTargetWeight( morphTargetId );
                        normal.addX( currentWeight * v.getX() );
                        normal.addY( currentWeight * v.getY() );
                        normal.addZ( currentWeight * v.getZ() );
                    }
                }
                
                // initialize normal
                float nx = 0, ny = 0, nz = 0;
                
                // blend together all vertex influences
                int influenceCount = vertex.influenceBoneIds.length;
                for( int influenceId = 0; influenceId < influenceCount; influenceId ++ )
                {
                    // get the influence
                    int boneId = vertex.influenceBoneIds[ influenceId ];
                    float weight = vertex.influenceWeights[ influenceId ];
                    
                    // get the bone of the influence vertex
                    CalBone bone = vectorBone[ boneId ];
                    
                    // transform normal with current state of the bone
                    v.set( normal );
                    CalCoreBone.transform( v, bone.getRotationBoneSpace() );
                    
                    nx += weight * v.getX();
                    ny += weight * v.getY();
                    nz += weight * v.getZ();
                }
                
                // re-normalize normal if desired
                if( normalize )
                {
                    float scale = 1.0f / ( float ) Math.sqrt( nx * nx + ny * ny + nz * nz );
                    
                    normalBuffer.put( vertexId, nx * scale, ny * scale, nz * scale );
                }
                else
                {
                    normalBuffer.put( vertexId, nx, ny, nz );
                }
            }
            
            return vertexCount;
        }
    }
    
    /**
     * Calculates the transformed tangent data.
     * 
     * This function calculates and returns the transformed tangent data of a
     * specific submesh.
     * 
     * @param submesh
     *            A pointer to the submesh from which the tangent data should be
     *            calculated and returned.
     * @param mapId
     *            The texture layer id for the tangents
     * @param tangentBuffer
     *            A pointer to the user-provided buffer where the tangent data
     *            is written to.
     * 
     * @return The number of tangents written to the buffer.
     */
    @SuppressWarnings( "unused" )
    public int calculateTangents( CalSubmesh submesh, int mapId, Vector3fBuffer tangentBuffer )
    {
        synchronized( model )
        {
            // get bone vector of the skeleton
            CalBone[] vectorBone = model.getSkeleton().getBones();
            
            // get vertex vector of the submesh
            CalCoreSubmesh.VertexInfo[] vectorVertex = submesh.getCoreSubmesh().getVectorVertexInfo();
            Vector3fBuffer vertexTangents = submesh.getCoreSubmesh().getVertexTangents( mapId );
            
            // get the number of vertices
            int vertexCount = submesh.getVertexCount();
            
            // get the sub morph target vector from the core sub mesh
            Vector<CalCoreSubMorphTarget> vectorSubMorphTarget = submesh.getCoreSubmesh().getCoreSubMorphTargets();
            
            // calculate the base weight
            float baseWeight = submesh.getBaseWeight();
            
            // get the number of morph targets
            int morphTargetCount = submesh.getMorphTargetWeightCount();
            
            // calculate tangent for all submesh vertices
            // loop temps
            Vector3f v = new Vector3f(), tangent = new Vector3f();
            for( int vertexId = 0; vertexId < vertexCount; vertexId ++ )
            {
                // get the vertex
                CalCoreSubmesh.VertexInfo vertex = vectorVertex[ vertexId ];
                
                vertexTangents.get( vertexId, tangent );
                
                // we don't blend morph targets - why?
                /*
                 * if(baseWeight == 1.0f) { tangent.x = v.x; tangent.y = v.y;
                 * tangent.z = v.z; } else { tangent.x = baseWeight*v.x;
                 * tangent.y = baseWeight*v.y; tangent.z = baseWeight*v.z; for
                 * (int morphTargetId=0; morphTargetId < morphTargetCount;
                 * morphTargetId++) { vectorSubMorphTarget.get
                 * (morphTargetId).getBlendVertexT(vertexId, v); float
                 * currentWeight = submesh.getMorphTargetWeight (morphTargetId);
                 * tangent.x += currentWeight*v.x; tangent.y +=
                 * currentWeight*v.y; tangent.z += currentWeight*v.z; } }
                 */

                // initialize normal
                float tx = 0, ty = 0, tz = 0;
                
                // blend together all vertex influences
                int influenceCount = vertex.influenceBoneIds.length;
                for( int influenceId = 0; influenceId < influenceCount; influenceId ++ )
                {
                    // get the influence
                    int boneId = vertex.influenceBoneIds[ influenceId ];
                    float weight = vertex.influenceWeights[ influenceId ];
                    
                    // get the bone of the influence vertex
                    CalBone bone = vectorBone[ boneId ];
                    
                    // transform normal with current state of the bone
                    v.set( tangent );
                    CalCoreBone.transform( v, bone.getRotationBoneSpace() );
                    
                    tx += weight * v.getX();
                    ty += weight * v.getY();
                    tz += weight * v.getZ();
                }
                
                // re-normalize tangent if desired
                if( normalize )
                {
                    float scale = 1.0f / ( float ) Math.sqrt( tx * tx + ty * ty + tz * tz );
                    
                    tangentBuffer.put( vertexId, tx * scale, ty * scale, tz * scale );
                }
                else
                {
                    tangentBuffer.put( vertexId, tx, ty, tz );
                }
            }
            
            return vertexCount;
        }
    }
    
    /**
     * Updates all the internally handled attached meshes.
     * 
     * This function updates all the attached meshes of the model that are
     * handled internally.
     */
    
    public void update( )
    {
        synchronized( model )
        {
            // loop through all the attached meshes
            for (CalMesh mesh: model.getMeshes())
            {
                // loop through all the submeshes of the mesh
                for (CalSubmesh submesh: mesh.getSubmeshes())
                {
                    // check if the submesh handles vertex data internally
                    if ( submesh.hasInternalData() )
                    {
                        // calculate the transformed vertices and store them in
                        // the submesh
                        Vector3fBuffer vectorVertex = submesh.getVertexPositions();
                        calculateVertices( submesh, vectorVertex );
                        
                        // calculate the transformed normals and store them in
                        // the submesh
                        Vector3fBuffer vectorNormal = submesh.getVertexNormals();
                        calculateNormals( submesh, vectorNormal );
                        
                        for( int mapId = 0; mapId < submesh.getVertexTangentsMapCount(); mapId ++ )
                        {
                            if( submesh.isTangentsEnabled( mapId ) )
                            {
                                calculateTangents( submesh, mapId, submesh.getVertexTangents( mapId ) );
                            }
                        }
                    }
                }
            }
        }
    }
}
