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

import org.jagatoo.input.InputSystemException;
import org.jagatoo.input.actions.LabeledInvokableInputAction;
import org.jagatoo.input.devices.InputDevice;
import org.jagatoo.input.devices.components.DeviceComponent;

/**
 * This is a Command, that can be bound to a key.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public abstract class InputActionCommand extends NoParamCommandBase implements LabeledInvokableInputAction
{
    private static int nextOrdinal = 0;
    
    private final int ordinal;
    
    /**
     * {@inheritDoc}
     */
    public final int ordinal()
    {
        return ( ordinal );
    }
    
    /**
     * {@inheritDoc}
     */
    public String invokeAction( InputDevice device, DeviceComponent comp, int delta, int state, long nanoTime ) throws InputSystemException
    {
        try
        {
            return ( execute( delta > 0 ) );
        }
        catch ( Throwable t )
        {
            throw new InputSystemException( t );
        }
    }
    
    /**
     * Creates a new key-bound {@link Command}.
     * 
     * @param commandKey this is NOT the Keyboard-key, but the command's key (name).
     * @param ordinal this {@link KeyCommandInterface}'s ordinal. The next auto-ordinal will be resettet to (ordinal + 1), if this is >= 0.
     * @param text the Text, that is displayed in a configuration dialog
     * @param numParams
     */
    protected InputActionCommand( final String commandKey, int ordinal, String text, int numParams )
    {
        super( commandKey, text, numParams );
        
        if ( ordinal < 0 )
        {
            this.ordinal = nextOrdinal++;
        }
        else
        {
            this.ordinal = ordinal;
            nextOrdinal = ordinal + 1;
        }
    }
    
    /**
     * Creates a new key-bound {@link Command}.
     * 
     * @param commandKey this is NOT the Keyboard-key, but the command's key (name).
     * @param ordinal this {@link KeyCommandInterface}'s ordinal. The next auto-ordinal will be resettet to (ordinal + 1), if this is >= 0.
     * @param text the Text, that is displayed in a configuration dialog
     */
    public InputActionCommand( final String commandKey, int ordinal, String text )
    {
        this( commandKey, ordinal, text, 0 );
    }
    
    /**
     * Creates a new key-bound {@link Command}.
     * 
     * @param commandKey this is NOT the Keyboard-key, but the command's key (name).
     * @param text the Text, that is displayed in a configuration dialog
     */
    public InputActionCommand( final String commandKey, String text )
    {
        this( commandKey, -1, text );
    }
}
