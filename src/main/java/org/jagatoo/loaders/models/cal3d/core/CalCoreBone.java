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


// ****************************************************************************//
// Class declaration //
// ****************************************************************************//

/** ************************************************************************** */
/*******************************************************************************
 * The core bone class.
 ******************************************************************************/
public class CalCoreBone
{
    private static Quaternion4f  qq                   = new Quaternion4f();
    
    private static Quaternion4f  qc                   = new Quaternion4f();
    
    protected String             name;
    
    protected CalCoreSkeleton    coreSkeleton;
    
    int                          id;
    
    protected int                parentId;
    
    protected ArrayList<Integer> listChildId          = new ArrayList<Integer>();
    
    protected Vector3f           translation          = new Vector3f();
    
    protected Quaternion4f       rotation             = new Quaternion4f();
    
    protected Vector3f           translationAbsolute  = new Vector3f();
    
    protected Quaternion4f       rotationAbsolute     = new Quaternion4f();
    
    protected Vector3f           translationBoneSpace = new Vector3f();
    
    protected Quaternion4f       rotationBoneSpace    = new Quaternion4f();
    
    protected Matrix3f           stateTransform       = new Matrix3f();
    
    protected Object             userData;
    
    // ****************************************************************************//
    
    /** ************************************************************************** */
    /***************************************************************************
     * Constructs the core bone instance.
     * 
     * This function is the default constructor of the core bone instance.
     **************************************************************************/
    
