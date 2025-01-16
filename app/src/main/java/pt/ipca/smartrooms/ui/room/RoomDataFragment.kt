package pt.ipca.smartrooms.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.adapters.SensorsAdapter
import pt.ipca.smartrooms.map.GoogleMap
import pt.ipca.smartrooms.viewmodel.RoomViewModel

class RoomDataFragment : Fragment() {

    private val viewmodel by activityViewModels<RoomViewModel>()
    private lateinit var map : GoogleMap
    private var recyclerView : RecyclerView? = null
    private var emptyView : TextView? = null
    private var progressBar : ProgressBar? = null
    private var recyclerViewAdapter : SensorsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_room_data, container, false)
        setViews(view)
        startObservables()

        return view
    }
    private fun setViews(view: View){
        progressBar = view.findViewById(R.id.room_data_pb)
        emptyView = view.findViewById(R.id.room_data_empty_tv)
        recyclerView = view.findViewById(R.id.room_data_rv)
        recyclerViewAdapter = SensorsAdapter(requireContext())
        recyclerView?.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setMap()
    }

    private fun setMap(){
        map = GoogleMap(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.room_data_map) as? SupportMapFragment
        mapFragment?.getMapAsync(map)
    }

    private fun startObservables(){
        viewmodel.firstLoading.observe(viewLifecycleOwner){
            it?.let { firstLoading ->
                if(firstLoading){
                    recyclerView?.visibility = View.INVISIBLE
                    emptyView?.visibility = View.GONE
                    progressBar?.visibility = View.VISIBLE
                } else {
                    progressBar?.visibility = View.GONE
                }
            }
        }

        viewmodel.room.observe(viewLifecycleOwner){
            it?.let { room ->
                map.setRooms(listOf(room))
            }
        }

        viewmodel.sensors.observe(viewLifecycleOwner) { listSensors ->
            if(viewmodel.firstLoading.value == true)
                return@observe

            if (listSensors.isNullOrEmpty()) {
                recyclerView?.visibility = View.INVISIBLE
                emptyView?.visibility = View.VISIBLE
            } else{
                recyclerView?.visibility = View.VISIBLE
                emptyView?.visibility = View.GONE
                recyclerViewAdapter?.setSensors(listSensors)
            }
        }
    }
}