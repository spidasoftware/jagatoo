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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a base class for a registry of available {@link Command}s.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class CommandsRegistry< C extends Command >
{
    protected final HashMap< String, C > commandsMap = new HashMap< String, C >();
    
    private final ArrayList< C > commandsList = new ArrayList< C >();
    private Object[] commands = null;
    private final HashMap< Command, Integer > commandIDs = new HashMap< Command, Integer >();
    
    /*
    @SuppressWarnings("unchecked")
    private final C[] incCapacity( C[] array )
    {
        if ( array == null )
        {
            return ( (C[])new Object[ 1 ] );
        }
        
        final int minCapacity = array.length + 1;
        
        final int oldCapacity = array.length;
        
        if ( minCapacity > oldCapacity )
        {
            final C[] oldArray = array;
            //final int newCapacity = ( oldCapacity * 3 ) / 2 + 1;
            final int newCapacity = minCapacity;
            array = (C[])new Object[ newCapacity ];
            System.arraycopy( oldArray, 0, array, 0, oldCapacity );
        }
        
        return ( array );
    }
    */

    protected void addCommand( C command )
    {
        commandsMap.put( command.getKey(), command );
        
        /*
        commands = incCapacity( commands );
        
        commands[ commands.length - 1 ] = command;
        */
        commandsList.add( command );
        commandIDs.put( command, commandsList.size() - 1 );
    }
    
    /**
     * @param command
     * 
     * @return the ID assotiated with the given {@link Command}.
     */
    public final int getCommandID( Command command )
    {
        return ( commandIDs.get( command ) );
    }
    
    /**
     * @param commandID
     * 
     * @return the Command assotiated with the given commandID.
     */
    public final Command getCommandByID( int commandID )
    {
        return ( (Command)values()[ commandID ] );
    }
    
    public final Object[] values()
    {
        if ( ( commands == null ) || ( commandsList.size() != commands.length ) )
        {
            commands = new Object[ commandsList.size() ];
            
            for ( int i = 0; i < commandsList.size(); i++ )
            {
                commands[ i ] = commandsList.get( i );
            }
        }
        
        return ( commands );
    }
    
    public final C valueOf( String key )
    {
        return ( commandsMap.get( key ) );
    }
    
    @SuppressWarnings("unchecked")
    public final C parseCommand( String str )
    {
        for ( int i = 0; i < commands.length; i++ )
        {
            if ( ((C)commands[ i ]).getKey().equalsIgnoreCase( str ) )
                return ( (C)commands[ i ] );
        }
        
        return ( null );
    }
}
