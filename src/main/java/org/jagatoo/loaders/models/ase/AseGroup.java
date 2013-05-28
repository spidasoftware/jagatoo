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

import java.util.HashMap;
import java.util.Map;

import org.jagatoo.logging.JAGTLog;

/**
 * Object for the ASE node *GROUP. It is a collection of subgroups, child geom
 * nodes and group attributes (stored in a helper object).
 * 
 * @author William Denniss
 * @author Marvin Froehlich (aka Qudus)
 */
public class AseGroup extends AseGeom
{
    public AseHelper helper = null;
    
    public Map< String, AseGeom > objects = new HashMap< String, AseGeom >();
    
    /**
     * Creats an empty AseGroup
     */
    public AseGroup()
    {
    }
    
    @Override
    public void parse( AseReader in )
    {
        // for this to work, blocks have to open on the same line as the
        // property definition.
        JAGTLog.debug( "  parsing " + this.getClass().getName() );
        
        boolean inBlock = in.blockStart;
        
        AseFileLine line;
        
        // Read until the end of this node
        while ( ( line = in.readAseLine() ) != null )
        {
            // Parses child geometry
            if ( line.getType() == AseFileLine.Type.GEOMETRY_OBJECT )
            {
                JAGTLog.debug( "Geom Object Starting" );
                
                AseGeom a = new AseGeom();
                a.parse( in );
                objects.put( a.name, a );
                JAGTLog.debug( "Geom Object " + a.name + " parsed" );
                
            }
            // Parses child groups
            else if ( line.getType() == AseFileLine.Type.GROUP )
            {
                AseGroup g = new AseGroup();
                g.parse( in );
                objects.put( g.name, g );
                JAGTLog.debug( "Geom Object " + g.name + " parsed" );
                
                // Parses the Group's helper object
            }
            else if ( line.getType() == AseFileLine.Type.HELPER_OBJECT )
            {
                JAGTLog.debug( "Group Helper Object Starting" );
                
                AseHelper h = new AseHelper();
                h.parse( in );
                helper = h;
                
                JAGTLog.debug( "Helper Object " + h.name + " parsed" );
                
                // Set the groups name and transform to that of the helper
                // object
                this.name = h.name;
                
                this.transform = h.transform;
                
                // Ignores any extra blocks
            }
            else if ( in.blockStart )
            {
                trashBlock( in );
            }
            
            // Ends parsing of group
            if ( inBlock && ( in.blockEnd ) )
            {
                break;
            }
        }
        
        if ( inBlock )
        {
            in.blockEnd = false;
        }
    }
}
