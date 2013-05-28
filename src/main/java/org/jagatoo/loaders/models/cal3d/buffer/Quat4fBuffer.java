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

import org.openmali.vecmath2.Quaternion4f;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/** Quat4fBuffer provides a wrapper to FloatBuffer for Quat4f.
 *  It is also the intended extension point for high-performance implementations
 *  of vector linear algebra.
 * @author Dave Lloyd, (c) Short Fuze Ltd., 2003.
 */
public class Quat4fBuffer {
    FloatBuffer floatBuffer;
    
    public Quat4fBuffer(int length) {
        floatBuffer = ByteBuffer.allocateDirect(length*4*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
    
    public void put(int n, float x, float y, float z, float w) {
        floatBuffer.put(4*n+0, x);
        floatBuffer.put(4*n+1, y);
        floatBuffer.put(4*n+2, z);
        floatBuffer.put(4*n+3, w);
    }
    
    public void put(int n, Quaternion4f v) {
        floatBuffer.put(4*n+0, v.getA());
        floatBuffer.put(4*n+1, v.getB());
        floatBuffer.put(4*n+2, v.getC());
        floatBuffer.put(4*n+3, v.getD());
    }
    
    public void putX(int n, float x) {
        floatBuffer.put(4*n+0, x);
    }
    
    public void putY(int n, float y) {
        floatBuffer.put(4*n+1, y);
    }
    
    public void putZ(int n, float z) {
        floatBuffer.put(4*n+2, z);
    }
    
    public void putW(int n, float w) {
        floatBuffer.put(4*n+3, w);
    }
    
    public Quaternion4f get(int n) {
        return new Quaternion4f(floatBuffer.get(4*n+0), floatBuffer.get(4*n+1), floatBuffer.get(4*n+2), floatBuffer.get(4*n+3));
    }
    
    public float getX(int n) {
        return floatBuffer.get(4*n+0);
    }
    
    public float getY(int n) {
        return floatBuffer.get(4*n+1);
    }
    
    public float getZ(int n) {
        return floatBuffer.get(4*n+2);
    }
    
    public float getW(int n) {
        return floatBuffer.get(4*n+3);
    }
    
    public Buffer getBuffer() {
        return floatBuffer;
    }
}
