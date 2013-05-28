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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.IncorrectFormatException;
import org.jagatoo.loaders.ParsingException;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;

/**
 * Extends LineNumberReader to read and parse a single line into pieces for easy
 * reference by the parsing code.
 * 
 * @author David Yazel
 */
public class AseReader //extends LineNumberReader
{
    private LineNumberReader lnReader;
    private int charPos;
    private AseFileLine afLine = new AseFileLine();
    boolean blockEnd;
    boolean blockStart;
    private StringBuffer wordBuff;
    private int lineNo = 0;
    boolean convertMeshCoordinates = true;
    
    public AseReader( Reader in )
    {
        this( in, true );
    }
    
    /**
     * Construct new ASE reader that will read ASE data from specified reader to
     * specified ASE file with optional converion of coordinate system from MAX
     * to Xith3D
     * 
     * @param in Reader to read ASE data from
     * @param convertMeshCoordinates true if ASE coordinates should be converted
     *            to Xith3D format, false if they should be left unchanged
     */
    public AseReader( Reader in, boolean convertMeshCoordinates )
    {
        lnReader = new LineNumberReader( ( in instanceof BufferedReader ) ? in : new BufferedReader( in ) );
        
        wordBuff = new StringBuffer( 100 );
        this.convertMeshCoordinates = convertMeshCoordinates;
    }
    
    public int getLastLineNumber()
    {
        return ( lineNo );
    }
    
    /**
     * Uses line and ch to collect an entire word. Spaces are counted as
     * separators or within quotes.
     */
    private String parseWord( String line )
    {
        wordBuff.setLength( 0 );
        
        int n = line.length();
        boolean inString = false;
        
        while ( charPos < n )
        {
            char c = line.charAt( charPos++ );
            
            // if its a space then either throw it away, add to string or
            // end the word
            if ( ( c == ' ' ) || ( c == 9 ) )
            {
                if ( inString )
                {
                    wordBuff.append( c );
                }
                else if ( wordBuff.length() > 0 )
                {
                    return ( wordBuff.toString() );
                }
            }
            else if ( c == '"' )
            {
                if ( ( wordBuff.length() > 0 ) || ( inString ) )
                {
                    return ( wordBuff.toString() );
                }
                
                inString = true;
            }
            else
            {
                wordBuff.append( c );
            }
        }
        
        if ( wordBuff.length() == 0 )
            return ( null );
        
        return ( wordBuff.toString() );
    }
    
    /**
     * Parses the line into pieces for easy matching
     */
    private AseFileLine parseLine( String line )
    {
        afLine.reset();
        afLine.setNumber( lineNo );
        
        // start parsing at the first character
        charPos = 0;
        blockStart = false;
        blockEnd = false;
        
        line = line.trim();
        
        String word;
        
        while ( ( word = parseWord( line ) ) != null )
        {
            if ( afLine.getKey().length() == 0 )
                afLine.setKey( word );
            else
                afLine.addParameter( word );
            
            blockStart = ( word.indexOf( "{" ) >= 0 );
            blockEnd = ( word.indexOf( "}" ) >= 0 );
        }
        
        return ( afLine );
    }
    
    /**
     * Reads in a line and breaks it into peices. If the line starts with a
     * star, then get the keyword out and then read the params.
     */
    public AseFileLine readAseLine()
    {
        try
        {
            String line;
            
            while ( true )
            {
                line = lnReader.readLine();
                lineNo++;
                
                if ( line == null )
                {
                    return ( null );
                }
                
                if ( line.length() > 0 )
                    break;
            }
            
            return ( parseLine( line ) );
        }
        catch ( IOException e )
        {
            return ( null );
        }
    }
    
    public void close() throws IOException
    {
        lnReader.close();
    }
    
    /**
     * 
     * @param in
     * @param baseURL
     * @param appFactory
     * @param geomFactory
     * @param convertZup2Yup
     * @param scale
     * @param nodeFactory
     * @param animFactory
     * @param siHandler
     * @param rootGroup
     * @throws IOException
     * @throws IncorrectFormatException
     * @throws ParsingException
     */
    public static final void load( InputStream in, URL baseURL, AppearanceFactory appFactory, GeometryFactory geomFactory, boolean convertZup2Yup, float scale, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, NamedObject rootGroup ) throws IOException, IncorrectFormatException, ParsingException
    {
        AseReader aseReader = new AseReader( new BufferedReader( new InputStreamReader( in ) ) );
        AseFile aseFile = new AseFile();
        aseFile.parse( aseReader );
        
        AseConverter.getTransformGroupTree( aseFile, appFactory, baseURL, geomFactory, convertZup2Yup, scale, nodeFactory, siHandler, rootGroup );
    }
}
