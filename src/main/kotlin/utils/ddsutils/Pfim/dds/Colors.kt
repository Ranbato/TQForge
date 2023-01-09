
    data class Color888
        (
        public val r:Byte,
        public val g:Byte,
        public val b:Byte,
    )

    data class Color8888
    (
        public val r:Byte,
        public val g:Byte,
        public val b:Byte,
        public val a:Byte,
    )

    data class ColorFloatRgb
        (
        public val r:Float,
        public val g:Float,
        public val b:Float,
    ){

        public  fun FromRgb565( rgb565:UShort):ColorFloatRgb
        {
            val F5 = 255f / 31f;
            val F6 = 255f / 63f;

            return ColorFloatRgb(

                r = ((rgb565.toInt() and 0x1f)) * F5,
                g = ((rgb565.toInt() and 0x7E0) shr 5) * F6,
                b = ((rgb565.toInt() and 0xF800) shr 11) * F5,
            )
        }

        public fun Lerp( other:ColorFloatRgb, blend:Float):ColorFloatRgb = ColorFloatRgb(

            r = r + blend * (other.r - r),
            g = g + blend * (other.g - g),
            b = b + blend * (other.b - b),
        )

        public fun As8Bit():Color888 = Color888(
            r = (r + 0.5f).toInt().toByte(),
            g = (g + 0.5f).toInt().toByte(),
            b = (b + 0.5f).toInt().toByte(),
        );

        public fun As8BitA():Color8888 = Color8888(

            r = (r + 0.5f).toInt().toByte(),
            g = (g + 0.5f).toInt().toByte(),
            b = (b + 0.5f).toInt().toByte(),
            a = 255.toByte(),
        )
    }

