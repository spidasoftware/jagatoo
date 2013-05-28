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
package org.jagatoo.opengl;

/**
 * A collection of OpenGL constants.
 * 
 * @author Marvin Froehilch (aka Qudus)
 */
public class OGL
{
    /**
     * int-value = 256<br>
     * hex-value = 0x100
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM = 0x100;
    
    /**
     * int-value = 257<br>
     * hex-value = 0x101
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LOAD = 0x101;
    
    /**
     * int-value = 258<br>
     * hex-value = 0x102
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RETURN = 0x102;
    
    /**
     * int-value = 259<br>
     * hex-value = 0x103
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MULT = 0x103;
    
    /**
     * int-value = 260<br>
     * hex-value = 0x104
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ADD = 0x104;
    
    /**
     * int-value = 512<br>
     * hex-value = 0x200
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NEVER = 0x200;
    
    /**
     * int-value = 513<br>
     * hex-value = 0x201
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LESS = 0x201;
    
    /**
     * int-value = 514<br>
     * hex-value = 0x202
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EQUAL = 0x202;
    
    /**
     * int-value = 515<br>
     * hex-value = 0x203
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LEQUAL = 0x203;
    
    /**
     * int-value = 516<br>
     * hex-value = 0x204
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GREATER = 0x204;
    
    /**
     * int-value = 517<br>
     * hex-value = 0x205
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NOTEQUAL = 0x205;
    
    /**
     * int-value = 518<br>
     * hex-value = 0x206
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GEQUAL = 0x206;
    
    /**
     * int-value = 519<br>
     * hex-value = 0x207
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALWAYS = 0x207;
    
    /**
     * int-value = 1<br>
     * hex-value = 0x1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_BIT = 0x1;
    
    /**
     * int-value = 2<br>
     * hex-value = 0x2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_BIT = 0x2;
    
    /**
     * int-value = 4<br>
     * hex-value = 0x4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_BIT = 0x4;
    
    /**
     * int-value = 8<br>
     * hex-value = 0x8
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_BIT = 0x8;
    
    /**
     * int-value = 16<br>
     * hex-value = 0x10
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_STIPPLE_BIT = 0x10;
    
    /**
     * int-value = 32<br>
     * hex-value = 0x20
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MODE_BIT = 0x20;
    
    /**
     * int-value = 64<br>
     * hex-value = 0x40
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHTING_BIT = 0x40;
    
    /**
     * int-value = 128<br>
     * hex-value = 0x80
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_BIT = 0x80;
    
    /**
     * int-value = 256<br>
     * hex-value = 0x100
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_BUFFER_BIT = 0x100;
    
    /**
     * int-value = 512<br>
     * hex-value = 0x200
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_BUFFER_BIT = 0x200;
    
    /**
     * int-value = 1024<br>
     * hex-value = 0x400
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_BUFFER_BIT = 0x400;
    
    /**
     * int-value = 2048<br>
     * hex-value = 0x800
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VIEWPORT_BIT = 0x800;
    
    /**
     * int-value = 4096<br>
     * hex-value = 0x1000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TRANSFORM_BIT = 0x1000;
    
    /**
     * int-value = 8192<br>
     * hex-value = 0x2000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ENABLE_BIT = 0x2000;
    
    /**
     * int-value = 16384<br>
     * hex-value = 0x4000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_BUFFER_BIT = 0x4000;
    
    /**
     * int-value = 32768<br>
     * hex-value = 0x8000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_HINT_BIT = 0x8000;
    
    /**
     * int-value = 65536<br>
     * hex-value = 0x10000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EVAL_BIT = 0x10000;
    
    /**
     * int-value = 131072<br>
     * hex-value = 0x20000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIST_BIT = 0x20000;
    
    /**
     * int-value = 262144<br>
     * hex-value = 0x40000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BIT = 0x40000;
    
    /**
     * int-value = 524288<br>
     * hex-value = 0x80000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SCISSOR_BIT = 0x80000;
    
    /**
     * int-value = 1048575<br>
     * hex-value = 0xFFFFF
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALL_ATTRIB_BITS = 0xFFFFF;
    
    /**
     * int-value = 0<br>
     * hex-value = 0x0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINTS = 0x0;
    
    /**
     * int-value = 1<br>
     * hex-value = 0x1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINES = 0x1;
    
    /**
     * int-value = 2<br>
     * hex-value = 0x2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_LOOP = 0x2;
    
    /**
     * int-value = 3<br>
     * hex-value = 0x3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_STRIP = 0x3;
    
    /**
     * int-value = 4<br>
     * hex-value = 0x4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TRIANGLES = 0x4;
    
    /**
     * int-value = 5<br>
     * hex-value = 0x5
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TRIANGLE_STRIP = 0x5;
    
    /**
     * int-value = 6<br>
     * hex-value = 0x6
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TRIANGLE_FAN = 0x6;
    
    /**
     * int-value = 7<br>
     * hex-value = 0x7
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_QUADS = 0x7;
    
    /**
     * int-value = 8<br>
     * hex-value = 0x8
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_QUAD_STRIP = 0x8;
    
    /**
     * int-value = 9<br>
     * hex-value = 0x9
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON = 0x9;
    
    /**
     * int-value = 0<br>
     * hex-value = 0x0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ZERO = 0x0;
    
    /**
     * int-value = 1<br>
     * hex-value = 0x1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE = 0x1;
    
    /**
     * int-value = 768<br>
     * hex-value = 0x300
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SRC_COLOR = 0x300;
    
    /**
     * int-value = 769<br>
     * hex-value = 0x301
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x301;
    
    /**
     * int-value = 770<br>
     * hex-value = 0x302
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SRC_ALPHA = 0x302;
    
    /**
     * int-value = 771<br>
     * hex-value = 0x303
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x303;
    
    /**
     * int-value = 772<br>
     * hex-value = 0x304
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DST_ALPHA = 0x304;
    
    /**
     * int-value = 773<br>
     * hex-value = 0x305
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x305;
    
    /**
     * int-value = 774<br>
     * hex-value = 0x306
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DST_COLOR = 0x306;
    
    /**
     * int-value = 775<br>
     * hex-value = 0x307
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_DST_COLOR = 0x307;
    
    /**
     * int-value = 776<br>
     * hex-value = 0x308
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SRC_ALPHA_SATURATE = 0x308;
    
    /**
     * int-value = 32769<br>
     * hex-value = 0x8001
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CONSTANT_COLOR = 0x8001;
    
    /**
     * int-value = 32770<br>
     * hex-value = 0x8002
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;
    
    /**
     * int-value = 32771<br>
     * hex-value = 0x8003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CONSTANT_ALPHA = 0x8003;
    
    /**
     * int-value = 32772<br>
     * hex-value = 0x8004
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;
    
    /**
     * int-value = 1<br>
     * hex-value = 0x1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TRUE = 0x1;
    
    /**
     * int-value = 0<br>
     * hex-value = 0x0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FALSE = 0x0;
    
    /**
     * int-value = 12288<br>
     * hex-value = 0x3000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE0 = 0x3000;
    
    /**
     * int-value = 12289<br>
     * hex-value = 0x3001
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE1 = 0x3001;
    
    /**
     * int-value = 12290<br>
     * hex-value = 0x3002
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE2 = 0x3002;
    
    /**
     * int-value = 12291<br>
     * hex-value = 0x3003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE3 = 0x3003;
    
    /**
     * int-value = 12292<br>
     * hex-value = 0x3004
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE4 = 0x3004;
    
    /**
     * int-value = 12293<br>
     * hex-value = 0x3005
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIP_PLANE5 = 0x3005;
    
    /**
     * int-value = 5120<br>
     * hex-value = 0x1400
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BYTE = 0x1400;
    
    /**
     * int-value = 5121<br>
     * hex-value = 0x1401
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNSIGNED_BYTE = 0x1401;
    
    /**
     * int-value = 5122<br>
     * hex-value = 0x1402
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SHORT = 0x1402;
    
    /**
     * int-value = 5123<br>
     * hex-value = 0x1403
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNSIGNED_SHORT = 0x1403;
    
    /**
     * int-value = 5124<br>
     * hex-value = 0x1404
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INT = 0x1404;
    
    /**
     * int-value = 5125<br>
     * hex-value = 0x1405
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNSIGNED_INT = 0x1405;
    
    /**
     * int-value = 5126<br>
     * hex-value = 0x1406
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FLOAT = 0x1406;
    
    /**
     * int-value = 5127<br>
     * hex-value = 0x1407
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_2_BYTES = 0x1407;
    
    /**
     * int-value = 5128<br>
     * hex-value = 0x1408
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_3_BYTES = 0x1408;
    
    /**
     * int-value = 5129<br>
     * hex-value = 0x1409
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_4_BYTES = 0x1409;
    
    /**
     * int-value = 5130<br>
     * hex-value = 0x140A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DOUBLE = 0x140A;
    
    /**
     * int-value = 0<br>
     * hex-value = 0x0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NONE = 0x0;
    
    /**
     * int-value = 1024<br>
     * hex-value = 0x400
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FRONT_LEFT = 0x400;
    
    /**
     * int-value = 1025<br>
     * hex-value = 0x401
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FRONT_RIGHT = 0x401;
    
    /**
     * int-value = 1026<br>
     * hex-value = 0x402
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BACK_LEFT = 0x402;
    
    /**
     * int-value = 1027<br>
     * hex-value = 0x403
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BACK_RIGHT = 0x403;
    
    /**
     * int-value = 1028<br>
     * hex-value = 0x404
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FRONT = 0x404;
    
    /**
     * int-value = 1029<br>
     * hex-value = 0x405
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BACK = 0x405;
    
    /**
     * int-value = 1030<br>
     * hex-value = 0x406
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LEFT = 0x406;
    
    /**
     * int-value = 1031<br>
     * hex-value = 0x407
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RIGHT = 0x407;
    
    /**
     * int-value = 1032<br>
     * hex-value = 0x408
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FRONT_AND_BACK = 0x408;
    
    /**
     * int-value = 1033<br>
     * hex-value = 0x409
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUX0 = 0x409;
    
    /**
     * int-value = 1034<br>
     * hex-value = 0x40A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUX1 = 0x40A;
    
    /**
     * int-value = 1035<br>
     * hex-value = 0x40B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUX2 = 0x40B;
    
    /**
     * int-value = 1036<br>
     * hex-value = 0x40C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUX3 = 0x40C;
    
    /**
     * int-value = 0<br>
     * hex-value = 0x0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NO_ERROR = 0x0;
    
    /**
     * int-value = 1280<br>
     * hex-value = 0x500
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INVALID_ENUM = 0x500;
    
    /**
     * int-value = 1281<br>
     * hex-value = 0x501
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INVALID_VALUE = 0x501;
    
    /**
     * int-value = 1282<br>
     * hex-value = 0x502
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INVALID_OPERATION = 0x502;
    
    /**
     * int-value = 1283<br>
     * hex-value = 0x503
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STACK_OVERFLOW = 0x503;
    
    /**
     * int-value = 1284<br>
     * hex-value = 0x504
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STACK_UNDERFLOW = 0x504;
    
    /**
     * int-value = 1285<br>
     * hex-value = 0x505
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OUT_OF_MEMORY = 0x505;
    
    /**
     * int-value = 1536<br>
     * hex-value = 0x600
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_2D = 0x600;
    
    /**
     * int-value = 1537<br>
     * hex-value = 0x601
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_3D = 0x601;
    
    /**
     * int-value = 1538<br>
     * hex-value = 0x602
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_3D_COLOR = 0x602;
    
    /**
     * int-value = 1539<br>
     * hex-value = 0x603
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_3D_COLOR_TEXTURE = 0x603;
    
    /**
     * int-value = 1540<br>
     * hex-value = 0x604
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_4D_COLOR_TEXTURE = 0x604;
    
    /**
     * int-value = 1792<br>
     * hex-value = 0x700
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PASS_THROUGH_TOKEN = 0x700;
    
    /**
     * int-value = 1793<br>
     * hex-value = 0x701
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_TOKEN = 0x701;
    
    /**
     * int-value = 1794<br>
     * hex-value = 0x702
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_TOKEN = 0x702;
    
    /**
     * int-value = 1795<br>
     * hex-value = 0x703
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_TOKEN = 0x703;
    
    /**
     * int-value = 1796<br>
     * hex-value = 0x704
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BITMAP_TOKEN = 0x704;
    
    /**
     * int-value = 1797<br>
     * hex-value = 0x705
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DRAW_PIXEL_TOKEN = 0x705;
    
    /**
     * int-value = 1798<br>
     * hex-value = 0x706
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COPY_PIXEL_TOKEN = 0x706;
    
    /**
     * int-value = 1799<br>
     * hex-value = 0x707
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_RESET_TOKEN = 0x707;
    
    /**
     * int-value = 2048<br>
     * hex-value = 0x800
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EXP = 0x800;
    
    /**
     * int-value = 2049<br>
     * hex-value = 0x801
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EXP2 = 0x801;
    
    /**
     * int-value = 2304<br>
     * hex-value = 0x900
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CW = 0x900;
    
    /**
     * int-value = 2305<br>
     * hex-value = 0x901
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CCW = 0x901;
    
    /**
     * int-value = 2560<br>
     * hex-value = 0xA00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COEFF = 0xA00;
    
    /**
     * int-value = 2561<br>
     * hex-value = 0xA01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ORDER = 0xA01;
    
    /**
     * int-value = 2562<br>
     * hex-value = 0xA02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DOMAIN = 0xA02;
    
    /**
     * int-value = 2816<br>
     * hex-value = 0xB00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_COLOR = 0xB00;
    
    /**
     * int-value = 2817<br>
     * hex-value = 0xB01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_INDEX = 0xB01;
    
    /**
     * int-value = 2818<br>
     * hex-value = 0xB02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_NORMAL = 0xB02;
    
    /**
     * int-value = 2819<br>
     * hex-value = 0xB03
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_TEXTURE_COORDS = 0xB03;
    
    /**
     * int-value = 2820<br>
     * hex-value = 0xB04
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_COLOR = 0xB04;
    
    /**
     * int-value = 2821<br>
     * hex-value = 0xB05
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_INDEX = 0xB05;
    
    /**
     * int-value = 2822<br>
     * hex-value = 0xB06
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 0xB06;
    
    /**
     * int-value = 2823<br>
     * hex-value = 0xB07
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_POSITION = 0xB07;
    
    /**
     * int-value = 2824<br>
     * hex-value = 0xB08
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 0xB08;
    
    /**
     * int-value = 2825<br>
     * hex-value = 0xB09
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CURRENT_RASTER_DISTANCE = 0xB09;
    
    /**
     * int-value = 2832<br>
     * hex-value = 0xB10
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_SMOOTH = 0xB10;
    
    /**
     * int-value = 2833<br>
     * hex-value = 0xB11
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_SIZE = 0xB11;
    
    /**
     * int-value = 2834<br>
     * hex-value = 0xB12
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_SIZE_RANGE = 0xB12;
    
    /**
     * int-value = 2835<br>
     * hex-value = 0xB13
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_SIZE_GRANULARITY = 0xB13;
    
    /**
     * int-value = 2848<br>
     * hex-value = 0xB20
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_SMOOTH = 0xB20;
    
    /**
     * int-value = 2849<br>
     * hex-value = 0xB21
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_WIDTH = 0xB21;
    
    /**
     * int-value = 2850<br>
     * hex-value = 0xB22
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_WIDTH_RANGE = 0xB22;
    
    /**
     * int-value = 2851<br>
     * hex-value = 0xB23
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_WIDTH_GRANULARITY = 0xB23;
    
    /**
     * int-value = 2852<br>
     * hex-value = 0xB24
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_STIPPLE = 0xB24;
    
    /**
     * int-value = 2853<br>
     * hex-value = 0xB25
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_STIPPLE_PATTERN = 0xB25;
    
    /**
     * int-value = 2854<br>
     * hex-value = 0xB26
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_STIPPLE_REPEAT = 0xB26;
    
    /**
     * int-value = 2864<br>
     * hex-value = 0xB30
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIST_MODE = 0xB30;
    
    /**
     * int-value = 2865<br>
     * hex-value = 0xB31
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_LIST_NESTING = 0xB31;
    
    /**
     * int-value = 2866<br>
     * hex-value = 0xB32
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIST_BASE = 0xB32;
    
    /**
     * int-value = 2867<br>
     * hex-value = 0xB33
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIST_INDEX = 0xB33;
    
    /**
     * int-value = 2880<br>
     * hex-value = 0xB40
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_MODE = 0xB40;
    
    /**
     * int-value = 2881<br>
     * hex-value = 0xB41
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_SMOOTH = 0xB41;
    
    /**
     * int-value = 2882<br>
     * hex-value = 0xB42
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_STIPPLE = 0xB42;
    
    /**
     * int-value = 2883<br>
     * hex-value = 0xB43
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EDGE_FLAG = 0xB43;
    
    /**
     * int-value = 2884<br>
     * hex-value = 0xB44
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CULL_FACE = 0xB44;
    
    /**
     * int-value = 2885<br>
     * hex-value = 0xB45
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CULL_FACE_MODE = 0xB45;
    
    /**
     * int-value = 2886<br>
     * hex-value = 0xB46
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FRONT_FACE = 0xB46;
    
    /**
     * int-value = 2896<br>
     * hex-value = 0xB50
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHTING = 0xB50;
    
    /**
     * int-value = 2897<br>
     * hex-value = 0xB51
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 0xB51;
    
    /**
     * int-value = 2898<br>
     * hex-value = 0xB52
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 0xB52;
    
    /**
     * int-value = 2899<br>
     * hex-value = 0xB53
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT_MODEL_AMBIENT = 0xB53;
    
    /**
     * int-value = 2900<br>
     * hex-value = 0xB54
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SHADE_MODEL = 0xB54;
    
    /**
     * int-value = 2901<br>
     * hex-value = 0xB55
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_MATERIAL_FACE = 0xB55;
    
    /**
     * int-value = 2902<br>
     * hex-value = 0xB56
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_MATERIAL_PARAMETER = 0xB56;
    
    /**
     * int-value = 2903<br>
     * hex-value = 0xB57
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_MATERIAL = 0xB57;
    
    /**
     * int-value = 2912<br>
     * hex-value = 0xB60
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG = 0xB60;
    
    /**
     * int-value = 2913<br>
     * hex-value = 0xB61
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_INDEX = 0xB61;
    
    /**
     * int-value = 2914<br>
     * hex-value = 0xB62
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_DENSITY = 0xB62;
    
    /**
     * int-value = 2915<br>
     * hex-value = 0xB63
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_START = 0xB63;
    
    /**
     * int-value = 2916<br>
     * hex-value = 0xB64
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_END = 0xB64;
    
    /**
     * int-value = 2917<br>
     * hex-value = 0xB65
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_MODE = 0xB65;
    
    /**
     * int-value = 2918<br>
     * hex-value = 0xB66
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_COLOR = 0xB66;
    
    /**
     * int-value = 2928<br>
     * hex-value = 0xB70
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_RANGE = 0xB70;
    
    /**
     * int-value = 2929<br>
     * hex-value = 0xB71
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_TEST = 0xB71;
    
    /**
     * int-value = 2930<br>
     * hex-value = 0xB72
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_WRITEMASK = 0xB72;
    
    /**
     * int-value = 2931<br>
     * hex-value = 0xB73
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_CLEAR_VALUE = 0xB73;
    
    /**
     * int-value = 2932<br>
     * hex-value = 0xB74
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_FUNC = 0xB74;
    
    /**
     * int-value = 2944<br>
     * hex-value = 0xB80
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_CLEAR_VALUE = 0xB80;
    
    /**
     * int-value = 2960<br>
     * hex-value = 0xB90
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_TEST = 0xB90;
    
    /**
     * int-value = 2961<br>
     * hex-value = 0xB91
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_CLEAR_VALUE = 0xB91;
    
    /**
     * int-value = 2962<br>
     * hex-value = 0xB92
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_FUNC = 0xB92;
    
    /**
     * int-value = 2963<br>
     * hex-value = 0xB93
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_VALUE_MASK = 0xB93;
    
    /**
     * int-value = 2964<br>
     * hex-value = 0xB94
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_FAIL = 0xB94;
    
    /**
     * int-value = 2965<br>
     * hex-value = 0xB95
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 0xB95;
    
    /**
     * int-value = 2966<br>
     * hex-value = 0xB96
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 0xB96;
    
    /**
     * int-value = 2967<br>
     * hex-value = 0xB97
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_REF = 0xB97;
    
    /**
     * int-value = 2968<br>
     * hex-value = 0xB98
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_WRITEMASK = 0xB98;
    
    /**
     * int-value = 2976<br>
     * hex-value = 0xBA0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MATRIX_MODE = 0xBA0;
    
    /**
     * int-value = 2977<br>
     * hex-value = 0xBA1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NORMALIZE = 0xBA1;
    
    /**
     * int-value = 2978<br>
     * hex-value = 0xBA2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VIEWPORT = 0xBA2;
    
    /**
     * int-value = 2979<br>
     * hex-value = 0xBA3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MODELVIEW_STACK_DEPTH = 0xBA3;
    
    /**
     * int-value = 2980<br>
     * hex-value = 0xBA4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PROJECTION_STACK_DEPTH = 0xBA4;
    
    /**
     * int-value = 2981<br>
     * hex-value = 0xBA5
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_STACK_DEPTH = 0xBA5;
    
    /**
     * int-value = 2982<br>
     * hex-value = 0xBA6
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MODELVIEW_MATRIX = 0xBA6;
    
    /**
     * int-value = 2983<br>
     * hex-value = 0xBA7
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PROJECTION_MATRIX = 0xBA7;
    
    /**
     * int-value = 2984<br>
     * hex-value = 0xBA8
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_MATRIX = 0xBA8;
    
    /**
     * int-value = 2992<br>
     * hex-value = 0xBB0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ATTRIB_STACK_DEPTH = 0xBB0;
    
    /**
     * int-value = 2993<br>
     * hex-value = 0xBB1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 0xBB1;
    
    /**
     * int-value = 3008<br>
     * hex-value = 0xBC0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_TEST = 0xBC0;
    
    /**
     * int-value = 3009<br>
     * hex-value = 0xBC1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_TEST_FUNC = 0xBC1;
    
    /**
     * int-value = 3010<br>
     * hex-value = 0xBC2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_TEST_REF = 0xBC2;
    
    /**
     * int-value = 3024<br>
     * hex-value = 0xBD0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DITHER = 0xBD0;
    
    /**
     * int-value = 3040<br>
     * hex-value = 0xBE0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLEND_DST = 0xBE0;
    
    /**
     * int-value = 3041<br>
     * hex-value = 0xBE1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLEND_SRC = 0xBE1;
    
    /**
     * int-value = 3042<br>
     * hex-value = 0xBE2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLEND = 0xBE2;
    
    /**
     * int-value = 3056<br>
     * hex-value = 0xBF0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LOGIC_OP_MODE = 0xBF0;
    
    /**
     * int-value = 3057<br>
     * hex-value = 0xBF1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_LOGIC_OP = 0xBF1;
    
    /**
     * int-value = 3058<br>
     * hex-value = 0xBF2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_LOGIC_OP = 0xBF2;
    
    /**
     * int-value = 3072<br>
     * hex-value = 0xC00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUX_BUFFERS = 0xC00;
    
    /**
     * int-value = 3073<br>
     * hex-value = 0xC01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DRAW_BUFFER = 0xC01;
    
    /**
     * int-value = 3074<br>
     * hex-value = 0xC02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_READ_BUFFER = 0xC02;
    
    /**
     * int-value = 3088<br>
     * hex-value = 0xC10
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SCISSOR_BOX = 0xC10;
    
    /**
     * int-value = 3089<br>
     * hex-value = 0xC11
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SCISSOR_TEST = 0xC11;
    
    /**
     * int-value = 3104<br>
     * hex-value = 0xC20
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_CLEAR_VALUE = 0xC20;
    
    /**
     * int-value = 3105<br>
     * hex-value = 0xC21
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_WRITEMASK = 0xC21;
    
    /**
     * int-value = 3106<br>
     * hex-value = 0xC22
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_CLEAR_VALUE = 0xC22;
    
    /**
     * int-value = 3107<br>
     * hex-value = 0xC23
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_WRITEMASK = 0xC23;
    
    /**
     * int-value = 3120<br>
     * hex-value = 0xC30
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_MODE = 0xC30;
    
    /**
     * int-value = 3121<br>
     * hex-value = 0xC31
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA_MODE = 0xC31;
    
    /**
     * int-value = 3122<br>
     * hex-value = 0xC32
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DOUBLEBUFFER = 0xC32;
    
    /**
     * int-value = 3123<br>
     * hex-value = 0xC33
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STEREO = 0xC33;
    
    /**
     * int-value = 3136<br>
     * hex-value = 0xC40
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RENDER_MODE = 0xC40;
    
    /**
     * int-value = 3152<br>
     * hex-value = 0xC50
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 0xC50;
    
    /**
     * int-value = 3153<br>
     * hex-value = 0xC51
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT_SMOOTH_HINT = 0xC51;
    
    /**
     * int-value = 3154<br>
     * hex-value = 0xC52
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE_SMOOTH_HINT = 0xC52;
    
    /**
     * int-value = 3155<br>
     * hex-value = 0xC53
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_SMOOTH_HINT = 0xC53;
    
    /**
     * int-value = 3156<br>
     * hex-value = 0xC54
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FOG_HINT = 0xC54;
    
    /**
     * int-value = 3168<br>
     * hex-value = 0xC60
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GEN_S = 0xC60;
    
    /**
     * int-value = 3169<br>
     * hex-value = 0xC61
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GEN_T = 0xC61;
    
    /**
     * int-value = 3170<br>
     * hex-value = 0xC62
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GEN_R = 0xC62;
    
    /**
     * int-value = 3171<br>
     * hex-value = 0xC63
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GEN_Q = 0xC63;
    
    /**
     * int-value = 3184<br>
     * hex-value = 0xC70
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_I = 0xC70;
    
    /**
     * int-value = 3185<br>
     * hex-value = 0xC71
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_S_TO_S = 0xC71;
    
    /**
     * int-value = 3186<br>
     * hex-value = 0xC72
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_R = 0xC72;
    
    /**
     * int-value = 3187<br>
     * hex-value = 0xC73
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_G = 0xC73;
    
    /**
     * int-value = 3188<br>
     * hex-value = 0xC74
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_B = 0xC74;
    
    /**
     * int-value = 3189<br>
     * hex-value = 0xC75
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_A = 0xC75;
    
    /**
     * int-value = 3190<br>
     * hex-value = 0xC76
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_R_TO_R = 0xC76;
    
    /**
     * int-value = 3191<br>
     * hex-value = 0xC77
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_G_TO_G = 0xC77;
    
    /**
     * int-value = 3192<br>
     * hex-value = 0xC78
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_B_TO_B = 0xC78;
    
    /**
     * int-value = 3193<br>
     * hex-value = 0xC79
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_A_TO_A = 0xC79;
    
    /**
     * int-value = 3248<br>
     * hex-value = 0xCB0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 0xCB0;
    
    /**
     * int-value = 3249<br>
     * hex-value = 0xCB1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 0xCB1;
    
    /**
     * int-value = 3250<br>
     * hex-value = 0xCB2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 0xCB2;
    
    /**
     * int-value = 3251<br>
     * hex-value = 0xCB3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 0xCB3;
    
    /**
     * int-value = 3252<br>
     * hex-value = 0xCB4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 0xCB4;
    
    /**
     * int-value = 3253<br>
     * hex-value = 0xCB5
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 0xCB5;
    
    /**
     * int-value = 3254<br>
     * hex-value = 0xCB6
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 0xCB6;
    
    /**
     * int-value = 3255<br>
     * hex-value = 0xCB7
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 0xCB7;
    
    /**
     * int-value = 3256<br>
     * hex-value = 0xCB8
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 0xCB8;
    
    /**
     * int-value = 3257<br>
     * hex-value = 0xCB9
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 0xCB9;
    
    /**
     * int-value = 3312<br>
     * hex-value = 0xCF0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_SWAP_BYTES = 0xCF0;
    
    /**
     * int-value = 3313<br>
     * hex-value = 0xCF1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_LSB_FIRST = 0xCF1;
    
    /**
     * int-value = 3314<br>
     * hex-value = 0xCF2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_ROW_LENGTH = 0xCF2;
    
    /**
     * int-value = 3315<br>
     * hex-value = 0xCF3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_SKIP_ROWS = 0xCF3;
    
    /**
     * int-value = 3316<br>
     * hex-value = 0xCF4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_SKIP_PIXELS = 0xCF4;
    
    /**
     * int-value = 3317<br>
     * hex-value = 0xCF5
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_UNPACK_ALIGNMENT = 0xCF5;
    
    /**
     * int-value = 3328<br>
     * hex-value = 0xD00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_SWAP_BYTES = 0xD00;
    
    /**
     * int-value = 3329<br>
     * hex-value = 0xD01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_LSB_FIRST = 0xD01;
    
    /**
     * int-value = 3330<br>
     * hex-value = 0xD02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_ROW_LENGTH = 0xD02;
    
    /**
     * int-value = 3331<br>
     * hex-value = 0xD03
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_SKIP_ROWS = 0xD03;
    
    /**
     * int-value = 3332<br>
     * hex-value = 0xD04
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_SKIP_PIXELS = 0xD04;
    
    /**
     * int-value = 3333<br>
     * hex-value = 0xD05
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PACK_ALIGNMENT = 0xD05;
    
    /**
     * int-value = 3344<br>
     * hex-value = 0xD10
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP_COLOR = 0xD10;
    
    /**
     * int-value = 3345<br>
     * hex-value = 0xD11
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP_STENCIL = 0xD11;
    
    /**
     * int-value = 3346<br>
     * hex-value = 0xD12
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_SHIFT = 0xD12;
    
    /**
     * int-value = 3347<br>
     * hex-value = 0xD13
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_OFFSET = 0xD13;
    
    /**
     * int-value = 3348<br>
     * hex-value = 0xD14
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RED_SCALE = 0xD14;
    
    /**
     * int-value = 3349<br>
     * hex-value = 0xD15
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RED_BIAS = 0xD15;
    
    /**
     * int-value = 3350<br>
     * hex-value = 0xD16
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ZOOM_X = 0xD16;
    
    /**
     * int-value = 3351<br>
     * hex-value = 0xD17
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ZOOM_Y = 0xD17;
    
    /**
     * int-value = 3352<br>
     * hex-value = 0xD18
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GREEN_SCALE = 0xD18;
    
    /**
     * int-value = 3353<br>
     * hex-value = 0xD19
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GREEN_BIAS = 0xD19;
    
    /**
     * int-value = 3354<br>
     * hex-value = 0xD1A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLUE_SCALE = 0xD1A;
    
    /**
     * int-value = 3355<br>
     * hex-value = 0xD1B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLUE_BIAS = 0xD1B;
    
    /**
     * int-value = 3356<br>
     * hex-value = 0xD1C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_SCALE = 0xD1C;
    
    /**
     * int-value = 3357<br>
     * hex-value = 0xD1D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_BIAS = 0xD1D;
    
    /**
     * int-value = 3358<br>
     * hex-value = 0xD1E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_SCALE = 0xD1E;
    
    /**
     * int-value = 3359<br>
     * hex-value = 0xD1F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_BIAS = 0xD1F;
    
    /**
     * int-value = 3376<br>
     * hex-value = 0xD30
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_EVAL_ORDER = 0xD30;
    
    /**
     * int-value = 3377<br>
     * hex-value = 0xD31
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_LIGHTS = 0xD31;
    
    /**
     * int-value = 3378<br>
     * hex-value = 0xD32
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_CLIP_PLANES = 0xD32;
    
    /**
     * int-value = 3379<br>
     * hex-value = 0xD33
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_TEXTURE_SIZE = 0xD33;
    
    /**
     * int-value = 3380<br>
     * hex-value = 0xD34
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_PIXEL_MAP_TABLE = 0xD34;
    
    /**
     * int-value = 3381<br>
     * hex-value = 0xD35
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_ATTRIB_STACK_DEPTH = 0xD35;
    
    /**
     * int-value = 3382<br>
     * hex-value = 0xD36
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 0xD36;
    
    /**
     * int-value = 3383<br>
     * hex-value = 0xD37
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_NAME_STACK_DEPTH = 0xD37;
    
    /**
     * int-value = 3384<br>
     * hex-value = 0xD38
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 0xD38;
    
    /**
     * int-value = 3385<br>
     * hex-value = 0xD39
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 0xD39;
    
    /**
     * int-value = 3386<br>
     * hex-value = 0xD3A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_VIEWPORT_DIMS = 0xD3A;
    
    /**
     * int-value = 3387<br>
     * hex-value = 0xD3B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0xD3B;
    
    /**
     * int-value = 3408<br>
     * hex-value = 0xD50
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SUBPIXEL_BITS = 0xD50;
    
    /**
     * int-value = 3409<br>
     * hex-value = 0xD51
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_BITS = 0xD51;
    
    /**
     * int-value = 3410<br>
     * hex-value = 0xD52
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RED_BITS = 0xD52;
    
    /**
     * int-value = 3411<br>
     * hex-value = 0xD53
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GREEN_BITS = 0xD53;
    
    /**
     * int-value = 3412<br>
     * hex-value = 0xD54
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLUE_BITS = 0xD54;
    
    /**
     * int-value = 3413<br>
     * hex-value = 0xD55
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA_BITS = 0xD55;
    
    /**
     * int-value = 3414<br>
     * hex-value = 0xD56
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_BITS = 0xD56;
    
    /**
     * int-value = 3415<br>
     * hex-value = 0xD57
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_BITS = 0xD57;
    
    /**
     * int-value = 3416<br>
     * hex-value = 0xD58
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_RED_BITS = 0xD58;
    
    /**
     * int-value = 3417<br>
     * hex-value = 0xD59
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_GREEN_BITS = 0xD59;
    
    /**
     * int-value = 3418<br>
     * hex-value = 0xD5A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_BLUE_BITS = 0xD5A;
    
    /**
     * int-value = 3419<br>
     * hex-value = 0xD5B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ACCUM_ALPHA_BITS = 0xD5B;
    
    /**
     * int-value = 3440<br>
     * hex-value = 0xD70
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NAME_STACK_DEPTH = 0xD70;
    
    /**
     * int-value = 3456<br>
     * hex-value = 0xD80
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AUTO_NORMAL = 0xD80;
    
    /**
     * int-value = 3472<br>
     * hex-value = 0xD90
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_COLOR_4 = 0xD90;
    
    /**
     * int-value = 3473<br>
     * hex-value = 0xD91
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_INDEX = 0xD91;
    
    /**
     * int-value = 3474<br>
     * hex-value = 0xD92
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_NORMAL = 0xD92;
    
    /**
     * int-value = 3475<br>
     * hex-value = 0xD93
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_TEXTURE_COORD_1 = 0xD93;
    
    /**
     * int-value = 3476<br>
     * hex-value = 0xD94
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_TEXTURE_COORD_2 = 0xD94;
    
    /**
     * int-value = 3477<br>
     * hex-value = 0xD95
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_TEXTURE_COORD_3 = 0xD95;
    
    /**
     * int-value = 3478<br>
     * hex-value = 0xD96
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_TEXTURE_COORD_4 = 0xD96;
    
    /**
     * int-value = 3479<br>
     * hex-value = 0xD97
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_VERTEX_3 = 0xD97;
    
    /**
     * int-value = 3480<br>
     * hex-value = 0xD98
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_VERTEX_4 = 0xD98;
    
    /**
     * int-value = 3504<br>
     * hex-value = 0xDB0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_COLOR_4 = 0xDB0;
    
    /**
     * int-value = 3505<br>
     * hex-value = 0xDB1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_INDEX = 0xDB1;
    
    /**
     * int-value = 3506<br>
     * hex-value = 0xDB2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_NORMAL = 0xDB2;
    
    /**
     * int-value = 3507<br>
     * hex-value = 0xDB3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_TEXTURE_COORD_1 = 0xDB3;
    
    /**
     * int-value = 3508<br>
     * hex-value = 0xDB4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_TEXTURE_COORD_2 = 0xDB4;
    
    /**
     * int-value = 3509<br>
     * hex-value = 0xDB5
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_TEXTURE_COORD_3 = 0xDB5;
    
    /**
     * int-value = 3510<br>
     * hex-value = 0xDB6
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_TEXTURE_COORD_4 = 0xDB6;
    
    /**
     * int-value = 3511<br>
     * hex-value = 0xDB7
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_VERTEX_3 = 0xDB7;
    
    /**
     * int-value = 3512<br>
     * hex-value = 0xDB8
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_VERTEX_4 = 0xDB8;
    
    /**
     * int-value = 3536<br>
     * hex-value = 0xDD0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_GRID_DOMAIN = 0xDD0;
    
    /**
     * int-value = 3537<br>
     * hex-value = 0xDD1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP1_GRID_SEGMENTS = 0xDD1;
    
    /**
     * int-value = 3538<br>
     * hex-value = 0xDD2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_GRID_DOMAIN = 0xDD2;
    
    /**
     * int-value = 3539<br>
     * hex-value = 0xDD3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MAP2_GRID_SEGMENTS = 0xDD3;
    
    /**
     * int-value = 3552<br>
     * hex-value = 0xDE0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_1D = 0xDE0;
    
    /**
     * int-value = 3553<br>
     * hex-value = 0xDE1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_2D = 0xDE1;
    
    /**
     * int-value = 3568<br>
     * hex-value = 0xDF0
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FEEDBACK_BUFFER_POINTER = 0xDF0;
    
    /**
     * int-value = 3569<br>
     * hex-value = 0xDF1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FEEDBACK_BUFFER_SIZE = 0xDF1;
    
    /**
     * int-value = 3570<br>
     * hex-value = 0xDF2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FEEDBACK_BUFFER_TYPE = 0xDF2;
    
    /**
     * int-value = 3571<br>
     * hex-value = 0xDF3
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SELECTION_BUFFER_POINTER = 0xDF3;
    
    /**
     * int-value = 3572<br>
     * hex-value = 0xDF4
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SELECTION_BUFFER_SIZE = 0xDF4;
    
    /**
     * int-value = 4096<br>
     * hex-value = 0x1000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_WIDTH = 0x1000;
    
    /**
     * int-value = 4097<br>
     * hex-value = 0x1001
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_HEIGHT = 0x1001;
    
    /**
     * int-value = 4099<br>
     * hex-value = 0x1003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 0x1003;
    
    /**
     * int-value = 4100<br>
     * hex-value = 0x1004
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BORDER_COLOR = 0x1004;
    
    /**
     * int-value = 4101<br>
     * hex-value = 0x1005
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BORDER = 0x1005;
    
    /**
     * int-value = 4352<br>
     * hex-value = 0x1100
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DONT_CARE = 0x1100;
    
    /**
     * int-value = 4353<br>
     * hex-value = 0x1101
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FASTEST = 0x1101;
    
    /**
     * int-value = 4354<br>
     * hex-value = 0x1102
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NICEST = 0x1102;
    
    /**
     * int-value = 16384<br>
     * hex-value = 0x4000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT0 = 0x4000;
    
    /**
     * int-value = 16385<br>
     * hex-value = 0x4001
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT1 = 0x4001;
    
    /**
     * int-value = 16386<br>
     * hex-value = 0x4002
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT2 = 0x4002;
    
    /**
     * int-value = 16387<br>
     * hex-value = 0x4003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT3 = 0x4003;
    
    /**
     * int-value = 16388<br>
     * hex-value = 0x4004
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT4 = 0x4004;
    
    /**
     * int-value = 16389<br>
     * hex-value = 0x4005
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT5 = 0x4005;
    
    /**
     * int-value = 16390<br>
     * hex-value = 0x4006
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT6 = 0x4006;
    
    /**
     * int-value = 16391<br>
     * hex-value = 0x4007
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LIGHT7 = 0x4007;
    
    /**
     * int-value = 4608<br>
     * hex-value = 0x1200
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AMBIENT = 0x1200;
    
    /**
     * int-value = 4609<br>
     * hex-value = 0x1201
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DIFFUSE = 0x1201;
    
    /**
     * int-value = 4610<br>
     * hex-value = 0x1202
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SPECULAR = 0x1202;
    
    /**
     * int-value = 4611<br>
     * hex-value = 0x1203
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POSITION = 0x1203;
    
    /**
     * int-value = 4612<br>
     * hex-value = 0x1204
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SPOT_DIRECTION = 0x1204;
    
    /**
     * int-value = 4613<br>
     * hex-value = 0x1205
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SPOT_EXPONENT = 0x1205;
    
    /**
     * int-value = 4614<br>
     * hex-value = 0x1206
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SPOT_CUTOFF = 0x1206;
    
    /**
     * int-value = 4615<br>
     * hex-value = 0x1207
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CONSTANT_ATTENUATION = 0x1207;
    
    /**
     * int-value = 4616<br>
     * hex-value = 0x1208
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINEAR_ATTENUATION = 0x1208;
    
    /**
     * int-value = 4617<br>
     * hex-value = 0x1209
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_QUADRATIC_ATTENUATION = 0x1209;
    
    /**
     * int-value = 4864<br>
     * hex-value = 0x1300
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COMPILE = 0x1300;
    
    /**
     * int-value = 4865<br>
     * hex-value = 0x1301
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COMPILE_AND_EXECUTE = 0x1301;
    
    /**
     * int-value = 5376<br>
     * hex-value = 0x1500
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLEAR = 0x1500;
    
    /**
     * int-value = 5377<br>
     * hex-value = 0x1501
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AND = 0x1501;
    
    /**
     * int-value = 5378<br>
     * hex-value = 0x1502
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AND_REVERSE = 0x1502;
    
    /**
     * int-value = 5379<br>
     * hex-value = 0x1503
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COPY = 0x1503;
    
    /**
     * int-value = 5380<br>
     * hex-value = 0x1504
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AND_INVERTED = 0x1504;
    
    /**
     * int-value = 5381<br>
     * hex-value = 0x1505
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NOOP = 0x1505;
    
    /**
     * int-value = 5382<br>
     * hex-value = 0x1506
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_XOR = 0x1506;
    
    /**
     * int-value = 5383<br>
     * hex-value = 0x1507
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OR = 0x1507;
    
    /**
     * int-value = 5384<br>
     * hex-value = 0x1508
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NOR = 0x1508;
    
    /**
     * int-value = 5385<br>
     * hex-value = 0x1509
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EQUIV = 0x1509;
    
    /**
     * int-value = 5386<br>
     * hex-value = 0x150A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INVERT = 0x150A;
    
    /**
     * int-value = 5387<br>
     * hex-value = 0x150B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OR_REVERSE = 0x150B;
    
    /**
     * int-value = 5388<br>
     * hex-value = 0x150C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COPY_INVERTED = 0x150C;
    
    /**
     * int-value = 5389<br>
     * hex-value = 0x150D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OR_INVERTED = 0x150D;
    
    /**
     * int-value = 5390<br>
     * hex-value = 0x150E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NAND = 0x150E;
    
    /**
     * int-value = 5391<br>
     * hex-value = 0x150F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SET = 0x150F;
    
    /**
     * int-value = 5632<br>
     * hex-value = 0x1600
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EMISSION = 0x1600;
    
    /**
     * int-value = 5633<br>
     * hex-value = 0x1601
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SHININESS = 0x1601;
    
    /**
     * int-value = 5634<br>
     * hex-value = 0x1602
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_AMBIENT_AND_DIFFUSE = 0x1602;
    
    /**
     * int-value = 5635<br>
     * hex-value = 0x1603
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_INDEXES = 0x1603;
    
    /**
     * int-value = 5888<br>
     * hex-value = 0x1700
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MODELVIEW = 0x1700;
    
    /**
     * int-value = 5889<br>
     * hex-value = 0x1701
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PROJECTION = 0x1701;
    
    /**
     * int-value = 5890<br>
     * hex-value = 0x1702
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE = 0x1702;
    
    /**
     * int-value = 6144<br>
     * hex-value = 0x1800
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR = 0x1800;
    
    /**
     * int-value = 6145<br>
     * hex-value = 0x1801
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH = 0x1801;
    
    /**
     * int-value = 6146<br>
     * hex-value = 0x1802
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL = 0x1802;
    
    /**
     * int-value = 6400<br>
     * hex-value = 0x1900
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_INDEX = 0x1900;
    
    /**
     * int-value = 6401<br>
     * hex-value = 0x1901
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_STENCIL_INDEX = 0x1901;
    
    /**
     * int-value = 6402<br>
     * hex-value = 0x1902
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DEPTH_COMPONENT = 0x1902;
    
    /**
     * int-value = 6403<br>
     * hex-value = 0x1903
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RED = 0x1903;
    
    /**
     * int-value = 6404<br>
     * hex-value = 0x1904
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_GREEN = 0x1904;
    
    /**
     * int-value = 6405<br>
     * hex-value = 0x1905
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BLUE = 0x1905;
    
    /**
     * int-value = 6406<br>
     * hex-value = 0x1906
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA = 0x1906;
    
    /**
     * int-value = 6407<br>
     * hex-value = 0x1907
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB = 0x1907;
    
    /**
     * int-value = 6408<br>
     * hex-value = 0x1908
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA = 0x1908;
    
    /**
     * int-value = 6409<br>
     * hex-value = 0x1909
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE = 0x1909;
    
    /**
     * int-value = 6410<br>
     * hex-value = 0x190A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE_ALPHA = 0x190A;
    
    /**
     * int-value = 6656<br>
     * hex-value = 0x1A00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_BITMAP = 0x1A00;
    
    /**
     * int-value = 6912<br>
     * hex-value = 0x1B00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POINT = 0x1B00;
    
    /**
     * int-value = 6913<br>
     * hex-value = 0x1B01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINE = 0x1B01;
    
    /**
     * int-value = 6914<br>
     * hex-value = 0x1B02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FILL = 0x1B02;
    
    /**
     * int-value = 7168<br>
     * hex-value = 0x1C00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RENDER = 0x1C00;
    
    /**
     * int-value = 7169<br>
     * hex-value = 0x1C01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FEEDBACK = 0x1C01;
    
    /**
     * int-value = 7170<br>
     * hex-value = 0x1C02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SELECT = 0x1C02;
    
    /**
     * int-value = 7424<br>
     * hex-value = 0x1D00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_FLAT = 0x1D00;
    
    /**
     * int-value = 7425<br>
     * hex-value = 0x1D01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SMOOTH = 0x1D01;
    
    /**
     * int-value = 7680<br>
     * hex-value = 0x1E00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_KEEP = 0x1E00;
    
    /**
     * int-value = 7681<br>
     * hex-value = 0x1E01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_REPLACE = 0x1E01;
    
    /**
     * int-value = 7682<br>
     * hex-value = 0x1E02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INCR = 0x1E02;
    
    /**
     * int-value = 7683<br>
     * hex-value = 0x1E03
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DECR = 0x1E03;
    
    /**
     * int-value = 7936<br>
     * hex-value = 0x1F00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VENDOR = 0x1F00;
    
    /**
     * int-value = 7937<br>
     * hex-value = 0x1F01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RENDERER = 0x1F01;
    
    /**
     * int-value = 7938<br>
     * hex-value = 0x1F02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERSION = 0x1F02;
    
    /**
     * int-value = 7939<br>
     * hex-value = 0x1F03
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EXTENSIONS = 0x1F03;
    
    /**
     * int-value = 8192<br>
     * hex-value = 0x2000
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_S = 0x2000;
    
    /**
     * int-value = 8193<br>
     * hex-value = 0x2001
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T = 0x2001;
    
    /**
     * int-value = 8194<br>
     * hex-value = 0x2002
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_R = 0x2002;
    
    /**
     * int-value = 8195<br>
     * hex-value = 0x2003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_Q = 0x2003;
    
    /**
     * int-value = 8448<br>
     * hex-value = 0x2100
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_MODULATE = 0x2100;
    
    /**
     * int-value = 8449<br>
     * hex-value = 0x2101
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_DECAL = 0x2101;
    
    /**
     * int-value = 8704<br>
     * hex-value = 0x2200
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_ENV_MODE = 0x2200;
    
    /**
     * int-value = 8705<br>
     * hex-value = 0x2201
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_ENV_COLOR = 0x2201;
    
    /**
     * int-value = 8960<br>
     * hex-value = 0x2300
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_ENV = 0x2300;
    
    /**
     * int-value = 9216<br>
     * hex-value = 0x2400
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EYE_LINEAR = 0x2400;
    
    /**
     * int-value = 9217<br>
     * hex-value = 0x2401
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OBJECT_LINEAR = 0x2401;
    
    /**
     * int-value = 9218<br>
     * hex-value = 0x2402
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_SPHERE_MAP = 0x2402;
    
    /**
     * int-value = 9472<br>
     * hex-value = 0x2500
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GEN_MODE = 0x2500;
    
    /**
     * int-value = 9473<br>
     * hex-value = 0x2501
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_OBJECT_PLANE = 0x2501;
    
    /**
     * int-value = 9474<br>
     * hex-value = 0x2502
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EYE_PLANE = 0x2502;
    
    /**
     * int-value = 9728<br>
     * hex-value = 0x2600
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NEAREST = 0x2600;
    
    /**
     * int-value = 9729<br>
     * hex-value = 0x2601
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINEAR = 0x2601;
    
    /**
     * int-value = 9984<br>
     * hex-value = 0x2700
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
    
    /**
     * int-value = 9985<br>
     * hex-value = 0x2701
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
    
    /**
     * int-value = 9986<br>
     * hex-value = 0x2702
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
    
    /**
     * int-value = 9987<br>
     * hex-value = 0x2703
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
    
    /**
     * int-value = 10240<br>
     * hex-value = 0x2800
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_MAG_FILTER = 0x2800;
    
    /**
     * int-value = 10241<br>
     * hex-value = 0x2801
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_MIN_FILTER = 0x2801;
    
    /**
     * int-value = 10242<br>
     * hex-value = 0x2802
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_WRAP_S = 0x2802;
    
    /**
     * int-value = 10243<br>
     * hex-value = 0x2803
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_WRAP_T = 0x2803;
    
    /**
     * int-value = 10496<br>
     * hex-value = 0x2900
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLAMP = 0x2900;
    
    /**
     * int-value = 10497<br>
     * hex-value = 0x2901
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_REPEAT = 0x2901;
    
    /**
     * int-value = 1<br>
     * hex-value = 0x1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIENT_PIXEL_STORE_BIT = 0x1;
    
    /**
     * int-value = 2<br>
     * hex-value = 0x2
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 0x2;
    
    /**
     * int-value = 4294967295<br>
     * hex-value = 0xFFFFFFFF
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALL_CLIENT_ATTRIB_BITS = 0xFFFFFFFF;
    
    /**
     * int-value = 32824<br>
     * hex-value = 0x8038
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
    
    /**
     * int-value = 10752<br>
     * hex-value = 0x2A00
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
    
    /**
     * int-value = 10753<br>
     * hex-value = 0x2A01
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_OFFSET_POINT = 0x2A01;
    
    /**
     * int-value = 10754<br>
     * hex-value = 0x2A02
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_OFFSET_LINE = 0x2A02;
    
    /**
     * int-value = 32823<br>
     * hex-value = 0x8037
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_POLYGON_OFFSET_FILL = 0x8037;
    
    /**
     * int-value = 32827<br>
     * hex-value = 0x803B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA4 = 0x803B;
    
    /**
     * int-value = 32828<br>
     * hex-value = 0x803C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA8 = 0x803C;
    
    /**
     * int-value = 32829<br>
     * hex-value = 0x803D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA12 = 0x803D;
    
    /**
     * int-value = 32830<br>
     * hex-value = 0x803E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_ALPHA16 = 0x803E;
    
    /**
     * int-value = 32831<br>
     * hex-value = 0x803F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE4 = 0x803F;
    
    /**
     * int-value = 32832<br>
     * hex-value = 0x8040
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE8 = 0x8040;
    
    /**
     * int-value = 32833<br>
     * hex-value = 0x8041
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE12 = 0x8041;
    
    /**
     * int-value = 32834<br>
     * hex-value = 0x8042
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE16 = 0x8042;
    
    /**
     * int-value = 32835<br>
     * hex-value = 0x8043
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE4_ALPHA4 = 0x8043;
    
    /**
     * int-value = 32836<br>
     * hex-value = 0x8044
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE6_ALPHA2 = 0x8044;
    
    /**
     * int-value = 32837<br>
     * hex-value = 0x8045
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE8_ALPHA8 = 0x8045;
    
    /**
     * int-value = 32838<br>
     * hex-value = 0x8046
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE12_ALPHA4 = 0x8046;
    
    /**
     * int-value = 32839<br>
     * hex-value = 0x8047
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE12_ALPHA12 = 0x8047;
    
    /**
     * int-value = 32840<br>
     * hex-value = 0x8048
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LUMINANCE16_ALPHA16 = 0x8048;
    
    /**
     * int-value = 32841<br>
     * hex-value = 0x8049
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INTENSITY = 0x8049;
    
    /**
     * int-value = 32842<br>
     * hex-value = 0x804A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INTENSITY4 = 0x804A;
    
    /**
     * int-value = 32843<br>
     * hex-value = 0x804B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INTENSITY8 = 0x804B;
    
    /**
     * int-value = 32844<br>
     * hex-value = 0x804C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INTENSITY12 = 0x804C;
    
    /**
     * int-value = 32845<br>
     * hex-value = 0x804D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INTENSITY16 = 0x804D;
    
    /**
     * int-value = 10768<br>
     * hex-value = 0x2A10
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_R3_G3_B2 = 0x2A10;
    
    /**
     * int-value = 32847<br>
     * hex-value = 0x804F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB4 = 0x804F;
    
    /**
     * int-value = 32848<br>
     * hex-value = 0x8050
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB5 = 0x8050;
    
    /**
     * int-value = 32849<br>
     * hex-value = 0x8051
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB8 = 0x8051;
    
    /**
     * int-value = 32850<br>
     * hex-value = 0x8052
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB10 = 0x8052;
    
    /**
     * int-value = 32851<br>
     * hex-value = 0x8053
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB12 = 0x8053;
    
    /**
     * int-value = 32852<br>
     * hex-value = 0x8054
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB16 = 0x8054;
    
    /**
     * int-value = 32853<br>
     * hex-value = 0x8055
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA2 = 0x8055;
    
    /**
     * int-value = 32854<br>
     * hex-value = 0x8056
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA4 = 0x8056;
    
    /**
     * int-value = 32855<br>
     * hex-value = 0x8057
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB5_A1 = 0x8057;
    
    /**
     * int-value = 32856<br>
     * hex-value = 0x8058
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA8 = 0x8058;
    
    /**
     * int-value = 32857<br>
     * hex-value = 0x8059
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGB10_A2 = 0x8059;
    
    /**
     * int-value = 32858<br>
     * hex-value = 0x805A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA12 = 0x805A;
    
    /**
     * int-value = 32859<br>
     * hex-value = 0x805B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_RGBA16 = 0x805B;
    
    /**
     * int-value = 32860<br>
     * hex-value = 0x805C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_RED_SIZE = 0x805C;
    
    /**
     * int-value = 32861<br>
     * hex-value = 0x805D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_GREEN_SIZE = 0x805D;
    
    /**
     * int-value = 32862<br>
     * hex-value = 0x805E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BLUE_SIZE = 0x805E;
    
    /**
     * int-value = 32863<br>
     * hex-value = 0x805F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_ALPHA_SIZE = 0x805F;
    
    /**
     * int-value = 32864<br>
     * hex-value = 0x8060
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 0x8060;
    
    /**
     * int-value = 32865<br>
     * hex-value = 0x8061
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_INTENSITY_SIZE = 0x8061;
    
    /**
     * int-value = 32867<br>
     * hex-value = 0x8063
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PROXY_TEXTURE_1D = 0x8063;
    
    /**
     * int-value = 32868<br>
     * hex-value = 0x8064
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_PROXY_TEXTURE_2D = 0x8064;
    
    /**
     * int-value = 32870<br>
     * hex-value = 0x8066
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_PRIORITY = 0x8066;
    
    /**
     * int-value = 32871<br>
     * hex-value = 0x8067
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_RESIDENT = 0x8067;
    
    /**
     * int-value = 32872<br>
     * hex-value = 0x8068
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BINDING_1D = 0x8068;
    
    /**
     * int-value = 32873<br>
     * hex-value = 0x8069
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_BINDING_2D = 0x8069;
    
    /**
     * int-value = 32884<br>
     * hex-value = 0x8074
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERTEX_ARRAY = 0x8074;
    
    /**
     * int-value = 32885<br>
     * hex-value = 0x8075
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NORMAL_ARRAY = 0x8075;
    
    /**
     * int-value = 32886<br>
     * hex-value = 0x8076
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_ARRAY = 0x8076;
    
    /**
     * int-value = 32887<br>
     * hex-value = 0x8077
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_ARRAY = 0x8077;
    
    /**
     * int-value = 32888<br>
     * hex-value = 0x8078
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COORD_ARRAY = 0x8078;
    
    /**
     * int-value = 32889<br>
     * hex-value = 0x8079
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EDGE_FLAG_ARRAY = 0x8079;
    
    /**
     * int-value = 32890<br>
     * hex-value = 0x807A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERTEX_ARRAY_SIZE = 0x807A;
    
    /**
     * int-value = 32891<br>
     * hex-value = 0x807B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERTEX_ARRAY_TYPE = 0x807B;
    
    /**
     * int-value = 32892<br>
     * hex-value = 0x807C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERTEX_ARRAY_STRIDE = 0x807C;
    
    /**
     * int-value = 32894<br>
     * hex-value = 0x807E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NORMAL_ARRAY_TYPE = 0x807E;
    
    /**
     * int-value = 32895<br>
     * hex-value = 0x807F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NORMAL_ARRAY_STRIDE = 0x807F;
    
    /**
     * int-value = 32897<br>
     * hex-value = 0x8081
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_ARRAY_SIZE = 0x8081;
    
    /**
     * int-value = 32898<br>
     * hex-value = 0x8082
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_ARRAY_TYPE = 0x8082;
    
    /**
     * int-value = 32899<br>
     * hex-value = 0x8083
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_ARRAY_STRIDE = 0x8083;
    
    /**
     * int-value = 32901<br>
     * hex-value = 0x8085
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_ARRAY_TYPE = 0x8085;
    
    /**
     * int-value = 32902<br>
     * hex-value = 0x8086
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_ARRAY_STRIDE = 0x8086;
    
    /**
     * int-value = 32904<br>
     * hex-value = 0x8088
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088;
    
    /**
     * int-value = 32905<br>
     * hex-value = 0x8089
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089;
    
    /**
     * int-value = 32906<br>
     * hex-value = 0x808A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808A;
    
    /**
     * int-value = 32908<br>
     * hex-value = 0x808C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 0x808C;
    
    /**
     * int-value = 32910<br>
     * hex-value = 0x808E
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_VERTEX_ARRAY_POINTER = 0x808E;
    
    /**
     * int-value = 32911<br>
     * hex-value = 0x808F
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_NORMAL_ARRAY_POINTER = 0x808F;
    
    /**
     * int-value = 32912<br>
     * hex-value = 0x8090
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_COLOR_ARRAY_POINTER = 0x8090;
    
    /**
     * int-value = 32913<br>
     * hex-value = 0x8091
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_INDEX_ARRAY_POINTER = 0x8091;
    
    /**
     * int-value = 32914<br>
     * hex-value = 0x8092
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;
    
    /**
     * int-value = 32915<br>
     * hex-value = 0x8093
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_EDGE_FLAG_ARRAY_POINTER = 0x8093;
    
    /**
     * int-value = 10784<br>
     * hex-value = 0x2A20
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_V2F = 0x2A20;
    
    /**
     * int-value = 10785<br>
     * hex-value = 0x2A21
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_V3F = 0x2A21;
    
    /**
     * int-value = 10786<br>
     * hex-value = 0x2A22
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_C4UB_V2F = 0x2A22;
    
    /**
     * int-value = 10787<br>
     * hex-value = 0x2A23
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_C4UB_V3F = 0x2A23;
    
    /**
     * int-value = 10788<br>
     * hex-value = 0x2A24
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_C3F_V3F = 0x2A24;
    
    /**
     * int-value = 10789<br>
     * hex-value = 0x2A25
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_N3F_V3F = 0x2A25;
    
    /**
     * int-value = 10790<br>
     * hex-value = 0x2A26
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_C4F_N3F_V3F = 0x2A26;
    
    /**
     * int-value = 10791<br>
     * hex-value = 0x2A27
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T2F_V3F = 0x2A27;
    
    /**
     * int-value = 10792<br>
     * hex-value = 0x2A28
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T4F_V4F = 0x2A28;
    
    /**
     * int-value = 10793<br>
     * hex-value = 0x2A29
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T2F_C4UB_V3F = 0x2A29;
    
    /**
     * int-value = 10794<br>
     * hex-value = 0x2A2A
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T2F_C3F_V3F = 0x2A2A;
    
    /**
     * int-value = 10795<br>
     * hex-value = 0x2A2B
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T2F_N3F_V3F = 0x2A2B;
    
    /**
     * int-value = 10796<br>
     * hex-value = 0x2A2C
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T2F_C4F_N3F_V3F = 0x2A2C;
    
    /**
     * int-value = 10797<br>
     * hex-value = 0x2A2D
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_T4F_C4F_N3F_V4F = 0x2A2D;
    
    /**
     * int-value = 3057<br>
     * hex-value = 0xBF1
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_LOGIC_OP = 0xBF1;
    
    /**
     * int-value = 4099<br>
     * hex-value = 0x1003
     * 
     * @since OpenGL 1.1
     */
    public static final int GL_TEXTURE_COMPONENTS = 0x1003;
    
