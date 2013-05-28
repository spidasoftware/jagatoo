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
package org.jagatoo.loaders.models.cal3d.core;

import org.openmali.vecmath2.Colorf;
import java.awt.Color;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class CalPlatform {
    public static String readString(DataInput file) throws IOException {
        // get the string length
        int length = file.readInt();
        
        if(length < 0) return null;
        
        // read the string
        byte [ ] strBuffer = new byte [length];
        file.readFully(strBuffer);
        
        return new String(strBuffer, 0, length-1);
    }
    
    public static Colorf readColour(DataInput file) throws IOException {
        float r = file.readUnsignedByte() / 256.0f;
        float g = file.readUnsignedByte() / 256.0f;
        float b = file.readUnsignedByte() / 256.0f;
        float a = file.readUnsignedByte() / 256.0f;
        
        return new Colorf(r, g, b, a);
    }

    public static void writeColour(DataOutput file, Colorf colorf) throws IOException {
        Color colorb = colorf.getAWTColor();
        
        file.writeByte(colorb.getRed());
        file.writeByte(colorb.getGreen());
        file.writeByte(colorb.getBlue());
        file.writeByte(colorb.getAlpha());
    }
    
    /*****************************************************************************/
    /** Writes a string.
     *
     * This function writes a string to a file stream.
     *
     * @param file The file stream to write the string to.
     * @param strValue A reference to the string that should be written.
     *****************************************************************************/
    
    public static void writeString(DataOutput file, String strValue) throws IOException {
        // get the string length
        int length = strValue.length() + 1;
        file.writeInt(length);
        file.writeBytes(strValue);
        file.writeByte('\0');
    }
}

