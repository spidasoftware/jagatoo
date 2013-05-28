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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/** IndexBuffer provides a wrapper to IntBuffer for geometry index data.
 *  TODO: Provide ShortBuffer or ByteBuffer as more compact alternatives depending on geometry size.
 * @author Dave Lloyd, (c) Short Fuze Ltd., 2003.
 */
public class IndexBuffer {
    IntBuffer intBuffer;
    ShortBuffer shortBuffer;
    ByteBuffer byteBuffer;
    int length;
    
    public IndexBuffer(int length) {
        this.length = length;
        
        intBuffer = ByteBuffer.allocateDirect(length*4).order(ByteOrder.nativeOrder()).asIntBuffer();
    }
    
    public void put(int n, int x) {
        intBuffer.put(n, x);
    }
    
    public int get(int n) {
        return intBuffer.get(n);
    }
    
    public void put(int [ ] x) {
        intBuffer.put(x);
    }
    
    public void put(int n, int [ ] x) {
        intBuffer.put(x, n, x.length);
    }
    
    /* Put a whole triangle into the index buffer.
     * Note that the index n is the nth triangle and hence sets indices 3*n to 3*n+2.
     * Unaligned triangles do not make sense in OpenGL.
     */
    public void putTri(int n, int a, int b, int c) {
        intBuffer.put(3*n+0, a);
        intBuffer.put(3*n+1, b);
        intBuffer.put(3*n+2, c);
    }
    
    /* Put a whole quad into the index buffer.
     * Note that the index n is the nth quad and hence sets indices 4*n to 4*n+3.
     * Unaligned quads do not make sense in OpenGL.
     */
    public void putQuad(int n, int a, int b, int c, int d) {
        intBuffer.put(4*n+0, a);
        intBuffer.put(4*n+1, b);
        intBuffer.put(4*n+2, c);
        intBuffer.put(4*n+3, d);
    }
    
    public int size() {
        return length;
    }
    
    public IntBuffer getBuffer() {
        return intBuffer;
    }
}
