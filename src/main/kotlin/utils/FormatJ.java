package utils;

public enum FormatJ {
    /**
     * 8-bit alpha
     */
    Alpha8(8),

    @Deprecated
    Reserved1(0),

    /**
     * 8-bit grayscale/luminance.
     */
    Luminance8(8),

    @Deprecated
    Reserved2(0),

    /**
     * half-precision floating-point grayscale/luminance.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    Luminance16F(16,true),

    /**
     * single-precision floating-point grayscale/luminance.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    Luminance32F(32,true),

    /**
     * 8-bit luminance/grayscale and 8-bit alpha.
     */
    Luminance8Alpha8(16),

    @Deprecated
    Reserved3(0),

    /**
     * half-precision floating-point grayscale/luminance and alpha.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    Luminance16FAlpha16F(32,true),

    @Deprecated
    Reserved4(0),

    @Deprecated
    Reserved5(0),

    /**
     * 8-bit blue, green, and red.
     */
    BGR8(24), // BGR and ABGR formats are often used on Windows systems

    /**
     * 8-bit red, green, and blue.
     */
    RGB8(24),

    @Deprecated
    Reserved6(0),

    @Deprecated
    Reserved7(0),

    /**
     * 5-bit red, 6-bit green, and 5-bit blue.
     */
    RGB565(16),

    @Deprecated
    Reserved8(0),

    /**
     * 5-bit red, green, and blue with 1-bit alpha.
     */
    RGB5A1(16),

    /**
     * 8-bit red, green, blue, and alpha.
     */
    RGBA8(32),

    /**
     * 8-bit alpha, blue, green, and red.
     */
    ABGR8(32),

    /**
     * 8-bit alpha, red, blue and green
     */
    ARGB8(32),

    /**
     * 8-bit blue, green, red and alpha.
     */
    BGRA8(32),

    @Deprecated
    Reserved9(0),

    /**
     * S3TC compression DXT1.
     */
    DXT1(4,false,true, false),

    /**
     * S3TC compression DXT1 with 1-bit alpha.
     */
    DXT1A(4,false,true, false),

    /**
     * S3TC compression DXT3 with 4-bit alpha.
     */
    DXT3(8,false,true, false),

    /**
     * S3TC compression DXT5 with interpolated 8-bit alpha.
     *
     */
    DXT5(8,false,true, false),

    RGTC2(8,false,true, false),

    SIGNED_RGTC2(8,false,true, false),

    RGTC1(4,false,true, false),

    SIGNED_RGTC1(4,false,true, false),

    /**
     * BPTC compression BC6 signed float RGB
     */
    BC6H_SF16(8, false, true, true),

    /**
     * BPTC compression BC6 unsigned float RGB
     */
    BC6H_UF16(8, false, true, true),

    /**
     * BPTC compression BC7 RGBA
     */
    BC7_UNORM(8, false, true, false),

    /**
     * BPTC compression BC7 SRGB Alpha
     */
    BC7_UNORM_SRGB(8, false, true, false),

    /**
     * Luminance-Alpha Texture Compression.
     *
     * @deprecated Not supported by OpenGL 3.0.
     */
    @Deprecated
    Reserved10(0),

    /**
     * Arbitrary depth format. The precision is chosen by the video
     * hardware.
     */
    Depth(0,true,false,false),

    /**
     * 16-bit depth.
     */
    Depth16(16,true,false,false),

    /**
     * 24-bit depth.
     */
    Depth24(24,true,false,false),

    /**
     * 32-bit depth.
     */
    Depth32(32,true,false,false),

    /**
     * single-precision floating point depth.
     *
     * Requires {@link Caps#FloatDepthBuffer}.
     */
    Depth32F(32,true,false,true),

    /**
     * Texture data is stored as {@link FormatJ#RGB16F} in system memory,
     * but will be converted to {@link FormatJ#RGB111110F} when sent
     * to the video hardware.
     *
     * Requires {@link Caps#FloatTexture} and {@link Caps#PackedFloatTexture}.
     */
    RGB16F_to_RGB111110F(48,true),

    /**
     * unsigned floating-point red, green and blue that uses 32 bits.
     *
     * Requires {@link Caps#PackedFloatTexture}.
     */
    RGB111110F(32,true),

    /**
     * Texture data is stored as {@link FormatJ#RGB16F} in system memory,
     * but will be converted to {@link FormatJ#RGB9E5} when sent
     * to the video hardware.
     *
     * Requires {@link Caps#FloatTexture} and {@link Caps#SharedExponentTexture}.
     */
    RGB16F_to_RGB9E5(48,true),

