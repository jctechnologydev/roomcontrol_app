package pt.ipca.smartrooms.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.model.School
import pt.ipca.smartrooms.ui.maintenanceroom.MaintenanceRoomActivity


class SchoolsAdapter(private val activity: Activity) : RecyclerView.Adapter<SchoolsAdapter.ViewHolder>() {

    private var schools: ArrayList<School> = arrayListOf()


    var fetchImage : ((url: String,callback: (Bitmap?) -> Unit) -> Unit)? = null

    fun setSchools(list: ArrayList<School>) {
        schools = list
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.findViewById<CardView>(R.id.row_school_card)
        val textViewSchool = view.findViewById<TextView>(R.id.row_school_tv)
        val imageViewSchool = card.findViewById<ImageView>(R.id.row_school_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_school, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        schools[position].let { school ->
            holder.textViewSchool.text = school.name

            // Ler imagem reccebida do servidor
            school.urlToImage?.let {
                fetchImage?.invoke(it) { bitmap ->
                    holder.imageViewSchool.setImageBitmap(bitmap)
                }
            }
            holder.card.setOnClickListener {

                val intent = Intent(activity, MaintenanceRoomActivity::class.java)
                intent.putExtra(MaintenanceRoomActivity.KEY_SCHOOL, school)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = schools.size

}

