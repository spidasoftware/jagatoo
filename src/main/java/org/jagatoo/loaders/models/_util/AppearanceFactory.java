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
package org.jagatoo.loaders.models._util;

import java.io.InputStream;
import java.net.URL;

import org.jagatoo.datatypes.NamedObject;
import org.jagatoo.loaders.textures.AbstractTexture;
import org.jagatoo.loaders.textures.AbstractTextureImage;
import org.jagatoo.opengl.enums.BlendFunction;
import org.jagatoo.opengl.enums.BlendMode;
import org.jagatoo.opengl.enums.ColorTarget;
import org.jagatoo.opengl.enums.DrawMode;
import org.jagatoo.opengl.enums.FaceCullMode;
import org.jagatoo.opengl.enums.PerspectiveCorrectionMode;
import org.jagatoo.opengl.enums.ShadeModel;
import org.jagatoo.opengl.enums.TestFunction;
import org.jagatoo.opengl.enums.TexCoordGenMode;
import org.jagatoo.opengl.enums.TextureBoundaryMode;
import org.jagatoo.opengl.enums.TextureCombineFunction;
import org.jagatoo.opengl.enums.TextureCombineMode;
import org.jagatoo.opengl.enums.TextureCombineSource;
import org.jagatoo.opengl.enums.CompareFunction;
import org.jagatoo.opengl.enums.TextureCompareMode;
import org.jagatoo.opengl.enums.TextureImageFormat;
import org.jagatoo.opengl.enums.TextureMagFilter;
import org.jagatoo.opengl.enums.TextureMinFilter;
import org.jagatoo.opengl.enums.TextureMode;
import org.openmali.vecmath2.Matrix4f;

/**
 * Insert type comment here.
 * 
 * @author Marvin Froehlich (aka Qudus)
 */
public interface AppearanceFactory
{
    public static final int APP_FLAG_STATIC = 1;
    
    public abstract NamedObject createTransparencyAttributes( String name );
    
    public abstract void setTransparencyAttribsSourceBlendFunc( NamedObject transpAttribs, BlendFunction srcBlendFunc );
    
    public abstract void setTransparencyAttribsDestBlendFunc( NamedObject transpAttribs, BlendFunction dstBlendFunc );
    
    public abstract void setTransparencyAttribsBlendMode( NamedObject transpAttribs, BlendMode blendMode );
    
    public abstract void setTransparencyAttribsTransparency( NamedObject transpAttribs, float transparency );
    
    public abstract void setTransparencyAttribsSortingEnabled( NamedObject transpAttribs, boolean sortingEnabled );
    
    public abstract void applyTransparancyAttributes( NamedObject transpAttribs, NamedObject appearance );
    
    
    
    public abstract NamedObject createMaterial( String name );
    
    public abstract void setMaterialColorTarget( NamedObject material, ColorTarget colorTarget );
    
    public abstract void setMaterialAmbientColor( NamedObject material, float r, float g, float b );
    
    public abstract void setMaterialEmissiveColor( NamedObject material, float r, float g, float b );
    
    public abstract void setMaterialDiffuseColor( NamedObject material, float r, float g, float b );
    
    public abstract void setMaterialSpecularColor( NamedObject material, float r, float g, float b );
    
    public abstract void setMaterialShininess( NamedObject material, float shininess );
    
    public abstract void setMaterialNormalizeNormals( NamedObject material, boolean normalizeNormals );
    
    public abstract void setMaterialLightingEnabled( NamedObject material, boolean lightingEnabled );
    
    public abstract void applyMaterial( NamedObject material, NamedObject appearance );
    
    
    
    public abstract NamedObject createColoringAttributes( String name );
    
    public abstract void setColoringAttribsShadeModel( NamedObject coloringAttribs, ShadeModel shadeModel );
    
    public abstract void setColoringAttribsColor( NamedObject coloringAttribs, float[] color, int offset, int colorSize );
    
    public abstract void applyColoringAttributes( NamedObject coloringAttribs, NamedObject appearance );
    
    
    
    public abstract NamedObject createRenderingAttributesAttributes( String name );
    
