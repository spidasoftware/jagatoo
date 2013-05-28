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


/** This class just holds morph data for a submesh morph target.
 */
public class CalCoreSubMorphTarget {
    Vector3fBuffer blendVertexPositions;
    Vector3fBuffer blendVertexNormals;
    
    public CalCoreSubMorphTarget() {
    }
    
    /** Creates a new instance of CalCoreSubMorphTarget.
     *  @param count the number of vertices for this target.
     */
    public CalCoreSubMorphTarget(int count) {
        reserve(count);
    }
    
    public void reserve(int count) {
        blendVertexPositions = new Vector3fBuffer(count);
        blendVertexNormals   = new Vector3fBuffer(count);
    }
    
    public int getBlendVertexCount() { return blendVertexPositions.size(); }
    
    public void setBlendVertex(int vertexId, Vector3f vertexPosition, Vector3f vertexNormal) {
        blendVertexPositions.put(vertexId, vertexPosition);
        blendVertexNormals.put(vertexId, vertexNormal);
    }
    
    public void getBlendVertexPosition(int vertexId, Vector3f x) {
        blendVertexPositions.get(vertexId, x);
    }
    
    public void getBlendVertexNormal(int vertexId, Vector3f x) {
        blendVertexNormals.get(vertexId, x);
    }
}

