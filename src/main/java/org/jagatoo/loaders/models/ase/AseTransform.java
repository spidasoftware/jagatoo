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
/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.ase;

import org.openmali.FastMath;
import org.openmali.vecmath2.Vector3f;

/**
 * A transform node which is part of a geom object
 * 
 * @author David Yazel
 */
class AseTransform extends AseNode
{
    public String name;
    public Vector3f inheritPos = new Vector3f( 0f, 0f, 0f );
    public Vector3f inheritRot = new Vector3f( 0f, 0f, 0f );
    public Vector3f inheritScl = new Vector3f( 0f, 0f, 0f );
    public Vector3f tmRow0 = new Vector3f( 1f, 0f, 0f );
    public Vector3f tmRow1 = new Vector3f( 0f, -1f, 0f );
    public Vector3f tmRow2 = new Vector3f( 0f, 0f, 1f );
    public Vector3f tmRow3 = new Vector3f( 0f, 0f, 0f );
    public Vector3f tmPos = new Vector3f( 0f, 0f, 0f );
    public Vector3f tmRotAxis = new Vector3f( 0f, 0f, 0f );
    public float tmRotAngle = FastMath.PI;
    public Vector3f tmScale = new Vector3f( 1f, 1f, 1f );
    public Vector3f tmScaleAxis = new Vector3f( 0f, 0f, 0f );
    public float tmScaleAxisAngle = 0;
    
    public AseTransform()
    {
        properties.put( "*NODE_NAME", "name" );
        
        properties.put( "*INHERIT_POS", "inheritPos" );
        properties.put( "*INHERIT_ROT", "inheritRot" );
        properties.put( "*INHERIT_SCL", "inheritScl" );
        
        properties.put( "*TM_ROW0", "tmRow0" );
        properties.put( "*TM_ROW1", "tmRow1" );
        properties.put( "*TM_ROW2", "tmRow2" );
        properties.put( "*TM_ROW3", "tmRow3" );
        
        properties.put( "*TM_POS", "tmPos" );
        properties.put( "*TM_ROTAXIS", "tmRotAxis" );
        properties.put( "*TM_ROTANGLE", "tmRotAngle" );
        properties.put( "*TM_SCALE", "tmScale" );
        properties.put( "*TM_SCALEAXIS", "tmScaleAxis" );
        properties.put( "*TM_SCALEAXISANG", "tmScaleAxisAngle" );
    }
}
