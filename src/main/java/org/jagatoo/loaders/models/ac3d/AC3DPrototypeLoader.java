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
/**
 * Copyright (c) 2003-2007, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.ac3d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.GeometryFactory.GeometryType;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.opengl.enums.BlendFunction;
import org.jagatoo.opengl.enums.BlendMode;
import org.jagatoo.opengl.enums.DrawMode;
import org.jagatoo.opengl.enums.FaceCullMode;
import org.jagatoo.opengl.enums.ShadeModel;
import org.jagatoo.opengl.enums.TextureFormat;
import org.jagatoo.opengl.enums.TextureMode;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.vecmath2.Matrix4f;

/**
 * Parses the AC3D data file into a java model.
 * 
 * @author Jeremy
 * @author Marvin Froehlich (aka Qudus)
 * 
 * @version 2.0
 */
public class AC3DPrototypeLoader
{
    /**
     * Reads the header block.
     */
    private static int loadHeader( BufferedReader reader ) throws IOException
    {
        String header = reader.readLine();
        
        String filetype = header.substring( 0, 4 );
        
        if ( !( filetype.equals( "AC3D" ) ) )
        {
            System.out.println( "File is not an AC3D file" );
            System.out.println( "Header read: " + header );
            
            throw new IOException( "File is not an AC3D file" );
        }
        
        String versionText = header.substring( 4 );
        int formatVersion = Integer.parseInt( versionText, 16 );
        
        //System.out.println( "Found AC3D file of format version " + formatVersion );
        
        return ( formatVersion );
    }
    
    /**
     * Loads an AC3DMaterial from the data.
     * 
     * @param data The data from the file
     * 
     * @return The <CODE>AC3DMaterial</CODE>
     */
    private static Object[] loadMaterial( String data, AppearanceFactory appFactory )
    {
        // The name of the material
        String name;
        // Color values
        float r, g, b;
        // The shinyness
        float shininess;
        // The translucancy of the material
        float translucency;
        
        StringTokenizer tokenizer = new StringTokenizer( data, " " );
        
        tokenizer.nextToken(); // Material
        
        name = tokenizer.nextToken();
        NamedObject material = appFactory.createMaterial( name );
        
        tokenizer.nextToken(); // rgb
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        appFactory.setMaterialDiffuseColor( material, r, g, b );
        
        tokenizer.nextToken();// amb
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        appFactory.setMaterialAmbientColor( material, r, g, b );
        
        tokenizer.nextToken();// emis
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        appFactory.setMaterialEmissiveColor( material, r, g, b );
        
        tokenizer.nextToken();// spec
        r = Float.parseFloat( tokenizer.nextToken() );
        g = Float.parseFloat( tokenizer.nextToken() );
        b = Float.parseFloat( tokenizer.nextToken() );
        appFactory.setMaterialSpecularColor( material, r, g, b );
        
        tokenizer.nextToken();// shi
        shininess = Float.parseFloat( tokenizer.nextToken() );
        appFactory.setMaterialShininess( material, shininess );
        
        tokenizer.nextToken();// trans
        translucency = Float.parseFloat( tokenizer.nextToken() );
        
        return ( new Object[] { material, translucency } );
    }
    
