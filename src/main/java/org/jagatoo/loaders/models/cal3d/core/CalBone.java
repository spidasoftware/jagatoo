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

import java.util.ArrayList;

import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Vector3f;


// ***//
// Class declaration //
// ***//

/******
 * The bone class.
 *****/
public class CalBone
{
    protected CalCoreBone   coreBone;
    
    protected CalSkeleton   skeleton;
    
    protected float         accumulatedWeight;
    
    protected float         accumulatedWeightAbsolute;
    
    protected Vector3f      translation          = new Vector3f();
    
    protected Quaternion4f  rotation             = new Quaternion4f();
    
    protected Vector3f      translationAbsolute  = new Vector3f();
    
    protected Quaternion4f  rotationAbsolute     = new Quaternion4f();
    
    protected Vector3f      translationBoneSpace = new Vector3f();
    
    protected Quaternion4f  rotationBoneSpace    = new Quaternion4f();
    
    protected Matrix3f      transformMatrix      = new Matrix3f();
    
    protected Matrix3f      tmpMatrix            = new Matrix3f();
    
    // ***//
    
    /**
     * Constructs a bone instance from a core bone.
     * 
     * This function is the default constructor of the bone instance.
     */
    
    public CalBone( CalCoreBone coreBone )
    {
        if( coreBone == null ) throw new IllegalArgumentException();
        
        this.coreBone = coreBone;
        
        clearState();
    }
    
    /**
     * Resets the bone to its core state
     * 
     * This function changes the state of the bone to its default non-animated
     * position and orientation. Child bones are unaffected and may be animated
     * independently.
     */
    
    public void setCoreState( )
    {
        // set the bone to the initial skeleton state
        translation = coreBone.getTranslation();
        rotation = coreBone.getRotation();
        
        // set the appropriate weights
        accumulatedWeightAbsolute = 1.0f;
        accumulatedWeight = 1.0f;
        
        calculateState();
    }
    
    /**
     * Resets the bone and children to core states
     * 
     * This function changes the state of the bone to its default non-animated
     * position and orientation. All child bones are also set in this manner.
     */
    public void setCoreStateRecursive( )
    {
        // set the bone to the initial skeleton state
        translation = coreBone.getTranslation();
        rotation = coreBone.getRotation();
        
        // set the appropriate weights
        accumulatedWeightAbsolute = 1.0f;
        accumulatedWeight = 1.0f;
        
        for( int childId : coreBone.listChildId )
        {
            skeleton.getBone( childId ).setCoreStateRecursive();
        }
        
        calculateState();
    }
    
    /**
     * Interpolates the current state to another state.
     * 
     * This function interpolates the current state (relative translation and
     * rotation) of the bone instance to another state of a given weight.
     * 
     * @param weight
     *            The blending weight.
     * @param translation
     *            The relative translation to be interpolated to.
     * @param rotation
     *            The relative rotation to be interpolated to.
     */
    
    public void blendState( float weight, Vector3f translation, Quaternion4f rotation )
    {
        if( accumulatedWeightAbsolute == 0.0f )
        {
            // it is the first state, so we can just copy it into the bone state
            translationAbsolute.set( translation );
            rotationAbsolute.set( rotation );
            
            accumulatedWeightAbsolute = weight;
        }
        else
        {
            // it is not the first state, so blend all attributes
            float factor = weight / ( accumulatedWeightAbsolute + weight );
            
            translationAbsolute.interpolate( translation, factor );
            rotationAbsolute.interpolate( rotation, factor );
            // QuatInterpolator.interpolate(rotationAbsolute,rotation, factor);
            accumulatedWeightAbsolute += weight;
        }
    }
    
    /**
     * Applies a translation and rotation to the current state, bypassing the
     * weighted accumulators
     * 
     * This function interpolates the current state (relative translation and
     * rotation) of the bone instance to another state of a given weight.
     * 
     * @param weight
     *            The blending weight.
     * @param translation
     *            The relative translation to be interpolated to.
     * @param rotation
     *            The relative rotation to be interpolated to.
     */
    
    public void applyState( float weight, Vector3f translation, Quaternion4f rotation )
    {
        this.translation.add( translation );
        this.rotation.mul( rotation, this.rotation );
    }
    
    /**
     * Locks the current state.
     * 
     * This function locks the current state (absolute translation and rotation)
     * of the bone instance and all its children.
     */
    
    public void lockState( )
    {
        // clamp accumulated weight
        if( accumulatedWeightAbsolute > 1.0f - accumulatedWeight )
        {
            accumulatedWeightAbsolute = 1.0f - accumulatedWeight;
        }
        
        if( accumulatedWeightAbsolute > 0.0f )
        {
            if( accumulatedWeight == 0.0f )
            {
                // it is the first state, so we can just copy it into the bone
                // state
                translation.set( translationAbsolute );
                rotation.set( rotationAbsolute );
                
                accumulatedWeight = accumulatedWeightAbsolute;
            }
            else
            {
                // it is not the first state, so blend all attributes
                float factor = accumulatedWeightAbsolute / ( accumulatedWeight + accumulatedWeightAbsolute );
                
                translation.interpolate( translationAbsolute, factor );
                rotation.interpolate( rotationAbsolute, factor );
                // QuatInterpolator.interpolate(rotation,rotationAbsolute,factor);
                accumulatedWeight += accumulatedWeightAbsolute;
            }
            
            accumulatedWeightAbsolute = 0.0f;
        }
    }
    
    /**
     * Calculates the current state.
     * 
     * This function calculates the current state (absolute translation and
     * rotation, as well as the bone space transformation) of the bone instance
     * and all its children.
     */
    
