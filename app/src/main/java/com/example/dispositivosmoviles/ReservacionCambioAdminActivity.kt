package com.example.dispositivosmoviles

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.util.rangeTo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_informacion_cliente_reservacion.*
import kotlinx.android.synthetic.main.activity_reservacion_cambio_admin.*

class ReservacionCambioAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservacion_cambio_admin)
        val bundle: Bundle? = intent.extras
        val email:String? = bundle?.getString("email")

        imageButton9.setOnClickListener {
            val ho = Intent(this, InformacionClienteReservacionActivity::class.java).apply {
                putExtra("email", email)
                // putExtra("provider", provider.name)
            }
            startActivity(ho)
        }

        infrese(email ?: "")

        button3.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            db.collection("Reservacion").document(email.toString())
                .delete()
                .addOnSuccessListener { showAlertEmail2() }
                .addOnFailureListener {  }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finishAffinity();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event)
    }
    private fun infrese(email: String){
        val db = FirebaseFirestore.getInstance()
        val doc = db.collection("Reservacion").document(email)
        doc.get()
            .addOnSuccessListener {
                val user = it.toObject<ReservacionClassClass>()
                gmail.text = user?.correo.toString()
                fechaini.text = user?.fechainicio.toString()
                fechafinn.text = user?.fechafin.toString()
                notaas.text = user?.nota.toString()
                diiii.text = user?.dias.toString()
                if (user?.checkin == true){
                    switch1.isChecked = true
                }

                if (user?.checkout == true){
                    switch2.isChecked = true
                }
            }

        button3uuu2.setOnClickListener {
            if (fechainieditt.text.isNotEmpty() && notasedditt.text.isNotEmpty() && diasedittes.text.isNotEmpty()){
                val fechafin =  fechafineditt.text.toString()
                val fechainicio = fechainieditt.text.toString()
                val dias = diasedittes.text.toString()
                val notas = notasedditt.text.toString()
                val checkin = switch1.isChecked
                val checkout = switch2.isChecked

                var usuarioNuevo = ReservacionClassClass(email, fechafin, fechainicio, dias, notas, "8", checkin, checkout)
                showAlertEmail()
                db.collection("Reservacion").document(email).set(usuarioNuevo)
            }
            else{
                showAlert()
            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Falta rellenar")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertEmail()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro exitoso")
        builder.setMessage("Cambio la informacion")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
            // Mandar de nuevo a la pantalla de login
            val loginIntent = Intent(this, ListadereserAdminActivity::class.java)
            startActivity(loginIntent)

        })
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
    private fun showAlertEmail2()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro exitoso")
        builder.setMessage("Cambio la informacion")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
            // Mandar de nuevo a la pantalla de login
            val loginIntent = Intent(this, ListadereserAdminActivity::class.java)
            startActivity(loginIntent)

        })
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}