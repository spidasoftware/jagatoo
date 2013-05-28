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
package org.jagatoo.input.localization;

import java.util.HashMap;
import java.util.Locale;

import org.jagatoo.input.devices.components.Key;
import org.jagatoo.input.devices.components.Keys;
import org.jagatoo.input.localization.mappings.Mapping;
import org.jagatoo.input.localization.mappings.german.MappingGermany;

/**
 * This class can be used to map key-assotiated characters
 * to their counterparts for the key with a modifier held.<br>
 * This class must be initialized <b>before</b> any access to the
 * {@link Key} or {@link Keys} class.
 * This class is consulted at creation time of a {@link Key} with
 * a key char assotiated. Specifically this means, that the Key's
 * {@link Key#hasKeyChar()} method returns true.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class KeyboardLocalizer
{
    private static final Mapping DEFAULT_MAPPING = new MappingGermany();
    
    private static final HashMap< Locale, Mapping > mappingMap = new HashMap< Locale, Mapping >();
    
    private static Mapping mapping = null;
    
    /**
     * Registers a new Locale->Mapping mapping, that is considered by the
     * {@link #autoselectMapping()} method.
     * 
     * @param locale
     * @param mapping
     */
    public static void registerMapping( Locale locale, Mapping mapping )
    {
        mappingMap.put( locale, mapping );
    }
    
    /**
     * Looks up the current mappings-map for the current system's locale.
     * If the locale isn't found, a default mapping is used.
     * Use {@link #setMapping(Mapping)} to force a {@link Mapping} to be used.
     * 
     * @return the selected {@link Mapping}
     */
    public static final Mapping autoselectMapping()
    {
        Mapping mapping = mappingMap.get( Locale.getDefault() );
        
        if ( mapping == null )
            KeyboardLocalizer.mapping = DEFAULT_MAPPING;
        else
            KeyboardLocalizer.mapping = mapping;
        
        return ( KeyboardLocalizer.mapping );
    }
    
    /**
     * Sets the new mapping for your localization.
     * 
     * @param mapping
     */
    public static void setMapping( Mapping mapping )
    {
        if ( mapping == null )
            throw new IllegalArgumentException( "mapping must not be null" );
        
        KeyboardLocalizer.mapping = mapping;
    }
    
    /**
     * Selects a Mapping, if none is currently selected.<br>
     * This will select the mapping from the System's default Locale.
     * 
     * @return the currently selected Mapping (never <code>null</code>).
     */
    public static final Mapping getMapping()
    {
        if ( mapping == null )
            autoselectMapping();
        
        return ( mapping );
    }
    
    /**
     * @return the class-name of the current Mapping.
     */
    public static final String getCurrentMappingName()
    {
        Mapping mapping = getMapping();
        
        if ( mapping == null )
            return ( "[NO MAPPING]" );
        
        return ( mapping.getClass().getName() );
    }
}
