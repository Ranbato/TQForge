package utils

import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer

/**
 * Simple [InputStream] implementation that exposes currently
 * available content of a [ByteBuffer].
 */
class ByteBufferBackedInputStream(protected val _b: ByteBuffer) : InputStream() {
    override fun available(): Int {
        return _b.remaining()
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return if (_b.hasRemaining()) _b.get().toInt() and 0xFF else -1
    }

    @Throws(IOException::class)
    override fun read(bytes: ByteArray, off: Int, len: Int): Int {
        var len = len
        if (!_b.hasRemaining()) return -1
        len = Math.min(len, _b.remaining())
        _b[bytes, off, len]
        return len
    }
}