package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MSResources(
    @SerialName("data")
    val `data`: List<Data> = listOf(),
    @SerialName("resheader")
    val resheader: List<Resheader> = listOf(),
    @SerialName("schema")
    val schema: Schema = Schema()
)