    /**
     * int-value = 32874<br>
     * hex-value = 0x806A
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_BINDING_3D = 0x806A;
    
    /**
     * int-value = 32875<br>
     * hex-value = 0x806B
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_PACK_SKIP_IMAGES = 0x806B;
    
    /**
     * int-value = 32876<br>
     * hex-value = 0x806C
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_PACK_IMAGE_HEIGHT = 0x806C;
    
    /**
     * int-value = 32877<br>
     * hex-value = 0x806D
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNPACK_SKIP_IMAGES = 0x806D;
    
    /**
     * int-value = 32878<br>
     * hex-value = 0x806E
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNPACK_IMAGE_HEIGHT = 0x806E;
    
    /**
     * int-value = 32879<br>
     * hex-value = 0x806F
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_3D = 0x806F;
    
    /**
     * int-value = 32880<br>
     * hex-value = 0x8070
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_PROXY_TEXTURE_3D = 0x8070;
    
    /**
     * int-value = 32881<br>
     * hex-value = 0x8071
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_DEPTH = 0x8071;
    
    /**
     * int-value = 32882<br>
     * hex-value = 0x8072
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_WRAP_R = 0x8072;
    
    /**
     * int-value = 32883<br>
     * hex-value = 0x8073
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_MAX_3D_TEXTURE_SIZE = 0x8073;
    
    /**
     * int-value = 32992<br>
     * hex-value = 0x80E0
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_BGR = 0x80E0;
    
    /**
     * int-value = 32993<br>
     * hex-value = 0x80E1
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_BGRA = 0x80E1;
    
    /**
     * int-value = 32818<br>
     * hex-value = 0x8032
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_BYTE_3_3_2 = 0x8032;
    
    /**
     * int-value = 33634<br>
     * hex-value = 0x8362
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 0x8362;
    
    /**
     * int-value = 33635<br>
     * hex-value = 0x8363
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
    
    /**
     * int-value = 33636<br>
     * hex-value = 0x8364
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 0x8364;
    
    /**
     * int-value = 32819<br>
     * hex-value = 0x8033
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
    
    /**
     * int-value = 33637<br>
     * hex-value = 0x8365
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 0x8365;
    
    /**
     * int-value = 32820<br>
     * hex-value = 0x8034
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
    
    /**
     * int-value = 33638<br>
     * hex-value = 0x8366
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 0x8366;
    
    /**
     * int-value = 32821<br>
     * hex-value = 0x8035
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_INT_8_8_8_8 = 0x8035;
    
    /**
     * int-value = 33639<br>
     * hex-value = 0x8367
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 0x8367;
    
    /**
     * int-value = 32822<br>
     * hex-value = 0x8036
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_INT_10_10_10_2 = 0x8036;
    
    /**
     * int-value = 33640<br>
     * hex-value = 0x8368
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 0x8368;
    
    /**
     * int-value = 32826<br>
     * hex-value = 0x803A
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_RESCALE_NORMAL = 0x803A;
    
    /**
     * int-value = 33272<br>
     * hex-value = 0x81F8
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL = 0x81F8;
    
    /**
     * int-value = 33273<br>
     * hex-value = 0x81F9
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SINGLE_COLOR = 0x81F9;
    
    /**
     * int-value = 33274<br>
     * hex-value = 0x81FA
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SEPARATE_SPECULAR_COLOR = 0x81FA;
    
    /**
     * int-value = 33071<br>
     * hex-value = 0x812F
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_CLAMP_TO_EDGE = 0x812F;
    
    /**
     * int-value = 33082<br>
     * hex-value = 0x813A
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_MIN_LOD = 0x813A;
    
    /**
     * int-value = 33083<br>
     * hex-value = 0x813B
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_MAX_LOD = 0x813B;
    
    /**
     * int-value = 33084<br>
     * hex-value = 0x813C
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_BASE_LEVEL = 0x813C;
    
    /**
     * int-value = 33085<br>
     * hex-value = 0x813D
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_TEXTURE_MAX_LEVEL = 0x813D;
    
    /**
     * int-value = 33000<br>
     * hex-value = 0x80E8
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_MAX_ELEMENTS_VERTICES = 0x80E8;
    
    /**
     * int-value = 33001<br>
     * hex-value = 0x80E9
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_MAX_ELEMENTS_INDICES = 0x80E9;
    
    /**
     * int-value = 33901<br>
     * hex-value = 0x846D
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
    
    /**
     * int-value = 33902<br>
     * hex-value = 0x846E
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
    
    /**
     * int-value = 2834<br>
     * hex-value = 0xB12
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 0xB12;
    
    /**
     * int-value = 2835<br>
     * hex-value = 0xB13
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 0xB13;
    
    /**
     * int-value = 2850<br>
     * hex-value = 0xB22
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 0xB22;
    
    /**
     * int-value = 2851<br>
     * hex-value = 0xB23
     * 
     * @since OpenGL 1.2
     */
    public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 0xB23;
    
