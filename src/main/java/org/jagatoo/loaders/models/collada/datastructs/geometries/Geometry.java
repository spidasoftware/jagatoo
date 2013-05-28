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
 * A COLLADA Geometry. For now, only polygons with a constant number of
 * vertex-per-polygons and triangles are supported.
 * 
 * Note that this class is abstract. Interesting ones are
 * COLLADATrianglesGeometry, and COLLADAPolygonsGeometry.
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public abstract class Geometry
{
    /** The source ID of this COLLADA Geometry */
    private final String id;
    
    /** The name of the COLLADA Geometry */
    private final String name;

    /** The file this COLLADAGeometry belongs to */
    private final AssetFolder file;

    /** The geometry */
    private final XMLGeometry geometry;

    /** The material that originally associated with this geometry **/
    public final String material;

    /** The mesh containing the data. */
    private Mesh mesh = null;

    /**
     * @return the id.
     */
    public final String getId()
    {
        return( id );
    }

    /**
     * @return the name.
     */
    public String getName()
    {
        return( name );
    }

    /**
     * @return the file.
     */
    public AssetFolder getFile()
    {
        return( file );
    }

    /**
     * @return the geometry.
     */
    public XMLGeometry getGeometry()
    {
        return( geometry );
    }

    /**
     * @param mesh the mesh to set
     */
    public void setMesh( Mesh mesh )
    {
        this.mesh = mesh;
    }

    /**
     * @return the mesh.
     */
    public Mesh getMesh()
    {
        return( mesh );
    }

    /**
     * @return a shared copy of this Geometry.
     * (The sources are shared.)
     */
    public abstract Geometry copy();

    /**
     * Creates a new COLLADAGeometry.
     *
     * @param file the file this COLLADAGeometry belongs to
     * @param id
     *            the id it is referenced by
     * @param name
     *            the name of the geometry
     * @param geometry the geometry
     */
    public Geometry( AssetFolder file, String id, String name, XMLGeometry geometry, String material )
    {
        this.file = file;
        this.id = id;
        this.name = name;
        this.geometry = geometry;
        this.material = material;
    }
}
