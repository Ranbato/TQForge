import java.io.File
import java.util.Properties
import javax.swing.filechooser.FileSystemView

private val logger = mu.KotlinLogging.logger {}
  public object Config
  {
    private val  FILENAME = "Configurations.txt"
    public val  CHARACTERS_FOLDER = "\\SaveData\\Main"
    public val  CHARACTERS_SAVE_FILE = "Player.chr"
    private val  SEPARATOR = "="
    private val  COMMENT = "#"
    public val  KEY_GAME_SAVE_LOCATION = "GameSaveLocation"
    public val  KEY_GAME_INSTALL_LOCATION = "GameInstallLocation"
    public val  KEY_DETAIL_LOGS = "detailLogs"
    public val  KEY_HELP_SHOWN = "helpShown"
    public val itemListFiles = arrayOf(

      "10_club.txt",
      "11_spear.txt",
      "12_staff.txt",
      "13_throwing.txt",
      "14_shield.txt",
      "15_ring.txt",
      "16_amulet.txt",
      "17_relic.txt",
      "18_charm.txt",
      "19_artifact.txt",
      "1_prefixes.txt",
      "20_scroll.txt",
      "21_artifact_formula.txt",
      "22_lootRandomizerTable.txt",
      "23_parchments.txt",
      "24_questItems.txt",
      "2_suffixes.txt",
      "3_head.txt",
      "4_chest.txt",
      "5_arms.txt",
      "6_legs.txt",
      "7_axe.txt",
      "8_bow.txt",
      "9_sword.txt"
    )
    public val  prefixFile = "ItemwisePrefix.txt"
    public val  suffixFile = "ItemwiseSuffix.txt"
    public val  miscFile = "misc.txt"
    public var areResourcesLoaded = false
    private var isInitialized = true

    public fun getSaveLocation():String = properties["GameSaveLocation"] as String
    public fun getInstallLocation():String = properties["GameInstallLocation"] as String
    public fun getDetailLogs():Boolean = properties["detailLogs"] as Boolean
    public fun getHelpShown():Boolean = properties["helpShown"] as Boolean

    private val properties = mapOf<String,String>(
      "GameSaveLocation" to FileSystemView.getFileSystemView().defaultDirectory.path + "\\My Games\\Titan Quest - Immortal Throne",
             "GameInstallLocation" to "",
              "detailLogs" to false.toString(),
              "helpShown" to false.toString()
    ).toProperties()


    public fun saveConfig():Unit
    {
      logger.info("saving Config")
      saveToFile()
    }

    private fun loadFromFile():Unit
    {
      val file = File(FILENAME)
        if (file.exists())
        {
          val prop = Properties()
          properties.load(file.reader())
        }
        else
        {
//          int num = (int) MessageBox.Show("Configuration.txt not found in local program folder.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
          logger.warn("Configuration.txt not found in local program folder.");
        }
      }

    private fun saveToFile():Unit
    {
      val file = File(FILENAME)
      properties.store(file.writer(),"Delete this file to recreate with defaults")
//        Logger.Log("Error writing configuration to Configuration.txt.\n\n" + ex.ToString());
      }


    private fun setProperty(name:String,  value:String):Unit
    {

      properties[name] = value
    }

    public fun getSaveFile( characterName:String):String = getSaveLocation() + "\\SaveData\\Main\\_$characterName\\Player.chr";
  }

