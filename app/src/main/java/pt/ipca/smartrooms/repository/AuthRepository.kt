package pt.ipca.smartrooms.repository

import kotlinx.coroutines.delay
import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.interfaces.AuthRepositoryInterface
import pt.ipca.smartrooms.model.DTO.UserLogin
import pt.ipca.smartrooms.model.SRError
import pt.ipca.smartrooms.model.User
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.AuthAPI
import retrofit2.Retrofit

class AuthRepository(private val retrofitBuilder : Retrofit.Builder) : AuthRepositoryInterface {

    private val webservice = retrofitBuilder
        .baseUrl("${BASE_URL}Auth/")
        .build()
        .create(AuthAPI::class.java)

    override suspend fun login(email: String, password: String): ApiResult<User> {
        val user = webservice.login(UserLogin(email, password))
        return user
    }
}