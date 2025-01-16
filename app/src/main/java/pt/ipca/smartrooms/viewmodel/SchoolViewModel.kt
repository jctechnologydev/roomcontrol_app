package pt.ipca.smartrooms.viewmodel;
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.ServiceLocator;
import pt.ipca.smartrooms.interfaces.SchoolRepositoryInterface;
import pt.ipca.smartrooms.model.Event
import pt.ipca.smartrooms.model.Result
import pt.ipca.smartrooms.model.SRError
import pt.ipca.smartrooms.model.School;

class SchoolViewModel(private val schoolRepositoryInterface:SchoolRepositoryInterface, savedStateHandle: SavedStateHandle) : ViewModel() {

    constructor(savedStateHandle: SavedStateHandle) : this(ServiceLocator.schoolRepository, savedStateHandle)

    private var _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    private var _error : MutableLiveData<Event<SRError>?> = MutableLiveData(null)
    val error : LiveData<Event<SRError>?> = _error

    private val _schools : MutableLiveData<ArrayList<School>> = MutableLiveData(null)
    val school : LiveData<ArrayList<School>> = _schools

    init {
        getSchools()
    }

    private fun getSchools(){
        viewModelScope.launch {
            _loading.value  = true
            val resp = schoolRepositoryInterface.getSchools()
            _loading.value = false
            when(resp){
                is Result.Failure -> _error.value = Event(resp.error)
                is Result.Success -> _schools.value = resp.data ?: arrayListOf()
            }
        }
    }


}