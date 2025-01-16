package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.Room
import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.net.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomAPI {

    @GET("All")
    suspend fun getAllRooms() : ApiResult<ArrayList<Room>>

    @GET("School/{id}")
    suspend fun getRoomsFromSchool(@Path("id") idSchool: Int) : ApiResult<ArrayList<Room>>
}