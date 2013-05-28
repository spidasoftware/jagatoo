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
package org.jagatoo.loaders.models.bsp;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models._util.AppearanceFactory;
import org.jagatoo.loaders.textures.AbstractTexture;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public final class BSPTextureAnimator
{
    private final AbstractTexture[] animFrames;
    private final int numFrames;
    private final NamedObject appearance;
    private final int textureUnit;
    
    private final AppearanceFactory appFactory;
    
    private final long frameTime;
    
    private long initialGameNanos = -1L;
    private int lastFrame = 0;
    
    private final void setFrame( int frame )
    {
        appFactory.applyTexture( animFrames[frame], textureUnit, appearance );
        
        lastFrame = frame;
    }
    
    public final void update( long gameNanos )
    {
        if ( initialGameNanos < 0L )
        {
            initialGameNanos = gameNanos;
            
            //setFrame( 0 );
            
            return;
        }
        
        int frame = (int)( ( ( gameNanos - initialGameNanos ) / frameTime ) % numFrames );
        if ( frame != lastFrame )
        {
            setFrame( frame );
        }
    }
    
    public BSPTextureAnimator( AbstractTexture[] animFrames, NamedObject appearance, int textureUnit, AppearanceFactory appFactory, float fps )
    {
        this.animFrames = animFrames;
        this.numFrames = animFrames.length - 1; // The last frame in the array is the "off-frame"!
        this.appearance = appearance;
        this.textureUnit = textureUnit;
        
        this.appFactory = appFactory;
        
        this.frameTime = (long)( ( 1f / fps ) * 1000000000L );
    }
}
