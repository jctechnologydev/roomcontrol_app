package pt.ipca.smartrooms

import Resource
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import pt.ipca.smartrooms.databinding.ActivityLoginBinding
import pt.ipca.smartrooms.model.User
import pt.ipca.smartrooms.model.UserType
import pt.ipca.smartrooms.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewmodel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailText.text.toString()
            email.ifEmpty {
                setErrorEmail(getString(R.string.invalid_email))
                return@setOnClickListener
            }

            val password = binding.passwordText.text.toString()
            password.ifEmpty {
                setErrorPassword(getString(R.string.invalid_password))
                return@setOnClickListener
            }

            viewmodel.login(email, password)
        }


        viewmodel.isLoading.observe(this){
            it?.let { isLoading ->
                isLoginLoading(isLoading)
            }
        }

        viewmodel.error.observe(this){ event->
            event?.getContentIfNotHandled()?.let { error ->
                alertError(error.message)
            }
        }

        viewmodel.userLogged.observe(this){ user ->
            if(user == null)
                ServiceLocator.tokenSP.removeToken()
            else {
                loginSuccess(user)
            }
        }

        binding.emailText.addTextChangedListener {
            binding.emailText.error = null
        }

        binding.passwordText.addTextChangedListener {
            binding.passwordText.error = null
        }
    }

    private fun isLoginLoading(isLoading : Boolean){
        binding.buttonLogin.visibility = if(isLoading) View.INVISIBLE else View.VISIBLE
        binding.pbLogin.isVisible = isLoading
    }

    private fun setErrorEmail(message: String){
        binding.emailText.error = message
    }

    private fun setErrorPassword(message: String){
        binding.passwordText.error = message
    }

    private fun alertError(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun loginSuccess(user: User){
        ServiceLocator.tokenSP.setToken(user.token)
        ServiceLocator.userRepository.setUser(user)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(KEY_USER, user)
        startActivity(intent)
    }

}
