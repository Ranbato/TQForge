/*
 * Copyright (c) 2009-2021 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package utils

import ddsutil.ByteBufferedImage
import org.jetbrains.skia.Bitmap
import org.jetbrains.skiko.toBitmap
import util.ImageUtils
import java.awt.image.BufferedImage
import java.io.DataInput
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.logging.Level
import java.util.logging.Logger

/**
 *
 * `DDSLoader` is an image loader that reads in a DirectX DDS file.
 * Supports DXT1, DXT3, DXT5, RGB, RGBA, Grayscale, Alpha pixel formats.
 * 2D images, mipmapped 2D images, and cubemaps.
 *
 * @author Gareth Jenkins-Jones
 * @author Kirill Vainer
 * @version $Id: DDSLoader.java,v 2.0 2008/8/15
 */
//private val logger = mu.KotlinLogging.logger {}
class DDSLoader {
    private var width = 0
    private var height = 0
    private var depth = 0
    private var flags = 0
    private var pitchOrSize = 0
    private var mipMapCount = 0
    private var caps1 = 0
    private var caps2 = 0
    private var directx10 = false
    private var compressed = false
    private var texture3D = false
    private var grayscaleOrAlpha = false
    private var pixelFormat: Format? = null
    private var bpp = 0
    private var sizes: IntArray = IntArray(0)
    private var redMask = 0
    private var greenMask = 0
    private var blueMask = 0
    private var alphaMask = 0
    private var `in`: DataInput? = null
    @Throws(IOException::class)
    fun load(info: String?): Any? {
        FileInputStream(info).use { stream ->
            `in` = LittleEndien(stream)
            loadHeader()
            //            if (texture3D) {
//                textureKey.setTextureTypeHint(Texture.Type.ThreeDimensional);
//            } else if (depth > 1) {
//                textureKey.setTextureTypeHint(Texture.Type.CubeMap);
//            }
            val data = readData(false)
            //            return new Image(pixelFormat, width, height, depth, data, sizes, ColorSpace.sRGB);
            val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            //            image.setRGB(0, 0, width, height, data.get(0), 0, width);
            return null
        }
    }

    @Throws(IOException::class)
    fun load(stream: InputStream?): Bitmap {
        `in` = LittleEndien(stream)
        loadHeader()
        val data = readData(false)
        //        return new Image(pixelFormat, width, height, depth, data, sizes, ColorSpace.sRGB);
//        val bi = ByteBufferedImage(width,height,data)

        return DDSDataToBMP(data[0].array())
    }

    /**
     * Fixup that isn't working completely
     */
    private fun DDSDataToBMP(numArray: ByteArray):Bitmap{
        val image = ByteBufferedImage(width, height,numArray)
        return image.toBitmap()
    }

    @Throws(IOException::class)
    private fun loadDX10Header() {
        val dxgiFormat = `in`!!.readInt()
        setPixelFormat(dxgiFormat)
        compressed = true
        val resDim = `in`!!.readInt()
        if (resDim == DX10DIM_TEXTURE3D) {
            texture3D = true
        }
        val miscFlag = `in`!!.readInt()
        val arraySize = `in`!!.readInt()
        if (`is`(miscFlag, DX10MISC_TEXTURECUBE)) {
            // mark texture as cube
            if (arraySize != 6) {
                throw IOException("Cubemaps should consist of 6 images!")
            }
        }
        `in`!!.skipBytes(4) // skip reserved value
    }

