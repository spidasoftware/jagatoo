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
package org.jagatoo.loaders.models.cal3d.buffer;

import org.openmali.vecmath2.Vector3f;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/** Vector3fBuffer provides a wrapper to FloatBuffer for Vector3f.
 *  It is also the intended extension point for high-performance implementations
 *  of vector linear algebra.
 * @author Dave Lloyd, (c) Short Fuze Ltd., 2003.
 */
public class Vector3fBuffer {
    public static final int STRIDE = 3;
    
    protected FloatBuffer floatBuffer;
    public int length;
    
    public Vector3fBuffer(FloatBuffer floatBuffer, int length) {
        this.floatBuffer = floatBuffer;
        this.length = length;
    }
    
    public Vector3fBuffer(int length) {
        this.length = length;
        floatBuffer = ByteBuffer.allocateDirect(length*4*STRIDE).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
    
    public void set(Vector3fBuffer v) {
        for (int n = 0; n < length*3; n++) {
            floatBuffer.put(n, v.floatBuffer.get(n));
        }
    }
    
    public void put(int n, float x, float y, float z) {
        floatBuffer.put(STRIDE*n+0, x);
        floatBuffer.put(STRIDE*n+1, y);
        floatBuffer.put(STRIDE*n+2, z);
    }
    
    public void put(int n, Vector3f v) {
        floatBuffer.put(STRIDE*n+0, v.getX());
        floatBuffer.put(STRIDE*n+1, v.getY());
        floatBuffer.put(STRIDE*n+2, v.getZ());
    }
    
    public void putX(int n, float x) {
        floatBuffer.put(STRIDE*n+0, x);
    }
    
    public void putY(int n, float y) {
        floatBuffer.put(STRIDE*n+1, y);
    }
    
    public void putZ(int n, float z) {
        floatBuffer.put(STRIDE*n+2, z);
    }
    
    public Vector3f get(int n) {
        return new Vector3f(floatBuffer.get(STRIDE*n+0), floatBuffer.get(STRIDE*n+1), floatBuffer.get(STRIDE*n+2));
    }
    
    public Vector3f get(int n, Vector3f v) {
        v.setX( floatBuffer.get(STRIDE*n+0) );
        v.setY( floatBuffer.get(STRIDE*n+1) );
        v.setZ( floatBuffer.get(STRIDE*n+2) );
        return v;
    }
    
    public float getX(int n) {
        return floatBuffer.get(STRIDE*n+0);
    }
    
    public float getY(int n) {
        return floatBuffer.get(STRIDE*n+1);
    }
    
    public float getZ(int n) {
        return floatBuffer.get(STRIDE*n+2);
    }
    
    public void add(int n, Vector3f v) {
        floatBuffer.put(STRIDE*n+0, floatBuffer.get(STRIDE*n+0) + v.getX());
        floatBuffer.put(STRIDE*n+1, floatBuffer.get(STRIDE*n+1) + v.getY());
        floatBuffer.put(STRIDE*n+2, floatBuffer.get(STRIDE*n+2) + v.getZ());
    }
    
    public void sub(int n, Vector3f v) {
        floatBuffer.put(STRIDE*n+0, floatBuffer.get(STRIDE*n+0) - v.getX());
        floatBuffer.put(STRIDE*n+1, floatBuffer.get(STRIDE*n+1) - v.getY());
        floatBuffer.put(STRIDE*n+2, floatBuffer.get(STRIDE*n+2) - v.getZ());
    }
    
    public void scale(int n, float v) {
        floatBuffer.put(STRIDE*n+0, floatBuffer.get(STRIDE*n+0) * v);
        floatBuffer.put(STRIDE*n+1, floatBuffer.get(STRIDE*n+1) * v);
        floatBuffer.put(STRIDE*n+2, floatBuffer.get(STRIDE*n+2) * v);
    }
    
    public Vector3fBuffer slice(int offset, int length) {
        floatBuffer.position(STRIDE*offset);
        FloatBuffer sliced = floatBuffer.slice();
        if (length > sliced.remaining()/STRIDE)
            throw new IllegalArgumentException("Insufficient elements for slice: "+length+" > "+sliced.remaining()/STRIDE);
        
        return new Vector3fBuffer(sliced, length);
    }
    
    public FloatBuffer getBuffer() {
        return floatBuffer;
    }
    
    /** Sets all vectors to the zero vector. */
    public void clear() {
        for (int n = 0; n < length; n++)
            floatBuffer.put(n, 0f);
    }
    
    public int size() {
        return length;
    }
    
   
    
    /** translate each vector. This is vectorisable!
     */
    public void translate(float x, float y, float z) {
        for (int n = 0; n < length; n++) {
            floatBuffer.put(STRIDE*n+0, x+floatBuffer.get(STRIDE*n+0));
            floatBuffer.put(STRIDE*n+1, y+floatBuffer.get(STRIDE*n+1));
            floatBuffer.put(STRIDE*n+2, z+floatBuffer.get(STRIDE*n+2));
        }
    }
    
    /** scale takes each vector and scales it in each dimension. This is vectorisable!
     */
    public void scale(float xs, float ys, float zs) {
        for (int n = 0; n < length; n++) {
            floatBuffer.put(STRIDE*n+0, xs*floatBuffer.get(STRIDE*n+0));
            floatBuffer.put(STRIDE*n+1, ys*floatBuffer.get(STRIDE*n+1));
            floatBuffer.put(STRIDE*n+2, zs*floatBuffer.get(STRIDE*n+2));
        }
    }
}
