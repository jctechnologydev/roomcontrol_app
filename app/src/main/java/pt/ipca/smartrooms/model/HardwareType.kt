package pt.ipca.smartrooms.model

enum class HardwareType(val value: String) {
    LIGHT("Luminosidade"), TEMPERATURE("Temperatura"), HUMIDITY("Humidade"), NOISE("Ruído");

    companion object{
        fun forValue(value: String) = values().find { it.value == value }
    }
}