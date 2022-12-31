package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    @SerialName("element")
    val element: List<ElementX> = listOf(),
    @SerialName("_maxOccurs")
    val maxOccurs: String = ""
)