    @Throws(IOException::class)
    private fun setPixelFormat(dxgiFormat: Int) {
        pixelFormat = when (dxgiFormat) {
            DXGIFormat.DXGI_FORMAT_UNKNOWN -> Format.ETC1
            DXGIFormat.DXGI_FORMAT_BC1_UNORM -> Format.DXT1
            DXGIFormat.DXGI_FORMAT_BC2_UNORM -> Format.DXT3
            DXGIFormat.DXGI_FORMAT_BC3_UNORM -> Format.DXT5
            DXGIFormat.DXGI_FORMAT_BC4_UNORM -> Format.RGTC1
            DXGIFormat.DXGI_FORMAT_BC4_SNORM -> Format.SIGNED_RGTC1
            DXGIFormat.DXGI_FORMAT_BC5_UNORM -> Format.RGTC2
            DXGIFormat.DXGI_FORMAT_BC5_SNORM -> Format.SIGNED_RGTC2
            DXGIFormat.DXGI_FORMAT_BC6H_UF16 -> Format.BC6H_UF16
            DXGIFormat.DXGI_FORMAT_BC6H_SF16 -> Format.BC6H_SF16
            DXGIFormat.DXGI_FORMAT_BC7_UNORM -> Format.BC7_UNORM
            DXGIFormat.DXGI_FORMAT_BC7_UNORM_SRGB -> Format.BC7_UNORM_SRGB
            else -> throw IOException("Unsupported DX10 format: $dxgiFormat")
        }
        bpp = DXGIFormat.getBitsPerPixel(dxgiFormat)
    }

    /**
     * Reads the header (first 128 bytes) of a DDS File
     */
    @Throws(IOException::class)
    private fun loadHeader() {
        if (`in`!!.readInt() != 0x20534444 || `in`!!.readInt() != 124) {
            throw IOException("Not a DDS file")
        }
        flags = `in`!!.readInt()
        if (!`is`(flags, DDSD_MANDATORY) && !`is`(flags, DDSD_MANDATORY_DX10)) {
            throw IOException("Mandatory flags missing")
        }
        height = `in`!!.readInt()
        width = `in`!!.readInt()
        pitchOrSize = `in`!!.readInt()
        depth = `in`!!.readInt()
        mipMapCount = `in`!!.readInt()
        `in`!!.skipBytes(44)
        pixelFormat = null
        directx10 = false
        readPixelFormat()
        caps1 = `in`!!.readInt()
        caps2 = `in`!!.readInt()
        `in`!!.skipBytes(12)
        texture3D = false
        if (!directx10) {
            if (!`is`(caps1, DDSCAPS_TEXTURE)) {
                logger.warning("Texture is missing the DDSCAPS_TEXTURE-flag")
            }
            if (depth <= 0) {
                depth = 1
            }
            if (`is`(caps2, DDSCAPS2_CUBEMAP)) {
                depth = 6 // somewhat of a hack, force loading 6 textures if a cubemap
            }
            if (`is`(caps2, DDSCAPS2_VOLUME)) {
                texture3D = true
            }
        }
        val expectedMipmaps = 1 + Math.ceil(Math.log(Math.max(height, width).toDouble()) / LOG2).toInt()
        if (`is`(caps1, DDSCAPS_MIPMAP)) {
            if (!`is`(flags, DDSD_MIPMAPCOUNT)) {
                mipMapCount = expectedMipmaps
            } else if (mipMapCount != expectedMipmaps) {
                // changed to warning- images often do not have the required amount,
                // or specify that they have mipmaps but include only the top level.
                logger.log(Level.WARNING, "Got {0} mipmaps, expected {1}", arrayOf<Any>(mipMapCount, expectedMipmaps))
            }
        } else {
            mipMapCount = 1
        }
        if (directx10) {
            loadDX10Header()
        }
        loadSizes()
    }

