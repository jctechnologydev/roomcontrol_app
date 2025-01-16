package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.ActuatorState
import pt.ipca.smartrooms.net.ApiResult

interface ActuatorsRepositoryInterface {
    suspend fun setActuatorState(idActuator: Int, actuatorState: ActuatorState): ApiResult<Unit>
}