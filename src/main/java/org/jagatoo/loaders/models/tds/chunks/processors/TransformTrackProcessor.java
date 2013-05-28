/**
 * Copyright (c) 2003-2008, Xith3D Project Group all rights reserved.
 * 
 * Portions based on the Java3D interface, Copyright by Sun Microsystems.
 * Many thanks to the developers of Java3D and Sun Microsystems for their
 * innovation and design.
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
package org.jagatoo.loaders.models.tds.chunks.processors;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.util.errorhandling.IncorrectFormatException;
import org.jagatoo.loaders.models._util.AnimationFactory;
import org.jagatoo.loaders.models._util.NodeFactory;
import org.jagatoo.loaders.models._util.SpecialItemsHandler;
import org.jagatoo.loaders.models._util.SpecialItemsHandler.SpecialItemType;
import org.jagatoo.loaders.models.tds.internal.KeyFrameFactory;
import org.jagatoo.loaders.models.tds.internal.ModelContext;
import org.jagatoo.logging.JAGTLog;
import org.openmali.spatial.bounds.BoundsType;
import org.openmali.vecmath2.Matrix4f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class TransformTrackProcessor extends ChunkProcessor
{
    private static final boolean IGNORE_HIERARCHY = false;
    
    public static void checkAndApplyTransform( ModelContext context, boolean force, NodeFactory nodeFactory, AnimationFactory animFactory, SpecialItemsHandler siHandler, boolean convertZup2Yup ) throws IncorrectFormatException
    {
        if ( ( context.orientation == null ) || ( context.translation == null ) || ( context.scale == null ) )
        {
            if ( force )
            {
                if ( ( context.orientation == null ) && ( context.translation == null ) && ( context.scale == null ) )
                    return;
            }
            else
            {
                return;
            }
        }
        
        
        /*
        if ( context.nodeName.equals( "$$$DUMMY" ) )
        {
            // Don't know, what to do with dummy objects.
            return;
        }
        */
        
        NamedObject shape = context.objectTable.get( context.nodeName );
        
        if ( ( shape == null ) && !context.nodeName.equals( "$$$DUMMY" ) )
        {
            IncorrectFormatException e = new IncorrectFormatException( "Can't locate referenced object." );
            JAGTLog.print( e );
            throw e;
        }
        
        boolean isRootNode = ( ( context.father == -1 ) || IGNORE_HIERARCHY );
        
        NamedObject transformGroup = nodeFactory.createTransformGroup( context.nodeName + "-TG", BoundsType.SPHERE );
        siHandler.addSpecialItem( SpecialItemType.NESTED_TRANSFORM, transformGroup.getName(), transformGroup );
        
        Matrix4f masterTransform = ( isRootNode && convertZup2Yup ) ? Matrix4f.Z_UP_TO_Y_UP : null;
        Object[] keyFrames = KeyFrameFactory.createKeyFrames( masterTransform, context.framesCount, context.translation, context.orientation, context.scale, animFactory );
        Object animController = animFactory.createMeshTransformKeyFrameController( keyFrames, transformGroup );
        
        if ( shape != null )
        {
            nodeFactory.translateShapeOrGeometry( shape, -context.pivot.getX(), -context.pivot.getY(), -context.pivot.getZ() );
            nodeFactory.addNodeToGroup( shape, transformGroup );
        }
        
        context.nodeIDMap.put( context.nodeID, transformGroup );
        
        if ( isRootNode )
        {
            context.rootNodes.add( transformGroup );
        }
        else
        {
            context.nestedNodes.add( transformGroup );
            NamedObject parentTG = context.nodeIDMap.get( context.father );
            nodeFactory.addNodeToGroup( transformGroup, parentTG );
        }
        
        if ( shape != null )
        {
            context.unanimatedNodes.remove( shape );
        }
        
        context.animControllers.add( animController );
        
        context.instanceName = null;
        context.translation = null;
        context.scale = null;
        context.orientation = null;
        
        context.animationFound = true;
    }
}
