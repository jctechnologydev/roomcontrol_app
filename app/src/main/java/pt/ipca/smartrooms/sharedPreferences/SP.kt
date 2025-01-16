package pt.ipca.smartrooms.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import pt.ipca.smartrooms.interfaces.TokenSP

class SP(private var context: Context) : TokenSP{
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE)

    private val TOKEN_KEY = "token"
    override fun removeToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }

    override fun setToken(token: String?){
        if(token.isNullOrEmpty())
            return
        sharedPreferences.edit().putString(TOKEN_KEY, "Bearer $token").apply()
    }

    override fun getToken() = sharedPreferences.getString(TOKEN_KEY, null)
}