package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Import(
    @SerialName("_namespace")
    val namespace: String = ""
)