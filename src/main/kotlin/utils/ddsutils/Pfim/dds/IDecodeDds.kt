import java.io.InputStream

interface IDecodeDds
    {
        fun ImageInfo(header:DdsHeader):DdsLoadInfo
        fun Decode(str: InputStream, header:DdsHeader, imageInfo:DdsLoadInfo, config:PfimConfig):ByteArray
    }

