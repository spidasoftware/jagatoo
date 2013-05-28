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

import java.util.List;

import org.jagatoo.input.InputSystem;
import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.actions.InputAction;
import org.jagatoo.input.actions.InvokableInputAction;
import org.jagatoo.input.devices.Controller;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.Keyboard;
import org.jagatoo.input.devices.Mouse;
import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.devices.components.InputState;
import org.jagatoo.input.devices.components.MouseAxis;
import org.jagatoo.input.devices.components.MouseButton;
import org.jagatoo.input.devices.components.MouseWheel;
import org.jagatoo.input.devices.components.MouseWheel.WheelUpDownComponent;
import org.jagatoo.logging.Log;

/**
 * Manages state-changes on any kind of {@link InputDevice}.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputStatesManager
{
    public static final int MOUSE_AXES_SUSPENDED = 1;
    public static final int MOUSE_BUTTONS_SUSPENDED = 2;
    public static final int MOUSE_WHEEL_SUSPENDED = 4;
    public static final int KEYBOARD_SUSPENDED = 8;
    public static final int CONTROLLERS_SUSPENDED = 16;
    
    private final InputBindingsManager< ? extends InputAction > bindingsManager;
    
    private InputStatesManipulator manipulator = null;
    
    private int suspendMask = 0;
    
    private final int numStates;
    
    private final short[] states1;
    private final short[] states2;
    
    private short[] prevStates;
    private short[] currStates;
    private final short[] tmpPrevStates;
    private final short[] tmpCurrStates;
    
    private boolean swapper = false;
    
    /**
     * @return the {@link InputStatesManipulator} for this manager.
     */
    public InputStatesManipulator getInputStatesManipulator()
    {
        if ( manipulator == null )
        {
            manipulator = new InputStatesManipulator( this );
        }
        
        return ( manipulator );
    }
    
    /**
     * @deprecated renamed to {@link #getInputStatesManipulator()}
     */
    @Deprecated
    public final InputStatesManipulator getKeyStatesManipulator()
    {
        return ( getInputStatesManipulator() );
    }
    
    /**
     * Suspends or resumes this InputStatesManager.<br>
     * 
     * @see #MOUSE_AXES_SUSPENDED
     * @see #MOUSE_BUTTONS_SUSPENDED
     * @see #MOUSE_WHEEL_SUSPENDED
     * @see #KEYBOARD_SUSPENDED
     * @see #CONTROLLERS_SUSPENDED
     * 
     * If an InputStatesManager is suspended, it will ignore any input.
     * 
     * @param suspendMask
     */
    public void setSuspendMask( int suspendMask )
    {
        this.suspendMask = suspendMask;
    }
    
    /**
     * @return the suspendMask.<br>
     * 
     * @see #MOUSE_AXES_SUSPENDED
     * @see #MOUSE_BUTTONS_SUSPENDED
     * @see #MOUSE_WHEEL_SUSPENDED
     * @see #KEYBOARD_SUSPENDED
     * @see #CONTROLLERS_SUSPENDED
     * 
     * If an InputStatesManager is suspended, it will ignore any input.
     */
    public final int getSuspendMask()
    {
        return ( suspendMask );
    }
    
    /**
     * @return the number of key-states (or commands).
     */
    public final int getNumStates()
    {
        return ( numStates );
    }
    
    /**
     * @param action
     * 
     * @return the current key-state for the given action.
     */
    public final InputState getInputState( InputAction action )
    {
        final int ordinal = action.ordinal();
        
        if ( states1[ ordinal ] == states2[ ordinal ] )
        {
            if ( states1[ ordinal ] > 0 )
                return ( InputState.POSITIVE );
            
            return ( InputState.NEGATIVE );
        }
        
        if ( swapper )
        {
            if ( states1[ ordinal ] > states2[ ordinal ] )
                return ( InputState.MADE_POSITIVE );
            
            return ( InputState.MADE_NEGATIVE );
        }
        
        if ( states2[ ordinal ] > states1[ ordinal ] )
            return ( InputState.MADE_POSITIVE );
        
        return ( InputState.MADE_NEGATIVE );
    }
    
    /**
     * @param action
     * 
     * @return the current key-state for the given action.
     */
    public final int getSimpleInputState( InputAction action )
    {
        return ( currStates[ action.ordinal() ] );
    }
    
    /**
     * @param action
     * 
     * @return whether the state for the given action's state has changed.
     */
    public final boolean hasInputStateChanged( InputAction action )
    {
        return ( states1[ action.ordinal() ] != states2[ action.ordinal() ] );
    }
    
    private final void invokeAction( final InputDevice device, final DeviceComponent comp, final int ordinal, final InputAction action, long nanoTime )
    {
        if ( action instanceof InvokableInputAction )
        {
            final InvokableInputAction invAction = (InvokableInputAction)action;
            
            try
            {
                invAction.invokeAction( device, comp, ( tmpCurrStates[ ordinal ] - tmpPrevStates[ ordinal ] ), tmpCurrStates[ ordinal ], nanoTime );
            }
            catch ( InputSystemException ex )
            {
                Log.print( InputSystem.LOG_CHANNEL, ex );
                ex.printStackTrace();
            }
        }
    }
    
    private final int updateState_( final InputDevice device, final DeviceComponent comp, int state, long nanoTime, boolean mouseAxesIgnored, boolean mouseButtonsIgnored, boolean mouseWheelIgnored, boolean keyboardIgnored, boolean controllersIgnored )
    {
        final InputAction action = bindingsManager.getBoundAction( comp );
        
        if ( action == null )
            return ( -1 );
        
        final int ordinal = action.ordinal();
        
        if ( mouseAxesIgnored && ( comp instanceof MouseAxis ) )
            tmpPrevStates[ ordinal ] = (short)state;
        else if ( mouseButtonsIgnored && ( comp instanceof MouseButton ) )
            tmpPrevStates[ ordinal ] = (short)state;
        else if ( mouseWheelIgnored && ( comp instanceof MouseWheel ) )
            tmpPrevStates[ ordinal ] = (short)state;
        else if ( mouseWheelIgnored && ( comp instanceof WheelUpDownComponent ) )
            tmpPrevStates[ ordinal ] = (short)state;
        else if ( keyboardIgnored && ( device instanceof Keyboard ) )
            { state = 0; tmpPrevStates[ ordinal ] = (short)state; }
        else if ( controllersIgnored && ( device instanceof Controller ) )
            tmpPrevStates[ ordinal ] = (short)state;
        
        tmpCurrStates[ ordinal ] = (short)state;
        
        if ( tmpCurrStates[ ordinal ] != tmpPrevStates[ ordinal ] )
        {
            invokeAction( device, comp, ordinal, action, nanoTime );
        }
        
        return ( ordinal );
    }
    
    private final void updateWheelStates( final Mouse mouse, final MouseWheel wheel, final int state, final int delta, long nanoTime, boolean mouseAxesIgnored, boolean mouseButtonsIgnored, boolean mouseWheelIgnored, boolean keyboardIgnored, boolean controllersIgnored )
    {
        updateState_( mouse, wheel, state, nanoTime, mouseAxesIgnored, mouseButtonsIgnored, mouseWheelIgnored, keyboardIgnored, controllersIgnored );
        
        final int result;
        if ( delta > 0 )
            result = updateState_( mouse, wheel.getUp(), 1, nanoTime, mouseAxesIgnored, mouseButtonsIgnored, mouseWheelIgnored, keyboardIgnored, controllersIgnored );
        else
            result = updateState_( mouse, wheel.getDown(), 1, nanoTime, mouseAxesIgnored, mouseButtonsIgnored, mouseWheelIgnored, keyboardIgnored, controllersIgnored );
        
        if ( !mouseWheelIgnored && ( result >= 0 ) )
        {
            tmpPrevStates[ result ] = 0;
        }
    }
    
    final void internalUpdateState( final InputDevice device, DeviceComponent comp, final int state, final int delta, long nanoTime )
    {
        final boolean mouseAxesIgnored = ( ( suspendMask & MOUSE_AXES_SUSPENDED ) != 0 );
        final boolean mouseButtonsIgnored = ( ( suspendMask & MOUSE_BUTTONS_SUSPENDED ) != 0 );
        final boolean mouseWheelIgnored = ( ( suspendMask & MOUSE_WHEEL_SUSPENDED ) != 0 );
        final boolean keyboardIgnored = ( ( suspendMask & KEYBOARD_SUSPENDED ) != 0 );
        final boolean controllersIgnored = ( ( suspendMask & CONTROLLERS_SUSPENDED ) != 0 );
        
        if ( comp.getType() == DeviceComponent.Type.MOUSE_WHEEL )
        {
            if ( comp != MouseWheel.GLOBAL_WHEEL )
                comp = MouseWheel.GLOBAL_WHEEL;
            
            updateWheelStates( (Mouse)device, (MouseWheel)comp, state, delta, nanoTime, mouseAxesIgnored, mouseButtonsIgnored, mouseWheelIgnored, keyboardIgnored, controllersIgnored );
        }
        else
        {
            updateState_( device, comp, state, nanoTime, mouseAxesIgnored, mouseButtonsIgnored, mouseWheelIgnored, keyboardIgnored, controllersIgnored );
        }
    }
    
    /**
     * Updates the key-states array.
     * 
     * @param nanoTime
     */
    public void update( long nanoTime )
    {
        swapper = !swapper;
        
        if ( swapper )
        {
            currStates = states1;
            prevStates = states2;
        }
        else
        {
            currStates = states2;
            prevStates = states1;
        }
        
        System.arraycopy( tmpPrevStates, 0, prevStates, 0, numStates );
        System.arraycopy( tmpCurrStates, 0, currStates, 0, numStates );
        System.arraycopy( tmpCurrStates, 0, tmpPrevStates, 0, numStates );
        
        //System.out.println( prevStates[ 0 ] + ", " + currStates[ 0 ] );
        
        if ( manipulator != null )
        {
            manipulator.apply( currStates );
        }
    }
    
    /**
     * Fills all InputActions into the list, that currently have
     * the requested InputState.
     * 
     * @param state
     * @param actions
     * @param clearListBefore
     */
    public void getActionsByState( InputState state, List< ? super InputAction > actions, boolean clearListBefore )
    {
        actions.clear();
        
        for ( InputAction action : bindingsManager.getBoundActions() )
        {
            final InputState state2 = getInputState( action );
            
            if ( state2 == state )
            {
                actions.add( action );
            }
        }
    }
    
    /**
     * Fills all InputActions into the list, that currently have
     * the requested InputState.<br>
     * The list is cleared before.
     * 
     * @param state
     * @param actions
     */
    public final void getActionsByState( InputState state, List< ? super InputAction > actions )
    {
        getActionsByState( state, actions, true );
    }
    
    /**
     * Fills all InputActions into the array, that currently have
     * the requested InputState.<br>
     * The array is not cleared before.<br>
     * The array must be of sufficient length.<br>
     * The number of found actions is returned.
     * 
     * @param state
     * @param actions
     * 
     * @return the number of found actions.
     */
    public int getActionsByState( InputState state, InputAction[] actions )
    {
        int i = 0;
        for ( InputAction action : bindingsManager.getBoundActions() )
        {
            final InputState state2 = getInputState( action );
            
            if ( state2 == state )
            {
                actions[ i++ ] = action;
            }
        }
        
        return ( i );
    }
    
    /**
     * Fills all InputActions, that currently have
     * the requested InputState, into an array.<br>
     * 
     * @param state
     * @param actions
     * 
     * @return an array of all found actions, or null, if no actions have been found.
     */
    public InputAction[] getActionsByState( InputState state )
    {
        InputAction[] actions = null;
        
        int i = 0;
        for ( InputAction action : bindingsManager.getBoundActions() )
        {
            final InputState state2 = getInputState( action );
            
            if ( state2 == state )
            {
                if ( actions == null )
                    actions = new InputAction[ numStates ];
                
                actions[ i++ ] = action;
            }
        }
        
        if ( ( i > 0 ) && ( i < numStates ) )
        {
            InputAction[] actions2 = new InputAction[ i ];
            System.arraycopy( actions, 0, actions2, 0, i );
            
            return ( actions2 );
        }
        
        return ( actions );
    }
    
    /**
     * Creates a new {@link InputStatesManager}.
     * 
     * @param numActions the number of available (bindeable) actions.
     */
    public InputStatesManager( InputBindingsManager< ? extends InputAction > bindingsManager )
    {
        this.bindingsManager = bindingsManager;
        
        final int numActions = bindingsManager.getNumActions();
        
        this.numStates = numActions;
        
        this.states1 = new short[ numActions ];
        this.states2 = new short[ numActions ];
        this.tmpPrevStates = new short[ numActions ];
        this.tmpCurrStates = new short[ numActions ];
        
        for ( int i = 0; i < numActions; i++ )
        {
            states1[ i ] = 0;
            states2[ i ] = 0;
            tmpPrevStates[ i ] = 0;
            tmpCurrStates[ i ] = 0;
        }
        
        prevStates = states1;
        currStates = states2;
        
        swapper = false;
    }
}