    /**
     * int-value = 33984<br>
     * hex-value = 0x84C0
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE0 = 0x84C0;
    
    /**
     * int-value = 33985<br>
     * hex-value = 0x84C1
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE1 = 0x84C1;
    
    /**
     * int-value = 33986<br>
     * hex-value = 0x84C2
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE2 = 0x84C2;
    
    /**
     * int-value = 33987<br>
     * hex-value = 0x84C3
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE3 = 0x84C3;
    
    /**
     * int-value = 33988<br>
     * hex-value = 0x84C4
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE4 = 0x84C4;
    
    /**
     * int-value = 33989<br>
     * hex-value = 0x84C5
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE5 = 0x84C5;
    
    /**
     * int-value = 33990<br>
     * hex-value = 0x84C6
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE6 = 0x84C6;
    
    /**
     * int-value = 33991<br>
     * hex-value = 0x84C7
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE7 = 0x84C7;
    
    /**
     * int-value = 33992<br>
     * hex-value = 0x84C8
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE8 = 0x84C8;
    
    /**
     * int-value = 33993<br>
     * hex-value = 0x84C9
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE9 = 0x84C9;
    
    /**
     * int-value = 33994<br>
     * hex-value = 0x84CA
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE10 = 0x84CA;
    
    /**
     * int-value = 33995<br>
     * hex-value = 0x84CB
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE11 = 0x84CB;
    
    /**
     * int-value = 33996<br>
     * hex-value = 0x84CC
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE12 = 0x84CC;
    
    /**
     * int-value = 33997<br>
     * hex-value = 0x84CD
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE13 = 0x84CD;
    
    /**
     * int-value = 33998<br>
     * hex-value = 0x84CE
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE14 = 0x84CE;
    
    /**
     * int-value = 33999<br>
     * hex-value = 0x84CF
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE15 = 0x84CF;
    
    /**
     * int-value = 34000<br>
     * hex-value = 0x84D0
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE16 = 0x84D0;
    
    /**
     * int-value = 34001<br>
     * hex-value = 0x84D1
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE17 = 0x84D1;
    
    /**
     * int-value = 34002<br>
     * hex-value = 0x84D2
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE18 = 0x84D2;
    
    /**
     * int-value = 34003<br>
     * hex-value = 0x84D3
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE19 = 0x84D3;
    
    /**
     * int-value = 34004<br>
     * hex-value = 0x84D4
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE20 = 0x84D4;
    
    /**
     * int-value = 34005<br>
     * hex-value = 0x84D5
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE21 = 0x84D5;
    
    /**
     * int-value = 34006<br>
     * hex-value = 0x84D6
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE22 = 0x84D6;
    
    /**
     * int-value = 34007<br>
     * hex-value = 0x84D7
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE23 = 0x84D7;
    
    /**
     * int-value = 34008<br>
     * hex-value = 0x84D8
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE24 = 0x84D8;
    
    /**
     * int-value = 34009<br>
     * hex-value = 0x84D9
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE25 = 0x84D9;
    
    /**
     * int-value = 34010<br>
     * hex-value = 0x84DA
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE26 = 0x84DA;
    
    /**
     * int-value = 34011<br>
     * hex-value = 0x84DB
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE27 = 0x84DB;
    
    /**
     * int-value = 34012<br>
     * hex-value = 0x84DC
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE28 = 0x84DC;
    
    /**
     * int-value = 34013<br>
     * hex-value = 0x84DD
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE29 = 0x84DD;
    
    /**
     * int-value = 34014<br>
     * hex-value = 0x84DE
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE30 = 0x84DE;
    
    /**
     * int-value = 34015<br>
     * hex-value = 0x84DF
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE31 = 0x84DF;
    
    /**
     * int-value = 34016<br>
     * hex-value = 0x84E0
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_ACTIVE_TEXTURE = 0x84E0;
    
    /**
     * int-value = 34017<br>
     * hex-value = 0x84E1
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_CLIENT_ACTIVE_TEXTURE = 0x84E1;
    
    /**
     * int-value = 34018<br>
     * hex-value = 0x84E2
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_MAX_TEXTURE_UNITS = 0x84E2;
    
    /**
     * int-value = 34065<br>
     * hex-value = 0x8511
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_NORMAL_MAP = 0x8511;
    
    /**
     * int-value = 34066<br>
     * hex-value = 0x8512
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_REFLECTION_MAP = 0x8512;
    
    /**
     * int-value = 34067<br>
     * hex-value = 0x8513
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP = 0x8513;
    
    /**
     * int-value = 34068<br>
     * hex-value = 0x8514
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
    
    /**
     * int-value = 34069<br>
     * hex-value = 0x8515
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
    
    /**
     * int-value = 34070<br>
     * hex-value = 0x8516
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
    
    /**
     * int-value = 34071<br>
     * hex-value = 0x8517
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
    
    /**
     * int-value = 34072<br>
     * hex-value = 0x8518
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
    
    /**
     * int-value = 34073<br>
     * hex-value = 0x8519
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
    
    /**
     * int-value = 34074<br>
     * hex-value = 0x851A
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
    
    /**
     * int-value = 34075<br>
     * hex-value = 0x851B
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_PROXY_TEXTURE_CUBE_MAP = 0x851B;
    
    /**
     * int-value = 34076<br>
     * hex-value = 0x851C
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
    
    /**
     * int-value = 34025<br>
     * hex-value = 0x84E9
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_ALPHA = 0x84E9;
    
    /**
     * int-value = 34026<br>
     * hex-value = 0x84EA
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_LUMINANCE = 0x84EA;
    
    /**
     * int-value = 34027<br>
     * hex-value = 0x84EB
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_LUMINANCE_ALPHA = 0x84EB;
    
    /**
     * int-value = 34028<br>
     * hex-value = 0x84EC
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_INTENSITY = 0x84EC;
    
    /**
     * int-value = 34029<br>
     * hex-value = 0x84ED
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_RGB = 0x84ED;
    
    /**
     * int-value = 34030<br>
     * hex-value = 0x84EE
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_RGBA = 0x84EE;
    
    /**
     * int-value = 34031<br>
     * hex-value = 0x84EF
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_COMPRESSION_HINT = 0x84EF;
    
    /**
     * int-value = 34464<br>
     * hex-value = 0x86A0
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 0x86A0;
    
    /**
     * int-value = 34465<br>
     * hex-value = 0x86A1
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TEXTURE_COMPRESSED = 0x86A1;
    
    /**
     * int-value = 34466<br>
     * hex-value = 0x86A2
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
    
    /**
     * int-value = 34467<br>
     * hex-value = 0x86A3
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
    
    /**
     * int-value = 32925<br>
     * hex-value = 0x809D
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_MULTISAMPLE = 0x809D;
    
    /**
     * int-value = 32926<br>
     * hex-value = 0x809E
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
    
    /**
     * int-value = 32927<br>
     * hex-value = 0x809F
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_ALPHA_TO_ONE = 0x809F;
    
    /**
     * int-value = 32928<br>
     * hex-value = 0x80A0
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_COVERAGE = 0x80A0;
    
    /**
     * int-value = 32936<br>
     * hex-value = 0x80A8
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_BUFFERS = 0x80A8;
    
    /**
     * int-value = 32937<br>
     * hex-value = 0x80A9
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLES = 0x80A9;
    
    /**
     * int-value = 32938<br>
     * hex-value = 0x80AA
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
    
    /**
     * int-value = 32939<br>
     * hex-value = 0x80AB
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
    
    /**
     * int-value = 536870912<br>
     * hex-value = 0x20000000
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_MULTISAMPLE_BIT = 0x20000000;
    
    /**
     * int-value = 34019<br>
     * hex-value = 0x84E3
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX = 0x84E3;
    
    /**
     * int-value = 34020<br>
     * hex-value = 0x84E4
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TRANSPOSE_PROJECTION_MATRIX = 0x84E4;
    
    /**
     * int-value = 34021<br>
     * hex-value = 0x84E5
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TRANSPOSE_TEXTURE_MATRIX = 0x84E5;
    
    /**
     * int-value = 34022<br>
     * hex-value = 0x84E6
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_TRANSPOSE_COLOR_MATRIX = 0x84E6;
    
    /**
     * int-value = 34160<br>
     * hex-value = 0x8570
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMBINE = 0x8570;
    
    /**
     * int-value = 34161<br>
     * hex-value = 0x8571
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMBINE_RGB = 0x8571;
    
    /**
     * int-value = 34162<br>
     * hex-value = 0x8572
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_COMBINE_ALPHA = 0x8572;
    
    /**
     * int-value = 34176<br>
     * hex-value = 0x8580
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE0_RGB = 0x8580;
    
    /**
     * int-value = 34177<br>
     * hex-value = 0x8581
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE1_RGB = 0x8581;
    
    /**
     * int-value = 34178<br>
     * hex-value = 0x8582
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE2_RGB = 0x8582;
    
    /**
     * int-value = 34184<br>
     * hex-value = 0x8588
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE0_ALPHA = 0x8588;
    
    /**
     * int-value = 34185<br>
     * hex-value = 0x8589
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE1_ALPHA = 0x8589;
    
    /**
     * int-value = 34186<br>
     * hex-value = 0x858A
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SOURCE2_ALPHA = 0x858A;
    
    /**
     * int-value = 34192<br>
     * hex-value = 0x8590
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND0_RGB = 0x8590;
    
    /**
     * int-value = 34193<br>
     * hex-value = 0x8591
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND1_RGB = 0x8591;
    
    /**
     * int-value = 34194<br>
     * hex-value = 0x8592
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND2_RGB = 0x8592;
    
    /**
     * int-value = 34200<br>
     * hex-value = 0x8598
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND0_ALPHA = 0x8598;
    
    /**
     * int-value = 34201<br>
     * hex-value = 0x8599
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND1_ALPHA = 0x8599;
    
    /**
     * int-value = 34202<br>
     * hex-value = 0x859A
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_OPERAND2_ALPHA = 0x859A;
    
    /**
     * int-value = 34163<br>
     * hex-value = 0x8573
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_RGB_SCALE = 0x8573;
    
    /**
     * int-value = 34164<br>
     * hex-value = 0x8574
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_ADD_SIGNED = 0x8574;
    
    /**
     * int-value = 34165<br>
     * hex-value = 0x8575
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_INTERPOLATE = 0x8575;
    
    /**
     * int-value = 34023<br>
     * hex-value = 0x84E7
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_SUBTRACT = 0x84E7;
    
    /**
     * int-value = 34166<br>
     * hex-value = 0x8576
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_CONSTANT = 0x8576;
    
    /**
     * int-value = 34167<br>
     * hex-value = 0x8577
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_PRIMARY_COLOR = 0x8577;
    
    /**
     * int-value = 34168<br>
     * hex-value = 0x8578
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_PREVIOUS = 0x8578;
    
    /**
     * int-value = 34478<br>
     * hex-value = 0x86AE
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_DOT3_RGB = 0x86AE;
    
    /**
     * int-value = 34479<br>
     * hex-value = 0x86AF
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_DOT3_RGBA = 0x86AF;
    
    /**
     * int-value = 33069<br>
     * hex-value = 0x812D
     * 
     * @since OpenGL 1.3
     */
    public static final int GL_CLAMP_TO_BORDER = 0x812D;
    