    /**
     * Reads the PixelFormat structure in a DDS file
     */
    @Throws(IOException::class)
    private fun readPixelFormat() {
        val pfSize = `in`!!.readInt()
        if (pfSize != 32) {
            throw IOException("Pixel format size is $pfSize, not 32")
        }
        val pfFlags = `in`!!.readInt()
        `is`(pfFlags, DDPF_NORMAL)
        if (`is`(pfFlags, DDPF_FOURCC)) {
            compressed = true
            val fourcc = `in`!!.readInt()
            val swizzle = `in`!!.readInt()
            `in`!!.skipBytes(16)
            when (fourcc) {
                PF_DXT1 -> {
                    bpp = 4
                    pixelFormat = if (`is`(pfFlags, DDPF_ALPHAPIXELS)) {
                        Format.DXT1A
                    } else {
                        Format.DXT1
                    }
                }

                PF_DXT3 -> {
                    bpp = 8
                    pixelFormat = Format.DXT3
                }

                PF_DXT5 -> {
                    bpp = 8
                    pixelFormat = Format.DXT5
                }

                PF_ATI1 -> {
                    bpp = 4
                    pixelFormat = Format.RGTC1
                }

                PF_ATI2 -> {
                    bpp = 8
                    pixelFormat = Format.RGTC2
                }

                PF_DX10 -> {
                    compressed = false
                    directx10 = true
                    // exit here, the rest of the structure is not valid
                    // the real format will be available in the DX10 header
                    return
                }

                113 -> {
                    compressed = false
                    bpp = 64
                    pixelFormat = Format.RGBA16F
                }

                111 -> {
                    compressed = false
                    bpp = 16
                    pixelFormat = Format.Luminance16F
                    grayscaleOrAlpha = true
                }

                PF_BC4S -> {
                    bpp = 4
                    pixelFormat = Format.SIGNED_RGTC1
                }

                PF_BC5S -> {
                    bpp = 8
                    pixelFormat = Format.SIGNED_RGTC2
                }

                else -> throw IOException("Unknown fourcc: " + string(fourcc) + ", " + Integer.toHexString(fourcc))
            }
            val size = (width + 3) / 4 * ((height + 3) / 4) * bpp * 2
            if (`is`(flags, DDSD_LINEARSIZE)) {
                if (pitchOrSize == 0) {
                    logger.warning("Must use linear size with fourcc")
                    pitchOrSize = size
                } else if (pitchOrSize != size) {
                    logger.log(Level.WARNING, "Expected size = {0}, real = {1}", arrayOf<Any>(size, pitchOrSize))
                }
            } else {
                pitchOrSize = size
            }
        } else {
            compressed = false

            // skip fourCC
            `in`!!.readInt()
            bpp = `in`!!.readInt()
            redMask = `in`!!.readInt()
            greenMask = `in`!!.readInt()
            blueMask = `in`!!.readInt()
            alphaMask = `in`!!.readInt()
            if (`is`(pfFlags, DDPF_RGB)) {
                pixelFormat = if (`is`(pfFlags, DDPF_ALPHAPIXELS)) {
                    if (bpp == 16) {
                        Format.RGB5A1
                    } else {
                        Format.RGBA8
                    }
                } else {
                    if (bpp == 16) {
                        Format.RGB565
                    } else {
                        Format.RGB8
                    }
                }
            } else if (`is`(pfFlags, DDPF_GRAYSCALE) && `is`(pfFlags, DDPF_ALPHAPIXELS)) {
                pixelFormat = when (bpp) {
                    16 -> Format.Luminance8Alpha8
                    else -> throw IOException("Unsupported GrayscaleAlpha BPP: $bpp")
                }
                grayscaleOrAlpha = true
            } else if (`is`(pfFlags, DDPF_GRAYSCALE)) {
                pixelFormat = when (bpp) {
                    8 -> Format.Luminance8
                    else -> throw IOException("Unsupported Grayscale BPP: $bpp")
                }
                grayscaleOrAlpha = true
            } else if (`is`(pfFlags, DDPF_ALPHA)) {
                pixelFormat = when (bpp) {
                    8 -> Format.Alpha8
                    else -> throw IOException("Unsupported Alpha BPP: $bpp")
                }
                grayscaleOrAlpha = true
            } else {
                throw IOException("Unknown PixelFormat in DDS file")
            }
            val size = bpp / 8 * width
            if (`is`(flags, DDSD_LINEARSIZE)) {
                if (pitchOrSize == 0) {
                    logger.warning("Linear size said to contain valid value but does not")
                    pitchOrSize = size
                } else if (pitchOrSize != size) {
                    logger.log(Level.WARNING, "Expected size = {0}, real = {1}", arrayOf<Any>(size, pitchOrSize))
                }
            } else {
                pitchOrSize = size
            }
        }
    }

