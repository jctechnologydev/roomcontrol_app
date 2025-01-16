package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.User
import pt.ipca.smartrooms.net.ApiResult

interface AuthRepositoryInterface {
    suspend fun login(email: String, password: String) : ApiResult<User>
}