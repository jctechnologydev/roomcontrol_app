package pt.ipca.smartrooms.ui.room

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pt.ipca.smartrooms.model.UserType

class RoomViewPager(private val userType: UserType, fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return when(userType){
            UserType.ALUNO -> 1
            UserType.TECNICO -> 2
            UserType.PROFESSOR -> 2
            else -> throw IllegalArgumentException("Invalid usertype")
        }
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RoomDataFragment()
            1 -> RoomOperationFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}