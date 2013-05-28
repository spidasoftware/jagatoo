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
package org.jagatoo.loaders.models.collada.datastructs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jagatoo.loaders.models.collada.datastructs.controllers.Controller;
import org.jagatoo.loaders.models.collada.datastructs.controllers.SkeletalController;

public class ColladaProtoypeModel
{
	private AssetFolder colladaFile;
	private final HashMap<String, SkeletalController> skelControllers = new HashMap<String, SkeletalController>();
	
	public void initAnimation( String animationName, boolean loop )
	{
		for ( Iterator<SkeletalController> iterator = skelControllers.values().iterator(); iterator.hasNext(); )
		{
			iterator.next().play( animationName, loop );
		}
	}

	public void animate( long time )
	{
		for ( Iterator<SkeletalController> iterator = skelControllers.values().iterator(); iterator.hasNext(); )
		{
			SkeletalController curr = iterator.next();
			colladaFile.getLibraryGeometries().put( curr.getSourceMeshId(), curr.updateDestinationGeometry( time ) );
		}
	}

    public ColladaProtoypeModel( AssetFolder colladaFile )
    {
        this.colladaFile = colladaFile;

        for ( Iterator<Entry<String, Controller>> iterator = colladaFile.getLibraryControllers().getControllers().entrySet().iterator(); iterator.hasNext(); )
        {
            Entry<String, Controller> entry = iterator.next();
            if ( entry.getValue() instanceof SkeletalController )
            {
                skelControllers.put( entry.getKey(), (SkeletalController)entry.getValue() );
            }
        }
    }
}
