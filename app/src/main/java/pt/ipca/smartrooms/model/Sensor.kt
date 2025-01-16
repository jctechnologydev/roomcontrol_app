package pt.ipca.smartrooms.model

data class Sensor(
    var id: Int?,
    var name : String?,
    var value : String?,
    var hardwareType: HardwareType?,
    val state: State
) : Hardware(id, name, value, hardwareType, FunctionalityType.SENSOR)