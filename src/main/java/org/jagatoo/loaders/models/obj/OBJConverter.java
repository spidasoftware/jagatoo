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
package org.jagatoo.loaders.models.obj;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.opengl.enums.BlendMode;
import org.jagatoo.opengl.enums.TextureMode;
import org.openmali.spatial.bounds.BoundsType;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class OBJConverter
{
    private static final GeometryType GEOM_TYPE = GeometryType.TRIANGLE_ARRAY;
    
    private static NamedObject textureAttribs = null;
    private static NamedObject transpAttribs = null;
    
    private static void generateAppeances( HashMap<String, OBJMaterial> matMap, HashMap< String, NamedObject > appMap, AppearanceFactory appFactory, URL baseURL )
    {
        for ( String name: matMap.keySet() )
        {
            OBJMaterial oMat = matMap.get( name );
            
            NamedObject app = appFactory.createAppearance( name, 0 );
            
            if ( oMat.getColor() != null )
            {
                NamedObject coloringAttribs = appFactory.createColoringAttributes( "" );
                appFactory.setColoringAttribsColor( coloringAttribs, oMat.getColor(), 0, 3 );
                appFactory.applyColoringAttributes( coloringAttribs, app );
            }
            
            NamedObject xMat = appFactory.createMaterial( name );
            appFactory.setMaterialLightingEnabled( xMat, true );
            
            if ( oMat.getAmbientColor() != null )
                appFactory.setMaterialAmbientColor( xMat, oMat.getAmbientColor()[ 0 ], oMat.getAmbientColor()[ 1 ], oMat.getAmbientColor()[ 2 ] );
            
            if ( oMat.getDiffuseColor() != null )
                appFactory.setMaterialDiffuseColor( xMat, oMat.getDiffuseColor()[ 0 ], oMat.getDiffuseColor()[ 1 ], oMat.getDiffuseColor()[ 2 ] );
            
            if ( oMat.getSpecularColor() != null )
                appFactory.setMaterialSpecularColor( xMat, oMat.getSpecularColor()[ 0 ], oMat.getSpecularColor()[ 1 ], oMat.getSpecularColor()[ 2 ] );
            
            appFactory.setMaterialShininess( xMat, oMat.getShininess() );
            
            appFactory.applyMaterial( xMat, app );
            
            if ( oMat.getTextureName() != null )
            {
                // assuming false for mipmap argument, no good reason
                AbstractTexture texture = appFactory.loadOrGetTexture( oMat.getTextureName(), baseURL, true, true, true, true, true );
                appFactory.applyTexture( texture, 0, app );
                
                if ( textureAttribs == null )
                {
                    textureAttribs = appFactory.createTextureAttributes( "SIMPLE_MODULATE" );
                    appFactory.setTextureAttribsTextureMode( textureAttribs, TextureMode.MODULATE );
                }
                
                appFactory.applyTextureAttributes( textureAttribs, 0, app );
                
                if ( texture.getFormat().hasAlpha() )
                {
                    if ( transpAttribs == null )
                    {
                        transpAttribs = appFactory.createTransparencyAttributes( "SIMPLE_BLENDED" );
                        appFactory.setTransparencyAttribsBlendMode( transpAttribs, BlendMode.BLENDED );
                    }
                    
                    appFactory.applyTransparancyAttributes( transpAttribs, app );
                }
            }
            
            appMap.put( name, app );
        }
    }
    
    private static NamedObject buildShape( OBJFaceList faceList, String name, GeometryFactory geomFactory, HashMap<String, NamedObject> appMap, NodeFactory nodeFactory )
    {
        int totalVerts = 0;
        int[] indexs = new int[ faceList.getFaces().size() ];
        
        for ( int i = 0; i < faceList.getFaces().size(); i++ )
        {
            OBJFace face = faceList.getFaces().get( i );
            totalVerts += face.getCount();
            
            if ( i < faceList.getFaces().size() - 1 )
            {
                indexs[ i + 1 ] = face.getCount();
            }
        }
        
        NamedObject geom = geomFactory.createGeometry( name, GEOM_TYPE, 3, totalVerts, 0, null );
        
        int currentVert = 0;
        
        float[][] dataVerts = new float[ totalVerts ][ 3 ];
        float[][] dataNormals = new float[ totalVerts ][ 3 ];
        float[][] dataTexs = new float[ totalVerts ][ 3 ];
        
        NamedObject app = null;
        
        for ( int i = 0; i < faceList.getFaces().size(); i++ )
        {
            OBJFace face = faceList.getFaces().get( i );
            // hawkwind hack:
            if ( face.getMaterial() != null )
            {
                app = appMap.get( face.getMaterial().getName() );
            }
            
            face.configure( dataVerts, dataNormals, dataTexs, currentVert );
            currentVert += face.getCount();
        }
        
        boolean useTex = faceList.texturesUsed() && ( dataTexs != null );
        
        for ( int i = 0 ; i < totalVerts; i++ )
        {
            geomFactory.setCoordinates( geom, GEOM_TYPE, i, dataVerts[i], 0, 1 );
            geomFactory.setNormals( geom, GEOM_TYPE, i, dataNormals[i], 0, 1 );
            
            if ( useTex )
                geomFactory.setTexCoords( geom, GEOM_TYPE, 0, 2, i, dataTexs[i], 0, 1 );
        }
        
        NamedObject shape = nodeFactory.createShape( name, geom, app, BoundsType.SPHERE );
        
        return ( shape );
    }
    
    private static void build( HashMap<String, NamedObject> appMap, OBJGroup objGroup, NamedObject parentGroup, GeometryFactory geomFactory, NodeFactory nodeFactory, SpecialItemsHandler siHandler )
    {
        List<OBJGroup> children = objGroup.getChildren();
        if ( ( children.size() > 0 ) || ( objGroup.getFaces().size() == 0 ) )
        {
            String name = "";
            if ( objGroup.hasName() )
            {
                if ( objGroup.getFaces().size() > 0 )
                    name = objGroup.getName() + "_children";
                else
                    name = objGroup.getName();
            }
            
            NamedObject childrenGroup = nodeFactory.createSimpleGroup( name, BoundsType.SPHERE);
            
            if ( objGroup.hasName() && !objGroup.isTopGroup() )
            {
                siHandler.addSpecialItem( SpecialItemType.NAMED_OBJECT, childrenGroup.getName(), childrenGroup );
            }
            
            for ( int i = 0; i < children.size(); i++ )
            {
                build( appMap, children.get( i ), childrenGroup, geomFactory, nodeFactory, siHandler );
            }
            
            nodeFactory.addNodeToGroup( childrenGroup, parentGroup );
        }
        
        if ( objGroup.getFaces().size() > 0 )
        {
            String shapeName = ( ( objGroup.hasName() ) && ( !objGroup.isTopGroup() ) ) ? objGroup.getName() : "";
            NamedObject shape = buildShape( objGroup, shapeName, geomFactory, appMap, nodeFactory );
            
            siHandler.addSpecialItem( SpecialItemType.SHAPE, shapeName, shape );
            
            nodeFactory.addNodeToGroup( shape, parentGroup );
        }
    }
    
    /**
     * 
     * @param prototype
     * @param baseURL
     * @param appFactory
     * @param skin
     * @param geomFactory
     * @param convertZup2Yup
     * @param scale
     * @param nodeFactory
     * @param siHandler
     * @param rootGroup
     */
    public static void convert( OBJModelPrototype prototype, URL baseURL, AppearanceFactory appFactory, String skin, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, SpecialItemsHandler siHandler, NamedObject rootGroup )
    {
        HashMap<String, NamedObject> appMap = new HashMap<String, NamedObject>();
        
        generateAppeances( prototype.getMaterialMap(), appMap, appFactory, baseURL );
        
        build( appMap, prototype.getTopGroup(), rootGroup, geomFactory, nodeFactory, siHandler );
    }
}
