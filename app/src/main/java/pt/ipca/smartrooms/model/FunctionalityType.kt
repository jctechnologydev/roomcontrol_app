package pt.ipca.smartrooms.model

enum class FunctionalityType(val value: String) {
    SENSOR("Sensor"), ACTUATOR("Actuador");

    companion object{
        fun forValue(value: String) = values().find { it.value == value }
    }
}