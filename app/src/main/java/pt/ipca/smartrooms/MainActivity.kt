package pt.ipca.smartrooms

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import pt.ipca.smartrooms.databinding.ActivityMainBinding
import pt.ipca.smartrooms.model.User
import pt.ipca.smartrooms.model.UserType
import pt.ipca.smartrooms.net.fetchImage
import pt.ipca.smartrooms.notification.MyFirebaseMessagingService
import pt.ipca.smartrooms.ui.map.MapFragment
import pt.ipca.smartrooms.ui.school.SchoolFragment
import pt.ipca.smartrooms.ui.ucurricular.UcurricularFragment

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyFirebaseMessagingService().start(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appbar.toolbar)

        title = null

        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawersLayout,
                R.string.open,
                R.string.close
            )
            drawersLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val user: User? = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(KEY_USER, User::class.java)
        } else {
            intent.getParcelableExtra(KEY_USER)
        }
        user?.userType?.let { userType ->
            val userMenu: Int
            val userHeader: Int
            val fragment : Fragment
            val title: String
            when (userType) {
                UserType.ALUNO -> {
                    userHeader = R.layout.nav_header_student
                    userMenu = R.menu.nav_menu_student_teacher
                    title = "Unidades Curriculares"
                    fragment = UcurricularFragment.newInstance(userType)
                }
                UserType.TECNICO, UserType.ADMIN -> {
                    userHeader = R.layout.nav_header_teacher_technician
                    userMenu = R.menu.nav_menu_technician
                    fragment = SchoolFragment()
                    title = "Manutenção"
                }
                UserType.PROFESSOR -> {
                    userHeader = R.layout.nav_header_teacher_technician
                    userMenu = R.menu.nav_menu_student_teacher
                    title = "Unidades Curriculares"
                    fragment = UcurricularFragment.newInstance(userType)
                }
            }

            binding.appbar.tvTitle.text = title
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFl.id, fragment)
                .commit()

            binding.navView.apply {
                menu.clear()
                val menuHeader = inflateHeaderView(userHeader)
                inflateMenu(userMenu)

                menuHeader.let { headerView ->
                    user.imageUrl?.let { urlImage ->
                        fetchImage(lifecycleScope, urlImage) { bitmap ->
                            headerView.findViewById<ImageView>(R.id.nav_header_s_image_view)
                                .setImageBitmap(bitmap)
                        }
                    }

                    headerView.findViewById<TextView>(R.id.nav_header_tt_name_tv)?.text =
                        user.name
                    headerView.findViewById<TextView>(R.id.nav_header_s_name_tv)?.text = user.name

                    headerView.findViewById<TextView>(R.id.nav_header_tt_permission_level_tv)?.text =
                        userType.name
                    headerView.findViewById<TextView>(R.id.nav_header_s_permission_level_tv)?.text =
                        userType.name

                    headerView.findViewById<TextView>(R.id.nav_header_s_course_tv)?.text = user.course
                }
            }

            binding.navView.setNavigationItemSelectedListener {
                binding.drawersLayout.close()
                var intent : Intent? = null
                var fragment : Fragment? = null
                var title : String? = null
                val result = when (it.itemId) {
                    R.id.nav_menu_curricular_unit -> {
                        title = "Unidades Curriculares"
                        fragment = UcurricularFragment.newInstance(userType)
                        true
                    }
                    R.id.nav_menu_maintenance -> {
                        title = "Manutenção"
                        fragment = SchoolFragment()
                        true
                    }
                    R.id.nav_menu_map_icon -> {
                        title = "Mapa"
                        fragment = MapFragment()
                        true
                    }
                    R.id.nav_menu_logout_icon -> {
                        ServiceLocator.tokenSP.removeToken()
                        intent = Intent(this, LoginActivity::class.java)
                        //limpar todas as activities que tenha
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        true
                    }
                    else -> {
                        throw IllegalArgumentException("Invalid option")
                    }
                }
                if(intent != null) {
                    intent.putExtra(KEY_USERTYPE, user.userType)
                    startActivity(intent)
                } else if(fragment != null){
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFl.id, fragment!!)
                        .commit()
                }

                if(!title.isNullOrEmpty())
                    binding.appbar.tvTitle.text = title

                return@setNavigationItemSelectedListener result
            }
        }
    }


    /***
     * Apresenta as opções do menu lateral
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }


        return super.onOptionsItemSelected(item)
    }
}