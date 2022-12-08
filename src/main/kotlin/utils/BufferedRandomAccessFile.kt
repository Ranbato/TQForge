import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*

/**
 * A `BufferedRandomAccessFile` is like a
 * `RandomAccessFile`, but it uses a private buffer so that most
 * operations do not require a disk access.
 * <P>
 *
 * Note: The operations on this class are unmonitored. Also, the correct
 * functioning of the `RandomAccessFile` methods that are not
 * overridden here relies on the implementation of those methods in the
 * superclass.
 * Author : Avinash Lakshman ( alakshman@facebook.com) & Prashant Malik ( pmalik@facebook.com )
</P> */
class BufferedRandomAccessFile : RandomAccessFile {
    /*
     * This implementation is based on the buffer implementation in Modula-3's
     * "Rd", "Wr", "RdClass", and "WrClass" interfaces.
     */
    private val logBuffSize = 16 // 64K buffer
    private val minBufferSize = 1 shl logBuffSize
    private val bufferMask = (minBufferSize.toLong() - 1L).inv()

    private var isDirty = false // true iff unflushed bytes exist
    private var isClosed = false // true iff the file is closed
    var filePointer: Long = 0 // current position in file
        private set
    private var buffLoPos: Long = 0
    private var buffHiPos: Long = 0 // bounds on characters in "buff"
    private lateinit var buffer // local buffer
            : ByteArray
    private var maxHi_: Long = 0 // this.lo + this.buff.length
    private var hitEOF = false // buffer contains last file block?
    private var diskPosition: Long = 0 // disk position
    /*
     * To describe the above fields, we introduce the following abstractions for
     * the file "f":
     *
     * len(f) the length of the file curr(f) the current position in the file
     * c(f) the abstract contents of the file disk(f) the contents of f's
     * backing disk file closed(f) true iff the file is closed
     *
     * "curr(f)" is an index in the closed interval [0, len(f)]. "c(f)" is a
     * character sequence of length "len(f)". "c(f)" and "disk(f)" may differ if
     * "c(f)" contains unflushed writes not reflected in "disk(f)". The flush
     * operation has the effect of making "disk(f)" identical to "c(f)".
     *
     * A file is said to be *valid* if the following conditions hold:
     *
     * V1. The "closed" and "curr" fields are correct:
     *
     * f.closed == closed(f) f.curr == curr(f)
     *
     * V2. The current position is either contained in the buffer, or just past
     * the buffer:
     *
     * f.lo <= f.curr <= f.hi
     *
     * V3. Any (possibly) unflushed characters are stored in "f.buff":
     *
     * (forall i in [f.lo, f.curr): c(f)[i] == f.buff[i - f.lo])
     *
     * V4. For all characters not covered by V3, c(f) and disk(f) agree:
     *
     * (forall i in [f.lo, len(f)): i not in [f.lo, f.curr) => c(f)[i] ==
     * disk(f)[i])
     *
     * V5. "f.dirty" is true iff the buffer contains bytes that should be
     * flushed to the file; by V3 and V4, only part of the buffer can be dirty.
     *
     * f.dirty == (exists i in [f.lo, f.curr): c(f)[i] != f.buff[i - f.lo])
     *
     * V6. this.maxHi == this.lo + this.buff.length
     *
     * Note that "f.buff" can be "null" in a valid file, since the range of
     * characters in V3 is empty when "f.lo == f.curr".
     *
     * A file is said to be *ready* if the buffer contains the current position,
     * i.e., when:
     *
     * R1. !f.closed && f.buff != null && f.lo <= f.curr && f.curr < f.hi
     *
     * When a file is ready, reading or writing a single byte can be performed
     * by reading or writing the in-memory buffer without performing a disk
     * operation.
     */
    /**
     * Open a new `BufferedRandomAccessFile` on `file`
     * in mode `mode`, which should be "r" for reading only, or
     * "rw" for reading and writing.
     */
    constructor(file: File, mode: String) : super(file, mode) {
        init(0)
    }

    constructor(file: File, mode: String, size: Int) : super(file, mode) {
        init(size)
    }

    /**
     * Open a new `BufferedRandomAccessFile` on the file named
     * `name` in mode `mode`, which should be "r" for
     * reading only, or "rw" for reading and writing.
     */
    constructor(name: String?, mode: String?) : super(name, mode) {
        init(0)
    }

    constructor(name: String, mode: String, size: Int) : super(name, mode) {
        init(size)
    }

    private fun init(size: Int) {
        isClosed = false
        isDirty = false
        buffHiPos = 0
        filePointer =0
        buffLoPos = 0
        buffer = if (size > minBufferSize) ByteArray(size) else ByteArray(minBufferSize)
        maxHi_ = minBufferSize.toLong()
        hitEOF = false
        diskPosition = 0L
    }

    @Throws(IOException::class)
    override fun close() {
        flush()
        isClosed = true
        super.close()
    }

    /**
     * Flush any bytes in the file's buffer that have not yet been written to
     * disk. If the file was created read-only, this method is a no-op.
     */
    @Throws(IOException::class)
    fun flush() {
        flushBuffer()
    }

