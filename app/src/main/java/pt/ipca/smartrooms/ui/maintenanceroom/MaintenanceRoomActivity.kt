package pt.ipca.smartrooms.ui.maintenanceroom

import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.smartrooms.adapters.MaintenanceRoomAdapter
import pt.ipca.smartrooms.databinding.ActivityMaintenanceRoomBinding
import pt.ipca.smartrooms.viewmodel.MaintenanceRoomViewModel

class MaintenanceRoomActivity : AppCompatActivity() {

    private var recyclerViewColumn: RecyclerView? = null
    private var recyclerViewAdapter: MaintenanceRoomAdapter? = null
    companion object {
        const val KEY_SCHOOL = "keySchool"
    }

    val viewmodel by viewModels<MaintenanceRoomViewModel>()
    private lateinit var binding: ActivityMaintenanceRoomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintenanceRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setObservables()
        setViews()
    }

    private fun setToolBar(){
        title = null
        setSupportActionBar(binding.appbar.toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setViews() {
        recyclerViewColumn = binding.classroomLv
        recyclerViewAdapter = MaintenanceRoomAdapter(this)
        recyclerViewColumn?.apply {
            adapter = recyclerViewAdapter
            layoutManager = GridLayoutManager(this@MaintenanceRoomActivity, 2)
        }
    }

    private fun setObservables(){
        viewmodel.loading.observe(this){
            it?.let { isLoading ->
                binding.classroomPb.isVisible = isLoading
            }
        }

        viewmodel.school.observe(this) {
            it?.let { school ->
                binding.appbar.tvTitle.text = school.name
                school.id?.let { schoolId ->
                    viewmodel.getMaintenanceRooms(schoolId)
                }
            }
        }

        viewmodel.maintenanceRoom.observe(this) {listClassrooms ->
            val isEmpty = listClassrooms.isNullOrEmpty()
            binding.classroomEmptyTv.isVisible = isEmpty
            recyclerViewColumn?.isVisible  = !isEmpty
            if(!isEmpty)
                recyclerViewAdapter?.setMaintenanceRooms(listClassrooms)
        }

        viewmodel.error.observe(this){
            it?.getContentIfNotHandled()?.let { error ->
                Toast.makeText(this@MaintenanceRoomActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}


