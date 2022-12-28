package utils

/**
 * DDSReader.java
 *
 * Copyright (c) 2015 Kenji Sasaki
 * Released under the MIT license.
 * https://github.com/npedotnet/DDSReader/blob/master/LICENSE
 *
 * English document
 * https://github.com/npedotnet/DDSReader/blob/master/README.md
 *
 * Japanese document
 * http://3dtech.jp/wiki/index.php?DDSReader
 *
 */
object DDSReader {
    val ARGB = Order(16, 8, 0, 24)
    val ABGR = Order(0, 8, 16, 24)
    fun getHeight(buffer: ByteArray): Int {
        return buffer[12].toInt() and 0xFF or (buffer[13].toInt() and 0xFF shl 8) or (buffer[14].toInt() and 0xFF shl 16) or (buffer[15].toInt() and 0xFF shl 24)
    }

    fun getWidth(buffer: ByteArray): Int {
        return buffer[16].toInt() and 0xFF or (buffer[17].toInt() and 0xFF shl 8) or (buffer[18].toInt() and 0xFF shl 16) or (buffer[19].toInt() and 0xFF shl 24)
    }

    fun getMipmap(buffer: ByteArray): Int {
        return buffer[28].toInt() and 0xFF or (buffer[29].toInt() and 0xFF shl 8) or (buffer[30].toInt() and 0xFF shl 16) or (buffer[31].toInt() and 0xFF shl 24)
    }

    fun getPixelFormatFlags(buffer: ByteArray): Int {
        return buffer[80].toInt() and 0xFF or (buffer[81].toInt() and 0xFF shl 8) or (buffer[82].toInt() and 0xFF shl 16) or (buffer[83].toInt() and 0xFF shl 24)
    }

    fun getFourCC(buffer: ByteArray): Int {
        return buffer[84].toInt() and 0xFF shl 24 or (buffer[85].toInt() and 0xFF shl 16) or (buffer[86].toInt() and 0xFF shl 8) or (buffer[87].toInt() and 0xFF)
    }

    fun getBitCount(buffer: ByteArray): Int {
        return buffer[88].toInt() and 0xFF or (buffer[89].toInt() and 0xFF shl 8) or (buffer[90].toInt() and 0xFF shl 16) or (buffer[91].toInt() and 0xFF shl 24)
    }

    fun getRedMask(buffer: ByteArray): Int {
        return buffer[92].toInt() and 0xFF or (buffer[93].toInt() and 0xFF shl 8) or (buffer[94].toInt() and 0xFF shl 16) or (buffer[95].toInt() and 0xFF shl 24)
    }

    fun getGreenMask(buffer: ByteArray): Int {
        return buffer[96].toInt() and 0xFF or (buffer[97].toInt() and 0xFF shl 8) or (buffer[98].toInt() and 0xFF shl 16) or (buffer[99].toInt() and 0xFF shl 24)
    }

    fun getBlueMask(buffer: ByteArray): Int {
        return buffer[100].toInt() and 0xFF or (buffer[101].toInt() and 0xFF shl 8) or (buffer[102].toInt() and 0xFF shl 16) or (buffer[103].toInt() and 0xFF shl 24)
    }

    fun getAlphaMask(buffer: ByteArray): Int {
        return buffer[104].toInt() and 0xFF or (buffer[105].toInt() and 0xFF shl 8) or (buffer[106].toInt() and 0xFF shl 16) or (buffer[107].toInt() and 0xFF shl 24)
    }

