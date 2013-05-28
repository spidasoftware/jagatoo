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
package org.jagatoo.input.events;

import org.jagatoo.input.devices.Mouse;

/**
 * Stores the details associated with a mouse event.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseMovedEvent extends MouseEvent
{
    /**
     * The X coordinate of the mouse when in non-exclusive mode.
     */
    private int x;
    
    /**
     * The Y coordinate of the mouse when in non-exclusive mode.
     */
    private int y;
    
    
    /**
     * The Delta X value (change in position on the X axis) of the mouse when in exclusive mode.
     */
    private int dx;
    
    /**
     * The Delta X value (change in position on the X axis) of the mouse when in exclusive mode.
     */
    private int dy;
    
    /**
     * {@inheritDoc}
     */
    public final int getX()
    {
        return ( x );
    }
    
    /**
     * {@inheritDoc}
     */
    public final int getY()
    {
        return ( y );
    }
    
    /**
     * {@inheritDoc}
     */
    public final int getDX()
    {
        return ( dx );
    }
    
    /**
     * {@inheritDoc}
     */
    public final int getDY()
    {
        return ( dy );
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( "MouseMovedEvent( pos = (" + getX() + ", " + getY() + "), " +
                "delta = (" + getDX() + ", " + getDY() + "), " +
                "buttonsState = " + getMouse().getButtonsState() + ", " +
                "when = " + getWhen() +
                " )"
              );
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected void set( Mouse mouse, int x, int y, int dx, int dy, long when, long lastWhen )
    {
        super.set( mouse, SubType.MOVED, null, when, lastWhen );
        
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Sets the fields of this MouseEvent to match the given MouseEvent.
     */
    protected MouseMovedEvent( Mouse mouse, int x, int y, int dx, int dy, long when, long lastWhen )
    {
        super( mouse, SubType.MOVED, null, when, lastWhen );
        
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Creates a MouseEvent with default values.
     */
    protected MouseMovedEvent()
    {
        super( SubType.MOVED );
    }
}
