package ui

import Config
import Database
import Item
import TQData
import TQData.loadCharacterList
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp


private val logger = mu.KotlinLogging.logger {}

object MainForm {

    var character: String? = null

    @Composable
    public fun createForm() {
        val listState = rememberLazyListState()
        Box {
            loadResources()
            TQData.loadCharacterList()
            TQData.loadPlayerData("MarkMule")
            val character = TQData.loadCharacter("MarkMule")

            LazyColumn ( state = listState){
//                items<String>(TQData.characterNameList) {
//                    Row {
//                        Text("Character:")
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(it)
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Button(
//                            onClick = {
////                        val j = JFileChooser(Config.getSaveLocation(),)
////                        j.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY;
////                        val res = j.showDialog(null, "Select")
//
//                            }) {
//                            Text("Select")
//                        }
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                }
//                Row {
//                    Text("Sack Items")
//                }
                items<Item>(character?.GetMainSack()?.getItems()?: emptyList()) { itm ->

                    addItem(itm)
                }
                character.toString()

            }
        }
    }
@Composable
    fun addItem(itm: Item) {
        val image = itm.itemBitmap!!
        Image(
            bitmap = image.asComposeImageBitmap(),
            modifier = Modifier.size((image.width+1).dp, (image.height+1).dp),
            contentDescription = itm.toString()
//                    onClick


//            button.FlatAppearance.MouseOverBackColor = ColorTranslator.FromHtml("#1a1d9d")
//            button.FlatAppearance.BorderSize = 0
//            button.MouseDown += MouseEventHandler(this.btn_MouseClick)
//            val toolTip = ToolTip()
//            this.imagePanel.Controls.Add(button as Control)
//            toolTip.ForeColor = Color.Green
//            toolTip.BackColor = Color.Black
//            toolTip.SetToolTip(button as Control, itm.ToString(false, false))
    )
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