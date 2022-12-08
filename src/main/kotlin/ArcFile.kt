import java.io.BufferedInputStream
import java.io.File
import java.io.ObjectInputStream
import java.io.RandomAccessFile
import java.util.*
import kotlin.collections.ArrayList

// Place definition above class declaration to make field static
private val logger = mu.KotlinLogging.logger {}

public class ArcFile {
  private var fileHasBeenRead = false
  private var directoryEntries  = mutableMapOf<String, ARCDirEntry>()
  private var keys = mutableSetOf<String>()
  private val  FileName:String

constructor(FileName:String){
  this.FileName = FileName
}

  private fun BuildKeyTable(): Unit {
    if (this.directoryEntries.isEmpty())
      return

    keys = directoryEntries.keys.toSortedSet()
  }
//
//    public fun ExtractArcFile(destination:String):Boolean
//    {
//      try
//      {
//        logger.debug("ARCFile.ReadARCFile()");
//        if (!this.fileHasBeenRead)
//          this.ReadARCToC();
//        foreach (ArcFile.ARCDirEntry arcDirEntry in this.directoryEntries.Values)
//        {
//          val dataId = Path.GetFileNameWithoutExtension(this.FileName) + "\\" + arcDirEntry.FileName;
//
//            logger.info("Directory Filename = " + arcDirEntry.FileName);
//            logger.info("dataID = " + dataId);
//          byte[] data = this.GetData(dataId);
//          string str = destination;
//          if (!str.EndsWith("\\", StringComparison.Ordinal))
//            str += "\\";
//          string path = str + arcDirEntry.FileName;
//          if (!Directory.Exists(Path.GetDirectoryName(path)))
//            Directory.CreateDirectory(Path.GetDirectoryName(path));
//          if (TQDebug.ArcFileDebugLevel > 1)
//            TQDebug.DebugWriteLine("Creating File - " + path);
//          using (FileStream fileStream = new FileStream(path, FileMode.Create, FileAccess.Write))
//            fileStream.Write(data, 0, data.Length);
//        }
//        logger.debug("Exiting ARCFile.ReadARCFile()");
//        return true;
//      }
//      catch (IOException ex)
//      {
//        if (!TQDebug.DebugEnabled)
//          TQDebug.DebugEnabled = true;
//        TQDebug.DebugWriteLine("ARCFile.ReadARCFile() - Error reading arcfile");
//        TQDebug.DebugWriteLine(ex.ToString());
//        return false;
//      }
//    }
//
//    public byte[] GetData(string dataId)
//    {
//      if (TQDebug.ArcFileDebugLevel > 0)
//        logger.info "ARCFile.GetData({0})", new object[1]
//        {
//          (object) dataId
//        }));
//      if (!this.fileHasBeenRead)
//        this.ReadARCToC();
//      if (this.directoryEntries == null)
//      {
//        logger.info(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error - Could not read {0}", new object[1]
//          {
//            (object) this.FileName
//          }));
//        return (byte[]) null;
//      }
//      dataId = TQData.NormalizeRecordPath(dataId);
//      if (TQDebug.ArcFileDebugLevel > 1)
//        logger.info "Normalized dataID = {0}", new object[1]
//        {
//          (object) dataId
//        }));
//      int num1 = dataId.IndexOf('\\');
//      if (num1 != -1)
//        dataId = dataId.Substring(num1 + 1);
//      ArcFile.ARCDirEntry directoryEntry;
//      try
//      {
//        directoryEntry = this.directoryEntries[dataId];
//      }
//      catch (KeyNotFoundException ex)
//      {
//        logger.info(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Error - {0} not found.", new object[1]
//          {
//            (object) dataId
//          }));
//        return (byte[]) null;
//      }
//      using (FileStream fileStream = new FileStream(this.FileName, FileMode.Open, FileAccess.Read))
//      {
//        byte[] buffer = new byte[directoryEntry.RealSize];
//        int offset = 0;
//        if (directoryEntry.StorageType == 1 && directoryEntry.CompressedSize == directoryEntry.RealSize)
//        {
//          if (TQDebug.ArcFileDebugLevel > 1)
//            logger.info "Offset={0}  Size={1}", new object[2]
//            {
//              (object) directoryEntry.FileOffset,
//              (object) directoryEntry.RealSize
//            }));
//          fileStream.Seek((long) directoryEntry.FileOffset, SeekOrigin.Begin);
//          fileStream.Read(buffer, 0, directoryEntry.RealSize);
//        }
//        else
//        {
//          foreach (ArcFile.ARCPartEntry part in directoryEntry.Parts)
//          {
//            fileStream.Seek((long) part.FileOffset, SeekOrigin.Begin);
//            fileStream.ReadByte();
//            fileStream.ReadByte();
//            using (DeflateStream deflateStream = new DeflateStream((Stream) fileStream, CompressionMode.Decompress, true))
//            {
//              int num2 = 0;
//              int num3;
//              while ((num3 = deflateStream.Read(buffer, offset, buffer.Length - offset)) > 0)
//              {
//                offset += num3;
//                num2 += num3;
//                int realSize = part.RealSize;
//              }
//            }
//          }
//        }
//        logger.debug("Exiting ARCFile.GetData()");
//        return buffer;
//      }
//    }
//
//    public string[] GetKeyTable()
//    {
//      if (this.keys == null || this.keys.Length == 0)
//        this.BuildKeyTable();
//      return (string[]) this.keys.Clone();
//    }
//
//    public bool Read()
//    {
//      try
//      {
//        if (!this.fileHasBeenRead)
//          this.ReadARCToC();
//        return this.directoryEntries != null;
//      }
//      catch (IOException ex)
//      {
//        if (!TQDebug.DebugEnabled)
//          TQDebug.DebugEnabled = true;
//        TQDebug.DebugWriteLine(ex.ToString());
//        return false;
//      }
//    }