    /**
     * Computes the sizes of each mipmap level in bytes, and stores it in sizes_[].
     */
    private fun loadSizes() {
        var mipWidth = width
        var mipHeight = height
        sizes = IntArray(mipMapCount)
        val outBpp = pixelFormat!!.getBitsPerPixel()
        for (i in 0 until mipMapCount) {
            var size: Int
            size = if (compressed) {
                (mipWidth + 3) / 4 * ((mipHeight + 3) / 4) * outBpp * 2
            } else {
                mipWidth * mipHeight * outBpp / 8
            }
            sizes[i] = (size + 3) / 4 * 4
            mipWidth = Math.max(mipWidth / 2, 1)
            mipHeight = Math.max(mipHeight / 2, 1)
        }
    }

    /**
     * Flips the given image data on the Y axis.
     * @param data Data array containing image data (without mipmaps)
     * @param scanlineSize Size of a single scanline = width * bytesPerPixel
     * @param height Height of the image in pixels
     * @return The new data flipped by the Y axis
     */
    fun flipData(data: ByteArray, scanlineSize: Int, height: Int): ByteArray {
        val newData = ByteArray(data.size)
        for (y in 0 until height) {
            System.arraycopy(
                data, y * scanlineSize,
                newData, (height - y - 1) * scanlineSize,
                scanlineSize
            )
        }
        return newData
    }

