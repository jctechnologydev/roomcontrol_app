package pt.ipca.smartrooms.model.DTO

data class HardwareDataDTO(
    val hardware: HardwareDTO,
    val state: String,
    val value: String,
    val dataTypeName: String
)