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
package org.jagatoo.loaders.models.bsp.lumps;

import org.openmali.vecmath2.Colorf;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.TexCoord2f;
import org.openmali.vecmath2.Vector3f;

/**
 * Represents a single BSP vertex
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 * @author Amos Wenger (aka BlueSky)
 */
public class BSPVertex
{
    public Point3f     position;
    public TexCoord2f  texCoord;
    public TexCoord2f  lightTexCoord;
    public Vector3f    normal;
    public Colorf      color;
    
    public void swizzle()
    {
        final float y = position.getY();
        position.setY( position.getZ() );
        position.setZ( -y );
    }
    
    public void scale( float scale )
    {
        position.scale( scale );
        texCoord.mul( scale );
        lightTexCoord.mul( scale );
        normal.scale( scale );
        color.mul( scale );
    }
    
    public void add( BSPVertex v )
    {
        position.add( v.position );
        texCoord.add( v.texCoord );
        lightTexCoord.add( v.lightTexCoord );
        color.add( v.color );
    }
    
    public void set( BSPVertex a )
    {
        position.set( a.position );
        texCoord.set( a.texCoord );
        lightTexCoord.set( a.lightTexCoord );
        color.set( a.color );
    }
    
    public void avg( BSPVertex a, BSPVertex b )
    {
        set( a );
        add( b );
        scale( 0.5f );
    }

    public BSPVertex copy()
    {
        BSPVertex v = new BSPVertex();
        
        v.position.set( position );
        v.texCoord.set( texCoord );
        v.lightTexCoord.set( lightTexCoord );
        v.normal.set( normal );
        v.color.set( color );
        
        return ( v );
    }
    
    public BSPVertex()
    {
        position = new Point3f();
        texCoord = new TexCoord2f();
        lightTexCoord = new TexCoord2f();
        normal = new Vector3f();
        color = new Colorf();
    }
}
