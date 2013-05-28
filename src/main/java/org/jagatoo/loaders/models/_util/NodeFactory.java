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
package org.jagatoo.loaders.models._util;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.models.bsp.BSPVisibilityUpdater;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.openmali.spatial.PlaneIndicator;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.vecmath2.AxisAngle3f;
import org.openmali.vecmath2.Matrix3f;
import org.openmali.vecmath2.Matrix4f;
import org.openmali.vecmath2.Quaternion4f;
import org.openmali.vecmath2.Tuple3f;
import org.openmali.vecmath2.Vector3f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface NodeFactory
{
    public NamedObject createDummyNode();
    
    public NamedObject createSimpleGroup( String name, BoundsType boundsType );
    
    public NamedObject createQuadTreeGroup( String name, float centerX, float centerY, float centerZ, PlaneIndicator plane, float width, float depth, float height );
    
    public NamedObject createOcTreeGroup( String name, float centerX, float centerY, float centerZ, float sizeX, float sizeY, float sizeZ );
    
    public NamedObject createBSPTreeGroup( String name, BSPVisibilityUpdater visUpdater, BoundsType boundsType );
    
    public void setBSPGroupVisibilityUpdater( NamedObject bspGroup, BSPVisibilityUpdater visUpdater );
    
    public NamedObject createTransformGroup( String name, BoundsType boundsType );
    
    public NamedObject createTransformGroup( String name, Matrix4f transform, BoundsType boundsType );
    
    public void setTransformGroupTransform( NamedObject tg, Matrix4f transform );
    
    public void setTransformGroupRotation( NamedObject tg, Matrix3f rotation );
    
    public void setTransformGroupRotation( NamedObject tg, float quatA, float quatB, float quatC, float quatD );
    
    public void setTransformGroupRotation( NamedObject tg, float rx, float ry, float rz );
    
    public void setTransformGroupScale( NamedObject tg, float sx, float sy, float sz );
    
    public void setTransformGroupTranslation( NamedObject tg, float tx, float ty, float tz );
    
    
    
    public NamedObject createShape( String name, NamedObject geometry, NamedObject appearance, BoundsType boundsType );
    
    
    
    public void applyGeometryToShape( NamedObject geometry, NamedObject shape );
    
    public void applyAppearanceToShape( NamedObject appearance, NamedObject shape );
    
    
    
    public NamedObject getGeometryFromShape( NamedObject shape );
    
    public NamedObject getAppearanceFromShape( NamedObject shape );
    
    
    
    public NamedObject createAmbientLightNode( String name );
    
    public void setAmbientLightColor( NamedObject ambientLight, float r, float g, float b );
    
    
    
    public NamedObject createPointLightNode( String name );
    
    public void setPointLightLocation( NamedObject pointLight, float x, float y, float z );
    
    public void setPointLightColor( NamedObject pointLight, float r, float g, float b );
    
    public void setPointLightAttenuation( NamedObject pointLight, float attConstant, float attLinear, float attQuadratic );
    
    
    
    public NamedObject createSpotLightNode( String name );
    
    public void setSpotLightLocation( NamedObject spotLight, float x, float y, float z );
    
    public void setSpotLightColor( NamedObject spotLight, float r, float g, float b );
    
    public void setSpotLightAngle( NamedObject spotLight, float angle );
    
    public void setSpotLightAttenuation( NamedObject spotLight, float attConstant, float attLinear, float attQuadratic );
    
    
    
    public void setLightRadius( NamedObject light, float radius );
    
    public void setLightEnbaled( NamedObject light, boolean enabled );
    
    
    
    public void addNodeToGroup( NamedObject node, NamedObject group );
    
    
    
    public Object createSkyBox( AbstractTexture texFront, AbstractTexture texRight, AbstractTexture texBack, AbstractTexture texLeft, AbstractTexture texTop, AbstractTexture texBottom );
    
    
    
    public NamedObject transformShapeOrGeometry( NamedObject shapeOrGeom, Matrix4f transform );
    
    public NamedObject transformShapeOrGeometry( NamedObject shapeOrGeom, Vector3f translation, Matrix3f rotation, Tuple3f scale );
    
    public NamedObject transformShapeOrGeometry( NamedObject shapeOrGeom, Vector3f translation, Quaternion4f rotation, Tuple3f scale );
    
    public NamedObject transformShapeOrGeometry( NamedObject shapeOrGeom, Vector3f translation, AxisAngle3f rotation, Tuple3f scale );
    
    public NamedObject translateShapeOrGeometry( NamedObject shapeOrGeom, Vector3f translation );
    
    public NamedObject translateShapeOrGeometry( NamedObject shapeOrGeom, float translationX, float translationY, float translationZ );
    
    public NamedObject rotateShapeOrGeometry( NamedObject shapeOrGeom, Matrix3f rotation );
    
    public NamedObject scaleShapeOrGeometry( NamedObject shapeOrGeom, Tuple3f scale );
}
