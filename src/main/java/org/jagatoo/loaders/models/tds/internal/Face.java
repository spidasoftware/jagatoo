/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
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
package org.jagatoo.loaders.models.tds.internal;

import org.jagatoo.loaders.models.tds.TDSFile;
import org.openmali.vecmath2.Point3f;
import org.openmali.vecmath2.Vector3f;

/**
 * A single face defined in the 3DS file
 * 
 * @author Kevin Glass
 */
public class Face
{
    private final TDSFile file;
    
    private final int a, b, c;
    private final Vector3f normal;
    public int group;
    
    public final int getCoordAIndex()
    {
        return ( a );
    }
    
    public final int getCoordBIndex()
    {
        return ( b );
    }
    
    public final int getCoordCIndex()
    {
        return ( c );
    }
    
    public final Point3f getCoordA()
    {
        return ( file.getContext().vertexCoords[a] );
    }
    
    public final Point3f getCoordB()
    {
        return ( file.getContext().vertexCoords[b] );
    }
    
    public final Point3f getCoordC()
    {
        return ( file.getContext().vertexCoords[c] );
    }
    
    public final Vector3f getNormal()
    {
        return ( normal );
    }
    
    private final Vector3f calculateFaceNormal( int a, int b, int c )
    {
        Vector3f vertCoordA = Vector3f.fromPool( file.getContext().vertexCoords[a] );
        Vector3f vertCoordB = Vector3f.fromPool( file.getContext().vertexCoords[b] );
        Vector3f vertCoordC = Vector3f.fromPool( file.getContext().vertexCoords[c] );
        
        Vector3f normal = new Vector3f();
        
        vertCoordB.sub( vertCoordA );
        vertCoordC.sub( vertCoordA );
        
        normal.cross( vertCoordB, vertCoordC );
        normal.normalize();
        
        //normal.negate();
        
        return ( normal );
    }
    
    public Face( TDSFile file, int vertexA, int vertexB, int vertexC )
    {
        this.file = file;
        
        this.a = vertexA;
        this.b = vertexB;
        this.c = vertexC;
        this.normal = calculateFaceNormal( a, b, c );
        
        file.getContext().sharedFaces[a].add( this );
        file.getContext().sharedFaces[b].add( this );
        file.getContext().sharedFaces[c].add( this );
    }
}
