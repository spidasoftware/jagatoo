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


//****************************************************************************//
// Class declaration                                                          //
//****************************************************************************//

/*****************************************************************************/
/** The spring system class.
 *****************************************************************************/
public class CalSpringSystem {
    protected CalModel model;
    protected Vector3f gravity = new Vector3f(0, 0, -9.81f);
    protected Vector3f force = new Vector3f(0, 0.05f, 0);
    protected boolean collisionEnabled = false;
    
    public static final int ITERATION_COUNT = 2;
    
    /*****************************************************************************/
    /** Constructs the spring system instance.
     *
     * This function is the default constructor of the spring system instance.
     * @param model the model that should be managed with this
     *               spring system instance.
     *****************************************************************************/
    
    public CalSpringSystem(CalModel model) {
        this.model = model;
        
        CalCoreModel coreModel = model.getCoreModel();
        if(coreModel == null)
            throw new IllegalArgumentException();
    }
    
    /*****************************************************************************/
    /** Calculates the forces on each unbound vertex.
     *
     * This function calculates the forces on each unbound vertex of a specific
     * submesh.
     *
     * @param submesh A pointer to the submesh from which the forces should be
     *                 calculated.
     * @param deltaTime The elapsed time in seconds since the last calculation.
     *****************************************************************************/
    
    public void calculateForces(CalSubmesh submesh, float deltaTime) {
        // get the physical property vector of the submesh
        CalSubmesh.PhysicalProperty [ ] vectorPhysicalProperty = submesh.getVectorPhysicalProperty();
        
        // get the physical property vector of the core submesh
        float [ ] vectorCorePhysicalProperty = submesh.getCoreSubmesh().getVectorPhysicalProperty();
        
        // loop through all the vertices
        Vector3f f = new Vector3f();
        for(int vertexId = 0; vertexId < vectorPhysicalProperty.length; vertexId++) {
            // get the physical property of the vertex
            CalSubmesh.PhysicalProperty physicalProperty = vectorPhysicalProperty[vertexId];
            
            // get the physical property of the core vertex
            float corePhysicalProperty = vectorCorePhysicalProperty[vertexId];
            
            // only take vertices with a weight > 0 into account
            if(corePhysicalProperty > 0.0f) {
                f.set(gravity);
                f.scale(corePhysicalProperty);
                f.add(force);
                physicalProperty.force.set(f);
            }
        }
    }
    
    
    /*****************************************************************************/
    /** Calculates the vertices influenced by the spring system instance.
     *
     * This function calculates the vertices influenced by the spring system
     * instance.
     *
     * @param submesh A pointer to the submesh from which the vertices should be
     *                 calculated.
     * @param deltaTime The elapsed time in seconds since the last calculation.
     *****************************************************************************/
    
