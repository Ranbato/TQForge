package tools.resourceextraction

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.math.min

fun main(args: Array<String>) {
    val dumpDir = Path.of("./src/test/resources/tmp")
    val tmp = Path.of(".").toFile()
    val obj = Json.decodeFromString<MSResources>(Files.readString(Path.of("./src/test/resources/Resources.json")))
    tmp.toString()
    if(!dumpDir.exists()) Files.createDirectory(dumpDir)
    obj.data.forEach { data ->
        val decoder = Base64.getDecoder()
        val decodedData = decoder.decode(data.value)
        val imageData = decodedData.copyOfRange(161,decodedData.lastIndex)
        val ext = when(String(imageData.sliceArray(0..3), Charsets.ISO_8859_1)) {

            0x89.toChar() + "PNG" -> ".png"
            String(byteArrayOf(0xFF.toByte(), 0xD8.toByte() , 0xFF.toByte() , 0xE0.toByte()),Charsets.ISO_8859_1) -> ".jpeg"

            else -> {
                System.out.println("Image ${data.name} \n[${String(decodedData.sliceArray(0..250.coerceAtMost(imageData.lastIndex)),Charsets.UTF_8)}]")

                println("Unknown type: ${String(imageData.sliceArray(0..3.coerceAtMost(imageData.lastIndex)),Charsets.UTF_8)}")
            ".unk"
        }
        }






        val currentFile = File(dumpDir.toFile(),data.name+ext)

//        System.out.println("Image ${data.name} \n[${String(imageData.sliceArray(0..250.coerceAtMost(imageData.lastIndex)),Charsets.UTF_8)}]")
//        System.out.println("Image ${data.name} \n[${imageData.sliceArray(0..250.coerceAtMost(imageData.lastIndex)).map { "%02X".format(it) }.joinToString(",")}]")
        Files.write(currentFile.toPath(),imageData.copyOfRange(0,imageData.lastIndex))


    }
}
