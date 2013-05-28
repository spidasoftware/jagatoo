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
package org.jagatoo.loaders.models.bsp.util;

import org.jagatoo.loaders.models.bsp.lumps.BSPVertex;

/**
 * TODO: Insert package comments here
 * 
 * @author David Yazel
 */
public class BezierPatch
{
    private int level;
    private BSPVertex[] vertex;
    public  BSPVertex[] controls;
    
    public void tesselate( int l )
    {
        this.level = l;
        
        // The number of vertices along a side is 1 + num edges
        final int l1 = l + 1;
        
        this.vertex = new BSPVertex[ l1 * l1 ];
        
        // Compute the vertices
        int i;
        
        for ( i = 0; i <= l; ++i )
        {
            float a = (float)i / (float)l;
            float b = 1 - a;
            
            BSPVertex v1 = controls[ 0 ].copy();
            v1.scale( b * b );
            
            BSPVertex v2 = controls[ 3 ].copy();
            v2.scale( 2 * b * a );
            
            BSPVertex v3 = controls[ 6 ].copy();
            v3.scale( a * a );
            
            vertex[ i ] = v1;
            vertex[ i ].add( v2 );
            vertex[ i ].add( v3 );
        }
        
        for ( i = 1; i <= l; ++i )
        {
            float a = (float)i / (float)l;
            float b = 1.0f - a;
            
            BSPVertex[] temp = new BSPVertex[ 3 ];
            
            for ( int j = 0; j < 3; ++j )
            {
                int k = 3 * j;
                
                BSPVertex v1 = controls[ k + 0 ].copy();
                v1.scale( b * b );
                
                BSPVertex v2 = controls[ k + 1 ].copy();
                v2.scale( 2 * b * a );
                
                BSPVertex v3 = controls[ k + 2 ].copy();
                v3.scale( a * a );
                
                temp[ j ] = v1;
                temp[ j ].add( v2 );
                temp[ j ].add( v3 );
            }
            
            for ( int j = 0; j <= l; ++j )
            {
                float aa = (float)j / (float)l;
                float bb = 1.0f - aa;
                
                BSPVertex v1 = temp[ 0 ].copy();
                v1.scale( bb * bb );
                
                BSPVertex v2 = temp[ 1 ].copy();
                v2.scale( 2 * bb * aa );
                
                BSPVertex v3 = temp[ 2 ].copy();
                v3.scale( aa * aa );
                
                v1.add( v2 );
                v1.add( v3 );
                
                vertex[ i * l1 + j ] = v1;
            }
        }
    }
    
    public int getNumTriangles()
    {
        return ( ( level + 1 ) * ( level + 1 ) * 2 );
    }
    
    public BezierPatch()
    {
        this.controls = new BSPVertex[ 9 ];
    }
}
