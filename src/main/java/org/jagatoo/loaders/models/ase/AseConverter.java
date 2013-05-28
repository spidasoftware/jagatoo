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
package org.jagatoo.loaders.models.ase;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.jagatoo.datatypes.NamableObject;
import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.opengl.enums.BlendMode;
import org.jagatoo.opengl.enums.DrawMode;
import org.jagatoo.opengl.enums.FaceCullMode;
import org.jagatoo.opengl.enums.ShadeModel;
import org.jagatoo.opengl.enums.TestFunction;
import org.jagatoo.opengl.enums.TextureMode;
import org.openmali.FastMath;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.util.MatrixUtils;

/**
 * Converts abstract ASE data to scenegraph data.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class AseConverter
{
    private static final GeometryType GEOM_TYPE = GeometryType.TRIANGLE_ARRAY;
    
    private static final boolean TGT_GROUPS_ONLY = false;
    private static final boolean TGT_NO_GROUPS = false;
    private static final boolean TGT_NO_TRANSLATE_TG = false;
    //private static final boolean TGT_NO_ROOT = false;
    
    private final HashMap<String, NamedObject> appearanceCache = new HashMap<String, NamedObject>();
    
    /**
     * Converts the mesh into a TrangleArray, offsetting the coordinates by the
     * given vector.
     * 
     * @param pivotPoint Vector by which the array will be offset
     */
    private NamedObject getTriangleArray( AseMesh aseMesh, int matID, AseMap map, Tuple3f pivotPoint, GeometryFactory geomFactory )
    {
        try
        {
            int nFaces;
            
            if ( matID < 0 )
            {
                nFaces = aseMesh.numFaces;
            }
            else
            {
                nFaces = aseMesh.totals[ matID ];
            }
            
            JAGTLog.debug( "Getting mesh for sub-material ", matID, " with ", nFaces, " faces" );
            
            NamedObject geom = geomFactory.createGeometry( "", GEOM_TYPE, 3, nFaces * 3, 0, null );
            
            // convert the faces and vertices into a vertex list. For each face
            // we have 3 vertices, each of which has 3 floats
            float[] coords = new float[ nFaces * 3 * 3 ];
            
            // float norms[] = new float[nFaces*3*3];
            int n = 0;
            
            // Builds the geometry, offsetting it by the passed pivot point
            for ( int f = 0; f < aseMesh.numFaces; f++ )
            {
                if ( ( aseMesh.faceMat[ f ] == matID ) || ( matID < 0 ) )
                {
                    for ( int v = 0; v < 3; v++ )
                    {
                        if ( aseMesh.convertMeshCoordinates )
                        {
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 0 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 1 ] - pivotPoint.getX(); // x1
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 1 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 2 ] - pivotPoint.getY(); // y2
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 2 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 0 ] - pivotPoint.getZ(); // z0
                        }
                        else
                        {
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 0 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 0 ] - pivotPoint.getX(); // x0
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 1 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 1 ] - pivotPoint.getY(); // y1
                            coords[ ( ( ( n * 3 ) + v ) * 3 ) + 2 ] = aseMesh.vertices[ ( aseMesh.faces[ ( f * 3 ) + v ] * 3 ) + 2 ] - pivotPoint.getZ(); // z2
                        }
                    }
                    
                    /*
                     * v1.sub(b.p.point, a.p.point); v2.sub(c.p.point,
                     * a.p.point); faceNormal.cross(v1, v2);
                     */
                    n++;
                }
            }
            
            if ( n != nFaces )
            {
                throw new Error( "Invalid number of vertices initialized" );
            }
            
            geomFactory.setCoordinates( geom, GEOM_TYPE, 0, coords, 0, nFaces * 3 );
            
            // convert the normals coordinates. For each face we have
            // three normal vertices, each of which has 3 floats
            if ( aseMesh.normals != null )
            {
                n = 0;
                
                float[] norms = new float[ nFaces * 3 * 3 ];
                
                for ( int f = 0; f < aseMesh.numFaces; f++ )
                {
                    if ( ( aseMesh.faceMat[ f ] == matID ) || ( matID < 0 ) )
                    {
                        for ( int v = 0; v < 3; v++ )
                        {
                            if ( aseMesh.convertMeshCoordinates )
                            {
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 0 ] = -aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 2 ]; // x
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 1 ] = +aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 1 ]; // y
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 2 ] = +aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 0 ]; // z
                            }
                            else
                            {
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 0 ] = +aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 0 ]; // x
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 1 ] = +aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 1 ]; // y
                                norms[ ( ( ( n * 3 ) + v ) * 3 ) + 2 ] = +aseMesh.normals[ ( ( ( f * 3 ) + v ) * 3 ) + 2 ]; // z
                            }
                        }
                        
                        n++;
                    }
                }
                
                geomFactory.setNormals( geom, GEOM_TYPE, 0, norms, 0, nFaces * 3 );
            }
            
            // convert the texture coordinates. For each face we have
            // three texture vertices, each of which has 2 floats
            if ( ( aseMesh.texVertices != null ) && ( aseMesh.texFaces != null ) && ( map != null ) )
            {
                n = 0;
                
                float[] texcoords = new float[ nFaces * 3 * 2 ];
                
                for ( int f = 0; f < aseMesh.numFaces; f++ )
                {
                    if ( ( aseMesh.faceMat[ f ] == matID ) || ( matID < 0 ) )
                    {
                        for ( int v = 0; v < 3; v++ )
                        {
                            texcoords[ ( ( ( n * 3 ) + v ) * 2 ) + 0 ] = aseMesh.texVertices[ ( aseMesh.texFaces[ ( f * 3 ) + v ] * 2 ) + 0 ] / map.uTiling; // x
                            texcoords[ ( ( ( n * 3 ) + v ) * 2 ) + 1 ] = aseMesh.texVertices[ ( aseMesh.texFaces[ ( f * 3 ) + v ] * 2 ) + 1 ] / map.vTiling; // y
                        }
                        
                        n++;
                    }
                }
                
                geomFactory.setTexCoords( geom, GEOM_TYPE, 0, 2, 0, texcoords, 0, nFaces * 3 );
            }
            
            return ( geom );
        }
        catch ( Exception e )
        {
            JAGTLog.print( e );
            throw new Error( e );
        }
    }
    
    /**
     * Converts the material into an extended appearance object for a Java3D
     * scenegraph.
     */
    private NamedObject getAppearance( AseGeom geom, AseMaterial aseMat, AppearanceFactory appFactory, URL baseURL )
    {
        NamedObject app = null;
        
        if ( ( aseMat.name != null ) && ( aseMat.name.length() > 0 ) )
        {
            app = appearanceCache.get( aseMat.name );
            
            if ( app != null )
            {
                return ( app );
            }
        }
        
        // if (cachedAppearance != null) return cachedAppearance;
        app = appFactory.createAppearance( aseMat.name, 0 );
        
        boolean hasTexture = false;
        
        // calculate the rendering attributes
        NamedObject ra = appFactory.createRenderingAttributesAttributes( "" );
        appFactory.setRenderingAttribsDepthBufferEnabled( ra, true );
        appFactory.setRenderingAttribsDepthBufferWriteEnabled( ra, true );
        
        // calculate material
        NamedObject mat = appFactory.createMaterial( "" );
        
        appFactory.setMaterialAmbientColor( mat, 0.75f, 0.75f, 0.75f );
        appFactory.setMaterialDiffuseColor( mat, 0.75f, 0.75f, 0.75f );
        appFactory.setMaterialSpecularColor( mat, 0f, 0f, 0f );
        appFactory.setMaterialLightingEnabled( mat, false );
        appFactory.applyMaterial( mat, app );
        
        // set up the polygon attributes
        NamedObject polyAttribs = appFactory.createPolygonAttributes( "" );
        appFactory.setPolygonAttribsDrawMode( polyAttribs, DrawMode.FILL );
        appFactory.setPolygonAttribsFaceCullMode( polyAttribs, FaceCullMode.BACK );
        
        // calculate transparency, create the texture
        
        /*
        aseMat.opacityMap = new AseMap();
        aseMat.opacityMap.bitmap = "bigtreetex.png";
        */
        
        if ( aseMat.opacityMap != null )
        {
            String texName = aseMat.opacityMap.bitmap;
            
            // Fix filename
            if ( texName.startsWith( "\\\\" ) )
            {
                texName = texName.substring( 2 );
            }
            texName = texName.replace( '\\', '/' );
            
            JAGTLog.debug( "opacity texture name is ", texName );
            
            AbstractTexture tex = appFactory.loadOrGetTexture( texName, baseURL, true, true, true, true, true );
            
            JAGTLog.debug( "setting name to ", texName );
            JAGTLog.debug( "checked name is ", tex.getName() );
            
            appFactory.applyTexture( tex, 0, app );
            
            // we need to set the blending so that the opacity map blends
            appFactory.setRenderingAttribsAlphaTestFunction( ra, TestFunction.GREATER );
            appFactory.setRenderingAttribsAlphaTestValue( ra, 0.05f );
            appFactory.setPolygonAttribsFaceCullMode( polyAttribs, FaceCullMode.NONE );
            
            if ( tex.getFormat().hasAlpha() || ( aseMat.opacityMap.amount != 1.0f ) )
            {
                NamedObject transpAttribs = appFactory.createTransparencyAttributes( "" );
                appFactory.setTransparencyAttribsBlendMode( transpAttribs, BlendMode.BLENDED );
                appFactory.setTransparencyAttribsTransparency( transpAttribs, /*1.0f - */aseMat.opacityMap.amount );
                appFactory.applyTransparancyAttributes( transpAttribs, app );
            }
            
            hasTexture = true;
        }
        else if ( ( aseMat.diffuseMap != null ) && ( aseMat.diffuseMap.bitmap != null ) )
        {
            String texName = aseMat.diffuseMap.bitmap;
            
            // Fix filename
            if ( texName.startsWith( "\\\\" ) )
            {
                texName = texName.substring( 2 );
            }
            texName = texName.replace( '\\', '/' );
            
            JAGTLog.debug( "texture name is ", texName );
            
            AbstractTexture tex = appFactory.loadOrGetTexture( texName, baseURL, true, false, true, true, true );
            
            appFactory.applyTexture( tex, 0, app );
            
            hasTexture = true;
        }
        
        appFactory.applyRenderingAttributes( ra, app );
        appFactory.applyPolygonAttributes( polyAttribs, app );
        
        if ( !hasTexture )
        {
            NamedObject colorAttribs = appFactory.createColoringAttributes( "" );
            appFactory.setColoringAttribsColor( colorAttribs, new float[] { geom.wireframeColor.getRed(), geom.wireframeColor.getGreen(), geom.wireframeColor.getBlue() }, 0, 3 );
            appFactory.setColoringAttribsShadeModel( colorAttribs, ShadeModel.GOURAUD );
            appFactory.applyColoringAttributes( colorAttribs, app );
        }
        else
        {
            NamedObject texAttribs = appFactory.createTextureAttributes( "" );
            appFactory.setTextureAttribsTextureMode( texAttribs, TextureMode.MODULATE );
            appFactory.setTextureAttribsTextureBlendColor( texAttribs, new float[] { 1f, 0.5f, 0.5f, 0.1f }, 0, 4 );
            appFactory.applyTextureAttributes( texAttribs, 0, app );
        }
        
        if ( ( aseMat.name != null ) && ( aseMat.name.length() > 0 ) )
        {
            appearanceCache.put( app.getName(), app );
        }
        
        return ( app );
    }
    
    /**
     * @return the shape relitive to a given offset (usually the object pivot point).
     */
    private NamedObject getShape( AseFile file, AseGeom aseGeom, Tuple3f geomOffset, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, NodeFactory nodeFactory )
    {
        JAGTLog.debug( "Shape ", aseGeom.name, " using material ref ", aseGeom.materialRef );
        
        AseMaterial m;
        if ( file.materials.size() > 0 )
            m = file.materials.get( aseGeom.materialRef );
        else
            m = new AseMaterial();
        
        if ( m.subMaterials.size() > 0 )
        {
            NamedObject group = nodeFactory.createSimpleGroup( aseGeom.name, BoundsType.SPHERE );
            JAGTLog.debug( "   Shape ", aseGeom.name, " has sub-materials, building multiple objects" );
            
            for ( int i = 0; i < m.subMaterials.size(); i++ )
            {
                try
                {
                    AseMaterial mat = m.subMaterials.get( i );
                    NamedObject app = getAppearance( aseGeom, mat, appFactory, baseURL );
                    NamedObject geom = getTriangleArray( aseGeom.mesh, i, mat.diffuseMap, geomOffset, geomFactory );
                    
                    NamedObject shape = nodeFactory.createShape( "", geom, app, BoundsType.SPHERE );
                    
                    nodeFactory.addNodeToGroup( shape, group );
                }
                catch ( Throwable t )
                {
                    t.printStackTrace();
                }
            }
            
            return ( group );
        }
        
        try
        {
            NamedObject app = getAppearance( aseGeom, m, appFactory, baseURL );
            NamedObject geom = getTriangleArray( aseGeom.mesh, -1, m.diffuseMap, geomOffset, geomFactory );
            
            NamedObject shape = nodeFactory.createShape( "", geom, app, BoundsType.SPHERE );
            
            return ( shape );
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
            
            return ( nodeFactory.createDummyNode() );
        }
    }
    
    /*
     * @return a wireframe for the mesh. This is mostly for debugging than anything.
     */
    /*
    private NamedObject getWireframeShape( AseMesh aseMesh, AseMap map, GeometryFactory geomFactory, AppearanceFactory appFactory, NodeFactory nodeFactory )
    {
        NamedObject geom = getTriangleArray( aseMesh, 0, map, geomFactory );
        
        NamedObject app = appFactory.createAppearance( "", 0 );
        
        NamedObject polygonAttribs = appFactory.createPolygonAttributes( "" );
        appFactory.setPolygonAttribsDrawMode( polygonAttribs, DrawMode.LINE );
        
        appFactory.applyPolygonAttributes( polygonAttribs, app );
        
        NamedObject shape = nodeFactory.createShape( geom.getName(), geom, app, BoundsType.SPHERE );
        
        return ( shape );
    }
    */
    
    /*
     * Translates the given transform group by the sum of the objects pivot
     * point less the given offset (typically the parents pivot point).
     * 
     * @param group the TransformGroup to which the translation will be applied
     * @param the amout this objects pivot point will be offset by. The
     *            translation is this object's pivot point minus the pivot
     *            offset (which is normally the pivot point of the parent group.
     */
    /*
    private Matrix4f computePivotTransform( AseGeom aseGeom, Tuple3f pivotOffset )
    {
        Tuple3f pivot = aseGeom.getPivot();
        
        // Gets the translation from the origin from this object,
        // negativly offsets it with the pivot point.
        
        Matrix4f transform = new Matrix4f( 1f, 0f, 0f, pivot.getX() - pivotOffset.getX(),
                                           0f, 1f, 0f, pivot.getY() - pivotOffset.getY(),
                                           0f, 0f, 1f, pivot.getZ() - pivotOffset.getZ(),
                                           0f, 0f, 0f, 1f
                                         );
        
        return ( transform );
    }
    */
    
    /**
     * Translates the given transform group by the sum of the objects pivot
     * point less the given offset (typically the parents pivot point).
     * 
     * @param group the TransformGroup to which the translation will be applied
     * @param the amout this objects pivot point will be offset by. The
     *            translation is this object's pivot point minus the pivot
     *            offset (which is normally the pivot point of the parent group.
     */
    private void applyPivotTranslation( AseGeom aseGeom, Tuple3f pivotOffset, NamedObject tg, NodeFactory nodeFactory )
    {
        Tuple3f pivot = aseGeom.getPivot();
        
        // Gets the translation from the origin from this object,
        // negativly offsets it with the pivot point.
        
        nodeFactory.setTransformGroupTranslation( tg,
                                                  pivot.getX() - pivotOffset.getX(),
                                                  pivot.getY() - pivotOffset.getY(),
                                                  pivot.getZ() - pivotOffset.getZ()
                                                );
    }
    
    /*
     * Returns a TransformGroup containing the geometry loaded relitive to the
     * objects pivot point and translated into place.
     * 
     * @param file the parent AseFile
     * @param pivotOffset vector that the geometry array will be offset by (this
     *            is NOT a translation but rather the actual values the array of
     *            geometry data will be offset by. This value is commonly the
     *            pivot point of the parent node
     * 
     * @return a TransformGroup containing the geometry loaded relitive to the
     *         objects pivot point and translated into place.
     */
    /*
    private NamedObject getTransformGroup( AseFile file, AseGeom aseGeom, Tuple3f pivotOffset, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, NodeFactory nodeFactory )
    {
        if ( aseGeom instanceof AseGroup )
        {
            final AseGroup aseGroup = (AseGroup)aseGeom;
            
            NamedObject tg = nodeFactory.createTransformGroup( aseGroup.name, computePivotTransform( aseGroup, pivotOffset ), BoundsType.SPHERE );
            
            Iterator<AseGeom> it = aseGroup.objects.values().iterator();
            
            // iterates though all child geometry adding them to root TG
            while ( it.hasNext() )
            {
                nodeFactory.addNodeToGroup( getTransformGroup( file, it.next(), aseGroup.getPivot(), appFactory, baseURL, geomFactory, nodeFactory ), tg );
            }
            
            //applyPivotTranslation( aseGroup, tg, pivotOffset );
            
            return ( tg );
        }
        else
        {
            //NamedObject tg = nodeFactory.createTransformGroup( aseGroup.name, BoundsType.SPHERE );
            NamedObject tg = nodeFactory.createTransformGroup( "", computePivotTransform( aseGeom, pivotOffset ), BoundsType.SPHERE );
            
            // Loads the GEOM
            nodeFactory.addNodeToGroup( getShape( file, aseGeom, aseGeom.getPivot(), appFactory, baseURL, geomFactory, nodeFactory ), tg );
            
            // Applies translation using the passed pivot offset (ie. parent pivot point).
            //applyPivotTranslation( aseGeom, tg, pivotOffset );
            
            return ( tg );
        }
    }
    */
    
    /**
     * Adds the geometry associated with this node to the passed TransformGroup.
     * The geometry is created relitive to this nodes pivot point. Used by
     * createTransformGroupTree to add the associated geom to the tree.
     * 
     * @param tg
     * @param namedObjects
     * @param file
     * @param aseGeom
     * @param geomPivot
     * @param flags
     */
    private void addGeometry( NamedObject tg, AseFile file, AseGeom aseGeom, Tuple3f geomPivot, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, NodeFactory nodeFactory, SpecialItemsHandler siHandler )
    {
        if ( aseGeom instanceof AseGroup )
        {
            final AseGroup aseGroup = (AseGroup)aseGeom;
            
            ( (NamableObject)tg ).setName( aseGroup.name );
            
            // Loops through all child geom and groups, recursivly adding them to
            // the Geometry TransformGroup
            for ( AseGeom aseGeom2: aseGroup.objects.values() )
            {
                createTransformGroupTree( tg, file, aseGeom2, geomPivot, appFactory, baseURL, geomFactory, nodeFactory, siHandler );
            }
        }
        else
        {
            // Gets the shape relitive to the nodes pivot point
            nodeFactory.addNodeToGroup( getShape( file, aseGeom, geomPivot, appFactory, baseURL, geomFactory, nodeFactory ), tg );
        }
    }
    
    /**
     * Creates a TransformGroup to contain the geometry data (loaded relitive to
     * the pivot point). A second TransformGroup is created and is made a parent
     * of the first one this one additionally is translated into place (the
     * pivot point, less the pivotOffset of the parent) If this node is named,
     * the former TransformGroup is added to the map of named nodes. The latter
     * TransformGroup is added to the tree.
     * 
     * @param tree The current TransformGroup branch to which this Node will be
     *            added
     * @param namedObjects map of the node names to their TransformGroup to which
     *            the created TransformGroup will be added
     * @param file the parent AseFile
     * @param pivotOffset the amount which the translation is offset by (this is
     *            usually the pivot point of the parent node)
     * @param flags various options
     */
    private void createTransformGroupTree( NamedObject parentGroup, AseFile file, AseGeom aseGeom, Tuple3f pivotOffset, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, NodeFactory nodeFactory, SpecialItemsHandler siHandler )
    {
        if ( TGT_GROUPS_ONLY && TGT_NO_GROUPS )
        {
            throw new IllegalArgumentException( "Can't use both AseFile.TGT_GROUPS_ONLY and AseFile.TGT_NOGROUPS together.  Perhaps you want the AseFile.getModel() method" );
        }
        
        // Geometry Transform
        NamedObject group;
        
        // Pivot point that the geometry will be loaded relitive to
        Tuple3f pivot;
        
        // Constructs a new TransformGroup for this nodes geometry and extracts
        // the pivot if it's a Group and Groups are not disabled or it's a GEOM
        // and it's not GROUP_ONLY
        if ( !TGT_GROUPS_ONLY || ( ( aseGeom instanceof AseGroup ) && !TGT_NO_GROUPS ) )
        {
            if ( !"".equals( aseGeom.name ) )
                group = nodeFactory.createTransformGroup( aseGeom.name, BoundsType.SPHERE );
            else
                group = nodeFactory.createTransformGroup( "", BoundsType.SPHERE );
            
            if ( group.getName().length() > 0 )
                siHandler.addSpecialItem( SpecialItemType.NESTED_TRANSFORM, group.getName(), group );
            
            // The TransformGroup to which the translation will be applied
            NamedObject translation;
            
            // Uses the geom TransformGroup for the translation
            if ( TGT_NO_TRANSLATE_TG )
            {
                translation = group;
            }
            else
            {
                // Creates a new TransformGroup to hold the translation and the
                // geom TransformGroup as a child
                
                if ( ( aseGeom.name != null ) && ( aseGeom.name.length() > 0 ) )
                    translation = nodeFactory.createTransformGroup( aseGeom.name + "Translation", BoundsType.SPHERE );
                else
                    translation = nodeFactory.createTransformGroup( "", BoundsType.SPHERE );
                
                nodeFactory.addNodeToGroup( group, translation );
                
                if ( translation.getName().length() >  0 )
                {
                    siHandler.addSpecialItem( SpecialItemType.NESTED_TRANSFORM, translation.getName(), translation );
                }
            }
            
            // Sets the pivot used to offset the loaded geometry to this objects
            // pivot
            pivot = aseGeom.getPivot();
            
            // Apply the translation to move this TransformGroup into it's
            // correct place
            // thus compensating for the pivot offset when loading
            applyPivotTranslation( aseGeom, pivotOffset, translation, nodeFactory );
            
            // Adds the resultant TransformGroup to the TransformGroup tree
            nodeFactory.addNodeToGroup( translation, parentGroup );
        }
        else
        {
            // This Shape will be added to the parent TransformGroup and it will
            // be loaded relitive to the pivot of the parent
            
            group = parentGroup;
            pivot = pivotOffset;
        }
        
        // Adds geometry to specified geometry group
        addGeometry( group, file, aseGeom, pivot, appFactory, baseURL, geomFactory, nodeFactory, siHandler );
    }
    
    /**
     * <p>
     * Constructs a tree of TransformGroupS, obeying groupings, and group/node
     * pivot points. A TransformGroup is created for each GROUP and GEOM
     * objects. The former will have as it's children, all GEOM TransformGroups
     * following the structure from the ase file and the latter will simply
     * contain the Geomtry data (adjusted relitive to the pivot point)
     * translated into it's correct place. Thus, just as in the modelling
     * program if the group is transformed, it's children will be transformed
     * with it, but if a child is transformed then only it will be transformed.
     * The resulting tree is returned, and for conveniance (to avoid parsing the
     * Scenegraph), all nodes which had name in the ase file are added to a Map
     * as well as the resultant TransformGroup which is added with the name
     * "Root". This method is recommended when the group structure is important
     * and there are "moving parts" i.e. models within the ase file which will
     * need to be transformed independantly from each other. If this isn't
     * required and just the "flat" model is needed, then getModel is better to
     * use.
     * </p>
     * 
     * <p>
     * Much of the behaviour of this method can be tweaked by passing various
     * flags. For exmaple, groups can be ignored. See the documentation for the
     * respective flags for details on what they cause
     * </p>
     * 
     * @param aseFile
     * @param orientationAngle XYZ angles to which final TG will be roatated
     *                         (in addition to 90 degrees if convertZup2Yup is set)
     * @param appFactory
     * @param baseURL
     * @param geomFactory
     * @param convertZup2Yup
     * @param scale
     * @param nodeFactory
     * @param siHandler
     * @param modelRootGroup
     */
    private void getTransformGroupTree( AseFile aseFile, Tuple3f orientationAngle, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, SpecialItemsHandler siHandler, NamedObject modelRootGroup )
    {
        if ( TGT_GROUPS_ONLY && TGT_NO_GROUPS )
        {
            throw new IllegalArgumentException( "Can't use both AseFile.TGT_GROUPS_ONLY and AseFile.TGT_NOGROUPS together.  Perhaps you want the AseFile.getModel() method" );
        }
        
        // Add in 90 degree X-axis rotation if set
        if ( convertZup2Yup )
        {
            orientationAngle.addX( FastMath.PI_HALF );
        }
        
        Matrix4f transform = MatrixUtils.eulerToMatrix4f( orientationAngle );
        
        if ( scale != 1.0f )
        {
            Matrix4f scaleMat = new Matrix4f();
            scaleMat.setIdentity();
            scaleMat.m00( scale );
            scaleMat.m11( scale );
            scaleMat.m22( scale );
            
            transform.mul( scaleMat );
        }
        
        NamedObject treeRoot = nodeFactory.createTransformGroup( "MasterRoot", transform, BoundsType.SPHERE );
        siHandler.addSpecialItem( SpecialItemType.NESTED_TRANSFORM, treeRoot.getName(), treeRoot );
        siHandler.addSpecialItem( SpecialItemType.MAIN_GROUP, treeRoot.getName(), treeRoot );
        
        // Adds all objects to the tree
        Collection<AseNode> c = aseFile.objects.values();
        Iterator< AseNode > it = c.iterator();
        while ( it.hasNext() )
        {
            AseGeom g = (AseGeom)it.next();
            
            createTransformGroupTree( treeRoot, aseFile, g, new Tuple3f( 0f, 0f, 0f ), appFactory, baseURL, geomFactory, nodeFactory, siHandler );
        }
        
        nodeFactory.addNodeToGroup( treeRoot, modelRootGroup );
    }
    
    /**
     * Constructs a TransformGroup tree, obeying groupings, and group/node pivot
     * points. Groups and Geometry with names are also added to the passed
     * hashmap.
     * 
     * @param aseFile
     * @param appFactory
     * @param baseURL
     * @param geomFactory
     * @param convertZup2Yup
     * @param scale
     * @param nodeFactory
     * @param siHandler
     * @param modelRootGroup
     * 
     * @see #getTransformGroupTree(AseFile, int, Tuple3f, LoadedGraph)
     */
    public static void getTransformGroupTree( AseFile aseFile, AppearanceFactory appFactory, URL baseURL, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, SpecialItemsHandler siHandler, NamedObject modelRootGroup )
    {
        AseConverter converter = new AseConverter();
        
        converter.getTransformGroupTree( aseFile, new Tuple3f( 0f, 0f, 0f ), appFactory, baseURL, geomFactory, convertZup2Yup, scale, nodeFactory, siHandler, modelRootGroup );
    }
}
