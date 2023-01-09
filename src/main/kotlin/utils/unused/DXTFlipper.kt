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
package utils.unused

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * DXTFlipper is a utility class used to flip along Y axis DXT compressed textures.
 *
 * @author Kirill Vainer
 */
object DXTFlipper {
    private val bb = ByteBuffer.allocate(8)

    init {
        bb.order(ByteOrder.LITTLE_ENDIAN)
    }

    private fun readCode5(data: Long, x: Int, y: Int): Long {
        val shift = ((4 * y + x) * 3).toLong()
        var mask: Long = 0x7
        mask = mask shl shift.toInt()
        var code = data and mask
        code = code shr shift.toInt()
        return code
    }

    private fun writeCode5(data: Long, x: Int, y: Int, code: Long): Long {
        var data = data
        var code = code
        val shift = ((4 * y + x) * 3).toLong()
        var mask: Long = 0x7
        code = code and mask shl shift.toInt()
        mask = mask shl shift.toInt()
        mask = mask.inv()
        data = data and mask
        data = data or code // write new code
        return data
    }

    private fun flipDXT5Block(block: ByteArray, h: Int) {
        if (h == 1) return
        val c0 = block[0]
        val c1 = block[1]
        bb.clear()
        bb.put(block, 2, 6).flip()
        bb.clear()
        val l = bb.long
        var n = l
        if (h == 2) {
            n = writeCode5(n, 0, 0, readCode5(l, 0, 1))
            n = writeCode5(n, 1, 0, readCode5(l, 1, 1))
            n = writeCode5(n, 2, 0, readCode5(l, 2, 1))
            n = writeCode5(n, 3, 0, readCode5(l, 3, 1))
            n = writeCode5(n, 0, 1, readCode5(l, 0, 0))
            n = writeCode5(n, 1, 1, readCode5(l, 1, 0))
            n = writeCode5(n, 2, 1, readCode5(l, 2, 0))
            n = writeCode5(n, 3, 1, readCode5(l, 3, 0))
        } else {
            n = writeCode5(n, 0, 0, readCode5(l, 0, 3))
            n = writeCode5(n, 1, 0, readCode5(l, 1, 3))
            n = writeCode5(n, 2, 0, readCode5(l, 2, 3))
            n = writeCode5(n, 3, 0, readCode5(l, 3, 3))
            n = writeCode5(n, 0, 1, readCode5(l, 0, 2))
            n = writeCode5(n, 1, 1, readCode5(l, 1, 2))
            n = writeCode5(n, 2, 1, readCode5(l, 2, 2))
            n = writeCode5(n, 3, 1, readCode5(l, 3, 2))
            n = writeCode5(n, 0, 2, readCode5(l, 0, 1))
            n = writeCode5(n, 1, 2, readCode5(l, 1, 1))
            n = writeCode5(n, 2, 2, readCode5(l, 2, 1))
            n = writeCode5(n, 3, 2, readCode5(l, 3, 1))
            n = writeCode5(n, 0, 3, readCode5(l, 0, 0))
            n = writeCode5(n, 1, 3, readCode5(l, 1, 0))
            n = writeCode5(n, 2, 3, readCode5(l, 2, 0))
            n = writeCode5(n, 3, 3, readCode5(l, 3, 0))
        }
        bb.clear()
        bb.putLong(n)
        bb.clear()
        bb[block, 2, 6].flip()
        assert(c0 == block[0] && c1 == block[1])
    }

    private fun flipDXT3Block(block: ByteArray, h: Int) {
        if (h == 1) return

        // first row
        var tmp0 = block[0]
        var tmp1 = block[1]
        if (h == 2) {
            block[0] = block[2]
            block[1] = block[3]
            block[2] = tmp0
            block[3] = tmp1
        } else {
            // write last row to first row
            block[0] = block[6]
            block[1] = block[7]

            // write first row to last row
            block[6] = tmp0
            block[7] = tmp1

            // 2nd row
            tmp0 = block[2]
            tmp1 = block[3]

            // write 3rd row to 2nd
            block[2] = block[4]
            block[3] = block[5]

            // write 2nd row to 3rd
            block[4] = tmp0
            block[5] = tmp1
        }
    }

