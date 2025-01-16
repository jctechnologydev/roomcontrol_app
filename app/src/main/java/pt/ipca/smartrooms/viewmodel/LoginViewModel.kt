package pt.ipca.smartrooms.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.interfaces.AuthRepositoryInterface
import pt.ipca.smartrooms.model.Event
import pt.ipca.smartrooms.model.Result
import pt.ipca.smartrooms.model.SRError
import pt.ipca.smartrooms.model.User

class LoginViewModel(private val authRepository: AuthRepositoryInterface, savedStateHandle: SavedStateHandle) : ViewModel(){
    constructor(savedStateHandle: SavedStateHandle) : this(ServiceLocator.authRepository, savedStateHandle)

    private var _userLogged: MutableLiveData<User?> = MutableLiveData(null)
    val userLogged : LiveData<User?> = _userLogged

    private var _error : MutableLiveData<Event<SRError>?> = MutableLiveData(null)
    val error : LiveData<Event<SRError>?> = _error

    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    fun login(user: String, password: String){
        viewModelScope.launch {
            _isLoading.value = true
            val resp = authRepository.login(user, password)
            _isLoading.value = false
            when(resp){
                is Result.Failure -> _error.value = Event(resp.error)
                is Result.Success -> _userLogged.value = resp.data
            }
        }
    }
}