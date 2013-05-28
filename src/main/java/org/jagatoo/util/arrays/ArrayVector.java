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
package org.jagatoo.util.arrays;

/**
 * Utility class, that provides vector-arithmetic methods to be applied to
 * float arrays.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class ArrayVector
{
    /**
     * Subtracts the array b componentwise from the array a and puts the result into o.
     * 
     * @param a
     * @param b
     * @param o
     */
    public static final void sub( float[] a, float[] b, float[] o )
    {
        for ( int i = 0; i < o.length; i++ )
        {
            o[ i ] = a[ i ] - b[ i ];
        }
    }
    
    /**
     * Adds the array a componentwise to the array b and puts the result into o.
     * 
     * @param a
     * @param b
     * @param o
     */
    public static final void add( float[] a, float[] b, float[] o )
    {
        for ( int i = 0; i < o.length; i++ )
        {
            o[ i ] = a[ i ] + b[ i ];
        }
    }
    
    /**
     * Returns the squared length of the given vector.
     * 
     * @return the squared length of the given vector
     */
    public static final float lengthSquared( float[] vector )
    {
        float result = 0.0f;
        
        for ( int i = 0; i < vector.length; i++ )
        {
            result += vector[ i ] * vector[ i ];
        }
        
        return ( result );
    }
    
    /**
     * Returns the length of the given vector.
     * 
     * @return the length of the given vector
     */
    public static final float length( float[] vector )
    {
        return ( (float)Math.sqrt( lengthSquared( vector ) ) );
    }
    
    /**
     * Computes the square of the distance between points p1 and p2.
     * 
     * @param p1 the first point
     * @param p2 the other point
     * 
     * @return the square of distance between these two points as a float
     */
    public static final float distanceSquared( float[] p1, float[] p2 )
    {
        float result = 0.0f;
        
        for ( int i = 0; i < p1.length; i++ )
        {
            result += ( p1[ i ] - p2[ i ] ) * ( p1[ i ] - p2[ i ] );
        }
        
        return ( result );
    }
    
    /**
     * Returns the distance between points p1 and p2.
     * 
     * @param p1 the first point
     * @param p2 the other point
     * 
     * @return the distance between these two points
     */
    public static final float distance( float[] p1, float[] p2 ) {
        return (float)Math.sqrt( distanceSquared( p1, p2 ) );
    }
    
    /**
     * Sets vector o to be the vector cross product of vectors v1 and v2.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @param o the output vector
     */
    public static final void cross( float[] v1, float[] v2, float[] o )
    {
        for ( int i = 0; i < o.length; i++ )
        {
            o[ i ] = ( v1[ ( i + 1 ) % v1.length ] * v2[ ( i + 2 ) % v2.length ] ) -
                     ( v1[ ( i + 2 ) % v1.length ] * v2[ ( i + 1 ) % v2.length ] );
        }
    }
    
    /**
     * Computes the dot product of the vectors v1 and v2.
     * 
     * @param v1 the first vector
     * @param v2 the other vector
     */
    public static final float dot( float[] v1, float[] v2 )
    {
        float result = 0.0f;
        
        for ( int i = 0; i < v1.length; i++ )
        {
            result += v1[ i ] * v2[ i ];
        }
        
        return ( result );
    }
    
    /**
     * Sets the elements of dst to src.
     * 
     * @param src the source vector
     * @param trg the target vector
     */
    public static final void set( float[] src, float[] trg )
    {
        for ( int i = 0; i < src.length; i++ )
        {
            trg[ i ] = src[ i ];
        }
    }
    
    /**
     * Creates a new instance of this float array and copies all values.
     * 
     * @param v the source float array
     * 
     * @return the new equal instance
     */
    public static final float[] clone( float[] v )
    {
        float[] result = new float[ v.length ];
        
        set( v, result );
        
        return ( result );
    }
    
    /**
     * Normalizes the given vector v1.
     * 
     * @param v1 the input vector
     */
    public static final void normalize( float[] v1 )
    {
        final float l = length( v1 );
        
        for ( int i = 0; i < v1.length; i++ )
        {
            // zero-div may occur.
            v1[ i ] /= l;
        }
    }
    
    /**
     * Sets the value of the given vector to the scalar multiplication of vector v1.
     * 
     * @param factor the scalar value
     * @param vector the vector to be scaled
     */
    public static final void scale( float factor, float[] vector )
    {
        for ( int i = 0; i < vector.length; i++ )
        {
            vector[ i ] *= factor;
        }
    }
    
    /**
     * Negates the given vector v1.
     * 
     * @param v1 the input vector
     */
    public static final void negate( float[] v1 )
    {
        scale( -1f, v1 );
    }
    
    /**
     * Sets each compnent of the given vector v1 to its absolute.
     * 
     * @param v1 the input vector
     */
    public static final void absolute( float[] v1 )
    {
        for ( int i = 0; i < v1.length; i++ )
        {
            v1[ i ] = Math.abs( v1[ i ] );
        }
    }
    
    /**
     * Returns the angle in radians between vector v1 and v2;<br>
     * The return value is constrained to the range [0, PI].
     * 
     * @param v1 the first vector
     * @param v2 the seconds vector
     * 
     * @return the angle in radians in the range [0, PI]
     */
    public static final float angle( float[] v1, float[] v2 )
    {
        // return (double)Math.acos(dot(v1)/v1.length()/v.length());
        // Numerically, near 0 and PI are very bad condition for acos.
        // In 3-space, |atan2(sin,cos)| is much stable.
        
        final float[] tmp = new float[ v1.length ];
        
        cross( v1, v2, tmp );
        
        float sum = 0.0f;
        for ( int i = 0; i < v1.length; i++ )
        {
            sum += tmp[ i ] * tmp[ i ];
        }
        final double tmp2 = Math.sqrt( sum );
        
        return (float)Math.abs( Math.atan2( tmp2, dot( v1, v2 ) ) );
    }
    
    /**
     * Clamps the minimum value of the given vector to the min parameter.
     * 
     * @param min the lowest value in the vector after clamping
     * @param v teh vector to clamp (modify)
     */
    public static final void clampMin( float min, float[] v )
    {
        for ( int i = 0; i < v.length; i++ )
        {
            if ( v[ i ] < min )
                v[ i ] = min;
        }
    }
    
    /**
     * Clamps the minimum value of the tuple parameter to the min parameter and
     * places the values into the trg vector.
     * 
     * @param min the lowest value in the tuple after clamping
     * @param src the source tuple, which will not be modified
     * @param trg the target tuple, which will be modified
     */
    public static final void clampMin( float min, float[] src, float[] trg )
    {
        set( src, trg );
        clampMin( min, trg );
    }
    
    /**
     * Clamps the maximum value of the given vector to the max parameter.
     * 
     * @param max the largest value in the vector after clamping
     * @param v the vector to clamp (modify)
     */
    public static final void clampMax( float max, float[] v )
    {
        for ( int i = 0; i < v.length; i++ )
        {
            if ( v[ i ] > max )
                v[ i ] = max;
        }
    }
    
    /**
     * Clamps the maximum value of the tuple parameter to the max parameter and
     * places the values into the trg vector.
     * 
     * @param max the lowest value in the tuple after clamping
     * @param src the source tuple, which will not be modified
     * @param trg the target tuple, which will be modified
     */
    public static final void clampMax( float max, float[] src, float[] trg )
    {
        set( src, trg );
        clampMax( max, trg );
    }
    
    /**
     * Clamps the trg vector to the range [min, max].
     * 
     * @param min the lowest value in the vector after clamping
     * @param max the highest value in the vector after clamping
     */
    public static final void clamp( float min, float max, float[] v )
    {
        clampMin( min, v );
        clampMax( max, v );
    }
    
    /**
     * Clamps the src vector parameter to the range [min, max] and places the values
     * into the trg vector.
     * 
     * @param min the lowest value in the vector after clamping
     * @param max the highest value in the vector after clamping
     * @param src the source vector, which will not be modified
     * @param trg the target vector, which will be modified
     */
    public static final void clamp( float min, float max, float[] src, float[] trg )
    {
        set( src, trg );
        clamp( min, max, trg );
    }
    
    /**
     * Rounds the vector to the given number of decimal places.
     * 
     * @param v
     * @param decPlaces
     */
    public static void round( float[] v, int decPlaces )
    {
        final float pow = (float)Math.pow( 10.0, decPlaces );
        
        for ( int i = 0; i < v.length; i++ )
        {
            v[ i ] = (int)( v[ i ] * pow ) / pow;
        }
    }
    
    /**
     * Linearly interpolates between vector v1 and t2 and places the
     * result into v1: v1 = (1 - alpha) * v1 + alpha * v2.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @param alpha the alpha interpolation parameter
     * 
     */
    public static final void interpolate( float[] v1, float[] v2, float alpha )
    {
        final float beta = 1 - alpha;
        
        for ( int i = 0; i < v1.length; i++ )
        {
            v1[ i ] = beta * v1[ i ] + alpha * v2[ i ];
        }
    }
    
    /**
     * Linearly interpolates between vectors v1 and v2 and places the result into
     * vector o: o = (1 - alpha) * v1 + alpha * v2.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @param alpha the alpha interpolation parameter
     */
    public static final void interpolate( float[] v1, float[] v2, float alpha, float[] o )
    {
        set( v1, o );
        interpolate( o, v2, alpha );
    }
    
    /**
     * Creates a String representation of the given vector.
     * 
     * @param vector
     * 
     * @return the String
     */
    public static final String toString( float[] vector )
    {
        StringBuffer s = new StringBuffer( "[ " );
        
        for (int i = 0; i < vector.length; i++ )
        {
            if ( i > 0 )
                s.append( ", " );
            
            s.append( vector[ i ] );
        }
        
        s.append( " ]" );
        
        return ( s .toString());
    }
}