    public abstract void setRenderingAttribsDepthBufferEnabled( NamedObject renderingAttribs, boolean depthBufferEnabled );
    
    public abstract void setRenderingAttribsDepthBufferWriteEnabled( NamedObject renderingAttribs, boolean depthBufferWriteEnabled );
    
    public abstract void setRenderingAttribsAlphaTestValue( NamedObject renderingAttribs, float alphaTestValue );
    
    public abstract void setRenderingAttribsAlphaTestFunction( NamedObject renderingAttribs, TestFunction alphaTestFunction );
    
    //public abstract void setRenderingAttribsStencilFuncSep( NamedObject renderingAttribs, int stencilFuncSep );
    
    //public abstract void setRenderingAttribsStencilOpSep( NamedObject renderingAttribs, int stencilOpSep );
    
    //public abstract void setRenderingAttribsStencilMaskSep( NamedObject renderingAttribs, int stencilMaskSep );
    
    public abstract void setRenderingAttribsDepthTestFunction( NamedObject renderingAttribs, TestFunction depthTestFunction );
    
    public abstract void setRenderingAttribsIgnoreVertexColors( NamedObject renderingAttribs, boolean ignoreVertexColors );
    
    public abstract void setRenderingAttribsStencilEnabled( NamedObject renderingAttribs, boolean stencilEnabled );
    
    //public abstract void setRenderingAttribsStencilOpFail( NamedObject renderingAttribs, int stencilOpFail );
    
    //public abstract void setRenderingAttribsStencilOpZFail( NamedObject renderingAttribs, int stencilOpZFail );
    
    //public abstract void setRenderingAttribsStencilOpZPass( NamedObject renderingAttribs, int stencilOpZPass );
    
    public abstract void setRenderingAttribsStencilTestFunction( NamedObject renderingAttribs, TestFunction stencilTestFunction );
    
    public abstract void setRenderingAttribsStencilRef( NamedObject renderingAttribs, int stencilRef );
    
    public abstract void setRenderingAttribsStencilMask( NamedObject renderingAttribs, int stencilMask );
    
    public abstract void setRenderingAttribsColorWriteMask( NamedObject renderingAttribs, int colorWriteMask );
    
    public abstract void applyRenderingAttributes( NamedObject renderingAttribs, NamedObject appearance );
    
    
    
    public abstract NamedObject createPolygonAttributes( String name );
    
    public abstract void setPolygonAttribsFaceCullMode( NamedObject polygonAttribs, FaceCullMode faceCullMode );
    
    public abstract void setPolygonAttribsDrawMode( NamedObject polygonAttribs, DrawMode drawMode );
    
    public abstract void setPolygonAttribsPolygonOffset( NamedObject polygonAttribs, float polygonOffset );
    
    public abstract void setPolygonAttribsPolygonOffsetFactor( NamedObject polygonAttribs, float polygonOffsetFactor );
    
    public abstract void setPolygonAttribsBackfaceNormalFlip( NamedObject polygonAttribs, boolean backfaceNormalFlip );
    
    public abstract void setPolygonAttribsAntialiasing( NamedObject polygonAttribs, boolean anitaliasing );
    
    public abstract void setPolygonAttribsSortingEnabled( NamedObject polygonAttribs, boolean sortingEnabled );
    
    public abstract void applyPolygonAttributes( NamedObject polygonAttribs, NamedObject appearance );
    
    
    
    public abstract void setTextureBoundaryModeS( AbstractTexture texture, TextureBoundaryMode boundaryModeS );
    
    public abstract void setTextureBoundaryModeT( AbstractTexture texture, TextureBoundaryMode boundaryModeT );
    
    public abstract void setTextureMagFilter( AbstractTexture texture, TextureMagFilter magFilter );
    
    public abstract void setTextureMinFilter( AbstractTexture texture, TextureMinFilter minFilter );
    
    public abstract void applyTexture( AbstractTexture texture, int textureUnit, NamedObject appearance );
    
    
    
    public abstract NamedObject createTextureAttributes( String name );
    
    public abstract void setTextureAttribsTextureMode( NamedObject textureAttribs, TextureMode textureMode );
    
