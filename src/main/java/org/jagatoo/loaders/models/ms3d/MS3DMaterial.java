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

import org.openmali.vecmath2.Colorf;

public class MS3DMaterial {
    
	public String name;
	public Colorf ambient;
	public Colorf diffuse;
	public Colorf specular;
	public Colorf emissive;
	public float shininess;
	public float transparency;
	public int mode;
	public int[] textureMap = new int[128];
	public int[] alpha = new int[128];
	//public CImage m_Texture;
	
	
	public MS3DMaterial(LittleEndianDataInputStream in) throws IOException {
		name = BinaryUtils.readString(in, 32);
		ambient = BinaryUtils.readColorf(in);
		diffuse = BinaryUtils.readColorf(in);
		specular = BinaryUtils.readColorf(in);
		emissive = BinaryUtils.readColorf(in);
		shininess = in.readFloat();
		transparency = in.readFloat();
		mode = in.readUnsignedByte();
		textureMap = BinaryUtils.readIntArray( in, textureMap.length );
		alpha = BinaryUtils.readIntArray( in, alpha.length );
	}

}
