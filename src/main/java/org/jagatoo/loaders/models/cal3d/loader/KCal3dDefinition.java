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
package org.jagatoo.loaders.models.cal3d.loader;

import com.thoughtworks.xstream.XStream;

import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kman
 * @author Amos Wenger (aka BlueSky)
 * @author Marvin Froehlich (aka Qudus)
 */
public class KCal3dDefinition {
    
    public static Cal3dModelDef load(Reader reader) {
        XStream xs = new XStream();
        getAliases(xs);
        
        return (Cal3dModelDef)xs.fromXML(reader);
    }
    
    public static void save(Cal3dModelDef data, Writer writer) {
        XStream xs = new XStream();
        getAliases(xs);
        xs.toXML(data, writer);
    }
    
    private static void getAliases(XStream xs) {
        xs.alias("model", Cal3dModelDef.class);
        xs.alias("submesh", Cal3dSubMeshDef.class);
        xs.alias("anim", Cal3dAnimDef.class);
    }
    
    public static class Cal3dModelDef {
        public URL skeleton;
        public URL mesh;
        public String skin;
        public URL material;
        public List<Cal3dSubMeshDef> meshes=new ArrayList<Cal3dSubMeshDef>();
        public List<Cal3dAnimDef> animations=new ArrayList<Cal3dAnimDef>();
        public URL baseURL;
    }
    
    public static class Cal3dSubMeshDef {
        public Cal3dSubMeshDef(URL meshURL) {
            this.mesh = meshURL;
        }
        public URL mesh;
        public String skin;
        public URL material;
        public boolean visible;
    }
    
    public static class Cal3dAnimDef {
        public Cal3dAnimDef(String name1, URL anim1) {
            name = name1;
            anim = anim1;
        }
        public String name;
        public URL anim;
    }
    
}