    /**
     * int-value = 33169<br>
     * hex-value = 0x8191
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_GENERATE_MIPMAP = 0x8191;
    
    /**
     * int-value = 33170<br>
     * hex-value = 0x8192
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_GENERATE_MIPMAP_HINT = 0x8192;
    
    /**
     * int-value = 33189<br>
     * hex-value = 0x81A5
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_DEPTH_COMPONENT16 = 0x81A5;
    
    /**
     * int-value = 33190<br>
     * hex-value = 0x81A6
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_DEPTH_COMPONENT24 = 0x81A6;
    
    /**
     * int-value = 33191<br>
     * hex-value = 0x81A7
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_DEPTH_COMPONENT32 = 0x81A7;
    
    /**
     * int-value = 34890<br>
     * hex-value = 0x884A
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_TEXTURE_DEPTH_SIZE = 0x884A;
    
    /**
     * int-value = 34891<br>
     * hex-value = 0x884B
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_DEPTH_TEXTURE_MODE = 0x884B;
    
    /**
     * int-value = 34892<br>
     * hex-value = 0x884C
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_TEXTURE_COMPARE_MODE = 0x884C;
    
    /**
     * int-value = 34893<br>
     * hex-value = 0x884D
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_TEXTURE_COMPARE_FUNC = 0x884D;
    
    /**
     * int-value = 34894<br>
     * hex-value = 0x884E
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_COMPARE_R_TO_TEXTURE = 0x884E;
    
    /**
     * int-value = 33872<br>
     * hex-value = 0x8450
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE_SOURCE = 0x8450;
    
    /**
     * int-value = 33873<br>
     * hex-value = 0x8451
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE = 0x8451;
    
    /**
     * int-value = 33874<br>
     * hex-value = 0x8452
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FRAGMENT_DEPTH = 0x8452;
    
    /**
     * int-value = 33875<br>
     * hex-value = 0x8453
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_CURRENT_FOG_COORDINATE = 0x8453;
    
    /**
     * int-value = 33876<br>
     * hex-value = 0x8454
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 0x8454;
    
    /**
     * int-value = 33877<br>
     * hex-value = 0x8455
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 0x8455;
    
    /**
     * int-value = 33878<br>
     * hex-value = 0x8456
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 0x8456;
    
    /**
     * int-value = 33879<br>
     * hex-value = 0x8457
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_FOG_COORDINATE_ARRAY = 0x8457;
    
    /**
     * int-value = 33062<br>
     * hex-value = 0x8126
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_POINT_SIZE_MIN = 0x8126;
    
    /**
     * int-value = 33063<br>
     * hex-value = 0x8127
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_POINT_SIZE_MAX = 0x8127;
    
    /**
     * int-value = 33064<br>
     * hex-value = 0x8128
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 0x8128;
    
    /**
     * int-value = 33065<br>
     * hex-value = 0x8129
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_POINT_DISTANCE_ATTENUATION = 0x8129;
    
    /**
     * int-value = 33880<br>
     * hex-value = 0x8458
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_COLOR_SUM = 0x8458;
    
    /**
     * int-value = 33881<br>
     * hex-value = 0x8459
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_CURRENT_SECONDARY_COLOR = 0x8459;
    
    /**
     * int-value = 33882<br>
     * hex-value = 0x845A
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 0x845A;
    
    /**
     * int-value = 33883<br>
     * hex-value = 0x845B
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 0x845B;
    
    /**
     * int-value = 33884<br>
     * hex-value = 0x845C
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 0x845C;
    
    /**
     * int-value = 33885<br>
     * hex-value = 0x845D
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 0x845D;
    
    /**
     * int-value = 33886<br>
     * hex-value = 0x845E
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_SECONDARY_COLOR_ARRAY = 0x845E;
    
    /**
     * int-value = 32968<br>
     * hex-value = 0x80C8
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_BLEND_DST_RGB = 0x80C8;
    
    /**
     * int-value = 32969<br>
     * hex-value = 0x80C9
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_BLEND_SRC_RGB = 0x80C9;
    
    /**
     * int-value = 32970<br>
     * hex-value = 0x80CA
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_BLEND_DST_ALPHA = 0x80CA;
    
    /**
     * int-value = 32971<br>
     * hex-value = 0x80CB
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_BLEND_SRC_ALPHA = 0x80CB;
    
    /**
     * int-value = 34055<br>
     * hex-value = 0x8507
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_INCR_WRAP = 0x8507;
    
    /**
     * int-value = 34056<br>
     * hex-value = 0x8508
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_DECR_WRAP = 0x8508;
    
    /**
     * int-value = 34048<br>
     * hex-value = 0x8500
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_TEXTURE_FILTER_CONTROL = 0x8500;
    
    /**
     * int-value = 34049<br>
     * hex-value = 0x8501
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_TEXTURE_LOD_BIAS = 0x8501;
    
    /**
     * int-value = 34045<br>
     * hex-value = 0x84FD
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_MAX_TEXTURE_LOD_BIAS = 0x84FD;
    
    /**
     * int-value = 33648<br>
     * hex-value = 0x8370
     * 
     * @since OpenGL 1.4
     */
    public static final int GL_MIRRORED_REPEAT = 0x8370;
    
