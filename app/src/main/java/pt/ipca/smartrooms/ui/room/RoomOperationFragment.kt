package pt.ipca.smartrooms.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.adapters.ActuatorAdapter
import pt.ipca.smartrooms.model.ActuatorState
import pt.ipca.smartrooms.model.HardwareType
import pt.ipca.smartrooms.viewmodel.RoomViewModel


class RoomOperationFragment : Fragment() {

    private val viewmodel by activityViewModels<RoomViewModel>()
    private var actuatorAdapter : ActuatorAdapter? = null
    private var emptyView : TextView? = null
    private var recyclerView : RecyclerView? = null
    private var progressBar : ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_room_operation, container, false)

        setAdapter()
        setViews(view)
        setObservers()

        return view
    }

    fun setViews(view: View){
        emptyView = view.findViewById(R.id.room_operation_empty)
        recyclerView = view.findViewById(R.id.room_operations_rv)
        recyclerView?.apply {
            adapter = actuatorAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        progressBar = view.findViewById(R.id.room_operation_pb)
    }

    fun setAdapter(){
        actuatorAdapter = ActuatorAdapter()
        actuatorAdapter?.clickListener = { actuator ->
            var newState : ActuatorState? = null
            when(actuator.hardwareType){
                HardwareType.LIGHT -> {
                    newState = when(actuator.state){
                        ActuatorState.ON -> ActuatorState.OFF
                        ActuatorState.OFF -> ActuatorState.ON
                        null -> ActuatorState.ON
                    }
                }
                null -> {}
                else -> {}
            }
            if(newState != null) {
                actuatorAdapter?.setAcutatorState(actuator.id!!, newState)
                viewmodel.setActuatorState(actuator.id!!, newState)
            }
        }
    }

    fun setObservers(){
        viewmodel.actuators.observe(viewLifecycleOwner){ listActuators ->
            val isLoadingVisible = listActuators.isNullOrEmpty()
            emptyView?.isVisible = isLoadingVisible
            recyclerView?.isVisible = !isLoadingVisible
            if(!isLoadingVisible){
                actuatorAdapter?.setActuators(listActuators)
            }
        }

        viewmodel.firstLoading.observe(viewLifecycleOwner){
            it?.let { firstLoading ->
                if(firstLoading){
                    emptyView?.isVisible = false
                    recyclerView?.isVisible = false

                }
                progressBar?.isVisible = firstLoading
            }
        }
    }
}