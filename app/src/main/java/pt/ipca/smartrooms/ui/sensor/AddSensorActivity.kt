package pt.ipca.smartrooms.ui.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.databinding.ActivityAddSensorBinding
import pt.ipca.smartrooms.model.DTO.HardwareAddDTO
import pt.ipca.smartrooms.model.HardwareType

class AddSensorActivity : AppCompatActivity() {


    private lateinit var binding : ActivityAddSensorBinding

    override fun onResume() {
        super.onResume()
        val hardwareType = resources.getStringArray(R.array.hardware_type)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_type_hardware, hardwareType)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Adicionar Sensor"

        binding = ActivityAddSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {

            val sensorName = binding.textInputName.text.toString()
            val minValue = binding.textInputMin.text.toString().toInt()
            val maxValue = binding.textInputMax.text.toString().toInt()
            val hardwareTypeString = binding.autoCompleteTextView.text.toString()
            val hardwareType = HardwareType.valueOf(hardwareTypeString)
            //val sensorDTO = HardwareAddDTO(null,sensorName,null, Har,null,minValue,maxValue)

            finish()
        }
    }
}