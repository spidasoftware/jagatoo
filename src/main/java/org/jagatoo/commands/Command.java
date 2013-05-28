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

/**
 * An interface for a command to be processed.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface Command
{
    /**
     * @return this command's key (like "bind" or "jump").
     */
    public String getKey();
    
    /**
     * @return a descriptive text for this {@link Command}.
     * 
     * @see #getLocalizedText()
     */
    public String getText();
    
    /**
     * @return a <b>localozed</b> descriptive text for this {@link Command}.
     * You should prefer this method over {@link #getText()}.
     */
    public String getLocalizedText();
    
    /**
     * @return the number of expected parameters.
     */
    public int getNumParameters();
    
    /**
     * @return an array containing the String representation of the parameter types used for highlighting.
     */
    public String[] getParameterTypes();
    
    /**
     * Creates a parameters array for the given command line.
     */
    public Object[] createParametersArray( ArrayList< String > parameters );
    
    /**
     * Executes this command.<br>
     * If a CommandException is thrown, then only its info text is displayed.
     * 
     * @param inputInfo this Boolean is true for a key-down or wheel-up and false for a key-up or wheel-down.
     *                  The Command implementation must be aware of the fact, that this Boolean can be null.
     * @param parameters the command's parameters
     * 
     * @return this command's result if successful.
     *         Used to give the user a textual response to the command execution.
     *         May be null for simple commands.
     * 
     * @throws CommandException
     */
    public String execute( Boolean inputInfo, Object[] parameters ) throws CommandException;
    
    /**
     * Executes this command.<br>
     * If a CommandException is thrown, then only its info text is displayed.
     * 
     * @param parameters the command's parameters
     * 
     * @return this command's result if successful.
     *         Used to give the user a textual response to the command execution.
     *         May be null for simple commands.
     * 
     * @throws CommandException
     * @throws Throwable
     */
    public String execute( Object[] parameters ) throws CommandException;
    
    /**
     * Executes this command.<br>
     * If a CommandException is thrown, then only its info text is displayed.
     * 
     * @param inputInfo this Boolean is true for a key-down or wheel-up and false for a key-up or wheel-down.
     *                  The Command implementation must be aware of the fact, that this Boolean can be null.
     * @param commandLine the CommandLine to extract the parameters array from
     * 
     * @return this command's result if successful.
     *         Used to give the user a textual response to the command execution.
     *         May be null for simple commands.
     * 
     * @throws CommandException
     */
    public String execute( Boolean inputInfo, CommandLine commandLine ) throws CommandException;
    
    /**
     * Executes this command.<br>
     * If a CommandException is thrown, then only its info text is displayed.
     * 
     * @param commandLine the CommandLine to extract the parameters array from
     * 
     * @return this command's result if successful.
     *         Used to give the user a textual response to the command execution.
     *         May be null for simple commands.
     * 
     * @throws CommandException
     * @throws Throwable
     */
    public String execute( CommandLine commandLine ) throws CommandException;
}