    /**
     * Loads an AC3DSurface from the file
     * 
     * @param reader The reader to use
     * 
     * @return The <CODE>AC3DSurface</CODE>
     * 
     * @throws IOException Thrown if an IO error happens
     * @throws FileFormatException Thrown if the file does not match the AC3D specification
     */
    private static AC3DSurface loadSurface( BufferedReader reader, float texRepX, float texOffsetX, float texRepY, float texOffsetY ) throws IOException
    {
        // The type of this surface
        int type;
        // is two sided?
        boolean twoSided;
        // is shaded
        boolean shaded;
        // materials index
        int material = -1;
        // The vertecies on this surface
        int[] surfVerts;
        // The texture coordiantes for each vertex
        float[] textCoords;
        
        String token;
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer( line, " " );
        
        //System.out.println( "Surface: " + line );
        
        tokenizer.nextToken();
        int flags = Integer.parseInt( tokenizer.nextToken().substring( 2 ), 16 );
        type = ( flags & 0x0f );
        //System.out.println( "Type is: " + type );
        shaded = ( ( flags >> 4 ) & 1 ) == 1;
        // System.out.println( "Shaded: " + shaded );
        twoSided = ( ( flags >> 5 ) & 1 ) == 1;
        //System.out.println( "Two sided: " + twoSided );
        
        // read next token
        line = reader.readLine();
        tokenizer = new StringTokenizer( line, " " );
        token = tokenizer.nextToken();
        if ( token.equals( "mat" ) )
        {
            material = Integer.parseInt( tokenizer.nextToken() );
            // read next token
            line = reader.readLine();
            tokenizer = new StringTokenizer( line, " " );
            token = tokenizer.nextToken();
        }
        int numRefs = Integer.parseInt( tokenizer.nextToken() );
        
        surfVerts = new int[ numRefs ];
        textCoords = new float[ numRefs * 2 ];
        float s, t;
        boolean hasTexCoords = false;
        for ( int i = 0; i < numRefs; i++ )
        {
            line = reader.readLine();
            tokenizer = new StringTokenizer( line, " " );
            
            token = tokenizer.nextToken();
            surfVerts[i] = Integer.parseInt( token );
            
            s = Float.parseFloat( tokenizer.nextToken() ) * texRepX + texOffsetX;
            t = Float.parseFloat( tokenizer.nextToken() ) * texRepY + texOffsetY;
            if ( ( s != 0f ) || ( t != 0f ) )
            {
                hasTexCoords = true;
            }
            textCoords[ i * 2 + 0 ] = s;
            textCoords[ i * 2 + 1 ] = t;
        }
        
        return ( new AC3DSurface( type, twoSided, shaded, material, surfVerts, ( hasTexCoords ? textCoords : null ) ) );
    }
    
    private static NamedObject texAttribs = null;
    private static NamedObject coloringAttribs = null;
    
