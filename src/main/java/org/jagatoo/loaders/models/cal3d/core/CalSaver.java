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

import org.jagatoo.util.streams.LittleEndianDataOutputStream;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector2f;
import org.openmali.vecmath2.Vector3f;

import org.jagatoo.loaders.models.cal3d.buffer.TexCoord2fBuffer;
import org.jagatoo.loaders.models.cal3d.buffer.Vector3fBuffer;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.Vector;


/*****************************************************************************/
/** Provides static methods for writing Cal3D elements to backing store.
 *****************************************************************************/
public class CalSaver {
    /*****************************************************************************/
    /** Saves a core animation instance.
     *
     * This function saves a core animation instance to a stream.
     *****************************************************************************/
    
    public static void saveCoreAnimation (OutputStream file, CalCoreAnimation coreAnimation) throws IOException
    {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream (file);
     
        out.write (CalLoader.ANIMATION_FILE_MAGIC);
     
        out.writeInt (CalLoader.CURRENT_FILE_VERSION);
     
        // write the duration of the core animation
        out.writeFloat (coreAnimation.getDuration ());
     
        List<CalCoreTrack> listCoreTrack  = coreAnimation.getListCoreTrack ();
     
        // write the number of tracks
        out.writeInt (listCoreTrack.size ());
     
        // write all core tracks
        for (CalCoreTrack track : listCoreTrack)
        {
            saveCoreTrack (out, track);
        }
    }
     
    
    /*****************************************************************************/
    /** Saves a core bone instance.
     *
     * This function saves a core bone instance to a file stream.
     *
     * @param file The file stream to save the core bone instance to.
     * @param coreBone A pointer to the core bone instance that should be saved.
     *****************************************************************************/
    
    protected static void saveCoreBones (DataOutput file, CalCoreBone coreBone) throws IOException
    {
     
        // write the name of the bone
        CalPlatform.writeString (file, coreBone.getName ());
     
        // write the translation of the bone
        Vector3f translation = coreBone.getTranslation ();
        file.writeFloat (translation.getX());
        file.writeFloat (translation.getY());
        file.writeFloat (translation.getZ());
     
        // write the rotation of the bone
        Quaternion4f rotation = coreBone.getRotation ();
        file.writeFloat (rotation.getA());
        file.writeFloat (rotation.getB());
        file.writeFloat (rotation.getC());
        file.writeFloat (rotation.getD());
     
        // write the translation of the bone
        Vector3f translationBoneSpace = coreBone.getTranslationBoneSpace ();
        file.writeFloat (translationBoneSpace.getX());
        file.writeFloat (translationBoneSpace.getY());
        file.writeFloat (translationBoneSpace.getZ());
     
        // write the rotation of the bone
        Quaternion4f rotationBoneSpace = coreBone.getRotationBoneSpace ();
        file.writeFloat (rotationBoneSpace.getA());
        file.writeFloat (rotationBoneSpace.getB());
        file.writeFloat (rotationBoneSpace.getC());
        file.writeFloat (rotationBoneSpace.getD());
     
        // write the parent bone id
        file.writeInt (coreBone.getParentId ());
     
        // get children list
        List<Integer> listChildId = coreBone.getListChildId ();
     
        // write the number of children
        file.writeInt (listChildId.size ());
     
        // write all children ids
        for (int childId : listChildId)
        {
            file.writeInt (childId);
        }
    }
     
    
    /*****************************************************************************/
    /** Saves a core keyframe instance.
     *
     * This function saves a core keyframe instance to a file stream.
     *
     * @param file The file stream to save the core keyframe instance to.
     * @param coreKeyframe A pointer to the core keyframe instance that should be
     *                      saved.
     *****************************************************************************/
    
    protected static void saveCoreKeyframe (DataOutput file, CalCoreKeyframe coreKeyframe) throws IOException
    {
        // write the time of the keyframe
        file.writeFloat (coreKeyframe.getTime ());
     
        // write the translation of the keyframe
        Vector3f translation = coreKeyframe.getTranslation ();
        file.writeFloat (translation.getX());
        file.writeFloat (translation.getY());
        file.writeFloat (translation.getZ());
     
        // write the rotation of the keyframe
        Quaternion4f rotation = coreKeyframe.getRotation ();
        file.writeFloat (rotation.getA());
        file.writeFloat (rotation.getB());
        file.writeFloat (rotation.getC());
        file.writeFloat (rotation.getD());
    }
     
    
    /*****************************************************************************/
    /** Saves a core material instance.
     *
     * This function saves a core material instance to a file.
     *
     * @param file The name of the file to save the core material instance
     *                    to.
     * @param coreMaterial A pointer to the core material instance that should
     *                      be saved.
     *****************************************************************************/
    
