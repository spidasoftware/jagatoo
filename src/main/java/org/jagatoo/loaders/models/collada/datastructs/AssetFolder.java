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
package org.jagatoo.loaders.models.collada.datastructs;

import java.net.URL;

import org.jagatoo.loaders.models.collada.datastructs.animation.LibraryAnimations;
import org.jagatoo.loaders.models.collada.datastructs.controllers.LibraryControllers;
import org.jagatoo.loaders.models.collada.datastructs.effects.LibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.geometries.LibraryGeometries;
import org.jagatoo.loaders.models.collada.datastructs.images.LibraryImages;
import org.jagatoo.loaders.models.collada.datastructs.materials.LibraryMaterials;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.LibraryVisualScenes;


/**
 * This class contains every information, that has been loaded
 * from a COLLADA file and that the loader handles. The reason,
 * this class exists, is that data provided directly by the xml lib when
 * loading a COLLADA file isn't really convenient to handle. So
 * in the org.collada.xith3d.COLLADALoader file, the xml lib data (which
 * is composed of classes contained in the
 * org.jagatoo.loaders.models.collada.schema.org.collada.x2005.x11.colladaSchema
 * package) is converted to a COLLADAFile. Then it can be used in a scenegraph,
 * e.g. Xith3D.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class AssetFolder
{
    /** LibraryControllers : contains all controllers */
    private final LibraryControllers libraryControllers;
    
    /** LibraryEffects : contains all effects */
    private final LibraryEffects libraryEffects;
    
    /** LibraryImages : contains all images */
    private final LibraryImages libraryImages;
    
    /** LibraryMaterials : contains all materials */
    private final LibraryMaterials libraryMaterials;
    
    /** LibraryGeometries : contains all geometries */
    private final LibraryGeometries libraryGeometries;
    
    /** LibraryVisualScenes : contains all visual scenes */
    private final LibraryVisualScenes libraryVisualsScenes;
    
    /** LibraryAnimations : contains all animations/actions */
    private final LibraryAnimations libraryAnimations;
    
    private ColladaProtoypeModel protoModel;
    
    /**
     * The base path, used e.g. when loading textures.
     * Basically it's where this file has been loaded from.
     */
    private final URL basePath;
    
    /**
     * @return the libraryControllers.
     */
    public final LibraryControllers getLibraryControllers()
    {
        return ( libraryControllers );
    }
    
    /**
     * @return the libraryEffects.
     */
    public final LibraryEffects getLibraryEffects()
    {
        return ( libraryEffects );
    }
    
    /**
     * @return the libraryImages.
     */
    public final LibraryImages getLibraryImages()
    {
        return ( libraryImages );
    }
    
    /**
     * @return the libraryMaterials.
     */
    public final LibraryMaterials getLibraryMaterials()
    {
        return ( libraryMaterials );
    }
    
    /**
     * @return the libraryGeometries.
     */
    public final LibraryGeometries getLibraryGeometries()
    {
        return ( libraryGeometries );
    }
    
    /**
     * @return the libraryVisualsScenes.
     */
    public final LibraryVisualScenes getLibraryVisualsScenes()
    {
        return ( libraryVisualsScenes );
    }
    
    /**
     * @return the libraryAnimations.
     */
    public final LibraryAnimations getLibraryAnimations()
    {
        return ( libraryAnimations );
    }
    
    /**
     * @return the basePath.
     */
    public final URL getBasePath()
    {
        return ( basePath );
    }
    
    public void setModel( ColladaProtoypeModel model )
    {
        this.protoModel = model;
    }
    
	public final ColladaProtoypeModel getModel()
	{
		return ( protoModel );
	}
    
    /**
     * Creates a new COLLADAFile
     * @param basePath The base path, used e.g. when loading textures
     */
    public AssetFolder( URL basePath )
    {
        this.basePath = basePath;
        
        this.libraryControllers = new LibraryControllers();
        this.libraryAnimations = new LibraryAnimations();
        this.libraryEffects = new LibraryEffects();
        this.libraryImages = new LibraryImages();
        this.libraryMaterials = new LibraryMaterials();
        this.libraryGeometries = new LibraryGeometries();
        this.libraryVisualsScenes = new LibraryVisualScenes();
    }
}
