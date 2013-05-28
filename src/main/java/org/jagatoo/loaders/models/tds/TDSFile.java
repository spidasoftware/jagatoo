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
package org.jagatoo.loaders.models.tds;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.errorhandling.ParsingException;
import org.openmali.vecmath2.Matrix3f;

import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.tds.chunks.TDSChunkType;
import org.jagatoo.loaders.models.tds.chunks.processors.ChunkProcessor;
import org.jagatoo.loaders.models.tds.internal.ModelContext;
import org.jagatoo.logging.JAGTLog;

/**
 * Responsbile for handling the reading of the data file
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class TDSFile
{
    private final DataInputStream din;
    private final URL baseURL;
    private final ModelContext context = new ModelContext();
    
    private final AppearanceFactory appFactory;
    private final GeometryFactory geomFactory;
    private final NodeFactory nodeFactory;
    private final AnimationFactory animFactory;
    private final SpecialItemsHandler siHandler;
    
    private final boolean convertZup2Yup;
    
    public final URL getBaseURL()
    {
        return ( baseURL );
    }
    
    public final boolean convertZup2Yup()
    {
        return ( convertZup2Yup );
    }
    
    public final ModelContext getContext()
    {
        return ( context );
    }
    
    public final byte readByte() throws IOException
    {
        return ( din.readByte() );
    }
    
    public final short readUnsignedByte() throws IOException
    {
        return ( (short)din.readUnsignedByte() );
    }
    
    public final int readUnsignedShort() throws IOException
    {
        int num = din.readUnsignedShort();
        
        return ( ( ( num << 8 ) & 0xFF00 ) | ( ( num >> 8 ) & 0x00FF ) );
    }
    
    public final int readInt() throws IOException
    {
        int num = din.readInt();
        
        return ( ( ( num << 24 ) & 0xFF000000 ) | ( ( num << 8 ) & 0x00FF0000 ) | ( ( num >> 8 ) & 0x0000FF00 ) | ( ( num >> 24 ) & 0x000000FF ) );
    }
    
    public final int readUnsignedInt() throws IOException
    {
        long uint = readInt() & 0xFFFFFFFF;
        
        return ( (int)uint );
    }
    
    public final float readFloat() throws IOException
    {
        return ( Float.intBitsToFloat( readInt() ) );
    }
    
    public final void skipBytes( int bytesToSkip ) throws IOException
    {
        while ( bytesToSkip > 0 )
        {
            bytesToSkip -= din.skipBytes( bytesToSkip );
        }
    }
    
    public final void skipChunk( int length ) throws IOException
    {
        skipBytes( length - 6 );
    }
    
    public final boolean hiddenObject( String name )
    {
        return ( name.charAt( 0 ) == '$' );
    }
    
    public final float readPercentage( boolean readChunkID, int[] chunkID ) throws IOException
    {
        int tag;
        if ( readChunkID )
            tag = readUnsignedShort();
        else
            tag = chunkID[0];
        
        if ( tag == TDSChunkType.INT_PERCENTAGE.getID() )
        {
            readInt();
            
            return ( (float)readUnsignedShort() / 100f );
        }
        else if ( tag == TDSChunkType.FLOAT_PERCENTAGE.getID() )
        {
            readInt();
            
            return ( readFloat() );
        }
        else
        {
            String message = "INT_PERCENTAGE/FLOAT_PERCENTAGE expected";
            //IOException ex = new IOException( message );
            //JAGTLog.print( ex );
            //throw ex;
            System.err.println( message );
            
            return ( -Float.MAX_VALUE );
        }
    }
    
    public final String readName() throws IOException
    {
        char[] chars = new char[ 256 ];
        int length = 0;
        char c;
        
        while ( ( c = (char)din.readUnsignedByte() ) != '\0' )
        {
            if ( chars.length <= length )
            {
                char[] tmp = new char[ (int)( length * 1.5 ) ];
                System.arraycopy( chars, 0, tmp, 0, length );
                chars = tmp;
            }
            
            chars[length++] = c;
        }
        
        return ( new String( chars, 0, length ) );
    }
    
    public final String readMatName( boolean readChunkID ) throws IOException
    {
        if ( readChunkID )
            readUnsignedShort();
        readInt();
        
        return ( readName() );
    }
    
    public final float[] readColor( boolean readChunkID, int[] chunkID ) throws IOException
    {
        int tag;
        if ( readChunkID )
            tag = readUnsignedShort();
        else
            tag = chunkID[0];
        
        if ( tag == TDSChunkType.COLOR_F.getID() )
        {
            readInt();
            
            return ( new float[] { readFloat(), readFloat(), readFloat() } );
        }
        else if ( tag == TDSChunkType.COLOR_24.getID() )
        {
            readInt();
            
            return ( new float[] { (float)din.readUnsignedByte() / 255f, (float)din.readUnsignedByte() / 255f, (float)din.readUnsignedByte() / 255f } );
        }
        else
        {
            String message = "COLOR_F/COLOR_24 expected";
            //IOException ex = new IOException( message );
            //JAGTLog.print( ex );
            //throw ex;
            System.err.println( message );
            
            return ( null );
        }
    }
    
    private static final String createHexString( int chunkID )
    {
        String hex = Integer.toHexString( chunkID ).toUpperCase();
        
        if ( hex.length() >= 4 )
            return ( "0x" + hex );
        
        char[] chars = new char[ 6 ];
        chars[0] = '0';
        chars[1] = 'x';
        
        int n = 4 - hex.length();
        for ( int i = 0; i < n; i++ )
        {
            chars[2 + i] = '0';
        }
        
        for ( int i = 0; i < hex.length(); i++ )
        {
            chars[2 + n + i] = hex.charAt( i );
        }
        
        return ( new String( chars ) );
    }
    
    public TDSChunkType processChunk() throws IOException
    {
        int chunkID = this.readUnsignedShort();
        int length = this.readInt();
        
        ChunkProcessor cp = ChunkProcessor.getChunkProcessor( chunkID );
        
        TDSChunkType chunkType = TDSChunkType.valueOf( chunkID );
        String logChunkType = ( chunkType != null ) ? chunkType.name() : createHexString( chunkID );
        //System.out.println( logChunkType );
        //System.out.println( logChunkType + ", " + p );
        //long t0 = System.currentTimeMillis();
        if ( cp != null )
        {
            JAGTLog.debug( "Processing chunk: ", logChunkType );
            cp.process( this, appFactory, geomFactory, nodeFactory, animFactory, siHandler, this.getContext(), length );
        }
        else
        {
            JAGTLog.debug( "Skipping Chunk: ", logChunkType );
            this.skipChunk( length );
        }
        //System.out.println( System.currentTimeMillis() - t0 );
        
        return ( chunkType );
    }
    
    public void close() throws IOException
    {
        din.close();
    }
    
    private TDSFile( InputStream in, URL baseURL, AppearanceFactory appFactory, GeometryFactory geomFactory, boolean convertZup2Yup, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler ) throws IOException
    {
        if ( !( in instanceof BufferedInputStream ) )
            in = new BufferedInputStream( in );
        
        this.din = new DataInputStream( in );
        
        this.baseURL = baseURL;
        
        this.appFactory = appFactory;
        this.geomFactory = geomFactory;
        this.nodeFactory = nodeFactory;
        this.animFactory = animFactory;
        this.siHandler = siHandler;
        
        this.convertZup2Yup = convertZup2Yup;
    }
    
    private void finalizeModel( boolean convertZup2Yup, NamedObject rootGroup, NodeFactory nodeFactory, SpecialItemsHandler siHandler )
    {
        context.applyAppearanceAttributes( nodeFactory );
        
        for ( NamedObject rootNode : context.rootNodes )
        {
            nodeFactory.addNodeToGroup( rootNode, rootGroup );
        }
        
        for ( NamedObject shape: context.unanimatedNodes )
        {
            if ( convertZup2Yup )
            {
                nodeFactory.rotateShapeOrGeometry( shape, Matrix3f.Z_UP_TO_Y_UP );
            }
            
            //context.rootNodes.add( shape );
            nodeFactory.addNodeToGroup( shape, rootGroup );
        }
        
        //context.unanimatedNodes.clear();
        
        if ( context.animationFound )
        {
            Object[] animControllers = (Object[])Array.newInstance( context.animControllers.get( 0 ).getClass(), context.animControllers.size() );
            context.animControllers.toArray( animControllers );
            Object animation = animFactory.createAnimation( "default", context.framesCount, 25f, animControllers, null );
            siHandler.addAnimation( animation );
        }
    }
    
    public static final TDSFile load( InputStream in, URL baseURL, AppearanceFactory appFactory, GeometryFactory geomFactory, boolean convertZup2Yup, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        TDSFile file = new TDSFile( in, baseURL, appFactory, geomFactory, convertZup2Yup, nodeFactory, animFactory, siHandler );
        
        try
        {
            while ( true )
            {
                file.processChunk();
            }
        }
        catch ( IOException e )
        {
            if ( e instanceof EOFException )
            {
                // left blank since this will happen at the end of the file
            }
            else
            {
                throw e;
            }
        }
        finally
        {
            file.close();
        }
        
        file.finalizeModel( convertZup2Yup, rootGroup, nodeFactory, siHandler );
        
        return ( file );
    }
}
