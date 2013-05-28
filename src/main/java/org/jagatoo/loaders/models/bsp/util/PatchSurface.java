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

import org.openmali.vecmath2.Point3f;

import org.jagatoo.loaders.models.bsp.lumps.BSPVertex;

/**
 * TODO: Insert package comments here
 * 
 * @author David Yazel
 */
public class PatchSurface
{
    private final static int MAXMESHLEVEL = 2;
    private final static float MINDIST = 0.5f * 0.5f;
    
    private int mCount;
    public BSPVertex mPoints[];       // vertices produced.
    public int mIndices[];      // indices into those vertices
    private int sizex = 0;
    private int sizey = 0;
    
    // Change (Amos Wenger)
    // TODO : check if it doesn't hurt
    // USED TO REDUCE GARBAGE CREATION
    private static Point3f a = new Point3f();
    private static Point3f b = new Point3f();
    
    private static Point3f v0 = new Point3f();
    private static Point3f v1 = new Point3f();
    private static Point3f v2 = new Point3f();
    
    private int getLevelWidth(int lvl)
    {
        return ( (1 << (lvl + 1)) + 1 );
    }
    
    private boolean findSize(int controlx, int controly, BSPVertex cp[])
    {
        /* Find non-coincident pairs in u direction */
        
        boolean found = false;
        
        BSPVertex a = null;
        BSPVertex b = null;
        
        int ai = 0;
        int bi = 0;
        for (int v = 0; v < controly; v++)
        {
            for (int u = 0; u < controlx; u += 2)
            {
                ai = v * controlx + u;
                bi = v * controlx + u + 2;
                
                a = cp[ai];
                b = cp[bi];
                
                /*
                if ( ( a.position.getX() != b.position.getX() ) ||
                     ( a.position.getY() != b.position.getY() ) ||
                     ( a.position.getZ() != b.position.getZ() ) )
                */
                if ( !a.position.equals( b.position ) )
                {
                    found = true;
                    
                    break;
                }
            }
            
            if (found)
                break;
        }
        
        if (!found)
        {
            System.out.println("bad mesh control points\n");
            return false;
        }
        
        /* Find subdivision level in u */
        int levelx = findLevel(cp[ai].position, cp[ai + 1].position, cp[bi].position);
        sizex = (getLevelWidth(levelx) - 1) * ((controlx - 1) / 2) + 1;
        
        for (int u = 0; u < controlx; u++)
        {
            for (int v = 0; v < controly; v += 2)
            {
                ai = v * controlx + u;
                bi = ((v + 2) * controlx) + u;
                
                a = cp[ai];
                b = cp[bi];
                
                /*
                if ( ( a.position.getX() != b.position.getX() ) ||
                     ( a.position.getY() != b.position.getY() ) ||
                     ( a.position.getZ() != b.position.getZ() ) )
                */
                if ( !a.position.equals( b.position ) )
                {
                    found = true;
                    
                    break;
                }
            }
            
            if (found)
                break;
        }
        
        if (!found)
        {
            //if (Xith3DDefaults.getLocalDebug(BSPLoader.DEBUG))
              //  System.out.println("Bad mesh control points\n");
            
            return ( false );
        }
        
        /* Find subdivision level in u */
        int levely = findLevel(cp[ai].position, cp[ai + 1].position, cp[bi].position);
        sizey = (getLevelWidth(levely) - 1) * ((controly - 1) / 2) + 1;
        
        return ( true );
    }
    
    private int findLevel(Point3f cv0, Point3f cv1, Point3f cv2)
    {
        int level;
        // Change (Amos Wenger)
        // TODO : check if it doesn't hurt
        /*Point3f a = new Point3f();
        Point3f b = new Point3f();
        
        Point3f v0 = new Point3f(cv0);
        Point3f v1 = new Point3f(cv1);
        Point3f v2 = new Point3f(cv2);*/
        // New version : 
        a.set(0f, 0f, 0f);
        b.set(0f, 0f, 0f);
        
        v0.set(cv0);
        v1.set(cv1);
        v2.set(cv2);
        
        /* Subdivide on the left until tolerance is reached */
        for (level = 0; level < MAXMESHLEVEL - 1; level++)
        {
            /* Subdivide on the left */
            a.interpolate(v0, v1, 0.5f);
            b.interpolate(v1, v2, 0.5f);
            v2.interpolate(a, b, 0.5f);
            
            /* Find distance moved */
            if (v2.distanceSquared(v1) < MINDIST) break;
            
            /* Insert new middle vertex */
            v1.set(a);
        }
        
        return ( level );
    }
    
    private void fillPatch(int controlx, int controly, BSPVertex p[])
    {
        int stepx = (sizex - 1) / (controlx - 1);
        
        for (int u = 0; u < sizex; u += stepx)
            fillCurve(controly, sizey, sizex, p, u);
        
        for (int v = 0; v < sizey; v++)
            fillCurve(controlx, sizex, 1, p, v * sizex);
    }
    
    private void fillCurve(int numcp, int size, int stride, BSPVertex p[], int start)
    {
        int step, halfstep, i, mid;
        BSPVertex a = new BSPVertex();
        BSPVertex b = new BSPVertex();
        
        step = (size - 1) / (numcp - 1);
        
        while (step > 0)
        {
            halfstep = step / 2;
            for (i = 0; i < size - 1; i += step * 2)
            {
                mid = (i + step) * stride;
                a.avg(p[start + i * stride], p[start + mid]);
                b.avg(p[start + mid], p[start + (i + step * 2) * stride]);
                p[mid+start].avg(a, b);
                
                //vec_avg(p[i*stride], p[mid], a);
                //vec_avg(p[mid], p[(i+step*2)*stride], b);
                //vec_avg(a, b, p[mid]);
                
                if (halfstep > 0)
                {
                    p[start+(i + halfstep) * stride] = a.copy();
                    p[start+(i + 3 * halfstep) * stride] = b.copy();
                }
            }
            
            step /= 2;
        }
    }
    
    public PatchSurface(BSPVertex cp[], int controlx, int controly)
    {
        findSize(controlx, controly, cp);
        
        int size = sizex * sizey;
        mPoints = new BSPVertex[size];
        for (int i = 0; i < size; i++)
            mPoints[i] = new BSPVertex();
        
        int stepx = (sizex - 1) / (controlx - 1);
        int stepy = (sizey - 1) / (controly - 1);
        int cv = 0;
        for (int y = 0; y < sizey; y += stepy)
        {
            for (int x = 0; x < sizex; x += stepx)
            {
                int p = y * sizex + x;
                mPoints[p] = cp[cv++].copy();
            }
        }
        
        fillPatch(controlx, controly, mPoints);
        
        mCount = (sizex - 1) * (sizey - 1) * 6;
        
        mIndices = new int[mCount];
        int ii = 0;
        for (int y = 0; y < sizey - 1; ++y)
        {
            for (int x = 0; x < sizex - 1; ++x)
            {
                mIndices[ii++] = y * sizex + x;
                mIndices[ii++] = (y + 1) * sizex + x;
                mIndices[ii++] = y * sizex + x + 1;

                mIndices[ii++] = y * sizex + x + 1;
                mIndices[ii++] = (y + 1) * sizex + x;
                mIndices[ii++] = (y + 1) * sizex + x + 1;
            }
        }
    }
}
