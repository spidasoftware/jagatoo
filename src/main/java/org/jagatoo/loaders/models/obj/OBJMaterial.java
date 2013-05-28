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
package org.jagatoo.loaders.models.obj;

/**
 * Abstractly stores an OBJ loaded material.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class OBJMaterial
{
    private String   name = null;
    
    private float[]  color = null;
    private float[]  ambientColor = null;
    private float[]  diffuseColor = null;
    private float[]  specularColor = null;
    private float    shininess = 64.0f;
    
    private String   textureName = null;
    
    /**
     * @return this Material's name
     */
    public String getName()
    {
        return ( name );
    }
    
    /**
     * Sets this material's color.
     * 
     * @param color
     */
    public void setColor( float[] color )
    {
        if ( color == null )
        {
            this.color = null;
        }
        if (this.color != null)
        {
            this.color[ 0 ] = color[ 0 ];
            this.color[ 1 ] = color[ 1 ];
            this.color[ 2 ] = color[ 2 ];
        }
        else
        {
            this.color = new float[] { color[ 0 ], color[ 1 ], color[ 2 ] };
        }
    }
    
    /**
     * @return this material's color.
     */
    public float[] getColor()
    {
        return ( color );
    }
    
    /**
     * Sets this material's ambient color.
     * 
     * @param color
     */
    public void setAmbientColor( float[] color )
    {
        if ( color == null )
        {
            this.ambientColor = null;
        }
        if (this.ambientColor != null)
        {
            this.ambientColor[ 0 ] = color[ 0 ];
            this.ambientColor[ 1 ] = color[ 1 ];
            this.ambientColor[ 2 ] = color[ 2 ];
        }
        else
        {
            this.ambientColor = new float[] { color[ 0 ], color[ 1 ], color[ 2 ] };
        }
    }
    
    /**
     * @return this material's color.
     */
    public float[] getAmbientColor()
    {
        return ( ambientColor );
    }
    
    /**
     * Sets this material's diffuse color.
     * 
     * @param color
     */
    public void setDiffuseColor( float[] color )
    {
        if ( color == null )
        {
            this.diffuseColor = null;
        }
        if (this.diffuseColor != null)
        {
            this.diffuseColor[ 0 ] = color[ 0 ];
            this.diffuseColor[ 1 ] = color[ 1 ];
            this.diffuseColor[ 2 ] = color[ 2 ];
        }
        else
        {
            this.diffuseColor = new float[] { color[ 0 ], color[ 1 ], color[ 2 ] };
        }
    }
    
    /**
     * @return this material's diffuse color.
     */
    public float[] getDiffuseColor()
    {
        return ( diffuseColor );
    }
    
    /**
     * Sets this material's specular color.
     * 
     * @param color
     */
    public void setSpecularColor( float[] color )
    {
        if ( color == null )
        {
            this.specularColor = null;
        }
        if (this.specularColor != null)
        {
            this.specularColor[ 0 ] = color[ 0 ];
            this.specularColor[ 1 ] = color[ 1 ];
            this.specularColor[ 2 ] = color[ 2 ];
        }
        else
        {
            this.specularColor = new float[] { color[ 0 ], color[ 1 ], color[ 2 ] };
        }
    }
    
    /**
     * @return this material's specular color.
     */
    public float[] getSpecularColor()
    {
        return ( specularColor );
    }
    
    /**
     * Sets this Material's shininess.
     * 
     * @param shininess
     */
    public void setShininess( float shininess )
    {
        this.shininess = shininess;
    }
    
    /**
     * @return this Material's shininess.
     */
    public float getShininess()
    {
        return ( shininess );
    }
    
    /**
     * Sets this Material's texture name.
     * 
     * @param textureName
     */
    public void setTextureName( String textureName )
    {
        this.textureName = textureName;
    }
    
    /**
     * @return this Material's texture name.
     */
    public String getTextureName()
    {
        return ( textureName );
    }
    
    public OBJMaterial( String name )
    {
        this.name = name;
    }
}
