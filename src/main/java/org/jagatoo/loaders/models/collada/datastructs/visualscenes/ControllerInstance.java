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

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.materials.Material;

/**
 * A node containing an instance of a controller.
 * Note that the COLLADA file format is more like a scenegraph
 * than a list of nodes. But I'm applying the YAGNI here : I use
 * files from Blender only. If we ever need more, then we'll change
 * it.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class ControllerInstance extends AbstractInstance
{
    /** Our controller */
    private final String materialURL;
    
    /**
     * @return the controller.
     */
    public final Controller getController()
    {
        return( getFile().getLibraryControllers().getControllers().get( getUrl() ) );
    }
    
    /**
     * @return the material.
     */
    public Material getMaterial()
    {
        return( getFile().getLibraryMaterials().getMaterials().get( materialURL ) );
    }
    
    /**
     * Creates a new {@link ControllerInstance}.
     * 
     * @param file The COLLADA file this node belongs to
     * @param id The id of this node
     * @param name The name of this node
     * @param transform The transform of this node
     * @param controllerURL The URL of the geometry this node is an instance of
     * @param materialURL The URL of the material bound to this node
     */
    public ControllerInstance( AssetFolder file, String id, String name, String controllerURL, String materialURL )
    {
        super( file, id, name, controllerURL );
        
        this.materialURL = materialURL;
    }
}