    /**
     * Reads a grayscale image with mipmaps from the InputStream
     * @param flip Flip the loaded image by Y axis
     * @param totalSize Total size of the image in bytes including the mipmaps
     * @return A ByteBuffer containing the grayscale image data with mips.
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readGrayscale2D(flip: Boolean, totalSize: Int): ByteBuffer {
        val buffer = ByteBuffer.allocate(totalSize)
        if (bpp == 8) {
            logger.finest("Source image format: R8")
        }
        assert(bpp == pixelFormat!!.getBitsPerPixel())
        var mipWidth = width
        var mipHeight = height
        for (mip in 0 until mipMapCount) {
            var data = ByteArray(sizes[mip])
            `in`!!.readFully(data)
            if (flip) {
                data = flipData(data, mipWidth * bpp / 8, mipHeight)
            }
            buffer.put(data)
            mipWidth = Math.max(mipWidth / 2, 1)
            mipHeight = Math.max(mipHeight / 2, 1)
        }
        return buffer
    }

    /**
     * Reads an uncompressed RGB or RGBA image.
     *
     * @param flip Flip the image on the Y axis
     * @param totalSize Size of the image in bytes including mipmaps
     * @return ByteBuffer containing image data with mipmaps in the format specified by pixelFormat_
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readRGB2D(flip: Boolean, totalSize: Int): ByteBuffer {
        val redCount = count(redMask)
        val blueCount = count(blueMask)
        val greenCount = count(greenMask)
        val alphaCount = count(alphaMask)
        if (redMask == 0x00FF0000 && greenMask == 0x0000FF00 && blueMask == 0x000000FF) {
            if (alphaMask == -0x1000000 && bpp == 32) {
                logger.finest("Data source format: BGRA8")
            } else if (bpp == 24) {
                logger.finest("Data source format: BGR8")
            }
        }
        val sourcebytesPP = bpp / 8
        val targetBytesPP = pixelFormat!!.getBitsPerPixel() / 8
        val dataBuffer = ByteBuffer.allocate(totalSize)
        var mipWidth = width
        var mipHeight = height
        var offset = 0
        val b = ByteArray(sourcebytesPP)
        for (mip in 0 until mipMapCount) {
            for (y in 0 until mipHeight) {
                for (x in 0 until mipWidth) {
                    `in`!!.readFully(b)
                    val i = byte2int(b)
                    val red = (i and redMask shr redCount).toByte()
                    val green = (i and greenMask shr greenCount).toByte()
                    val blue = (i and blueMask shr blueCount).toByte()
                    val alpha = (i and alphaMask shr alphaCount).toByte()
                    if (flip) {
                        dataBuffer.position(offset + ((mipHeight - y - 1) * mipWidth + x) * targetBytesPP)
                    }
                    //else
                    //    dataBuffer.position(offset + (y * width + x) * targetBytesPP);
                    if (alphaMask == 0) {
                        dataBuffer.put(red).put(green).put(blue)
                    } else {
                        dataBuffer.put(alpha).put(red).put(green).put(blue)
                    }
                }
            }
            offset += mipWidth * mipHeight * targetBytesPP
            mipWidth = Math.max(mipWidth / 2, 1)
            mipHeight = Math.max(mipHeight / 2, 1)
        }
        return dataBuffer
    }

    /**
     * Reads a DXT compressed image from the InputStream
     *
     * @param flip trueflip image along the Y axis, falsedon't flip
     * @param totalSize Total size of the image in bytes, including mipmaps
     * @return ByteBuffer containing compressed DXT image in the format specified by pixelFormat_
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readDXT2D(flip: Boolean, totalSize: Int): ByteBuffer {
        logger.finest("Source image format: DXT")
        val buffer = ByteBuffer.allocate(totalSize)
        var mipWidth = width
        var mipHeight = height
        for (mip in 0 until mipMapCount) {
            if (flip) {
                val data = ByteArray(sizes[mip])
                `in`!!.readFully(data)
                val wrapped = ByteBuffer.wrap(data)
                wrapped.rewind()
                val flipped = DXTFlipper.flipDXT(wrapped, mipWidth, mipHeight, pixelFormat)
                buffer.put(flipped)
            } else {
                val data = ByteArray(sizes[mip])
                `in`!!.readFully(data)
                buffer.put(data)
            }
            mipWidth = Math.max(mipWidth / 2, 1)
            mipHeight = Math.max(mipHeight / 2, 1)
        }
        buffer.rewind()
        return buffer
    }

    /**
     * Reads a grayscale image with mipmaps from the InputStream
     * @param flip Flip the loaded image by Y axis
     * @param totalSize Total size of the image in bytes including the mipmaps
     * @return A ByteBuffer containing the grayscale image data with mips.
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readGrayscale3D(flip: Boolean, totalSize: Int): ByteBuffer {
        val buffer = ByteBuffer.allocate(totalSize * depth)
        if (bpp == 8) {
            logger.finest("Source image format: R8")
        }
        assert(bpp == pixelFormat!!.getBitsPerPixel())
        for (i in 0 until depth) {
            var mipWidth = width
            var mipHeight = height
            for (mip in 0 until mipMapCount) {
                var data = ByteArray(sizes[mip])
                `in`!!.readFully(data)
                if (flip) {
                    data = flipData(data, mipWidth * bpp / 8, mipHeight)
                }
                buffer.put(data)
                mipWidth = Math.max(mipWidth / 2, 1)
                mipHeight = Math.max(mipHeight / 2, 1)
            }
        }
        buffer.rewind()
        return buffer
    }

    /**
     * Reads an uncompressed RGB or RGBA image.
     *
     * @param flip Flip the image on the Y axis
     * @param totalSize Size of the image in bytes including mipmaps
     * @return ByteBuffer containing image data with mipmaps in the format specified by pixelFormat_
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readRGB3D(flip: Boolean, totalSize: Int): ByteBuffer {
        val redCount = count(redMask)
        val blueCount = count(blueMask)
        val greenCount = count(greenMask)
        val alphaCount = count(alphaMask)
        if (redMask == 0x00FF0000 && greenMask == 0x0000FF00 && blueMask == 0x000000FF) {
            if (alphaMask == -0x1000000 && bpp == 32) {
                logger.finest("Data source format: BGRA8")
            } else if (bpp == 24) {
                logger.finest("Data source format: BGR8")
            }
        }
        val sourcebytesPP = bpp / 8
        val targetBytesPP = pixelFormat!!.getBitsPerPixel() / 8
        val dataBuffer = ByteBuffer.allocate(totalSize * depth)
        for (k in 0 until depth) {
            //   ByteBuffer dataBuffer = ByteBuffer.allocate(totalSize);
            var mipWidth = width
            var mipHeight = height
            var offset = k * totalSize
            val b = ByteArray(sourcebytesPP)
            for (mip in 0 until mipMapCount) {
                for (y in 0 until mipHeight) {
                    for (x in 0 until mipWidth) {
                        `in`!!.readFully(b)
                        val i = byte2int(b)
                        val red = (i and redMask shr redCount).toByte()
                        val green = (i and greenMask shr greenCount).toByte()
                        val blue = (i and blueMask shr blueCount).toByte()
                        val alpha = (i and alphaMask shr alphaCount).toByte()
                        if (flip) {
                            dataBuffer.position(offset + ((mipHeight - y - 1) * mipWidth + x) * targetBytesPP)
                        }
                        //else
                        //    dataBuffer.position(offset + (y * width + x) * targetBytesPP);
                        if (alphaMask == 0) {
                            dataBuffer.put(red).put(green).put(blue)
                        } else {
                            dataBuffer.put(red).put(green).put(blue).put(alpha)
                        }
                    }
                }
                offset += mipWidth * mipHeight * targetBytesPP
                mipWidth = Math.max(mipWidth / 2, 1)
                mipHeight = Math.max(mipHeight / 2, 1)
            }
        }
        dataBuffer.rewind()
        return dataBuffer
    }

    /**
     * Reads a DXT compressed image from the InputStream
     *
     * @param flip trueflip image along the Y axis, falsedon't flip
     * @param totalSize Total size of the image in bytes, including mipmaps
     * @return ByteBuffer containing compressed DXT image in the format specified by pixelFormat_
     * @throws IOException If an error occurred while reading from InputStream
     */
    @Throws(IOException::class)
    fun readDXT3D(flip: Boolean, totalSize: Int): ByteBuffer {
        logger.finest("Source image format: DXT")
        val bufferAll = ByteBuffer.allocateDirect(totalSize * depth)
        for (i in 0 until depth) {
            val buffer = ByteBuffer.allocateDirect(totalSize)
            var mipWidth = width
            var mipHeight = height
            for (mip in 0 until mipMapCount) {
                if (flip) {
                    val data = ByteArray(sizes[mip])
                    `in`!!.readFully(data)
                    val wrapped = ByteBuffer.wrap(data)
                    wrapped.rewind()
                    val flipped = DXTFlipper.flipDXT(wrapped, mipWidth, mipHeight, pixelFormat)
                    flipped!!.rewind()
                    buffer.put(flipped)
                } else {
                    val data = ByteArray(sizes[mip])
                    `in`!!.readFully(data)
                    buffer.put(data)
                }
                mipWidth = Math.max(mipWidth / 2, 1)
                mipHeight = Math.max(mipHeight / 2, 1)
            }
            buffer.rewind()
            bufferAll.put(buffer)
        }
        return bufferAll
    }

