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
package org.jagatoo.loaders.models.md2;

import java.io.IOException;

import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.util.streams.LittleEndianDataInputStream;

/**
 * The header of a Quake 2 Model file.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MD2Header
{
    public static final int MAGIC_NUMBER = 0x32504449;
    
    public final int version;
    public final int skinWidth;
    public final int skinHeight;           
    public final int frameSize;
    public final int numSkins;
    public final int numVertices;
    public final int numTexCoords;
    public final int numTriangles;     
    public final int numGlCommandBytes;
    public final int numFrames;      
    public final int offsetSkins;
    public final int offsetTexCoords;
    public final int offsetTriangles;
    public final int offsetFrames;       
    public final int offsetGlCommands; 
    public final int offsetEnd;
    
    private MD2Header( LittleEndianDataInputStream in ) throws IOException, IncorrectFormatException
    {
        int ident = in.readInt();
        if ( ident != MAGIC_NUMBER )
            throw new IncorrectFormatException( "This is not an MD2 file!" );
        
        version = in.readInt();
        if ( version != 8 )
            throw new IncorrectFormatException( "Unsupported MD2 version " + version + ". Currently ony version 8 is supported." );
        
        this.skinWidth = in.readInt();
        this.skinHeight = in.readInt();
        this.frameSize = in.readInt();
        this.numSkins = in.readInt();
        this.numVertices = in.readInt();
        this.numTexCoords = in.readInt();
        this.numTriangles = in.readInt();
        this.numGlCommandBytes = in.readInt() * 4;
        this.numFrames = in.readInt();
        this.offsetSkins = in.readInt();
        this.offsetTexCoords = in.readInt();
        this.offsetTriangles = in.readInt();
        this.offsetFrames = in.readInt();
        this.offsetGlCommands = in.readInt();
        this.offsetEnd = in.readInt();
    }
    
    public static final MD2Header readHeader( LittleEndianDataInputStream in ) throws IOException, IncorrectFormatException
    {
        return ( new MD2Header( in ) );
    }
}
