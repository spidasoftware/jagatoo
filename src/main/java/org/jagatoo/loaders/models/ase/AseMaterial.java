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
package org.jagatoo.loaders.models.ase;

import java.util.ArrayList;
import java.util.List;

import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Colorf;

/**
 * This node holds information from a Max ASE Material node.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class AseMaterial extends AseNode
{
    public String className;
    public String name;
    public Colorf ambient = new Colorf();
    public Colorf diffuse = new Colorf();
    public Colorf specular = new Colorf();
    public float shine;
    public float shineStrength;
    public float transparency;
    public float wireSize;
    public String shading;
    public float xpFalloff;
    public float selfIllum;
    public String fallOff;
    public String xpType;
    public AseMap opacityMap = null;
    public AseMap diffuseMap = null;
    public List< AseMaterial > subMaterials = new ArrayList< AseMaterial >();
    
    public AseMaterial()
    {
        properties.put( "*MATERIAL_NAME", "name" );
        properties.put( "*MATERIAL_CLASS", "className" );
        properties.put( "*MATERIAL_AMBIENT", "ambient" );
        properties.put( "*MATERIAL_DIFFUSE", "diffuse" );
        properties.put( "*MATERIAL_SPECULAR", "specular" );
        properties.put( "*MATERIAL_SHINE", "shine" );
        properties.put( "*MATERIAL_SHINESTRENGTH", "shineStrength" );
        properties.put( "*MATERIAL_TRANSPARENCY", "transparency" );
        properties.put( "*MATERIAL_WIRESIZE", "wireSize" );
        properties.put( "*MATERIAL_SHADING", "shading" );
        properties.put( "*MATERIAL_XP_FALLOFF", "xpFalloff" );
        properties.put( "*MATERIAL_SELFILLUM", "selfIllum" );
        properties.put( "*MATERIAL_FALLOFF", "fallOff" );
        properties.put( "*MATERIAL_XP_TYPE", "xpType" );
    }
    
    /**
     * Override the default parse method because we are going to parse the
     * entire mesh in thos node, rather than recusing into further node types.
     */
    @Override
    public void parse( AseReader in )
    {
        AseFileLine line;
        
        // for this to work, blocks have to open on the same line as the
        // property definition.
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( !parseProperty( in, line ) )
            {
                // check for the various special types
                if ( line.getType() == AseFileLine.Type.MAP_OPACITY )
                {
                    opacityMap = new AseMap();
                    opacityMap.parse( in );
                    
                    // org.xith3d.utility.logs.Log.log.println(org.xith3d.utility.logs.LogType.DEBUG,"
                    // Parsed opacity map "+opacityMap.name);
                }
                else if ( line.getType() == AseFileLine.Type.MAP_DIFFUSE )
                {
                    diffuseMap = new AseMap();
                    diffuseMap.parse( in );
                    
                    // org.xith3d.utility.logs.Log.log.println(org.xith3d.utility.logs.LogType.DEBUG,"
                    // Parsed diffuse map "+diffuseMap.name);
                }
                else if ( line.getType() == AseFileLine.Type.SUBMATERIAL )
                {
                    int n = Integer.parseInt( line.getParameter( 0 ) );
                    
                    if ( n != subMaterials.size() )
                    {
                        throw new Error( "Sub-Material index does not match material list" );
                    }
                    
                    AseMaterial m = new AseMaterial();
                    m.parse( in );
                    JAGTLog.debug( "   Parsed sub-material " + m.name );
                    subMaterials.add( m );
                    in.blockEnd = false;
                    
                    // org.xith3d.utility.logs.Log.log.println(org.xith3d.utility.logs.LogType.DEBUG,"
                    // Parsed diffuse map "+diffuseMap.name);
                }
                else if ( in.blockStart )
                {
                    trashBlock( in );
                }
            }
            
            if ( in.blockEnd )
            {
                break;
            }
        }
    }
    
    @Override
    public String toString()
    {
        return name + " (Appearance)";
    }
}