    public CalCoreBone( String name )
    {
        this.name = name;
        coreSkeleton = null;
        parentId = - 1;
        userData = null;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Adds a child ID.
     * 
     * This function adds a core bone ID to the child ID list of the core bone
     * instance.
     * 
     * @param childId
     *            The ID of the core bone ID that shoud be added to the child ID
     *            list.
     **************************************************************************/
    
    public void addChildId( int childId )
    {
        listChildId.add( new Integer( childId ) );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Calculates the current state.
     * 
     * This function calculates the current state (absolute translation and
     * rotation) of the core bone instance and all its children.
     **************************************************************************/
    
    public void calculateState( )
    {
        if( parentId == - 1 )
        {
            // no parent, this means absolute state == relative state
            translationAbsolute.set( translation );
            rotationAbsolute.set( rotation );
        }
        else
        {
            // get the parent bone
            CalCoreBone parent = coreSkeleton.getCoreBone( parentId );
            
            // transform relative state with the absolute state of the parent
            translationAbsolute.set( translation );
            transform( translationAbsolute, parent.getRotationAbsolute() );
            translationAbsolute.add( parent.getTranslationAbsolute() );
            
            rotationAbsolute.set( rotation );
            rotationAbsolute.mul( parent.getRotationAbsolute() );
        }
        
        // calculate all child bones
        for( int childId : listChildId )
        {
            coreSkeleton.getCoreBone( childId ).calculateState();
        }
    }
    
    public static void transform( Vector3f v, Quaternion4f q )
    {
        qq.set( - q.getA(), - q.getB(), - q.getC(), q.getD() );
        mul( qq, v );
        qq.mul( q );
        v.setX( qq.getA() );
        v.setY( qq.getB() );
        v.setZ( qq.getC() );
    }
    
    public static void transformInv( Vector3f v, Quaternion4f q )
    {
        qq.set( q );
        qc.set( - q.getA(), - q.getB(), - q.getC(), q.getD() );
        mul( qq, v );
        qq.mul( qc );
        v.setX( qq.getA() );
        v.setY( qq.getB() );
        v.setZ( qq.getC() );
    }
    
    public static void mul( Quaternion4f q, Vector3f v )
    {
        float qx, qy, qz, qw;
        qx = q.getA();
        qy = q.getB();
        qz = q.getC();
        qw = q.getD();
        
        q.setA( qw * v.getX() + qy * v.getZ() - qz * v.getY() );
        q.setB( qw * v.getY() - qx * v.getZ() + qz * v.getX() );
        q.setC( qw * v.getZ() + qx * v.getY() - qy * v.getX() );
        q.setD( -qx * v.getX() - qy * v.getY() - qz * v.getZ() );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the child ID list.
     * 
     * This function returns the list that contains all child IDs of the core
     * bone instance.
     * 
     * @return A reference to the child ID list.
     **************************************************************************/
    
    public ArrayList<Integer> getListChildId( )
    {
        return listChildId;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the name.
     * 
     * This function returns the name of the core bone instance.
     * 
     * @return The name as string.
     **************************************************************************/
    
    public String getName( )
    {
        return name;
    }
    
    public void setName( String name )
    {
        this.name = name;
    }
    
    /**
     * @return the name of the bone.
     */
    
    @Override
    public String toString( )
    {
        return name;
    }
    
    /**
     * Get the id for this core bone - needed to find the corresponding bone
     * instance.
     */
    
    public int getId( )
    {
        return id;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the parent ID.
     * 
     * This function returns the parent ID of the core bone instance.
     * 
     * @return One of the following values: \li the \b ID of the parent \li \b
     *         -1 if the core bone instance is a root core bone
     **************************************************************************/
    
    public int getParentId( )
    {
        return parentId;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the rotation.
     * 
     * This function returns the relative rotation of the core bone instance.
     * 
     * @return The relative rotation to the parent as quaternion.
     **************************************************************************/
    
    public Quaternion4f getRotation( )
    {
        return rotation;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the absolute rotation.
     * 
     * This function returns the absolute rotation of the core bone instance.
     * 
     * @return The absolute rotation to the parent as quaternion.
     **************************************************************************/
    
    public Quaternion4f getRotationAbsolute( )
    {
        return rotationAbsolute;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the bone space rotation.
     * 
     * This function returns the rotation to bring a point into the core bone
     * instance space. I.e., the transform that takes the mesh in its reference
     * pose to the bone space from which the physique transform can be applied.
     * 
     * @return The rotation to bring a point into bone space.
     **************************************************************************/
    
    public Quaternion4f getRotationBoneSpace( )
    {
        return rotationBoneSpace;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the translation.
     * 
     * This function returns the relative translation of the core bone instance.
     * 
     * @return The relative translation to the parent as quaternion.
     **************************************************************************/
    
    public Vector3f getTranslation( )
    {
        return translation;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the absolute translation.
     * 
     * This function returns the absolute translation of the core bone instance.
     * 
     * @return The absolute translation to the parent as quaternion.
     **************************************************************************/
    
    public Vector3f getTranslationAbsolute( )
    {
        return translationAbsolute;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Returns the bone space translation.
     * 
     * This function returns the translation to bring a point into the core bone
     * instance space. I.e., the transform that takes the mesh in its reference
     * pose to the bone space from which the physique transform can be applied.
     * 
     * @return The translation to bring a point into bone space.
     **************************************************************************/
    
    public Vector3f getTranslationBoneSpace( )
    {
        return translationBoneSpace;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Provides access to the user data.
     * 
     * This function returns the user data stored in the core bone instance.
     * 
     * @return The user data stored in the core bone instance.
     **************************************************************************/
    
    public Object getUserData( )
    {
        return userData;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the core skeleton.
     * 
     * This function sets the core skeleton to which the core bone instance is
     * attached to.
     * 
     * @param coreSkeleton
     *            The core skeleton to which the core bone instance should be
     *            attached to.
     **************************************************************************/
    
    public void setCoreSkeleton( CalCoreSkeleton coreSkeleton )
    {
        this.coreSkeleton = coreSkeleton;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the parent ID.
     * 
     * This function sets the parent ID of the core bone instance.
     * 
     * @param parentId
     *            The ID of the parent that should be set.
     **************************************************************************/
    
    public void setParentId( int parentId )
    {
        this.parentId = parentId;
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the rotation.
     * 
     * This function sets the relative rotation of the core bone instance.
     * 
     * @param rotation
     *            The relative rotation to the parent as quaternion.
     **************************************************************************/
    
    public void setRotation( Quaternion4f rotation )
    {
        this.rotation.set( rotation );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the bone space rotation.
     * 
     * This function sets the rotation that brings a point into the core bone
     * instance space.
     * 
     * @param rotation
     *            The rotation that brings a point into bone space.
     **************************************************************************/
    
    public void setRotationBoneSpace( Quaternion4f rotation )
    {
        rotationBoneSpace.set( rotation );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the translation.
     * 
     * This function sets the relative translation of the core bone instance.
     * 
     * @param translation
     *            The relative translation to the parent as vector.
     **************************************************************************/
    
    public void setTranslation( Vector3f translation )
    {
        this.translation.set( translation );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Sets the bone space translation.
     * 
     * This function sets the translation that brings a point into the core bone
     * instance space.
     * 
     * @param translation
     *            The translation that brings a point into bone space.
     **************************************************************************/
    
    public void setTranslationBoneSpace( Vector3f translation )
    {
        translationBoneSpace.set( translation );
    }
    
    /** ************************************************************************** */
    /***************************************************************************
     * Stores user data.
     * 
     * This function stores user data in the core bone instance.
     * 
     * @param userData
     *            The user data that should be stored.
     **************************************************************************/
    
    public void setUserData( Object userData )
    {
        this.userData = userData;
    }
}