    /* Flush any dirty bytes in the buffer to disk. */
    @Throws(IOException::class)
    private fun flushBuffer() {
        if (isDirty) {
            if (diskPosition != buffLoPos) super.seek(buffLoPos)
            val len = (filePointer - buffLoPos).toInt()
            super.write(buffer, 0, len)
            diskPosition = filePointer
            isDirty = false
        }
    }

    /*
     * Read at most "this.buff.length" bytes into "this.buff", returning the
     * number of bytes read. If the return result is less than
     * "this.buff.length", then EOF was read.
     */
    @Throws(IOException::class)
    private fun fillBuffer(): Int {
        var cnt = 0
        var rem = buffer.size
        while (rem > 0) {
            val n: Int = super.read(buffer, cnt, rem)
            if (n < 0) break
            cnt += n
            rem -= n
        }
        if (cnt < 0 && (cnt < buffer.size).also { hitEOF = it }) {
            // make sure buffer that wasn't read is initialized with -1
            Arrays.fill(buffer, cnt, buffer.size, 0xff.toByte())
        }
        diskPosition += cnt.toLong()
        return cnt
    }

    /*
     * This method positions <code>this.curr</code> at position <code>pos</code>.
     * If <code>pos</code> does not fall in the current buffer, it flushes the
     * current buffer and loads the correct one.<p>
     *
     * On exit from this routine <code>this.curr == this.hi</code> iff <code>pos</code>
     * is at or past the end-of-file, which can only happen if the file was
     * opened in read-only mode.
     */
    @Throws(IOException::class)
    override fun seek(pos: Long) {
        if (pos >= buffHiPos || pos < buffLoPos) {
            // seeking outside of current buffer -- flush and read             
            flushBuffer()
            buffLoPos = pos and bufferMask // start at BuffSz boundary
            maxHi_ = buffLoPos + buffer.size.toLong()
            if (diskPosition != buffLoPos) {
                super.seek(buffLoPos)
                diskPosition = buffLoPos
            }
            val n = fillBuffer()
            buffHiPos = buffLoPos + n.toLong()
        } else {
            // seeking inside current buffer -- no read required
            if (pos < filePointer) {
                // if seeking backwards, we must flush to maintain V4
                flushBuffer()
            }
        }
        filePointer = pos
    }

    @Throws(IOException::class)
    override fun length(): Long {
        return Math.max(filePointer, super.length())
    }

    @Throws(IOException::class)
    override fun read(): Int {
        if (filePointer >= buffHiPos) {
            // test for EOF
            // if (this.hi < this.maxHi) return -1;
            if (hitEOF) return -1

            // slow path -- read another buffer
            seek(filePointer)
            if (filePointer == buffHiPos) return -1
        }
        val res = buffer[(filePointer - buffLoPos).toInt()]
        filePointer++
        return res.toInt() and 0xFF // convert byte -> int
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray): Int {
        return this.read(b, 0, b.size)
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray, off: Int, len: Int): Int {
        var len = len
        if (filePointer >= buffHiPos) {
            // test for EOF
            // if (this.hi < this.maxHi) return -1;
            if (hitEOF) return -1

            // slow path -- read another buffer
            seek(filePointer)
            if (filePointer == buffHiPos) return -1
        }
        len = Math.min(len, (buffHiPos - filePointer).toInt())
        val buffOff = (filePointer - buffLoPos).toInt()
        System.arraycopy(buffer, buffOff, b, off, len)
        filePointer += len.toLong()
        return len
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        if (filePointer >= buffHiPos) {
            if (hitEOF && buffHiPos < maxHi_) {
                // at EOF -- bump "hi"
                buffHiPos++
            } else {
                // slow path -- write current buffer; read next one
                seek(filePointer)
                if (filePointer == buffHiPos) {
                    // appending to EOF -- bump "hi"
                    buffHiPos++
                }
            }
        }
        buffer[(filePointer - buffLoPos).toInt()] = b.toByte()
        filePointer++
        isDirty = true
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray) {
        this.write(b, 0, b.size)
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        var off = off
        var len = len
        while (len > 0) {
            val n = writeAtMost(b, off, len)
            off += n
            len -= n
            isDirty = true
        }
    }

    /*
     * Write at most "len" bytes to "b" starting at position "off", and return
     * the number of bytes written.
     */
    @Throws(IOException::class)
    private fun writeAtMost(b: ByteArray, off: Int, len: Int): Int {
        var len = len
        if (filePointer >= buffHiPos) {
            if (hitEOF && buffHiPos < maxHi_) {
                // at EOF -- bump "hi"
                buffHiPos = maxHi_
            } else {
                // slow path -- write current buffer; read next one                
                seek(filePointer)
                if (filePointer == buffHiPos) {
                    // appending to EOF -- bump "hi"
                    buffHiPos = maxHi_
                }
            }
        }
        len = Math.min(len, (buffHiPos - filePointer).toInt())
        val buffOff = (filePointer - buffLoPos).toInt()
        System.arraycopy(b, off, buffer, buffOff, len)
        filePointer += len.toLong()
        return len
    }

}