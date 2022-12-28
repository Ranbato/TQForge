import utils.ByteBufferBackedInputStream
import utils.LittleEndianDataInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.DataInputStream
import java.io.ObjectOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.Files


private val logger = mu.KotlinLogging.logger {}

class Character constructor(val caracterName:String, val caracterFile:String) {
private val  beginBlockPattern = byteArrayOf(

  11,
  0,
  0,
  0,
  98,
  101,
  103,
  105,
  110,
  95,
  98,
  108,
  111,
  99,
  107
)
private val endBlockPattern = byteArrayOf(

  9,
  0,
  0,
  0,
  101,
  110,
  100,
  95,
  98,
  108,
  111,
  99,
  107
)
//private var rawData:ByteArray
private var itemBlockStart:Int =0
private var itemBlockEnd:Int =0
private var equipmentBlockStart:Int =0
private var equipmentBlockEnd:Int =0
private var numberOfSacks:Int =0
private var currentlyFocusedSackNumber:Int =0
private var currentlySelectedSackNumber:Int=0
private var equipmentCtrlIOStreamVersion:Int=0
private val sacks:MutableList<Sack> = mutableListOf()
private var equipmentSack:Sack = Sack()
public var isModified = false
  private var rawData:ByteBuffer = ByteBuffer.allocate(0)



public fun loadFile():Unit
    {
      val file = File(caracterFile)
        try {

            rawData = FileChannel.open(file.toPath()).map(FileChannel.MapMode.READ_ONLY,0,file.length())
            rawData.order( ByteOrder.LITTLE_ENDIAN);

            this.parseRawData();
        }catch(ex:Exception){
            logger.error { "Error opening file $caracterFile ${ex.message} $ex" }
        }
    }

    private fun parseRawData():Unit
    {
          var num = 0;
          var start = 0;
          var offset1 = 0;
          var offset2 = 0;
          var flag1 = false;
          var flag2 = false;
          var nextBlockDelim = 0
          while (run { nextBlockDelim = FindNextBlockDelim( start)
              (!flag1 || !flag2) && nextBlockDelim != -1})
          {
            if ( rawData[nextBlockDelim] ==  beginBlockPattern[0])
            {
              ++num;
              val offset3 = nextBlockDelim + beginBlockPattern.size + 4;
              val upperInvariant = TQData.readCString(rawData,offset3).uppercase()
              start = rawData.position() //offset3 + upperInvariant.length +1
              var str:String
              if (upperInvariant == "BEGIN_BLOCK")
              {
                str = "(NONAME)"
                start -= beginBlockPattern.size
              }
              else if (upperInvariant == "END_BLOCK")
              {
                str = "(NONAME)"
                start -= endBlockPattern.size;
              }
              else if (upperInvariant == "ITEMPOSITIONSSAVEDASGRIDCOORDS")
              {
                start += 4;
                offset1 = start;
                flag1 = true;
              }
              else if (upperInvariant =="USEALTERNATE")
              {
                start += 4;
                offset2 = start;
                flag2 = true;
              }
            }
            else
            {
              --num;
              start = nextBlockDelim + endBlockPattern.size
            }
          }
          if (flag1)
          {
            try
            {
              this.parseItemBlock(offset1);
            }
            catch ( ex:Exception)
            {
              logger.debug("Error parsing player file Item Block - $caracterName", ex)
//              throw new ArgumentException("Error parsing player file Item Block- " + this.caracterFile, (Exception) ex);
            }
          }
          if (true)
            return;
          try
          {
            this.parseEquipmentBlock(offset2);
          }
          catch ( ex:Exception)
          {
            logger.debug("Error parsing player file Item Block - " + this.caracterName);
            logger.debug(ex.toString());
          }
        }


    private fun FindNextBlockDelim(start:Int):Int
    {
      var index1 = 0
      var index2 = 0
      for ( index3 in start until rawData.limit() )
      {
        if (rawData[index3] == beginBlockPattern[index1])
        {
          ++index1;
          if (index1 == beginBlockPattern.size)
            return index3 + 1 - index1;
        }
        else if (index1 > 0)
        {
          index1 = 0;
          if (rawData[index3] == beginBlockPattern[index1])
            ++index1;
        }
        if (rawData[index3] == endBlockPattern[index2])
        {
          ++index2;
          if (index2 == endBlockPattern.size)
            return index3 + 1 - index2;
        }
        else if (index2 > 0)
        {
          index2 = 0;
          if (rawData[index3] == endBlockPattern[index2])
            ++index2;
        }
      }
      return -1;
    }

