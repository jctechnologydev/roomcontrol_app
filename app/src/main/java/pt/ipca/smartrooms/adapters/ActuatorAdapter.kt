package pt.ipca.smartrooms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.Actuator
import pt.ipca.smartrooms.model.ActuatorState
import pt.ipca.smartrooms.model.HardwareType

class ActuatorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listActuator : ArrayList<Actuator> = arrayListOf()
    var clickListener : ((actuator: Actuator) -> Unit)? = null


    override fun getItemViewType(position: Int): Int {
        return when(listActuator[position].hardwareType){
            HardwareType.LIGHT -> HardwareType.LIGHT.ordinal
            else -> throw IllegalArgumentException("invalid type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(HardwareType.values()[viewType]){
            HardwareType.LIGHT -> LightActuatorHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_light, parent, false))
            else -> throw IllegalArgumentException("invalid type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LightActuatorHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int = listActuator.size


    inner class LightActuatorHolder(private val view: View) : RecyclerView.ViewHolder(view){
        private val card = view.findViewById<View>(R.id.row_light_card)
        private val viewState = view.findViewById<View>(R.id.row_light_view_light_state)
        private val tvName = view.findViewById<TextView>(R.id.row_light_actuator_name)
        private val tvState = view.findViewById<TextView>(R.id.row_light_tv_state)
        fun bind(position: Int){
            listActuator[position].let { actuator ->
                tvName.text = actuator.name
                val stateViewId : Int
                when(actuator.hardwareType){
                    HardwareType.LIGHT ->{
                        when(actuator.state){
                            ActuatorState.ON ->{
                                stateViewId = R.drawable.light_on
                                tvState.text = view.context.getString(R.string.turned_on)
                            }
                            ActuatorState.OFF -> {
                                stateViewId = R.drawable.light_off
                                tvState.text = view.context.getString(R.string.turned_off)
                            }
                            null -> throw IllegalArgumentException("unexpected state for this actuator")
                        }
                    }
                    null -> throw IllegalArgumentException("unexpected actuator type")
                    else -> { stateViewId = 0}
                }

                viewState.background = ContextCompat.getDrawable(view.context, stateViewId)
                card.setOnClickListener {
                    clickListener?.let {
                        it(actuator)
                    }
                }
            }
        }
    }

    fun setAcutatorState(id: Int, state: ActuatorState){
        listActuator.find { it.id == id }?.let {
            it.state = state
        }
        notifyDataSetChanged()
    }

    fun setActuators(actuators: ArrayList<Actuator>){
        listActuator = actuators
        this.notifyDataSetChanged()
    }
}