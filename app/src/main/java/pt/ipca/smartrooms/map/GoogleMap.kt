package pt.ipca.smartrooms.map

import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipca.smartrooms.model.Room

class GoogleMap(private val context: Context) : OnMapReadyCallback, OnMarkerClickListener {
    private var mMap : GoogleMap? = null

    private var listRooms : List<Room> = emptyList()

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val center = LatLng(41.537246104756946, -8.627542057579772)
        mMap?.let { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(center)
                    .tilt(10F)
                    .zoom(17.5F)
                    .bearing(5F)
                    .build()
            ))
            googleMap.setMinZoomPreference(17.5F)
            googleMap.uiSettings.apply {
                    //isScrollGesturesEnabled = false
                    //isCompassEnabled = false
            }
            googleMap.setOnMarkerClickListener(this)
            googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(context))
        }
        drawRooms()
    }

    private fun drawRooms(){
        val builder = LatLngBounds.Builder()
        var newPoints = false
        mMap?.let { map ->
            listRooms.forEach { room ->
                room.position?.let { latLng ->
                    newPoints = true
                    builder.include(latLng)
                    val marker = map.addMarker(MarkerOptions()
                        .position(latLng)
                        .title(room.name)
                    )
                    marker?.tag = room

                }
            }
        }
        if (newPoints)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 160))
    }

    fun setRooms(rooms : List<Room>){
        listRooms = rooms
        drawRooms()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()
        return true
    }


}