@file:OptIn(ExperimentalUnsignedTypes::class)

import de.justjanne.bitflags.*
import utils.LittleEndianDataInputStream
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.InputStream
import java.util.EnumSet

/// <summary>
    /// Denotes the compression algorithm used in the image. Either the image
    /// is uncompressed or uses some sort of block compression. The
    /// compression used is encoded in the header of image as textual
    /// representation of itself. So a DXT1 image is encoded as "1TXD" so the
    /// enum represents these values directly
    /// </summary>
    public enum class CompressionAlgorithm( val value:UInt)
    {
        
        /// <summary>
        /// No compression was used in the image.
        /// </summary>
        None( 0.toUInt()),

        /// <summary>
        /// <see cref="Dxt1Dds"/>. Also known as BC1
        /// </summary>
        D3DFMT_DXT1(827611204.toUInt()),

        /// <summary>
        /// Not supported. Also known as BC2
        /// </summary>
        D3DFMT_DXT2(844388420.toUInt()),

        /// <summary>
        /// <see cref="Dxt3Dds"/>. Also known as BC2
        /// </summary>
        D3DFMT_DXT3(861165636.toUInt()),

        /// <summary>
        /// Not supported. Also known as BC3
        /// </summary>
        D3DFMT_DXT4(877942852.toUInt()),

        /// <summary>
        /// <see cref="Dxt5Dds"/>. Also known as BC3
        /// </summary>
        D3DFMT_DXT5(894720068.toUInt()),

        DX10(808540228.toUInt()),

        ATI1(826889281.toUInt()),
        BC4U(1429488450.toUInt()),
        BC4S(1395934018.toUInt()),

        ATI2(843666497.toUInt()),
        BC5U(1429553986.toUInt()),
        BC5S(1395999554.toUInt());

        companion object {
            public fun of(id: UInt):CompressionAlgorithm?{
                return CompressionAlgorithm.values().find { enum-> enum.value == id  }
            }

        }
    }

    /// <summary>Flags to indicate which members contain valid data.</summary>
    public enum class DdsFlags(override val value:UInt): Flag<UInt>
    {
        /// <summary>
        /// Required in every .dds file.
        /// </summary>
        Caps(0x1.toUInt()),

        /// <summary>
        /// Required in every .dds file.
        /// </summary>
        Height(0x2.toUInt()),

        /// <summary>
        /// Required in every .dds file.
        /// </summary>
        Width(0x4.toUInt()),

        /// <summary>
        /// Required when pitch is provided for an uncompressed texture.
        /// </summary>
        Pitch(0x8.toUInt()),

        /// <summary>
        /// Required in every .dds file.
        /// </summary>
        PixelFormat(0x1000.toUInt()),

        /// <summary>
        /// Required in a mipmapped texture.
        /// </summary>
        MipMapCount(0x20000.toUInt()),

        /// <summary>
        /// Required when pitch is provided for a compressed texture.
        /// </summary>
        LinearSize(0x80000.toUInt()),

        /// <summary>
        /// Required in a depth texture.
        /// </summary>
        Depth(0x800000.toUInt());

        companion object: Flags<UInt, DdsFlags> {
            override val all:Set<DdsFlags> = values().toEnumSet()
        }

    }

    /// <summary>Values which indicate what type of data is in the surface.</summary>

    public enum class DdsPixelFormatFlags(override val value:UInt): Flag<UInt>
    {


        /// <summary>
        ///     Texture contains alpha data; dwRGBAlphaBitMask contains valid data.
        /// </summary>
        AlphaPixels(0x1.toUInt()),

        /// <summary>
        ///     Used in some older DDS files for alpha channel only uncompressed data (dwRGBBitCount contains the alpha channel
        ///     bitcount; dwABitMask contains valid data)
        /// </summary>
        Alpha(0x2.toUInt()),

        /// <summary>
        ///     Texture contains compressed RGB data; dwFourCC contains valid data.
        /// </summary>
        Fourcc(0x4.toUInt()),

        /// <summary>
        ///     Texture contains uncompressed RGB data; dwRGBBitCount and the RGB masks (dwRBitMask, dwGBitMask, dwBBitMask)
        ///     contain valid data.
        /// </summary>
        Rgb(0x40.toUInt()),

        /// <summary>
        ///     Used in some older DDS files for YUV uncompressed data (dwRGBBitCount contains the YUV bit count; dwRBitMask
        ///     contains the Y mask, dwGBitMask contains the U mask, dwBBitMask contains the V mask)
        /// </summary>
        Yuv(0x200.toUInt()),

        /// <summary>
        ///     Used in some older DDS files for single channel color uncompressed data (dwRGBBitCount contains the luminance
        ///     channel bit count; dwRBitMask contains the channel mask). Can be combined with DDPF_ALPHAPIXELS for a two channel
        ///     DDS file.
        /// </summary>
        Luminance(0x20000.toUInt());


        companion object: Flags<UInt, DdsPixelFormatFlags> {
            override val all:Set<DdsPixelFormatFlags> = values().toEnumSet()
        }

    }

    /// <summary>
    /// Surface pixel format.
    /// https://msdn.microsoft.com/en-us/library/windows/desktop/bb943984(v=vs.85).aspx
    /// </summary>
    public class DdsPixelFormat
    {
        /// <summary>
        /// Structure size; set to 32 (bytes).
        /// </summary>
        public var Size:UInt = 0.toUInt()

        /// <summary>
        /// Values which indicate what type of data is in the surface.
        /// </summary>
        public var PixelFormatFlags = DdsPixelFormatFlags.none()

        /// <summary>
        /// Four-character codes for specifying compressed or custom formats.
        /// Possible values include: DXT1, DXT2, DXT3, DXT4, or DXT5.  A
        /// FourCC of DX10 indicates the prescense of the DDS_HEADER_DXT10
        /// extended header,  and the dxgiFormat member of that structure
        /// indicates the true format. When using a four-character code,
        /// dwFlags must include DDPF_FOURCC.
        /// </summary>
        public var FourCC = CompressionAlgorithm.None

        /// <summary>
        /// Number of bits in an RGB (possibly including alpha) format.
        /// Valid when dwFlags includes DDPF_RGB, DDPF_LUMINANCE, or DDPF_YUV.
        /// </summary>
        public var RGBBitCount:Int = 0

        /// <summary>
        /// Red (or lumiannce or Y) mask for reading color data.
        /// For instance, given the A8R8G8B8 format, the red mask would be 0x00ff0000.
        /// </summary>
        public var RBitMask:UInt = 0.toUInt()

        /// <summary>
        /// Green (or U) mask for reading color data.
        /// For instance, given the A8R8G8B8 format, the green mask would be 0x0000ff00.
        /// </summary>
        public var GBitMask:UInt = 0.toUInt()

        /// <summary>
        /// Blue (or V) mask for reading color data.
        /// For instance, given the A8R8G8B8 format, the blue mask would be 0x000000ff.
        /// </summary>
        public var BBitMask:UInt = 0.toUInt()

        /// <summary>
        /// Alpha mask for reading alpha data.
        /// dwFlags must include DDPF_ALPHAPIXELS or DDPF_ALPHA.
        /// For instance, given the A8R8G8B8 format, the alpha mask would be 0xff000000.
        /// </summary>
        public var ABitMask:UInt = 0.toUInt()
    }

    /// <summary>
    /// The header that accompanies all direct draw images
    /// https://msdn.microsoft.com/en-us/library/windows/desktop/bb943982(v=vs.85).aspx
    /// </summary>
    public class DdsHeader( val stream:InputStream, val skipMagic:Boolean = false)
    {
        /// <summary>
        /// Size of a Direct Draw Header in number of bytes.
        /// This does not include the magic number
        /// </summary>
        private val SIZE = 124;

        /// <summary>
        /// The magic number is the 4 bytes that starts off every Direct Draw Surface file.
        /// </summary>
        private val DDS_MAGIC = 542327876.toUInt();

        var pixelFormat:DdsPixelFormat = DdsPixelFormat()
        /// <summary>
        /// Size of structure. This member must be set to 124.
        /// </summary>
        public var Size:Int = 0
            private set

        /// <summary>
        /// Flags to indicate which members contain valid data.
        /// </summary>
        var Flags :EnumSet<DdsFlags> = EnumSet.noneOf(DdsFlags::class.java)

        /// <summary>
        /// Surface height in pixels
        /// </summary>
        public var Height:Int = 0
            private set

        /// <summary>
        /// Surface width in pixels
        /// </summary>
        public var Width:Int = 0
            private set

        /// <summary>
        /// The pitch or number of bytes per scan line in an uncompressed texture.
        /// The total number of bytes in the top level texture for a compressed texture.
        /// </summary>
        public var PitchOrLinearSize:Int = 0
            private set

        /// <summary>
        /// Depth of a volume texture (in pixels), otherwise unused.
        /// </summary>
        var Depth:Int = 0
            private set
        /// <summary>
        /// Number of mipmap levels, otherwise unused.
        /// </summary>
        var MipMapCount:Int = 0
            private set
        /// <summary>
        /// Unused
        /// </summary>
        public var Reserved1 :UIntArray = UIntArray(0)
            private set

        /// <summary>
        /// Specifies the complexity of the surfaces stored.
        /// </summary>
        var Caps:UInt = 0.toUInt()
            private set
        /// <summary>
        /// Additional detail about the surfaces stored.
        /// </summary>
        var Caps2:UInt = 0.toUInt()
            private set
        /// <summary>
        /// Unused
        /// </summary>
        var Caps3:UInt = 0.toUInt()
            private set
        /// <summary>
        /// Unused
        /// </summary>
        var Caps4:UInt = 0.toUInt()
            private set
        /// <summary>
        /// Unused
        /// </summary>
        var Reserved2:UInt = 0.toUInt()
            private set
        /// <summary>Create header from stream</summary>
        init {


            headerInit(stream, skipMagic);
        }

        private fun headerInit( stream:InputStream, skipMagic:Boolean)
        {
            var headerSize = if(skipMagic) SIZE else SIZE + 4;
            val buffer = ByteArray(headerSize)
            val Reserved1 = ByteArray(4*11) // Don't need UInt since we ignore so do 4x byte array
            Util.ReadExactly(stream, buffer, 0, headerSize);
            LittleEndianDataInputStream(ByteArrayInputStream(buffer)).use{workingBufferPtr ->


                if (!skipMagic)
                {
                    if (workingBufferPtr.readInt().toUInt() != DDS_MAGIC)
                        throw Exception("Not a valid DDS");
                }

                Size = workingBufferPtr.readInt()
                if (Size != SIZE)
                    throw Exception("Not a valid header size");
                Flags = DdsFlags.of(workingBufferPtr.readInt().toUInt())
                Height = workingBufferPtr.readInt()
                Width = workingBufferPtr.readInt()
                PitchOrLinearSize = workingBufferPtr.readInt()
                Depth = workingBufferPtr.readInt()
                MipMapCount = workingBufferPtr.readInt()

                workingBufferPtr.read(Reserved1)


                pixelFormat.Size = workingBufferPtr.readInt().toUInt()
                if (pixelFormat.Size != 32.toUInt())
                {
                    throw Exception("Expected pixel size to be 32, not: ${pixelFormat.Size}");
                }

                pixelFormat.PixelFormatFlags = DdsPixelFormatFlags.of(workingBufferPtr.readInt().toUInt())
                pixelFormat.FourCC = CompressionAlgorithm.of(workingBufferPtr.readInt().toUInt())?:CompressionAlgorithm.None
                pixelFormat.RGBBitCount = workingBufferPtr.readInt()
                pixelFormat.RBitMask = workingBufferPtr.readInt().toUInt()
                pixelFormat.GBitMask = workingBufferPtr.readInt().toUInt()
                pixelFormat.BBitMask = workingBufferPtr.readInt().toUInt()
                pixelFormat.ABitMask = workingBufferPtr.readInt().toUInt()

                Caps = workingBufferPtr.readInt().toUInt()
                Caps2 = workingBufferPtr.readInt().toUInt()
                Caps3 = workingBufferPtr.readInt().toUInt()
                Caps4 = workingBufferPtr.readInt().toUInt()
                Reserved2 = workingBufferPtr.readInt().toUInt()
            }
        }



    }


