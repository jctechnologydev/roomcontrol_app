package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.*
import pt.ipca.smartrooms.net.ApiResult

interface RoomRepositoryInterface {
    suspend fun getRooms() : ApiResult<ArrayList<Room>>
    suspend fun getSchoolRooms(idSchool: Int) : ApiResult<ArrayList<Room>>
}