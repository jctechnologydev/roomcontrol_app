package pt.ipca.smartrooms.model

import com.google.gson.annotations.SerializedName

enum class UserType{
    @SerializedName("1")
    ALUNO,
    @SerializedName("2")
    PROFESSOR,
    @SerializedName("3")
    TECNICO,
    @SerializedName("4")
    ADMIN;
}
