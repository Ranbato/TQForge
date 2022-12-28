/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package utils

import java.io.*

/**
 * An implementation of [DataInput] that uses little-endian byte ordering for reading `short`, `int`, `float`, `double`, and `long` values.
 *
 *
 * **Note:** This class intentionally violates the specification of its supertype `DataInput`, which explicitly requires big-endian byte order.
 *
 * @author Chris Nokleberg
 * @author Keith Bottner
 * @since 8.0
 */
class LittleEndianDataInputStream
/**
 * Creates a `LittleEndianDataInputStream` that wraps the given stream.
 *
 * @param in the stream to delegate to
 */
    (`in`: InputStream?) : FilterInputStream(`in`), DataInput {
    /** This method will throw an [UnsupportedOperationException].  */
    override fun readLine(): String {
        throw UnsupportedOperationException("readLine is not supported")
    }
    /**
     * Attempts to read enough bytes from the stream to fill the given byte array, with the same
     * behavior as [DataInput.readFully]. Does not close the stream.
     *
     * @param in the input stream to read from.
     * @param b the buffer into which the data is read.
     * @throws EOFException if this stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    /**
     * Attempts to read `len` bytes from the stream into the given array starting at `off`, with the same behavior as [DataInput.readFully]. Does not close
     * the stream.
     *
     * @param in the input stream to read from.
     * @param b the buffer into which the data is read.
     * @param off an int specifying the offset into the data.
     * @param len an int specifying the number of bytes to read.
     * @throws EOFException if this stream reaches the end before reading all the bytes.
     * @throws IOException if an I/O error occurs.
     */
    @Throws(IOException::class)
    override fun readFully(b: ByteArray, off: Int, len: Int) {
        val read = read(b, off, len)
        if (read != len) {
            throw EOFException(
                "reached end of stream after reading $read bytes; $len bytes expected"
            )
        }
    }

    @Throws(IOException::class)
    override fun readFully(b: ByteArray) {
        readFully(b, 0, b.size)
    }

    @Throws(IOException::class)
    override fun skipBytes(n: Int): Int {
        return `in`.skip(n.toLong()).toInt()
    }

    @Throws(IOException::class)
    override fun readUnsignedByte(): Int {
        val b1 = `in`.read()
        if (0 > b1) {
            throw EOFException()
        }
        return b1
    }

    /**
     * Reads an unsigned `short` as specified by [DataInputStream.readUnsignedShort],
     * except using little-endian byte order.
     *
     * @return the next two bytes of the input stream, interpreted as an unsigned 16-bit integer in
     * little-endian byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readUnsignedShort(): Int {
        val b1 = readAndCheckByte()
        val b2 = readAndCheckByte()
        return fromBytes(0.toByte(), 0.toByte(), b2, b1)
    }

    /**
     * Returns the `int` value whose byte representation is the given 4 bytes, in big-endian
     * order; equivalent to `Ints.fromByteArray(new byte[] {b1, b2, b3, b4})`.
     *
     * @since 7.0
     */
    private fun fromBytes(b1: Byte, b2: Byte, b3: Byte, b4: Byte): Int {
        return b1.toInt() shl 24 or (b2.toInt() and 0xFF shl 16) or (b3.toInt() and 0xFF shl 8) or (b4.toInt() and 0xFF)
    }

    /**
     * Reads an integer as specified by [DataInputStream.readInt], except using little-endian
     * byte order.
     *
     * @return the next four bytes of the input stream, interpreted as an `int` in little-endian
     * byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readInt(): Int {
        val b1 = readAndCheckByte()
        val b2 = readAndCheckByte()
        val b3 = readAndCheckByte()
        val b4 = readAndCheckByte()
        return fromBytes(b4, b3, b2, b1)
    }

    /**
     * Reads a `long` as specified by [DataInputStream.readLong], except using
     * little-endian byte order.
     *
     * @return the next eight bytes of the input stream, interpreted as a `long` in
     * little-endian byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readLong(): Long {
        val b1 = readAndCheckByte()
        val b2 = readAndCheckByte()
        val b3 = readAndCheckByte()
        val b4 = readAndCheckByte()
        val b5 = readAndCheckByte()
        val b6 = readAndCheckByte()
        val b7 = readAndCheckByte()
        val b8 = readAndCheckByte()
        return fromBytes(b8, b7, b6, b5, b4, b3, b2, b1)
    }

    /**
     * Reads a `float` as specified by [DataInputStream.readFloat], except using
     * little-endian byte order.
     *
     * @return the next four bytes of the input stream, interpreted as a `float` in
     * little-endian byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readFloat(): Float {
        return java.lang.Float.intBitsToFloat(readInt())
    }

    /**
     * Reads a `double` as specified by [DataInputStream.readDouble], except using
     * little-endian byte order.
     *
     * @return the next eight bytes of the input stream, interpreted as a `double` in
     * little-endian byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readDouble(): Double {
        return java.lang.Double.longBitsToDouble(readLong())
    }

    @Throws(IOException::class)
    override fun readUTF(): String {
        return DataInputStream(`in`).readUTF()
    }

    /**
     * Reads a `short` as specified by [DataInputStream.readShort], except using
     * little-endian byte order.
     *
     * @return the next two bytes of the input stream, interpreted as a `short` in little-endian
     * byte order.
     * @throws IOException if an I/O error occurs.
     */
    @Throws(IOException::class)
    override fun readShort(): Short {
        return readUnsignedShort().toShort()
    }

    /**
     * Reads a char as specified by [DataInputStream.readChar], except using little-endian
     * byte order.
     *
     * @return the next two bytes of the input stream, interpreted as a `char` in little-endian
     * byte order
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun readChar(): Char {
        return readUnsignedShort().toChar()
    }

    @Throws(IOException::class)
    override fun readByte(): Byte {
        return readUnsignedByte().toByte()
    }

    @Throws(IOException::class)
    override fun readBoolean(): Boolean {
        return readUnsignedByte() != 0
    }

    /**
     * Reads a byte from the input stream checking that the end of file (EOF) has not been
     * encountered.
     *
     * @return byte read from input
     * @throws IOException if an error is encountered while reading
     * @throws EOFException if the end of file (EOF) is encountered.
     */
    @Throws(IOException::class, EOFException::class)
    private fun readAndCheckByte(): Byte {
        val b1 = `in`.read()
        if (-1 == b1) {
            throw EOFException()
        }
        return b1.toByte()
    }

    companion object {
        /**
         * Returns the `long` value whose byte representation is the given 8 bytes, in big-endian
         * order; equivalent to `Longs.fromByteArray(new byte[] {b1, b2, b3, b4, b5, b6, b7, b8})`.
         *
         * @since 7.0
         */
        fun fromBytes(
            b1: Byte, b2: Byte, b3: Byte, b4: Byte, b5: Byte, b6: Byte, b7: Byte, b8: Byte
        ): Long {
            return b1.toLong() and 0xFFL shl 56 or (b2.toLong() and 0xFFL shl 48
                    ) or (b3.toLong() and 0xFFL shl 40
                    ) or (b4.toLong() and 0xFFL shl 32
                    ) or (b5.toLong() and 0xFFL shl 24
                    ) or (b6.toLong() and 0xFFL shl 16
                    ) or (b7.toLong() and 0xFFL shl 8
                    ) or (b8.toLong() and 0xFFL)
        }
    }
}