    public void calculateVertices(CalSubmesh submesh, float deltaTime) {
        // get the vertex vector of the submesh
        Vector3fBuffer vertexPositions = submesh.getVertexPositions();
        
        // get the vertex vector of the submesh
        CalSubmesh.PhysicalProperty [ ] vectorPhysicalProperty = submesh.getVectorPhysicalProperty();
        
        // get the physical property vector of the core submesh
        float [ ] vectorCorePhysicalProperty = submesh.getCoreSubmesh().getVectorPhysicalProperty();
        
        // loop through all the vertices
        // temps for loop
        Vector3f vertex = new Vector3f();
        Vector3f position = new Vector3f();
        Vector3f delta = new Vector3f();
        for(int vertexId = 0; vertexId < vectorPhysicalProperty.length; vertexId++) {
            // get the physical property of the vertex
            CalSubmesh.PhysicalProperty physicalProperty = vectorPhysicalProperty[vertexId];
            
            // get the physical property of the core vertex
            float corePhysicalProperty = vectorCorePhysicalProperty[vertexId];
            
            // store current position for later use
            position.set(physicalProperty.position);
            
            // only take vertices with a weight > 0 into account
            if(corePhysicalProperty > 0.0f) {
                // do the Verlet step
                delta.sub(position, physicalProperty.positionOld);
                delta.scale(0.99f);
                physicalProperty.position.add(delta);
                delta.set(physicalProperty.force);
                delta.scale(1.0f / corePhysicalProperty * deltaTime * deltaTime);
                physicalProperty.position.add(delta);
                
//                if(m_collision)
//                {
//                    std::vector<CalBone *> &m_vectorbone =  pSkeleton->getVectorBone ();
//
//                    int boneId;
//                    for(boneId=0; boneId < m_vectorbone.size (); boneId++)
//                    {
//                        CalBoundingBox p = m_vectorbone[boneId]->getBoundingBox ();
//                        bool in=true;
//                        float min=1e10;
//                        int index=-1;
//
//                        int faceId;
//                        for(faceId=0; faceId < 6 ; faceId++)
//                        {
//                            if(p.plane[faceId].eval (physicalProperty.position)<=0)
//                            {
//                                in=false;
//                            }
//                            else
//                            {
//                                float dist=p.plane[faceId].dist (physicalProperty.position);
//                                if(dist<min)
//                                {
//                                    index=faceId;
//                                    min=dist;
//                                }
//                            }
//                        }
//
//                        if(in && index!=-1)
//                        {
//                            CalVector normal = CalVector (p.plane[index].a,p.plane[index].b,normal.z = p.plane[index].c);
//                            normal.normalize ();
//                            physicalProperty.position = physicalProperty.position - min*normal;
//                        }
//
//                        in=true;
//
//                        for(faceId=0; faceId < 6 ; faceId++)
//                        {
//                            if(p.plane[faceId].eval (physicalProperty.position) < 0 )
//                            {
//                                in=false;
//                            }
//                        }
//                        if(in)
//                        {
//                            physicalProperty.position = vectorVertex[vertexId];
//                        }
//                    }
//                }
            } else {
                vertexPositions.get(vertexId, vertex);
                
                physicalProperty.position.set(vertex);
            }
            
            // make the current position the old one
            physicalProperty.positionOld.set(position);
            
            // set the new position of the vertex
            vertexPositions.put(vertexId, physicalProperty.position);
            
            // clear the accumulated force on the vertex
            physicalProperty.force.set(0.0f, 0.0f, 0.0f);
        }
        
        // get the spring vector of the core submesh
        CalCoreSubmesh.Spring [ ] vectorSpring = submesh.getCoreSubmesh().getVectorSpring();
        
        // iterate a few times to relax the constraints
        // loop temporaries
        Vector3f springVertex0 = new Vector3f();
        Vector3f springVertex1 = new Vector3f();
        Vector3f distance = new Vector3f();
        Vector3f tmp = new Vector3f();
        for(int iterationCount = 0; iterationCount < ITERATION_COUNT; iterationCount++) {
            // loop through all the springs
            for(int iteratorSpring = 0; iteratorSpring != vectorSpring.length; ++iteratorSpring) {
                // get the spring
                CalCoreSubmesh.Spring spring = vectorSpring [iteratorSpring];
                
                vertexPositions.get(spring.vertexId0, springVertex0);
                vertexPositions.get(spring.vertexId1, springVertex1);
                
                // compute the difference between the two spring vertices
                distance.sub(springVertex1, springVertex0);
                
                // get the current length of the spring
                float length = distance.length();
                
                if(length > 0.0f) {
                    float factor0 = (length - spring.idleLength) / length;
                    float factor1 = factor0;
                    
                    if(vectorCorePhysicalProperty[spring.vertexId0] > 0.0f) {
                        factor0 /= 2.0f;
                        factor1 /= 2.0f;
                    } else {
                        factor0 = 0.0f;
                    }
                    
                    if(vectorCorePhysicalProperty[spring.vertexId1] <= 0.0f) {
                        factor0 *= 2.0f;
                        factor1 = 0.0f;
                    }
                    
                    tmp.set(distance); 
                    tmp.scale(factor0);
                    springVertex0.add(tmp);
                    vectorPhysicalProperty[spring.vertexId0].position.set(springVertex0);
                    
                    tmp.set(distance); 
                    tmp.scale(factor1);
                    springVertex1.sub(tmp);
                    vectorPhysicalProperty[spring.vertexId1].position.set(springVertex1);
                    
                    vertexPositions.put(spring.vertexId0, springVertex0);
                    vertexPositions.put(spring.vertexId1, springVertex1);
                }
            }
        }
    }
    
    
    
    /*****************************************************************************/
    /** Updates all the spring systems in the attached meshes.
     *
     * This functon updates all the spring systems in the attached meshes.
     *****************************************************************************/
    
    public void update(float deltaTime) {
        // loop through all the attached meshes
        for (CalMesh mesh: model.getMeshes()) {
            // loop through all the submeshes of the mesh
            for (CalSubmesh submesh: mesh.getSubmeshes()) {
                // check if the submesh contains a spring system
                if (submesh.getCoreSubmesh().getSpringCount() > 0) {
                    // calculate the new forces on each unbound vertex
                    calculateForces(submesh, deltaTime);
                    
                    // calculate the vertices influenced by the spring system
                    calculateVertices(submesh, deltaTime);
                }
            }
        }
    }
    
    public Vector3f getGravity() {
        return gravity;
    }
    
    public void setGravity(Vector3f gravity) {
        this.gravity = gravity;
    }
    
    public Vector3f getForce() {
        return force;
    }
    
    public void setForce(Vector3f force) {
        this.force = force;
    }
    
    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }
    
    public void setCollisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }
}