    public static void saveCoreMaterial (OutputStream file, CalCoreMaterial coreMaterial) throws IOException
    {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream (file);
     
        out.write (CalLoader.MATERIAL_FILE_MAGIC);
     
        out.writeInt (CalLoader.CURRENT_FILE_VERSION);
     
        // write the ambient color
        CalPlatform.writeColour (out, coreMaterial.getAmbientColor ());
     
        // write the diffuse color
        CalPlatform.writeColour (out, coreMaterial.getDiffuseColor ());
     
        // write the specular color
        CalPlatform.writeColour (out, coreMaterial.getSpecularColor ());
     
        // write the shininess factor
        out.writeFloat (coreMaterial.getShininess ());
     
        // get the map vector
        CalCoreMaterial.Map [] vectorMap = coreMaterial.getMaps ();
     
        // write the number of maps
        out.writeInt (vectorMap.length);
     
        // write all maps
        for (CalCoreMaterial.Map map : vectorMap)
        {
            // write the filename of the map
            CalPlatform.writeString (out, map.filename);
        }
    }
     
     
    /*****************************************************************************/
    /** Saves a core mesh instance.
     *
     * This function saves a core mesh instance to a file.
     *
     * @param file The name of the file to save the core mesh instance to.
     * @param coreMesh A pointer to the core mesh instance that should be saved.
     *****************************************************************************/
    
    public static void saveCoreMesh (OutputStream file, CalCoreMesh coreMesh) throws IOException
    {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream (file);
     
        out.write (CalLoader.MESH_FILE_MAGIC);
     
        out.writeInt (CalLoader.CURRENT_FILE_VERSION);
     
        // get the submesh vector
        Vector<CalCoreSubmesh> vectorCoreSubmesh = coreMesh.getVectorCoreSubmesh ();
     
        // write the number of submeshes
        out.writeInt (vectorCoreSubmesh.size ());
     
        // write all core submeshes
        for (CalCoreSubmesh submesh : vectorCoreSubmesh)
        {
            saveCoreSubmesh (out, submesh);
        }
    }
     
     
    /*****************************************************************************/
    /** Saves a core skeleton instance.
     *
     * This function saves a core skeleton instance to a file.
     *
     * @param file The name of the file to save the core skeleton instance
     *                    to.
     * @param coreSkeleton A pointer to the core skeleton instance that should be
     *                      saved.
     *****************************************************************************/
    
    public static void saveCoreSkeleton (OutputStream file, CalCoreSkeleton coreSkeleton) throws IOException
    {
        LittleEndianDataOutputStream out = new LittleEndianDataOutputStream (file);
     
        out.write (CalLoader.SKELETON_FILE_MAGIC);
     
        out.writeInt (CalLoader.CURRENT_FILE_VERSION);
     
        // write the number of bones
        out.writeInt (coreSkeleton.getCoreBones ().size ());
     
        // write all core bones
        for (CalCoreBone bone : coreSkeleton.getCoreBones ())
        {
            // write the core bone
            saveCoreBones (out, bone);
        }
    }
     
     
    /*****************************************************************************/
    /** Saves a core submesh instance.
     *
     * This function saves a core submesh instance to a file stream.
     *
     * @param out The file stream to save the core submesh instance to.
     * @param coreSubmesh A pointer to the core submesh instance that should be
     *                     saved.
     *****************************************************************************/
    
