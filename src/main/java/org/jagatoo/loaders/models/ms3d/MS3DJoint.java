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
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;

public class MS3DJoint {

	/*public int flags;*/
	public String name;
	public String parent;
	public Point3f rotation;
	public Point3f position;
	public int numRotFrames;
	public int numTransFrames;

	public MS3DKeyFrame[] rotKeyFrames;
	public MS3DKeyFrame[] transKeyFrames;


	//logic data
	public int parentIndex;
	public Matrix4f matLocal;
	public Matrix4f matAbs;
	public Matrix4f matFinal;
	public  int curRotFrame;
	public  int curTransFrame;

	public MS3DJoint(LittleEndianDataInputStream in) throws IOException {
		curRotFrame = 0;
		curTransFrame = 0;
		parentIndex = -1;
		matLocal = new Matrix4f();
		matAbs = new Matrix4f();
		matFinal = new Matrix4f();

		/*flags = */in.readByte();
		name = BinaryUtils.readString(in, 32);
		parent = BinaryUtils.readString(in, 32);
		rotation = BinaryUtils.readPoint3f( in );
		position = BinaryUtils.readPoint3f( in );
		numRotFrames = in.readUnsignedShort();
		numTransFrames = in.readUnsignedShort();

		rotKeyFrames = new MS3DKeyFrame[ numRotFrames ];
		for (int i = 0; i < rotKeyFrames.length; i++) {
			rotKeyFrames[ i ] = new MS3DKeyFrame( in );
		}

		transKeyFrames = new MS3DKeyFrame[ numTransFrames ];
		for (int i = 0; i < transKeyFrames.length; i++) {
			transKeyFrames[ i ] = new MS3DKeyFrame( in );
		}
	}

	/**
	 * Selects the current transformation key frame, based on the current time
	 * @param currentTime beetween 0 and the end of the animation, in seconds
	 * @return frame index selected
	 */
	public int selectCurrentTransFrame(float currentTime) {
		this.curTransFrame = searchNextFrame( transKeyFrames, currentTime );
		return this.curTransFrame;
	}

	/**
	 * Selects the current rotation key frame, based on the current time
	 * @param currentTime beetween 0 and the end of the animation, in seconds
	 * @return frame index selected
	 */
	public int selectCurrentRotFrame(float currentTime) {
		this.curRotFrame = searchNextFrame( rotKeyFrames, currentTime );
		return this.curRotFrame;
	}

	/**
	 * Searchs the next key frame according to the current time
	 * @param frames
	 * @param currentTime in seconds
	 * @return selected key frame index
	 */
	private int searchNextFrame( MS3DKeyFrame[] frames, float currentTime ) {
		int frame = 0;
		while( frame < frames.length && frames[ frame ].time < currentTime ) {
			frame++;
		}
		return frame;
	}

	/**
	 * Says if the joint has at least one key frame to animate
	 * @return true/false
	 */
	public boolean hasKeyFrames() {
		return this.numRotFrames != 0 || this.numTransFrames != 0;
	}

}









