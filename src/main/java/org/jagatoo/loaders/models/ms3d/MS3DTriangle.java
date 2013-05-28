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
package org.jagatoo.loaders.models.ms3d;

import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.BinaryUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Point3f;

public class MS3DTriangle {

	/*public int flags;*/
	public int[] vertIndices = new int[3];
	public Point3f[] normals = new Point3f[3];
	public float texCoords[][] = new float[2][3];
	public int smoothing;
	public int group;

	public MS3DTriangle(LittleEndianDataInputStream in) throws IOException {
		/*flags = */in.readUnsignedShort();

		for (int i = 0; i < vertIndices.length; i++) {
			vertIndices[ i ] = in.readUnsignedShort();
		}

		for (int i = 0; i < normals.length; i++) {
			normals[ i ] = BinaryUtils.readPoint3f(in);
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				texCoords[ i ][ j ] = in.readFloat();
			}
		}

		smoothing = in.readUnsignedByte();
		group = in.readUnsignedByte();
	}

}
