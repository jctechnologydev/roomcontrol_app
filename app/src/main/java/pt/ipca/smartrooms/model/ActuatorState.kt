package pt.ipca.smartrooms.model

import com.google.gson.annotations.SerializedName

enum class ActuatorState(val value: Int){
    @SerializedName("Ligado")
    ON(5),
    @SerializedName("Desligado")
    OFF(6)
}