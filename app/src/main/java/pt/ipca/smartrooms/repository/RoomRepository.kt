package pt.ipca.smartrooms.repository

import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.interfaces.RoomRepositoryInterface
import pt.ipca.smartrooms.model.Room
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.RoomAPI
import retrofit2.Retrofit.Builder

class RoomRepository(private val retrofitBuilder: Builder) : RoomRepositoryInterface{

    val webservice = retrofitBuilder
        .baseUrl("${BASE_URL}Room/")
        .build()
        .create(RoomAPI::class.java)

    override suspend fun getRooms(): ApiResult<ArrayList<Room>> {
        return webservice.getAllRooms()
    }

    override suspend fun getSchoolRooms(idSchool: Int): ApiResult<ArrayList<Room>> {
        return webservice.getRoomsFromSchool(idSchool)
    }

}