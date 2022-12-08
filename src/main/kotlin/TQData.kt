import java.io.File
import java.io.FileNotFoundException
import java.io.RandomAccessFile

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

    public fun readCString( reader: RandomAccessFile):String
    {
      val num = reader.readInt();
      val bytes = ByteArray(num)
      reader.read(bytes);
      return String(bytes,Charsets.ISO_8859_1)
    }

    public fun validateNextString( value:String,  reader:RandomAccessFile):Boolean
    {
      val str = readCString(reader);
      if (str.uppercase() == value.uppercase()) {
        return true
      }
      logger.debug{"Error reading file at position ${reader.filePointer -  str.length - 4L}.  Expecting $value.  Got $str"}
      return false;
    }


    public fun loadCharacterList():List<String>
    {
      characterNameList.clear()
      try
      {
        val directories = File(Config.getSaveLocation() + "\\SaveData\\Main").listFiles { file -> file.name[0] == '_' && file.isDirectory })
        if (directories == null || directories.isEmpty()) {
          return emptyList()
        }
        var tempList = MutableList(directories.size)
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

//          Logger.Log("Error: Unable to load character weaponSmith data: " + ex.ToString());
      return character;
    }

    public fun  loadCharacter(chrName:String):Character?
    {
      if (chrName == null)
        return null;

      val character1 = characterList.find { it.characterName == chrName }

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

