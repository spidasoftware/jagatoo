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
package org.jagatoo.input.devices.components;

/**
 * A simple abstraction of a keyboard's key.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Key extends DigitalDeviceComponent
{
    private final KeyID keyID;
    
    private final int keyCode;
    
    private final boolean hasKeyChar;
    
    /**
     * @return this key's key-ID.
     */
    public final KeyID getKeyID()
    {
        return ( keyID );
    }
    
    /**
     * @return this key's key-code.
     * Each Key has a unique key-code, that starts at 1 for the
     * first registered Key.<br>
     * This is always equal to the KeyID's ordinal + 1.
     */
    public final int getKeyCode()
    {
        return ( keyCode );
    }
    
    /**
     * @return true, if this Key has a printable char.
     */
    public final boolean hasKeyChar()
    {
        return ( hasKeyChar );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object key )
    {
        if ( !(key instanceof Key ) )
            return ( false );
        
        return ( this.getName().equals( ((Key)key).getName() ) );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return ( getName().hashCode() );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return ( "Key { name = " + getName() + ", ID = " + getDeviceComponentID() + ", keyCode = " + getKeyCode() + " }" );
    }
    
    public Key( String keyName, boolean hasKeyChar )
    {
        super( Type.KEY, keyName );
        
        this.hasKeyChar = hasKeyChar;
        
        this.keyID = KeyID.valueOf( this );
        this.keyCode = keyID.ordinal() + 1;
        
        Keys.numKeys = keyCode;
    }
}
