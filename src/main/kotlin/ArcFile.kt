import java.io.BufferedInputStream
import java.io.File
import java.io.ObjectInputStream
import java.io.RandomAccessFile
import java.util.*

// Place definition above class declaration to make field static
private val logger = mu.KotlinLogging.logger {}

public class ArcFile(FileName:String) {
  private var fileHasBeenRead: Boolean = false
  private var directoryEntries: Map<String, ArcFile.ARCDirEntry> = emptyMap()
  private var keys: SortedSet<String> = sortedSetOf("")


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
      logger.debug{ "ARCFile.ReadARCToC({$FileName})"}


      val ras = RandomAccessFile(File(FileName),"r")
        .use { it->
        if (it.readByte() !=  65.toByte() || it.readByte() !=  82.toByte() || it.readByte() !=  67.toByte() || it.available() < 33L)  return@use
        it.seek(8L);
        val capacity = it.readInt()
        val length1 = it.readInt()
        logger.debug{ "numEntries={$capacity}, numParts={$length1}"}
        val arcPartEntryArray = arrayOfNulls<ARCPartEntry>(length1)
        val arcDirEntryArray = arrayOfNulls<ARCDirEntry>(capacity)

          logger.info("Seeking to tocOffset location")
          it.seek(24L)
          val offset = it.readInt()
          logger.debug { "tocOffset = {$offset}"}
          if (it.available() <  (offset + 12))  return@use
          it.seek( offset)

            for (index in 1..length1)
            {
              arcPartEntryArray[index] = ARCPartEntry( it.readInt(),  it.readInt(),  it.readInt());
              logger.debug {  "parts[{$index}]"}
              logger.debug {  "  fileOffset={$arcPartEntryArray[index].FileOffset}, compressedSize={$arcPartEntryArray[index].CompressedSize}, realSize={$arcPartEntryArray[index].RealSize}"}

            }

          val position = it.getFilePointer()
          val num1 = 44 * capacity
          logger.debug {   "fileNamesOffset = {$position}.  Seeking to {$num1} to read file record data."}
          it.seek( it.length() - num1);
      }




