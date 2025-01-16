package pt.ipca.smartrooms.ui.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.viewmodel.RoomViewModel

class RoomActivity : AppCompatActivity() {
    val viewModel by viewModels<RoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        title = null
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        viewModel.room.observe(this){
            it?.let { room ->
                findViewById<TextView>(R.id.tv_title).text = room.name
            }
        }

        viewModel.error.observe(this){
            it?.getContentIfNotHandled()?.let { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val viewPager = findViewById<ViewPager2>(R.id.room_view_pager)
        viewPager.adapter = RoomViewPager(viewModel.userType, this)
        val tabLayout = findViewById<TabLayout>(R.id.room_tab_layout)
        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            when(position){
                0 -> tab.text = "Dados"
                1 -> tab.text = "Operações"
            }
        }.attach()

        tabLayout.visibility = if(viewPager.adapter?.itemCount == 1) View.GONE else View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

}