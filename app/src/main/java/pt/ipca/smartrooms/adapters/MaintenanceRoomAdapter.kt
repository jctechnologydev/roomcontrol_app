package pt.ipca.smartrooms.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.KEY_ROOM
import pt.ipca.smartrooms.KEY_USERTYPE
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.Room
import pt.ipca.smartrooms.model.UserType
import pt.ipca.smartrooms.ui.room.RoomActivity


class MaintenanceRoomAdapter(private val activity: Activity) : RecyclerView.Adapter<MaintenanceRoomAdapter.ViewHolder>() {

    private var mRooms : ArrayList<Room> = arrayListOf()

    fun setMaintenanceRooms(list: ArrayList<Room>){
        mRooms = list
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val card = view.findViewById<CardView>(R.id.row_classroom_card)
        val textViewMaintenanceRoom = view.findViewById<TextView>(R.id.row_classroom_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_classroom, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mRooms[position].let { classroom ->

            holder.textViewMaintenanceRoom.text = classroom.name
            //Manuntenção para os menus de gerir sensores
            holder.card.setOnClickListener {
                val intent = Intent(activity, RoomActivity::class.java)
                intent.putExtra(KEY_ROOM, classroom)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = mRooms.size
}