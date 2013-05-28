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

import org.openmali.vecmath2.Vector2f;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/** Vector2fBuffer provides a wrapper to FloatBuffer for Vector2f.
 *  It is also the intended extension point for high-performance implementations
 *  of vector linear algebra.
 * @author Dave Lloyd, (c) Short Fuze Ltd., 2003.
 */
public class Vector2fBuffer {
    FloatBuffer floatBuffer;
    
    public Vector2fBuffer(int length) {
        floatBuffer = ByteBuffer.allocateDirect(length*4*2).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }
    
    public void put(int n, float x, float y) {
        floatBuffer.put(2*n+0, x);
        floatBuffer.put(2*n+1, y);
    }
    
    public void put(int n, Vector2f v) {
        floatBuffer.put(2*n+0, v.getX());
        floatBuffer.put(2*n+1, v.getY());
    }
    
    public void putX(int n, float x) {
        floatBuffer.put(2*n+0, x);
    }
    
    public void putY(int n, float y) {
        floatBuffer.put(2*n+1, y);
    }
    
    public Vector2f get(int n) {
        return new Vector2f(floatBuffer.get(2*n+0), floatBuffer.get(2*n+1));
    }
    
    public float getX(int n) {
        return floatBuffer.get(2*n+0);
    }
    
    public float getY(int n) {
        return floatBuffer.get(2*n+1);
    }
    
    public FloatBuffer getBuffer() {
        return floatBuffer;
    }
}