    /**
     * int-value = 34962<br>
     * hex-value = 0x8892
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_ARRAY_BUFFER = 0x8892;
    
    /**
     * int-value = 34963<br>
     * hex-value = 0x8893
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
    
    /**
     * int-value = 34964<br>
     * hex-value = 0x8894
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_ARRAY_BUFFER_BINDING = 0x8894;
    
    /**
     * int-value = 34965<br>
     * hex-value = 0x8895
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
    
    /**
     * int-value = 34966<br>
     * hex-value = 0x8896
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;
    
    /**
     * int-value = 34967<br>
     * hex-value = 0x8897
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;
    
    /**
     * int-value = 34968<br>
     * hex-value = 0x8898
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;
    
    /**
     * int-value = 34969<br>
     * hex-value = 0x8899
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING = 0x8899;
    
    /**
     * int-value = 34970<br>
     * hex-value = 0x889A
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A;
    
    /**
     * int-value = 34971<br>
     * hex-value = 0x889B
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 0x889B;
    
    /**
     * int-value = 34972<br>
     * hex-value = 0x889C
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 0x889C;
    
    /**
     * int-value = 34973<br>
     * hex-value = 0x889D
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 0x889D;
    
    /**
     * int-value = 34974<br>
     * hex-value = 0x889E
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = 0x889E;
    
    /**
     * int-value = 34975<br>
     * hex-value = 0x889F
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;
    
    /**
     * int-value = 35040<br>
     * hex-value = 0x88E0
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STREAM_DRAW = 0x88E0;
    
    /**
     * int-value = 35041<br>
     * hex-value = 0x88E1
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STREAM_READ = 0x88E1;
    
    /**
     * int-value = 35042<br>
     * hex-value = 0x88E2
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STREAM_COPY = 0x88E2;
    
    /**
     * int-value = 35044<br>
     * hex-value = 0x88E4
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STATIC_DRAW = 0x88E4;
    
    /**
     * int-value = 35045<br>
     * hex-value = 0x88E5
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STATIC_READ = 0x88E5;
    
    /**
     * int-value = 35046<br>
     * hex-value = 0x88E6
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_STATIC_COPY = 0x88E6;
    
    /**
     * int-value = 35048<br>
     * hex-value = 0x88E8
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_DYNAMIC_DRAW = 0x88E8;
    
    /**
     * int-value = 35049<br>
     * hex-value = 0x88E9
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_DYNAMIC_READ = 0x88E9;
    
    /**
     * int-value = 35050<br>
     * hex-value = 0x88EA
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_DYNAMIC_COPY = 0x88EA;
    
    /**
     * int-value = 35000<br>
     * hex-value = 0x88B8
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_READ_ONLY = 0x88B8;
    
    /**
     * int-value = 35001<br>
     * hex-value = 0x88B9
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_WRITE_ONLY = 0x88B9;
    
    /**
     * int-value = 35002<br>
     * hex-value = 0x88BA
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_READ_WRITE = 0x88BA;
    
    /**
     * int-value = 34660<br>
     * hex-value = 0x8764
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_BUFFER_SIZE = 0x8764;
    
    /**
     * int-value = 34661<br>
     * hex-value = 0x8765
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_BUFFER_USAGE = 0x8765;
    
    /**
     * int-value = 35003<br>
     * hex-value = 0x88BB
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_BUFFER_ACCESS = 0x88BB;
    
    /**
     * int-value = 35004<br>
     * hex-value = 0x88BC
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_BUFFER_MAPPED = 0x88BC;
    
    /**
     * int-value = 35005<br>
     * hex-value = 0x88BD
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_BUFFER_MAP_POINTER = 0x88BD;
    
    /**
     * int-value = 33872<br>
     * hex-value = 0x8450
     * 
     * @since OpenGL 1.5
     */
    public static final int FOG_COORD_SRC = 0x8450;
    
