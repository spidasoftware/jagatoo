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
package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.ArrayList;
import java.util.HashMap;


import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.geometries.Geometry;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;

/**
 * A node containing an instance of a geometry.
 * Note that the COLLADA file format is more like a scenegraph
 * than a list of nodes. But I'm applying the YAGNI here : I use
 * files from Blender only. If we ever need more, then we'll change
 * it.
 *
 * @author Amos Wenger (aka BlueSky)
 */
public class GeometryInstance extends AbstractInstance
{
    /** Our geometry */
    private final HashMap<String, String> materialIDs;

    /**
     * @return the geometry.
     */
    public final ArrayList<Geometry> getGeometry()
    {
        return( getFile().getLibraryGeometries().getGeometries().get( getUrl() ) );
    }

    /**
     * @return the material.
     */
    public final Material getMaterial(String materialID)
    {
    	// TODO: Why are we storing anything? There should probably bea  double lookup here, but we seem
    	// to only have half of the information we actually need to do this properly
    	// the material instance defines which instance of a material is used
    	// but the triangles tag *also* defines which materials it uses
    	// I think the correct answer may be a lookup to get the instance of the base material
    	// that matches the one specified in the triangles tag.
        return( getFile().getLibraryMaterials().getMaterials().get( materialIDs.get(materialID) ) );
    }

    /**
     * Creates a new {@link GeometryInstance}.
     *
     * @param file The COLLADA file this node belongs to
     * @param id The id of this node
     * @param name The name of this node
     * @param transform The transform of this node
     * @param geometryUrl The URL of the geometry this node is an instance of
     * @param materialUrl The URL of the material bound to this node
     */
    public GeometryInstance( AssetFolder file, String id, String name, String geometryUrl, HashMap<String, String> materialUrls )
    {
        super( file, id, name, geometryUrl );

        this.materialIDs = materialUrls;
    }
}
