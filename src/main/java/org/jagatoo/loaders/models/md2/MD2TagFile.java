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
package org.jagatoo.loaders.models.md2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.jagatoo.logging.JAGTLog;
import org.jagatoo.util.streams.LittleEndianDataInputStream;
import org.openmali.vecmath2.Matrix4f;

/**
 * Insert type comment here.
 * 
 * @author Piotr Maceluch (aka kerno)
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2TagFile
{
    private static int MAGIC_NUMBER = 0x3250444A;
    private static int VERSION = 1;
    private static int MAX_TAG_NAME_LENGTH = 64;
    
    private int version;
    private int numTags;
    private int numFrames;
    private int offsetNames;
    private int offsetMatrices;
    //private int offsetEnd;
    //private int offsetExtractEnd;
    
    private String[] tagNames;
    private Matrix4f[][] matrices;
    
    public final int getNumTags()
    {
        return ( tagNames.length );
    }
    
    public final String[] getTagNames()
    {
        return ( tagNames );
    }
    
    public final Matrix4f[][] getMatrices()
    {
        return ( matrices );
    }
    
    private void readHeader( LittleEndianDataInputStream in ) throws IOException, IncorrectFormatException, ParsingException
    {
        int ident = in.readInt();
        if ( ident != MAGIC_NUMBER )
            throw new IncorrectFormatException( "This is not an MD2-TAG file!" );
        
        version = in.readInt();
        if ( version != VERSION )
            throw new IncorrectFormatException( "Unsupported MD2-TAG version " + version + ". Currently ony version " + VERSION + " is supported." );
        
        numTags = in.readInt();
        numFrames = in.readInt();
        offsetNames = in.readInt();
        offsetMatrices = in.readInt();
        /*offsetEnd = */in.readInt();
        /*offsetExtractEnd = */in.readInt();
    }
    
    private String[] readTagNames( LittleEndianDataInputStream in ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 TAG names..." );
        
        in.skipBytes( offsetNames - in.getPointer() );
        
        String[] tagNames = new String[ numTags ];
        
        for ( int i = 0; i != numTags; i++ )
        {
            tagNames[i] = in.readCString( MAX_TAG_NAME_LENGTH, true );
        }
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( tagNames );
    }
    
    private Matrix4f readMatrix( LittleEndianDataInputStream in, boolean convertZup2Yup, float scale ) throws IOException
    {
        Matrix4f m = new Matrix4f();
        
        for ( int i = 0; i < 4; i++ )
        {
            float f1 = in.readFloat();
            float f2 = in.readFloat();
            float f3 = in.readFloat();
            
            m.setColumn( i, f1, f2, f3, ( i < 3 ) ? 0f : 1f );
        }
        
        if ( convertZup2Yup )
        {
            /*
            Matrix3f m3 = Matrix3f.fromPool();
            m.getRotationScale( m3 );
            AxisAngle3f aa = AxisAngle3f.fromPool();
            aa.set( m3 );
            Matrix3f.toPool( m3 );
            aa.set( aa.getX(), aa.getZ(), -aa.getY(), aa.getAngle() );
            m.setRotation( aa );
            AxisAngle3f.toPool( aa );
            
            if ( scale != 1.0f )
            {
                m.m03( m.m03() * scale );
                m.m13( m.m23() * scale );
                m.m23( -m.m13() * scale );
            }
            else
            {
                m.m03( m.m03() );
                m.m13( m.m23() );
                m.m23( -m.m13() );
            }
            */
            
            m.mul( Matrix4f.Z_UP_TO_Y_UP, m );
            m.mul( m, Matrix4f.ROT_PLUS_90_DEG_BY_X_AXIS );
            
            if ( scale != 1.0f )
            {
                m.m03( m.m03() * scale );
                m.m13( m.m13() * scale );
                m.m23( m.m23() * scale );
            }
        }
        else if ( scale != 1.0f )
        {
            m.m03( m.m03() * scale );
            m.m13( m.m13() * scale );
            m.m23( m.m23() * scale );
        }
        
        return ( m );
    }
    
    private Matrix4f[][] readMatrices( LittleEndianDataInputStream in, boolean convertZup2Yup, float scale ) throws IOException, IncorrectFormatException, ParsingException
    {
        long t0 = System.currentTimeMillis();
        JAGTLog.debug( "Loading MD2 TAG matrices..." );
        
        in.skipBytes( offsetMatrices - in.getPointer() );
        
        Matrix4f[][] matrices = new Matrix4f[ numFrames ][];
        for ( int f = 0; f < numFrames; f++ )
        {
            matrices[f] = new Matrix4f[ numTags ];
        }
        
        for ( int t = 0; t < numTags; t++ )
        {
            for ( int f = 0; f < numFrames; f++ )
            {
                Matrix4f matrix = readMatrix( in, convertZup2Yup, scale );
                // TODO: explain why can't we just use raw matrix as the original project seems to do so
                //matrix = preProcessMatrix(matrix);
                
                matrices[f][t] = matrix;
            }
        }
        
        JAGTLog.debug( "done. (", ( System.currentTimeMillis() - t0 ) / 1000f, " seconds)" );
        
        return ( matrices );
    }
    
    public MD2TagFile( InputStream in, boolean convertZup2Yup, float scale ) throws IOException, IncorrectFormatException, ParsingException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        LittleEndianDataInputStream lein = new LittleEndianDataInputStream( in );
        
        readHeader( lein );
        this.tagNames = readTagNames( lein );
        this.matrices = readMatrices( lein, convertZup2Yup, scale );
        
        lein.close();
    }
}