    /**
     * Reads the image data from the InputStream in the required format.
     * If the file contains a cubemap image, it is loaded as 6 ByteBuffers
     * (potentially containing mipmaps if they were specified), otherwise
     * a single ByteBuffer is returned for a 2D image.
     *
     * @param flip Flip the image data or not.
     * For cubemaps, each of the cubemap faces is flipped individually.
     * If the image is DXT compressed, no flipping is done.
     * @return An ArrayList containing a single ByteBuffer for a 2D image, or 6 ByteBuffers for a cubemap.
     * The cubemap ByteBuffer order is PositiveX, NegativeX, PositiveY, NegativeY, PositiveZ, NegativeZ.
     *
     * @throws IOException If an error occurred while reading from the stream.
     */
    @Throws(IOException::class)
    fun readData(flip: Boolean): ArrayList<ByteBuffer> {
        var totalSize = 0
        for (i in sizes.indices) {
            totalSize += sizes[i]
        }
        val allMaps = ArrayList<ByteBuffer>()
        if (depth > 1 && !texture3D) {
            for (i in 0 until depth) {
                if (compressed) {
                    allMaps.add(readDXT2D(flip, totalSize))
                } else if (grayscaleOrAlpha) {
                    allMaps.add(readGrayscale2D(flip, totalSize))
                } else {
                    allMaps.add(readRGB2D(flip, totalSize))
                }
            }
        } else if (texture3D) {
            if (compressed) {
                allMaps.add(readDXT3D(flip, totalSize))
            } else if (grayscaleOrAlpha) {
                allMaps.add(readGrayscale3D(flip, totalSize))
            } else {
                allMaps.add(readRGB3D(flip, totalSize))
            }
        } else {
            if (compressed) {
                allMaps.add(readDXT2D(flip, totalSize))
            } else if (grayscaleOrAlpha) {
                allMaps.add(readGrayscale2D(flip, totalSize))
            } else {
                allMaps.add(readRGB2D(flip, totalSize))
            }
        }
        return allMaps
    }

