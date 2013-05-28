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

/**
 * An AseFileLine represents a line read from an ase-file.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
class AseFileLine
{
    public enum Type
    {
        GEOMETRY_OBJECT,
        GROUP,
        MATERIAL_LIST,
        MATERIAL,
        SUBMATERIAL,
        MAP_OPACITY,
        MAP_DIFFUSE,
        MESH_VERTEX_LIST,
        MESH_VERTEX,
        MESH_TEX_VERT_LIST,
        MESH_TEX_VERT,
        MESH_VERTEXNORMAL,
        MESH_FACE_LIST,
        MESH_FACE,
        MESH_TEX_FACE_LIST,
        MESH_TEX_FACE,
        MESH_FACENORMAL,
        MESH_NORMALS,
        HELPER_OBJECT,
        UNKNOWN;
    }
    
    private int number;
    private Type type;
    private String key;
    private String[] params;
    private int numParams;
    
    /**
     * Sets the line number of this line
     * 
     * @param index
     */
    public void setNumber( int num )
    {
        this.number = num;
    }
    
    /**
     * @return the line-number of this line
     */
    public int getNumber()
    {
        return ( number );
    }
    
    /**
     * Sets this line's key.
     * 
     * @param key the new value for key
     */
    public void setKey( String key )
    {
        if ( ( this.key == null ) || ( this.key.equals( "" ) ) )
        {
            if ( key.equalsIgnoreCase( "*GEOMOBJECT" ) )
                this.type = Type.GEOMETRY_OBJECT;
            else if ( key.equalsIgnoreCase( "*GROUP" ) )
                this.type = Type.GROUP;
            else if ( key.equalsIgnoreCase( "*MATERIAL_LIST" ) )
                this.type = Type.MATERIAL_LIST;
            else if ( key.equalsIgnoreCase( "*MATERIAL" ) )
                this.type = Type.MATERIAL;
            else if ( key.equalsIgnoreCase( "*SUBMATERIAL" ) )
                this.type = Type.SUBMATERIAL;
            else if ( key.equalsIgnoreCase( "*MAP_OPACITY" ) )
                this.type = Type.MAP_OPACITY;
            else if ( key.equalsIgnoreCase( "*MAP_DIFFUSE" ) )
                this.type = Type.MAP_DIFFUSE;
            else if ( key.equalsIgnoreCase( "*MESH_VERTEX_LIST" ) )
                this.type = Type.MESH_VERTEX_LIST;
            else if ( key.equalsIgnoreCase( "*MESH_VERTEX" ) )
                this.type = Type.MESH_VERTEX;
            else if ( key.equalsIgnoreCase( "*MESH_TVERTLIST" ) )
                this.type = Type.MESH_TEX_VERT_LIST;
            else if ( key.equalsIgnoreCase( "*MESH_TVERT" ) )
                this.type = Type.MESH_TEX_VERT;
            else if ( key.equalsIgnoreCase( "*MESH_VERTEXNORMAL" ) )
                this.type = Type.MESH_VERTEXNORMAL;
            else if ( key.equalsIgnoreCase( "*MESH_FACE_LIST" ) )
                this.type = Type.MESH_FACE_LIST;
            else if ( key.equalsIgnoreCase( "*MESH_FACE" ) )
                this.type = Type.MESH_FACE;
            else if ( key.equalsIgnoreCase( "*MESH_TFACELIST" ) )
                this.type = Type.MESH_TEX_FACE_LIST;
            else if ( key.equalsIgnoreCase( "*MESH_TFACE" ) )
                this.type = Type.MESH_TEX_FACE;
            else if ( key.equalsIgnoreCase( "*MESH_FACENORMAL" ) )
                this.type = Type.MESH_FACENORMAL;
            else if ( key.equalsIgnoreCase( "*MESH_NORMALS" ) )
                this.type = Type.MESH_NORMALS;
            else if ( key.equalsIgnoreCase( "*HELPEROBJECT" ) )
                this.type = Type.HELPER_OBJECT;
            else
                this.type = Type.UNKNOWN;
        }
        
        this.key = key;
    }
    
    /**
     * @return the key value of this line
     */
    public String getKey()
    {
        return ( key );
    }
    
    /**
     * @return this Line's type
     */
    public Type getType()
    {
        return ( type );
    }
    
    /**
     * Adds the given parameter to the list of parameters and
     * increases the count value.
     * 
     * @param param the new parameter to add
     */
    public void addParameter( String param )
    {
        this.params[numParams] = param;
        this.numParams++;
    }
    
    /**
     * @return the number of parameters in this line
     */
    public int getParametersCount()
    {
        return ( numParams );
    }
    
    /**
     * @return the parameter at the given index
     * 
     * @param index the index of the desired parameter
     */
    public String getParameter( int index )
    {
        if ( index >= numParams )
            throw new IllegalArgumentException( "A parameter with this index (" + index + ") does not exist." );
        
        return ( params[index] );
    }
    
    /**
     * Resets <b>key</b> to <b>null</b> and <b>numParams</b> to zero.
     */
    public void reset()
    {
        this.number = -1;
        this.key = "";
        this.numParams = 0;
    }
    
    /**
     * Creates and initializes a new instance.
     */
    public AseFileLine()
    {
        this.params = new String[ 20 ];
        this.reset();
    }
}
