package pt.ipca.smartrooms.services

import pt.ipca.smartrooms.model.DTO.UserLogin
import pt.ipca.smartrooms.model.User
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.repository.AuthRepository
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("Login")
    suspend fun login(@Body userLogin: UserLogin) : ApiResult<User>
}