    fun read(buffer: ByteArray, order: Order, mipmapLevel: Int): IntArray? {

        // header
        var width = getWidth(buffer)
        var height = getHeight(buffer)
        val mipmap = getMipmap(buffer)

        // type
        val type = getType(buffer)
        if (type == 0) return null

        // offset
        var offset = 128 // header size
        if (mipmapLevel > 0 && mipmapLevel < mipmap) {
            for (i in 0 until mipmapLevel) {
                when (type) {
                    DXT1 -> offset += 8 * ((width + 3) / 4) * ((height + 3) / 4)
                    DXT2, DXT3, DXT4, DXT5 -> offset += 16 * ((width + 3) / 4) * ((height + 3) / 4)
                    A1R5G5B5, X1R5G5B5, A4R4G4B4, X4R4G4B4, R5G6B5, R8G8B8, A8B8G8R8, X8B8G8R8, A8R8G8B8, X8R8G8B8 -> offset += (type and 0xFF) * width * height
                }
                width /= 2
                height /= 2
            }
            if (width <= 0) width = 1
            if (height <= 0) height = 1
        }
        var pixels: IntArray? = null
        when (type) {
            DXT1 -> pixels = decodeDXT1(width, height, offset, buffer, order)
            DXT2 -> pixels = decodeDXT2(width, height, offset, buffer, order)
            DXT3 -> pixels = decodeDXT3(width, height, offset, buffer, order)
            DXT4 -> pixels = decodeDXT4(width, height, offset, buffer, order)
            DXT5 -> pixels = decodeDXT5(width, height, offset, buffer, order)
            A1R5G5B5 -> pixels = readA1R5G5B5(width, height, offset, buffer, order)
            X1R5G5B5 -> pixels = readX1R5G5B5(width, height, offset, buffer, order)
            A4R4G4B4 -> pixels = readA4R4G4B4(width, height, offset, buffer, order)
            X4R4G4B4 -> pixels = readX4R4G4B4(width, height, offset, buffer, order)
            R5G6B5 -> pixels = readR5G6B5(width, height, offset, buffer, order)
            R8G8B8 -> pixels = readR8G8B8(width, height, offset, buffer, order)
            A8B8G8R8 -> pixels = readA8B8G8R8(width, height, offset, buffer, order)
            X8B8G8R8 -> pixels = readX8B8G8R8(width, height, offset, buffer, order)
            A8R8G8B8 -> pixels = readA8R8G8B8(width, height, offset, buffer, order)
            X8R8G8B8 -> pixels = readX8R8G8B8(width, height, offset, buffer, order)
        }
        return pixels
    }

    private fun getType(buffer: ByteArray): Int {
        var type = 0
        val flags = getPixelFormatFlags(buffer)
        if (flags and 0x04 != 0) {
            // DXT
            type = getFourCC(buffer)
        } else if (flags and 0x40 != 0) {
            // RGB
            val bitCount = getBitCount(buffer)
            val redMask = getRedMask(buffer)
            val greenMask = getGreenMask(buffer)
            val blueMask = getBlueMask(buffer)
            val alphaMask = if (flags and 0x01 != 0) getAlphaMask(buffer) else 0 // 0x01 alpha
            if (bitCount == 16) {
                if (redMask == A1R5G5B5_MASKS[0] && greenMask == A1R5G5B5_MASKS[1] && blueMask == A1R5G5B5_MASKS[2] && alphaMask == A1R5G5B5_MASKS[3]) {
                    // A1R5G5B5
                    type = A1R5G5B5
                } else if (redMask == X1R5G5B5_MASKS[0] && greenMask == X1R5G5B5_MASKS[1] && blueMask == X1R5G5B5_MASKS[2] && alphaMask == X1R5G5B5_MASKS[3]) {
                    // X1R5G5B5
                    type = X1R5G5B5
                } else if (redMask == A4R4G4B4_MASKS[0] && greenMask == A4R4G4B4_MASKS[1] && blueMask == A4R4G4B4_MASKS[2] && alphaMask == A4R4G4B4_MASKS[3]) {
                    // A4R4G4B4
                    type = A4R4G4B4
                } else if (redMask == X4R4G4B4_MASKS[0] && greenMask == X4R4G4B4_MASKS[1] && blueMask == X4R4G4B4_MASKS[2] && alphaMask == X4R4G4B4_MASKS[3]) {
                    // X4R4G4B4
                    type = X4R4G4B4
                } else if (redMask == R5G6B5_MASKS[0] && greenMask == R5G6B5_MASKS[1] && blueMask == R5G6B5_MASKS[2] && alphaMask == R5G6B5_MASKS[3]) {
                    // R5G6B5
                    type = R5G6B5
                } else {
                    // Unsupported 16bit RGB image
                }
            } else if (bitCount == 24) {
                if (redMask == R8G8B8_MASKS[0] && greenMask == R8G8B8_MASKS[1] && blueMask == R8G8B8_MASKS[2] && alphaMask == R8G8B8_MASKS[3]) {
                    // R8G8B8
                    type = R8G8B8
                } else {
                    // Unsupported 24bit RGB image
                }
            } else if (bitCount == 32) {
                if (redMask == A8B8G8R8_MASKS[0] && greenMask == A8B8G8R8_MASKS[1] && blueMask == A8B8G8R8_MASKS[2] && alphaMask == A8B8G8R8_MASKS[3]) {
                    // A8B8G8R8
                    type = A8B8G8R8
                } else if (redMask == X8B8G8R8_MASKS[0] && greenMask == X8B8G8R8_MASKS[1] && blueMask == X8B8G8R8_MASKS[2] && alphaMask == X8B8G8R8_MASKS[3]) {
                    // X8B8G8R8
                    type = X8B8G8R8
                } else if (redMask == A8R8G8B8_MASKS[0] && greenMask == A8R8G8B8_MASKS[1] && blueMask == A8R8G8B8_MASKS[2] && alphaMask == A8R8G8B8_MASKS[3]) {
                    // A8R8G8B8
                    type = A8R8G8B8
                } else if (redMask == X8R8G8B8_MASKS[0] && greenMask == X8R8G8B8_MASKS[1] && blueMask == X8R8G8B8_MASKS[2] && alphaMask == X8R8G8B8_MASKS[3]) {
                    // X8R8G8B8
                    type = X8R8G8B8
                } else {
                    // Unsupported 32bit RGB image
                }
            }
        } else {
            // YUV or LUMINANCE image
        }
        return type
    }

