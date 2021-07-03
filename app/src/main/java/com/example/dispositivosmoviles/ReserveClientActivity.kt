package com.example.dispositivosmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_main_client.*
import kotlinx.android.synthetic.main.activity_reserve_client.*

class ReserveClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_client)
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        setup()
        loadTexts(email ?: "")


    }
    private fun loadTexts(email: String){
        val db = FirebaseFirestore.getInstance()
        val doc = db.collection("Reservacion").document(email)

        doc.get()
            .addOnSuccessListener {
                val yipos = it.toObject<ReservacionClassClass>()
                fechainicioview.text = yipos?.fechainicio.toString()
                fechafinviw.text = yipos?.fechafin.toString()
                ffff.text = yipos?.hora.toString()
                horaentrada2.text = yipos?.hora.toString()
            }
    }
    private fun setup( ) {
        title = "Cliente"
        val bundle: Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        holahola.setOnClickListener {
            val homeIn = Intent(this, MainClientActivity::class.java).apply {
            putExtra("email", email)
            // putExtra("provider", provider.name)
            }
            startActivity(homeIn)
        }
        buttondos.setOnClickListener {
            val homeIn = Intent(this, CrearreservacionClientActivity::class.java).apply {
                putExtra("email", email)
                // putExtra("provider", provider.name)
            }
            startActivity(homeIn)
        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finishAffinity();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event)
    }

}
