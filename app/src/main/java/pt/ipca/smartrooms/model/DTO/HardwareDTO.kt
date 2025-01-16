package pt.ipca.smartrooms.model.DTO

open class HardwareDTO(
    open val id: Int?,
    open val name: String?,
    val hardwareType: HardwareTypeDTO?,
    val funcionalityType: FunctionalityTypeDTO?
)