package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import java.sql.Timestamp
import java.time.Instant


class AddBreakfastFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var vista: View

    lateinit var nombre: EditText
    lateinit var calorias: EditText
    lateinit var descripcion: EditText
    lateinit var urlImagen: EditText
    lateinit var btnAgregar: Button
    lateinit var btnCancelar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_add_breakfast, container, false)
        nombre = vista.findViewById(R.id.edt_nombre_desayuno)
        calorias = vista.findViewById(R.id.edt_calorias_desayuno)
        descripcion = vista.findViewById(R.id.edt_detail_desayuno)
        urlImagen = vista.findViewById(R.id.edt_url_img_desayuno)
        btnAgregar = vista.findViewById(R.id.btn_accept_add_breack)
        btnCancelar = vista.findViewById(R.id.btn_cancel_add_breack)

        return vista
    }

    override fun onStart() {
        super.onStart()

        btnAgregar.setOnClickListener {
            agregarDesayuno()
            clear()
        }

        btnCancelar.setOnClickListener {
            clear()
            val goToBack = AddBreakfastFragmentDirections.actionAddBreakfastFragmentToListBreackfastFragment()
            vista.findNavController().navigate(goToBack)
        }
    }

    /**
     * Método privado que comprueba que los inputs no se encuentren vacíos, si no lo están: crea un
     * nuevo registro con los datos ingresados por el usuario, asignándole un id y cargándolo a la
     * base de datos correspondiente.
     */
    //TODO verificar que el identificador sea único y se autoincremente, validar que no haya platos
    // repetidos.
    private fun agregarDesayuno() {
        if(nombre.text.isNotEmpty() && descripcion.text.isNotEmpty() && urlImagen.text.isNotEmpty() && calorias.text.isNotEmpty()) {
            val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!
            val desayunoPrueba = Food(-1,nombre.text.toString(),descripcion.text.toString(),"",urlImagen.text.toString(),calorias.text.toString().toInt())

            val newFood = hashMapOf(
                "usuario" to  usuario,
                "idComida" to desayunoPrueba.idComida,
                "nombre" to desayunoPrueba.nombre,
                "tipoComida" to desayunoPrueba.tipoComida,
                "calorias" to desayunoPrueba.calorias,
                "descripcion" to desayunoPrueba.descripcion,
                "urlImagen" to desayunoPrueba.urlImagen,
                "fechaRealizada" to Timestamp.from(Instant.now())
            )
            db.collection("desayunosYmeriendas")
                .add(newFood)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}")
                    showAlert("La comida se cargó en la BBDD")
                }
                .addOnFailureListener {
                        e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                    showAlert("Entró al ERROR")
                }
        } else {
            showAlert("ERROR, Debe completar todos los campos!")
        }

    }

    /**
     * Método privado que recibe un mensaje a mostrar en formato de ventana alert
     */
    private fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("MENSAJE IMPORTANTE")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * Método que se encarga de setear todos los campos como vacíos
     */
    private fun clear() {
        nombre.setText("")
        calorias.setText("")
        descripcion.setText("")
        urlImagen.setText("")
    }
}