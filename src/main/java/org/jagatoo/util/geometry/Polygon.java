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
package org.jagatoo.util.geometry;

import java.util.ArrayList;
import java.util.List;

import org.jagatoo.util.arrays.ArrayUtils;

/**
 * Simple class encapsulating a set of points which define the outline of a coplanar
 * polygon.  Usually this is a triagle or a quad.
 * 
 * @author David Yazel
 */
public class Polygon
{
    private List<float[]> vertices;
    
    public List<float[]> getVertices()
    {
        return ( vertices );
    }
    
    public void add( float[] v )
    {
        vertices.add( v );
    }
    
    public void add( float x, float y, float z )
    {
        add( new float[] { x, y, z } );
    }
    
    public boolean contains( float[] point )
    {
        for ( int i = 0; i < vertices.size(); i++ )
        {
            final float[] vert = vertices.get( i );
            
            if ( ArrayUtils.equals( vert, point ) )
            {
                return ( true );
            }
        }
        
        return ( false );
    }
    
    /**
     * @return a list of triangles
     */
    public List<Polygon> getAsTriangles()
    {
        ArrayList<Polygon> list = new ArrayList<Polygon>();
        
        if ( vertices.size() == 3)
        {
            list.add( this );
            
            return ( list );
        }
        else if ( vertices.size() > 3 )
        {
            float[] origin = vertices.get( 0 );
            for ( int i = 1; i < vertices.size() - 1; i++ )
            {
                Polygon tri = new Polygon();
                float[] v2 = vertices.get( i );
                float[] v3 = vertices.get( i + 1 );
                tri.add( origin );
                tri.add( v2 );
                tri.add( v3 );
                list.add( tri );
            }
            
            return ( list );
        }
        
        return ( null );
    }
    
    public void print()
    {
        System.out.println( "Number of vertices in polygon is " + vertices.size() );
        
        for ( int i = 0; i < vertices.size(); i++ )
        {
            System.out.println( "  P" + i + ": " + (float[])vertices.get( i ) );
        }
    }
    
    public Polygon( List<float[]> vertices )
    {
        this.vertices = vertices;
    }
    
    public Polygon()
    {
        this( new ArrayList<float[]>() );
    }
}