    public void calculateState( )
    {
        // check if the bone was not touched by any active animation
        if( accumulatedWeight == 0.0f )
        {
            // set the bone to the initial skeleton state
            translation.set( coreBone.getTranslation() );
            rotation.set( coreBone.getRotation() );
        }
        
        // get parent bone id
        int parentId = coreBone.getParentId();
        
        if( parentId == - 1 )
        {
            // no parent, this means absolute state == relative state
            translationAbsolute.set( translation );
            rotationAbsolute.set( rotation );
        }
        else
        {
            // get the parent bone
            CalBone parent = skeleton.getBone( parentId );
            
            // transform relative state with the absolute state of the parent
            translationAbsolute.set( translation );
            CalCoreBone.transform( translationAbsolute, parent.getRotationAbsolute() );
            translationAbsolute.add( parent.getTranslationAbsolute() );
            
            rotationAbsolute.set( rotation );
            rotationAbsolute.mul( parent.getRotationAbsolute() );
        }
        
        // calculate the bone space transformation
        translationBoneSpace.set( coreBone.getTranslationBoneSpace() );
        CalCoreBone.transform( translationBoneSpace, rotationAbsolute );
        translationBoneSpace.add( translationAbsolute );
        
        rotationBoneSpace.set( coreBone.getRotationBoneSpace() );
        rotationBoneSpace.mul( rotationAbsolute );
        
        // Generate the vertex transform. If I ever add support for bone-scaling
        // to Cal3D, this step will become significantly more complex.
        transformMatrix.set( rotationBoneSpace );
        
        // calculate all child bones
        ArrayList<Integer> list = coreBone.getListChildId();
        for( int i = 0; i < list.size(); i ++ )
        {
            int childId = list.get( i );
            skeleton.getBone( childId ).calculateState();
        }
    }
    
    /**
     * Clears the current state.
     * 
     * This function clears the current state (absolute translation and
     * rotation) of the bone instance and all its children.
     */
    
    public void clearState( )
    {
        accumulatedWeight = 0.0f;
        accumulatedWeightAbsolute = 0.0f;
    }
    
    /**
     * Provides access to the core bone.
     * 
     * This function returns the core bone on which this bone instance is based
     * on.
     * 
     * @return One of the following values: \li a pointer to the core bone \li
     *         \b 0 if an error happend
     */
    
    public CalCoreBone getCoreBone( )
    {
        return coreBone;
    }
    
    /**
     * Returns the current rotation.
     * 
     * This function returns the current relative rotation of the bone instance.
     * 
     * @return The relative rotation to the parent as quaternion.
     */
    
    public Quaternion4f getRotation( )
    {
        return rotation;
    }
    
    /**
     * Sets the current rotation.
     * 
     * This function sets the current relative rotation of the bone instance.
     * Caveat: For this change to appear, calculateState() must be called
     * afterwards.
     */
    
    public void setRotation( Quaternion4f rotation )
    {
        this.rotation.set( rotation );
        accumulatedWeightAbsolute = 1;
        accumulatedWeight = 1;
    }
    
    /**
     * Returns the current absolute rotation.
     * 
     * This function returns the current absolute rotation of the bone instance.
     * 
     * @return The absolute rotation to the parent as quaternion.
     */
    
    public Quaternion4f getRotationAbsolute( )
    {
        return rotationAbsolute;
    }
    
    /**
     * Returns the current bone space rotation.
     * 
     * This function returns the current rotation to bring a point into the bone
     * instance space.
     * 
     * @return The rotation to bring a point into bone space.
     */
    
    public Quaternion4f getRotationBoneSpace( )
    {
        return rotationBoneSpace;
    }
    
    /**
     * Returns the current translation.
     * 
     * This function returns the current relative translation of the bone
     * instance.
     * 
     * @return The relative translation to the parent as quaternion.
     */
    
    public Vector3f getTranslation( )
    {
        return translation;
    }
    
    /**
     * Sets the current translation.
     * 
     * This function sets the current relative translation of the bone instance.
     * Caveat: For this change to appear, calculateState() must be called
     * afterwards.
     */
    
    public void setTranslation( Vector3f translation )
    {
        this.translation.set( translation );
        accumulatedWeightAbsolute = 1;
        accumulatedWeight = 1;
    }
    
    /**
     * Returns the current absolute translation.
     * 
     * This function returns the current absolute translation of the bone
     * instance.
     * 
     * @return The absolute translation to the parent as quaternion.
     */
    
    public Vector3f getTranslationAbsolute( )
    {
        return translationAbsolute;
    }
    
    /**
     * Returns the current bone space translation.
     * 
     * This function returns the current translation to bring a point into the
     * bone instance space.
     * 
     * @return The translation to bring a point into bone space.
     */
    
    public Vector3f getTranslationBoneSpace( )
    {
        return translationBoneSpace;
    }
    
    /**
     * Returns the current bone space translation.
     * 
     * This function returns the current translation to bring a point into the
     * bone instance space.
     * 
     * @return The translation to bring a point into bone space.
     */
    
    public Matrix3f getTransformMatrix( )
    {
        return transformMatrix;
    }
    
    /**
     * Sets the skeleton.
     * 
     * This function sets the skeleton to which the bone instance is attached
     * to.
     * 
     * @param skeleton
     *            The skeleton to which the bone instance should be attached to.
     */
    
    public void setSkeleton( CalSkeleton skeleton )
    {
        this.skeleton = skeleton;
    }
    
    /**
     * The string representation is the name of the core bone
     */
    @Override
    public String toString( )
    {
        return coreBone.name;
    }
}
