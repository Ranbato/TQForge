import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.zip.InflaterInputStream

// Place definition above class declaration to make field static
private val logger = mu.KotlinLogging.logger {}

public class ArcFile {
    private var fileHasBeenRead = false
    private var directoryEntries = mutableMapOf<String, ARCDirEntry>()
    private var keys = mutableSetOf<String>()
    private val FileName: String

    constructor(FileName: String) {
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

    public fun GetData(dataIdOrg: String): ByteArray {
        logger.info { "ARCFile.GetData($dataIdOrg)" }
        if (!this.fileHasBeenRead)
            this.ReadARCToC();
        if (this.directoryEntries.isEmpty()) {
            logger.info { "Error - Could not read ${FileName}" }
            return ByteArray(0)
        }
        var dataId = TQData.NormalizeRecordPath(dataIdOrg);
        logger.info { "Normalized dataID = ${dataId}" }
        val num1 = dataId.indexOf('\\');
        if (num1 != -1)
            dataId = dataId.substring(num1 + 1);

        val directoryEntry = this.directoryEntries[dataId];

        if (directoryEntry == null) {
            logger.info { "Error - $dataId not found." }
            return ByteArray(0)
        }
        BufferedInputStream(FileInputStream(FileName)).use { fileStream ->
            val buffer = ByteArray(directoryEntry.RealSize)
            var offset = 0;
            if (directoryEntry.StorageType == 1 && directoryEntry.CompressedSize == directoryEntry.RealSize) {
                logger.info { "Offset=${directoryEntry.FileOffset}  Size=${directoryEntry.RealSize}" }
                fileStream.skip(directoryEntry.FileOffset.toLong());
                fileStream.read(buffer, 0, directoryEntry.RealSize);
            } else {

                directoryEntry.Parts.forEach { part ->

                    fileStream.skip(part.FileOffset.toLong());
                    fileStream.read()
                    fileStream.read();
                    InflaterInputStream(fileStream).use {
                        var num2 = 0
                        var num3: Int
                        while (run {
                                num3 = it.read(buffer, offset, buffer.size - offset)
                                num3 > 0
                            }) {
                            offset += num3;
                            num2 += num3;
                            var realSize = part.RealSize;
                        }
                    }
                }
            }
            logger.debug("Exiting ARCFile.GetData()");
            return buffer;
        }
    }


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

    private fun ReadARCToC(): Unit {
        fileHasBeenRead = true;
        logger.debug { "ARCFile.ReadARCToC(${FileName})" }


        val file = File(FileName)
        val rawData = FileChannel.open(file.toPath()).map(FileChannel.MapMode.READ_ONLY, 0, file.length())
        rawData.order(ByteOrder.LITTLE_ENDIAN)

        if (rawData.get() != 65.toByte() || rawData.get() != 82.toByte() || rawData.get() != 67.toByte() || file.length() < 33L) return
        rawData.position(8);
        val capacity = rawData.getInt()
        val length1 = rawData.getInt()
        logger.debug { "numEntries=${capacity}, numParts=${length1}" }
        val arcPartEntryArray = arrayOfNulls<ARCPartEntry>(length1)
        val arcDirEntryArray = arrayOfNulls<ARCDirEntry>(capacity)

        logger.info("Seeking to tocOffset location")
        rawData.position(24)
        val offset = rawData.getInt()
        logger.debug { "tocOffset = ${offset}" }
        if (file.length() < (offset + 12)) return
        rawData.position(offset)

        for (index in 0 until length1) {
            arcPartEntryArray[index] = ARCPartEntry(rawData.getInt(), rawData.getInt(), rawData.getInt());
            logger.debug { "parts[{$index}]" }
            logger.debug { "  fileOffset=${arcPartEntryArray[index]?.FileOffset}, compressedSize=${arcPartEntryArray[index]?.CompressedSize}, realSize=${arcPartEntryArray[index]?.RealSize}" }

        }

        val position = rawData.position()
        val num1 = 44 * capacity
        logger.debug { "fileNamesOffset = {$position}.  Seeking to {$num1} to read file record data." }
        rawData.position((file.length() - num1).toInt());

        for (index1 in 0 until capacity) {
            val num2 = rawData.getInt()
            arcDirEntryArray[index1] = ARCDirEntry(num2, rawData.getInt(), rawData.getInt(), rawData.getInt())
            logger.info { "StorageType=${num2}" }
            val num3 = rawData.getInt()
            logger.info { "Crap2=${num3}" }
            val num4 = rawData.getInt()
            logger.info { "Crap3=${num4}" }
            val num5 = rawData.getInt()
            logger.info { "Crap4=${num5}" }
            val length2 = rawData.getInt()
            if (length2 < 1) {
                arcDirEntryArray[index1]?.Parts?.clear()
                logger.info { "File ${index1} is not compressed." }
            } else {
//              arcDirEntryArray[index1].Parts = ARCPartEntry [length2];
            }
            val num6 = rawData.getInt()
            val num7 = rawData.getInt()
            logger.info { "Filename Length=${num7}" }
            val num8 = rawData.getInt()
            logger.info { "Filename Offset=${num8}" }
            logger.info { "record[{$index1}]" }
            logger.info { "  offset=${arcDirEntryArray[index1]?.FileOffset} compressedSize=${arcDirEntryArray[index1]?.CompressedSize} realSize=${arcDirEntryArray[index1]?.RealSize}" }
            if (num2 != 1 && arcDirEntryArray[index1]?.IsActive() == true)
                logger.info { "  numParts=${arcDirEntryArray[index1]?.Parts?.size} firstPart=${num6} lastPart=${(num6 + (arcDirEntryArray[index1]?.Parts?.size ?: 0) - 1)}" }
            else
                logger.info { "  INACTIVE firstPart=${num6}" }


            if (num2 != 1 && arcDirEntryArray[index1]?.IsActive() == true) {
                for (index2 in 0 until arcDirEntryArray[index1]!!.Parts!!.size)
                    arcDirEntryArray[index1]!!.Parts[index2] = arcPartEntryArray[index2 + num6]!!
            }
        }
        rawData.position(position)
        var bytes = ByteArray(2048)
        for (index3 in 0 until capacity) {
            if (arcDirEntryArray[index3]?.IsActive() == true) {
                logger.info { "Reading entry name ${index3}" }
                var index4 = 0;
                while (run {
                        bytes[index4] = rawData.get()
                        bytes[index4++] > 0
                    }) {
                    if (bytes[index4 - 1].toInt() == 3) {
                        rawData.position(rawData.position() - 1)
                        --index4
                        bytes[index4] = 0
                        logger.info("Null file - inactive?")
                        break
                    }
                    if (index4 >= bytes.size) {
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
                logger.info { "Read ${index4} bytes for name.  Converting to string." }

                var recordId: String
                if (index4 >= 1) {
                    recordId = String(bytes.sliceArray(0..index4 - 1))
                } else
                    recordId = "Null File ${index3}"
                arcDirEntryArray[index3]?.FileName = TQData.NormalizeRecordPath(recordId);
                logger.info { "Name ${index3} = '${arcDirEntryArray[index3]?.FileName}'" }
            }
        }
        logger.debug("Creating Dictionary");
        for (index in 0 until capacity) {
            if (arcDirEntryArray[index]?.IsActive() == true)
                directoryEntries[arcDirEntryArray[index]!!.FileName] = arcDirEntryArray[index]!!
        }
        logger.info("Exiting ARCFile.ReadARCToC()");
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
        val StorageType: Int = 0,
        val FileOffset: Int = 0,
        val CompressedSize: Int = 0,
        val RealSize: Int = 0,
        var FileName: String = "",
        val Parts: ArrayList<ARCPartEntry> = ArrayList(),
    ) {


        public fun IsActive(): Boolean = this.StorageType == 1 || (this.Parts != null && this.Parts.isNotEmpty())
    }

    data class ARCPartEntry
        (
        val FileOffset: Int,
        val CompressedSize: Int,
        val RealSize: Int,
    ) {}
}


