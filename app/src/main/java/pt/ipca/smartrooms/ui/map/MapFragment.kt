package pt.ipca.smartrooms.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.launch
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.map.GoogleMap
import pt.ipca.smartrooms.model.Result

class MapFragment : Fragment() {

    var map : GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        setMap()
        setValues()
        return view
    }

    private fun setMap(){
        map = GoogleMap(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_map) as? SupportMapFragment
        mapFragment?.getMapAsync(map!!)
    }

    private fun setValues(){
        lifecycleScope.launch {
            val resp = ServiceLocator.roomRepository.getRooms()
            when(resp){
                is Result.Failure -> Toast.makeText(requireContext(), resp.error.message, Toast.LENGTH_SHORT).show()
                is Result.Success -> map?.setRooms(resp.data)
            }
        }
    }

}