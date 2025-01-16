package pt.ipca.smartrooms.repository

import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.interfaces.ActuatorsRepositoryInterface
import pt.ipca.smartrooms.model.Actuator
import pt.ipca.smartrooms.model.ActuatorState
import pt.ipca.smartrooms.model.HardwareType
import pt.ipca.smartrooms.model.DTO.ActuatorSetterDTO
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.ActuatorsAPI
import retrofit2.Retrofit

class ActuatorsRepository(private val retrofitBuilder : Retrofit.Builder) :
    ActuatorsRepositoryInterface {

    private val webservice = retrofitBuilder
        .baseUrl("${BASE_URL}State/")
        .build()
        .create(ActuatorsAPI::class.java)

    override suspend fun setActuatorState(idActuator: Int, actuatorState: ActuatorState): ApiResult<Unit> {
        val actuatorSetter = ActuatorSetterDTO(idActuator, actuatorState.value)
        return webservice.setActuator(actuatorSetter)
    }
}