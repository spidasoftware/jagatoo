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
package org.jagatoo.loaders.models.collada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jagatoo.loaders.models.collada.datastructs.AssetFolder;
import org.jagatoo.loaders.models.collada.datastructs.effects.Effect;
import org.jagatoo.loaders.models.collada.datastructs.effects.LibraryEffects;
import org.jagatoo.loaders.models.collada.datastructs.effects.Profile;
import org.jagatoo.loaders.models.collada.datastructs.effects.ProfileCommon;
import org.jagatoo.loaders.models.collada.datastructs.effects.ShadingParameters;
import org.jagatoo.loaders.models.collada.datastructs.images.Surface;
import org.jagatoo.loaders.models.collada.stax.XMLEffect;
import org.jagatoo.loaders.models.collada.stax.XMLLibraryEffects;
import org.jagatoo.loaders.models.collada.stax.XMLProfileCOMMON;
import org.jagatoo.loaders.models.collada.stax.XMLProfileCOMMON_NewParam;
import org.jagatoo.loaders.models.collada.stax.XMLSampler2D;
import org.jagatoo.loaders.models.collada.stax.XMLSurface;
import org.jagatoo.logging.JAGTLog;

/**
 * Loader for LibraryEffects
 * 
 * @author Amos Wenger (aka BlueSky)
 */
public class LibraryEffectsLoader
{
    /**
     * Load LibraryEffects
     * 
     * @param colladaFile
     *            The collada file to add them to
     * @param libEffects
     *            The JAXB data to load from
     */
    static void loadLibraryEffects( AssetFolder colladaFile, XMLLibraryEffects libEffects )
    {
        LibraryEffects colLibEffects = colladaFile.getLibraryEffects();
        Collection<XMLEffect> effects = libEffects.effects;
        
        for ( XMLEffect effect : effects )
        {
            JAGTLog.debug( "TT] Effect \"", effect.id, "\"" );
            JAGTLog.increaseIndentation();
            Effect colladaEffect = new Effect( effect.id );
            colLibEffects.getEffects().put( colladaEffect.getId(), colladaEffect );
            colladaEffect.profiles = new ArrayList<Profile>();
            
            if ( effect.profileCOMMON != null )
            {
                JAGTLog.debug( "TT] Profile COMMON : loading..." );
                JAGTLog.increaseIndentation();
                colladaEffect.profiles.add(LibraryEffectsLoader.loadCommonProfile(effect, effect.profileCOMMON));
                JAGTLog.decreaseIndentation();
            }
            
            if ( effect.profileCG != null )
            {
                JAGTLog.debug( "EE] CG shaders profile isn't implemented yet !" );
            }
            
            if ( effect.profileGLSL != null )
            {
                JAGTLog.debug( "EE] GLSL shaders profile isn't implemented yet !" );
            }
            
            JAGTLog.decreaseIndentation();
        }
    }
    
    /**
     * Loads a profile common.
     * 
     * @param effect
     * @param profile
     * @param type
     * 
     * @return the loaded profile
     */
    static Profile loadCommonProfile( XMLEffect effect, XMLProfileCOMMON profile )
    {
        ProfileCommon colladaProfile = new ProfileCommon();
        colladaProfile.setSurfaces( new HashMap<String, Surface>() );

    	if (profile.technique.blinn != null) {
			colladaProfile.technique.blinn = new ShadingParameters(
					profile.technique.blinn);
		}
		if (profile.technique.constant != null) {
			colladaProfile.technique.constant = new ShadingParameters(
					profile.technique.constant);
		}
		if (profile.technique.lambert != null) {

			colladaProfile.technique.lambert = new ShadingParameters(
					profile.technique.lambert);
		}
		if (profile.technique.phong != null) {
			colladaProfile.technique.phong = new ShadingParameters(
					profile.technique.phong);
		}

        List<XMLProfileCOMMON_NewParam> newParams = profile.newParams;
        
        if ( newParams != null )
        {
            for ( XMLProfileCOMMON_NewParam newParam : newParams )
            {
                if ( newParam.surface != null )
                {
                    XMLSurface surface = newParam.surface;
                    Surface colladaSurface = new Surface( newParam.sid );
                    colladaSurface.imageIds = new ArrayList<String>();
                    JAGTLog.debug( "TT] Found surface ! (id = ", newParam.sid, ")" );
                    JAGTLog.increaseIndentation();
                    
                    String imageId = surface.initFrom;
                    JAGTLog.debug( "TT] Image id : ", imageId );
                    colladaSurface.imageIds.add( imageId );
                    colladaProfile.getSurfaces().put( colladaSurface.getId(), colladaSurface );
                    JAGTLog.decreaseIndentation();
                }
                else if ( newParam.sampler2D != null )
                {
                    JAGTLog.debug( "TT] Found sampler ! (id = ", newParam.sid, ")" );
                    JAGTLog.increaseIndentation();
                    XMLSampler2D sampler2D = newParam.sampler2D;
                    JAGTLog.debug( "TT] Sampler using source : ", sampler2D.source );
                    JAGTLog.decreaseIndentation();
                }
            }
        }
        
        return ( colladaProfile );
    }
}
