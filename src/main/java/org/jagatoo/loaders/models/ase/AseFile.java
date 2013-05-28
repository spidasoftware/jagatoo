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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jagatoo.logging.JAGTLog;

/**
 * Loader for the ase file format. Once the data is loaded, several methods can
 * be used to build the Xith3D Scenegraph object. If the ase file represents a
 * single object which is always treated as one (i.e. no moving parts within it)
 * then the getModel method is the simplest way. Alternativly, to load each ase
 * node as it's own Group with it's geometry relitive to it's pivot point
 * (allowing for easy rotation) use the getNamedNodesMap and
 * getTransformGroupTree methods. The former gives you a map containing just the
 * named GEOM nodes, infact it just calls getTransformGroupTree passing a few
 * flags to get this behaviour. The most useful and customisable method is
 * getTransformGroupTree. All groups and geometry are loaded relitive to their
 * pivot points, transformed into place and grouped into TransformGroupS. This
 * is the most true representation of the file data as the pivots group concepts
 * are preserved.<br>
 * <br>
 * The <a href="http://xith.org/tutes.php#GettingStarted">Getting Started Guide</a>
 * has some tutorials to illustrate how to use the ASE loader, including a
 * chapter on <a
 * href="http://xith.org/tutes/GettingStarted/html/transformgroup_trees_with_t.html">
 * TransformGroup trees</a> by the author of that method which diagramatically
 * describes how that method works.
 * 
 * @author David Yazel
 * @author William Denniss
 * @author Marvin Froehlich (aka Qudus)
 */
public class AseFile extends AseNode
{
    /**
     * Field used by the parser to store ASE information
     */
    public float version;
    
    /**
     * Field used by the parser to store ASE information
     */
    public String comment = "";
    
    /**
     * Field used by the parser to store the ase graph
     */
    public AseGraph aseGraph = new AseGraph();
    
    /**
     * Field used by the parser to store all Ase objects. This map maintains the
     * scene graph structure; i.e., child nodes of the topmost ones are not
     * present in this map, but instead in the maps contained in the subordinate
     * AseGroups.
     */
    public Map< String, AseNode > objects = new HashMap< String, AseNode >();
    
    /**
     * Field used by the parser to store all Ase objects read in by the
     * top-level parser. Also contains temporary nodes constructed during
     * parsing for the purpose of reconstructing the hierarchy based on the
     * NODE_PARENT field.
     */
    public Map< String, AseNode > allTopLevelObjects = new HashMap< String, AseNode >();
    
    /**
     * Field used by the parser to store materials
     */
    public List< AseMaterial > materials = new ArrayList< AseMaterial >();
    
    /**
     * Field used by the parser to store ASE information
     */
    public int materialCount;
    
    private static final String fabricatedGroupNodeSuffix = "_GROUP_";
    
    /**
     * Ensure the given geometry node is either a group node (so we can always
     * add children to it) or reparent it into a fabricated Group node, so we
     * can add children to it later without needing to search the scene graph
     * for it.
     */
    private static AseGroup groupNodeForGeometry( Map< String, AseNode > objects, Map< String, AseNode > allTopLevelObjects, AseGeom geom )
    {
        if ( geom instanceof AseGroup )
        {
            return (AseGroup)geom;
        }
        
        String fabricatedGroupName = geom.name + fabricatedGroupNodeSuffix;
        // It isn't an invariant that the fabricated group node is at
        // the top level, but if it's been created already, it's in
        // the allTopLevelObjects map
        AseGroup group = (AseGroup)allTopLevelObjects.get( fabricatedGroupName );
        if ( group != null )
        {
            return group;
        }
        
        // Fabricate a parent group node
        group = new AseGroup();
        group.name = fabricatedGroupName;
        group.objects.put( geom.name, geom );
        AseTransform tmpXform = geom.transform;
        geom.transform = group.transform;
        group.transform = tmpXform;
        // The geometry had better be in the "objects" map at this point
        if ( objects.remove( geom.name ) == null )
        {
            // Warn at this point; should make this an error
            JAGTLog.exception( "Warning: node named \"" + geom.name + "\" not in top-level objects map" );
        }
        objects.put( group.name, group );
        allTopLevelObjects.put( group.name, group );
        return group;
    }
    
