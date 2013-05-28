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
package org.jagatoo.loaders.models.collada.datastructs.geometries;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.stax.XMLGeometry;

/**
 * COLLADA Geometry which has been loaded from a COLLADA file with
 * the format "triangles".
 * 
 * All data here is public since it may be accessed very frequently,
 * thus it's a good thing that we do not need to access it via
 * getter/setter methods.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class TrianglesGeometry extends Geometry
{
    /**
     * {@inheritDoc}
     */
    @Override
    public TrianglesGeometry copy()
    {
        TrianglesGeometry newGeom = new TrianglesGeometry( this.getFile(), this.getId() + "-copy", this.getName(), this.getGeometry(), this.material );
        
        newGeom.setMesh( this.getMesh().copy() );

        return( newGeom );
    }

    /**
     * Creates a new COLLADAGeometry.
     *
     * @param file The given AssetFolder to load from
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param geometry the geometry
     */
    public TrianglesGeometry( AssetFolder file, String id, String name, XMLGeometry geometry, String material )
    {
        super( file, id, name, geometry, material );
    }
}
