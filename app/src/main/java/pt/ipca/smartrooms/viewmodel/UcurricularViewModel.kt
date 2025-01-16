package pt.ipca.smartrooms.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.KEY_USERTYPE
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.interfaces.UcurricularRepositoryInterface
import pt.ipca.smartrooms.model.Event
import pt.ipca.smartrooms.model.Result
import pt.ipca.smartrooms.model.SRError
import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.model.UserType

class UcurricularViewModel(
    private val ucurricularRepositoryInterface: UcurricularRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    constructor(savedStateHandle: SavedStateHandle) : this(
        ServiceLocator.ucurricularRepository,
        savedStateHandle
    )

    val userType: UserType = savedStateHandle.get<UserType>(KEY_USERTYPE)
        ?: throw IllegalStateException("Invalid usertype")

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(null)
    val loading : LiveData<Boolean> = _loading

    private val _ucurriculars: MutableLiveData<ArrayList<UCurricular>> = MutableLiveData(null)
    val ucurricular: LiveData<ArrayList<UCurricular>>
        get() = _ucurriculars

    private var _error : MutableLiveData<Event<SRError>?> = MutableLiveData(null)
    val error : LiveData<Event<SRError>?> = _error

    init {
        getUcurriculars()
    }

    private fun getUcurriculars() {
        viewModelScope.launch {
            _loading.value = true
            val resp = ucurricularRepositoryInterface.getUcurriculars()
            when(resp){
                is Result.Failure -> _error.value = Event(resp.error)
                is Result.Success -> _ucurriculars.value = resp.data ?: arrayListOf()
            }
            _loading.value = false
        }
    }


}