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
package org.jagatoo.loaders.textures.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.jagatoo.loaders.textures.AbstractTexture;

/**
 * A {@link TextureCache} caches Textures, so that they don't
 * get loaded multiple times.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class TextureCache
{
    private final Stack<Boolean> enabledStack = new Stack<Boolean>();
    private boolean enabled = true;
    
    private final ArrayList<String> cacheKeys = new ArrayList<String>();
    
    public synchronized void setEnabled( boolean enabled )
    {
        enabledStack.set( enabledStack.size() - 1, enabled );
        this.enabled = enabled;
    }
    
    public synchronized void pushEnabled( boolean enabled )
    {
        enabledStack.push( enabled );
        this.enabled = enabled;
    }
    
    public synchronized void popEnabled()
    {
        if ( enabledStack.size() <= 1 )
            return;
        
        enabledStack.pop();
        this.enabled = enabledStack.peek();
    }
    
    public final boolean isEnabled()
    {
        return ( enabled );
    }
    
    /**
     * Removes the Texture from the cache.
     * 
     * @param key the cache-key of the Texture to be removed.
     */
    protected abstract void addImpl( String key, AbstractTexture texture );
    
    /**
     * Removes the Texture from the cache.
     * 
     * @param key the cache-key of the Texture to be removed.
     */
    public synchronized final void add( String key, AbstractTexture texture )
    {
        cacheKeys.add( key );
        
        addImpl( key, texture );
    }
    
    /**
     * Removes the Texture from the cache.
     * 
     * @param texture the Texture to be removed
     */
    protected abstract void removeImpl( AbstractTexture texture );
    
    /**
     * Removes the Texture from the cache.
     * 
     * @param texture the Texture to be removed
     */
    public synchronized final void remove( AbstractTexture texture )
    {
        cacheKeys.remove( texture.getCacheKey() );
    }
    
    /**
     * @param key
     * 
     * @return the Texture cached under the given key or null.
     */
    public abstract AbstractTexture get( String key );
    
    public synchronized final String[] getCachedKeys()
    {
        String[] keys = new String[ cacheKeys.size() ];
        
        keys = cacheKeys.toArray( keys );
        
        return ( keys );
    }
    
    public abstract Collection<AbstractTexture> getCachedTextures();
    
    /**
     * Clears the Texture-cache.
     */
    public abstract void clear();
    
    protected TextureCache()
    {
        this.enabled = true;
        this.enabledStack.push( Boolean.TRUE );
    }
}