    /**
     * int-value = 33873<br>
     * hex-value = 0x8451
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD = 0x8451;
    
    /**
     * int-value = 33875<br>
     * hex-value = 0x8453
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_CURRENT_FOG_COORD = 0x8453;
    
    /**
     * int-value = 33876<br>
     * hex-value = 0x8454
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD_ARRAY_TYPE = 0x8454;
    
    /**
     * int-value = 33877<br>
     * hex-value = 0x8455
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD_ARRAY_STRIDE = 0x8455;
    
    /**
     * int-value = 33878<br>
     * hex-value = 0x8456
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD_ARRAY_POINTER = 0x8456;
    
    /**
     * int-value = 33879<br>
     * hex-value = 0x8457
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD_ARRAY = 0x8457;
    
    /**
     * int-value = 34973<br>
     * hex-value = 0x889D
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 0x889D;
    
    /**
     * int-value = 34176<br>
     * hex-value = 0x8580
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC0_RGB = 0x8580;
    
    /**
     * int-value = 34177<br>
     * hex-value = 0x8581
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC1_RGB = 0x8581;
    
    /**
     * int-value = 34178<br>
     * hex-value = 0x8582
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC2_RGB = 0x8582;
    
    /**
     * int-value = 34184<br>
     * hex-value = 0x8588
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC0_ALPHA = 0x8588;
    
    /**
     * int-value = 34185<br>
     * hex-value = 0x8589
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC1_ALPHA = 0x8589;
    
    /**
     * int-value = 34186<br>
     * hex-value = 0x858A
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SRC2_ALPHA = 0x858A;
    
    /**
     * int-value = 35092<br>
     * hex-value = 0x8914
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_SAMPLES_PASSED = 0x8914;
    
    /**
     * int-value = 34916<br>
     * hex-value = 0x8864
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_QUERY_COUNTER_BITS = 0x8864;
    
    /**
     * int-value = 34917<br>
     * hex-value = 0x8865
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_CURRENT_QUERY = 0x8865;
    
    /**
     * int-value = 34918<br>
     * hex-value = 0x8866
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_QUERY_RESULT = 0x8866;
    
    /**
     * int-value = 34919<br>
     * hex-value = 0x8867
     * 
     * @since OpenGL 1.5
     */
    public static final int GL_QUERY_RESULT_AVAILABLE = 0x8867;
    
