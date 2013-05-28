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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.jagatoo.loaders.models.ms3d.utils.RotationUtils;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.util.Interpolation;

public class MS3DPrototypeModel {

	private final static String STR_HEADER_NAME = "MS3D000000";
	private final static byte[] HEADER_NAME = STR_HEADER_NAME.getBytes();

	//data
	private int numVerts;
	private int numTriangles;
	private int numMeshes;
	private int numMaterials;
	private int numJoints;

	private MS3DVertex[] vertices;
	private MS3DTriangle[] triangles;
	private MS3DMesh[] meshes;
	private MS3DMaterial[] materials;
	private MS3DJoint[] joints;

	//aux
	private Point3f pointAux = new Point3f();


	/**
	 * Creates a new MS3D Model parsing the .ms3d file
	 * @param bin
	 * @throws IOException
	 */
	public MS3DPrototypeModel(BufferedInputStream bin) throws IOException {
		DataInputStream in = new DataInputStream(bin);
		readFile(in);
		in.close();
	}



	/**
	 * Parse de .ms3d file and fill all the logic data structures
	 * @param din
	 * @throws IOException
	 */
	private void readFile(DataInputStream din) throws IOException {
		LittleEndianDataInputStream in = new LittleEndianDataInputStream(din);

		//get header name and compare
		byte[] auxArray = new byte[10];
		in.read(auxArray, 0, 10);
		if( !compareHeaderName( auxArray ) ) {
			throw new RuntimeException( "Header name incorrect" );
		}

		//version
		int version = in.readInt();
		if( version != 3 && version != 4 ) {
			throw new RuntimeException( "Version incorrect" );
		}

		//read vertices
		numVerts = in.readUnsignedShort();
		vertices = new MS3DVertex[ numVerts ];
		for (int i = 0; i < vertices.length; i++) {
			vertices[ i ] = new MS3DVertex( in );
		}

		//read triangules
		numTriangles = in.readUnsignedShort();
		triangles = new MS3DTriangle[ numTriangles ];
		for (int i = 0; i < triangles.length; i++) {
			triangles[ i ] = new MS3DTriangle( in );
		}

		//read mesh groups
		numMeshes = in.readUnsignedShort();
		meshes = new MS3DMesh[ numMeshes ];
		for (int i = 0; i < meshes.length; i++) {
			meshes[ i ] = new MS3DMesh( in );
		}

		//read materials
		numMaterials = in.readUnsignedShort();
		materials = new MS3DMaterial[ numMaterials ];
		for (int i = 0; i < materials.length; i++) {
			materials[ i ] = new MS3DMaterial( in );
		}

		//skip data
		auxArray = new byte[ 4 + 8 ];
		in.read(auxArray, 0, auxArray.length );

		//read joints and animations
		numJoints = in.readUnsignedShort();
		joints = new MS3DJoint[ numJoints ];
		for (int i = 0; i < joints.length; i++) {
			joints[ i ] = new MS3DJoint( in );
		}

		//set parent for joints
		setParentForJoints();

		setupJoints();


	}

	/**
	 * Sets up the joints to ther begginnig position
	 */
	private void setupJoints() {

		//go through each joint
		MS3DJoint joint;
		for (int i = 0; i < joints.length; i++) {
			joint = joints[ i ];

			joint.matLocal.setRotation( joint.rotation );
			joint.matLocal.setTranslation( joint.position );

			//with parent
			if( joint.parentIndex != -1 ) {
				joint.matAbs.set( joints[ joint.parentIndex ].matAbs );
				joint.matAbs.mul( joint.matLocal );

			//without parent
			} else {
				joint.matAbs.set( joint.matLocal );
			}

			joint.matFinal.set( joint.matAbs );
		}

		//go through each vertex
		MS3DVertex vertex;
		for (int i = 0; i < vertices.length; i++) {
			vertex = vertices[ i ];

			//without bone
			if( vertex.bone == -1 ) {
				continue;
			}

			Matrix4f mat = joints[ vertex.bone ].matFinal;

			//inv trans
			pointAux.set( vertex.vert );
			pointAux.negate();

			//FIXME el setTranslation() se implenta distinto, ver por que?
			mat.setTranslation( pointAux );

			//inv rot
			mat.setInvRotation( pointAux );
		}

		//go through the normals and transform them
		MS3DTriangle triangle;
		for (int i = 0; i < triangles.length; i++) {
			triangle = triangles[ i ];

			//loop through each vertex
			for (int j = 0; j < 3; j++) {
				vertex = vertices[ triangle.vertIndices[ j ] ];

				//without bone
				if( vertex.bone == -1 ) {
					continue;
				}
				joint = joints[ vertex.bone ];

				//transform the normal
				joint.matFinal.setInvRotation( pointAux );
			}
		}
	}



