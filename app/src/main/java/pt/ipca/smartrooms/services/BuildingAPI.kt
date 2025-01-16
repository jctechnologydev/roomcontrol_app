package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.School
import pt.ipca.smartrooms.net.ApiResult
import retrofit2.http.GET

interface BuildingAPI {
    @GET("All")
    suspend fun getAll() : ApiResult<ArrayList<School>>
}