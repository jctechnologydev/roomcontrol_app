package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.DTO.HardwareDataDTO
import pt.ipca.smartrooms.net.ApiResult

interface HardwareRepositoryInterface {
    suspend fun getHardware(idRoom : Int): ApiResult<List<HardwareDataDTO>>
}