package pt.ipca.smartrooms.ui.sensor

import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.DialogTitle
import pt.ipca.smartrooms.R
import pt.ipca.smartrooms.databinding.ActivityEditSensorBinding

private lateinit var binding : ActivityEditSensorBinding

class EditSensorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //alertDialog("Aviso!","Tem a certeza que deseja apagar?")



    }

    fun alertDialog(title: String, message: String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Siam",
                DialogInterface.OnClickListener { dialog, which ->
                    // Continue with delete operation
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("Não", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}