    /**
     * Flips a DXT color block or a DXT3 alpha block
     * @param block
     * @param h
     */
    private fun flipDXT1orDXTA3Block(block: ByteArray, h: Int) {
        var tmp: Byte
        when (h) {
            1 -> return
            2 -> {
                // keep header intact (the two colors)
                // header takes 4 bytes

                // flip only two top rows
                tmp = block[4 + 1]
                block[4 + 1] = block[4 + 0]
                block[4 + 0] = tmp
                return
            }

            else -> {
                // keep header intact (the two colors)
                // header takes 4 bytes

                // flip first & fourth row
                tmp = block[4 + 3]
                block[4 + 3] = block[4 + 0]
                block[4 + 0] = tmp

                // flip second and third row
                tmp = block[4 + 2]
                block[4 + 2] = block[4 + 1]
                block[4 + 1] = tmp
                return
            }
        }
    }

    fun flipDXT(img: ByteBuffer, w: Int, h: Int, format: Format?): ByteBuffer? {
        val originalLimit = img.limit()
        val blocksX = Math.ceil((w / 4f).toDouble()).toInt()
        val blocksY = Math.ceil((h / 4f).toDouble()).toInt()
        val type: Int
        type = when (format) {
            Format.DXT1, Format.DXT1A -> 1
            Format.DXT3 -> 2
            Format.DXT5 -> 3
            Format.RGTC2, Format.SIGNED_RGTC2 -> 4
            Format.RGTC1, Format.SIGNED_RGTC1 -> 5
            else -> throw IllegalArgumentException("No flip support for texture format $format")
        }

        // DXT1 uses 8 bytes per block,
        // DXT3, DXT5, LATC use 16 bytes per block
        val bpb = if (type == 1 || type == 5) 8 else 16
        val retImg = ByteBuffer.allocate(blocksX * blocksY * bpb)
        if (h == 1) {
            retImg.put(img)
            retImg.rewind()
        } else if (h == 2) {
            val colorBlock = ByteArray(8)
            val alphaBlock = if (type != 1 && type != 5) ByteArray(8) else null
            for (x in 0 until blocksX) {
                // prepare for block reading
                val blockByteOffset = x * bpb
                img.position(blockByteOffset)
                img.limit(blockByteOffset + bpb)
                if (alphaBlock != null) {
                    img[alphaBlock]
                    when (type) {
                        2 -> flipDXT3Block(alphaBlock, h)
                        3, 4 -> flipDXT5Block(alphaBlock, h)
                    }
                    retImg.put(alphaBlock)
                }
                img[colorBlock]
                if (type == 4 || type == 5) flipDXT5Block(colorBlock, h) else flipDXT1orDXTA3Block(colorBlock, h)

                // write block (no need to flip block indexes, only pixels
                // inside block
                retImg.put(colorBlock)
            }
            retImg.rewind()
        } else if (h >= 4) {
            val colorBlock = ByteArray(8)
            val alphaBlock = if (type != 1 && type != 5) ByteArray(8) else null
            for (y in 0 until blocksY) {
                for (x in 0 until blocksX) {
                    // prepare for block reading
                    var blockIdx = y * blocksX + x
                    var blockByteOffset = blockIdx * bpb
                    img.position(blockByteOffset)
                    img.limit(blockByteOffset + bpb)
                    blockIdx = (blocksY - y - 1) * blocksX + x
                    blockByteOffset = blockIdx * bpb
                    retImg.position(blockByteOffset)
                    retImg.limit(blockByteOffset + bpb)
                    if (alphaBlock != null) {
                        img[alphaBlock]
                        when (type) {
                            2 -> flipDXT3Block(alphaBlock, h)
                            3, 4 -> flipDXT5Block(alphaBlock, h)
                        }
                        retImg.put(alphaBlock)
                    }
                    img[colorBlock]
                    if (type == 4 || type == 5) flipDXT5Block(colorBlock, h) else flipDXT1orDXTA3Block(colorBlock, h)
                    retImg.put(colorBlock)
                }
            }
            retImg.limit(retImg.capacity())
            retImg.position(0)
        } else {
            return null
        }
        img.limit(originalLimit) // make sure to restore original limit.
        return retImg
    }
}