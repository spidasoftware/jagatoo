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
package org.jagatoo.loaders.models.collada;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.ColladaProtoypeModel;
import org.jagatoo.loaders.models.collada.stax.XMLCOLLADA;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryAnimations;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryControllers;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryEffects;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryGeometries;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryImages;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryMaterials;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryVisualScenes;
import org.jagatoo.logging.JAGTLog;

/**
 * This is a really simple COLLADA file loader. Its features are limited for now
 * but improving every minute :)
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class COLLADALoader
{
    private static final XMLInputFactory factory;
    
    static
    {
        factory = XMLInputFactory.newInstance();
        //factory.configureForSpeed(); (if using a WstxInputFactory)
        factory.setProperty( XMLInputFactory.IS_COALESCING, true );
    }
    
    /**
     * Create a new COLLADA Loader.
     */
    public COLLADALoader() throws ParsingException
    {
    }
    
    /**
     * Loads a COLLADA file from a stream
     * @param basePath The base path, used e.g. when there are textures to load
     * 
     * @param stream
     *            The stream to load the scene from
     * @return the loaded file
     */
    public AssetFolder load( URL basePath, InputStream stream )
    {
        long t1 = System.nanoTime();
        
        AssetFolder colladaFile = new AssetFolder( basePath );
        
        try
        {
            JAGTLog.debug( "TT] Parsing..." );
            JAGTLog.increaseIndentation();
            
            long l1 = System.nanoTime();

            XMLStreamReader reader = factory.createXMLStreamReader( stream );
            XMLCOLLADA collada = new XMLCOLLADA();
            try
            {
                collada.parse( reader );
            }
            catch ( XMLStreamException e )
            {
                System.out.println( reader.getLocation().toString() );
                e.printStackTrace();
            }
            catch ( NumberFormatException e )
            {
                System.out.println( reader.getLocation().toString() );
                e.printStackTrace();
            }
            finally
            {
                reader.close();
            }
            
            long l2 = System.nanoTime();
            JAGTLog.debug( "TT] Took ", ( ( l2 - l1 ) / 1000000 ), " milliseconds to parse" );
            
            JAGTLog.decreaseIndentation();
            
            JAGTLog.debug( "--] This is a COLLADA ", collada.version, " file" );
            JAGTLog.debug( "--] Note that the loader don't care whether it's 1.4.0 or 1.4.1, though",
                           "\n the COLLADA schema used for parsing is the for 1.4.1, tests for development",
                           "\n have been done with 1.4.0 files exported by Blender (Illusoft script)"
                         );
            
            JAGTLog.debug( "TT] Exploring libraries..." );
            
            JAGTLog.increaseIndentation();
            
            List<XMLLibraryGeometries> libraryGeometriesList = collada.libraryGeometries;
            if ( libraryGeometriesList != null )
            {
                for ( XMLLibraryGeometries libraryGeometries : libraryGeometriesList )
                {
                    JAGTLog.debug( "CC] Found LibraryGeometries ! We know that !" );
                    JAGTLog.increaseIndentation();
                    LibraryGeometriesLoader.loadLibraryGeometries( colladaFile, libraryGeometries );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryControllers> libraryControllersList = collada.libraryControllers;
            if ( libraryControllersList != null )
            {
                for ( XMLLibraryControllers libraryControllers : libraryControllersList )
                {
                    JAGTLog.debug( "CC] Found LibraryControllers ! Investigating... !" );
                    JAGTLog.increaseIndentation();
                    LibraryControllersLoader.loadLibraryControllers( colladaFile, libraryControllers );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryEffects> libraryEffectsList = collada.libraryEffects;
            if ( libraryEffectsList != null )
            {
                for ( XMLLibraryEffects libraryEffects : libraryEffectsList )
                {
                    JAGTLog.debug( "CC] Found LibraryEffects ! Investigating... !" );
                    JAGTLog.increaseIndentation();
                    LibraryEffectsLoader.loadLibraryEffects( colladaFile, libraryEffects );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryImages> libraryImagesList = collada.libraryImages;
            if ( libraryImagesList != null )
            {
                for ( XMLLibraryImages libraryImages : libraryImagesList )
                {
                    JAGTLog.debug( "CC] Found LibraryImages ! We know that !" );
                    JAGTLog.increaseIndentation();
                    LibraryImagesLoader.loadLibraryImages( colladaFile, libraryImages );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryVisualScenes> libraryVisualScenesList = collada.libraryVisualScenes;
            if ( libraryVisualScenesList != null )
            {
                for ( XMLLibraryVisualScenes libraryVisualScenes : libraryVisualScenesList )
                {
                    JAGTLog.debug( "CC] Found LibraryVisualScenes ! Investigating... !" );
                    JAGTLog.increaseIndentation();
                    LibraryVisualScenesLoader.loadLibraryVisualScenes( colladaFile, libraryVisualScenes, collada.asset.getUpVector() );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryMaterials> libraryMaterialsList = collada.libraryMaterials;
            if ( libraryMaterialsList != null )
            {
                for ( XMLLibraryMaterials libraryMaterials : libraryMaterialsList )
                {
                    JAGTLog.debug( "CC] Found LibraryMaterials ! We know that !" );
                    JAGTLog.increaseIndentation();
                    LibraryMaterialsLoader.loadLibraryMaterials( colladaFile, libraryMaterials );
                    JAGTLog.decreaseIndentation();
                }
            }
            
            List<XMLLibraryAnimations> libraryAnimationsList = collada.libraryAnimations;
            if ( libraryAnimationsList != null )
            {
            	for ( XMLLibraryAnimations libraryAnimations : libraryAnimationsList )
            	{
            		JAGTLog.debug( "CC] Found LibraryAnimations ! We should know that !" );
                    JAGTLog.increaseIndentation();
                    LibraryAnimationsLoader.loadLibraryAnimations( colladaFile, libraryAnimations );
                    JAGTLog.decreaseIndentation();
            	}
            }
            
            JAGTLog.decreaseIndentation();
            
            //creates a simple model to perform the skeleton animation algorithm
            colladaFile.setModel( new ColladaProtoypeModel( colladaFile ) );
            
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        long t2 = System.nanoTime();
        
        JAGTLog.debug( "TT] Took ", ( ( t2 - t1 ) / 1000L / 1000L ), " milliseconds to load." );
        
        // We still don't know what we will return..
        return ( colladaFile );
    }
}
