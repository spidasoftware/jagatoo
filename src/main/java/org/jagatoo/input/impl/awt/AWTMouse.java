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
package org.jagatoo.input.impl.awt;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.MouseFactory;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.InputEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseEvent;
import org.jagatoo.input.events.MouseEventPool;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * AWT implementation of the Mouse class.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class AWTMouse extends Mouse
{
    private static final MouseButton[] buttonMap = new MouseButton[ 12 ];
    static
    {
        buttonMap[ 1 ] = MouseButtons.LEFT_BUTTON;
        buttonMap[ 3 ] = MouseButtons.RIGHT_BUTTON;
        buttonMap[ 2 ] = MouseButtons.MIDDLE_BUTTON;
        buttonMap[ 0 ] = MouseButtons.EXT_BUTTON_1;
        buttonMap[ 4 ] = MouseButtons.EXT_BUTTON_2;
        buttonMap[ 5 ] = MouseButtons.EXT_BUTTON_3;
        buttonMap[ 6 ] = MouseButtons.EXT_BUTTON_4;
        buttonMap[ 7 ] = MouseButtons.EXT_BUTTON_5;
        buttonMap[ 8 ] = MouseButtons.EXT_BUTTON_6;
        buttonMap[ 9 ] = MouseButtons.EXT_BUTTON_7;
        buttonMap[ 10 ] = MouseButtons.EXT_BUTTON_8;
        buttonMap[ 11 ] = MouseButtons.EXT_BUTTON_9;
    }
    
    public static final MouseButton convertButton( int awtButton )
    {
        return ( buttonMap[ awtButton ] );
    }
    
    private java.awt.Component usedComponent;
    private final java.awt.Point centerComponent = new java.awt.Point();
    private java.awt.Point los = null;
    
    private java.awt.Robot robot = null;
    private boolean needsRecenter = false;
    
    private int lastAbsoluteX = 0;
    private int lastAbsoluteY = 0;
    
    @SuppressWarnings("unused")
    private int lastRelX = -1;
    @SuppressWarnings("unused")
    private int lastRelY = -1;
    
    private int nextIgnoredX = -1;
    private int nextIgnoredY = -1;
    
    private long lastKnownNanoTime = -1L;
    
    private final void ensureRobot() throws InputSystemException
    {
        if ( robot == null )
        {
            try
            {
                robot = new java.awt.Robot();
            }
            catch ( java.awt.AWTException e )
            {
                throw new InputSystemException( e );
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition( int x, int y ) throws InputSystemException
    {
        super.setPosition( x, y );
        
        try
        {
            if ( isAbsolute() )
            {
                ensureRobot();
                robot.mouseMove( los.x + x, los.y + y );
            }
            else
            {
                lastAbsoluteX = x;
                lastAbsoluteY = y;
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void centerMouse() throws InputSystemException
    {
        int centerX = 0;
        int centerY = 0;
        try
        {
            centerX = getSourceWindow().getWidth() / 2;
            centerY = getSourceWindow().getHeight() / 2;
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
        
        setPosition( centerX, centerY );
    }
    
    /**
     * Calculates the positions needed for exlusive mode.
     * Make sure to call either this method after the first frame is rendered or call the setExclusive(true) after it.
     */
    private void updateCenters()
    {
        // calculate center-of-component (in absolute sizes)
        los = usedComponent.getLocationOnScreen();
        final int centerX = los.x + ( usedComponent.getWidth() / 2) + 1;
        final int centerY = los.y + ( usedComponent.getHeight() / 2 ) + 1;
        centerComponent.setLocation( centerX, centerY );
    }
    
    /**
     * Moves the mouse back to the center.
     */
    private void recenter() throws InputSystemException
    {
        ensureRobot();
        
        nextIgnoredX = centerComponent.x - los.x;
        nextIgnoredY = centerComponent.y - los.y;
        
        lastRelX = nextIgnoredX;
        lastRelY = nextIgnoredY;
        
        needsRecenter = false;
        
        robot.mouseMove( centerComponent.x, centerComponent.y );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void collectEvents( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
    }
    
    private final void notifyStatesManagersFromQueue( InputSystem is, EventQueue eventQueue, long nanoTime )
    {
        if ( eventQueue.getNumEvents() == 0 )
            return;
        
        synchronized ( EventQueue.LOCK )
        {
            for ( int i = 0; i < eventQueue.getNumEvents(); i++ )
            {
                final InputEvent event = eventQueue.getEvent( i );
                
                if ( event.getType() == InputEvent.Type.MOUSE_EVENT )
                {
                    final MouseEvent moEvent = (MouseEvent)event;
                    
                    switch( moEvent.getSubType() )
                    {
                        case BUTTON_PRESSED:
                            is.notifyInputStatesManagers( this, moEvent.getComponent(), 1, +1, nanoTime );
                            break;
                        case BUTTON_RELEASED:
                            is.notifyInputStatesManagers( this, moEvent.getComponent(), 0, -1, nanoTime );
                            break;
                        case WHEEL_MOVED:
                            final MouseWheelEvent mwEvent = (MouseWheelEvent)moEvent;
                            final MouseWheel wheel = (MouseWheel)mwEvent.getComponent();
                            is.notifyInputStatesManagers( this, wheel, wheel.getIntValue(), mwEvent.getWheelDelta(), nanoTime );
                            break;
                        case MOVED:
                            final MouseMovedEvent mmEvent = (MouseMovedEvent)moEvent;
                            if ( mmEvent.getDX() != 0 )
                                is.notifyInputStatesManagers( this, mmEvent.getMouse().getXAxis(), mmEvent.getX(), mmEvent.getDX(), nanoTime );
                            
                            if ( mmEvent.getDY() != 0 )
                                is.notifyInputStatesManagers( this, mmEvent.getMouse().getYAxis(), mmEvent.getY(), mmEvent.getDY(), nanoTime );
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMouse( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        lastKnownNanoTime = nanoTime;
        
        try
        {
            notifyStatesManagersFromQueue( is, eventQueue, nanoTime );
            
            getEventQueue().dequeueAndFire( is, InputEvent.Type.MOUSE_EVENT );
            
            handleClickedEvents( nanoTime, is.getMouseButtonClickThreshold() );
            
            if ( !isAbsolute() && needsRecenter )
            {
                //recenter();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void setAbsoluteImpl( boolean absolute ) throws InputSystemException
    {
        try
        {
            updateCenters();
            
            if ( absolute )
            {
                setPosition( getCurrentX(), getCurrentY() );
            }
            else
            {
                recenter();
            }
        }
        catch ( Throwable t )
        {
            if ( t instanceof InputSystemException )
                throw (InputSystemException)t;
            
            if ( t instanceof Error )
                throw (Error)t;
            
            if ( t instanceof RuntimeException )
                throw (RuntimeException)t;
            
            throw new InputSystemException( t );
        }
    }
    
    private void processMouseEvent( java.awt.event.MouseEvent _e )
    {
        if ( !isEnabled() || !getSourceWindow().receivesInputEvents() )
            return;
        
        switch ( _e.getID() )
        {
            case java.awt.event.MouseEvent.MOUSE_PRESSED:
            {
                final MouseButton button = convertButton( _e.getButton() );
                if ( button != null )
                {
                    MouseButtonPressedEvent e = prepareMouseButtonPressedEvent( button, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                }
                
                break;
            }
            case java.awt.event.MouseEvent.MOUSE_RELEASED:
            {
                final MouseButton button = convertButton( _e.getButton() );
                if ( button != null )
                {
                    MouseButtonReleasedEvent e = prepareMouseButtonReleasedEvent( button, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                }
                
                break;
            }
            case java.awt.event.MouseEvent.MOUSE_MOVED:
            case java.awt.event.MouseEvent.MOUSE_DRAGGED:
            {
                if ( isAbsolute() )
                {
                    final int x = _e.getX();
                    final int y = _e.getY();
                    final int dx = x - getCurrentX();
                    final int dy = y - getCurrentY();
                    
                    storePosition( x, y );
                    
                    MouseMovedEvent e = prepareMouseMovedEvent( x, y, dx, dy, lastKnownNanoTime );
                    
                    if ( e == null )
                        return;
                    
                    getEventQueue().enqueue( e );
                    
                    lastAbsoluteX = x;
                    lastAbsoluteY = y;
                }
                else
                {
                    final int dx = _e.getX() - centerComponent.x + los.x;
                    final int dy = _e.getY() - centerComponent.y + los.y;
                    
                    if ( ( dx != 0 ) || ( dy != 0 ) )
                    {
                        lastRelX = _e.getX();
                        lastRelY = _e.getY();
                        
                        nextIgnoredX = -1;
                        nextIgnoredY = -1;
                        
                        MouseMovedEvent e = prepareMouseMovedEvent( lastAbsoluteX, lastAbsoluteY, dx, dy, lastKnownNanoTime );
                        
                        if ( e == null )
                            return;
                        
                        getEventQueue().enqueue( e );
                        
                        needsRecenter = true;
                        
                        try
                        {
                            recenter();
                        }
                        catch ( InputSystemException ise )
                        {
                            ise.printStackTrace();
                        }
                    }
                }
                
                break;
            }
        }
    }
    
    private void processMouseEvent( java.awt.event.MouseWheelEvent _e )
    {
        if ( !isEnabled() )
            return;
        
        java.awt.event.MouseWheelEvent __e = (java.awt.event.MouseWheelEvent)_e;
        final boolean isPageMove = __e.getScrollType() == java.awt.event.MouseWheelEvent.WHEEL_BLOCK_SCROLL;
        
        MouseWheelEvent e = MouseEventPool.allocWheel( this, getWheel(), -__e.getWheelRotation(), isPageMove, lastKnownNanoTime, 0L );
        
        if ( e != null )
            getEventQueue().enqueue( e );
    }
    
    private final java.awt.event.AWTEventListener eventListener = new java.awt.event.AWTEventListener()
    {
        public void eventDispatched( java.awt.AWTEvent event )
        {
            if ( event instanceof java.awt.event.MouseEvent )
                processMouseEvent( (java.awt.event.MouseEvent)event );
            /*
            else if ( event instanceof java.awt.event.MouseWheelEvent )
                processMouseEvent( (java.awt.event.MouseWheelEvent)event );
            */
        }
        
    };
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyImpl() throws InputSystemException
    {
        try
        {
            java.awt.Toolkit.getDefaultToolkit().removeAWTEventListener( eventListener );
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
    }
    
    private final void trapInitialLocationOnScreen( final java.awt.Component component )
    {
        if ( component.isDisplayable() )
        {
            this.los = component.getLocationOnScreen();
        }
        else
        {
            component.addComponentListener( new java.awt.event.ComponentAdapter()
            {
                @Override
                public void componentShown( java.awt.event.ComponentEvent e )
                {
                    AWTMouse.this.los = component.getLocationOnScreen();
                    component.removeComponentListener( this );
                }
            } );
        }
    }
    
    protected AWTMouse( MouseFactory factory, InputSourceWindow sourceWindow, EventQueue eventQueue ) throws InputSystemException
    {
        super( factory, sourceWindow, eventQueue, "Primary Mouse", 12, true );
        
        try
        {
            java.awt.Toolkit.getDefaultToolkit().addAWTEventListener( eventListener, java.awt.AWTEvent.MOUSE_EVENT_MASK | java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK/* | java.awt.AWTEvent.MOUSE_WHEEL_EVENT_MASK*/ );
            
            java.awt.Component component = (java.awt.Component)sourceWindow.getDrawable();
            component.addMouseWheelListener( new java.awt.event.MouseWheelListener()
            {
                public void mouseWheelMoved( java.awt.event.MouseWheelEvent e )
                {
                    processMouseEvent( e );
                }
            } );
            
            this.usedComponent = component;
            trapInitialLocationOnScreen( component );
        }
        catch ( Throwable e )
        {
            throw new InputSystemException( e );
        }
    }
}
