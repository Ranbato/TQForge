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
 * An implementation of [DataOutput] that uses little-endian byte ordering for writing `char`, `short`, `int`, `float`, `double`, and `long` values.
 *
 *
 * **Note:** This class intentionally violates the specification of its supertype `DataOutput`, which explicitly requires big-endian byte order.
 *
 * @author Chris Nokleberg
 * @author Keith Bottner
 * @since 8.0
 */
class LittleEndianDataOutputStream
/**
 * Creates a `LittleEndianDataOutputStream` that wraps the given stream.
 *
 * @param out the stream to delegate to
 */
    (out: OutputStream?) : FilterOutputStream(DataOutputStream(out)), DataOutput {
    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        // Override slow FilterOutputStream impl
        out.write(b, off, len)
    }

    @Throws(IOException::class)
    override fun writeBoolean(v: Boolean) {
        (out as DataOutputStream).writeBoolean(v)
    }

    @Throws(IOException::class)
    override fun writeByte(v: Int) {
        (out as DataOutputStream).writeByte(v)
    }

    @Deprecated(
        """The semantics of {@code writeBytes(String s)} are considered dangerous. Please use
        {@link #writeUTF(String s)}, {@link #writeChars(String s)} or another write method instead."""
    )
    @Throws(
        IOException::class
    )
    override fun writeBytes(s: String) {
        (out as DataOutputStream).writeBytes(s)
    }

    /**
     * Writes a char as specified by [DataOutputStream.writeChar], except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeChar(v: Int) {
        writeShort(v)
    }

    /**
     * Writes a `String` as specified by [DataOutputStream.writeChars], except
     * each character is written using little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeChars(s: String) {
        for (i in 0 until s.length) {
            writeChar(s[i].code)
        }
    }

    /**
     * Writes a `double` as specified by [DataOutputStream.writeDouble], except
     * using little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeDouble(v: Double) {
        writeLong(java.lang.Double.doubleToLongBits(v))
    }

    /**
     * Writes a `float` as specified by [DataOutputStream.writeFloat], except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeFloat(v: Float) {
        writeInt(java.lang.Float.floatToIntBits(v))
    }

    /**
     * Writes an `int` as specified by [DataOutputStream.writeInt], except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeInt(v: Int) {
        out.write(0xFF and v)
        out.write(0xFF and (v shr 8))
        out.write(0xFF and (v shr 16))
        out.write(0xFF and (v shr 24))
    }

    /**
     * Writes a `long` as specified by [DataOutputStream.writeLong], except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeLong(v: Long) {
        val bytes = toByteArray(reverseBytes(v))
        write(bytes, 0, bytes.size)
    }

    /**
     * Returns the value obtained by reversing the order of the bytes in the
     * two's complement representation of the specified `long` value.
     *
     * @param i the value whose bytes are to be reversed
     * @return the value obtained by reversing the bytes in the specified
     * `long` value.
     * @since 1.5
     */
    private fun reverseBytes(i: Long): Long {
        var i = i
        i = i and 0x00ff00ff00ff00ffL shl 8 or (i ushr 8 and 0x00ff00ff00ff00ffL)
        return i shl 48 or (i and 0xffff0000L shl 16) or
                (i ushr 16 and 0xffff0000L) or (i ushr 48)
    }

    /**
     * Returns a big-endian representation of `value` in an 8-element byte array; equivalent to
     * `ByteBuffer.allocate(8).putLong(value).array()`. For example, the input value `0x1213141516171819L` would yield the byte array `{0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
     * 0x18, 0x19}`.
     *
     */
    private fun toByteArray(value: Long): ByteArray {
        // Note that this code needs to stay compatible with GWT, which has known
        // bugs when narrowing byte casts of long values occur.
        var value = value
        val result = ByteArray(8)
        for (i in 7 downTo 0) {
            result[i] = (value and 0xffL).toByte()
            value = value shr 8
        }
        return result
    }

    /**
     * Writes a `short` as specified by [DataOutputStream.writeShort], except using
     * little-endian byte order.
     *
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun writeShort(v: Int) {
        out.write(0xFF and v)
        out.write(0xFF and (v shr 8))
    }

    @Throws(IOException::class)
    override fun writeUTF(str: String) {
        (out as DataOutputStream).writeUTF(str)
    }

    // Overriding close() because FilterOutputStream's close() method pre-JDK8 has bad behavior:
    // it silently ignores any exception thrown by flush(). Instead, just close the delegate stream.
    // It should flush itself if necessary.
    @Throws(IOException::class)
    override fun close() {
        out.close()
    }
}