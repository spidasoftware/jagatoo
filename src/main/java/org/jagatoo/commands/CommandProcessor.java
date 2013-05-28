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

import java.util.HashMap;
import java.util.List;

import org.jagatoo.input.devices.components.DeviceComponent;
import org.jagatoo.input.listeners.InputListener;
import org.jagatoo.input.managers.InputBindingsManager;
import org.jagatoo.input.managers.InputBindingsSet;
import org.jagatoo.logging.Log;
import org.jagatoo.logging.LogChannel;

/**
 * Processes command lines and extracts a CommandLine object from it.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class CommandProcessor
{
    private final LogChannel logChannel;
    private final HashMap< String, Command > availableCommandsMap = new HashMap< String, Command >();
    private Command[] availableCommands;
    
    private final CommandLine commandLine = new CommandLine();
    
    private final CommandDispatcher dispatcher;
    
    /**
     * @return the {@link LogChannel}, which responses are logged to.
     */
    public final LogChannel getLogChannel()
    {
        return ( logChannel );
    }
    
    /**
     * @return an array of all registered {@link Command}s.
     */
    public Command[] getRegisteredCommands()
    {
        return ( availableCommands );
    }
    
    /**
     * @return the {@link Command} registered with that key.
     */
    public Command getRegisteredCommand( String key )
    {
        return ( availableCommandsMap.get( key ) );
    }
    
    /**
     * @return the InputListener, that has to be registered to the {@link InputManager}.
     */
    public final InputListener getInputListener()
    {
        return ( dispatcher );
    }
    
    /**
     * Registers a Command to be handled by the CommandProcessor.
     * 
     * @param command
     */
    public final Command registerCommand( Command command )
    {
        if ( !availableCommandsMap.containsKey( command.getKey() ) )
        {
            availableCommandsMap.put( command.getKey(), command );
            
            Command[] newArray = new Command[ availableCommands.length + 1 ];
            System.arraycopy( availableCommands, 0, newArray, 0, availableCommands.length );
            newArray[ newArray.length - 1 ] = command;
            availableCommands = newArray;
        }
        
        return ( command );
    }
    
    /**
     * Registers a list of Commands to be handled by the CommandProcessor.
     * 
     * @param commands
     */
    public final void registerCommands( List< ? extends Command > commands )
    {
        for ( int i = 0; i < commands.size(); i++ )
        {
            registerCommand( commands.get( i ) );
        }
    }
    
    /**
     * Registers a list of Commands to be handled by the CommandProcessor.
     * 
     * @param commands
     */
    public final void registerCommands( Command[] commands )
    {
        for ( int i = 0; i < commands.length; i++ )
        {
            registerCommand( commands[ i ] );
        }
    }
    
    /**
     * Binds a Command to a keyboard-key or mouse-button action.
     * 
     * @param comp
     * @param command
     */
    public void bindCommand( DeviceComponent comp, InputActionCommand command )
    {
        dispatcher.bindCommand( comp, command );
    }

    /**
     * Binds all {@link InputActionCommand}s to a keyboard-key or mouse-button action.
     * 
     * @param bindingsManager
     */
    public < C extends InputActionCommand > void bindCommands( InputBindingsManager< C > bindingsManager )
    {
        for ( C command: bindingsManager.getBoundActions() )
        {
            DeviceComponent comp = bindingsManager.getBoundComponent( command, InputBindingsSet.PRIMARY );
            if ( comp != null )
            {
                bindCommand( comp, command );
            }
            
            comp = bindingsManager.getBoundComponent( command, InputBindingsSet.SECONDARY );
            if ( comp != null )
            {
                bindCommand( comp, command );
            }
        }
    }
         
    /**
     * Unbinds a Command from a keyboard-key or mouse-button action.
     * 
     * @param comp
     * @param command
     * 
     * @see KeyCode#getKeyName(int)
     * @see MouseCode#getButtonName(int)
     */
    public void unbindCommand( DeviceComponent comp, InputActionCommand command )
    {
        dispatcher.unbindCommand( comp, command );
    }
    
    /**
     * Unbinds all.
     */
    public void unbindAll()
    {
        dispatcher.unbindAll();
    }
    
    /**
     * Processes a command line and invokes the bound {@link Command}.
     * 
     * @param line
     */
    public void processLine( String line )
    {
        commandLine.parse( line );
        
        if ( commandLine.getKey() != null )
        {
            final Command command = availableCommandsMap.get( commandLine.getKey() );
            
            final Object[] params = command.createParametersArray( commandLine.getParameters() );
            if ( params != null )
            {
                dispatcher.executeCommand( command, null, params );
            }
            else
            {
                Log.exception( logChannel, "Invalid command invocation: " + line );
            }
        }
        else
        {
            Log.exception( logChannel, "Unknown command: " + line );
        }
    }
    
    /**
     * Creates a new CommandProcessor.
     * 
     * @param logChannel the {@link LogChannel}, which responses are logged to.
     */
    public CommandProcessor( LogChannel logChannel )
    {
        if ( logChannel == null )
        {
            throw new NullPointerException( "logChannel must not be null" );
        }
        
        this.logChannel = logChannel;
        
        this.dispatcher = new CommandDispatcher( logChannel );
        
        this.availableCommands = new Command[ 0 ];
    }
    
    /**
     * Creates a new CommandProcessor.
     * 
     * @param logChannel the {@link LogChannel}, which responses are logged to.
     * @param availableCommands
     */
    public CommandProcessor( LogChannel logChannel, Command[] availableCommands )
    {
        this( logChannel );
        
        this.availableCommands = new Command[ availableCommands.length ];
        for ( int i = 0; i < availableCommands.length; i++ )
        {
            this.availableCommandsMap.put( availableCommands[ i ].getKey(), availableCommands[ i ] );
            this.availableCommands[ i ] = availableCommands[ i ];
        }
    }
    
    /**
     * Creates a new CommandProcessor.
     * 
     * @param logChannel the {@link LogChannel}, which responses are logged to.
     * @param availableCommands
     */
    public CommandProcessor( LogChannel logChannel, List< Command > availableCommands )
    {
        this( logChannel );
        
        this.availableCommands = new Command[ availableCommands.size() ];
        for ( int i = 0; i < availableCommands.size(); i++ )
        {
            this.availableCommandsMap.put( availableCommands.get( i ).getKey(), availableCommands.get( i ) );
            this.availableCommands[ i ] = availableCommands.get( i );
        }
    }
}