    companion object {
        private val logger = Logger.getLogger(DDSLoader::class.java.name)
        private const val DDSD_MANDATORY = 0x1007
        private const val DDSD_MANDATORY_DX10 = 0x6
        private const val DDSD_MIPMAPCOUNT = 0x20000
        private const val DDSD_LINEARSIZE = 0x80000
        private const val DDPF_ALPHAPIXELS = 0x1
        private const val DDPF_FOURCC = 0x4
        private const val DDPF_RGB = 0x40

        // used by compressonator to mark grayscale images, red channel mask is used for data and bitcount is 8
        private const val DDPF_GRAYSCALE = 0x20000

        // used by compressonator to mark alpha images, alpha channel mask is used for data and bitcount is 8
        private const val DDPF_ALPHA = 0x2

        // used by NVTextureTools to mark normal images.
        private const val DDPF_NORMAL = -0x80000000
        private const val DDSCAPS_TEXTURE = 0x1000
        private const val DDSCAPS_MIPMAP = 0x400000
        private const val DDSCAPS2_CUBEMAP = 0x200
        private const val DDSCAPS2_VOLUME = 0x200000
        private const val PF_DXT1 = 0x31545844
        private const val PF_DXT3 = 0x33545844
        private const val PF_DXT5 = 0x35545844
        private const val PF_ATI1 = 0x31495441
        private const val PF_ATI2 = 0x32495441 // 0x41544932;
        private const val PF_DX10 = 0x30315844 // a DX10 format
        private const val PF_BC4S = 0x53344342 // a DX9 file format for BC4 signed
        private const val PF_BC5S = 0x53354342 // a DX9 file format for BC5 signed
        private const val DX10DIM_TEXTURE3D = 0x4
        private const val DX10MISC_TEXTURECUBE = 0x4
        private val LOG2 = Math.log(2.0)

        /**
         * Checks if flags contains the specified mask
         */
        private fun `is`(flags: Int, mask: Int): Boolean {
            return flags and mask == mask
        }

        /**
         * Counts the amount of bits needed to shift till bitmask n is at zero
         * @param n Bitmask to test
         */
        private fun count(n: Int): Int {
            var n = n
            if (n == 0) {
                return 0
            }
            var i = 0
            while (n and 0x1 == 0) {
                n = n shr 1
                i++
                if (i > 32) {
                    throw RuntimeException(Integer.toHexString(n))
                }
            }
            return i
        }

        /**
         * Converts a 1 to 4 sized byte array to an integer
         */
        private fun byte2int(b: ByteArray): Int {
            return if (b.size == 1) {
                b[0].toInt() and 0xFF
            } else if (b.size == 2) {
                b[0].toInt() and 0xFF or (b[1].toInt() and 0xFF shl 8)
            } else if (b.size == 3) {
                b[0].toInt() and 0xFF or (b[1].toInt() and 0xFF shl 8) or (b[2].toInt() and 0xFF shl 16)
            } else if (b.size == 4) {
                b[0].toInt() and 0xFF or (b[1].toInt() and 0xFF shl 8) or (b[2]
                    .toInt() and 0xFF shl 16) or (b[3].toInt() and 0xFF shl 24)
            } else {
                0
            }
        }

        /**
         * Converts an int representing a FourCC into a String
         */
        private fun string(value: Int): String {
            val buf = StringBuilder()
            buf.append((value and 0xFF).toChar())
            buf.append((value and 0xFF00 shr 8).toChar())
            buf.append((value and 0xFF0000 shr 16).toChar())
            buf.append((value and 0xFF00000 shr 24).toChar())
            return buf.toString()
        }
    }
}