package pt.ipca.smartrooms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.Sensor
import pt.ipca.smartrooms.model.State.*

class SensorsAdapter(private val context: Context) : RecyclerView.Adapter<SensorsAdapter.ViewHolder>() {

    private var hardwares : ArrayList<Sensor> = arrayListOf()

    fun setSensors(list: ArrayList<Sensor>){
        hardwares = list
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val textViewSensorLabel = view.findViewById<TextView>(R.id.row_room_name)
        val textViewSensorValue = view.findViewById<TextView>(R.id.row_sensor_value_tv)
        val viewStateDataSensor = view.findViewById<View>(R.id.row_sensor_state_data_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_sensor_data, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        hardwares[position].let { sensor ->
            holder.textViewSensorValue.text = sensor.value?.toDouble()?.toInt().toString()
            holder.textViewSensorLabel.text = sensor.name
            sensor.state.let { state ->
                val background = when(state){
                    GOOD -> R.drawable.circle_state_green
                    MEDIUM -> R.drawable.circle_state_middle
                    BAD -> R.drawable.circle_state_red
                }
                holder.viewStateDataSensor.background = ContextCompat.getDrawable(context, background)
            }
        }
    }

    override fun getItemCount(): Int = hardwares.size
}