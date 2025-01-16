package pt.ipca.smartrooms.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.interfaces.RoomRepositoryInterface
import pt.ipca.smartrooms.model.*
import pt.ipca.smartrooms.ui.maintenanceroom.MaintenanceRoomActivity


class MaintenanceRoomViewModel (private val roomRepository: RoomRepositoryInterface, savedStateHandle: SavedStateHandle) : ViewModel() {

    constructor(savedStateHandle: SavedStateHandle) : this(ServiceLocator.roomRepository, savedStateHandle)

    private val _maintenanceRooms : MutableLiveData<ArrayList<Room>> = MutableLiveData(null)
    val maintenanceRoom : LiveData<ArrayList<Room>> = _maintenanceRooms

    val school : LiveData<School> = savedStateHandle.getLiveData(MaintenanceRoomActivity.KEY_SCHOOL)

    private val _error : MutableLiveData<Event<SRError>?> = MutableLiveData(null)
    val error : LiveData<Event<SRError>?> = _error

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    fun getMaintenanceRooms(idSchool: Int){
        viewModelScope.launch {
            _loading.value = true
            val resp = roomRepository.getSchoolRooms(idSchool)
            _loading.value = false
            when(resp){
                is Result.Failure -> _error.value = Event(resp.error)
                is Result.Success -> _maintenanceRooms.value = resp.data ?: arrayListOf()
            }
        }
    }


}