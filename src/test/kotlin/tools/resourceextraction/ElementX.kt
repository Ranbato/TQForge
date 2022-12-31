package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ElementX(
    @SerialName("complexType")
    val complexType: ComplexTypeX = ComplexTypeX(),
    @SerialName("_name")
    val name: String = ""
)