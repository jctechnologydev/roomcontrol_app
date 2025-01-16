package pt.ipca.smartrooms.model

data class Actuator(
    var id: Int?,
    var name : String?,
    var value : String?,
    val hardwareType: HardwareType,
    var state: ActuatorState
) : Hardware(id, name, value, hardwareType, FunctionalityType.ACTUATOR)