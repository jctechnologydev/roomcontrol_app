package pt.ipca.smartrooms.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.Marker
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.Room
import pt.ipca.smartrooms.model.State.*

class CustomInfoWindowAdapter(
    private val context: Context) : com.google.android.gms.maps.GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View? {
        val room = marker.tag as Room

        val view = LayoutInflater.from(context).inflate(R.layout.map_info_layout, null)
        view.findViewById<TextView>(R.id.map_info_tv).text = room.name
        val viewState = view.findViewById<View>(R.id.map_info_state)
        val drawableId = room.state?.let {
            when(it){
                GOOD -> R.drawable.circle_state_green
                MEDIUM -> R.drawable.circle_state_middle
                BAD -> R.drawable.circle_state_red
            }
        }
        if(drawableId == null)
            viewState.visibility = View.GONE
        else{
            viewState.visibility = View.VISIBLE
            viewState.background = ContextCompat.getDrawable(context, drawableId)
        }

        return view
    }
}