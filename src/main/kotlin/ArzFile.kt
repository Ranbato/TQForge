import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.DataInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.file.Files
import java.util.zip.Inflater
import kotlin.math.roundToInt


private val logger = mu.KotlinLogging.logger {}
public class  ArzFile constructor(filename:String)
  {
    private val fileName:String
    private var rawData = ByteArray(0)
    private val strings = mutableListOf<String>()
    private var recordInfo = mutableMapOf<String, ArzFile.RecordInfo>()
    private val cache = mutableMapOf <String, DBRecord>()
    private val keys= mutableListOf<String>()
    private val requiredKeys= mutableListOf<String>()
    public var requiredKeyIndex = 0

    init {
      this.fileName = filename
    }

    private fun BuildKeyTable():Unit
    {
      if (this.recordInfo == null || this.recordInfo.size == 0) {
        return;
      }
      var index = 0;
//      DBClasses.getClasses();
      this.requiredKeyIndex = 0;
      recordInfo.keys.sorted().forEach { key ->

        this.keys[index] = key;
        val recordType = this.recordInfo[key]?.RecordType;
        if (recordType != null && !recordType.isNullOrEmpty())
          this.requiredKeys[this.requiredKeyIndex++] = key
        index++
      }
    }

    public fun GetItem(recordIdOrg:String):DBRecord?
    {
      if (recordIdOrg.isNullOrEmpty()) {
        return null
      }
      val recordId = TQData.NormalizeRecordPath(recordIdOrg);
      return if(cache.containsKey(recordId)) {
        this.cache[recordId];
      } else {
        if(recordInfo.containsKey(recordId)){
          val recordInfo = this.recordInfo[recordId]
          recordInfo?.Decompress(this)
          } else {
          null
          }

      }
    }

    public fun GetKeyTable():List<String>
    {
      if (this.keys == null || this.keys.size == 0)
        this.BuildKeyTable();
      return this.keys.toList()
    }

    public fun GetRequiredKeyTable():List<String>
    {
      if (this.requiredKeys == null || this.requiredKeys.size == 0)
        this.BuildKeyTable();
      return this.requiredKeys.toList()
    }

    public fun GetRecordNotCached( recordIdOrg:String):DBRecord?
    {
      val recordId = TQData.NormalizeRecordPath(recordIdOrg);
      return if(cache.containsKey(recordId)) {
        this.cache[recordId]
      }else {
          if(recordInfo.containsKey(recordId))
          {
            this.recordInfo[recordId]?.Decompress(this)
          }else {
            null
          }
      }
    }

    private fun Getstring(index:Int) = this.strings[index].orEmpty()

    public fun Read():Boolean
    {


      val file = File(fileName)
      val len = file.length() as Int
      val reader = DataInputStream(BufferedInputStream(file.inputStream()))
      rawData = Files.readAllBytes(file.toPath())


          val numArray = IntArray(6)
          for (index in numArray.indices)
          {
            val header = reader.readInt()
            numArray[index] = header
            val hex = "(0x%01X)".format(header)
            logger.info{"Header[${index}] = ${header} $hex"}
          }
          val pos = numArray[1]
          val numEntries = numArray[3];
          this.ReadStringTable(numArray[4] + reader.available() - file.length().toInt(), reader);
          this.ReadRecordTable(pos+ reader.available() - file.length().toInt(), numEntries, reader);
          for ( index in 0 until 4)
          {
            val num = reader.readInt()
            val hex = "$num 0x%01X".format(num)
            logger.info{"Footer $hex"}
          }
//        }
//
//      catch ( ex: IOException)
//      {
//        if (!TQDebug.DebugEnabled)
//          TQDebug.DebugEnabled = true;
//        TQDebug.DebugWriteLine(ex.ToString());
//        return false;
//      }
//      finally
//      {
//        outStream?.Close();
//      }
      return true;
    }

    private fun ReadRecordTable(
       pos:Int,
      numEntries:Int,
       reader: DataInputStream
    )
    {
      this.recordInfo = LinkedHashMap((numEntries * 1.2).roundToInt());
      reader.skip(pos.toLong())
      logger.info { "RecordTable located at $pos"}
      for (index in 0 until numEntries)
      {
        val recordInfo = ArzFile.RecordInfo();
        recordInfo.Decode(reader, 24, this);
        this.recordInfo.put(TQData.NormalizeRecordPath(recordInfo.ID), recordInfo);
        logger.debug { "recordtable $index ${recordInfo.ID} ${recordInfo.RecordType}"}
      }
    }

    private fun ReadStringTable( pos:Int,  reader:DataInputStream)
    {
      reader.skip(pos.toLong())
      val length = reader.readInt()
      logger.debug { "stringTable located at $pos numstrings= $length"}
      for (index in 0 until length)
      {
        this.strings[index] = TQData.readCString(reader);
        logger.debug { "stringtable entry $index ${strings[index]}"}
      }
    }

    public fun Count() = this.recordInfo.size

    public class RecordInfo
    {
      private var offset =0
      private var idStringIndex = -1
      public var ID =""
      public var RecordType = ""


      public fun Decode(inReader: DataInputStream, baseOffset:Int, arzFile:ArzFile )
      {
        this.idStringIndex = inReader.readInt();
        this.RecordType = TQData.readCString(inReader);
        this.offset = inReader.readInt() + baseOffset;
        inReader.readInt();
        inReader.readInt();
        inReader.readInt();
        this.ID = arzFile.Getstring(this.idStringIndex);
      }

      public fun Decompress( arzFile:ArzFile):DBRecord?
      {
        val buffer = this.DecompressBytes(arzFile)
        val num1 = buffer.size / 4;
        if (buffer.size % 4 > 0)
        {

          logger.error { "Error in ARZFile - ${arzFile.fileName} \n Error while parsing arz record ${this.ID}, data Length = ${buffer.size} which is not a multiple of 4"}
          return null
        }
        val dbRecord = DBRecord(this.ID, this.RecordType)
        DataInputStream(ByteArrayInputStream(buffer)).use {
          var num2 = 0
          while (num2 < num1) {
            val dataType = it.readShort() as Int
            val numberOfValues = it.readShort() as Int
            val index1 = it.readInt()
            val variableName = arzFile.Getstring(index1);
            if (variableName.isEmpty()) {
              logger.error { "Error in ARZFile - ${arzFile.fileName} \nError while parsing arz record ${ID}, variable is NULL" }
              return null
            }
            if (dataType < 0 || dataType > 3) {
              logger.error { "Error in ARZFile - ${arzFile.fileName}\nError while parsing arz record $ID, variable $variableName, bad dataType $dataType" }
              return null
            }
            val variable = Variable(variableName, VariableDataType.values()[dataType], numberOfValues);
            if (numberOfValues < 1) {
              logger.error { "Error in ARZFile - ${arzFile.fileName}\nError while parsing arz record $ID, variable $variableName, bad valCount $numberOfValues" }
              return null
            }
            num2 += 2 + numberOfValues
            for (index2 in 0 until numberOfValues) {
              when (variable.DataType) {
                VariableDataType.Integer,
                VariableDataType.Boolean -> {
                  val num3 = it.readInt()
                  variable.setString(index2, num3.toString())
                }

                VariableDataType.Float -> {
                  val num4 = it.readFloat()
                  variable.setString(index2, num4.toString())
                }

                VariableDataType.StringVar -> {
                  val index3 = it.readInt()
                  val str1 = arzFile.Getstring(index3);
                  variable.setString(index2, str1.trim())
                }

                else -> {
                  val num5 = it.readInt()
                  variable.setString(index2, num5.toString())
                }
              }
              dbRecord.Set(variable);
            }
          }
        }
        return dbRecord;
      }

      private fun DecompressBytes(arzFile :ArzFile):ByteArray
      {
        val inflater = Inflater()
        inflater.setInput(arzFile.rawData, offset,arzFile.rawData.size - offset)
//          memoryStream1.read()
//          memoryStream1.read()
          val memoryStream2 = ByteArrayOutputStream()
            memoryStream2.use {
              val buffer = ByteArray(1024)
              var count = 0
              while (!inflater.finished()) {
                count = inflater.inflate(buffer)
                memoryStream2.write(buffer, 0, count)
              }
            }
        return memoryStream2.toByteArray()
            }
          }
        }




