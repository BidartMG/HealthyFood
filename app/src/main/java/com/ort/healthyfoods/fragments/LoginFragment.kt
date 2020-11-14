package com.ort.healthyfoods.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ort.healthyfoods.R

class LoginFragment : Fragment() {
    lateinit var vista: View
    lateinit var usuario: EditText
    lateinit var password: EditText
    lateinit var btnAceptar: Button
    lateinit var btnRegister: Button

    val PREF_NAME = "myPreferences"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_login, container, false)
        usuario = vista.findViewById(R.id.emailEditText)
        password = vista.findViewById(R.id.passEditText)
        btnAceptar = vista.findViewById(R.id.ingresarButton)
        btnRegister = vista.findViewById(R.id.registrarseButton)
        return vista
    }

    override fun onStart() {
        super.onStart()

        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        btnRegister.setOnClickListener() {
            clear()
            val goToRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment2()
            vista.findNavController().navigate(goToRegister)
        }
        btnAceptar.setOnClickListener() {
            if (usuario.text.isNotEmpty() && password.text.isNotEmpty()) {
                if(!usuario.text.isNotEmpty()) {
                    editor.putString("USER", "ERROR") // REVISAR el Else
                }
                editor.putString("USER", usuario.text.toString())
                editor.apply()

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(usuario.text.toString(),password.text.toString())
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            val goToPresentacion = LoginFragmentDirections.actionLoginFragmentToPresentacionFragment()
                            vista.findNavController().navigate(goToPresentacion)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }
    private fun showAlert() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Usuario Inválido")
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }
    private fun clear() {
        usuario.setText("")
        password.setText("")

    }
    // TODO showTips()
}