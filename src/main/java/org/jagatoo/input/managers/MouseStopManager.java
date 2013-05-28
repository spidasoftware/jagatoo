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
package org.jagatoo.input.managers;

import java.util.ArrayList;

import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.events.MouseEventPool;
import org.jagatoo.input.events.MouseStoppedEvent;
import org.jagatoo.input.listeners.MouseStopListener;

/**
 * Looks for the mouse beeing stopped for a specified amount of milliseconds.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseStopManager
{
    private static final long LAST_MOVED_RESET_TIME = Long.MAX_VALUE - 100000000000L;
    
    private final ArrayList< MouseStopListener > listeners = new ArrayList< MouseStopListener >();
    private long[] stopDelays = null;
    private long[] lastMovedTimes = null;
    private int numListeners = 0;
    
    private long lastWhen_stopped = -1L;
    
    /**
     * Adds a {@link MouseStopListener} to this Mouse to be notified
     * when the mouse has stopped being moved (for a while).
     * 
     * @param l
     */
    public void addMouseStopListener( MouseStopListener l )
    {
        if ( !listeners.contains( l ) )
        {
            listeners.add( l );
            
            if ( ( stopDelays == null ) || ( stopDelays.length == 0 ) )
            {
                stopDelays = new long[] { l.getMouseStopDelay() };
                lastMovedTimes = new long[] { LAST_MOVED_RESET_TIME };
            }
            else
            {
                long[] tmp = new long[ listeners.size() ];
                System.arraycopy( stopDelays, 0, tmp, 0, stopDelays.length );
                tmp[ tmp.length - 1 ] = l.getMouseStopDelay();
                stopDelays = tmp;
                
                tmp = new long[ listeners.size() ];
                System.arraycopy( lastMovedTimes, 0, tmp, 0, lastMovedTimes.length );
                tmp[ tmp.length - 1 ] = LAST_MOVED_RESET_TIME;
                lastMovedTimes = tmp;
            }
        }
        
        numListeners = listeners.size();
    }
    
    /**
     * Removes a {@link MouseStopListener} from the list of notified instances.
     * 
     * @param l
     */
    public void removeMouseStopListener( MouseStopListener l )
    {
        int index = listeners.indexOf( l );
        
        if ( index >= 0 )
        {
            listeners.remove( index );
            
            if ( listeners.size() == 0 )
            {
                stopDelays = null;
                lastMovedTimes = null;
            }
            else
            {
                long[] tmp = new long[ listeners.size() ];
                System.arraycopy( stopDelays, 0, tmp, 0, index );
                System.arraycopy( stopDelays, index + 1, tmp, index, stopDelays.length - index - 1 );
                tmp[ tmp.length - 1 ] = l.getMouseStopDelay();
                stopDelays = tmp;
                
                tmp = new long[ listeners.size() ];
                System.arraycopy( lastMovedTimes, 0, tmp, 0, index );
                System.arraycopy( lastMovedTimes, index + 1, tmp, index, lastMovedTimes.length - index - 1 );
                tmp[ tmp.length - 1 ] = LAST_MOVED_RESET_TIME;
                lastMovedTimes = tmp;
            }
        }
        
        numListeners = listeners.size();
    }
    
    /**
     * @return true, of at least one {@link MouseStopListener} is currently registered.
     */
    public final boolean hasMouseListener()
    {
        return ( numListeners > 0 );
    }
    
    /**
     * Prepares a {@link MouseStoppedEvent} for bein fired.<br>
     * The event is not fired from this method.<br>
     * 
     * @param mouse
     * @param when
     * 
     * @return the new event from the pool or <code>null</code>, if no events are currently accepted.
     */
    private final MouseStoppedEvent prepareMouseStoppedEvent( Mouse mouse, long when )
    {
        if ( !mouse.isEnabled() || ( numListeners == 0 ) )
            return ( null );
        
        MouseStoppedEvent e = MouseEventPool.allocStopped( mouse, mouse.getCurrentX(), mouse.getCurrentY(), when, lastWhen_stopped );
        
        lastWhen_stopped = when;
        
        return ( e );
    }
    
    /**
     * Fires a {@link MouseStoppedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param listener
     * @param consumeEvent
     */
    public final void fireOnMouseStopped( MouseStoppedEvent e, MouseStopListener listener, boolean consumeEvent )
    {
        listener.onMouseStopped( e, e.getX(), e.getY() );
        
        if ( consumeEvent )
            MouseEventPool.freeStopped( e );
    }
    
    /**
     * Fires a {@link MouseStoppedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param mouse
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseStopped( Mouse mouse, MouseStoppedEvent e, boolean consumeEvent )
    {
        if ( !mouse.isEnabled() || ( numListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freeStopped( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseStopped( e, e.getX(), e.getY() );
        }
        
        if ( consumeEvent )
            MouseEventPool.freeStopped( e );
    }
    
    /**
     * Notifies this StopManager of a mouse-moved event, so that it can
     * know, when the mouse has not moved for a while an hence has stopped.
     * 
     * @param mouse
     * @param nanoTime
     */
    public final void notifyMouseMoved( Mouse mouse, long nanoTime )
    {
        for ( int i = 0; i < numListeners; i++ )
        {
            lastMovedTimes[ i ] = nanoTime;
        }
    }
    
    public final void update( Mouse mouse, long nanoTime )
    {
        long t = nanoTime;
        
        MouseStoppedEvent e = null;
        
        for ( int i = 0; i < numListeners; i++ )
        {
            if ( t >= lastMovedTimes[ i ] + stopDelays[ i ] )
            {
                lastMovedTimes[ i ] = LAST_MOVED_RESET_TIME;
                
                if ( e == null )
                {
                    e = prepareMouseStoppedEvent( mouse, nanoTime );
                }
                
                fireOnMouseStopped( e, listeners.get( i ), false );
            }
        }
        
        if ( e != null )
        {
            MouseEventPool.freeStopped( e );
        }
    }
    
    public MouseStopManager()
    {
    }
}
