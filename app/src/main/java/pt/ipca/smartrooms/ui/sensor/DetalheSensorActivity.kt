package pt.ipca.smartrooms.ui.sensor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import pt.ipca.smartrooms.databinding.ActivityDetalheSensorBinding


class DetalheSensorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetalheSensorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalheSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val graph = binding.graphTemperature
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 7.0),
                DataPoint(6.0, 8.0),
                DataPoint(7.0, 4.0),
                DataPoint(8.0, 5.0),
                DataPoint(9.0, 6.0),
                DataPoint(10.0, 9.0),
                DataPoint(11.0, 2.0),
                DataPoint(12.0, 1.0)
            )
        )
        graph.addSeries(series)
        /*
        val sensors: Sensor<>
        val stateRoom = binding.viewStateRoom
        stateRoom.
        viewmodel.getSensor()
        */

    }
}