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
package org.jagatoo.commands;

import org.jagatoo.input.devices.components.*;
import org.jagatoo.input.events.*;
import org.jagatoo.input.listeners.InputListener;
import org.jagatoo.logging.Log;
import org.jagatoo.logging.LogChannel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The CommandDispatcher is capable of dispatching {@link Command}s
 * invoked by input devices or a CommandLine.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
class CommandDispatcher implements InputListener
{
    private final LogChannel logChannel;
    private final HashMap< DeviceComponent, ArrayList< Command > > bindings = new HashMap< DeviceComponent, ArrayList< Command > >();
    
    /**
     * Binds a Command to a key action.
     * 
     * @param comp
     * @param command
     */
    public void bindCommand( DeviceComponent comp, Command command )
    {
        ArrayList< Command > commands = bindings.get( comp );
        
        if ( commands == null )
        {
            commands = new ArrayList< Command >();
            bindings.put( comp, commands );
        }
        
        if ( !commands.contains( command ) )
        {
            commands.add( command );
        }
    }
    
    /**
     * Unbinds a Command from a key action.
     * 
     * @param comp
     * @param command
     */
    public void unbindCommand( DeviceComponent comp, Command command )
    {
        ArrayList< Command > commands = bindings.get( comp );
        
        if ( commands == null )
        {
            commands = new ArrayList< Command >();
            bindings.put( comp, commands );
        }
        
        if ( !commands.contains( command ) )
        {
            commands.add( command );
        }
    }
    
    /**
     * Unbinds all.
     */
    public void unbindAll()
    {
        bindings.clear();
    }
    
    public final void executeCommand( Command command, Boolean inputInfo, Object[] parameters )
    {
        try
        {
            final String result = command.execute( inputInfo, parameters );
            
            if ( result != null )
            {
				Log.println(logChannel, result);
            }
        }
        catch ( CommandException ex )
        {
            Log.println( logChannel, ex.getMessage() );
        }
        catch ( Throwable ex )
        {
            Log.print( logChannel, ex );
        }
    }
    
    private final void processInputEvent( Boolean inputInfo, DeviceComponent comp )
    {
        final ArrayList< Command > commands = bindings.get( comp );
        
        if ( commands != null )
        {
            for ( int i = 0; i < commands.size(); i++ )
            {
                executeCommand( commands.get( i ), inputInfo, null );
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void onKeyPressed( KeyPressedEvent e, Key key )
    {
        processInputEvent( Boolean.TRUE, key );
    }
    
    /**
     * {@inheritDoc}
     */
    public void onKeyReleased( KeyReleasedEvent e, Key key )
    {
        processInputEvent( Boolean.FALSE, key );
    }
    
    public void onKeyStateChanged( KeyStateEvent e, Key key, boolean state )
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void onKeyTyped( KeyTypedEvent e, char keyChar )
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void onMouseButtonPressed( MouseButtonPressedEvent e, MouseButton button )
    {
        processInputEvent( Boolean.TRUE, button );
    }
    
    /**
     * {@inheritDoc}
     */
    public void onMouseButtonReleased( MouseButtonReleasedEvent e, MouseButton button )
    {
        processInputEvent( Boolean.FALSE, button );
    }
    
    public void onMouseButtonClicked( MouseButtonClickedEvent e, MouseButton button, int clickCount )
    {
    }
    
    public void onMouseButtonStateChanged( MouseButtonEvent e, MouseButton button, boolean state )
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void onMouseMoved( MouseMovedEvent e, int x, int y, int dx, int dy )
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public void onMouseWheelMoved( MouseWheelEvent e, int wheelDelta )
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public long getMouseStopDelay()
    {
        return ( 500000000L );
    }
    
    /**
     * {@inheritDoc}
     */
    public void onMouseStopped( MouseStoppedEvent e, int x, int y )
    {
    }
    
    public void onControllerButtonPressed( ControllerButtonPressedEvent e, ControllerButton button )
    {
        processInputEvent( Boolean.TRUE, button );
    }
    
    public void onControllerButtonReleased( ControllerButtonReleasedEvent e, ControllerButton button )
    {
        processInputEvent( Boolean.FALSE, button );
    }
    
    public void onControllerButtonStateChanged( ControllerButtonEvent e, ControllerButton button, boolean state )
    {
    }
    
    public void onControllerAxisChanged( ControllerAxisChangedEvent e, ControllerAxis axis, float axisDelta )
    {
    }
    
    /**
     * Creates a new CommandProcessor.
     * 
     * @param logChannel the {@link LogChannel}, which responses are logged to.
     */
    public CommandDispatcher( LogChannel logChannel )
    {
        if ( logChannel == null )
        {
            throw new NullPointerException( "logChannel must not be null" );
        }
        
        this.logChannel = logChannel;
    }
}
