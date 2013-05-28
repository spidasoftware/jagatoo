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

import java.net.URL;

import org.openmali.vecmath2.Colorf;


/*****************************************************************************/
/** The core material class.
 *****************************************************************************/
public class CalCoreMaterial {
    protected String name;
    protected Colorf ambientColor;
    protected Colorf diffuseColor;
    protected Colorf specularColor;
    protected float shininess;
    protected Map[] maps;
    protected Object userData;
    private URL baseURL;
    
    
    /*****************************************************************************/
    /** Constructs the core material instance.
     *
     * This function is the default constructor of the core material instance.
     *****************************************************************************/
    
    public  CalCoreMaterial() {
        userData = null;
    }
    
    /*****************************************************************************/
    /** Returns the ambient color.
     * 
     * This function returns the ambient color of the core material instance.
     *
     * @return A reference to the ambient color.
     *****************************************************************************/
    
    public Colorf getAmbientColor() {
        return ambientColor;
    }
    
    
    /*****************************************************************************/
    /** Returns the diffuse color.
     *
     * This function returns the diffuse color of the core material instance.
     *
     * @return A reference to the diffuse color.
     *****************************************************************************/
    
    public Colorf getDiffuseColor() {
        return diffuseColor;
    }
    
    
    /*****************************************************************************/
    /** Returns the number of maps.
     *
     * This function returns the number of mapss in the core material instance.
     *
     * @return The number of maps.
     *****************************************************************************/
    
    public int getMapCount() {
        return maps.length;
    }
    
    
    /*****************************************************************************/
    /** Returns a specified map texture filename.
     *
     * This function returns the texture filename for a specified map ID of the
     * core material instance.
     *
     * @param mapId The ID of the map.
     *
     * @return One of the following values:
     *         \li the filename of the map texture
     *         \li an empty string if an error happend
     *****************************************************************************/
    
    public String getMapFilename(int mapId) {
        return maps[mapId].filename;
    }
    
    
    /*****************************************************************************/
    /** Provides access to a specified map user data.
     *
     * This function returns the user data stored in the specified map of the core
     * material instance.
     *
     * @param mapId The ID of the map.
     *
     * @return One of the following values:
     *         \li the user data stored in the specified map
     *         \li \b 0 if an error happend
     *****************************************************************************/
    
    public Object getMapUserData(int mapId) {
        return maps[mapId].userData;
    }
    
    
    /*****************************************************************************/
    /** Returns the shininess factor.
     *
     * This function returns the shininess factor of the core material instance.
     *
     * @return The shininess factor.
     *****************************************************************************/
    
    public float getShininess() {
        return shininess;
    }
    
    
    /*****************************************************************************/
    /** Returns the specular color.
     *
     * This function returns the specular color of the core material instance.
     *
     * @return A reference to the specular color.
     *****************************************************************************/
    
    public Colorf getSpecularColor() {
        return specularColor;
    }
    
    
    /*****************************************************************************/
    /** Provides access to the user data.
     *
     * This function returns the user data stored in the core material instance.
     *
     * @return The user data stored in the core material instance.
     *****************************************************************************/
    
    public Object getUserData() {
        return userData;
    }
    
    public void setMaps(Map [] maps) {
        this.maps = maps;
    }
    
    /*****************************************************************************/
    /** Returns the map vector.
     *
     * This function returns the vector that contains all maps of the core material
     * instance.
     *
     * @return A reference to the map vector.
     *****************************************************************************/
    
    public Map [ ] getMaps() {
        return maps;
    }
    
    
    /*****************************************************************************/
    /** Reserves memory for the maps.
     *
     * This function reserves memory for the maps of the core material instance.
     *
     * @param mapCount The number of maps that this core material instance should
     *                 be able to hold.
     *****************************************************************************/
    
    public void reserve(int mapCount) {
        // reserve the space needed in all the vectors
        // DL - do we need to copy existing ??
        maps = new Map [mapCount];
    }
    
    
    /*****************************************************************************/
    /** Sets the ambient color.
     *
     * This function sets the ambient color of the core material instance.
     *
     * @param ambientColor The ambient color that should be set.
     *****************************************************************************/
    
    public void setAmbientColor(Colorf ambientColor) {
        this.ambientColor = ambientColor;
    }
    
    
    /*****************************************************************************/
    /** Sets the diffuse color.
     *
     * This function sets the diffuse color of the core material instance.
     *
     * @param diffuseColor The diffuse color that should be set.
     *****************************************************************************/
    
    public void setDiffuseColor(Colorf diffuseColor) {
        this.diffuseColor = diffuseColor;
    }
    
    
    /*****************************************************************************/
    /** Sets a specified map.
     *
     * This function sets a specified map in the core material instance.
     *
     * @param mapId  The ID of the map.
     * @param map The map that should be set.
     *****************************************************************************/
    
    public void setMap(int mapId, Map map) {
        maps [mapId] = map;
    }
    
    
    /*****************************************************************************/
    /** Stores specified map user data.
     *
     * This function stores user data in a specified map of the core material
     * instance.
     *
     * @param mapId  The ID of the map.
     * @param userData The user data that should be stored.
     *****************************************************************************/
    
    public void setMapUserData(int mapId, Object userData) {
        maps[mapId].userData = userData;
    }
    
    
    /*****************************************************************************/
    /** Sets the shininess factor.
     *
     * This function sets the shininess factor of the core material instance.
     *
     * @param shininess The shininess factor that should be set.
     *****************************************************************************/
    
    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
    
    
    /*****************************************************************************/
    /** Sets the specular color.
     *
     * This function sets the specular color of the core material instance.
     *
     * @param specularColor The specular color that should be set.
     *****************************************************************************/
    
    public void setSpecularColor(Colorf specularColor) {
        this.specularColor = specularColor;
    }
    
    
    /*****************************************************************************/
    /** Stores user data.
     *
     * This function stores user data in the core material instance.
     *
     * @param userData The user data that should be stored.
     *****************************************************************************/
    
    public void setUserData(Object userData) {
        this.userData = userData;
    }
    
    /** Gets the material resource name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /** Sets the material resource name.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /// The core material Map.
    public static class Map {
        public String filename;
        public Object userData;
    }
    
    /*
     * Used to find textures 
     */
    public void setBaseURL(URL baseURL) {
        this.baseURL = baseURL;
    }

    public URL getBaseURL() {
        return baseURL;
    }
}