    /**
     * int-value = 35724<br>
     * hex-value = 0x8B8C
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
    
    /**
     * int-value = 35725<br>
     * hex-value = 0x8B8D
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_CURRENT_PROGRAM = 0x8B8D;
    
    /**
     * int-value = 35663<br>
     * hex-value = 0x8B4F
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SHADER_TYPE = 0x8B4F;
    
    /**
     * int-value = 35712<br>
     * hex-value = 0x8B80
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DELETE_STATUS = 0x8B80;
    
    /**
     * int-value = 35713<br>
     * hex-value = 0x8B81
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_COMPILE_STATUS = 0x8B81;
    
    /**
     * int-value = 35714<br>
     * hex-value = 0x8B82
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_LINK_STATUS = 0x8B82;
    
    /**
     * int-value = 35715<br>
     * hex-value = 0x8B83
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VALIDATE_STATUS = 0x8B83;
    
    /**
     * int-value = 35716<br>
     * hex-value = 0x8B84
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_INFO_LOG_LENGTH = 0x8B84;
    
    /**
     * int-value = 35717<br>
     * hex-value = 0x8B85
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_ATTACHED_SHADERS = 0x8B85;
    
    /**
     * int-value = 35718<br>
     * hex-value = 0x8B86
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_ACTIVE_UNIFORMS = 0x8B86;
    
    /**
     * int-value = 35719<br>
     * hex-value = 0x8B87
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
    
    /**
     * int-value = 35721<br>
     * hex-value = 0x8B89
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_ACTIVE_ATTRIBUTES = 0x8B89;
    
    /**
     * int-value = 35722<br>
     * hex-value = 0x8B8A
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
    
    /**
     * int-value = 35720<br>
     * hex-value = 0x8B88
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SHADER_SOURCE_LENGTH = 0x8B88;
    
    /**
     * int-value = 35656<br>
     * hex-value = 0x8B48
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SHADER_OBJECT = 0x8B48;
    
    /**
     * int-value = 35664<br>
     * hex-value = 0x8B50
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_VEC2 = 0x8B50;
    
    /**
     * int-value = 35665<br>
     * hex-value = 0x8B51
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_VEC3 = 0x8B51;
    
    /**
     * int-value = 35666<br>
     * hex-value = 0x8B52
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_VEC4 = 0x8B52;
    
    /**
     * int-value = 35667<br>
     * hex-value = 0x8B53
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_INT_VEC2 = 0x8B53;
    
    /**
     * int-value = 35668<br>
     * hex-value = 0x8B54
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_INT_VEC3 = 0x8B54;
    
    /**
     * int-value = 35669<br>
     * hex-value = 0x8B55
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_INT_VEC4 = 0x8B55;
    
    /**
     * int-value = 35670<br>
     * hex-value = 0x8B56
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BOOL = 0x8B56;
    
    /**
     * int-value = 35671<br>
     * hex-value = 0x8B57
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BOOL_VEC2 = 0x8B57;
    
    /**
     * int-value = 35672<br>
     * hex-value = 0x8B58
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BOOL_VEC3 = 0x8B58;
    
    /**
     * int-value = 35673<br>
     * hex-value = 0x8B59
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BOOL_VEC4 = 0x8B59;
    
    /**
     * int-value = 35674<br>
     * hex-value = 0x8B5A
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_MAT2 = 0x8B5A;
    
    /**
     * int-value = 35675<br>
     * hex-value = 0x8B5B
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_MAT3 = 0x8B5B;
    
    /**
     * int-value = 35676<br>
     * hex-value = 0x8B5C
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FLOAT_MAT4 = 0x8B5C;
    
    /**
     * int-value = 35677<br>
     * hex-value = 0x8B5D
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_1D = 0x8B5D;
    
    /**
     * int-value = 35678<br>
     * hex-value = 0x8B5E
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_2D = 0x8B5E;
    
    /**
     * int-value = 35679<br>
     * hex-value = 0x8B5F
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_3D = 0x8B5F;
    
    /**
     * int-value = 35680<br>
     * hex-value = 0x8B60
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_CUBE = 0x8B60;
    
    /**
     * int-value = 35681<br>
     * hex-value = 0x8B61
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_1D_SHADOW = 0x8B61;
    
    /**
     * int-value = 35682<br>
     * hex-value = 0x8B62
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_SAMPLER_2D_SHADOW = 0x8B62;
    
    /**
     * int-value = 35633<br>
     * hex-value = 0x8B31
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_SHADER = 0x8B31;
    
    /**
     * int-value = 35658<br>
     * hex-value = 0x8B4A
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 0x8B4A;
    
    /**
     * int-value = 35659<br>
     * hex-value = 0x8B4B
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_VARYING_FLOATS = 0x8B4B;
    
    /**
     * int-value = 34921<br>
     * hex-value = 0x8869
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_VERTEX_ATTRIBS = 0x8869;
    
    /**
     * int-value = 34930<br>
     * hex-value = 0x8872
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
    
    /**
     * int-value = 35660<br>
     * hex-value = 0x8B4C
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
    
    /**
     * int-value = 35661<br>
     * hex-value = 0x8B4D
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
    
    /**
     * int-value = 34929<br>
     * hex-value = 0x8871
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_TEXTURE_COORDS = 0x8871;
    
    /**
     * int-value = 34370<br>
     * hex-value = 0x8642
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;
    
    /**
     * int-value = 34371<br>
     * hex-value = 0x8643
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE = 0x8643;
    
    /**
     * int-value = 34338<br>
     * hex-value = 0x8622
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
    
    /**
     * int-value = 34339<br>
     * hex-value = 0x8623
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
    
    /**
     * int-value = 34340<br>
     * hex-value = 0x8624
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
    
    /**
     * int-value = 34341<br>
     * hex-value = 0x8625
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
    
    /**
     * int-value = 34922<br>
     * hex-value = 0x886A
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
    
    /**
     * int-value = 34342<br>
     * hex-value = 0x8626
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_CURRENT_VERTEX_ATTRIB = 0x8626;
    
    /**
     * int-value = 34373<br>
     * hex-value = 0x8645
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
    
    /**
     * int-value = 35632<br>
     * hex-value = 0x8B30
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FRAGMENT_SHADER = 0x8B30;
    
    /**
     * int-value = 35657<br>
     * hex-value = 0x8B49
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;
    
    /**
     * int-value = 35723<br>
     * hex-value = 0x8B8B
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B;
    
    /**
     * int-value = 34852<br>
     * hex-value = 0x8824
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_MAX_DRAW_BUFFERS = 0x8824;
    
    /**
     * int-value = 34853<br>
     * hex-value = 0x8825
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER0 = 0x8825;
    
    /**
     * int-value = 34854<br>
     * hex-value = 0x8826
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER1 = 0x8826;
    
    /**
     * int-value = 34855<br>
     * hex-value = 0x8827
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER2 = 0x8827;
    
    /**
     * int-value = 34856<br>
     * hex-value = 0x8828
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER3 = 0x8828;
    
    /**
     * int-value = 34857<br>
     * hex-value = 0x8829
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER4 = 0x8829;
    
    /**
     * int-value = 34858<br>
     * hex-value = 0x882A
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER5 = 0x882A;
    
    /**
     * int-value = 34859<br>
     * hex-value = 0x882B
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER6 = 0x882B;
    
    /**
     * int-value = 34860<br>
     * hex-value = 0x882C
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER7 = 0x882C;
    
    /**
     * int-value = 34861<br>
     * hex-value = 0x882D
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER8 = 0x882D;
    
    /**
     * int-value = 34862<br>
     * hex-value = 0x882E
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER9 = 0x882E;
    
    /**
     * int-value = 34863<br>
     * hex-value = 0x882F
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER10 = 0x882F;
    
    /**
     * int-value = 34864<br>
     * hex-value = 0x8830
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER11 = 0x8830;
    
    /**
     * int-value = 34865<br>
     * hex-value = 0x8831
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER12 = 0x8831;
    
    /**
     * int-value = 34866<br>
     * hex-value = 0x8832
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER13 = 0x8832;
    
    /**
     * int-value = 34867<br>
     * hex-value = 0x8833
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER14 = 0x8833;
    
    /**
     * int-value = 34868<br>
     * hex-value = 0x8834
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_DRAW_BUFFER15 = 0x8834;
    
    /**
     * int-value = 34913<br>
     * hex-value = 0x8861
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_POINT_SPRITE = 0x8861;
    
    /**
     * int-value = 34914<br>
     * hex-value = 0x8862
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_COORD_REPLACE = 0x8862;
    
    /**
     * int-value = 36000<br>
     * hex-value = 0x8CA0
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 0x8CA0;
    
    /**
     * int-value = 36001<br>
     * hex-value = 0x8CA1
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_LOWER_LEFT = 0x8CA1;
    
    /**
     * int-value = 36002<br>
     * hex-value = 0x8CA2
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_UPPER_LEFT = 0x8CA2;
    
    /**
     * int-value = 34816<br>
     * hex-value = 0x8800
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_FUNC = 0x8800;
    
    /**
     * int-value = 34817<br>
     * hex-value = 0x8801
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_FAIL = 0x8801;
    
    /**
     * int-value = 34818<br>
     * hex-value = 0x8802
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
    
    /**
     * int-value = 34819<br>
     * hex-value = 0x8803
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
    
    /**
     * int-value = 36003<br>
     * hex-value = 0x8CA3
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_REF = 0x8CA3;
    
    /**
     * int-value = 36004<br>
     * hex-value = 0x8CA4
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;
    
    /**
     * int-value = 36005<br>
     * hex-value = 0x8CA5
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_STENCIL_BACK_WRITEMASK = 0x8CA5;
    
    /**
     * int-value = 32777<br>
     * hex-value = 0x8009
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BLEND_EQUATION_RGB = 0x8009;
    
    /**
     * int-value = 34877<br>
     * hex-value = 0x883D
     * 
     * @since OpenGL 2.0
     */
    public static final int GL_BLEND_EQUATION_ALPHA = 0x883D;
    
