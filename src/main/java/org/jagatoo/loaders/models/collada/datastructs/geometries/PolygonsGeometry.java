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
 * COLLADA Polygons Geometry contains geometry loaded from a COLLADA
 * file which has the "polygon" format
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class PolygonsGeometry extends Geometry
{
    /** The Polygons in this geometry */
    private final Mesh[] polygons;
    
    /**
     * @return the polygons.
     */
    public final Mesh[] getPolygons()
    {
        return ( polygons );
    }
    
    @Override
    public PolygonsGeometry copy()
    {
        PolygonsGeometry newGeom = new PolygonsGeometry( this.getFile(), this.getId() + "-copy", this.getName(), this.getPolygons().length, this.getGeometry() );
        
        /*
         * FIXME:
         * A PolygonsGeometry has several "meshes" (one per poly), unlike a TriangleGeometry
         * thus Geometry should be changed, and this copy() method.
         * That's for later, when we implement tesselation.
         */
        newGeom.setMesh( this.getMesh().copy() );
        
        return ( newGeom );
    }
    
    /**
     * Creates a new COLLADA Polygons Geometry.
     * 
     * @param file The given AssetFolder to load from
     * @param id {@inheritDoc}
     * @param name {@inheritDoc}
     * @param polygonCount The number of polygons that should be
     * in that PolygonsGeometry
     * @param geometry the geometry
     */
    public PolygonsGeometry( AssetFolder file, String id, String name, int polygonCount, XMLGeometry geometry )
    {
        super( file, id, name, geometry, new String("") );

        this.polygons = new Mesh[ polygonCount ];
    }
}
