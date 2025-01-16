package pt.ipca.smartrooms

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }


}