    /**
     * int-value = 35685<br>
     * hex-value = 0x8B65
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT2x3 = 0x8B65;
    
    /**
     * int-value = 35686<br>
     * hex-value = 0x8B66
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT2x4 = 0x8B66;
    
    /**
     * int-value = 35687<br>
     * hex-value = 0x8B67
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT3x2 = 0x8B67;
    
    /**
     * int-value = 35688<br>
     * hex-value = 0x8B68
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT3x4 = 0x8B68;
    
    /**
     * int-value = 35689<br>
     * hex-value = 0x8B69
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT4x2 = 0x8B69;
    
    /**
     * int-value = 35690<br>
     * hex-value = 0x8B6A
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_FLOAT_MAT4x3 = 0x8B6A;
    
    /**
     * int-value = 35051<br>
     * hex-value = 0x88EB
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_PIXEL_PACK_BUFFER = 0x88EB;
    
    /**
     * int-value = 35052<br>
     * hex-value = 0x88EC
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_PIXEL_UNPACK_BUFFER = 0x88EC;
    
    /**
     * int-value = 35053<br>
     * hex-value = 0x88ED
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED;
    
    /**
     * int-value = 35055<br>
     * hex-value = 0x88EF
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF;
    
    /**
     * int-value = 35904<br>
     * hex-value = 0x8C40
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SRGB = 0x8C40;
    
    /**
     * int-value = 35905<br>
     * hex-value = 0x8C41
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SRGB8 = 0x8C41;
    
    /**
     * int-value = 35906<br>
     * hex-value = 0x8C42
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SRGB_ALPHA = 0x8C42;
    
    /**
     * int-value = 35907<br>
     * hex-value = 0x8C43
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SRGB8_ALPHA8 = 0x8C43;
    
    /**
     * int-value = 35908<br>
     * hex-value = 0x8C44
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SLUMINANCE_ALPHA = 0x8C44;
    
    /**
     * int-value = 35909<br>
     * hex-value = 0x8C45
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SLUMINANCE8_ALPHA8 = 0x8C45;
    
    /**
     * int-value = 35910<br>
     * hex-value = 0x8C46
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SLUMINANCE = 0x8C46;
    
    /**
     * int-value = 35911<br>
     * hex-value = 0x8C47
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_SLUMINANCE8 = 0x8C47;
    
    /**
     * int-value = 35912<br>
     * hex-value = 0x8C48
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_COMPRESSED_SRGB = 0x8C48;
    
    /**
     * int-value = 35913<br>
     * hex-value = 0x8C49
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_COMPRESSED_SRGB_ALPHA = 0x8C49;
    
    /**
     * int-value = 35914<br>
     * hex-value = 0x8C4A
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_COMPRESSED_SLUMINANCE = 0x8C4A;
    
    /**
     * int-value = 35915<br>
     * hex-value = 0x8C4B
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = 0x8C4B;
    
    /**
     * int-value = 33887<br>
     * hex-value = 0x845F
     * 
     * @since OpenGL 2.1
     */
    public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = 0x845F;
    
    /**
     * int-value = 33776<br>
     * hex-value = 0x83F0
     * 
     * @since EXTTextureCompressionS3TC
     */
    public static final int GL_COMPRESSED_RGB_S3TC_DXT1_EXT = 0x83F0;
    
    /**
     * int-value = 33777<br>
     * hex-value = 0x83F1
     * 
     * @since EXTTextureCompressionS3TC
     */
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 0x83F1;
    
    /**
     * int-value = 33778<br>
     * hex-value = 0x83F2
     * 
     * @since EXTTextureCompressionS3TC
     */
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83F2;
    
    /**
     * int-value = 33779<br>
     * hex-value = 0x83F3
     * 
     * @since EXTTextureCompressionS3TC
     */
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83F3;
    
    /**
     * int-value = 34160<br>
     * hex-value = 0x8570
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_COMBINE_EXT = 0x8570;
    
    /**
     * int-value = 34161<br>
     * hex-value = 0x8571
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_COMBINE_RGB_EXT = 0x8571;
    
    /**
     * int-value = 34162<br>
     * hex-value = 0x8572
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_COMBINE_ALPHA_EXT = 0x8572;
    
    /**
     * int-value = 34176<br>
     * hex-value = 0x8580
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE0_RGB_EXT = 0x8580;
    
    /**
     * int-value = 34177<br>
     * hex-value = 0x8581
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE1_RGB_EXT = 0x8581;
    
    /**
     * int-value = 34178<br>
     * hex-value = 0x8582
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE2_RGB_EXT = 0x8582;
    
    /**
     * int-value = 34184<br>
     * hex-value = 0x8588
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE0_ALPHA_EXT = 0x8588;
    
    /**
     * int-value = 34185<br>
     * hex-value = 0x8589
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE1_ALPHA_EXT = 0x8589;
    
    /**
     * int-value = 34186<br>
     * hex-value = 0x858A
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_SOURCE2_ALPHA_EXT = 0x858A;
    
    /**
     * int-value = 34192<br>
     * hex-value = 0x8590
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND0_RGB_EXT = 0x8590;
    
    /**
     * int-value = 34193<br>
     * hex-value = 0x8591
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND1_RGB_EXT = 0x8591;
    
    /**
     * int-value = 34194<br>
     * hex-value = 0x8592
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND2_RGB_EXT = 0x8592;
    
    /**
     * int-value = 34200<br>
     * hex-value = 0x8598
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND0_ALPHA_EXT = 0x8598;
    
    /**
     * int-value = 34201<br>
     * hex-value = 0x8599
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND1_ALPHA_EXT = 0x8599;
    
    /**
     * int-value = 34202<br>
     * hex-value = 0x859A
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_OPERAND2_ALPHA_EXT = 0x859A;
    
    /**
     * int-value = 34163<br>
     * hex-value = 0x8573
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_RGB_SCALE_EXT = 0x8573;
    
    /**
     * int-value = 34164<br>
     * hex-value = 0x8574
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_ADD_SIGNED_EXT = 0x8574;
    
    /**
     * int-value = 34165<br>
     * hex-value = 0x8575
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_INTERPOLATE_EXT = 0x8575;
    
    /**
     * int-value = 34166<br>
     * hex-value = 0x8576
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_CONSTANT_EXT = 0x8576;
    
    /**
     * int-value = 34167<br>
     * hex-value = 0x8577
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_PRIMARY_COLOR_EXT = 0x8577;
    
    /**
     * int-value = 34168<br>
     * hex-value = 0x8578
     * 
     * @since EXTTextureEnvCombine
     */
    public static final int GL_PREVIOUS_EXT = 0x8578;
    
    /**
     * int-value = 34160<br>
     * hex-value = 0x8570
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_COMBINE_ARB = 0x8570;
    
    /**
     * int-value = 34161<br>
     * hex-value = 0x8571
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_COMBINE_RGB_ARB = 0x8571;
    
    /**
     * int-value = 34162<br>
     * hex-value = 0x8572
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_COMBINE_ALPHA_ARB = 0x8572;
    
    /**
     * int-value = 34176<br>
     * hex-value = 0x8580
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE0_RGB_ARB = 0x8580;
    
    /**
     * int-value = 34177<br>
     * hex-value = 0x8581
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE1_RGB_ARB = 0x8581;
    
    /**
     * int-value = 34178<br>
     * hex-value = 0x8582
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE2_RGB_ARB = 0x8582;
    
    /**
     * int-value = 34184<br>
     * hex-value = 0x8588
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE0_ALPHA_ARB = 0x8588;
    
    /**
     * int-value = 34185<br>
     * hex-value = 0x8589
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE1_ALPHA_ARB = 0x8589;
    
    /**
     * int-value = 34186<br>
     * hex-value = 0x858A
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SOURCE2_ALPHA_ARB = 0x858A;
    
    /**
     * int-value = 34192<br>
     * hex-value = 0x8590
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND0_RGB_ARB = 0x8590;
    
    /**
     * int-value = 34193<br>
     * hex-value = 0x8591
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND1_RGB_ARB = 0x8591;
    
    /**
     * int-value = 34194<br>
     * hex-value = 0x8592
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND2_RGB_ARB = 0x8592;
    
    /**
     * int-value = 34200<br>
     * hex-value = 0x8598
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND0_ALPHA_ARB = 0x8598;
    
    /**
     * int-value = 34201<br>
     * hex-value = 0x8599
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND1_ALPHA_ARB = 0x8599;
    
    /**
     * int-value = 34202<br>
     * hex-value = 0x859A
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_OPERAND2_ALPHA_ARB = 0x859A;
    
    /**
     * int-value = 34163<br>
     * hex-value = 0x8573
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_RGB_SCALE_ARB = 0x8573;
    
    /**
     * int-value = 34164<br>
     * hex-value = 0x8574
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_ADD_SIGNED_ARB = 0x8574;
    
    /**
     * int-value = 34165<br>
     * hex-value = 0x8575
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_INTERPOLATE_ARB = 0x8575;
    
    /**
     * int-value = 34023<br>
     * hex-value = 0x84E7
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_SUBTRACT_ARB = 0x84E7;
    
    /**
     * int-value = 34166<br>
     * hex-value = 0x8576
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_CONSTANT_ARB = 0x8576;
    
    /**
     * int-value = 34167<br>
     * hex-value = 0x8577
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_PRIMARY_COLOR_ARB = 0x8577;
    
    /**
     * int-value = 34168<br>
     * hex-value = 0x8578
     * 
     * @since ARBTextureEnvCombine
     */
    public static final int GL_PREVIOUS_ARB = 0x8578;
}