    private fun decodeDXT1(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        val pixels = IntArray(width * height)
        var index = offset
        val w = (width + 3) / 4
        val h = (height + 3) / 4
        for (i in 0 until h) {
            for (j in 0 until w) {
                val c0 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                val c1 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                for (k in 0..3) {
                    if (4 * i + k >= height) break
                    val t0 = buffer[index].toInt() and 0x03
                    val t1 = buffer[index].toInt() and 0x0C shr 2
                    val t2 = buffer[index].toInt() and 0x30 shr 4
                    val t3 = buffer[index++].toInt() and 0xC0 shr 6
                    pixels[4 * width * i + 4 * j + width * k + 0] = getDXTColor(c0, c1, 0xFF, t0, order)
                    if (4 * j + 1 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 1] = getDXTColor(c0, c1, 0xFF, t1, order)
                    if (4 * j + 2 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 2] = getDXTColor(c0, c1, 0xFF, t2, order)
                    if (4 * j + 3 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 3] = getDXTColor(c0, c1, 0xFF, t3, order)
                }
            }
        }
        return pixels
    }

    private fun decodeDXT2(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        return decodeDXT3(width, height, offset, buffer, order)
    }

    private fun decodeDXT3(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val w = (width + 3) / 4
        val h = (height + 3) / 4
        val pixels = IntArray(width * height)
        val alphaTable = IntArray(16)
        for (i in 0 until h) {
            for (j in 0 until w) {
                // create alpha table(4bit to 8bit)
                for (k in 0..3) {
                    val a0 = buffer[index++].toInt() and 0xFF
                    val a1 = buffer[index++].toInt() and 0xFF
                    // 4bit alpha to 8bit alpha
                    alphaTable[4 * k + 0] = 17 * (a0 and 0xF0 shr 4)
                    alphaTable[4 * k + 1] = 17 * (a0 and 0x0F)
                    alphaTable[4 * k + 2] = 17 * (a1 and 0xF0 shr 4)
                    alphaTable[4 * k + 3] = 17 * (a1 and 0x0F)
                }
                val c0 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                val c1 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                for (k in 0..3) {
                    if (4 * i + k >= height) break
                    val t0 = buffer[index].toInt() and 0x03
                    val t1 = buffer[index].toInt() and 0x0C shr 2
                    val t2 = buffer[index].toInt() and 0x30 shr 4
                    val t3 = buffer[index++].toInt() and 0xC0 shr 6
                    pixels[4 * width * i + 4 * j + width * k + 0] =
                        getDXTColor(c0, c1, alphaTable[4 * k + 0], t0, order)
                    if (4 * j + 1 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 1] =
                        getDXTColor(c0, c1, alphaTable[4 * k + 1], t1, order)
                    if (4 * j + 2 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 2] =
                        getDXTColor(c0, c1, alphaTable[4 * k + 2], t2, order)
                    if (4 * j + 3 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 3] =
                        getDXTColor(c0, c1, alphaTable[4 * k + 3], t3, order)
                }
            }
        }
        return pixels
    }