    private static NamedObject getAppearance( AC3DSurface surface, String textureName, AppearanceFactory appFactory, URL baseURL, ArrayList<NamedObject> materials, ArrayList<Float> translucencies, AC3DAppearanceCache appCache )
    {
        AbstractTexture texture = null;
        if ( ( textureName != null ) && ( !textureName.equals( "" ) ) )
        {
            texture = appFactory.loadOrGetTexture( textureName, baseURL, true, true, true, true, true );
        }
        
        boolean hasMaterial = ( ( surface.getMaterialIndex() >= 0 ) && ( surface.getMaterialIndex() < materials.size() ) );
        
        float translucency = 0f;
        if ( hasMaterial )
        {
            translucency = translucencies.get( surface.getMaterialIndex() ).floatValue();
        }
        
        final boolean isTransparent = ( ( translucency > 0f ) || ( ( texture != null ) && ( texture.getFormat() == TextureFormat.RGBA ) ) );
        
        final String transAttribsCacheKey = String.valueOf( isTransparent ) + String.valueOf( translucency );
        final String polyAttribsCacheKey = String.valueOf( surface.isTwoSided() ) + "-" + String.valueOf( surface.isLine() );
        final String appCacheKey = textureName + "-" +
                                   surface.getMaterialIndex() + "-" +
                                   transAttribsCacheKey + "-" +
                                   polyAttribsCacheKey;
        
        NamedObject appearance = appCache.appearanceCache.get( appCacheKey );
        if ( appearance != null )
        {
            return ( appearance );
        }
        
        appearance = appFactory.createAppearance( "", 0 );
        
        appFactory.applyTexture( texture, 0, appearance );
        
        if ( hasMaterial )
        {
            appFactory.applyMaterial( materials.get( surface.getMaterialIndex() ), appearance );
        }
        
        NamedObject transAttribs = appCache.transAttribsCache.get( transAttribsCacheKey );
        if ( transAttribs == null )
        {
            if ( isTransparent )
            {
                transAttribs = appFactory.createTransparencyAttributes( "" );
                appFactory.setTransparencyAttribsBlendMode( transAttribs, BlendMode.BLENDED );
                appFactory.setTransparencyAttribsTransparency( transAttribs, translucency );
                appFactory.setTransparencyAttribsSourceBlendFunc( transAttribs, BlendFunction.SRC_ALPHA );
                appFactory.setTransparencyAttribsDestBlendFunc( transAttribs, BlendFunction.ONE_MINUS_SRC_ALPHA );
            }
            else
            {
                transAttribs = appFactory.createTransparencyAttributes( "" );
                appFactory.setTransparencyAttribsBlendMode( transAttribs, BlendMode.NONE );
            }
            
            appCache.transAttribsCache.put( transAttribsCacheKey, transAttribs );
        }
        
        appFactory.applyTransparancyAttributes( transAttribs, appearance );
        
        
        NamedObject polyAttributes = appCache.polyAttribsCache.get( polyAttribsCacheKey );
        if ( polyAttributes == null )
        {
            polyAttributes = appFactory.createPolygonAttributes( "" );
            
            if ( surface.isTwoSided() )
            {
                /*
                 * set up pooly attributes for twosided lines
                 * set back face culling off, and normal flipping on for two sided shapes
                 */
                appFactory.setPolygonAttribsFaceCullMode( polyAttributes, FaceCullMode.NONE );
                appFactory.setPolygonAttribsBackfaceNormalFlip( polyAttributes, true );
            }
            else
            {
                /*
                 * set back face culling on, and normal flipping off for one sided shapes
                 */
                appFactory.setPolygonAttribsFaceCullMode( polyAttributes, FaceCullMode.BACK );
                appFactory.setPolygonAttribsBackfaceNormalFlip( polyAttributes, false );
            }
            
            if ( surface.isLine() )
            {
                /*
                 * set the polygon type to line for lines
                 */
                appFactory.setPolygonAttribsDrawMode( polyAttributes, DrawMode.LINE );
            }
            else
            {
                /*
                 * set the polygon type to line for lines
                 */
                appFactory.setPolygonAttribsDrawMode( polyAttributes, DrawMode.FILL );
            }
            
            appCache.polyAttribsCache.put( polyAttribsCacheKey, polyAttributes );
        }
        
        appFactory.applyPolygonAttributes( polyAttributes, appearance );
        
        if ( texAttribs == null )
        {
            texAttribs = appFactory.createTextureAttributes( "" );
            appFactory.setTextureAttribsTextureMode( texAttribs, TextureMode.MODULATE );
        }
        
        appFactory.applyTextureAttributes( texAttribs, 0, appearance );
        
        if ( coloringAttribs == null )
        {
            coloringAttribs = appFactory.createColoringAttributes( "" );
            appFactory.setColoringAttribsShadeModel( coloringAttribs, ShadeModel.NICEST );
        }
        
        appFactory.applyColoringAttributes( coloringAttribs, appearance );
        
        appCache.appearanceCache.put( appCacheKey, appearance );
        
        return ( appearance );
    }
    
    private static void transformVertexCoords( float[] vcs, Matrix4f t )
    {
        float x, y, z;
        
        final int n = vcs.length / 3;
        for ( int i = 0; i < n; i++ )
        {
            x = t.m00() * vcs[i * 3 + 0] + t.m01() * vcs[i * 3 + 1] + t.m02() * vcs[i * 3 + 2] + t.m03();
            y = t.m10() * vcs[i * 3 + 0] + t.m11() * vcs[i * 3 + 1] + t.m12() * vcs[i * 3 + 2] + t.m13();
            z = t.m20() * vcs[i * 3 + 0] + t.m21() * vcs[i * 3 + 1] + t.m22() * vcs[i * 3 + 2] + t.m23();
            
            vcs[i * 3 + 0] = x;
            vcs[i * 3 + 1] = y;
            vcs[i * 3 + 2] = z;
        }
    }
    