    /**
     * 9-bit red, green and blue with 5-bit exponent.
     *
     * Requires {@link Caps#SharedExponentTexture}.
     */
    RGB9E5(32,true),

    /**
     * half-precision floating point red, green, and blue.
     *
     * Requires {@link Caps#FloatTexture}.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB16F(48,true),

    /**
     * half-precision floating point red, green, blue, and alpha.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    RGBA16F(64,true),

    /**
     * single-precision floating point red, green, and blue.
     *
     * Requires {@link Caps#FloatTexture}.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB32F(96,true),

    /**
     * single-precision floating point red, green, blue and alpha.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    RGBA32F(128,true),

    @Deprecated
    Reserved11(0),

    /**
     * 24-bit depth with 8-bit stencil.
     * Check the cap {@link Caps#PackedDepthStencilBuffer}.
     */
    Depth24Stencil8(32, true, false, false),

    @Deprecated
    Reserved12(0),

    /**
     * Ericsson Texture Compression. Typically used on Android.
     *
     * Requires {@link Caps#TextureCompressionETC1}.
     */
    ETC1(4, false, true, false),

    /**
     * 8-bit signed int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R8I(8),
    /**
     * 8-bit unsigned int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R8UI(8),
    /**
     * 16-bit signed int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R16I(16),
    /**
     * 16-bit unsigned int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R16UI(16),
    /**
     * 32-bit signed int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R32I(32),
    /**
     * 32-bit unsigned int red.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    R32UI(32),


    /**
     * 8-bit signed int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG8I(16),
    /**
     * 8-bit unsigned int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG8UI(16),
    /**
     * 16-bit signed int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG16I(32),
    /**
     * 16-bit unsigned int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG16UI(32),
    /**
     * 32-bit signed int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG32I(64),
    /**
     * 32-bit unsigned int red and green.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RG32UI(64),

    /**
     * 8-bit signed int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB8I(24),
    /**
     * 8 bit unsigned int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB8UI(24),
    /**
     * 16-bit signed int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB16I(48),
    /**
     * 16-bit unsigned int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB16UI(48),
    /**
     * 32-bit signed int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB32I(96),
    /**
     * 32-bit unsigned int red, green and blue.
     *
     * Requires {@link Caps#IntegerTexture} to be supported for textures.
     * May be supported for renderbuffers, but the OpenGL specification does not require it.
     */
    RGB32UI(96),


    /**
     * 8-bit signed int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA8I(32),
    /**
     * 8-bit unsigned int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA8UI(32),
    /**
     * 16-bit signed int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA16I(64),
    /**
     * 16-bit unsigned int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA16UI(64),
    /**
     * 32-bit signed int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA32I(128),
    /**
     * 32-bit unsigned int red, green, blue and alpha.
     *
     * Requires {@link Caps#IntegerTexture}.
     */
    RGBA32UI(128),

    /**
     * half-precision floating point red.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    R16F(16,true),

    /**
     * single-precision floating point red.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    R32F(32,true),

    /**
     * half-precision floating point red and green.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    RG16F(32,true),

    /**
     * single-precision floating point red and green.
     *
     * Requires {@link Caps#FloatTexture}.
     */
    RG32F(64,true),

    /**
     * 10-bit red, green, and blue with 2-bit alpha.
     */
    RGB10A2(32),
    ;

    private int bpp;
    private boolean isDepth;
    private boolean isCompressed;
    private boolean isFloatingPoint;

    private FormatJ(int bpp){
        this.bpp = bpp;
    }

    private FormatJ(int bpp, boolean isFP){
        this(bpp);
        this.isFloatingPoint = isFP;
    }

    private FormatJ(int bpp, boolean isDepth, boolean isCompressed, boolean isFP){
        this(bpp, isFP);
        this.isDepth = isDepth;
        this.isCompressed = isCompressed;
    }

    /**
     * @return bits per pixel.
     */
    public int getBitsPerPixel(){
        return bpp;
    }

    /**
     * @return True if this format is a depth format, false otherwise.
     */
    public boolean isDepthFormat(){
        return isDepth;
    }

    /**
     * @return True if this format is a depth + stencil (packed) format, false otherwise.
     */
    boolean isDepthStencilFormat() {
        return this == Depth24Stencil8;
    }

    /**
     * @return True if this is a compressed image format, false if
     * uncompressed.
     */
    public boolean isCompressed() {
        return isCompressed;
    }

    /**
     * @return True if this image format is in floating point,
     * false if it is an integer format.
     */
    public boolean isFloatingPont(){
        return isFloatingPoint;
    }



}