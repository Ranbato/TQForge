import ddsutil.DDSUtil
import jogl.DDSImage
import org.jetbrains.skia.*
import org.jetbrains.skiko.toBitmap
import utils.DDSLoader
import utils.DDSReader
import java.awt.Transparency
import java.awt.image.*
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

private val logger = mu.KotlinLogging.logger {}
  public object BitmapCode
  {
//    private static Bitmap DDSDataToBMP(byte[] ddsData)
//    {
//      int Images;
//      Il.ilGenImages(1, out Images);
//      Il.ilBindImage(Images);
//      Il.ilLoadL(1079, ddsData, ddsData.Length);
//      int integer1 = Il.ilGetInteger(3556);
//      int integer2 = Il.ilGetInteger(3557);
//      Rectangle rect = new Rectangle(0, 0, integer1, integer2);
//      Il.ilConvertImage(32993, 5121);
//      Bitmap bmp = new Bitmap(integer1, integer2);
//      BitmapData bitmapdata = bmp.LockBits(rect, ImageLockMode.WriteOnly, PixelFormat.Format32bppArgb);
//      switch (Il.ilCopyPixels(0, 0, 0, Il.ilGetInteger(3556), Il.ilGetInteger(3557), 1, 32993, 5121, bitmapdata.Scan0))
//      {
//        case 1286:
//        case 1296:
//          throw new InvalidDataException("Cannot Convert Bitmap.");
//        default:
//          Il.ilDeleteImages(1, ref Images);
//          bmp.UnlockBits(bitmapdata);
//          return bmp;
//      }
//    }
//
//    public static Bitmap LoadFromDdsFile(string fileName)
//    {
//      byte[] numArray;
//      using (FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read))
//      {
//        int offset = 0;
//        numArray = new byte[Convert.ToInt32(fileStream.Length)];
//        int num;
//        while ((num = fileStream.Read(numArray, offset, numArray.Length - offset)) > 0)
//          offset += num;
//      }
//      if (numArray.Length >= 128)
//      {
//        uint uint32 = BitConverter.ToUInt32(numArray, 0);
//        switch (uint32)
//        {
//          case 542327876:
//          case 1381188676:
//            if (uint32 == 1381188676U)
//              numArray[3] = (byte) 32;
//            if (BitConverter.ToInt32(numArray, 4) == 124 && BitConverter.ToInt32(numArray, 76) == 32 && BitConverter.ToInt32(numArray, 88) == 32)
//            {
//              numArray[80] = (byte) ((uint) numArray[80] | 1U);
//              break;
//            }
//            break;
//        }
//      }
//      return BitmapCode.LoadFromDdsMemory(numArray, 0, numArray.Length);
//    }

    public fun LoadFromDdsMemory( data:ByteArray,  offset:Int,  count:Int): Bitmap?  = LoadFromMemory(data, offset, count)

    private fun  LoadFromMemory( data:ByteArray,  offset:Int,  count:Int): Bitmap?
    {
      if (data.isEmpty()) {
        logger.error { "Empty bitmap data passed to LoadFromMemory" }
        return null
      }
      if (offset < 0 || offset > data.size){
        logger.error { "Invalid offset data passed to LoadFromMemory" }
        return null
      }
      if (count < 0 || data.size - offset < count){
        logger.error { "Invalid count data passed to LoadFromMemory" }
        return null
      }

      val numArray = data.copyOfRange(offset,offset + count)
//    This doesn't support the correct image format but does create BufferedImage from bytearray
//      val image = DDSImage.read(ByteBuffer.wrap(data))
//      return DDSUtil.loadBufferedImage(image).toBitmap()

      // Weird colors
      val loader = DDSLoader()
      return loader.load(ByteArrayInputStream(numArray))

      // Still nope
//      val img = Image.makeFromEncoded(data)
//      return img.toBufferedImage().toBitmap()
    }
    fun Image.toBufferedImage(): BufferedImage {
      val storage = Bitmap()
      storage.allocPixelsFlags(ImageInfo.makeS32(this.width, this.height, ColorAlphaType.PREMUL), false)
      Canvas(storage).drawImage(this, 0f, 0f)

      val bytes = storage.readPixels(storage.imageInfo, (this.width * 4), 0, 0)!!
      val buffer = DataBufferByte(bytes, bytes.size)
      val raster = Raster.createInterleavedRaster(
        buffer,
        this.width,
        this.height,
        this.width * 4, 4,
        intArrayOf(2, 1, 0, 3),     // BGRA order
        null
      )
      val colorModel = ComponentColorModel(
        java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_sRGB),
        true,
        false,
        Transparency.TRANSLUCENT,
        DataBuffer.TYPE_BYTE
      )

      return BufferedImage(colorModel, raster!!, false, null)
    }
    private fun DDSDataToBMP(numArray: ByteArray):Bitmap{
      val pixels = DDSReader.read(numArray, DDSReader.ARGB, 0);
      val width = DDSReader.getWidth(numArray);
      val height = DDSReader.getHeight(numArray);
      val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
      image.setRGB(0, 0, width, height, pixels, 0, width)
      return image.toBitmap()
    }
