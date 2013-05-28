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
package org.jagatoo.loaders.models.ms3d.utils;

import java.io.IOException;

import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Point3f;

public class BinaryUtils {

	public static String readString(LittleEndianDataInputStream in, int length)
			throws IOException {
		byte[] array = new byte[length];
		in.read(array, 0, length);

		int len = length;
		for (int i = 0; i < length; i++) {
			if (array[i] == 0) {
				len = i;
				break;
			}
		}
		if( len == 0 )
			return null;
		return new String(array, 0, len);
	}

	public static int[] readIntArray(LittleEndianDataInputStream in, int length)
			throws IOException {
		byte[] array = new byte[length];
		in.read(array, 0, length);
		int[] arrayInt = new int[length];
		for (int i = 0; i < array.length; i++) {
			arrayInt[i] = array[i];
		}
		return arrayInt;
	}

	public static Point3f readPoint3f(LittleEndianDataInputStream in)
			throws IOException {
		return new Point3f(in.readFloat(), in.readFloat(), in.readFloat());
	}

	public static Colorf readColorf(LittleEndianDataInputStream in)
			throws IOException {
		return new Colorf(in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
	}

}
