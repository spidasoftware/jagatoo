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
package org.jagatoo.loaders.models.collada.datastructs.visualscenes;

import java.util.ArrayList;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;

/**
 * A COLLADA Node
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Node
{
    /** The COLLADA file this node belongs to */
    private final AssetFolder file;
    
    /** The id of this node */
    private final String id;
    
    /** The name of this node */
    private final String name;
    
    /** The transform of this node */
    private final COLLADATransform transform;
    
    /** Children nodes */
    private final ArrayList< Node > children = new ArrayList< Node >();
    
    /** geometry instance nodes */
    private final ArrayList< GeometryInstance > geometryInstances = new ArrayList< GeometryInstance >();
    
    /** controller instance nodes */
    private final ArrayList< ControllerInstance > controllerInstances = new ArrayList< ControllerInstance >();
    
    /**
     * @return the file.
     */
    public final AssetFolder getFile()
    {
        return ( file );
    }
    
    /**
     * @return the id.
     */
    public final String getId()
    {
        return ( id );
    }
    
    /**
     * @return the name.
     */
    public final String getName()
    {
        return ( name );
    }
    
    /**
     * @return the transform.
     */
    public final COLLADATransform getTransform()
    {
        return ( transform );
    }
    
    public void addGeometryInstance( GeometryInstance instance )
    {
        geometryInstances.add( instance );
    }
    
    public void addControllerInstance( ControllerInstance instance )
    {
        controllerInstances.add( instance );
    }
    
    public void addChild( Node child )
    {
        children.add( child );
    }
    
    public ArrayList< Node > getChildren()
    {
        return children;
    }
    
    public ArrayList< GeometryInstance > getGeometryInstances()
    {
        return geometryInstances;
    }
    
    public ArrayList< ControllerInstance > getControllerInstances()
    {
        return controllerInstances;
    }

    /**
     * Creates a new COLLADANode.
     * 
     * @param file the COLLADA file this node belongs to
     * @param id The id of this Node
     * @param name The name of this Node
     * @param transform The transform of this Node
     */
    public Node( AssetFolder file, String id, String name, COLLADATransform transform )
    {
        this.file = file;
        this.id = id;
        this.name = name;
        this.transform = transform;
    }
}