            for (int index1 = 0; index1 < capacity; ++index1)
            {
              arcDirEntryArray[index1] = new ArcFile.ARCDirEntry();
              int num2 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
                logger.info "StorageType={0}", new object[1]
                {
                  (object) num2
                }));
              arcDirEntryArray[index1].StorageType = num2;
              arcDirEntryArray[index1].FileOffset = binaryReader.ReadInt32();
              arcDirEntryArray[index1].CompressedSize = binaryReader.ReadInt32();
              arcDirEntryArray[index1].RealSize = binaryReader.ReadInt32();
              int num3 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
                logger.info "Crap2={0}", new object[1]
                {
                  (object) num3
                }));
              int num4 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
                logger.info "Crap3={0}", new object[1]
                {
                  (object) num4
                }));
              int num5 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
                logger.info "Crap4={0}", new object[1]
                {
                  (object) num5
                }));
              int length2 = binaryReader.ReadInt32();
              if (length2 < 1)
              {
                arcDirEntryArray[index1].Parts = (ArcFile.ARCPartEntry[]) null;
                if (TQDebug.ArcFileDebugLevel > 2)
                  logger.info "File {0} is not compressed.", new object[1]
                  {
                    (object) index1
                  }));
              }
              else
                arcDirEntryArray[index1].Parts = new ArcFile.ARCPartEntry[length2];
              int num6 = binaryReader.ReadInt32();
              int num7 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
                logger.info "Filename Length={0}", new object[1]
                {
                  (object) num7
                }));
              int num8 = binaryReader.ReadInt32();
              if (TQDebug.ArcFileDebugLevel > 2)
              {
                logger.info "Filename Offset={0}", new object[1]
                {
                  (object) num8
                }));
                logger.info "record[{0}]", new object[1]
                {
                  (object) index1
                }));
                logger.info "  offset={0} compressedSize={1} realSize={2}", new object[3]
                {
                  (object) arcDirEntryArray[index1].FileOffset,
                  (object) arcDirEntryArray[index1].CompressedSize,
                  (object) arcDirEntryArray[index1].RealSize
                }));
                if (num2 != 1 && arcDirEntryArray[index1].IsActive)
                  logger.info "  numParts={0} firstPart={1} lastPart={2}", new object[3]
                  {
                    (object) arcDirEntryArray[index1].Parts.Length,
                    (object) num6,
                    (object) (num6 + arcDirEntryArray[index1].Parts.Length - 1)
                  }));
                else
                  logger.info "  INACTIVE firstPart={0}", new object[1]
                  {
                    (object) num6
                  }));
              }
              if (num2 != 1 && arcDirEntryArray[index1].IsActive)
              {
                for (int index2 = 0; index2 < arcDirEntryArray[index1].Parts.Length; ++index2)
                  arcDirEntryArray[index1].Parts[index2] = arcPartEntryArray[index2 + num6];
              }
            }
            input.Seek((long) position, SeekOrigin.Begin);
            byte[] bytes = new byte[2048];
            ASCIIEncoding asciiEncoding = new ASCIIEncoding();
            for (int index3 = 0; index3 < capacity; ++index3)
            {
              if (arcDirEntryArray[index3].IsActive)
              {
                if (TQDebug.ArcFileDebugLevel > 2)
                  logger.info "Reading entry name {0:n0}", new object[1]
                  {
                    (object) index3
                  }));
                int index4 = 0;
                while ((bytes[index4++] = binaryReader.ReadByte()) > (byte) 0)
                {
                  if (bytes[index4 - 1] == (byte) 3)
                  {
                    input.Seek(-1L, SeekOrigin.Current);
                    --index4;
                    bytes[index4] = (byte) 0;
                    if (TQDebug.ArcFileDebugLevel > 2)
                    {
                      TQDebug.DebugWriteLine("Null file - inactive?");
                      break;
                    }
                    break;
                  }
                  if (index4 >= bytes.Length)
                  {
                    TQDebug.DebugWriteLine("ARCFile.ReadARCToC() Error - Buffer size of 2048 has been exceeded.");
                    if (TQDebug.ArcFileDebugLevel > 2)
                    {
                      TQDebug.DebugWriteLine("Buffer contents:\n");
                      for (int index5 = 0; index5 < index4; ++index5)
                        TQDebug.DebugWrite(string.Format((IFormatProvider) CultureInfo.InvariantCulture, "0x{0:X}", new object[1]
                        {
                          (object) bytes[index5]
                        }));
                      TQDebug.DebugWriteLine(string.Empty);
                    }
                  }
                }
                if (TQDebug.ArcFileDebugLevel > 2)
                  logger.info "Read {0:n0} bytes for name.  Converting to string.", new object[1]
                  {
                    (object) index4
                  }));
                string recordId;
                if (index4 >= 1)
                {
                  char[] chars = new char[asciiEncoding.GetCharCount(bytes, 0, index4 - 1)];
                  asciiEncoding.GetChars(bytes, 0, index4 - 1, chars, 0);
                  recordId = new string(chars);
                }
                else
                  recordId = string.Format((IFormatProvider) CultureInfo.InvariantCulture, "Null File {0}", new object[1]
                  {
                    (object) index3
                  });
                arcDirEntryArray[index3].FileName = TQData.NormalizeRecordPath(recordId);
                if (TQDebug.ArcFileDebugLevel > 2)
                  logger.info "Name {0:n0} = '{1}'", new object[2]
                  {
                    (object) index3,
                    (object) arcDirEntryArray[index3].FileName
                  }));
              }
            }
            Dictionary<string, ArcFile.ARCDirEntry> dictionary = new Dictionary<string, ArcFile.ARCDirEntry>(capacity);
            if (TQDebug.ArcFileDebugLevel > 1)
              TQDebug.DebugWriteLine("Creating Dictionary");
            for (int index = 0; index < capacity; ++index)
            {
              if (arcDirEntryArray[index].IsActive)
                dictionary.Add(arcDirEntryArray[index].FileName, arcDirEntryArray[index]);
            }
            this.directoryEntries = dictionary;
            if (TQDebug.ArcFileDebugLevel <= 0)
              return;
            TQDebug.DebugWriteLine("Exiting ARCFile.ReadARCToC()");
          }
        }
      }
      catch (IOException ex)
      {
        if (!TQDebug.DebugEnabled)
          TQDebug.DebugEnabled = true;
        TQDebug.DebugWriteLine("ARCFile.ReadARCToC() - Error reading arcfile");
        TQDebug.DebugWriteLine(ex.ToString());
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

     class ARCDirEntry(
        FileName:String,
        StorageType:Int,
        FileOffset:Int,
        CompressedSize:Int,
        RealSize:Int,
        Parts:List<ArcFile.ARCPartEntry>,) {

//      public fun IsActive(): Boolean = this.StorageType == 1 || this.Parts != null
    }

     class ARCPartEntry
      (
        FileOffset:Int,
        CompressedSize:Int,
        RealSize:Int,
    )
  }

