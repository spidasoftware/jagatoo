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
package org.jagatoo.input.devices;

import java.util.ArrayList;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.InputSystemRuntimeException;
import org.jagatoo.input.devices.components.ControllerButton;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.InputState;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseButtons;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.events.EventQueue;
import org.jagatoo.input.events.MouseButtonClickedEvent;
import org.jagatoo.input.events.MouseButtonPressedEvent;
import org.jagatoo.input.events.MouseButtonReleasedEvent;
import org.jagatoo.input.events.MouseEvent;
import org.jagatoo.input.events.MouseEventPool;
import org.jagatoo.input.events.MouseMovedEvent;
import org.jagatoo.input.events.MouseStoppedEvent;
import org.jagatoo.input.events.MouseWheelEvent;
import org.jagatoo.input.events._IS_Evts_PrivilegedAccess;
import org.jagatoo.input.listeners.MouseListener;
import org.jagatoo.input.listeners.MouseStopListener;
import org.jagatoo.input.managers.MouseStopManager;
import org.jagatoo.input.render.InputSourceWindow;

/**
 * This is the base-class for all Mouse implementations.<br>
 * Applications should always use instances as Mouse, but never cast them to
 * the concrete implementation.<br>
 * Instances can only be created/retrieved through an {@link InputDeviceFactory}.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class Mouse extends InputDevice
{
    private final MouseFactory sourceFactory;
    
    private final MouseAxis xAxis, yAxis;
    private final MouseButton[] buttons;
    private final MouseWheel wheel;
    
    private boolean isAbsolute = true;
    
    private int buttonState = 0;
    
    private final ArrayList< MouseListener > listeners = new ArrayList< MouseListener >();
    private int numListeners = 0;
    
    private final MouseButtonClickedEvent[] clickedEvents;
    
    private long lastWhen_buttonPressed = -1L;
    private long lastWhen_buttonReleased = -1L;
    private long lastWhen_moved = -1L;
    private long lastWhen_wheelMoved = -1L;
    
    private MouseStopManager stopManager = new MouseStopManager();
    
    /**
     * @return the {@link MouseFactory}, that created this instance.
     */
    public final MouseFactory getSourceFactory()
    {
        return ( sourceFactory );
    }
    
    protected final void notifyMouseStopManager( long nanoTime )
    {
        if ( stopManager == null )
            return;
        
        stopManager.notifyMouseMoved( this, nanoTime );
    }
    
    /**
     * @return the MouseAxis instance for this Mouse's X-axis.
     */
    public final MouseAxis getXAxis()
    {
        return ( xAxis );
    }
    
    /**
     * @return the MouseAxis instance for this Mouse's Y-axis.
     */
    public final MouseAxis getYAxis()
    {
        return ( yAxis );
    }
    
    /**
     * This method stores the positional values and may do
     * some additional implementation dependent stuff.
     * 
     * @param x
     * @param y
     */
    protected void setPosition( int x, int y ) throws InputSystemException
    {
        this.xAxis.setValue( x );
        this.yAxis.setValue( y );
    }
    
    /**
     * Centers the mouse cursor on the source-window.
     * 
     * @throws InputSystemException
     */
    public abstract void centerMouse() throws InputSystemException;
    
    /**
     * This method simply stores the positional values.
     * 
     * @param x
     * @param y
     */
    protected void storePosition( int x, int y )
    {
        this.xAxis.setValue( x );
        this.yAxis.setValue( y );
    }
    
    /**
     * @return the current x-position of this Mouse.
     */
    public final int getCurrentX()
    {
        return ( xAxis.getIntValue() );
    }
    
    /**
     * @return the current y-position of this Mouse.
     */
    public final int getCurrentY()
    {
        return ( yAxis.getIntValue() );
    }
    
    /**
     * @return the number of {@link ControllerButton}s mounted to this {@link Controller}.
     */
    public final int getButtonsCount()
    {
        return ( buttons.length );
    }
    
    /**
     * @return the number of {@link ControllerButton}s mounted to this {@link Controller}.
     */
    public final MouseButton getButton( int index )
    {
        return ( buttons[ index ] );
    }
    
    /**
     * Sets the new button-state.
     * 
     * @param buttonState
     */
    protected void setButtonsState( int buttonState )
    {
        this.buttonState = buttonState;
    }
    
    /**
     * Adds bits to the button-state's bitmask.
     * 
     * @param buttonState
     */
    protected void addButtonsState( int buttonState )
    {
        this.buttonState |= buttonState;
    }
    
    /**
     * Adds bits to the button-state's bitmask.
     * 
     * @param button
     */
    protected void addButtonsState( MouseButton button )
    {
        this.buttonState |= button.getMaskValue();
    }
    
    /**
     * Removes bits from the button-state's bitmask.
     * 
     * @param buttonState
     */
    protected void removeButtonsState( int buttonState )
    {
        this.buttonState &= ~buttonState;
    }
    
    /**
     * Removes bits from the button-state's bitmask.
     * 
     * @param button
     */
    protected void removeButtonsState( MouseButton button )
    {
        this.buttonState &= ~button.getMaskValue();
    }
    
    /**
     * @return a bitmask with set bits for all pressed buttons.
     */
    public final int getButtonsState()
    {
        return ( buttonState );
    }
    
    /**
     * @return the MouseWheel mounted to this Mouse.
     */
    public final MouseWheel getWheel()
    {
        return ( wheel );
    }
    
    /**
     * @param button
     * 
     * @return true, if the queried {@link MouseButton} is pressed on this Mouse.
     */
    public final boolean isButtonPressed( MouseButton button )
    {
        return ( ( buttonState & button.getMaskValue() ) != 0 );
    }
    
    /**
     * @param button
     * 
     * @return the appropriate state.
     */
    public final InputState getButtonState( MouseButton button )
    {
        if ( isButtonPressed( button ) )
            return ( InputState.POSITIVE );
        
        return ( InputState.NEGATIVE );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getState( DeviceComponent component ) throws InputSystemRuntimeException
    {
        if ( component instanceof MouseButton )
        {
            boolean state = isButtonPressed( (MouseButton)component );
            
            return ( state ? 1 : 0 );
        }
        
        if ( component instanceof MouseWheel )
        {
            if ( ( component != MouseWheel.GLOBAL_WHEEL ) && ( component != getWheel() ) )
                throw new InputSystemRuntimeException( "The given MouseWheel is not part of this Mouse." );
            
            return ( getWheel().getIntValue() );
        }
        
        if ( component instanceof MouseAxis )
        {
            if ( component == getXAxis() )
                return ( getCurrentX() );
            
            if ( component == getYAxis() )
                return ( getCurrentY() );
            
            throw new InputSystemRuntimeException( "The given MouseAxis is not part of this Mouse." );
        }
        
        throw new InputSystemRuntimeException( "The Mouse only supports MouseButton, MouseAxis and MouseWheel instances for this method." );
    }
    
    /**
     * Adds a {@link MouseStopListener} to this Mouse to be notified
     * when the mouse has stopped being moved (for a while).
     * 
     * @param l
     */
    public void addMouseStopListener( MouseStopListener l )
    {
        stopManager.addMouseStopListener( l );
    }
    
    /**
     * Removes a {@link MouseStopListener} from the list of notified instances.
     * 
     * @param l
     */
    public void removeMouseStopListener( MouseStopListener l )
    {
        stopManager.removeMouseStopListener( l );
    }
    
    /**
     * @return true, of at least one {@link MouseStopListener} is currently registered.
     */
    public final boolean hasMouseListener()
    {
        return ( stopManager.hasMouseListener() );
    }
    
    /**
     * @return true, if one of {@link #hasInputStateListener()} or {@link #hasMouseListener()} return true.
     */
    public final boolean hasListener()
    {
        return ( hasInputStateListener() || hasMouseListener() );
    }
    
    /**
     * Adds a {@link MouseListener} to the list of instances being notifed on
     * mouse events.
     * 
     * @param l
     */
    public void addMouseListener( MouseListener l )
    {
        boolean contains = false;
        for ( int i = 0; i < listeners.size(); i++ )
        {
            if ( listeners.get( i ) == l )
            {
                contains = true;
                break;
            }
        }
        
        if ( !contains )
            listeners.add( l );
        if ( l instanceof MouseStopListener )
            addMouseStopListener( (MouseStopListener)l );
        numListeners = listeners.size();
    }
    
    /**
     * Removes a {@link MouseListener} from the list of notified instances.
     * 
     * @param l
     */
    public void removeMouseListener( MouseListener l )
    {
        listeners.remove( l );
        if ( l instanceof MouseStopListener )
            removeMouseStopListener( (MouseStopListener)l );
        numListeners = listeners.size();
    }
    
    /**
     * Prepares a {@link MouseButtonPressedEvent} for being fired.<br>
     * The event is not fired from this method.<br>
     * This method cares about the current button-state.
     * 
     * @param button
     * @param when
     * 
     * @return the new event from the pool or <code>null</code>, if no events are currently accepted.
     */
    protected final MouseButtonPressedEvent prepareMouseButtonPressedEvent( MouseButton button, long when )
    {
        addButtonsState( button );
        
        if ( !isEnabled() || !hasListener() )
            return ( null );
        
        MouseButtonPressedEvent e = MouseEventPool.allocPressed( this, button, when, lastWhen_buttonPressed );
        
        lastWhen_buttonPressed = when;
        
        return ( e );
    }
    
    /**
     * Fires a {@link MouseButtonPressedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseButtonPressed( MouseButtonPressedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                MouseEventPool.freePressed( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseButtonStateChanged( e, e.getButton(), e.getButtonBooleanState() );
            listeners.get( i ).onMouseButtonPressed( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, +1, 1 );
        
        if ( consumeEvent )
            MouseEventPool.freePressed( e );
    }
    
    /**
     * Prepares a {@link MouseButtonReleasedEvent} for being fired.<br>
     * The event is not fired from this method.<br>
     * This method cares about the current button-state.
     * 
     * @param button
     * @param when
     * 
     * @return the new event from the pool or <code>null</code>, if no events are currently accepted.
     */
    protected final MouseButtonReleasedEvent prepareMouseButtonReleasedEvent( MouseButton button, long when )
    {
        removeButtonsState( button );
        
        if ( !isEnabled() || !hasListener() )
            return ( null );
        
        MouseButtonReleasedEvent e = MouseEventPool.allocReleased( this, button, when, lastWhen_buttonReleased );
        
        if ( clickedEvents[ button.getIndex() ] == null )
        {
            clickedEvents[ button.getIndex() ] = MouseEventPool.allocClicked( this, button, when, lastWhen_buttonReleased );
        }
        else
        {
            _IS_Evts_PrivilegedAccess.incClickCount( clickedEvents[ button.getIndex() ], when );
        }
        
        lastWhen_buttonReleased = when;
        
        return ( e );
    }
    
    /**
     * Fires a {@link MouseButtonReleasedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseButtonReleased( MouseButtonReleasedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                MouseEventPool.freeReleased( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseButtonStateChanged( e, e.getButton(), e.getButtonBooleanState() );
            listeners.get( i ).onMouseButtonReleased( e, e.getButton() );
        }
        
        fireStateEventsAndDoActions( e, -1, 0 );
        
        if ( consumeEvent )
            MouseEventPool.freeReleased( e );
    }
    
    /**
     * Fires a {@link MouseButtonClickedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseButtonClicked( MouseButtonClickedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() || !hasListener() )
        {
            if ( consumeEvent )
                MouseEventPool.freeClicked( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseButtonClicked( e, e.getButton(), e.getClickCount() );
        }
        
        if ( consumeEvent )
            MouseEventPool.freeClicked( e );
    }
    
    /**
     * Checks, if there are pending clicked-events and compares their when-times
     * with the current when and threshold and fires the events, if necessary.
     * 
     * @param when
     * @param threshold
     */
    protected final void handleClickedEvents( long when, long threshold )
    {
        for ( int i = 0; i < clickedEvents.length; i++ )
        {
            MouseButtonClickedEvent e = clickedEvents[ i ];
            
            if ( ( e != null ) && ( e.getWhen() < ( when - threshold ) ) )
            {
                clickedEvents[ i ] = null;
                
                fireOnMouseButtonClicked( e, true );
            }
        }
    }
    
    /**
     * Prepares a {@link MouseMovedEvent} for being fired.<br>
     * The event is not fired from this method.<br>
     * This method also notifies the {@link MouseStopManager}, so
     * that it can track, when the mouse has stopped.
     * 
     * @param button
     * @param when
     * 
     * @return the new event from the pool or <code>null</code>, if no events are currently accepted.
     */
    protected final MouseMovedEvent prepareMouseMovedEvent( int x, int y, int dx, int dy, long when )
    {
        if ( !isEnabled() || !hasListener() )
            return ( null );
        
        notifyMouseStopManager( when );
        
        if ( numListeners == 0 )
            return ( null );
        
        MouseMovedEvent e = MouseEventPool.allocMoved( this, x, y, dx, dy, when, lastWhen_moved );
        
        lastWhen_moved = when;
        
        return ( e );
    }
    
    /**
     * Fires a {@link MouseMovedEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseMoved( MouseMovedEvent e, boolean consumeEvent )
    {
        if ( !isEnabled() )
        {
            if ( consumeEvent )
                MouseEventPool.freeMoved( e );
            return;
        }
        
        if ( numListeners == 0 )
        {
            if ( consumeEvent )
                MouseEventPool.freeMoved( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseMoved( e, e.getX(), e.getY(), e.getDX(), e.getDY() );
        }
        
        if ( e.getDX() != 0 )
            fireStateEventsAndDoActions( e, e.getDX(), e.getX() );
        if ( e.getDY() != 0 )
            fireStateEventsAndDoActions( e, e.getDY(), e.getY() );
        
        if ( consumeEvent )
            MouseEventPool.freeMoved( e );
    }
    
    /**
     * Prepares a {@link MouseWheelEvent} for being fired.<br>
     * The event is not fired from this method.<br>
     * This method updates the wheel-state.
     * 
     * @param button
     * @param when
     * 
     * @return the new event from the pool or <code>null</code>, if no events are currently accepted.
     */
    protected final MouseWheelEvent prepareMouseWheelMovedEvent( int wheelDelta, boolean isPageMove, long when )
    {
        final MouseWheel wheel = getWheel();
        
        if ( wheel == null )
            return ( null );
        
        wheel.addValue( wheelDelta );
        
        if ( !isEnabled() || !hasListener() )
            return ( null );
        
        MouseWheelEvent e = MouseEventPool.allocWheel( this, wheel, wheelDelta, isPageMove, when, lastWhen_wheelMoved );
        
        lastWhen_wheelMoved = when;
        
        return ( e );
    }
    
    /**
     * Fires a {@link MouseWheelEvent} and pushes it back to the pool,
     * if consumeEvent is true.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireOnMouseWheelMoved( MouseWheelEvent e, boolean consumeEvent )
    {
        final MouseWheel wheel = getWheel();
        
        if ( wheel == null )
        {
            if ( consumeEvent )
                MouseEventPool.freeWheel( e );
            return;
        }
        
        if ( !isEnabled() || ( numListeners == 0 ) )
        {
            if ( consumeEvent )
                MouseEventPool.freeWheel( e );
            return;
        }
        
        for ( int i = 0; i < listeners.size(); i++ )
        {
            listeners.get( i ).onMouseWheelMoved( e, e.getWheelDelta() );
        }
        
        fireStateEventsAndDoActions( e, e.getWheelDelta(), e.getMouse().getWheel().getIntValue() );
        
        if ( consumeEvent )
            MouseEventPool.freeWheel( e );
    }
    
    /**
     * This method simply forwards to the concrete fire* methods.
     * 
     * @param e
     * @param consumeEvent
     */
    public final void fireMouseEvent( MouseEvent e, boolean consumeEvent )
    {
        switch ( e.getSubType() )
        {
            case BUTTON_PRESSED:
                fireOnMouseButtonPressed( (MouseButtonPressedEvent)e, consumeEvent );
                break;
            case BUTTON_RELEASED:
                fireOnMouseButtonReleased( (MouseButtonReleasedEvent)e, consumeEvent );
                break;
            case BUTTON_CLICKED:
                fireOnMouseButtonClicked( (MouseButtonClickedEvent)e, consumeEvent );
                break;
            case MOVED:
                fireOnMouseMoved( (MouseMovedEvent)e, consumeEvent );
                break;
            case STOPPED:
                if ( stopManager != null )
                {
                    stopManager.fireOnMouseStopped( this, (MouseStoppedEvent)e, consumeEvent );
                }
                else if ( consumeEvent )
                {
                    MouseEventPool.freeStopped( (MouseStoppedEvent)e );
                }
                break;
            case WHEEL_MOVED:
                fireOnMouseWheelMoved( (MouseWheelEvent)e, consumeEvent );
                break;
        }
    }
    
    /**
     * Makes this Mouse an absolute mouse (only delta positions become valid) or relative.
     * This method is implicitly called by {@link #setAbsolute(boolean)},
     * but only if the value has changed.
     * 
     * @param absolute
     */
    protected abstract void setAbsoluteImpl( boolean absolute ) throws InputSystemException;
    
    /**
     * Makes this Mouse an absolute mouse (only delta positions become valid) or relative.
     * 
     * @param absolute
     */
    public final void setAbsolute( boolean absolute ) throws InputSystemException
    {
        if ( !this.isAbsolute && absolute )
        {
            this.isAbsolute = absolute;
            setAbsoluteImpl( absolute );
            getSourceWindow().refreshCursor( this );
        }
        else if ( this.isAbsolute && !absolute )
        {
            this.isAbsolute = absolute;
            setAbsoluteImpl( absolute );
            getSourceWindow().refreshCursor( this );
        }
    }
    
    /**
     * @return whether this Mouse is absolute or not.
     */
    public final boolean isAbsolute()
    {
        return ( isAbsolute );
    }
    
    /**
     * Processes pending events from the system
     * and directly fires them (notifes the listeners).<br>
     * This method also flushes the EventQueue, if the previously
     * called {@link #collectEvents(InputSystem, EventQueue, long)}
     * method placed events into it.
     * 
     * @param is
     * @param eventQueue
     * @param nanoTime
     * 
     * @throws InputSystemException
     */
    protected abstract void updateMouse( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void update( InputSystem is, EventQueue eventQueue, long nanoTime ) throws InputSystemException
    {
        updateMouse( is, eventQueue, nanoTime );
        
        stopManager.update( this, nanoTime );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( this.getClass().getSimpleName() + " { name = \"" + getName() + "\", numButtons = " + getButtonsCount() + ", hasWheel = " + ( getWheel() != null ) + " }" );
    }
    
    /**
     * Destroys the Mouse.
     */
    protected abstract void destroyImpl() throws InputSystemException;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void destroy() throws InputSystemException
    {
        this.setAbsolute( true );
        
        destroyImpl();
    }
    
    protected Mouse( MouseFactory sourceFactory, InputSourceWindow sourceWindow, EventQueue eventQueue, String name, int numButtons, boolean hasWheel ) throws InputSystemException
    {
        super( sourceWindow, eventQueue, name );
        
        this.sourceFactory = sourceFactory;
        
        this.xAxis = new MouseAxis( this, 'X', "Mouse-X-Axis" );
        xAxis.setValue( sourceWindow.getWidth() / 2 );
        this.yAxis = new MouseAxis( this, 'Y', "Mouse-Y-Axis" );
        yAxis.setValue( sourceWindow.getHeight() / 2 );
        this.buttons = new MouseButton[ numButtons ];
        this.clickedEvents = new MouseButtonClickedEvent[ numButtons ];
        
        for ( int i = 0; i < buttons.length; i++ )
        {
            buttons[ i ] = MouseButtons.getByIndex( i );
            clickedEvents[ i ] = null;
        }
        
        if ( hasWheel )
            this.wheel = new MouseWheel( this );
        else
            this.wheel = null;
    }
}
