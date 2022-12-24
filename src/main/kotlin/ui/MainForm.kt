package ui

import Config
import Database
import TQData
import TQData.loadCharacterList
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


private val logger = mu.KotlinLogging.logger {}

object MainForm {

    var character: String? = null

    @Composable
    public fun createForm() {
        Box {
            TQData.loadCharacterList()
            Column {
                TQData.characterNameList.forEach {
                    Row {
                        Text("Character:")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(it)
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
//                        val j = JFileChooser(Config.getSaveLocation(),)
//                        j.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;
//                        val res = j.showDialog(null, "Select")

                            }) {
                            Text("Select")
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            TQData.loadPlayerData("MarkMule")
            val character = TQData.loadCharacter("MarkMule")
            loadResources()
            character.toString()

        }

    }

    private fun loadResources() {
        Database.AutoDetectLanguage = true
        Database.TQLanguage = "en"
        try {
            Database.LoadDBFile()
            Database.LoadAllItems()
            loadCharacterList()
            Config.areResourcesLoaded = true
        } catch (ex: Exception) {
            logger.error("Error loading resources $ex" )

        }
    }
}