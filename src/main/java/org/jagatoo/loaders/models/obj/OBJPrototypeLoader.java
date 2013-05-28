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
package org.jagatoo.loaders.models.obj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;

/**
 * A loader to create abstract java data from a Wavefront OBJ file.
 * 
 * @author Kevin Glass
 * @author <a href="http://www.CroftSoft.com/">David Wallace Croft</a>
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public final class OBJPrototypeLoader
{
    protected static Boolean debug = false;
    
    private static float[] parseVector( String line )
    {
        StringTokenizer tokens = new StringTokenizer( line );
        
        tokens.nextToken();
        
        float[] vector = new float[ 3 ];
        
        vector[ 0 ] = Float.parseFloat( tokens.nextToken() );
        vector[ 1 ] = Float.parseFloat( tokens.nextToken() );
        vector[ 2 ] = Float.parseFloat( tokens.nextToken() );
        
        return ( vector );
    }
    
    private static float[] parseTexCoord( String line )
    {
        StringTokenizer tokens = new StringTokenizer( line );
        
        tokens.nextToken();
        
        float[] texCoord = new float[ 2 ];
        
        texCoord[ 0 ] = Float.parseFloat( tokens.nextToken() );
        texCoord[ 1 ] = Float.parseFloat( tokens.nextToken() );
        
        return ( texCoord );
    }
    
    private static float[] parseColor( String token )
    {
        StringTokenizer tokens = new StringTokenizer( token );
        
        float[] color = new float[ 3 ];
        
        color[ 0 ] = Float.parseFloat( tokens.nextToken() );
        color[ 1 ] = Float.parseFloat( tokens.nextToken() );
        color[ 2 ] = Float.parseFloat( tokens.nextToken() );
        
        return ( color );
    }
    
    private static List<OBJMaterial> parseMatLib( URL baseURL, String name ) throws IOException
    {
        List<OBJMaterial> matList = new ArrayList<OBJMaterial>( 1 );
        OBJMaterial       mat     = null;
        
        BufferedReader    bufferedReader = null;
        
        try
        {
            try
            {
                bufferedReader = new BufferedReader( new InputStreamReader( new URL( baseURL, name ).openStream() ) );
            }
            catch ( FileNotFoundException f )
            {
                try
                {
                    System.out.println( "Base URL = " + baseURL );
                    System.out.println( "Resource name = " + name );
                    bufferedReader = new BufferedReader( new InputStreamReader( Thread.currentThread().getContextClassLoader().getResourceAsStream( name ) ) );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
            
            String line;
            while ( ( line = bufferedReader.readLine() ) != null )
            {
                int i;
                
                for ( i = 0; i < line.length ( ); i++ )
                {
                    if ( !Character.isWhitespace( line.charAt( i ) ) )
                    {
                        break;
                    }
                }
                
                line = line.substring( i );
                
                if ( line.startsWith( "#" ) )
                {
                    // ignore comments
                }
                else if ( line.trim().length() == 0 )
                {
                    // ignore blank lines
                }
                else if ( line.startsWith( "newmtl" ) )
                {
                    String matName = line.substring( line.indexOf( " " ) + 1 );
                    
                    mat = new OBJMaterial( matName );
                    
                    matList.add( mat );
                }
                else if ( line.startsWith( "Tf" ) )
                {
                    float[] color = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setDiffuseColor( color );
                    mat.setAmbientColor( color );
                }
                else if ( line.startsWith( "Ka" ) )
                {
                    float[] color = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setAmbientColor( color );
                }
                else if ( line.startsWith( "Kd" ) )
                {
                    float[] color = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setColor( color );
                    mat.setDiffuseColor( color );
                }
                else if ( line.startsWith( "Ks" ) )
                {
                    float[] color = parseColor( line.substring( line.indexOf( " " ) + 1 ) );
                    
                    mat.setSpecularColor( color );
                }
                else if ( line.startsWith( "Ns" ) )
                {
                    mat.setShininess( Float.parseFloat( line.substring( line.indexOf( " " ) + 1 ) ) );
                }
                else if ( line.startsWith( "map_Kd" ) )
                {
                    String texName = line.substring( line.indexOf( " " ) + 1 );
                    
                    mat.setTextureName( texName );
                }
                else
                {
                    if ( OBJPrototypeLoader.debug != null && OBJPrototypeLoader.debug.booleanValue() )
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() +
                                            ":  ignoring unknown material tag:  \"" + line + "\"" );
                    }
                }
            }
        }
        finally
        {
            if ( bufferedReader != null )
            {
                bufferedReader.close();
            }
        }
        
        return ( matList );
    }
    
    public static OBJModelPrototype load( InputStream in, URL baseURL, float[] geomOffset ) throws IOException
    {
        if ( ( geomOffset != null ) && ( geomOffset.length != 3 ) )
        {
            throw new IllegalArgumentException( "geomOffset must be either null or length 3." );
        }
        
        BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
        
        HashMap<String, OBJMaterial> matMap = new HashMap<String, OBJMaterial>();
        
        List<float[]> verts = new ArrayList<float[]>();
        List<float[]> normals = new ArrayList<float[]>();
        List<float[]> texs = new ArrayList<float[]>();
        
        OBJGroup topGroup = OBJGroup.createTopGroup( verts, normals, texs );
        
        try
        {
            OBJMaterial currentMat = null;
            
            OBJGroup currentGroup = topGroup;
            
            String line = null;
            
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith( "#" ))
                {
                    // comment: ignore
                }
                else if (line.length() == 0)
                {
                    // empty line: ignore
                }
                else if (line.startsWith( "vn" ))
                {
                    normals.add( parseVector( line ) );
                }
                else if (line.startsWith( "vt" ))
                {
                    texs.add( parseTexCoord( line ) );
                }
                else if (line.startsWith( "v" ))
                {
                    float[] vert = parseVector( line );
                    if (geomOffset != null)
                    {
                        vert[ 0 ] += geomOffset[ 0 ];
                        vert[ 1 ] += geomOffset[ 1 ];
                        vert[ 2 ] += geomOffset[ 2 ];
                    }
                    verts.add( vert );
                }
                else if (line.startsWith( "f" ))
                {
                    currentGroup.add( line, currentMat );
                }
                else if (line.startsWith( "g" ))
                {
                    String name = (line.trim().length() >= 3) ? line.substring( 2 ) : null;
                    OBJGroup g = new OBJGroup( name, verts, normals, texs );
                    topGroup.addChild( g );
                    currentGroup = g;
                }
                else if (line.startsWith( "o" ))
                {
                    String name = (line.trim().length() >= 3) ? line.substring( 2 ) : null;
                    OBJGroup g = new OBJGroup( name, verts, normals, texs );
                    topGroup.addChild( g );
                    currentGroup = g;
                }
                else if (line.startsWith( "mtllib" ))
                {
                    StringTokenizer tokens = new StringTokenizer( line );
                    tokens.nextToken();
                    String name = tokens.nextToken();
                    List<OBJMaterial> matList = parseMatLib( baseURL, name );
                    for (OBJMaterial mat: matList)
                    {
                        if (mat != null)
                            matMap.put( mat.getName(), mat );
                    }
                }
                else if (line.startsWith( "usemtl" ))
                {
                    String name = line.substring( line.indexOf( " " ) + 1 );
                    currentMat = matMap.get( name );
                }
                else if (line.startsWith( "s" ))
                {
                    if (debug != null && debug.booleanValue())
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() + ":  smoothing groups not currently supported:  \"" + line + "\"" );
                    }
                }
                else
                {
                    if (debug != null && debug.booleanValue())
                    {
                        System.err.println( OBJPrototypeLoader.class.getName() + ":  ignoring unknown OBJ tag:  \"" + line + "\"" );
                    }
                }
            }
        }
        finally
        {
            br.close();
        }
        
        return ( new OBJModelPrototype( matMap, topGroup ) );
    }
    
    public static OBJModelPrototype load( InputStream in, URL baseURL ) throws IOException
    {
        return ( load( in, baseURL, null ) );
    }
    
    public static void load( InputStream in, URL baseURL, AppearanceFactory appFactory, String skin, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        OBJModelPrototype prototype = load( in, baseURL );
        
        OBJConverter.convert( prototype, baseURL, appFactory, skin, geomFactory, convertZup2Yup, scale, nodeFactory, siHandler, rootGroup );
    }
    
    private OBJPrototypeLoader()
    {
    }
}
