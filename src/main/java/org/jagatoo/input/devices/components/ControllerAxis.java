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
package org.jagatoo.input.devices.components;

import org.jagatoo.input.devices.Controller;

/**
 * This is a simple abstraction of a {@link Controller}'s axis.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class ControllerAxis extends AnalogDeviceComponent
{
    private final Controller controller;
    private final int index;
    
    private float minValue = -1f;
    private float maxValue = +1f;
    private float invMinMaxSpan = 2f / ( maxValue - minValue );
    
    private float normValue = 0f;
    
    private float deadZone;
    private final float povX;
    private final float povY;
    
    /**
     * @return the {@link Controller}, this axis belongs to.
     */
    public final Controller getController()
    {
        return ( controller );
    }
    
    /**
     * @return the Axis' index.
     */
    public final int getIndex()
    {
        return ( index );
    }
    
    private final void updateNormValue()
    {
        this.normValue = ( ( getFloatValue() - getMinValue() ) * invMinMaxSpan ) - 1f;
    }
    
    /**
     * Sets the minimum value, the normalized value can take.
     * 
     * @param minValue
     */
    public void setMinValue( float minValue )
    {
        this.minValue = minValue;
        this.invMinMaxSpan = 2f / ( this.maxValue - this.minValue );
        
        updateNormValue();
    }
    
    /**
     * @return the minimum value, the normalized value can take.
     */
    public final float getMinValue()
    {
        return ( minValue );
    }
    
    /**
     * Sets the maximum value, the normalized value can take.
     * 
     * @param minValue
     */
    public void setMaxValue( float maxValue )
    {
        this.maxValue = maxValue;
        this.invMinMaxSpan = 2f / ( this.maxValue - this.minValue );
        
        updateNormValue();
    }
    
    /**
     * @return the maximum value, the normalized value can take.
     */
    public final float getMaxValue()
    {
        return ( maxValue );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValue( int intValue, float floatValue )
    {
        super.setValue( intValue, floatValue );
        
        updateNormValue();
    }
    
    /**
     * @return the current value normalized on the interval [-1, +1].
     */
    public final float getNormalizedValue()
    {
        return ( normValue );
    }
    
    protected abstract void setDeadZoneImpl( float zone );
    
    public final void setDeadZone( float zone )
    {
        this.deadZone = zone;
        
        setDeadZoneImpl( zone );
    }
    
    public final float getDeadZone()
    {
        return ( deadZone );
    }
    
    public final float getPovX()
    {
        return ( povX );
    }
    
    public final float getPovY()
    {
        return ( povY );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( this.getClass().getSimpleName() + " { name = \"" + getName() + "\", index = " + getIndex() + ", value = " + getFloatValue() + " }" );
    }
    
    public ControllerAxis( Controller controller, int index, String name, float deadZone, float povX, float povY )
    {
        super( Type.CONTROLLER_AXIS, name );
        
        this.controller = controller;
        this.index = index;
        this.deadZone = deadZone;
        this.povX = povX;
        this.povY = povY;
    }
}
