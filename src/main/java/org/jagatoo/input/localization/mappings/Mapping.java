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
package org.jagatoo.input.localization.mappings;

import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.Key;

/**
 * This is used to map modified chars to unmodified ones.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface Mapping
{
    /**
     * @return the key or {Y, Z}, which is the upper one.
     * On a German keyboard this is the Z key on US or UK keyboard
     * this is the Y key.
     */
    public abstract Key getUpperYZKey();
    
    /**
     * @return the key or {Y, Z}, which is the lower one.
     * On a German keyboard this is the Y key on US or UK keyboard
     * this is the Z key.
     */
    public abstract Key getLowerYZKey();
    
    /**
     * Must return the char, that is assotiated to the key with the
     * given key-code when the given modifier is pressed.
     * 
     * @param key the queried key
     * @param unmodChar
     * @param modifier this is one of {@link Keys#MODIFIER_SHIFT}, {@link Keys#MODIFIER_ALT}, {@link Keys#MODIFIER_ALT_GR}
     * 
     * @return the modified char.
     */
    public abstract char getModifiedChar( Key key, char unmodChar, int modifierMask );
    
    public abstract Key getLocalizedKey( char keyChar );
    
    /**
     * Searches for a localized name for the given Key.<br>
     * If none was found, the result of key.getName() is returned.
     * 
     * @param key
     * 
     * @return the localized key-name.
     */
    public abstract String getLocalizedKeyName( DeviceComponent key );
}
