package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ElementXX(
    @SerialName("_minOccurs")
    val minOccurs: String = "",
    @SerialName("_msdata:Ordinal")
    val msdataOrdinal: String = "",
    @SerialName("_name")
    val name: String = "",
    @SerialName("_type")
    val type: String = ""
)