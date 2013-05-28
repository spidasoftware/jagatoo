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

import java.util.HashMap;

/**
 * A COLLADA Scene
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Scene
{
    /** The id of the scene */
    private final String id;
    
    /** The name of the scene */
    private String name;
    
    /** A map of all nodes */
    private final HashMap<String, Node> nodes = new HashMap<String, Node>();
    
    /**
     * @return the id.
     */
    public String getId()
    {
        return ( id );
    }
    
    /**
     * @return the name.
     */
    public String getName()
    {
        return ( name );
    }
    
    /**
     * @return the nodes.
     */
    public HashMap<String, Node> getNodes()
    {
        return ( nodes );
    }
    
    /**
     * Creates a new COLLADAScene.
     * 
     * @param id The id of the scene
     * @param name The name of the scene
     */
    public Scene( String id, String name )
    {
        this.id = id;
        this.name = name;
    }
}