    public abstract void setTextureAttribsPerspectiveCorrectionMode( NamedObject textureAttribs, PerspectiveCorrectionMode perspCorrMode );
    
    public abstract void setTextureAttribsTextureBlendColor( NamedObject textureAttribs, float[] texBlendColor, int offset, int colorSize );
    
    public abstract void setTextureAttribsTextureTransfrom( NamedObject textureAttribs, Matrix4f textureTransform );
    
    public abstract void setTextureAttribsCombineRGBMode( NamedObject textureAttribs, TextureCombineMode combineRGBMode );
    
    public abstract void setTextureAttribsCombineAlphaMode( NamedObject textureAttribs, TextureCombineMode combineAlphaMode );
    
    public abstract void setTextureAttribsCombineRGBSource( NamedObject textureAttribs, int channel, TextureCombineSource combineRGBSource );
    
    public abstract void setTextureAttribsCombineAlphaSource( NamedObject textureAttribs, int channel, TextureCombineSource combineAlphaSource );
    
    public abstract void setTextureAttribsCombineRGBFunction( NamedObject textureAttribs, int channel, TextureCombineFunction combineRGBFunction );
    
    public abstract void setTextureAttribsCombineAlphaFunction( NamedObject textureAttribs, int channel, TextureCombineFunction combineAlphaFunction );
    
    public abstract void setTextureAttribsCombineRGBScale( NamedObject textureAttribs, int combineRGBScale );
    
    public abstract void setTextureAttribsCombineAlphaScale( NamedObject textureAttribs, int combineAlphaScale );
    
    public abstract void setTextureAttribsCompareMode( NamedObject textureAttribs, TextureCompareMode compareMode );
    
    public abstract void setTextureAttribsCompareFunc( NamedObject textureAttribs, CompareFunction compareFunc );
    
    public abstract void applyTextureAttributes( NamedObject textureAttribs, int textureUnit, NamedObject appearance );
    
    
    
    public abstract NamedObject createTextureCoordGeneration( String name );
    
    public abstract void setTexCoordGenerationGenMode( NamedObject texCoordGen, TexCoordGenMode genMode );
    
    public abstract void setTexCoordGenerationNumTexGenUnits( NamedObject texCoordGen, int numTexGenUnits );
    
    public abstract void setTexCoordGenerationPlaneS( NamedObject texCoordGen, float[] planeS, int offset );
    
    public abstract void setTexCoordGenerationPlaneT( NamedObject texCoordGen, float[] planeT, int offset );
    
    public abstract void setTexCoordGenerationPlaneR( NamedObject texCoordGen, float[] planeR, int offset );
    
    public abstract void setTexCoordGenerationPlaneQ( NamedObject texCoordGen, float[] planeQ, int offset );
    
    public abstract void applyTextureCoordGeneration( NamedObject texCoordGen, int textureUnit, NamedObject appearance );
    
    
    
    public abstract NamedObject createAppearance( String name, int flags );
    
    public abstract NamedObject createStandardAppearance( String name, AbstractTexture texture0, int flags );
    
    public abstract NamedObject createStandardAppearance( String name, String textureName0, URL baseURL, int flags );
    
    public abstract void applyAppearance( NamedObject appearance, NamedObject geometry );
    
    
    public abstract AbstractTexture getFallbackTexture();
    
    public abstract boolean isFallbackTexture( AbstractTexture texture );
    
    public abstract AbstractTexture loadTexture( InputStream in, String texName, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTexture loadTexture( URL url, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTexture loadOrGetTexture( String texName, URL baseURL, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTexture loadOrGetTexture( String texName, boolean flipVertically, boolean acceptAlpha, boolean loadMipmaps, boolean allowStreching, boolean acceptFallbackTexture );
    
    public abstract AbstractTextureImage createTextureImage( TextureImageFormat format, int width, int height );
    
    public AbstractTextureImage createTextureImage( TextureImageFormat format, int orgWidth, int orgHeight, int width, int height );
    
    public abstract AbstractTexture createTexture( AbstractTextureImage texImage0, boolean generateMipmaps );
}
