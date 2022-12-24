import java.io.*
import java.nio.ByteBuffer

private val logger = mu.KotlinLogging.logger {}
public object TQData
  {
    public var selectedCharacter:Character? = null
    public  val  characterNameList = mutableListOf<String>()
    public  val characterList = mutableListOf<Character>();



    public fun isWeaponOrArmor( type:ItemType):Boolean = ItemType.Head == type || ItemType.Chest == type || ItemType.Arm == type || ItemType.Leg == type || ItemType.Sword == type || ItemType.Axe == type || ItemType.Shield == type || ItemType.Mace == type || ItemType.Staff == type || ItemType.Spear == type || ItemType.Bow == type || ItemType.Thrown == type

    public fun writeCString(writer: RandomAccessFile, value:String):Unit
    {
      val bytes = value.toByteArray(Charsets.ISO_8859_1)
      writer.write(bytes)
    }

    public fun writeCString(writer: OutputStream, value:String):Unit
    {
      val bytes = value.toByteArray(Charsets.ISO_8859_1)
      writer.write(bytes)
    }

    public fun readCString( reader: ByteBuffer, offset:Int):String
    {
      val num = reader.getInt(offset)
      val bytes = ByteArray(num)
      reader.get(bytes);
      return String(bytes,Charsets.ISO_8859_1)
    }
    public fun readCString( array: ByteArray):String
    {
      return readCString(ByteArrayInputStream(array))
    }

    public fun readCString( reader: InputStream):String
    {
      val num = DataInputStream(reader).readInt()
      val bytes = ByteArray(num)
      reader.read(bytes);
      return String(bytes,Charsets.ISO_8859_1)


    }

//    private fun getInt(buffer: ByteArray, offset:Int = 0):Int {
//      return buffer[offset+0].toInt() and 0xFF or
//              (buffer[offset+1].toInt() and 0xFF shl 8) or
//              (buffer[offset+2].toInt() and 0xFF shl 16) or
//              (buffer[offset+3].toInt() and 0xFF shl 24)
//    }
//    public fun validateNextString( value:String,  reader:B):Boolean
//    {
//      val str = readCString(reader);
//      if (str.uppercase() == value.uppercase()) {
//        return true
//      }
//      logger.debug{"Error reading file at position ${reader.filePointer -  str.length - 4L}.  Expecting $value.  Got $str"}
//      return false;
//    }

    public fun validateNextString( value:String,  reader: DataInputStream):Boolean
    {
      val str = readCString(reader);
      if (str.uppercase() == value.uppercase()) {
        return true
      }
      logger.debug{"Error reading data at from buffer.  Expecting $value.  Got $str"}
      return false;
    }

    public fun loadCharacterList():List<String>
    {
      characterNameList.clear()
      try
      {
        val directories = File(Config.getSaveLocation() + "\\SaveData\\Main").listFiles { file -> file.name[0] == '_' && file.isDirectory }
        if (directories == null || directories.isEmpty()) {
          return emptyList()
        }
        var tempList = mutableListOf<String>()
        directories.forEach {
          tempList.add(it.name.substring(1))
          logger.debug{"Character found: ${it.name.substring(1)}"}
        }
        characterNameList.addAll(tempList.sorted())
        return characterNameList;
      }
      catch (ex:FileNotFoundException)
      {
        return emptyList()
      }
    }

    public fun  loadPlayerData(playerName:String):Character
    {

        val saveFile = Config.getSaveFile(playerName);
        val character = Character(playerName, saveFile);

        character.loadFile();

         // logger.error{"Error: Unable to load character $playerName data: ${ex.ToString()}"}
      return character;
    }

    public fun  loadCharacter(chrName:String):Character?
    {
      if (chrName == null)
        return null;

      val character1 = characterList.find { it.caracterName == chrName }

      if (character1 != null) {
        return character1;
      } else {
        val newChar = loadPlayerData(chrName)
        characterList.add(newChar)
        return newChar
      }
    }

    public fun  NormalizeRecordPath( recordId:String):String = recordId.uppercase().replace('/', '\\')

    public fun  getItemType( type:String):ItemType
    {
      return when (type.lowercase())
      {
        "amulet" ->  ItemType.Amulet;
        "arm" ->  ItemType.Arm;
        "armor" ->  ItemType.Armor;
        "arms" ->  ItemType.Arm;
        "artifact" ->  ItemType.Artifact;
        "axe" ->  ItemType.Axe;
        "bow" ->  ItemType.Bow;
        "charm" ->  ItemType.Charm;
        "chest" ->  ItemType.Chest;
        "club" ->  ItemType.Mace;
        "formula" ->  ItemType.Formula;
        "head" ->  ItemType.Head;
        "leg" ->  ItemType.Leg;
        "legs" ->  ItemType.Leg;
        "mace" ->  ItemType.Mace;
        "misc" ->  ItemType.Misc;
        "parchment" ->  ItemType.Parchment;
        "questitem" ->  ItemType.Quest;
        "relic" ->  ItemType.Relic;
        "ring" ->  ItemType.Ring;
        "scroll" ->  ItemType.Scroll;
        "shield" ->  ItemType.Shield;
        "spear" ->  ItemType.Spear;
        "staff" ->  ItemType.Staff;
        "sword" ->  ItemType.Sword;
        "thrown" ->  ItemType.Thrown;
        "torso" ->  ItemType.Chest;
        "weapon" ->  ItemType.Weapon;
        else -> ItemType.Weapon;
      }
    }
  }

