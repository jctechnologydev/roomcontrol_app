package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.DTO.HardwareDataDTO
import pt.ipca.smartrooms.net.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HardwareAPI {

    @GET("Room/{id}")
    suspend fun getHardwareFromRoom(@Path("id") idRoom : Int) : ApiResult<List<HardwareDataDTO>>
}