    private fun parseEquipmentBlock(offset:Int):Unit
    {
      try
      {
        this.equipmentBlockStart = offset;
        TQData.validateNextString(rawData,"equipmentCtrlIOStreamVersion", offset)
        this.equipmentCtrlIOStreamVersion = rawData.getInt();
        this.equipmentSack = Sack()
        this.equipmentSack.sackType = SackType.Equipment;
        this.equipmentSack.IsImmortalThrone = true;
        this.equipmentSack.Parse(rawData);
        this.equipmentBlockEnd =  rawData.position()
      }
      catch (ex:Exception )
      {
        logger.error { "Error $ex" }
      }
    }

    private fun parseItemBlock(offset:Int):Unit
    {
      try
      {
        this.itemBlockStart = offset;
        TQData.validateNextString(rawData, "numberOfSacks", offset);
        this.numberOfSacks = rawData.getInt();
        TQData.validateNextString(rawData,"currentlyFocusedSackNumber");
        this.currentlyFocusedSackNumber = rawData.getInt();
        TQData.validateNextString(rawData,"currentlySelectedSackNumber");
        this.currentlySelectedSackNumber = rawData.getInt();
//        sacks. = Sack[this.numberOfSacks];
        for (index in 0 until numberOfSacks)
        {
          val sack =  Sack();
          sack.sackType = SackType.Sack;
          sack.IsImmortalThrone = true;
          sack.Parse(rawData);
          sacks[index] = sack
        }
        this.itemBlockEnd = rawData.position()
      }
      catch ( ex:java.lang.Exception)
      {
        throw ex
      }
    }

    public fun CreateEmptySacks():Sack
    {
      isModified = false
      return Sack()

    }

    public fun emptyMainSack()
    {
      GetMainSack().EmptySack();
      isModified = true;
    }

    public fun Save( fileName:String, backup: Boolean = false):Unit
    {
      var str1 = "";
      if (backup)
      {
        val orig = File(fileName)
        val dir = orig.parentFile
        val str2 = File(dir, "BlaksmithBackup_Player.chr")
        if (!str2.exists())
        {
          logger.info("Save file backup created: $str2")
          Files.copy(orig.toPath(), str2.toPath());
        }
        str1 = " Original file backed to BlaksmithBackup_Player.chr";
      }
      val buffer = Encode();
      Files.write(File(fileName).toPath(),buffer)
      this.isModified = false;
      this.GetMainSack().isModified = false;
      logger.info("Character data saved to $fileName $str1");
    }

     fun Encode():ByteArray
    {
      val sourceArray = this.EncodeItemData();
        val tempArray = ByteArray(rawData.limit() - this.itemBlockEnd)
      var destinationArray = ByteArray(itemBlockStart + sourceArray.size + (rawData.limit() - this.itemBlockEnd))
        rawData.get(destinationArray,0,itemBlockStart)
      sourceArray.copyInto(destinationArray, 0,  this.itemBlockStart, sourceArray.size)
      this.rawData.get(this.itemBlockEnd, destinationArray, itemBlockStart + sourceArray.size, this.rawData.limit() - this.itemBlockEnd)
      return destinationArray
    }

    private fun EncodeEquipmentData():ByteArray
    {
      var length = 0
      val output =  ByteArrayOutputStream(2048)

        val writer = ObjectOutputStream(output)
          TQData.writeCString(writer, "equipmentCtrlIOStreamVersion");
          writer.write(this.equipmentCtrlIOStreamVersion);
          this.equipmentSack.Encode(writer);
          length = output.size()

        val buffer = output.toByteArray()

      return if (length == buffer.size) {
        buffer
      } else {
        buffer.copyOfRange(0, length)
      }
    }

    private fun EncodeItemData():ByteArray
    {
      var length = 0
      val output =  ByteArrayOutputStream(2048)

      val writer = ObjectOutputStream(output)
          TQData.writeCString(writer, "numberOfSacks");
          writer.write(this.numberOfSacks);
          TQData.writeCString(writer, "currentlyFocusedSackNumber");
          writer.write(this.currentlyFocusedSackNumber);
          TQData.writeCString(writer, "currentlySelectedSackNumber");
          writer.write(this.currentlySelectedSackNumber);
          sacks.forEach { sack -> sack.Encode(writer) }

          length =  output.size()

        val buffer = output.toByteArray()

        return if (length == buffer.size) {
            buffer
        } else {
            buffer.copyOfRange(0, length)
        }
    }


    public fun GetMainSack() = sacks[0]

    public override fun equals(other: Any?): Boolean {
        return if (other is Character) {
            caracterName == other.caracterName
        } else {
            false
        }
    }

    public override fun hashCode(): Int = caracterName.hashCode()


  }

