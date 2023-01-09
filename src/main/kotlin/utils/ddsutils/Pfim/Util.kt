import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.math.min

/// <summary>
    /// Utility class housing methods used by multiple image decoders
    /// </summary>
    public object Util
    {
        /// <summary>
        /// Buffer size to read data from
        /// </summary>
        private val BUFFER_SIZE = 0x8000;

         fun CreateExposed(data:ByteArray): InputStream
        {
            return ByteArrayInputStream(data);
        }

        /// <summary>
        /// Takes all the bytes at and after an index and moves them to the front and fills the rest
        /// of the buffer with information from the stream.
        /// </summary>
        /// <remarks>
        /// This function is useful when the buffer doesn't have enough information to process a
        /// certain amount of information thus more information from the stream has to be read. This
        /// preserves the information that hasn't been read by putting it at the front.
        /// </remarks>
        /// <param name="str">Stream where more data will be read to fill in end of the buffer.</param>
        /// <param name="buf">The buffer that contains the data that will be translated.</param>
        /// <param name="bufIndex">
        /// Start of the translation. The value initially at this index will be the value at index 0
        /// in the buffer after translation.
        /// </param>
        /// <returns>
        /// The total number of bytes read into the buffer and translated. May be less than the
        /// buffer's length.
        /// </returns>
        public fun Translate(str:InputStream,  buf:ByteArray,  bufLen:Int, bufIndex:Int):Int
        {
            System.arraycopy(buf, bufIndex, buf, 0, bufLen - bufIndex);
            val result = Util.ReadFill(str, buf, bufLen - bufIndex, bufIndex);
            return result + bufLen - bufIndex;
        }

        /// <summary>
        /// Sets all the values in a buffer to a single value.
        /// </summary>
        /// <param name="buffer">Buffer that will have its values set</param>
        /// <param name="value">The value that the buffer will contain</param>
        /// <param name="length">The number of bytes to write in the buffer</param>
        public fun memset(buffer:IntArray, value:Int, length:Int)
        {
            intFill(buffer, value, length )
        }

        /// <summary>
        /// Sets all the values in a buffer to a single value.
        /// </summary>
        /// <param name="buffer">Buffer that will have its values set</param>
        /// <param name="value">The value that the buffer will contain</param>
        /// <param name="length">The number of bytes to write in the buffer</param>
        public fun memset(buffer:ByteArray, value:Byte, length:Int)
        {
            byteFill(buffer,value,length)
        }

        /**
         * This essentially makes log2(array.length) calls to System.arraycopy which hopefully utilizes an optimized memcpy implementation.
         *
         * From Java server performance: A case study of building efficient, scalable Jvms" by R. Dimpsey, R. Arora, K. Kuiper.
         * via https://stackoverflow.com/questions/9128737/fastest-way-to-set-all-values-of-an-array
         */
        private fun byteFill(array: ByteArray, value:Byte, length: Int):Unit
        {

            val len: Int = min(array.size,length)

            if (len > 0) {
                array[0] = value
            }

            //Value of i will be [1, 2, 4, 8, 16, 32, ..., len]

                var i = 1
                while (i < len) {
                    System.arraycopy(array, 0, array, i, if (len - i < i) len - i else i)
                    i += i
                }

        }

        private fun intFill(array: IntArray, value:Int, length: Int):Unit
        {

            val len: Int = min(array.size,length)

            if (len > 0) {
                array[0] = value
            }

            //Value of i will be [1, 2, 4, 8, 16, 32, ..., len]

            var i = 1
            while (i < len) {
                System.arraycopy(array, 0, array, i, if (len - i < i) len - i else i)
                i += i
            }

        }
        public fun Fill(stream:InputStream,  data:ByteArray, dataLen:Int, bufSize:Int = BUFFER_SIZE, offset:Int = 0)
        {
//            if (stream is MemoryStream s && s.TryGetBuffer(out var arr))
//            {
//                Buffer.BlockCopy(arr.Array, (int)s.Position, data, offset, dataLen);
//                s.Position += dataLen;
//            }
//            else
//            {
                InnerFill(stream, data, dataLen, bufSize, offset);
//            }
        }

        public fun Fill(stream:InputStream,  data:ByteArray, dataLen:Int, bufSize:Int = BUFFER_SIZE) =
            Fill(stream, data, dataLen, bufSize, 0)

        public fun InnerFillUnaligned(str:InputStream, buf:ByteArray,  bufLen:Int,  width:Int, stride:Int, bufSize :Int= BUFFER_SIZE,  offset:Int = 0)
        {
            for ( i in offset until bufLen + offset step stride)
            {
                ReadExactly(str, buf, i, width);
            }
        }


        public fun InnerFillUnaligned(str:InputStream, buf:ByteArray,  bufLen:Int,  width:Int, stride:Int, bufSize :Int= BUFFER_SIZE) =
            InnerFillUnaligned(str, buf, bufLen, width, stride, bufSize, 0);

        /// <summary>
        /// Fills the buffer all the way up with info from the stream
        /// </summary>
        /// <param name="str">Stream that will be used to fill the buffer</param>
        /// <param name="buf">Buffer that will house the information from the stream</param>
        /// <param name="bufSize">The chunk size of data that will be read from the stream</param>
        private fun InnerFill(str:InputStream, buf:ByteArray, dataLen:Int,  bufSize :Int= BUFFER_SIZE,  offset:Int = 0)
        {
            ReadExactly(str, buf, offset, dataLen);
        }

        /// <summary>
        /// Fills a data array starting from the bottom left by reading from a stream.
        /// </summary>
        /// <param name="str">The stream to read data from</param>
        /// <param name="data">The buffer to be filled with stream data</param>
        /// <param name="rowSize">The size in bytes of each row in the stream</param>
        /// <param name="bufSize">The chunk size of data that will be read from the stream</param>
        /// <param name="stride">The number of bytes that make up a row in the data</param>
        /// <param name="buffer">The temporary buffer used to read data in</param>
        public fun FillBottomLeft(
            str:InputStream,
            data:ByteArray,
            dataLen:Int,
            rowSize:Int,
            stride:Int,
            bufferOrg:ByteArray? = null,
            bufSize:Int = BUFFER_SIZE)
        {
            val buffer = bufferOrg?: ByteArray(BUFFER_SIZE)
            if (buffer.size < bufSize)
            {
                throw Exception("Buffer $buffer must be longer than bufSize $bufSize");
            }

            var bufferIndex = 0;
            val rowsPerBuffer = Math.min(bufSize, dataLen) / stride;
            var dataIndex = dataLen - stride;
            var rowsRead = 0;
            val totalRows = dataLen / stride;
            var rowsToRead = rowsPerBuffer;

            if (rowsPerBuffer == 0)
            {
                var workingSize = Util.ReadFill(str, buffer, 0, bufSize);
                while (workingSize > 0 && dataIndex >= 0)
                {
                    var copied = 0;
                    while (workingSize > 0 && copied < rowSize)
                    {
                        val toCopy = Math.min(workingSize, rowSize - copied);
                        System.arraycopy(buffer, 0, data, dataIndex + copied, toCopy);
                        copied += toCopy;
                        workingSize = Translate(str, buffer, bufSize, toCopy);
                    }

                    dataIndex -= stride;
                }
            }
            else
            {
                var workingSize = Util.ReadFill(str, buffer, 0, bufSize);
                while (workingSize > 0 && dataIndex >= 0)
                {
                    for ( i in  0 until rowsToRead)
                    {
                        System.arraycopy(buffer, bufferIndex, data, dataIndex, rowSize);
                        dataIndex -= stride;
                        bufferIndex += rowSize;
                    }

                    if (dataIndex >= 0)
                    {
                        workingSize = Translate(str, buffer, bufSize, bufferIndex);
                        bufferIndex = 0;
                        rowsRead += rowsPerBuffer;
                        rowsToRead = if(rowsRead + rowsPerBuffer < totalRows)  rowsPerBuffer else totalRows - rowsRead;
                    }
                }
            }
        }

        /// <summary>
        /// Fills the provided buffer. Throws if not enough data is available
        /// </summary>
        public fun ReadExactly(str:InputStream, buffer:ByteArray, offset:Int, count:Int):Unit
        {
            ReadAtLeastCore(str, buffer, offset, count, throwOnEndOfStream= true);
        }

        /// <summary>
        /// Reads from the stream until either the buffer is full or the stream has been consumed
        /// </summary>
        public fun ReadFill( str: InputStream, buffer:ByteArray, offset:Int, count:Int):Int
        {
            return ReadAtLeastCore(str, buffer, offset, count, throwOnEndOfStream = false);
        }

        /// <summary>
        /// From https://github.com/dotnet/runtime/blob/c61ef61be8672bbc32d5e1d4e70c5ed4c04f4293/src/libraries/System.Private.CoreLib/src/System/IO/Stream.cs#L896
        /// </summary>
        private fun ReadAtLeastCore(str:InputStream, buffer:ByteArray, offset:Int, count:Int, throwOnEndOfStream:Boolean = true):Int
        {
            var totalRead = 0;
            while (totalRead < count)
            {
                val read = str.read(buffer, offset + totalRead, count - totalRead);
                if (read == 0)
                {
                    if (throwOnEndOfStream)
                    {
                        throw Exception("Unable to fill buffer with ${count} bytes");
                    }

                    return totalRead;
                }

                totalRead += read;
            }

            return totalRead;
        }

        /// <summary>
        /// Calculates stride of an image in bytes given its width in pixels and pixel depth in bits
        /// </summary>
        /// <param name="width">Width in pixels</param>
        /// <param name="pixelDepth">The number of bits that represents a pixel</param>
        /// <returns>The number of bytes that consists of a single line</returns>
        public fun Stride(width:Int, pixelDepth:Int):Int
        {
            if (width <= 0)
                throw Exception("Width must be greater than zero");
            else if (pixelDepth <= 0)
                throw Exception("Pixel depth must be greater than zero");

            val bytesPerPixel = (pixelDepth + 7) / 8;

            // Windows GDI+ requires that the stride be a multiple of four.
            // Even if not being used for Windows GDI+ there isn't a anything
            // bad with having extra space.
            return 4 * ((width * bytesPerPixel + 3) / 4);
        }
    }

