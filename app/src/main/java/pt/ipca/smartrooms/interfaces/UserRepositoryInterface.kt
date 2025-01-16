package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.User

interface UserRepositoryInterface {
    fun setUser(user: User)
    fun getUser() : User?
}