package tools.resourceextraction

import org.jetbrains.skiko.toBufferedImage
import utils.QuickDrawPanel
import java.io.File
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun main(args: Array<String>) {

    val file = File("./src/test/resources/relicobject01bmp.tex")
    try {
    FileChannel.open(file.toPath()).use { it ->
        val rawData = it.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
        rawData.order(ByteOrder.LITTLE_ENDIAN)
        val data = ByteArray(file.length().toInt())
        rawData.get(0,data,0,data.size)
//    val        data = file.readBytes()

            //https://github.com/dolda2000/haven-client/blob/0fcceaf00da523576482cf97625d5a0eef8dfaae/src/haven/Resource.java#L967
            //https://github.com/RaynsAS/Custom-Salem/tree/master/src/haven
            //bitmap2 = BitmapCode.LoadFromTexMemory(data, 0, data.Length);


        val bitmap = BitmapCode.LoadFromTexMemory(data,0,data.size)
            val f =  JFrame("Test Frame")
        f.setSize(300,300)
        f.setDefaultCloseOperation(EXIT_ON_CLOSE)
        val c = f.contentPane
        val quickDrawPanel = QuickDrawPanel()
        f.add( JScrollPane(quickDrawPanel), "Center")
        ImageIO.write(bitmap?.toBufferedImage(),"bmp",File("./src/test/resources/relicobject01bmp.bmp"))
        quickDrawPanel.setImage(bitmap?.toBufferedImage());

        f.isVisible = true


        }


        } catch (ex2: Exception) {
            print("Error loading bitmap for $file")

    }
}
