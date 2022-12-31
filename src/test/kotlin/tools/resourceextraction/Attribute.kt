package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    @SerialName("_msdata:Ordinal")
    val msdataOrdinal: String = "",
    @SerialName("_name")
    val name: String = "",
    @SerialName("_ref")
    val ref: String = "",
    @SerialName("_type")
    val type: String = "",
    @SerialName("_use")
    val use: String = ""
)