//
//    [EnvironmentPermission(SecurityAction.LinkDemand, Unrestricted = true)]
//    public static Bitmap LoadFromTexFile(string fileName)
//    {
//      byte[] numArray;
//      using (FileStream fileStream = new FileStream(fileName, FileMode.Open, FileAccess.Read))
//      {
//        int offset = 0;
//        numArray = new byte[Convert.ToInt32(fileStream.Length)];
//        int num;
//        while ((num = fileStream.Read(numArray, offset, numArray.Length - offset)) > 0)
//          offset += num;
//      }
//      return BitmapCode.LoadFromTexMemory(numArray, 0, numArray.Length);
//    }

    public fun  LoadFromTexMemory( dataOrg:ByteArray,  offset:Int,  count:Int): Bitmap?
    {
      if (dataOrg.isEmpty()) {
        logger.error { "Empty bitmap data passed to LoadFromTexMemory" }
        return null
      }
      if (offset < 0 || offset > dataOrg.size){
        logger.error { "Invalid offset data passed to LoadFromTexMemory" }
        return null
      }
      if (count < 0 || dataOrg.size - offset < count){
        logger.error { "Invalid count data passed to LoadFromTexMemory" }
        return null
      }

      if (dataOrg.size < 12){
        logger.error { "TEX is not long enough to be valid." }
        return null
      }
      val data = ByteBuffer.wrap(dataOrg)
      data.order(ByteOrder.LITTLE_ENDIAN)
      if (data.getInt(offset).toUInt() != 22562132.toUInt()) {
        logger.error("Invalid TEX magic")
        return null
      }
      val int32_1 = data.getInt( offset + 4);
      if (int32_1 < 0 || int32_1 > count - offset) {
        logger.error ("TEX texture offset is invalid.");
        return null
      }
      val int32_2 = data.getInt( offset + 8);
      if (int32_2 < 0 || int32_2 > count - offset - int32_1) {
        logger.error("TEX texture length is invalid.");
        return null
      }
      if (int32_2 < 4) {
        logger.error ("Cannot read TEX texture image magic.");
        return null
      }
      val num = offset + int32_1 + 12;
      if (data.getInt(num) == 542327876 || data.getInt(num) == 1381188676)
      {

          if (int32_2 >= 128)
          {
            if (data.getInt( num + 4) != 124 || data.getInt( num + 76) != 32) {
             logger.error  ("Invalid Header format.");
              return null
            }
            val numArray = ByteArray(int32_2)
            data.get(num,numArray,0, int32_2)
            
            numArray[3] =  32
            val int32_3 = data.getInt(num+88)
            if (int32_3 >= 24)
            {
              numArray[92] =  0;
              numArray[93] =  0;
              numArray[94] = Byte.MAX_VALUE
              numArray[95] = 0
              numArray[96] = 0
              numArray[97] = Byte.MAX_VALUE
              numArray[98] = 0
              numArray[99] = 0
              numArray[100] = Byte.MAX_VALUE
              numArray[101] = 0
              numArray[102] = 0
              numArray[103] = 0
              if (int32_3 == 32)
              {
                numArray[80] =  (numArray[80].toUInt() or 1.toUInt()).toByte()
                numArray[104] = 0
                numArray[105] = 0
                numArray[106] = 0
                numArray[107] = Byte.MAX_VALUE
              }
            }
            numArray[109] =  (numArray[109].toUInt() or 16.toUInt()).toByte()
            return LoadFromDdsMemory(numArray, 0, numArray.size);
          }

      }
      logger.error ("Unknown texture format.");
      return null
    }
  }

