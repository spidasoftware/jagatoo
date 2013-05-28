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
package org.jagatoo.loaders.models.cal3d.loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jagatoo.loaders.models.cal3d.core.CalCoreAnimation;
import org.jagatoo.loaders.models.cal3d.core.CalCoreMaterial;
import org.jagatoo.loaders.models.cal3d.core.CalCoreMesh;
import org.jagatoo.loaders.models.cal3d.core.CalCoreModel;
import org.jagatoo.loaders.models.cal3d.core.CalCoreSkeleton;
import org.jagatoo.loaders.models.cal3d.core.CalLoader;
import org.jagatoo.loaders.models.cal3d.core.CalModel;
import org.jagatoo.loaders.models.cal3d.loader.KCal3dDefinition.Cal3dAnimDef;
import org.jagatoo.loaders.models.cal3d.loader.KCal3dDefinition.Cal3dModelDef;
import org.jagatoo.loaders.models.cal3d.loader.KCal3dDefinition.Cal3dSubMeshDef;
import org.jagatoo.logging.JAGTLog;

/**
 * 
 * @author kman
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public class KCal3dLoader
{
    private static Map<String, CalCoreModel> coreModelCache = new HashMap<String, CalCoreModel>();
    private static Map<String, CalCoreMaterial> coreMaterialCache = new HashMap<String, CalCoreMaterial>();
    
    public static Cal3dModelDef loadCfg( InputStream in, URL baseURL )
    {
        Cal3dModelDef data = new Cal3dModelDef();
        
        try
        {
            BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
            
            data.baseURL = baseURL;
            JAGTLog.debug( "Path = " + data.baseURL );
            
            String line;
            while ( ( line = br.readLine() ) != null )
            {
                if ( line.startsWith( "#" ) )
                {
                    // Comment
                }
                else if ( line.equals( "" ) )
                {
                    // Empty line
                }
                else if ( line.startsWith( "data.path=" ) )
                {
                    final String path = line.substring( line.lastIndexOf( "=" ) + 1 );
                    data.baseURL = new URL( data.baseURL, path );
                    JAGTLog.debug( "[KCal3dLoader] data.path = " + data.baseURL );
                }
                else if ( line.startsWith( "skeleton=" ) )
                {
                    final String skeFile = line.substring( line.lastIndexOf( "=" ) + 1 );
                    data.skeleton = new URL( data.baseURL, skeFile );
                    JAGTLog.debug( "[KCal3dLoader] Set skeleton to " + data.skeleton );
                }
                else if ( line.startsWith( "animation=" ) )
                {
                    final String animFile = line.substring( line.lastIndexOf( "=" ) + 1 );
                    URL animURL = new URL( data.baseURL, animFile );
                    String name = animFile.substring( 0, animFile.lastIndexOf( "." ) );
                    name = name.replace( '\\', '/' );
                    name = name.substring( name.lastIndexOf( "/" ) + 1 );
                    Cal3dAnimDef anim = new Cal3dAnimDef( name, animURL );
                    data.animations.add( anim );
                    JAGTLog.debug( "[KCal3dLoader] Added anim " + animURL );
                }
                else if ( line.startsWith( "mesh=" ) )
                {
                    final String meshFile = line.substring( line.lastIndexOf( "=" ) + 1 );
                    URL meshURL = new URL( data.baseURL, meshFile );
                    Cal3dSubMeshDef subMesh = new Cal3dSubMeshDef( meshURL );
                    data.meshes.add( subMesh );
                    JAGTLog.debug( "[KCal3dLoader] Added mesh " + meshURL );
                }
                else if ( line.startsWith( "material=" ) )
                {
                    final String matFile = line.substring( line.lastIndexOf( "=" ) + 1 );
                    data.material = new URL( data.baseURL, matFile );
                    JAGTLog.debug( "[KCal3dLoader] Set material to " + data.material );
                }
                else
                {
                    JAGTLog.debug( "[KCal3dLoader] Unsupported line : " + line );
                }
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        
        return ( data );
    }
    
    public static CalModel getCalModel( Cal3dModelDef modelDef, String name, URL baseURL )
    {
        try
        {
            CalCoreModel core = null;
            if ( ( name != null ) && ( coreModelCache.containsKey( name ) ) )
            {
                core = coreModelCache.get( name );
            }
            else
            {
                core = loadCoreModel( modelDef, modelDef.skeleton, name );
                if ( name != null )
                    coreModelCache.put( name, core );
            }
            
            CalModel model = new CalModel( core );
            for ( String s : core.getCoreMeshIds() )
            {
                model.attachMesh( s );
            }
            model.setLodLevel( 1f );
            
            List<CalCoreMaterial> materials = new Vector<CalCoreMaterial>();
            CalCoreMaterial mat = null;
            if ( ( modelDef.material != null ) && ( coreMaterialCache.containsKey( modelDef.material.toExternalForm() ) ) )
            {
                mat = coreMaterialCache.get( modelDef.material.toExternalForm() );
            }
            else
            {
                mat = CalLoader.loadCoreMaterial( modelDef.material );
                if ( modelDef.material != null )
                    coreMaterialCache.put( modelDef.material.toExternalForm(), mat );
            }
            mat.setBaseURL( modelDef.baseURL );
            materials.add( mat );
            model.setMaterialSet( materials );
            
            model.update( 0.0f );
            
            return ( model );
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            
            return ( null );
        }
    }
    
    public static CalCoreModel loadCoreModel( Cal3dModelDef modelDef, URL modelResource, String name )
    {
        try
        {
            CalCoreModel coreModel;
            CalCoreSkeleton skeleton = CalLoader.loadCoreSkeleton( modelResource );
            coreModel = new CalCoreModel( name, skeleton );
            if ( modelDef.mesh != null && !modelDef.mesh.toExternalForm().equals( "" ) )
            {
                CalCoreMesh coreMesh = CalLoader.loadCoreMesh( modelDef.mesh );
                coreMesh.skin = modelDef.skin;
                if ( modelDef.material != null && !modelDef.material.toExternalForm().equals( "" ) )
                {
                    CalCoreMaterial calmat = CalLoader.loadCoreMaterial( modelDef.material );
                    coreMesh.material = modelDef.material;
                    coreModel.addCoreMaterial( modelDef.material.toExternalForm(), calmat );
                }
                
                coreModel.addCoreMesh( modelDef.mesh.toExternalForm(), coreMesh );
            }
            
            for ( Cal3dSubMeshDef subMesh : modelDef.meshes )
            {
                CalCoreMesh coreMesh = CalLoader.loadCoreMesh( subMesh.mesh );
                coreMesh.skin = subMesh.skin;
                if ( ( subMesh.material != null ) && !subMesh.material.equals( "" ) )
                {
                    CalCoreMaterial calmat = CalLoader.loadCoreMaterial( modelDef.material );
                    calmat.setBaseURL( modelDef.baseURL );
                    coreMesh.material = modelDef.material;
                    coreModel.addCoreMaterial( modelDef.material.toExternalForm(), calmat );
                }
                
                coreModel.addCoreMesh( subMesh.mesh.toExternalForm(), coreMesh );
            }
            
            for ( Cal3dAnimDef anim : modelDef.animations )
            {
                CalCoreAnimation coreAnimation = CalLoader.loadCoreAnimation( anim.anim, anim.name );
                coreModel.addCoreAnimation( anim.name, coreAnimation );
            }
            
            return ( coreModel );
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            
            return ( null );
        }
    }
}
