package tools.resourceextraction


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Element(
    @SerialName("complexType")
    val complexType: ComplexType = ComplexType(),
    @SerialName("_msdata:IsDataSet")
    val msdataIsDataSet: String = "",
    @SerialName("_name")
    val name: String = ""
)