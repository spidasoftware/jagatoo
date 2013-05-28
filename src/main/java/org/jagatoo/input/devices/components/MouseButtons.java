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
 * This class provides public static access to all Mouse buttons.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public class MouseButtons
{
    static final MouseButton[] buttonsMap = new MouseButton[ 32 ];
    
    public static final MouseButton LEFT_BUTTON = new MouseButton( "Left Button" );
    public static final MouseButton MIDDLE_BUTTON = new MouseButton( "Middle Button" );
    public static final MouseButton RIGHT_BUTTON = new MouseButton( "Right Button" );
    
    public static final MouseButton EXT_BUTTON_1 = new MouseButton( "Extension Button 1" );
    public static final MouseButton EXT_BUTTON_2 = new MouseButton( "Extension Button 2" );
    public static final MouseButton EXT_BUTTON_3 = new MouseButton( "Extension Button 3" );
    public static final MouseButton EXT_BUTTON_4 = new MouseButton( "Extension Button 4" );
    public static final MouseButton EXT_BUTTON_5 = new MouseButton( "Extension Button 5" );
    public static final MouseButton EXT_BUTTON_6 = new MouseButton( "Extension Button 6" );
    public static final MouseButton EXT_BUTTON_7 = new MouseButton( "Extension Button 7" );
    public static final MouseButton EXT_BUTTON_8 = new MouseButton( "Extension Button 8" );
    public static final MouseButton EXT_BUTTON_9 = new MouseButton( "Extension Button 9" );
    public static final MouseButton EXT_BUTTON_10 = new MouseButton( "Extension Button 10" );
    public static final MouseButton EXT_BUTTON_11 = new MouseButton( "Extension Button 11" );
    public static final MouseButton EXT_BUTTON_12 = new MouseButton( "Extension Button 12" );
    public static final MouseButton EXT_BUTTON_13 = new MouseButton( "Extension Button 13" );
    public static final MouseButton EXT_BUTTON_14 = new MouseButton( "Extension Button 14" );
    public static final MouseButton EXT_BUTTON_15 = new MouseButton( "Extension Button 15" );
    public static final MouseButton EXT_BUTTON_16 = new MouseButton( "Extension Button 16" );
    public static final MouseButton EXT_BUTTON_17 = new MouseButton( "Extension Button 17" );
    public static final MouseButton EXT_BUTTON_18 = new MouseButton( "Extension Button 18" );
    public static final MouseButton EXT_BUTTON_19 = new MouseButton( "Extension Button 19" );
    public static final MouseButton EXT_BUTTON_20 = new MouseButton( "Extension Button 20" );
    public static final MouseButton EXT_BUTTON_21 = new MouseButton( "Extension Button 21" );
    public static final MouseButton EXT_BUTTON_22 = new MouseButton( "Extension Button 22" );
    public static final MouseButton EXT_BUTTON_23 = new MouseButton( "Extension Button 23" );
    public static final MouseButton EXT_BUTTON_24 = new MouseButton( "Extension Button 24" );
    public static final MouseButton EXT_BUTTON_25 = new MouseButton( "Extension Button 25" );
    public static final MouseButton EXT_BUTTON_26 = new MouseButton( "Extension Button 26" );
    public static final MouseButton EXT_BUTTON_27 = new MouseButton( "Extension Button 27" );
    public static final MouseButton EXT_BUTTON_28 = new MouseButton( "Extension Button 28" );
    public static final MouseButton EXT_BUTTON_29 = new MouseButton( "Extension Button 29" );
    
    public static final int MASK_LEFT_BUTTON = LEFT_BUTTON.getMaskValue();
    public static final int MASK_MIDDLE_BUTTON = MIDDLE_BUTTON.getMaskValue();
    public static final int MASK_RIGHT_BUTTON = RIGHT_BUTTON.getMaskValue();
    
    public static final int MASK_EXT_BUTTON_1 = EXT_BUTTON_1.getMaskValue();
    public static final int MASK_EXT_BUTTON_2 = EXT_BUTTON_2.getMaskValue();
    public static final int MASK_EXT_BUTTON_3 = EXT_BUTTON_3.getMaskValue();
    public static final int MASK_EXT_BUTTON_4 = EXT_BUTTON_4.getMaskValue();
    public static final int MASK_EXT_BUTTON_5 = EXT_BUTTON_5.getMaskValue();
    public static final int MASK_EXT_BUTTON_6 = EXT_BUTTON_6.getMaskValue();
    public static final int MASK_EXT_BUTTON_7 = EXT_BUTTON_7.getMaskValue();
    public static final int MASK_EXT_BUTTON_8 = EXT_BUTTON_8.getMaskValue();
    public static final int MASK_EXT_BUTTON_9 = EXT_BUTTON_9.getMaskValue();
    public static final int MASK_EXT_BUTTON_10 = EXT_BUTTON_10.getMaskValue();
    public static final int MASK_EXT_BUTTON_11 = EXT_BUTTON_11.getMaskValue();
    public static final int MASK_EXT_BUTTON_12 = EXT_BUTTON_12.getMaskValue();
    public static final int MASK_EXT_BUTTON_13 = EXT_BUTTON_13.getMaskValue();
    public static final int MASK_EXT_BUTTON_14 = EXT_BUTTON_14.getMaskValue();
    public static final int MASK_EXT_BUTTON_15 = EXT_BUTTON_15.getMaskValue();
    public static final int MASK_EXT_BUTTON_16 = EXT_BUTTON_16.getMaskValue();
    public static final int MASK_EXT_BUTTON_17 = EXT_BUTTON_17.getMaskValue();
    public static final int MASK_EXT_BUTTON_18 = EXT_BUTTON_18.getMaskValue();
    public static final int MASK_EXT_BUTTON_19 = EXT_BUTTON_19.getMaskValue();
    public static final int MASK_EXT_BUTTON_20 = EXT_BUTTON_20.getMaskValue();
    public static final int MASK_EXT_BUTTON_21 = EXT_BUTTON_21.getMaskValue();
    public static final int MASK_EXT_BUTTON_22 = EXT_BUTTON_22.getMaskValue();
    public static final int MASK_EXT_BUTTON_23 = EXT_BUTTON_23.getMaskValue();
    public static final int MASK_EXT_BUTTON_24 = EXT_BUTTON_24.getMaskValue();
    public static final int MASK_EXT_BUTTON_25 = EXT_BUTTON_25.getMaskValue();
    public static final int MASK_EXT_BUTTON_26 = EXT_BUTTON_26.getMaskValue();
    public static final int MASK_EXT_BUTTON_27 = EXT_BUTTON_27.getMaskValue();
    public static final int MASK_EXT_BUTTON_28 = EXT_BUTTON_28.getMaskValue();
    public static final int MASK_EXT_BUTTON_29 = EXT_BUTTON_29.getMaskValue();
    
    public static final MouseWheel.WheelUpDownComponent WHEEL_UP = MouseWheel.GLOBAL_WHEEL.getUp();
    public static final MouseWheel.WheelUpDownComponent WHEEL_DOWN = MouseWheel.GLOBAL_WHEEL.getDown();
    
    public static final MouseButton getByIndex( int index )
    {
        return ( buttonsMap[ index ] );
    }
}