    protected static void saveCoreSubmesh (DataOutput out, CalCoreSubmesh coreSubmesh) throws IOException
    {
        // write the core material thread id
        out.writeInt (coreSubmesh.getCoreMaterialThreadId ());
     
        // get the vertex, face, physical property and spring vector
        CalCoreSubmesh.VertexInfo [] vectorVertex = coreSubmesh.getVectorVertexInfo ();
        Vector3fBuffer vertexPositions = coreSubmesh.getVertexPositions ();
        Vector3fBuffer vertexNormals = coreSubmesh.getVertexNormals ();
        CalCoreSubmesh.Face [] vectorFace = coreSubmesh.getVectorFace ();
        float [] vectorPhysicalProperty = coreSubmesh.getVectorPhysicalProperty ();
        CalCoreSubmesh.Spring [] vectorSpring = coreSubmesh.getVectorSpring ();
     
        // write the number of vertices, faces, level-of-details and springs
        out.writeInt (vectorVertex.length);
        out.writeInt (vectorFace.length);
        out.writeInt (coreSubmesh.getLodCount ());
        out.writeInt (coreSubmesh.getSpringCount ());
     
        // get the texture coordinate vector vector
        TexCoord2fBuffer [] textureCoordinates = coreSubmesh.getTextureCoordinates ();
     
        // write the number of texture coordinates per vertex
        out.writeInt (textureCoordinates.length);
     
        // write all vertices
     
        for (int vertexId = 0; vertexId < vectorVertex.length; vertexId++)
        {
            CalCoreSubmesh.VertexInfo vertexInfo     = vectorVertex[vertexId];
            Vector3f                  vertexPosition = vertexPositions.get (vertexId);
            Vector3f                  vertexNormal   = vertexNormals.get (vertexId);
     
            // write the vertex data
            out.writeFloat (vertexPosition.getX());
            out.writeFloat (vertexPosition.getY());
            out.writeFloat (vertexPosition.getZ());
            out.writeFloat (vertexNormal.getX());
            out.writeFloat (vertexNormal.getY());
            out.writeFloat (vertexNormal.getZ());
            out.writeInt (vertexInfo.collapseId);
            out.writeInt (vertexInfo.faceCollapseCount);
     
            // write all texture coordinates of this vertex
            for (int textureCoordinateId = 0; textureCoordinateId < textureCoordinates.length; textureCoordinateId++)
            {
                Vector2f texCoord = textureCoordinates [textureCoordinateId].get (vertexId);
     
                // write the texture coord data
                out.writeFloat (texCoord.getX());
                out.writeFloat (texCoord.getY());
            }
     
            // write the number of influences
            out.writeInt (vertexInfo.influenceBoneIds.length);
     
            // write all influences of this vertex
            for (int influenceId = 0; influenceId < vertexInfo.influenceBoneIds.length; influenceId++)
            {
                // write the influence data
                out.writeInt (vertexInfo.influenceBoneIds [influenceId]);
                out.writeFloat (vertexInfo.influenceWeights [influenceId]);
            }
     
            // save the physical property of the vertex if there are springs in the core submesh
            if(coreSubmesh.getSpringCount () > 0)
            {
                // write the physical property of this vertex if there are springs in the core submesh
                out.writeFloat (vectorPhysicalProperty[vertexId]);
            }
        }
     
        // write all springs
        for (int springId = 0; springId < coreSubmesh.getSpringCount (); springId++)
        {
            CalCoreSubmesh.Spring spring = vectorSpring[springId];
     
            // write the spring data
            out.writeInt (spring.vertexId0);
            out.writeInt (spring.vertexId1);
            out.writeFloat (spring.springCoefficient);
            out.writeFloat (spring.idleLength);
        }
     
        // write all faces
        for (int faceId = 0; faceId < vectorFace.length; faceId++)
        {
            CalCoreSubmesh.Face face = vectorFace[faceId];
     
            // write the face data
            out.writeInt (face.vertexId[0]);
            out.writeInt (face.vertexId[1]);
            out.writeInt (face.vertexId[2]);
        }
    }
     
    
    /*****************************************************************************/
    /** Saves a core track instance.
     *
     * This function saves a core track instance to a file stream.
     *
     * @param file The file stream to save the core track instance to.
     * @param coreTrack A pointer to the core track instance that should be saved.
     *****************************************************************************/
    
    protected static void saveCoreTrack (DataOutput file, CalCoreTrack coreTrack) throws IOException
    {
        // write the bone id
        file.writeInt (coreTrack.getCoreBoneId ());
     
        // get core keyframe map
        Set<CalCoreKeyframe> mapCoreKeyframe = coreTrack.getCoreKeyFrames ();
     
        // write the number of keyframes
        file.writeInt (mapCoreKeyframe.size ());
     
        // save all core keyframes
        for (CalCoreKeyframe keyframe : mapCoreKeyframe)
        {
            saveCoreKeyframe (file, keyframe);
        }
    }
}

