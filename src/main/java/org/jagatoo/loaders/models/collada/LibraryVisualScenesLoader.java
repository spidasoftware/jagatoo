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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.animation.Skeleton;
import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.ControllerInstance;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.GeometryInstance;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.LibraryVisualScenes;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.MatrixTransform;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Node;
import org.jagatoo.loaders.models.collada.datastructs.visualscenes.Scene;
import org.jagatoo.loaders.models.collada.stax.XMLBindMaterial;
import org.jagatoo.loaders.models.collada.stax.XMLInstanceController;
import org.jagatoo.loaders.models.collada.stax.XMLInstanceGeometry;
import org.jagatoo.loaders.models.collada.stax.XMLInstanceMaterial;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryVisualScenes;
import org.jagatoo.loaders.models.collada.stax.XMLNode;
import org.jagatoo.loaders.models.collada.stax.XMLVisualScene;
import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Vector3f;

/**
 * Class used to load LibraryVisualScenes.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryVisualScenesLoader
{
    /**
     * Load LibraryVisualScenes
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libScenes
     *            The JAXB data to load from
     */
    static void loadLibraryVisualScenes( AssetFolder colladaFile, XMLLibraryVisualScenes libScenes, Vector3f upVector )
    {
        LibraryVisualScenes colLibVisualScenes = colladaFile.getLibraryVisualsScenes();
        HashMap<String, Scene> scenes = colLibVisualScenes.getScenes();

        Collection<XMLVisualScene> visualScenes = libScenes.scenes.values();

        JAGTLog.increaseIndentation();
        for ( XMLVisualScene visualScene : visualScenes )
        {
            Scene colScene = new Scene( visualScene.id, visualScene.name );
            scenes.put( colScene.getId(), colScene );

            JAGTLog.debug( "TT] Found scene [", colScene.getId(), ":", colScene.getName(), "]" );
            JAGTLog.increaseIndentation();
            for ( XMLNode node : visualScene.nodes.values() )
            {
                Node colNode = processNode( node, colLibVisualScenes, colladaFile, upVector );

                if ( colNode != null && node.type != XMLNode.Type.JOINT )
                {
                	JAGTLog.debug( "TT] Successfully adding colNode ", colNode.getId() );
                    colScene.getNodes().put( colNode.getId(), colNode );
                }
            }

            JAGTLog.decreaseIndentation();
        }

        JAGTLog.decreaseIndentation();
    }

    static Node processNode( XMLNode node, LibraryVisualScenes colLibVisualScenes, AssetFolder colladaFile, Vector3f upVector )
    {
        JAGTLog.debug( "TT] Found node [", node.id, ":", node.name, "]" );
        JAGTLog.increaseIndentation();

        Node colNode = new Node( colladaFile, node.id, node.name, new MatrixTransform( node.matrix.matrix4f ) );

        if ( node.type == XMLNode.Type.NODE )
        {
            for ( XMLNode child: node.childrenList )
            {
                colNode.addChild( processNode( child, colLibVisualScenes, colladaFile, upVector ) );
            }

            JAGTLog.debug( "TT] Alright, it's a basic node" );

            if ( node.instanceGeometries != null && !node.instanceGeometries.isEmpty() )
            {
                JAGTLog.debug( "TT] A geometry node!" );

                for ( XMLInstanceGeometry instanceGeometry: node.instanceGeometries )
                {
                    colNode.addGeometryInstance( newCOLLADAGeometryInstanceNode( colladaFile, node, instanceGeometry.url, instanceGeometry.bindMaterial ) );
                }
            }
            else if ( node.instanceControllers != null && !node.instanceControllers.isEmpty() )
            {
                JAGTLog.debug( "TT] A controller node!" );

                for ( XMLInstanceController instanceController: node.instanceControllers )
                {
                    colNode.addControllerInstance( newCOLLADAControllerInstanceNode( colladaFile, node, instanceController.url, instanceController.bindMaterial ) );
                    Controller controller = colladaFile.getLibraryControllers().getControllers().get( instanceController.url );

                    if ( controller instanceof SkeletalController )
                    {
                        final SkeletalController skelController = (SkeletalController)controller;

                        JAGTLog.debug( "Wow! It's a Skeletal Controller Node!" );
                        skelController.setSkeleton( colLibVisualScenes.getSkeletons().get( instanceController.skeleton ) );
                        skelController.setDestinationMesh( colladaFile.getLibraryGeometries().getGeometries().get( skelController.getSourceMeshId() ).get(0) );
                    }
                }
            }
        }
        else if ( node.type == XMLNode.Type.JOINT )
        {
            JAGTLog.debug( "TT] Alright, it's a skeleton node" );

            Skeleton skeleton = SkeletonLoader.loadSkeleton( node, upVector );
            colLibVisualScenes.getSkeletons().put( node.id, skeleton );
            Collection< Controller > controllers = colladaFile.getLibraryControllers().getControllers().values();
            for ( Controller controller: controllers )
            {
                if ( controller instanceof SkeletalController )
                {
                    final SkeletalController skelController = (SkeletalController)controller;

                    if ( node.id.equals( skelController.getController().skin.source ) )
                    {
                        skelController.setSkeleton( skeleton );
                    }
                }
            }
        }
        else
        {
            JAGTLog.debug( "TT] Node is of type : ", node.type, " we don't support specific nodes yet..." );
        }

        JAGTLog.decreaseIndentation();

        return colNode;
    }

    /**
     * Creates a new COLLADA node (type : geometry instance) from the informations given.
     *
     * @param colladaFile
     * @param node
     * @param transform
     * @param geometryUrl
     * @param bindMaterial
     *
     * @return
     */
    static GeometryInstance newCOLLADAGeometryInstanceNode( AssetFolder colladaFile, XMLNode node, String geometryUrl, XMLBindMaterial bindMaterial )
    {
        GeometryInstance colNode;
        HashMap<String, String> materialUrl = new HashMap<String, String>();

        if (bindMaterial != null)
        {
            XMLBindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
            List<XMLInstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
            for ( XMLInstanceMaterial instanceMaterial : instanceMaterialList )
            {
                materialUrl.put(instanceMaterial.symbol, instanceMaterial.target);
    //            if ( materialUrl == null )
    //            {
    //                materialUrl = instanceMaterial.target;
    //            }
    //            else
    //            {
    //                JAGTLog.debug( "TT] Several materials for the same geometry instance ! Skipping...." );
    //            }
            }
        }

        colNode = new GeometryInstance(
                colladaFile,
                node.id,
                node.name,
                geometryUrl,
                materialUrl
        );

        return( colNode );
    }

    /**
     * Creates a new COLLADA node (type : controller instance) from the informations given.
     *
     * @param colladaFile
     * @param node
     * @param transform
     * @param geometryUrl
     * @param bindMaterial
     *
     * @return
     */
    static ControllerInstance newCOLLADAControllerInstanceNode( AssetFolder colladaFile, XMLNode node, String controllerUrl, XMLBindMaterial bindMaterial )
    {
        ControllerInstance colNode;
        String materialUrl = null;
        XMLBindMaterial.TechniqueCommon techniqueCommon = bindMaterial.techniqueCommon;
        List<XMLInstanceMaterial> instanceMaterialList = techniqueCommon.instanceMaterials;
        for ( XMLInstanceMaterial instanceMaterial : instanceMaterialList )
        {
            if ( materialUrl == null )
            {
                materialUrl = instanceMaterial.target;
            }
            else
            {
                JAGTLog.debug( "TT] Several materials for the same controller instance ! Skipping...." );
            }
        }

        colNode = new ControllerInstance(
                colladaFile,
                node.id,
                node.name,
                controllerUrl,
                materialUrl
        );

        return( colNode );
    }
}
