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
 * This class provides public static access to all Keyboard keys.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class Keys
{
    protected static int numKeys = 0;
    
    /**
     * @return the number of registered {@link Key}s.
     */
    public static final int getNumKeys()
    {
        return ( numKeys );
    }
    
    public static final int MODIFIER_SHIFT = 1;
    public static final int MODIFIER_CONTROL = 2;
    public static final int MODIFIER_ALT = 4;
    public static final int MODIFIER_ALT_GRAPH = 8;
    public static final int MODIFIER_LEFT_META = 16;
    public static final int MODIFIER_RIGHT_META = 32;
    
    public static final Key ESCAPE       = new Key( "ESCAPE", false );
    
    public static final Key F1           = new Key( "F1", false );
    public static final Key F2           = new Key( "F2", false );
    public static final Key F3           = new Key( "F3", false );
    public static final Key F4           = new Key( "F4", false );
    public static final Key F5           = new Key( "F5", false );
    public static final Key F6           = new Key( "F6", false );
    public static final Key F7           = new Key( "F7", false );
    public static final Key F8           = new Key( "F8", false );
    public static final Key F9           = new Key( "F9", false );
    public static final Key F10          = new Key( "F10", false );
    public static final Key F11          = new Key( "F11", false );
    public static final Key F12          = new Key( "F12", false );
    
    public static final Key PRINTSCREEN  = new Key( "PRINTSCREEN", false );
    public static final Key PAUSE        = new Key( "PAUSE", false );
    public static final Key SCROLL_LOCK  = new Key( "SCROLL_LOCK", false );
    
    public static final Key CIRCUMFLEX   = new Key( "CIRCUMFLEX", true );
    
    public static final Key _0 = new Key( "0", true );
    public static final Key _1 = new Key( "1", true );
    public static final Key _2 = new Key( "2", true );
    public static final Key _3 = new Key( "3", true );
    public static final Key _4 = new Key( "4", true );
    public static final Key _5 = new Key( "5", true );
    public static final Key _6 = new Key( "6", true );
    public static final Key _7 = new Key( "7", true );
    public static final Key _8 = new Key( "8", true );
    public static final Key _9 = new Key( "9", true );
    
    public static final Key A = new Key( "A", true );
    public static final Key B = new Key( "B", true );
    public static final Key C = new Key( "C", true );
    public static final Key D = new Key( "D", true );
    public static final Key E = new Key( "E", true );
    public static final Key F = new Key( "F", true );
    public static final Key G = new Key( "G", true );
    public static final Key H = new Key( "H", true );
    public static final Key I = new Key( "I", true );
    public static final Key J = new Key( "J", true );
    public static final Key K = new Key( "K", true );
    public static final Key L = new Key( "L", true );
    public static final Key M = new Key( "M", true );
    public static final Key N = new Key( "N", true );
    public static final Key O = new Key( "O", true );
    public static final Key P = new Key( "P", true );
    public static final Key Q = new Key( "Q", true );
    public static final Key R = new Key( "R", true );
    public static final Key S = new Key( "S", true );
    public static final Key T = new Key( "T", true );
    public static final Key U = new Key( "U", true );
    public static final Key V = new Key( "V", true );
    public static final Key W = new Key( "W", true );
    public static final Key X = new Key( "X", true );
    public static final Key Y = new Key( "Y", true );
    public static final Key Z = new Key( "Z", true );
    
    public static final Key TAB             = new Key( "TAB", true );
    public static final Key SPACE           = new Key( "SPACE", true );
    public static final Key BACK_SPACE      = new Key( "BACK_SPACE", true );
    public static final Key ENTER           = new Key( "ENTER", true );
    
    public static final Key LEFT_SHIFT      = new Key( "LEFT_SHIFT", false );
    public static final Key RIGHT_SHIFT     = new Key( "RIGHT_SHIFT", false );
    public static final Key LEFT_CONTROL    = new Key( "LEFT_CONTROL", false );
    public static final Key RIGHT_CONTROL   = new Key( "RIGHT_CONTROL", false );
    public static final Key ALT             = new Key( "ALT", false );
    public static final Key ALT_GRAPH       = new Key( "ALT_GRAPH", false );
    public static final Key LEFT_META       = new Key( "LEFT_META", false );
    public static final Key RIGHT_META      = new Key( "RIGHT_META", false );
    public static final Key CAPS_LOCK       = new Key( "CAPS_LOCK", false );
    
    public static final Key DELETE          = new Key( "DELETE", false );
    public static final Key INSERT          = new Key( "INSERT", false );
    public static final Key END             = new Key( "END", false );
    public static final Key HOME            = new Key( "HOME", false );
    public static final Key PAGE_UP         = new Key( "PAGE_UP", false );
    public static final Key PAGE_DOWN       = new Key( "PAGE_DOWN", false );
    
    public static final Key RIGHT           = new Key( "RIGHT", false );
    public static final Key LEFT            = new Key( "LEFT", false );
    public static final Key UP              = new Key( "UP", false );
    public static final Key DOWN            = new Key( "DOWN", false );
    
    public static final Key NUM_LOCK        = new Key( "NUM_LOCK", false );
    
    public static final Key NUMPAD_DIVIDE   = new Key( "NUMPAD_DIVIDE", true );
    public static final Key NUMPAD_MULTIPLY = new Key( "NUMPAD_MULTIPLY", true );
    public static final Key NUMPAD_SUBTRACT = new Key( "NUMPAD_SUBTRACT", true );
    public static final Key NUMPAD_ADD      = new Key( "NUMPAD_ADD", true );
    public static final Key NUMPAD_ENTER    = new Key( "NUMPAD_ENTER", true );
    public static final Key NUMPAD_DECIMAL  = new Key( "NUMPAD_DECIMAL", true );
    
    public static final Key NUMPAD0         = new Key( "NUMPAD0", true );
    public static final Key NUMPAD1         = new Key( "NUMPAD1", true );
    public static final Key NUMPAD2         = new Key( "NUMPAD2", true );
    public static final Key NUMPAD3         = new Key( "NUMPAD3", true );
    public static final Key NUMPAD4         = new Key( "NUMPAD4", true );
    public static final Key NUMPAD5         = new Key( "NUMPAD5", true );
    public static final Key NUMPAD6         = new Key( "NUMPAD6", true );
    public static final Key NUMPAD7         = new Key( "NUMPAD7", true );
    public static final Key NUMPAD8         = new Key( "NUMPAD8", true );
    public static final Key NUMPAD9         = new Key( "NUMPAD9", true );
    
    /**
     * Localized key:<br>
     * two keys left to the back-space
     */
    public static final Key LOCAL_KEY1    = new Key( "LOCAL_KEY1", true );
    
    /**
     * Localized key:<br>
     * one key left to the back-space
     */
    public static final Key LOCAL_KEY2    = new Key( "LOCAL_KEY2", true );
    
    /**
     * Localized key:<br>
     * one key right to P
     */
    public static final Key LOCAL_KEY3    = new Key( "LOCAL_KEY3", true );
    
    /**
     * Localized key:<br>
     * two key right to P
     */
    public static final Key LOCAL_KEY4    = new Key( "LOCAL_KEY4", true );
    
    /**
     * Localized key:<br>
     * one key right to L
     */
    public static final Key LOCAL_KEY5    = new Key( "LOCAL_KEY5", true );
    
    /**
     * Localized key:<br>
     * two keys right to L
     */
    public static final Key LOCAL_KEY6    = new Key( "LOCAL_KEY6", true );
    
    /**
     * Localized key:<br>
     * three keys right to L
     */
    public static final Key LOCAL_KEY7    = new Key( "LOCAL_KEY7", true );
    
    /**
     * Localized key:<br>
     * one key right to M
     */
    public static final Key LOCAL_KEY8    = new Key( "LOCAL_KEY8", true );
    
    /**
     * Localized key:<br>
     * two keys right to M
     */
    public static final Key LOCAL_KEY9    = new Key( "LOCAL_KEY9", true );
    
    /**
     * Localized key:<br>
     * three keys right to M
     */
    public static final Key LOCAL_KEY10   = new Key( "LOCAL_KEY10", true );
    
    /**
     * Localized key:<br>
     * two keys left to the X
     */
    public static final Key LOCAL_KEY11   = new Key( "LOCAL_KEY11", true );
}
