package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComplexTypeX(
    @SerialName("attribute")
    val attribute: List<Attribute> = listOf(),
    @SerialName("sequence")
    val sequence: Sequence = Sequence()
)