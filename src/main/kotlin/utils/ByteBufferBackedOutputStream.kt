package utils

import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer


class ByteBufferBackedOutputStream(val _b: ByteBuffer) : OutputStream() {

    @Throws(IOException::class)
    override  // java.io.OutputStream
    fun write(i: Int) {
        _b.put(i.toByte())
    }

    @Throws(IOException::class)
    override  // java.io.OutputStream
    fun write(bArr: ByteArray?, i: Int, i2: Int) {
        _b.put(bArr, i, i2)
    }
}