    /**
     * Loads an AC3DObject from the file
     * 
     * @param line The first line of this tag
     * @param reader The reader to read from
     * 
     * @throws IOException Thrown if there is an IO Error
     * @throws FileFormatException Thrown if the file does not match the AC3D specification
     */
    private static void loadObject( BufferedReader reader, URL baseURL, ArrayList<NamedObject> materials, ArrayList<Float> translucencies, AppearanceFactory appFactory, AC3DAppearanceCache appCache, GeometryFactory geomFactory, NodeFactory nodeFactory, boolean keepNestedTransforms, Matrix4f parentTransform, NamedObject parentGroup, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        // The object name
        String name = null;
        // The texture name
        String textureName = null;
        // The objects verticies
        float[] vertexCoords = null;
        // Texture repeat values
        float textureRepeatX = 1, textureRepeatY = 1;
        // Texture offset values
        float textureOffsetX = 0f, textureOffsetY = 0f;
        // temporary stor of surfaces
        ArrayList<AC3DSurface> tempSurfaces = new ArrayList<AC3DSurface>();
        TreeSet<Integer> surfMatIndices = new TreeSet<Integer>();
        
        NamedObject childrenGroup = null;
        boolean isGroup = false;
        
        Matrix4f objectTransform = new Matrix4f( parentTransform );
        
        String line = reader.readLine();
        
        StringTokenizer tokenizer = new StringTokenizer( line, " " );
        
        // Skip token "OBJECT"
        tokenizer.nextToken();
        
        String token = tokenizer.nextToken();
        /*
        if ( token.equals( "world" ) )
        {
            type = AC3DObject.TYPE_WORLD;
        }
        else */if ( token.equals( "group" ) )
        {
            isGroup = true;
        }
        else if ( token.equals( "poly" ) )
        {
            //type = AC3DObject.TYPE_POLY;
        }
        else
        {
            throw new IncorrectFormatException( "Object type \"" + token + "\" is not valid" );
        }
        //System.out.println( "Object type: " + type );
        
        while ( true )
        {
            line = reader.readLine();
            
            tokenizer = new StringTokenizer( line );
            
            token = tokenizer.nextToken();
            
            if ( token.equals( "name" ) )
            {
                name = tokenizer.nextToken();
                if ( name.startsWith( "\"" ) )
                {
                    if ( name.endsWith( "\"" ) )
                        name = name.substring( 1, name.length() - 1 );
                    else
                        name = name.substring( 1, name.length() );
                }
                else
                {
                    if ( name.endsWith( "\"" ) )
                        name = name.substring( 0, name.length() - 1 );
                    else
                        name = name.substring( 0, name.length() );
                }
                //System.out.println( "name: " + name );
                
                if ( isGroup )
                {
                    if ( keepNestedTransforms )
                    {
                        childrenGroup = nodeFactory.createTransformGroup( name, BoundsType.SPHERE );
                        nodeFactory.addNodeToGroup( childrenGroup, parentGroup );
                        siHandler.addSpecialItem( SpecialItemType.NESTED_TRANSFORM, name, childrenGroup );
                    }
                    else
                    {
                        childrenGroup = parentGroup;
                    }
                }
            }
            else if ( token.equals( "data" ) )
            {
                // I think this is just one line, the data block is a single line (maybe)
                line = reader.readLine();
                //System.out.println( "data tags unsupported" );
            }
            else if ( token.equals( "texture" ) )
            {
                // Read the first quote
                tokenizer.nextToken( "\"" );
                // read up to the second quote
                textureName = tokenizer.nextToken( "\"" );
            }
            else if ( token.equals( "texoff" ) )
            {
                textureOffsetX = Float.parseFloat( tokenizer.nextToken() );
                textureOffsetY = Float.parseFloat( tokenizer.nextToken() );
            }
            else if ( token.equals( "texrep" ) )
            {
                textureRepeatX = Float.parseFloat( tokenizer.nextToken() );
                textureRepeatY = Float.parseFloat( tokenizer.nextToken() );
                //System.out.println( "repy: " + repy + " repx: " + repx );
            }
            else if ( token.equals( "rot" ) )
            {
                objectTransform.m00( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m01( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m02( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m10( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m11( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m12( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m20( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m21( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m22( Float.parseFloat( tokenizer.nextToken() ) );
            }
            else if ( token.equals( "loc" ) )
            {
                objectTransform.m03( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m13( Float.parseFloat( tokenizer.nextToken() ) );
                objectTransform.m23( Float.parseFloat( tokenizer.nextToken() ) );
            }
            else if ( token.equals( "url" ) )
            {
                System.out.println( "url tag unsupported" );
            }
            else if ( token.equals( "numvert" ) )
            {
                int numvert = Integer.parseInt( tokenizer.nextToken() );
                vertexCoords = new float[ numvert * 3 ];
                
                for ( int i = 0; i < numvert; i++ )
                {
                    line = reader.readLine();
                    tokenizer = new StringTokenizer( line, " " );
                    vertexCoords[ i * 3 + 0 ] = Float.parseFloat( tokenizer.nextToken() );
                    vertexCoords[ i * 3 + 1 ] = Float.parseFloat( tokenizer.nextToken() );
                    vertexCoords[ i * 3 + 2 ] = Float.parseFloat( tokenizer.nextToken() );
                }
            }
            else if ( token.equals( "numsurf" ) )
            {
                int numsurf = Integer.parseInt( tokenizer.nextToken() );
                //System.out.println( "Reading " + numsurf + " surfaces" );
                for ( int i = 0; i < numsurf; i++ )
                {
                    //System.out.println( "Reading surface " + i );
                    AC3DSurface surface = loadSurface( reader, textureRepeatX, textureOffsetX, textureRepeatY, textureOffsetY );
                    
                    if ( ( surface.getMaterialIndex() >= 0 ) && ( surface.getMaterialIndex() < materials.size() ) )
                    {
                        surfMatIndices.add( surface.getMaterialIndex() );
                    }
                    else
                    {
                        surfMatIndices.add( -1 );
                    }
                    
                    // check we are a line, or that we have at least 3 vertecies
                    // as a poly with 3 vertecies is broked
                    if ( ( surface.isLine() ) || ( surface.getVertexReferenceCount() >= 3 ) )
                    {
                        tempSurfaces.add( surface );
                    }
                    else
                    {
                        //System.out.println( "Read broken surface" );
                        numsurf--;
                        i--;
                    }
                    //System.out.println( "Loaded surface " + i );
                }
            }
            else if ( token.equals( "kids" ) )
            {
                break;
            }
        }
        
        if ( childrenGroup != null )
        {
            if ( keepNestedTransforms )
            {
                nodeFactory.setTransformGroupTransform( childrenGroup, objectTransform );
            }
            else
            {
                objectTransform.mul( parentTransform );
                
                transformVertexCoords( vertexCoords, objectTransform );
            }
        }
        
        // at this point we should have all we need to create the object, we can add the kids later
        
        ArrayList<AC3DSurface> uniSurfs = new ArrayList<AC3DSurface>();
        int uniIdx = -1;
        for ( Integer surfMatIndex : surfMatIndices )
        {
            uniSurfs.clear();
            uniIdx++;
            
            int numVertices = 0;
            for ( int i = 0; i < tempSurfaces.size(); i++ )
            {
                AC3DSurface surface = tempSurfaces.get( i );
                
                if ( ( surface.getMaterialIndex() >= 0 ) && ( surface.getMaterialIndex() < materials.size() ) )
                {
                    if ( surface.getMaterialIndex() == surfMatIndex.intValue() )
                    {
                        uniSurfs.add( surface );
                        numVertices += surface.getVertexReferenceCount();
                    }
                }
                else if ( surfMatIndex.intValue() == -1 )
                {
                    uniSurfs.add( surface );
                    numVertices += surface.getVertexReferenceCount();
                }
            }
            
            GeometryType geomType = GeometryType.TRIANGLE_ARRAY;
            NamedObject geom = geomFactory.createGeometry( name + "_" + uniIdx, geomType, 3, numVertices, 0, null );
            int vertexIndex = 0;
            for ( int i = 0; i < uniSurfs.size(); i++ )
            {
                AC3DSurface surface = uniSurfs.get( i );
                
                for ( int j = 0; j < surface.getVertexReferenceCount(); j++ )
                {
                    int vertexRef =  surface.getVertexReferences()[j];
                    
                    geomFactory.setCoordinate( geom, geomType, vertexIndex, vertexCoords[vertexRef * 3 + 0], vertexCoords[vertexRef * 3 + 1], vertexCoords[vertexRef * 3 + 2] );
                    if ( surface.hasTextureCoordinates() )
                    {
                        geomFactory.setTexCoords( geom, geomType, 0, 2, vertexIndex, surface.getTextureCoordinates(), vertexRef * 2, 1 );
                    }
                    
                    vertexIndex++;
                }
            }
            
            NamedObject appearance = getAppearance( uniSurfs.get( 0 ), textureName, appFactory, baseURL, materials, translucencies, appCache );
            
            NamedObject shape = nodeFactory.createShape( name + "_" + uniIdx, geom, appearance, BoundsType.SPHERE );
            
            nodeFactory.addNodeToGroup( shape, parentGroup );
            siHandler.addSpecialItem( SpecialItemType.SHAPE, shape.getName(), shape );
        }
        
        // there is always one, and only one kids-token
        if ( token.equals( "kids" ) )
        {
            int numKids = Integer.parseInt( tokenizer.nextToken() );
            
            if ( numKids > 0 )
            {
                if ( !isGroup )
                {
                    throw new IncorrectFormatException( "Detected a number of kids (" + numKids + "), but no group." );
                }
                
                loadObjects( reader, numKids, baseURL, materials, translucencies, appFactory, appCache, geomFactory, nodeFactory, keepNestedTransforms, objectTransform, childrenGroup, siHandler );
            }
        }
        else
        {
            // Something is wrong with the file, the file spec says the the
            // surfaces and kids are the last tags, we have found something else
            throw new ParsingException( "\"" + token.toString() + "\"" + " found where only a 'kids' should be" );
        }
    }
    
    /**
     * The real loading method (for objects)
     */
    private static void loadObjects( BufferedReader reader, int numKids, URL baseURL, ArrayList<NamedObject> materials, ArrayList<Float> translucencies, AppearanceFactory appFactory, AC3DAppearanceCache appCache, GeometryFactory geomFactory, NodeFactory nodeFactory, boolean keepNestedTransforms, Matrix4f parentTransform, NamedObject parentGroup, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        for ( int i = 0; i < numKids; i++ )
        {
            loadObject( reader, baseURL, materials, translucencies, appFactory, appCache, geomFactory, nodeFactory, keepNestedTransforms, parentTransform, parentGroup, siHandler );
        }
    }
    
    /**
     * The real loading method
     */
    public static void load( InputStream in, URL baseURL, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, boolean keepNestedTransforms, NamedObject rootGroup, SpecialItemsHandler siHandler ) throws IOException, IncorrectFormatException, ParsingException
    {
        BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
        
        /*int fileVersion = */loadHeader( reader );
        
        ArrayList<NamedObject> materials = new ArrayList<NamedObject>();
        ArrayList<Float> translucencies = new ArrayList<Float>();
        AC3DAppearanceCache appCache = new AC3DAppearanceCache();
        
        Matrix4f rootTransform = new Matrix4f();
        rootTransform.setIdentity();
        
        String line;
        while ( ( line = reader.readLine() ) != null )
        {
            String token = new StringTokenizer( line, " " ).nextToken();
            
            if ( token.equals( "MATERIAL" ) )
            {
                Object[] mat = loadMaterial( line, appFactory );
                materials.add( (NamedObject)mat[0] );
                translucencies.add( (Float)mat[1] );
            }
            else if ( token.equals( "OBJECT" ) )
            {
                line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer( line, " " );
                token = tokenizer.nextToken();
                if ( !token.equals( "kids" ) )
                    throw new ParsingException( "expected \"kids\" line, but found \"" + line + "\"." );
                
                int numKids = Integer.parseInt( tokenizer.nextToken() );
                loadObjects( reader, numKids, baseURL, materials, translucencies, appFactory, appCache, geomFactory, nodeFactory, keepNestedTransforms, rootTransform, rootGroup, siHandler );
            }
        }
    }
}
