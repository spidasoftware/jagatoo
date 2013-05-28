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

import org.openmali.FastMath;
import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.util.MatrixUtils;

public class RotationUtils
{
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * 
     * @param rotX
     *            Rotation about the X axis, in degrees
     * @param rotY
     *            Rotation about the Y axis, in degrees
     * @param rotZ
     *            Rotation about the Z axis, in degrees
     * 
     * @return The Quaternion representing the same rotation.
     * 
     * author Martin Baker (euclideanspace.com)
     */
    public static Quaternion4f toQuaternion( float rotX, float rotY, float rotZ )
    {
        float x = FastMath.toRad( rotX );
        float y = FastMath.toRad( rotY );
        float z = FastMath.toRad( rotZ );
        Matrix3f matrix = MatrixUtils.eulerToMatrix3f( x, y, z );
        Quaternion4f quat = new Quaternion4f();
        quat.set( matrix );
        quat.normalize();
        
        return ( quat );
    }
    
    /**
     * Convert Euler angles (in degrees) to a (normalized) Quaternion.
     * 
     * @param tuple
     * 
     * @return teh Quaternion
     */
    public static Quaternion4f toQuaternion( Tuple3f tuple )
    {
        return ( toQuaternion( tuple.getX(), tuple.getY(), tuple.getZ() ) );
    }
    
    /**
     * Convert a Quaternion to Euler angles (in degrees) Note : the Quaternion
     * can be non-normalized.
     * 
     * @param quaternion
     *            The quaternion
     * 
     * @return The euler angles (in degrees)
     * 
     * author Martin Baker (euclideanspace.com)
     */
    public static Tuple3f toEuler( Quaternion4f quaternion )
    {
        Matrix3f matrix = new Matrix3f();
        matrix.set( quaternion );
        Tuple3f euler = MatrixUtils.matrixToEuler( matrix );
        euler.setX( FastMath.toDeg( euler.getX() ) );
        euler.setY( FastMath.toDeg( euler.getY() ) );
        euler.setZ( FastMath.toDeg( euler.getZ() ) );
        
        return ( euler );
    }
}
