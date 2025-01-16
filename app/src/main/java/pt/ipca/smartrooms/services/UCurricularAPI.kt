package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.net.ApiResult
import retrofit2.http.GET

interface UCurricularAPI {

    @GET("rooms")
    suspend fun getUcurricularRooms() : ApiResult<ArrayList<UCurricular>>
}