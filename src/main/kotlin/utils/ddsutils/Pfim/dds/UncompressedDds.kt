import utils.ddsutils.Pfim.MipMapOffset
import java.io.InputStream
import kotlin.experimental.and

/// <summary>
    /// A DirectDraw Surface that is not compressed.  
    /// Thus what is in the input stream gets directly translated to the image buffer.
    /// </summary>
    public class UncompressedDds : Dds
    {
        private var  _bitsPerPixel:UInt? = null
        private var  _rgbSwapped:Boolean? = null
        private var _format: ImageFormat? = null
//          private var _mipMaps:Array<MipMapOffset?> = emptyArray()


          constructor( header:DdsHeader, config: PfimConfig, bitsPerPixel:UInt,  rgbSwapped:Boolean):super(header, config){


            _bitsPerPixel = bitsPerPixel;
            _rgbSwapped = rgbSwapped;
        }

          constructor ( header:DdsHeader, config: PfimConfig) : super(header, config)


        public override fun bitsPerPixel()= ImageInfo().depth;
        public override fun format():ImageFormat = _format!!
        public override var compressed = false;
        public override var mipMaps: Array<MipMapOffset?> =  arrayOfNulls(0)

        public override fun Decompress()
        {
        }



        protected override  fun Decode( stream:InputStream,  config:PfimConfig):Unit
        {
            data = DataDecode(stream, config);
        }

        /// <summary>Determine image info from header</summary>
        public fun ImageInfo():DdsLoadInfo
        {
            val rgbSwapped = _rgbSwapped ?: (header.pixelFormat.RBitMask < header.pixelFormat.GBitMask)

            when ((_bitsPerPixel?: header.pixelFormat.RGBBitCount))
            {
                8-> {
                    return  DdsLoadInfo(false, rgbSwapped, true, 1, 1, 8, ImageFormat.Rgb8)
                };
                 16 ->
                     {
                    val form = SixteenBitImageFormat()
                    return  DdsLoadInfo(false, rgbSwapped, false, 1, 2, 16, form)
                    };
                24 -> {
                return  DdsLoadInfo(false, rgbSwapped, false, 1, 3, 24, ImageFormat.Rgb24)
                };
                 32 -> {
                     return  DdsLoadInfo(false, rgbSwapped, false, 1, 4, 32, ImageFormat.Rgba32)
                 };
                else -> {
                    throw  Exception("Unrecognized rgb bit count: ${header.pixelFormat.RGBBitCount}")
                };
            }
        }

        private fun  SixteenBitImageFormat():ImageFormat
        {
            var pf = header.pixelFormat;

            if (pf.ABitMask.toInt() == 0xF000 && pf.RBitMask.toInt() == 0xF00 && pf.GBitMask.toInt() == 0xF0 && pf.BBitMask.toInt() == 0xF)
            {
                return ImageFormat.Rgba16;
            }

            if (pf.PixelFormatFlags.contains(DdsPixelFormatFlags.AlphaPixels))
            {
                return ImageFormat.R5g5b5a1;
            }

            return if(pf.GBitMask.toInt() == 0x7e0)  ImageFormat.R5g6b5 else ImageFormat.R5g5b5;
        }

        /// <summary>Calculates the number of bytes to hold image data</summary>
        private fun CalcSize( info:DdsLoadInfo):Int
        {
            val height = Math.max(info.divSize, header.Height.toInt());
            return stride() * height;
        }

        private fun AllocateMipMaps( info:DdsLoadInfo):Int
        {
            var len = CalcSize(info);

            if (header.MipMapCount <= 1)
            {
                return len;
            }

            mipMaps = arrayOfNulls(size = header.MipMapCount - 1)
            var totalLen = len;

            for (i in 0 until header.MipMapCount - 1)
            {
                val width = Math.max(info.divSize, (header.Width / Math.pow(2.0, ((i + 1).toDouble())).toInt()));
                val height = Math.max(info.divSize, header.Height / Math.pow(2.0, ((i + 1).toDouble())).toInt())
                val stride = Util.Stride(width, bitsPerPixel());
                len = stride * height;

                mipMaps[i] = MipMapOffset(width, height, stride, totalLen, len);
                totalLen += len;
            }

            return totalLen;
        }

        /// <summary>Decode data into raw rgb format</summary>
        private fun DataDecode(str:InputStream,  config:PfimConfig):ByteArray
        {
            var imageInfo = ImageInfo();
            _format = imageInfo.format;

            dataLen = CalcSize(imageInfo);
            var totalLen = AllocateMipMaps(imageInfo);
            val data = ByteArray(totalLen);

            var stride = Util.Stride( header.Width, bitsPerPixel());
            var width =  header.Width;
            var len = dataLen;

            if (width * BytesPerPixel == stride)
            {
                Util.Fill(str, data, len, config.bufferSize);
            }
            else
            {
                Util.InnerFillUnaligned(str, data, len, width * BytesPerPixel, stride, config.bufferSize);
            }

            mipMaps.forEach { mip ->

                if(mip != null) {
                    if (mip.width * BytesPerPixel == mip.stride) {
                        Util.Fill(str, data, mip.dataLen, config.bufferSize, mip.dataOffset);
                    } else {
                        Util.InnerFillUnaligned(
                            str,
                            data,
                            mip.dataLen,
                            mip.width * BytesPerPixel,
                            mip.stride,
                            config.bufferSize,
                            mip.dataOffset
                        );
                    }
                }
            }

            // Swap the R and B channels
            if (imageInfo.swap)
            {
                when (imageInfo.format)
                {
                    ImageFormat.Rgba32->
                        for (i in 0 until totalLen step 4)
                        {
                            val temp = data[i];
                            data[i] = data[i + 2];
                            data[i + 2] = temp;
                        }

                    ImageFormat.Rgba16->
                        for (i in 0 until totalLen step 2)
                        {
                            val temp =  (data[i] and 0xF);
                            data[i] =  (((data[i] and 0xF0.toByte()) + (data[i + 1] and 0xF)).toByte());
                            data[i + 1] =  (((data[i + 1] and 0xF0.toByte()) + temp).toByte());

                        }

                    else ->
                        throw  Exception("Do not know how to swap ${imageInfo.format}");
                }
            }

            return data;
        }
    }

