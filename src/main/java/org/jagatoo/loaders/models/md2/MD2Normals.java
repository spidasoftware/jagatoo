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
package org.jagatoo.loaders.models.md2;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * A list of MD2 normals referred to by MD2Vertex
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2Normals
{
    public static final float[] dataZup =
    {
        -0.525731f,  0.000000f,  0.850651f,
        -0.442863f,  0.238856f,  0.864188f,
        -0.295242f,  0.000000f,  0.955423f,
        -0.309017f,  0.500000f,  0.809017f,
        -0.162460f,  0.262866f,  0.951056f,
         0.000000f,  0.000000f,  1.000000f,
         0.000000f,  0.850651f,  0.525731f,
        -0.147621f,  0.716567f,  0.681718f,
         0.147621f,  0.716567f,  0.681718f,
         0.000000f,  0.525731f,  0.850651f,
         0.309017f,  0.500000f,  0.809017f,
         0.525731f,  0.000000f,  0.850651f,
         0.295242f,  0.000000f,  0.955423f,
         0.442863f,  0.238856f,  0.864188f,
         0.162460f,  0.262866f,  0.951056f,
        -0.681718f,  0.147621f,  0.716567f,
        -0.809017f,  0.309017f,  0.500000f,
        -0.587785f,  0.425325f,  0.688191f,
        -0.850651f,  0.525731f,  0.000000f,
        -0.864188f,  0.442863f,  0.238856f,
        -0.716567f,  0.681718f,  0.147621f,
        -0.688191f,  0.587785f,  0.425325f,
        -0.500000f,  0.809017f,  0.309017f,
        -0.238856f,  0.864188f,  0.442863f,
        -0.425325f,  0.688191f,  0.587785f,
        -0.716567f,  0.681718f, -0.147621f,
        -0.500000f,  0.809017f, -0.309017f,
        -0.525731f,  0.850651f,  0.000000f,
         0.000000f,  0.850651f, -0.525731f,
        -0.238856f,  0.864188f, -0.442863f,
         0.000000f,  0.955423f, -0.295242f,
        -0.262866f,  0.951056f, -0.162460f,
         0.000000f,  1.000000f,  0.000000f,
         0.000000f,  0.955423f,  0.295242f,
        -0.262866f,  0.951056f,  0.162460f,
         0.238856f,  0.864188f,  0.442863f,
         0.262866f,  0.951056f,  0.162460f,
         0.500000f,  0.809017f,  0.309017f,
         0.238856f,  0.864188f, -0.442863f,
         0.262866f,  0.951056f, -0.162460f,
         0.500000f,  0.809017f, -0.309017f,
         0.850651f,  0.525731f,  0.000000f,
         0.716567f,  0.681718f,  0.147621f,
         0.716567f,  0.681718f, -0.147621f,
         0.525731f,  0.850651f,  0.000000f,
         0.425325f,  0.688191f,  0.587785f,
         0.864188f,  0.442863f,  0.238856f,
         0.688191f,  0.587785f,  0.425325f,
         0.809017f,  0.309017f,  0.500000f,
         0.681718f,  0.147621f,  0.716567f,
         0.587785f,  0.425325f,  0.688191f,
         0.955423f,  0.295242f,  0.000000f,
         1.000000f,  0.000000f,  0.000000f,
         0.951056f,  0.162460f,  0.262866f,
         0.850651f, -0.525731f,  0.000000f,
         0.955423f, -0.295242f,  0.000000f,
         0.864188f, -0.442863f,  0.238856f,
         0.951056f, -0.162460f,  0.262866f,
         0.809017f, -0.309017f,  0.500000f,
         0.681718f, -0.147621f,  0.716567f,
         0.850651f,  0.000000f,  0.525731f,
         0.864188f,  0.442863f, -0.238856f,
         0.809017f,  0.309017f, -0.500000f,
         0.951056f,  0.162460f, -0.262866f,
         0.525731f,  0.000000f, -0.850651f,
         0.681718f,  0.147621f, -0.716567f,
         0.681718f, -0.147621f, -0.716567f,
         0.850651f,  0.000000f, -0.525731f,
         0.809017f, -0.309017f, -0.500000f,
         0.864188f, -0.442863f, -0.238856f,
         0.951056f, -0.162460f, -0.262866f,
         0.147621f,  0.716567f, -0.681718f,
         0.309017f,  0.500000f, -0.809017f,
         0.425325f,  0.688191f, -0.587785f,
         0.442863f,  0.238856f, -0.864188f,
         0.587785f,  0.425325f, -0.688191f,
         0.688191f,  0.587785f, -0.425325f,
        -0.147621f,  0.716567f, -0.681718f,
        -0.309017f,  0.500000f, -0.809017f,
         0.000000f,  0.525731f, -0.850651f,
        -0.525731f,  0.000000f, -0.850651f,
        -0.442863f,  0.238856f, -0.864188f,
        -0.295242f,  0.000000f, -0.955423f,
        -0.162460f,  0.262866f, -0.951056f,
         0.000000f,  0.000000f, -1.000000f,
         0.295242f,  0.000000f, -0.955423f,
         0.162460f,  0.262866f, -0.951056f,
        -0.442863f, -0.238856f, -0.864188f,
        -0.309017f, -0.500000f, -0.809017f,
        -0.162460f, -0.262866f, -0.951056f,
         0.000000f, -0.850651f, -0.525731f,
        -0.147621f, -0.716567f, -0.681718f,
         0.147621f, -0.716567f, -0.681718f,
         0.000000f, -0.525731f, -0.850651f,
         0.309017f, -0.500000f, -0.809017f,
         0.442863f, -0.238856f, -0.864188f,
         0.162460f, -0.262866f, -0.951056f,
         0.238856f, -0.864188f, -0.442863f,
         0.500000f, -0.809017f, -0.309017f,
         0.425325f, -0.688191f, -0.587785f,
         0.716567f, -0.681718f, -0.147621f,
         0.688191f, -0.587785f, -0.425325f,
         0.587785f, -0.425325f, -0.688191f,
         0.000000f, -0.955423f, -0.295242f,
         0.000000f, -1.000000f,  0.000000f,
         0.262866f, -0.951056f, -0.162460f,
         0.000000f, -0.850651f,  0.525731f,
         0.000000f, -0.955423f,  0.295242f,
         0.238856f, -0.864188f,  0.442863f,
         0.262866f, -0.951056f,  0.162460f,
         0.500000f, -0.809017f,  0.309017f,
         0.716567f, -0.681718f,  0.147621f,
         0.525731f, -0.850651f,  0.000000f,
        -0.238856f, -0.864188f, -0.442863f,
        -0.500000f, -0.809017f, -0.309017f,
        -0.262866f, -0.951056f, -0.162460f,
        -0.850651f, -0.525731f,  0.000000f,
        -0.716567f, -0.681718f, -0.147621f,
        -0.716567f, -0.681718f,  0.147621f,
        -0.525731f, -0.850651f,  0.000000f,
        -0.500000f, -0.809017f,  0.309017f,
        -0.238856f, -0.864188f,  0.442863f,
        -0.262866f, -0.951056f,  0.162460f,
        -0.864188f, -0.442863f,  0.238856f,
        -0.809017f, -0.309017f,  0.500000f,
        -0.688191f, -0.587785f,  0.425325f,
        -0.681718f, -0.147621f,  0.716567f,
        -0.442863f, -0.238856f,  0.864188f,
        -0.587785f, -0.425325f,  0.688191f,
        -0.309017f, -0.500000f,  0.809017f,
        -0.147621f, -0.716567f,  0.681718f,
        -0.425325f, -0.688191f,  0.587785f,
        -0.162460f, -0.262866f,  0.951056f,
         0.442863f, -0.238856f,  0.864188f,
         0.162460f, -0.262866f,  0.951056f,
         0.309017f, -0.500000f,  0.809017f,
         0.147621f, -0.716567f,  0.681718f,
         0.000000f, -0.525731f,  0.850651f,
         0.425325f, -0.688191f,  0.587785f,
         0.587785f, -0.425325f,  0.688191f,
         0.688191f, -0.587785f,  0.425325f,
        -0.955423f,  0.295242f,  0.000000f,
        -0.951056f,  0.162460f,  0.262866f,
        -1.000000f,  0.000000f,  0.000000f,
        -0.850651f,  0.000000f,  0.525731f,
        -0.955423f, -0.295242f,  0.000000f,
        -0.951056f, -0.162460f,  0.262866f,
        -0.864188f,  0.442863f, -0.238856f,
        -0.951056f,  0.162460f, -0.262866f,
        -0.809017f,  0.309017f, -0.500000f,
        -0.864188f, -0.442863f, -0.238856f,
        -0.951056f, -0.162460f, -0.262866f,
        -0.809017f, -0.309017f, -0.500000f,
        -0.681718f,  0.147621f, -0.716567f,
        -0.681718f, -0.147621f, -0.716567f,
        -0.850651f,  0.000000f, -0.525731f,
        -0.688191f,  0.587785f, -0.425325f,
        -0.587785f,  0.425325f, -0.688191f,
        -0.425325f,  0.688191f, -0.587785f,
        -0.425325f, -0.688191f, -0.587785f,
        -0.587785f, -0.425325f, -0.688191f,
        -0.688191f, -0.587785f, -0.425325f
    };
    
    public static final float[] dataYup =
    {
        -0.525731f,  0.850651f,  0.000000f,
        -0.442863f,  0.864188f, -0.238856f,
        -0.295242f,  0.955423f,  0.000000f,
        -0.309017f,  0.809017f, -0.500000f,
        -0.162460f,  0.951056f, -0.262866f,
         0.000000f,  1.000000f,  0.000000f,
         0.000000f,  0.525731f, -0.850651f,
        -0.147621f,  0.681718f, -0.716567f,
         0.147621f,  0.681718f, -0.716567f,
         0.000000f,  0.850651f, -0.525731f,
         0.309017f,  0.809017f, -0.500000f,
         0.525731f,  0.850651f,  0.000000f,
         0.295242f,  0.955423f,  0.000000f,
         0.442863f,  0.864188f, -0.238856f,
         0.162460f,  0.951056f, -0.262866f,
        -0.681718f,  0.716567f, -0.147621f,
        -0.809017f,  0.500000f, -0.309017f,
        -0.587785f,  0.688191f, -0.425325f,
        -0.850651f,  0.000000f, -0.525731f,
        -0.864188f,  0.238856f, -0.442863f,
        -0.716567f,  0.147621f, -0.681718f,
        -0.688191f,  0.425325f, -0.587785f,
        -0.500000f,  0.309017f, -0.809017f,
        -0.238856f,  0.442863f, -0.864188f,
        -0.425325f,  0.587785f, -0.688191f,
        -0.716567f, -0.147621f, -0.681718f,
        -0.500000f, -0.309017f, -0.809017f,
        -0.525731f,  0.000000f, -0.850651f,
         0.000000f, -0.525731f, -0.850651f,
        -0.238856f, -0.442863f, -0.864188f,
         0.000000f, -0.295242f, -0.955423f,
        -0.262866f, -0.162460f, -0.951056f,
         0.000000f,  0.000000f, -1.000000f,
         0.000000f,  0.295242f, -0.955423f,
        -0.262866f,  0.162460f, -0.951056f,
         0.238856f,  0.442863f, -0.864188f,
         0.262866f,  0.162460f, -0.951056f,
         0.500000f,  0.309017f, -0.809017f,
         0.238856f, -0.442863f, -0.864188f,
         0.262866f, -0.162460f, -0.951056f,
         0.500000f, -0.309017f, -0.809017f,
         0.850651f,  0.000000f, -0.525731f,
         0.716567f,  0.147621f, -0.681718f,
         0.716567f, -0.147621f, -0.681718f,
         0.525731f,  0.000000f, -0.850651f,
         0.425325f,  0.587785f, -0.688191f,
         0.864188f,  0.238856f, -0.442863f,
         0.688191f,  0.425325f, -0.587785f,
         0.809017f,  0.500000f, -0.309017f,
         0.681718f,  0.716567f, -0.147621f,
         0.587785f,  0.688191f, -0.425325f,
         0.955423f,  0.000000f, -0.295242f,
         1.000000f,  0.000000f,  0.000000f,
         0.951056f,  0.262866f, -0.162460f,
         0.850651f,  0.000000f,  0.525731f,
         0.955423f,  0.000000f,  0.295242f,
         0.864188f,  0.238856f,  0.442863f,
         0.951056f,  0.262866f,  0.162460f,
         0.809017f,  0.500000f,  0.309017f,
         0.681718f,  0.716567f,  0.147621f,
         0.850651f,  0.525731f,  0.000000f,
         0.864188f, -0.238856f, -0.442863f,
         0.809017f, -0.500000f, -0.309017f,
         0.951056f, -0.262866f, -0.162460f,
         0.525731f, -0.850651f,  0.000000f,
         0.681718f, -0.716567f, -0.147621f,
         0.681718f, -0.716567f,  0.147621f,
         0.850651f, -0.525731f,  0.000000f,
         0.809017f, -0.500000f,  0.309017f,
         0.864188f, -0.238856f,  0.442863f,
         0.951056f, -0.262866f,  0.162460f,
         0.147621f, -0.681718f, -0.716567f,
         0.309017f, -0.809017f, -0.500000f,
         0.425325f, -0.587785f, -0.688191f,
         0.442863f, -0.864188f, -0.238856f,
         0.587785f, -0.688191f, -0.425325f,
         0.688191f, -0.425325f, -0.587785f,
        -0.147621f, -0.681718f, -0.716567f,
        -0.309017f, -0.809017f, -0.500000f,
         0.000000f, -0.850651f, -0.525731f,
        -0.525731f, -0.850651f,  0.000000f,
        -0.442863f, -0.864188f, -0.238856f,
        -0.295242f, -0.955423f,  0.000000f,
        -0.162460f, -0.951056f, -0.262866f,
         0.000000f, -1.000000f,  0.000000f,
         0.295242f, -0.955423f,  0.000000f,
         0.162460f, -0.951056f, -0.262866f,
        -0.442863f, -0.864188f,  0.238856f,
        -0.309017f, -0.809017f,  0.500000f,
        -0.162460f, -0.951056f,  0.262866f,
         0.000000f, -0.525731f,  0.850651f,
        -0.147621f, -0.681718f,  0.716567f,
         0.147621f, -0.681718f,  0.716567f,
         0.000000f, -0.850651f,  0.525731f,
         0.309017f, -0.809017f,  0.500000f,
         0.442863f, -0.864188f,  0.238856f,
         0.162460f, -0.951056f,  0.262866f,
         0.238856f, -0.442863f,  0.864188f,
         0.500000f, -0.309017f,  0.809017f,
         0.425325f, -0.587785f,  0.688191f,
         0.716567f, -0.147621f,  0.681718f,
         0.688191f, -0.425325f,  0.587785f,
         0.587785f, -0.688191f,  0.425325f,
         0.000000f, -0.295242f,  0.955423f,
         0.000000f,  0.000000f,  1.000000f,
         0.262866f, -0.162460f,  0.951056f,
         0.000000f,  0.525731f,  0.850651f,
         0.000000f,  0.295242f,  0.955423f,
         0.238856f,  0.442863f,  0.864188f,
         0.262866f,  0.162460f,  0.951056f,
         0.500000f,  0.309017f,  0.809017f,
         0.716567f,  0.147621f,  0.681718f,
         0.525731f,  0.000000f,  0.850651f,
        -0.238856f, -0.442863f,  0.864188f,
        -0.500000f, -0.309017f,  0.809017f,
        -0.262866f, -0.162460f,  0.951056f,
        -0.850651f,  0.000000f,  0.525731f,
        -0.716567f, -0.147621f,  0.681718f,
        -0.716567f,  0.147621f,  0.681718f,
        -0.525731f,  0.000000f,  0.850651f,
        -0.500000f,  0.309017f,  0.809017f,
        -0.238856f,  0.442863f,  0.864188f,
        -0.262866f,  0.162460f,  0.951056f,
        -0.864188f,  0.238856f,  0.442863f,
        -0.809017f,  0.500000f,  0.309017f,
        -0.688191f,  0.425325f,  0.587785f,
        -0.681718f,  0.716567f,  0.147621f,
        -0.442863f,  0.864188f,  0.238856f,
        -0.587785f,  0.688191f,  0.425325f,
        -0.309017f,  0.809017f,  0.500000f,
        -0.147621f,  0.681718f,  0.716567f,
        -0.425325f,  0.587785f,  0.688191f,
        -0.162460f,  0.951056f,  0.262866f,
         0.442863f,  0.864188f,  0.238856f,
         0.162460f,  0.951056f,  0.262866f,
         0.309017f,  0.809017f,  0.500000f,
         0.147621f,  0.681718f,  0.716567f,
         0.000000f,  0.850651f,  0.525731f,
         0.425325f,  0.587785f,  0.688191f,
         0.587785f,  0.688191f,  0.425325f,
         0.688191f,  0.425325f,  0.587785f,
        -0.955423f,  0.000000f, -0.295242f,
        -0.951056f,  0.262866f, -0.162460f,
        -1.000000f,  0.000000f,  0.000000f,
        -0.850651f,  0.525731f,  0.000000f,
        -0.955423f,  0.000000f,  0.295242f,
        -0.951056f,  0.262866f,  0.162460f,
        -0.864188f, -0.238856f, -0.442863f,
        -0.951056f, -0.262866f, -0.162460f,
        -0.809017f, -0.500000f, -0.309017f,
        -0.864188f, -0.238856f,  0.442863f,
        -0.951056f, -0.262866f,  0.162460f,
        -0.809017f, -0.500000f,  0.309017f,
        -0.681718f, -0.716567f, -0.147621f,
        -0.681718f, -0.716567f,  0.147621f,
        -0.850651f, -0.525731f,  0.000000f,
        -0.688191f, -0.425325f, -0.587785f,
        -0.587785f, -0.688191f, -0.425325f,
        -0.425325f, -0.587785f, -0.688191f,
        -0.425325f, -0.587785f,  0.688191f,
        -0.587785f, -0.688191f,  0.425325f,
        -0.688191f, -0.425325f,  0.587785f
    };
    
    public static void convertNormalsZup2Yup()
    {
        NumberFormat nf = NumberFormat.getNumberInstance( Locale.ENGLISH );
        nf.setMinimumFractionDigits( 6 );
        nf.setMaximumFractionDigits( 6 );
        
        System.out.println( "    public static final float[] dataYup =" );
        System.out.println( "    {" );
        
        float v;
        
        for ( int i = 0; i < dataZup.length / 3; i++ )
        {
            System.out.print( "        " );
            
            v = dataZup[i * 3 + 0];
            
            if ( v >= 0f )
                System.out.print( " " + nf.format( v ) + "f" );
            else
                System.out.print( nf.format( v ) + "f" );
            
            System.out.print( ", " );
            
            v = dataZup[i * 3 + 2];
            
            if ( v >= 0f )
                System.out.print( " " + nf.format( v ) + "f" );
            else
                System.out.print( nf.format( v ) + "f" );
            
            System.out.print( ", " );
            
            v = -dataZup[i * 3 + 1];
            if ( ( v == 0f ) || ( v == -0f ) )
                v = 0f;
            
            if ( v >= 0f )
                System.out.print( " " + nf.format( v ) + "f" );
            else
                System.out.print( nf.format( v ) + "f" );
            
            if ( i < ( dataZup.length / 3 ) - 1 )
                System.out.println( "," );
            else
                System.out.println();
        }
        
        System.out.println( "    };" );
    }
}
