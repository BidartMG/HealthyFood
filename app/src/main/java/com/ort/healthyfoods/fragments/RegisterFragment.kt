package com.ort.healthyfoods.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.enums.ProviderType

class RegisterFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var vista: View
    lateinit var titulo: TextView
    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var email: EditText
    lateinit var telefono: EditText
    lateinit var password: EditText
    lateinit var btnAccept: Button
    lateinit var btnCancel: Button
    lateinit var provider: ProviderType

    var identificador: Int = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_register, container, false)

        titulo = vista.findViewById(R.id.txt_tittle_register)
        nombre = vista.findViewById(R.id.edt_name_register)
        apellido = vista.findViewById(R.id.edt_lastname_register)
        email = vista.findViewById(R.id.edt_mail_register)
        telefono = vista.findViewById(R.id.edt_phone_register)
        password = vista.findViewById(R.id.edt_pass_register)
        btnAccept = vista.findViewById(R.id.btn_send_register)
        btnCancel = vista.findViewById(R.id.btn_cancel_register)

        return vista
    }

    override fun onStart() {
        super.onStart()

        titulo.text = "Ingrese sus datos"

        btnCancel.setOnClickListener() {
            limpiarEditTexts()
            val goToLoginFragment = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            vista.findNavController().navigate(goToLoginFragment)
        }

        btnAccept.setOnClickListener() {
            crearUserAutenticado()
            db.collection("users").document(email.text.toString()).set(
                hashMapOf("provider" to provider, // Ver si es de utilidad este proveedor
                    "nombre" to nombre.text.toString(),
                    "apellido" to apellido.text.toString(),
                    "email" to email.text.toString(),
                    "telefono" to telefono.text.toString(),
                    "password" to password.text.toString())
            )
            limpiarEditTexts()
        }
    }

    private fun limpiarEditTexts () {
        nombre.setText("")
        apellido.setText("")
        email.setText("")
        telefono.setText("")
        password.setText("")
    }

    private fun crearUserAutenticado () {
        if (inputsIsNotEmpty()) {
            if (email.text.isNotEmpty() && password.text.isNotEmpty()) {
                provider = ProviderType.BASIC
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    )
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            val goToPresentacion = RegisterFragmentDirections.actionRegisterFragmentToPresentacionFragment()
                            vista.findNavController().navigate(goToPresentacion)
                        } else {
                            showAlert("Se ha producido un error autenticando al usuario'<br/>' Email ya registrado o Usuario inexistente!")
                        }
                    }
            }
        }else {
            showAlert("TODOS los campos deben estar completos")
        }
    }

    private fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }

    private fun inputsIsNotEmpty() : Boolean{
        return (nombre.text.isNotEmpty() && apellido.text.isNotEmpty() && telefono.text.isNotEmpty())
    }
}