    private fun ReadARCToC():Unit
    {
      fileHasBeenRead = true;
      logger.debug{ "ARCFile.ReadARCToC(${FileName})"}


      val ras = BufferedRandomAccessFile(FileName,"r")
        .use { it->
        if (it.readByte() !=  65.toByte() || it.readByte() !=  82.toByte() || it.readByte() !=  67.toByte() || it.length()- it.filePointer < 33L)  return@use
        it.seek(8L);
        val capacity = it.readInt()
        val length1 = it.readInt()
        logger.debug{ "numEntries=${capacity}, numParts=${length1}"}
        val arcPartEntryArray = arrayOfNulls<ARCPartEntry>(length1)
        val arcDirEntryArray = arrayOfNulls<ARCDirEntry>(capacity)

          logger.info("Seeking to tocOffset location")
          it.seek(24L)
          val offset = it.readInt().toLong()
          logger.debug { "tocOffset = ${offset}"}
          if (it.length()- it.filePointer <  (offset + 12))  return@use
          it.seek( offset)

            for (index in 1..length1)
            {
              arcPartEntryArray[index] = ARCPartEntry( it.readInt(),  it.readInt(),  it.readInt());
              logger.debug {  "parts[{$index}]"}
              logger.debug {  "  fileOffset=${arcPartEntryArray[index]?.FileOffset}, compressedSize=${arcPartEntryArray[index]?.CompressedSize}, realSize=${arcPartEntryArray[index]?.RealSize}"}

            }

          val position = it.getFilePointer()
          val num1 = 44 * capacity
          logger.debug {   "fileNamesOffset = {$position}.  Seeking to {$num1} to read file record data."}
          it.seek( it.length() - num1);

          for (index1 in 1..capacity)
          {
            val num2 = it.readInt()
            arcDirEntryArray[index1] = ARCDirEntry(num2, it.readInt(), it.readInt(), it.readInt())
              logger.info {"StorageType=${num2}"}
            val num3 = it.readInt()
              logger.info{"Crap2=${num3}"}
          val num4 = it.readInt()
          logger.info{ "Crap3=${num4}"}
      val num5 = it.readInt()
      logger.info{ "Crap4=${num5}"}
            val length2 = it.readInt()
            if (length2 < 1)
            {
              arcDirEntryArray[index1]?.Parts?.clear()
                logger.info {"File ${index1} is not compressed."}
            }
            else {
//              arcDirEntryArray[index1].Parts = ARCPartEntry [length2];
            }
            val num6 = it.readInt()
            val num7 = it.readInt()
              logger.info {"Filename Length=${num7}"}
            val num8 = it.readInt()
              logger.info {"Filename Offset=${num8}"}
              logger.info {"record[{$index1}]"}
              logger.info {"  offset=${arcDirEntryArray[index1]?.FileOffset} compressedSize=${arcDirEntryArray[index1]?.CompressedSize} realSize=${arcDirEntryArray[index1]?.RealSize}"}
              if (num2 != 1 && arcDirEntryArray[index1]?.IsActive() == true)
                logger.info {"  numParts=${arcDirEntryArray[index1]?.Parts?.size} firstPart=${num6} lastPart=${(num6 + (arcDirEntryArray[index1]?.Parts?.size?:0) - 1)}"}
              else
                logger.info {"  INACTIVE firstPart=${num6}"}


            if (num2 != 1 && arcDirEntryArray[index1]?.IsActive()== true)
            {
              for (index2 in 1..arcDirEntryArray[index1]!!.Parts!!.size)
              arcDirEntryArray[index1]!!.Parts[index2] = arcPartEntryArray[index2 + num6]!!
            }
          }
            it.seek( position)
            var bytes =  ByteArray(2048)
            for (index3 in 1 until capacity)
            {
              if (arcDirEntryArray[index3]?.IsActive() == true)
              {
                  logger.info {"Reading entry name ${index3}"}
                var index4 = 0;
                while (run {
                    bytes[index4] = it.readByte()
                    bytes[index4++] > 0
                  })
                {
                  if (bytes[index4 - 1].toInt() ==  3)
                  {
                    it.seek(it.filePointer-1)
                    --index4
                    bytes[index4] =  0
                      logger.info ("Null file - inactive?")
                    break
                  }
                  if (index4 >= bytes.size)
                  {
                    logger.debug("ARCFile.ReadARCToC() Error - Buffer size of 2048 has been exceeded.");
//                    if (TQDebug.ArcFileDebugLevel > 2)
//                    {
//                      TQDebug.DebugWriteLine("Buffer contents:\n");
//                      for (int index5 = 0; index5 < index4; ++index5)
//                        TQDebug.DebugWrite(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "0x{0:X}", new object[1]
//                        {
//                          (object) bytes[index5]
//                        }));
//                      TQDebug.DebugWriteLine(string.Empty);
//                    }
                  }
                }
                 logger.info {"Read ${index4} bytes for name.  Converting to string."}

                var recordId:String
                if (index4 >= 1)
                {
                  recordId = String(bytes.sliceArray(0..index4-1))
                }
                else
                  recordId =  "Null File ${index3}"
                arcDirEntryArray[index3]?.FileName = TQData.NormalizeRecordPath(recordId);
                  logger.info {"Name ${index3} = '${arcDirEntryArray[index3]?.FileName}'"}
              }
            }
              logger.debug("Creating Dictionary");
            for (index in 1 until capacity)
            {
              if (arcDirEntryArray[index]?.IsActive() == true)
                directoryEntries[arcDirEntryArray[index]!!.FileName] =  arcDirEntryArray[index]!!
            }
            logger.info("Exiting ARCFile.ReadARCToC()");
          }
        }



//
//    public void Write(string baseFolder, string record, string destinationFileName)
//    {
//      try
//      {
//        if (!this.fileHasBeenRead)
//          this.ReadARCToC();
//        byte[] data = this.GetData(Path.GetFileNameWithoutExtension(this.FileName) + "\\" + record);
//        if (data == null)
//          return;
//        string str = baseFolder;
//        if (!str.EndsWith("\\", StringComparison.OrdinalIgnoreCase))
//          str += "\\";
//        string path = str + destinationFileName;
//        if (!Directory.Exists(Path.GetDirectoryName(path)))
//          Directory.CreateDirectory(Path.GetDirectoryName(path));
//        using (FileStream fileStream = new FileStream(path, FileMode.Create, FileAccess.Write))
//          fileStream.Write(data, 0, data.Length);
//      }
//      catch (IOException ex)
//      {
//        if (!TQDebug.DebugEnabled)
//          TQDebug.DebugEnabled = true;
//        TQDebug.DebugWriteLine(ex.ToString());
//      }
//    }
//
//    public int Count => this.directoryEntries.Count;

     data class ARCDirEntry(
       val StorageType:Int,
       val FileOffset:Int,
       val CompressedSize:Int,
       val RealSize:Int,
       var FileName:String = "",
       val Parts:ArrayList<ARCPartEntry> = ArrayList(),) {

      public fun IsActive(): Boolean = this.StorageType == 1 || (this.Parts != null && this.Parts.isNotEmpty())
    }

     data class ARCPartEntry
      (
        val FileOffset:Int,
        val CompressedSize:Int,
        val RealSize:Int,
    )
  }

