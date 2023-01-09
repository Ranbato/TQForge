
    /// <summary>Contains additional info about the image</summary>
    public class DdsLoadInfo(val compressed:Boolean, val swap:Boolean, val palette:Boolean, val divSize:Int, val blockBytes:Int, val depth:Int, val format:ImageFormat)
    {

        /// <summary>
        /// The length of a block is in pixels.
        /// This mainly affects compressed images as they are
        /// encoded in blocks that are divSize by divSize.
        /// Uncompressed DDS do not need this value.
        /// </summary>
//        public uint DivSize { get; }

        /// <summary>
        /// Number of bytes needed to decode block of pixels
        /// that is divSize by divSize.  This takes into account
        /// how many bytes it takes to extract color and alpha information.
        /// Uncompressed DDS do not need this value.
        /// </summary>
//        public uint BlockBytes { get; }

//        public int Depth { get; }

    }
