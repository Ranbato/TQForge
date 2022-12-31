package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sequence(
    @SerialName("element")
    val element: List<ElementXX> = listOf()
)