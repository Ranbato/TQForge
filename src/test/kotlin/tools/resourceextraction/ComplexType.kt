package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComplexType(
    @SerialName("choice")
    val choice: Choice = Choice()
)