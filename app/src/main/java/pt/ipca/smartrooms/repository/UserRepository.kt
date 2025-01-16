package pt.ipca.smartrooms.repository

import pt.ipca.smartrooms.interfaces.UserRepositoryInterface
import pt.ipca.smartrooms.model.User

class UserRepository : UserRepositoryInterface {
    private var user : User? = null

    override fun setUser(user: User) {
        this.user = user
    }

    override fun getUser(): User? = user
}