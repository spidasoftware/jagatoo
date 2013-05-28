/**
 * Copyright (c) 2007-2008, JAGaToo Project Group all rights reserved.
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
 *
 */
package org.jagatoo.loaders.models.collada.datastructs.effects;

import org.jagatoo.loaders.models.collada.stax.XMLShadingParameters;
import org.openmali.vecmath2.Colorf;

/**
 * Insert type comment here.
 *
 * @author Michael Ford
 */
/**
 * @author Michael Ford
 */
public class ShadingParameters {

	public Colorf emission = new Colorf();
	public Colorf ambient = new Colorf();
	public Colorf diffuse = new Colorf();
	public Colorf specular = new Colorf();
	public float shininess = 0f;
	public Colorf reflective = new Colorf();
	public float reflectivity = 0f;
	public Colorf transparent = new Colorf();
	public float transparency = 0f;

	public ShadingParameters(XMLShadingParameters xmlParams)
	{

		// need to add something to handle textures better.

		if (xmlParams.emission != null && xmlParams.emission.color4 != null)
		{
			emission = xmlParams.emission.color4.color;
		}
		if (xmlParams.ambient != null&& xmlParams.ambient.color4 != null)
		{
			ambient = xmlParams.ambient.color4.color;
		}
		if (xmlParams.diffuse != null&& xmlParams.diffuse.color4 != null)
		{
			diffuse = xmlParams.diffuse.color4.color;
		}
		if (xmlParams.specular != null&& xmlParams.specular.color4 != null)
		{
			specular = xmlParams.specular.color4.color;
		}
		if (xmlParams.shininess != null)
		{
			shininess = xmlParams.shininess._float;
		}
		if (xmlParams.reflective != null && xmlParams.reflective.color4 != null)

		{
			reflective = xmlParams.reflective.color4.color;
		}
		if (xmlParams.reflectivity != null)
		{
			reflectivity = xmlParams.reflectivity._float;
		}
		if (xmlParams.transparent != null && xmlParams.transparent.color4 != null)
		{
			transparent = xmlParams.transparent.color4.color;
		}
		if (xmlParams.transparency != null)
		{
			transparency = xmlParams.transparency._float;
		}
	}



}
