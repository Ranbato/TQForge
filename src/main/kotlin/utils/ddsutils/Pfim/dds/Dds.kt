import utils.ddsutils.Pfim.MipMapOffset
import java.io.InputStream

/// <summary>
    /// Class that represents direct draw surfaces
    /// </summary>
    public abstract class Dds( val header:DdsHeader, val _config:PfimConfig) : IImage
    {

        /// <summary>
        /// Instantiates a direct draw surface image from a header, the data,
        /// and additional info.
        /// </summary>


        protected fun Config() = _config;


        public var BytesPerPixel = bitsPerPixel() / 8;
        public override fun stride() = Util.Stride(header.Width, bitsPerPixel());
        public override var  data:ByteArray = ByteArray(0)
        public override var dataLen:Int = 0
        public override var width = header.Width.toInt()
        public override var height = header.Height.toInt()
        public override var compressed:Boolean = false
//        public abstract void Decompress();
//        public var Header10:DdsHeaderDxt10? = null

        companion object {
            public fun Create(data: ByteArray, config: PfimConfig): Dds {
                return Create(Util.CreateExposed(data), config);
            }

            /// <summary>Create a direct draw image from a stream</summary>
            public fun Create(stream: InputStream, config: PfimConfig): Dds {
                val header = DdsHeader(stream);
                return DecodeDds(stream, config, header);
            }

            /// <summary>
            /// Same as a regular create except assumes that the magic number has already been consumed
            /// </summary>
            fun CreateSkipMagic(stream: InputStream, config: PfimConfig): IImage {
                val header = DdsHeader(stream, true);
                return DecodeDds(stream, config, header);
            }

            private fun DecodeDds(stream: InputStream, config: PfimConfig, header: DdsHeader): Dds {
                var dds: Dds =
                    when (header.pixelFormat.FourCC) {
//                CompressionAlgorithm.D3DFMT_DXT1,->
//                    Dxt1Dds(header, config);
//
//                CompressionAlgorithm.D3DFMT_DXT2,
//                CompressionAlgorithm.D3DFMT_DXT4,->
//                    throw Exception("Cannot support DXT2 or DXT4");
//                CompressionAlgorithm.D3DFMT_DXT3,->
//                     Dxt3Dds(header, config);
//
//                CompressionAlgorithm.D3DFMT_DXT5,->
//                    Dxt5Dds(header, config);

                        CompressionAlgorithm.None, ->
                            UncompressedDds(header, config)
//
//                CompressionAlgorithm.DX10,->
//                    var header10 = DdsHeaderDxt10(stream);
//                    dds = header10.NewDecoder(header, config);
//                    dds.Header10 = header10;
//
//                CompressionAlgorithm.ATI1,
//                CompressionAlgorithm.BC4U,->
//                    Bc4Dds(header, config);
//
//                CompressionAlgorithm.BC4S,->
//                    Bc4sDds(header, config);
//
//
//                CompressionAlgorithm.ATI2,
//                CompressionAlgorithm.BC5U,->
//                    Bc5Dds(header, config);
//
//                CompressionAlgorithm.BC5S,->
//                    Bc5sDds(header, config);


                        else ->
                            throw Exception("FourCC: ${header.pixelFormat.FourCC} not supported.");
                    }

                dds.Decode(stream, config);
                return dds;
            }
        }
        protected abstract fun Decode( stream:InputStream,  config:PfimConfig):Unit

        public override fun ApplyColorMap()
        {
        }



    }