	/**
	 * Search and set the parent for each joint
	 */
	private void setParentForJoints() {
		for (int i = 0; i < joints.length; i++) {
			if( joints[ i ].parent != null ) {
				for (int j = 0; j < joints.length; j++) {
					if( joints[ j ].name.equals( joints[ i ].parent ) ) {
						joints[ i ].parentIndex = j;
					}
				}
			} else {
				joints[ i ].parentIndex = -1;
			}
		}
	}


	/**
	 * Compare the header name of the .ms3d file is correct to MILSHAPE file spec.
	 * @param headerName
	 * @return
	 */
	private boolean compareHeaderName(byte[] headerName) {
		for (int i = 0; i < headerName.length; i++) {
			if( headerName[ i ] != HEADER_NAME[ i ] ) {
				return false;
			}
		}
		return true;
	}












	/**
	 * Last animated time
	 */
	private float lastTime = -1;

	/**
	 * Time when the animation began
	 */
	private float startTime = -1;

	/**
	 * Aux for rotation and translation speed
	 */
	private Point3f speed = new Point3f();



	public void initAnimation( float currentTime ) {
		startTime = currentTime;
		lastTime = startTime;
	}

	/**
	 *
	 * @param currentTime in seconds
	 */
	public void animate( float currentTime ) {


		//deltaT to animate
		float deltaT = currentTime - lastTime;
		lastTime = currentTime;


		//loop through all joints
		MS3DJoint joint;
		for (int i = 0; i < joints.length; i++) {
			joint = joints[ i ];

			//if there is no keyframes, don�t do any transformations
			if( !joint.hasKeyFrames() ) {
				joint.matFinal.set( joint.matAbs );
				continue;
			}

			//Translation
			Point3f translation = new Point3f();
			int frame = joint.selectCurrentTransFrame( currentTime - startTime );

			//if its at the extremes
			if( frame == 0 ) {
				translation.set( joint.transKeyFrames[ frame ].param );

			} else if( frame == joint.numTransFrames ) {
				translation.set( joint.transKeyFrames[ frame - 1 ].param );


			//if its in the middle of two frames
			} else {

				MS3DKeyFrame curFrame = joint.transKeyFrames[ frame ];
				MS3DKeyFrame prevFrame = joint.transKeyFrames[ frame - 1 ];

				//time distance beetween both frames
				float timeDist = curFrame.time - prevFrame.time;

				//space distance beetween both frames
				speed.set( curFrame.param );
				speed.sub( prevFrame.param );

				//interpolation speed => distance/time
				speed.scale( 1 / timeDist );

				//interpolate translation with speed and the elapsed time beetween the last call
				speed.scale( deltaT );
				translation.set( curFrame.param );
				translation.add( speed );
			}

			//Rotation
			Matrix4f matTmp = new Matrix4f();
			frame = joint.selectCurrentRotFrame(currentTime);

			//if its at the extremes
			if( frame == 0 ) {
				matTmp.setRotation( joint.rotKeyFrames[ frame ].param );

			} else if( frame == joint.numTransFrames ) {
				matTmp.setRotation( joint.rotKeyFrames[ frame - 1 ].param );


			//if its in the middle of two frames, use quaternion NLERP operation
			//to calculate a new position
			} else {

				MS3DKeyFrame curFrame = joint.rotKeyFrames[ frame ];
				MS3DKeyFrame prevFrame = joint.rotKeyFrames[ frame - 1 ];

				//time distance beetween both frames
				float timeDist = curFrame.time - prevFrame.time;

				//interpolate distance with nlerp
				Quaternion4f quatCur = RotationUtils.toQuaternion( curFrame.param );
				Quaternion4f quatPrev = RotationUtils.toQuaternion( prevFrame.param );
				Quaternion4f quatSpeed = new Quaternion4f();
				Interpolation.nlerp(quatCur, quatPrev, timeDist, quatSpeed);

				//scale quatSpeed with elapsed time
				quatSpeed.scale( 1 / timeDist );

				//apply to matrix
				matTmp.set( quatSpeed );

			}

			//set the translation part of the matrix
			matTmp.setTranslation( translation );

			//calculate the joints final transformation
			Matrix4f matFinal =  new Matrix4f( joint.matLocal );
			matFinal.mul( matTmp );

			//if there is no parent, just use the matrix you just made
			if( joint.parentIndex == -1 ) {
				joint.matFinal.set( matFinal );

			//otherwise the final matrix is the parents final matrix * the new matrix
			} else {
				joint.matFinal.set( joints[ joint.parentIndex ].matFinal );
				joint.matFinal.mul( matFinal );
			}
		}
	}









}
