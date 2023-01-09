import utils.ddsutils.Pfim.MipMapOffset

 interface IImage
    {
        /// <summary>The raw data</summary>
        var data:ByteArray

        /// <summary>
        /// Length of the raw data. Unless decoding with a custom allocator
        /// this will be equivalent to `Data.Length`
        /// </summary>
        var dataLen:Int

        /// <summary>Width of the image in pixels</summary>
        val width:Int

        /// <summary>Height of the image in pixels</summary>
        val height:Int

        /// <summary>The number of bytes that compose one line</summary>
        fun stride():Int

        /// <summary>The number of bits that compose a pixel</summary>
        fun bitsPerPixel():Int

        /// <summary>The format of the raw data</summary>
        fun format():ImageFormat

        /// <summary>If the image format is compressed</summary>
        val compressed:Boolean

        /// <summary>Decompress the image. Will have no effect if not compressed</summary>
        fun Decompress();
          
        ///<summary>Apply colormap, may change data and image format</summary>
        fun ApplyColorMap();

        var mipMaps:Array<MipMapOffset?>
    }

