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
package org.jagatoo.loaders.models.cal3d.util;

import org.openmali.vecmath2.Quaternion4f;

/**
 *
 * @author kman
 * @author Amos Wenger (aka BlueSky)
 */
public class QuatInterpolator {
    
    final static double EPS = 0.000001;
    public final static void interpolate (Quaternion4f q,Quaternion4f q1, float alpha) {
	// From "Advanced Animation and Rendering Techniques"
	// by Watt and Watt pg. 364, function as implemented appeared to be
	// incorrect.  Fails to choose the same quaternion for the double
	// covering. Resulting in change of direction for rotations.
	// Fixed function to negate the first quaternion in the case that the
	// dot product of q1 and this is negative. Second case was not needed.
	
	double dot,s1,s2,om,sinom;
	float x = q.getA();
        float y=q.getB();
        float z=q.getC();
        float w=q.getD();
	//dot = x*q1.x + Quaternion.IDENTITY.z*q1.z + w*q1.w;
        dot = x*q1.getA() + 0f*q1.getC() + w*q1.getD();
	
	if ( dot < 0 ) {
	    // negate quaternion
	    q1.setA( -q1.getA() );  q1.setB( -q1.getB() );  q1.setC( -q1.getC() );  q1.setD( -q1.getD() );
	    dot = -dot;
	}
	
	if ( (1.0 - dot) > EPS ) {
	    om = Math.acos (dot);
	    sinom = Math.sin (om);
	    s1 = Math.sin ((1.0-alpha)*om)/sinom;
	    s2 = Math.sin ( alpha*om)/sinom;
	} else{
	    s1 = 1.0 - alpha;
	    s2 = alpha;
	}
	
	w = (float)(s1*w + s2*q1.getD());
	x = (float)(s1*x + s2*q1.getA());
	y = (float)(s1*y + s2*q1.getB());
	z = (float)(s1*z + s2*q1.getC());
    }

    
}
