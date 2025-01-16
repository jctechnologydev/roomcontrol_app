package pt.ipca.smartrooms.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.KEY_ROOM
import pt.ipca.smartrooms.KEY_USERTYPE
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.interfaces.ActuatorsRepositoryInterface
import pt.ipca.smartrooms.interfaces.HardwareRepositoryInterface
import pt.ipca.smartrooms.model.*
import pt.ipca.smartrooms.model.DTO.HardwareDTO
import pt.ipca.smartrooms.model.DTO.HardwareDataDTO

open class RoomViewModel(private val hardwareRepository: HardwareRepositoryInterface,
                         private val actuatorsRepositoryInterface: ActuatorsRepositoryInterface,
                         savedStateHandle: SavedStateHandle) : ViewModel() {

    constructor(savedStateHandle: SavedStateHandle) : this(
        ServiceLocator.hardwareRepository,
        ServiceLocator.actuatorsRepository,
        savedStateHandle)

    val room : LiveData<Room> = savedStateHandle.getLiveData<Room>(KEY_ROOM)
    val userType : UserType = ServiceLocator.userRepository.getUser()?.userType ?: throw IllegalArgumentException("Invalid usertype")

    private val _sensors : MutableLiveData<ArrayList<Sensor>> = MutableLiveData(null)
    val sensors : LiveData<ArrayList<Sensor>>
        get() = _sensors

    private val _actuators : MutableLiveData<ArrayList<Actuator>> = MutableLiveData(null)
    val actuators : LiveData<ArrayList<Actuator>>
        get() = _actuators

    private var _error : MutableLiveData<Event<SRError>?> = MutableLiveData(null)
    val error : LiveData<Event<SRError>?> = _error

    private var _firstLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    val firstLoading : LiveData<Boolean> = _firstLoading

    init {
        _firstLoading.value = true
        getHardware()
    }

    private fun getHardware(){
        viewModelScope.launch {
            while (this.isActive){
                requestHardware()
                delay(5000)
            }
        }
    }

    private var jobRequest : Job? = null
    private var isChangingState : Boolean = false

    private suspend fun requestHardware(){
        jobRequest = viewModelScope.launch {
            val id = room.value?.id
            if(id != null && !isChangingState) {
                val resp = hardwareRepository.getHardware(room.value!!.id!!)
                _firstLoading.value = false
                when(resp){
                    is Result.Failure -> _error.value = Event(resp.error)
                    is Result.Success -> handleListHardware(resp.data)
                }
            }
        }
    }

    private fun handleListHardware(listHardware: List<HardwareDataDTO>){
        val listSensor = arrayListOf<Sensor>()
        val listActuator = arrayListOf<Actuator>()
        listHardware.forEach { hardwareData ->
            val functionalityType = FunctionalityType.forValue(hardwareData.hardware.funcionalityType?.name ?: "") ?: return@forEach
            val hardwareType = HardwareType.forValue(hardwareData.hardware.hardwareType?.name ?: "") ?: return@forEach
            when(functionalityType){
                FunctionalityType.SENSOR ->{
                    val sensor = Sensor(hardwareData.hardware.id, hardwareData.hardware.name, hardwareData.value, hardwareType, State.GOOD)
                    listSensor.add(sensor)
                }
                FunctionalityType.ACTUATOR -> {
                    val actuator = Actuator(hardwareData.hardware.id, hardwareData.hardware.name, hardwareData.value, hardwareType, if(hardwareData.value == "1") ActuatorState.ON else ActuatorState.OFF)
                    listActuator.add(actuator)
                }
            }
        }
        _sensors.value = listSensor
        _actuators.value = listActuator
    }

    fun setActuatorState(idHardware: Int, state: ActuatorState){
        viewModelScope.launch {
            jobRequest?.cancel()
            isChangingState = true
            actuatorsRepositoryInterface.setActuatorState(idHardware, state)
            delay(5000)
            isChangingState = false
        }
    }

}