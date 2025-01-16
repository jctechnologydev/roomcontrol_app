package pt.ipca.smartrooms.model.DTO

data class HardwareAddDTO(
    val idRoom : Int,
    val idHardwareType : Int,
    val idFuncionality: Int,
    val name: String,
    val maxValue: Int,
    val minValue: Int
)