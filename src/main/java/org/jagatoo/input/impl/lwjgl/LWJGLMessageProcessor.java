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
package org.jagatoo.input.impl.lwjgl;

/**
 * If you want special control over LWJGL's processMessages() call,
 * which is quite expensive on Linux, you can use this class'es static methods.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class LWJGLMessageProcessor
{
    private static final Object LOCK = new Object();
    
    private static boolean messageProcessingEnabled = true;
    
    private static int allowedUpdates = -1;
    
    private static long lastUpdateTime = -2L;
    
    /**
     * This enables/disables the call to processMessages() (from within
     * the InputSystem) generally.<br>
     * No updates are performed, if {@link #allowOneUpdate()} was called
     * and {@link #allowUpdateAlways()} wasn't after it.
     * 
     * @param enabled
     */
    public static final void setMessageProcessingEnabled( boolean enabled )
    {
        messageProcessingEnabled = enabled;
    }
    
    /**
     * @return true, if calls to LWJGL's processMessages() method are allowed
     * generally.<br>
     * No updates are performed, if {@link #allowOneUpdate()} was called
     * and {@link #allowUpdateAlways()} wasn't after it.
     */
    public static final boolean isMessageProcessingEnabled()
    {
        return ( messageProcessingEnabled );
    }
    
    /**
     * This method increases the count of allowed calls to LWJGL's
     * processMessages() method.<br>
     * Updates are not performed, if {@link #isMessageProcessingEnabled()}
     * is false.
     */
    public static final void allowOneUpdate()
    {
        synchronized ( LOCK )
        {
            if ( allowedUpdates < 0 )
                allowedUpdates = 1;
            else
                allowedUpdates++;
        }
    }
    
    /**
     * Resets the number of allowed calls to LWJGL's processMessages()
     * method to unlimited.<br>
     * Updates are not performed, if {@link #isMessageProcessingEnabled()}
     * is false.
     */
    public static final void allowUpdateAlways()
    {
        synchronized ( LOCK )
        {
            allowedUpdates = -1;
        }
    }
    
    /**
     * This value is for LWJGLKeyboard, LWJGLMouse and LWJGLController.
     * It is not used in this class at all.
     */
    static void processMessages( long nanoTime )
    {
        if ( !messageProcessingEnabled || ( nanoTime <= lastUpdateTime ) )
            return;
        
        if ( allowedUpdates == 0 )
        {
            return;
        }
        else if ( allowedUpdates > 0 )
        {
            synchronized ( LOCK )
            {
                allowedUpdates--;
            }
        }
        
        org.lwjgl.opengl.Display.processMessages();
        
        lastUpdateTime = nanoTime;
    }
}
