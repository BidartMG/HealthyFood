package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.User

@Suppress("UNREACHABLE_CODE")
class MisDatosFragment : Fragment() {

    companion object {
        fun newInstance() = MisDatosFragment()
    }

    private lateinit var viewModel: MisDatosViewModel
    private lateinit var vista: View
    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var email: EditText
    lateinit var telefono: EditText
    lateinit var password: EditText
    lateinit var edit : Button
    lateinit var save : Button

    lateinit var user : User
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!
        vista = inflater.inflate(R.layout.mis_datos_fragment, container, false)

        nombre = vista.findViewById(R.id.txtNombre)
        apellido = vista.findViewById(R.id.txtApellido)
        email = vista.findViewById(R.id.txtMail)
        telefono = vista.findViewById(R.id.txtTel)
        password = vista.findViewById(R.id.txtPass)
        edit = vista.findViewById(R.id.btnEdit)
        save = vista.findViewById(R.id.btnGuardar)

        db.collection("users").document(usuario)
            .get()
            .addOnSuccessListener { result ->
                user = result.toObject(User::class.java)!!
                populateFragment()
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

        edit.setOnClickListener() {
            habilitarCampos()
        }

        save.setOnClickListener() {
            when {
                nombre.text.isEmpty() -> {
                    nombre.error = "Campo 'Nombre' no puede ser vacío"
                    nombre.requestFocus()
                }
                apellido.text.isEmpty() -> {
                    apellido.error = "Campo 'Apellido' no puede ser vacío"
                    apellido.requestFocus()
                }
                password.text.isEmpty() -> {
                    password.error = "Campo 'Password' no puede ser vacío"
                    password.requestFocus()
                }
                else -> {
                    db.collection("users").document(email.text.toString()).set(
                        hashMapOf("nombre" to nombre.text.toString(),
                            "apellido" to apellido.text.toString(),
                            "email" to email.text.toString(),
                            "telefono" to telefono.text.toString(),
                            "password" to password.text.toString())
                    )
                    deshabilitarCampos()
                    Snackbar.make(vista,"Datos actualizados correctamente",Snackbar.LENGTH_LONG).show()
                }
            }

        }
        return vista
    }

    private fun deshabilitarCampos() {
        nombre.isEnabled = false
        apellido.isEnabled = false
        telefono.isEnabled = false
        password.isEnabled = false
    }

    private fun habilitarCampos() {
        nombre.isEnabled = true
        apellido.isEnabled = true
        telefono.isEnabled = true
        password.isEnabled = true
    }

    private fun populateFragment() {
        nombre.setText(user.nombre)
        apellido.setText(user.apellido)
        email.setText(user.email)
        telefono.setText(user.telefono)
        password.setText(user.password)
        nombre.isEnabled = false
        apellido.isEnabled = false
        email.isEnabled = false
        telefono.isEnabled = false
        password.isEnabled = false
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MisDatosViewModel::class.java)
    }
}