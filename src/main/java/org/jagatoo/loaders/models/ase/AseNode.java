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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.jagatoo.logging.JAGTLog;
import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Vector3f;

/**
 * Base node for all ASE nodes. Any node that inherits from this need only
 * define the method variables and assign them to the parse list and the node
 * will automatically parse itself.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class AseNode
{
    protected static final Boolean debug = null;
    protected Map< String, String > properties;
    
    public AseNode()
    {
        properties = new HashMap< String, String >();
    }
    
    /**
     * This is a generic parser method for any AseNodes. Some might need to
     * override this in order to handle the parse properly (Like
     * MESH_FACENORMAL). Basically this reads a line and if it has a key it is
     * looked up in the properties hash. If it finds a match it then checks the
     * type of the class property using reflection and then parses the input
     * based on type.
     * 
     * An example is Colorf. If a property is of type Colorf then it will
     * assume that there will be 3 floats as parameters to this line, in which
     * case it will parse it.
     */
    public boolean parseProperty( AseReader in, AseFileLine line )
    {
        String property = properties.get( line.getKey() );
        
        if ( property != null )
        {
            // if (Xith3DDefaults.getLocalDebug(debug))
            // org.xith3d.utility.logs.Log.log.println(org.xith3d.utility.logs.LogType.DEBUG,"
            // matched "+in.key+", field "+property);
            try
            {
                Field f = this.getClass().getDeclaredField( property );
                String type = f.getType().getName();
                
                // if (Xith3DDefaults.getLocalDebug(debug))
                // org.xith3d.utility.logs.Log.log.println(org.xith3d.utility.logs.LogType.DEBUG,"
                // type is "+type);
                if ( type.equals( "float" ) )
                {
                    f.setFloat( this, Float.parseFloat( line.getParameter( 0 ) ) );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + line.getParameter( 0 ) );
                }
                else if ( type.equals( "int" ) )
                {
                    f.setInt( this, Integer.parseInt( line.getParameter( 0 ) ) );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + line.getParameter( 0 ) );
                }
                else if ( type.equals( "boolean" ) )
                {
                    f.setBoolean( this, Integer.parseInt( line.getParameter( 0 ) ) == 1 );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + f.getBoolean( this ) );
                }
                else if ( type.equals( "org.openmali.vecmath2.Colorf" ) )
                {
                    Colorf c = (Colorf)f.get( this );
                    c.setRed( Float.parseFloat( line.getParameter( 0 ) ) );
                    c.setGreen( Float.parseFloat( line.getParameter( 1 ) ) );
                    c.setBlue( Float.parseFloat( line.getParameter( 2 ) ) );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + c );
                }
                else if ( type.equals( "org.openmali.vecmath2.Vector3f" ) )
                {
                    Vector3f v = (Vector3f)f.get( this );
                    v.setX( Float.parseFloat( line.getParameter( 0 ) ) );
                    v.setY( Float.parseFloat( line.getParameter( 1 ) ) );
                    v.setZ( Float.parseFloat( line.getParameter( 2 ) ) );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + v );
                }
                else if ( type.equals( "java.lang.String" ) )
                {
                    f.set( this, line.getParameter( 0 ) );
                    
                    JAGTLog.debug( "  Setting " + line.getKey() + "/" + f.getName() + " to " + line.getParameter( 0 ) );
                }
                else
                {
                    // if this is field is an AseNode then call the parser for
                    // it
                    if ( AseNode.class.isAssignableFrom( f.getType() ) )
                    {
                        JAGTLog.debug( "  AseNode detected" );
                        
                        AseNode node = (AseNode)f.get( this );
                        node.parse( in );
                    }
                    else
                    {
                        JAGTLog.debug( "  Can't determine type of matched " + line.getKey() + ", field " + property );
                        
                        return ( false );
                    }
                }
            }
            catch ( NoSuchFieldException e )
            {
                e.printStackTrace();
            }
            catch ( IllegalAccessException ee )
            {
                ee.printStackTrace();
            }
        }
        else
        {
            return ( false );
        }
        
        return ( true );
    }
    
    public void trashBlock( AseReader in )
    {
        // if this property is not registered in the list, and this
        // is a block, then trash the block since we don't handle it
        if ( in.blockStart )
        {
            JAGTLog.debug( "  Trashing block " /*+ in.key*/);
            
            int numOpen = 1;
            
            while ( in.readAseLine() != null )
            {
                if ( in.blockStart )
                {
                    numOpen++;
                }
                else if ( in.blockEnd )
                {
                    numOpen--;
                }
                
                if ( numOpen <= 0 )
                {
                    break;
                }
            }
            
            in.blockEnd = false;
        }
    }
    
    public void parse( AseReader in )
    {
        // for this to work, blocks have to open on the same line as the
        // property definition.
        JAGTLog.debug( "  parsing " + this.getClass().getName() );
        
        boolean inBlock = in.blockStart;
        
        AseFileLine line;
        
        while ( ( line = in.readAseLine() ) != null )
        {
            if ( !parseProperty( in, line ) )
            {
                if ( in.blockStart )
                {
                    trashBlock( in );
                }
            }
            
            if ( ( inBlock ) && ( in.blockEnd ) )
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
