package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schema(
    @SerialName("element")
    val element: List<Element> = listOf(),
    @SerialName("_id")
    val id: String = "",
    @SerialName("import")
    val `import`: Import = Import(),
    @SerialName("_xmlns")
    val xmlns: String = "",
    @SerialName("_xmlns:msdata")
    val xmlnsMsdata: String = "",
    @SerialName("_xmlns:xsd")
    val xmlnsXsd: String = ""
)