package pt.ipca.smartrooms.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.KEY_ROOM
import pt.ipca.smartrooms.KEY_USERTYPE
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.model.State.*
import pt.ipca.smartrooms.model.UserType
import pt.ipca.smartrooms.ui.room.RoomActivity


class UCurricularsAdapter(private val activity: Activity, private val userType: UserType) : RecyclerView.Adapter<UCurricularsAdapter.ViewHolder>() {

    private var uCurriculars : ArrayList<UCurricular> = arrayListOf()

    fun setUcurriculars(list: ArrayList<UCurricular>){
        uCurriculars = list
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val card = view.findViewById<CardView>(R.id.row_curricular_card)
        val textViewCurricularUnit = view.findViewById<TextView>(R.id.row_curricular_tv)
        val textViewClassroomCurricularUnit = view.findViewById<TextView>(R.id.row_curricular_classroom_tv)
        val viewStateCurricularUnit = view.findViewById<View>(R.id.row_curricular_view_state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_curricular_unit, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        uCurriculars[position].let { ucurricular ->
            holder.textViewCurricularUnit.text = ucurricular.name
            holder.textViewClassroomCurricularUnit.text = ucurricular.room?.name
            ucurricular.room?.state?.let { state ->
                val background = when(state){
                    GOOD -> R.drawable.circle_state_green
                    MEDIUM -> R.drawable.circle_state_middle
                    BAD -> R.drawable.circle_state_red
                }
                holder.viewStateCurricularUnit.background = ContextCompat.getDrawable(activity, background)
            }
            holder.card.setOnClickListener {
                val intent = Intent(activity, RoomActivity::class.java)
                intent.putExtra(KEY_ROOM, ucurricular.room)
                intent.putExtra(KEY_USERTYPE, userType)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = uCurriculars.size
}