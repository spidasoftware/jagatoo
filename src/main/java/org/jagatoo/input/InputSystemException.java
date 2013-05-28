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
package org.jagatoo.input;

/**
 * This Exception must be the only exception thrown by methods of
 * JAGaToo's InputSystem.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class InputSystemException extends Exception
{
    private static final long serialVersionUID = 6198466862319520551L;
    
    public enum Info
    {
        ILLEGAL_CONTROLLER_CONFIGURATION( "Illegal Controller configuration. Probably no axes and buttons." ),
        ;
        
        private final String text;
        
        public final String getText()
        {
            return ( text );
        }
        
        private Info( String text )
        {
            this.text = text;
        }
    }
    
    private final Info info;
    
    public final Info getInfo()
    {
        return ( info );
    }
    
    public InputSystemException( Info info, Throwable cause )
    {
        super( info.getText() );
        
        this.info = info;
        
        this.initCause( cause );
    }
    
    public InputSystemException( Info info )
    {
        super( info.getText() );
        
        this.info = info;
    }
    
    public InputSystemException( String message, Throwable cause )
    {
        super( message );
        
        this.info = null;
        
        this.initCause( cause );
    }
    
    public InputSystemException( String message )
    {
        super( message );
        
        this.info = null;
    }
    
    public InputSystemException( Throwable cause )
    {
        super( cause.getMessage() );
        
        this.info = null;
        
        this.initCause( cause );
    }
}
