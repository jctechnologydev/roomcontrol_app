package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.DTO.ActuatorSetterDTO
import pt.ipca.smartrooms.net.ApiResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ActuatorsAPI {

    @PATCH("Change")
    suspend fun setActuator(@Body actuatorSetter: ActuatorSetterDTO) : ApiResult<Unit>
}