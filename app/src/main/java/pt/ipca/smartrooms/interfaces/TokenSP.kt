package pt.ipca.smartrooms.interfaces

interface TokenSP {
    fun removeToken()
    fun setToken(token: String?)
    fun getToken() : String?
}