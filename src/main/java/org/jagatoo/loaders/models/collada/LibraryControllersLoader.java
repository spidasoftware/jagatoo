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
package org.jagatoo.loaders.models.collada;

import java.util.Collection;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.stax.XMLController;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryControllers;
import org.jagatoo.logging.JAGTLog;

/**
 * Loader for LibraryMaterials
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryControllersLoader
{
    /**
     * Load LibraryControllers
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param controllers
     *            The JAXB data to load from
     */
    static void loadLibraryControllers( AssetFolder colladaFile, XMLLibraryControllers controllers )
    {
        Collection<XMLController> controllersList = controllers.controllers.values();
        
        for ( XMLController controller : controllersList )
        {
            String source = controller.skin.source.replaceAll( " ", "_" );
            String id = controller.id;
            JAGTLog.debug( "TT] Found controller with Id : \"", id, "\" and source : \"", source + "\"" );
            colladaFile.getLibraryControllers().getControllers().put( id, new SkeletalController( colladaFile.getLibraryGeometries(), source, controller, colladaFile.getLibraryAnimations(), colladaFile.getLibraryVisualsScenes().getSkeletons().get( id.replace( "-skin", "" ) ) ) );
        }
    }
}
