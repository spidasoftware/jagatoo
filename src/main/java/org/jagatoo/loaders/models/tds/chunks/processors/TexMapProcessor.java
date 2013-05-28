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
package org.jagatoo.loaders.models.tds.chunks.processors;

import java.io.IOException;

import org.jagatoo.opengl.enums.TextureFormat;
import org.jagatoo.opengl.enums.TextureMode;
import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.models._util.GeometryFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models.tds.TDSFile;
import org.jagatoo.loaders.models.tds.internal.ModelContext;
import org.jagatoo.loaders.textures.AbstractTexture;

/**
 * A processor to handle the texture map chunk
 * 
 * @author Kevin Glass
 * @author Marvin Froehlich (aka Qudus)
 */
public class TexMapProcessor extends ChunkProcessor
{
    @Override
    public void process( TDSFile file, AppearanceFactory appFactory, GeometryFactory geomFactory, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, ModelContext context, int length ) throws IOException
    {
        int[] chunkID = { -1 };
        float percent = file.readPercentage( true, chunkID );
        
        String texName;
        if ( percent < 0f )
            texName = file.readMatName( false );
        else
            texName = file.readMatName( true );
        
        AbstractTexture texture = appFactory.loadOrGetTexture( texName, file.getBaseURL(), true, true, true, true, true );
        
        appFactory.applyTexture( texture, 0, context.appearance );
        NamedObject texA = context.textureAttributes;
        
        if ( texA == null )
        {
            texA = appFactory.createTextureAttributes( "MODULATE" );
            appFactory.setTextureAttribsTextureMode( texA, TextureMode.MODULATE );
            context.textureAttributes = texA;
        }
        
        if ( ( texture.getFormat() == TextureFormat.RGB ) && ( context.transparency == 0f ) )
        {
            appFactory.applyTransparancyAttributes( null, context.appearance );
        }
    }
    
    public TexMapProcessor()
    {
    }
}