    /**
     * Updates the hierarchy based on the NODE_PARENT field of the given AseGeom
     * node. The passed "objects" map contains the scene graph structure at the
     * current depth in the scene. If the passed geometry node is reparented to
     * another node, the geometry node is removed from the "objects" map. The
     * "allTopLevelObjects" map, conversely, contains all objects that were ever
     * parsed by the AseFile object; geometry nodes are never removed from this
     * map, but AseGroup nodes that did not exist in the original file may be
     * added to this map for the purpose of being able to find them quickly
     * without searching through the scene graph.
     */
    public static void updateHierarchyFromNodeParent( Map< String, AseNode > objects, Map< String, AseNode > allTopLevelObjects, AseGeom geom )
    {
        // If this node has a parent specified via NODE_PARENT,
        // reparent it, possibly creating a temporary AseGroup node in
        // order to be able to do so.
        String parentName = geom.parentName;
        if ( parentName != null && ( !parentName.equals( "" ) ) )
        {
            // Replace geometry with fabricated parent if any
            geom = groupNodeForGeometry( objects, allTopLevelObjects, geom );
            
            // See if we can find the node's parent
            AseNode parentNode = allTopLevelObjects.get( parentName );
            if ( parentNode == null )
            {
                // Would like to provide a warning but doesn't look
                // like there is a logging class to support that
                JAGTLog.exception( "Warning: unable to find parent node named \"" + geom.parentName + "\"" );
                return;
            }
            
            if ( !( parentNode instanceof AseGeom ) )
            {
                Class< ? extends AseNode > parentClass = parentNode.getClass();
                // Sorry, don't know how to deal with this right now
                JAGTLog.exception( "Warning: unknown / unsupported parent node type \"" + parentClass.getName() + "\"" );
            }
            
            // Get or fabricate a group node for the parent
            AseGroup parentGroup = groupNodeForGeometry( objects, allTopLevelObjects, (AseGeom)parentNode );
            
            // Make the AseGeom node (actually, its containing
            // AseGroup) a child of the specified AseGroup instead of
            // the top level
            objects.remove( geom.name );
            parentGroup.objects.put( geom.name, geom );
            // System.out.println("Added " + geom.name + " to " +
            // parentGroup.name);
        }
    }
    
    /**
     * Overrides the default parse method because the top level of an ASE file
     * has various nodes of different types, and its easier to handle them
     * explicitly
     */
    @Override
    public void parse( AseReader in )
    {
        // for this to work, blocks have to open on the same line as the
        // property definition.
        try
        {
            AseFileLine line;
            
            while ( ( line = in.readAseLine() ) != null )
            {
                if ( !parseProperty( in, line ) )
                {
                    switch ( line.getType() )
                    {
                        case GEOMETRY_OBJECT:
                            JAGTLog.debug( "Geom Object Starting" );
                            
                            AseGeom a = new AseGeom();
                            a.parse( in );
                            objects.put( a.name, a );
                            allTopLevelObjects.put( a.name, a );
                            updateHierarchyFromNodeParent( objects, allTopLevelObjects, a );
                            JAGTLog.debug( "Geom Object " + a.name + " parsed" );
                            
                            break;
                        
                        case GROUP:
                            AseGroup g = new AseGroup();
                            g.parse( in );
                            objects.put( g.name, g );
                            allTopLevelObjects.put( g.name, g );
                            updateHierarchyFromNodeParent( objects, allTopLevelObjects, g );
                            JAGTLog.debug( "Geom Object " + g.name + " parsed" );
                            
                            break;
                        
                        case MATERIAL_LIST:
                            JAGTLog.debug( "Parsing material list" );
                            
                            break;
                        
                        case MATERIAL:
                            int n = Integer.parseInt( line.getParameter( 0 ) );
                            
                            if ( n != materials.size() )
                            {
                                throw new Error( "Material index does not match material list" );
                            }
                            
                            AseMaterial m = new AseMaterial();
                            m.parse( in );
                            JAGTLog.debug( "   Parsed material " + m.name );
                            materials.add( m );
                            
                            break;
                        
                        case UNKNOWN:
                        default:
                            trashBlock( in );
                            
                            break;
                    }
                }
            }
        }
        catch ( Throwable t )
        {
            t.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch ( Throwable t )
            {
                t.printStackTrace();
            }
        }
    }
    
    /**
     * <p>
     * Creates an empty AseFile object to which data from the file will be read
     * into. To read the data, use the parse method passing an AseReader object.
     * </p>
     * 
     * <p>
     * <b>Example:</b> <code>
     *   AseFile ase = new AseFile();
     *   ase.parse(new BufferedReader(new FileReader("CUBE.ASE"));
     * </code>
     * </p>
     * 
     */
    public AseFile()
    {
        super();
        
        properties.put( "*3DSMAX_ASCIIEXPORT", "version" );
        properties.put( "*COMMENT", "comment" );
        properties.put( "*SCENE", "aseGraph" );
        properties.put( "*MATERIAL_COUNT", "materialCount" );
    }
}