    private fun decodeDXT4(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        return decodeDXT5(width, height, offset, buffer, order)
    }

    private fun decodeDXT5(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val w = (width + 3) / 4
        val h = (height + 3) / 4
        val pixels = IntArray(width * height)
        val alphaTable = IntArray(16)
        for (i in 0 until h) {
            for (j in 0 until w) {
                // create alpha table
                val a0 = buffer[index++].toInt() and 0xFF
                val a1 = buffer[index++].toInt() and 0xFF
                val b0 =
                    buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8) or (buffer[index + 2].toInt() and 0xFF shl 16)
                index += 3
                val b1 =
                    buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8) or (buffer[index + 2].toInt() and 0xFF shl 16)
                index += 3
                alphaTable[0] = b0 and 0x07
                alphaTable[1] = b0 shr 3 and 0x07
                alphaTable[2] = b0 shr 6 and 0x07
                alphaTable[3] = b0 shr 9 and 0x07
                alphaTable[4] = b0 shr 12 and 0x07
                alphaTable[5] = b0 shr 15 and 0x07
                alphaTable[6] = b0 shr 18 and 0x07
                alphaTable[7] = b0 shr 21 and 0x07
                alphaTable[8] = b1 and 0x07
                alphaTable[9] = b1 shr 3 and 0x07
                alphaTable[10] = b1 shr 6 and 0x07
                alphaTable[11] = b1 shr 9 and 0x07
                alphaTable[12] = b1 shr 12 and 0x07
                alphaTable[13] = b1 shr 15 and 0x07
                alphaTable[14] = b1 shr 18 and 0x07
                alphaTable[15] = b1 shr 21 and 0x07
                val c0 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                val c1 = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
                index += 2
                for (k in 0..3) {
                    if (4 * i + k >= height) break
                    val t0 = buffer[index].toInt() and 0x03
                    val t1 = buffer[index].toInt() and 0x0C shr 2
                    val t2 = buffer[index].toInt() and 0x30 shr 4
                    val t3 = buffer[index++].toInt() and 0xC0 shr 6
                    pixels[4 * width * i + 4 * j + width * k + 0] =
                        getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 0]), t0, order)
                    if (4 * j + 1 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 1] =
                        getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 1]), t1, order)
                    if (4 * j + 2 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 2] =
                        getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 2]), t2, order)
                    if (4 * j + 3 >= width) continue
                    pixels[4 * width * i + 4 * j + width * k + 3] =
                        getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4 * k + 3]), t3, order)
                }
            }
        }
        return pixels
    }

    private fun readA1R5G5B5(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val rgba = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
            index += 2
            val r = BIT5[rgba and A1R5G5B5_MASKS[0] shr 10]
            val g = BIT5[rgba and A1R5G5B5_MASKS[1] shr 5]
            val b = BIT5[rgba and A1R5G5B5_MASKS[2]]
            val a = 255 * (rgba and A1R5G5B5_MASKS[3] shr 15)
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readX1R5G5B5(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val rgba = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
            index += 2
            val r = BIT5[rgba and X1R5G5B5_MASKS[0] shr 10]
            val g = BIT5[rgba and X1R5G5B5_MASKS[1] shr 5]
            val b = BIT5[rgba and X1R5G5B5_MASKS[2]]
            val a = 255
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readA4R4G4B4(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val rgba = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
            index += 2
            val r = 17 * (rgba and A4R4G4B4_MASKS[0] shr 8)
            val g = 17 * (rgba and A4R4G4B4_MASKS[1] shr 4)
            val b = 17 * (rgba and A4R4G4B4_MASKS[2])
            val a = 17 * (rgba and A4R4G4B4_MASKS[3] shr 12)
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readX4R4G4B4(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val rgba = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
            index += 2
            val r = 17 * (rgba and A4R4G4B4_MASKS[0] shr 8)
            val g = 17 * (rgba and A4R4G4B4_MASKS[1] shr 4)
            val b = 17 * (rgba and A4R4G4B4_MASKS[2])
            val a = 255
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readR5G6B5(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val rgba = buffer[index].toInt() and 0xFF or (buffer[index + 1].toInt() and 0xFF shl 8)
            index += 2
            val r = BIT5[rgba and R5G6B5_MASKS[0] shr 11]
            val g = BIT6[rgba and R5G6B5_MASKS[1] shr 5]
            val b = BIT5[rgba and R5G6B5_MASKS[2]]
            val a = 255
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readR8G8B8(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val b = buffer[index++].toInt() and 0xFF
            val g = buffer[index++].toInt() and 0xFF
            val r = buffer[index++].toInt() and 0xFF
            val a = 255
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readA8B8G8R8(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val r = buffer[index++].toInt() and 0xFF
            val g = buffer[index++].toInt() and 0xFF
            val b = buffer[index++].toInt() and 0xFF
            val a = buffer[index++].toInt() and 0xFF
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readX8B8G8R8(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val r = buffer[index++].toInt() and 0xFF
            val g = buffer[index++].toInt() and 0xFF
            val b = buffer[index++].toInt() and 0xFF
            val a = 255
            index++
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readA8R8G8B8(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val b = buffer[index++].toInt() and 0xFF
            val g = buffer[index++].toInt() and 0xFF
            val r = buffer[index++].toInt() and 0xFF
            val a = buffer[index++].toInt() and 0xFF
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun readX8R8G8B8(width: Int, height: Int, offset: Int, buffer: ByteArray, order: Order): IntArray {
        var index = offset
        val pixels = IntArray(width * height)
        for (i in 0 until height * width) {
            val b = buffer[index++].toInt() and 0xFF
            val g = buffer[index++].toInt() and 0xFF
            val r = buffer[index++].toInt() and 0xFF
            val a = 255
            index++
            pixels[i] =
                a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
        }
        return pixels
    }

    private fun getDXTColor(c0: Int, c1: Int, a: Int, t: Int, order: Order): Int {
        when (t) {
            0 -> return getDXTColor1(c0, a, order)
            1 -> return getDXTColor1(c1, a, order)
            2 -> return if (c0 > c1) getDXTColor2_1(c0, c1, a, order) else getDXTColor1_1(c0, c1, a, order)
            3 -> return if (c0 > c1) getDXTColor2_1(c1, c0, a, order) else 0
        }
        return 0
    }

    private fun getDXTColor2_1(c0: Int, c1: Int, a: Int, order: Order): Int {
        // 2*c0/3 + c1/3
        val r = (2 * BIT5[c0 and 0xFC00 shr 11] + BIT5[c1 and 0xFC00 shr 11]) / 3
        val g = (2 * BIT6[c0 and 0x07E0 shr 5] + BIT6[c1 and 0x07E0 shr 5]) / 3
        val b = (2 * BIT5[c0 and 0x001F] + BIT5[c1 and 0x001F]) / 3
        return a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
    }

    private fun getDXTColor1_1(c0: Int, c1: Int, a: Int, order: Order): Int {
        // (c0+c1) / 2
        val r = (BIT5[c0 and 0xFC00 shr 11] + BIT5[c1 and 0xFC00 shr 11]) / 2
        val g = (BIT6[c0 and 0x07E0 shr 5] + BIT6[c1 and 0x07E0 shr 5]) / 2
        val b = (BIT5[c0 and 0x001F] + BIT5[c1 and 0x001F]) / 2
        return a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
    }

    private fun getDXTColor1(c: Int, a: Int, order: Order): Int {
        val r = BIT5[c and 0xFC00 shr 11]
        val g = BIT6[c and 0x07E0 shr 5]
        val b = BIT5[c and 0x001F]
        return a shl order.alphaShift or (r shl order.redShift) or (g shl order.greenShift) or (b shl order.blueShift)
    }

    private fun getDXT5Alpha(a0: Int, a1: Int, t: Int): Int {
        if (a0 > a1) when (t) {
            0 -> return a0
            1 -> return a1
            2 -> return (6 * a0 + a1) / 7
            3 -> return (5 * a0 + 2 * a1) / 7
            4 -> return (4 * a0 + 3 * a1) / 7
            5 -> return (3 * a0 + 4 * a1) / 7
            6 -> return (2 * a0 + 5 * a1) / 7
            7 -> return (a0 + 6 * a1) / 7
        } else when (t) {
            0 -> return a0
            1 -> return a1
            2 -> return (4 * a0 + a1) / 5
            3 -> return (3 * a0 + 2 * a1) / 5
            4 -> return (2 * a0 + 3 * a1) / 5
            5 -> return (a0 + 4 * a1) / 5
            6 -> return 0
            7 -> return 255
        }
        return 0
    }

    // Image Type
    private const val DXT1 = 0x44585431
    private const val DXT2 = 0x44585432
    private const val DXT3 = 0x44585433
    private const val DXT4 = 0x44585434
    private const val DXT5 = 0x44585435
    private const val A1R5G5B5 = 1 shl 16 or 2
    private const val X1R5G5B5 = 2 shl 16 or 2
    private const val A4R4G4B4 = 3 shl 16 or 2
    private const val X4R4G4B4 = 4 shl 16 or 2
    private const val R5G6B5 = 5 shl 16 or 2
    private const val R8G8B8 = 1 shl 16 or 3
    private const val A8B8G8R8 = 1 shl 16 or 4
    private const val X8B8G8R8 = 2 shl 16 or 4
    private const val A8R8G8B8 = 3 shl 16 or 4
    private const val X8R8G8B8 = 4 shl 16 or 4

    // RGBA Masks
    private val A1R5G5B5_MASKS = intArrayOf(0x7C00, 0x03E0, 0x001F, 0x8000)
    private val X1R5G5B5_MASKS = intArrayOf(0x7C00, 0x03E0, 0x001F, 0x0000)
    private val A4R4G4B4_MASKS = intArrayOf(0x0F00, 0x00F0, 0x000F, 0xF000)
    private val X4R4G4B4_MASKS = intArrayOf(0x0F00, 0x00F0, 0x000F, 0x0000)
    private val R5G6B5_MASKS = intArrayOf(0xF800, 0x07E0, 0x001F, 0x0000)
    private val R8G8B8_MASKS = intArrayOf(0xFF0000, 0x00FF00, 0x0000FF, 0x000000)
    private val A8B8G8R8_MASKS = intArrayOf(0x000000FF, 0x0000FF00, 0x00FF0000, -0x1000000)
    private val X8B8G8R8_MASKS = intArrayOf(0x000000FF, 0x0000FF00, 0x00FF0000, 0x00000000)
    private val A8R8G8B8_MASKS = intArrayOf(0x00FF0000, 0x0000FF00, 0x000000FF, -0x1000000)
    private val X8R8G8B8_MASKS = intArrayOf(0x00FF0000, 0x0000FF00, 0x000000FF, 0x00000000)

    // BIT4 = 17 * index;
    private val BIT5 = intArrayOf(
        0,
        8,
        16,
        25,
        33,
        41,
        49,
        58,
        66,
        74,
        82,
        90,
        99,
        107,
        115,
        123,
        132,
        140,
        148,
        156,
        165,
        173,
        181,
        189,
        197,
        206,
        214,
        222,
        230,
        239,
        247,
        255
    )
    private val BIT6 = intArrayOf(
        0,
        4,
        8,
        12,
        16,
        20,
        24,
        28,
        32,
        36,
        40,
        45,
        49,
        53,
        57,
        61,
        65,
        69,
        73,
        77,
        81,
        85,
        89,
        93,
        97,
        101,
        105,
        109,
        113,
        117,
        121,
        125,
        130,
        134,
        138,
        142,
        146,
        150,
        154,
        158,
        162,
        166,
        170,
        174,
        178,
        182,
        186,
        190,
        194,
        198,
        202,
        206,
        210,
        215,
        219,
        223,
        227,
        231,
        235,
        239,
        243,
        247,
        251,
        255
    )

    class Order internal constructor(
        var redShift: Int,
        var greenShift: Int,
        var blueShift: Int,
        var alphaShift: Int
    )
}