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
package org.jagatoo.loaders.models.collada.datastructs.materials;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;

/**
 * A COLLADA Material
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class Material
{
    /** The file this COLLADAMaterial belongs to */
    private final AssetFolder file;
    
    /** The ID */
    private final String id;
    
    /** All effects used in this material */
    private final String effect;
    
    /**
     * @return the file.
     */
    public final AssetFolder getFile()
    {
        return ( file );
    }
    
    /**
     * @return the effect.
     */
    public final String getEffect()
    {
        return ( effect );
    }
    
    /**
     * @return the id.
     */
    public final String getId()
    {
        return ( id );
    }
    
    /**
     * Creates a new Material.
     * 
     * @param file the file this COLLADAMaterial belongs to
     * @param id The ID of this Material
     * @param effect The effect associated to this Material
     */
    public Material( AssetFolder file, String id, String effect )
    {
        this.file = file;
        this.id = id;
        this.effect = effect;
    }
}
