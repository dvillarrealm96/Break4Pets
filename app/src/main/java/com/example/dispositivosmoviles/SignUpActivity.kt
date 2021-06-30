package com.example.dispositivosmoviles

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.emailEditText
import kotlinx.android.synthetic.main.activity_auth.passwordEditText
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //SETUP
        setup()
    }
    private fun setup() {
        title = "Registro"
        val db = FirebaseFirestore.getInstance()

        signUpButton.setOnClickListener {
            if (passwordEditText.text.toString().equals(editTextPassword.text.toString())){

                if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            //showHome(it.result?.user?.email ?: "")
                            sendEmailVerification()
                            showAlertEmail()
                            db.collection("Usuarios").document(emailEditText.text.toString()).set(
                                hashMapOf("Correo" to emailEditText.text.toString(),
                                    "Telefono" to telefonotextview.text.toString(),
                                    "Nombre" to nametextview.text.toString(),
                                    "isAdmin" to false)
                            )
                        } else {
                            showAlert()
                        }
                    }
                }
            }
            else {
                showAlert()
            }
        }
    }


    private fun showAlert()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña no coincide")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertEmail()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro exitoso")
        builder.setMessage("Por favor revisa tu correo para la autenticación")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, id ->
            // Mandar de nuevo a la pantalla de login
            val loginIntent = Intent(this, AuthActivity::class.java)
            startActivity(loginIntent)

        })
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "Email sent.")
                }
            }
        // [END send_email_verification]
    }
}