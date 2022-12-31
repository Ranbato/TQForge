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

import java.io.BufferedInputStream
import java.io.DataInput
import java.io.IOException
import java.io.InputStream

/**
 * `LittleEndien` is a class to read little-endian stored data
 * via an InputStream.  All functions work as defined in DataInput, but
 * assume they come from a LittleEndien input stream.  Currently used to read .ms3d and .3ds files.
 * @author Jack Lindamood
 */
class LittleEndien(`in`: InputStream?) : InputStream(), DataInput {
    protected var `in`: BufferedInputStream

    /**
     * Creates a new LittleEndien reader from the given input stream.  The
     * stream is wrapped in a BufferedReader automatically.
     * @param in The input stream to read from.
     */
    init {
        this.`in` = BufferedInputStream(`in`)
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return `in`.read()
    }

    @Throws(IOException::class)
    override fun read(buf: ByteArray): Int {
        return `in`.read(buf)
    }

    @Throws(IOException::class)
    override fun read(buf: ByteArray, off: Int, len: Int): Int {
        return `in`.read(buf, off, len)
    }

    @Throws(IOException::class)
    override fun readUnsignedShort(): Int {
        return `in`.read() and 0xff or (`in`.read() and 0xff shl 8)
    }

    /**
     * read an unsigned int as a long
     *
     * @return the value that was read
     * @throws IOException if an I/O error occurs while reading
     */
    @Throws(IOException::class)
    fun readUInt(): Long {
        return (`in`.read() and 0xff
                or (`in`.read() and 0xff shl 8)
                or (`in`.read() and 0xff shl 16)
                ).toLong() or ((`in`.read() and 0xff).toLong() shl 24)
    }

    @Throws(IOException::class)
    override fun readBoolean(): Boolean {
        return `in`.read() != 0
    }

    @Throws(IOException::class)
    override fun readByte(): Byte {
        return `in`.read().toByte()
    }

    @Throws(IOException::class)
    override fun readUnsignedByte(): Int {
        return `in`.read()
    }

    @Throws(IOException::class)
    override fun readShort(): Short {
        return readUnsignedShort().toShort()
    }

    @Throws(IOException::class)
    override fun readChar(): Char {
        return readUnsignedShort().toChar()
    }

    @Throws(IOException::class)
    override fun readInt(): Int {
        return (`in`.read() and 0xff
                or (`in`.read() and 0xff shl 8)
                or (`in`.read() and 0xff shl 16)
                or (`in`.read() and 0xff shl 24))
    }

    @Throws(IOException::class)
    override fun readLong(): Long {
        return ((`in`.read() and 0xff)
            .toLong()
                or ((`in`.read() and 0xff).toLong() shl 8)
                or ((`in`.read() and 0xff).toLong() shl 16)
                or ((`in`.read() and 0xff).toLong() shl 24)
                or ((`in`.read() and 0xff).toLong() shl 32)
                or ((`in`.read() and 0xff).toLong() shl 40)
                or ((`in`.read() and 0xff).toLong() shl 48)
                or ((`in`.read() and 0xff).toLong() shl 56))
    }

    @Throws(IOException::class)
    override fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readInt())
    }

    @Throws(IOException::class)
    override fun readDouble(): Double {
        return java.lang.Double.longBitsToDouble(readLong())
    }

    @Throws(IOException::class)
    override fun readFully(b: ByteArray) {
        `in`.read(b, 0, b.size)
    }

    @Throws(IOException::class)
    override fun readFully(b: ByteArray, off: Int, len: Int) {
        `in`.read(b, off, len)
    }

    @Throws(IOException::class)
    override fun skipBytes(n: Int): Int {
        return `in`.skip(n.toLong()).toInt()
    }

    @Throws(IOException::class)
    override fun readLine(): String {
        throw IOException("Unsupported operation")
    }

    @Throws(IOException::class)
    override fun readUTF(): String {
        throw IOException("Unsupported operation")
    }

    @Throws(IOException::class)
    override fun close() {
        `in`.close()
    }

    @Throws(IOException::class)
    override fun available(): Int {
        return `in`.available()
    }
}