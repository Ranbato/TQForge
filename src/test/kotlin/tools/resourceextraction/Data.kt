package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("_mimetype")
    val mimetype: String = "",
    @SerialName("_name")
    val name: String = "",
    @SerialName("value")
    val value: String = ""
)