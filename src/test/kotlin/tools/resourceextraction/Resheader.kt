package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resheader(
    @SerialName("_name")
    val name: String = "",
    @SerialName("value")
    val value: String = ""
)