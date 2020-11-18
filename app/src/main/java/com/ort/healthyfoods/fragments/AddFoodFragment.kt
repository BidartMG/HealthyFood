package com.ort.healthyfoods.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import java.sql.Timestamp
import java.time.Instant

class AddFoodFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var vista: View

    lateinit var nombre: EditText
    lateinit var calorias: EditText
    lateinit var descripcion: EditText
    lateinit var tipoComida: CheckBox
    lateinit var urlImagen: EditText
    lateinit var btnAgregar: Button
    lateinit var btnCancelar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_add_food, container, false)
        nombre = vista.findViewById(R.id.edt_nombre_food)
        calorias = vista.findViewById(R.id.edt_calorias_food)
        descripcion = vista.findViewById(R.id.edt_detail_food)
        tipoComida = vista.findViewById(R.id.check_type_cc_food)
        urlImagen = vista.findViewById(R.id.edt_url_img_food)
        btnAgregar = vista.findViewById(R.id.btn_accept_add_food)
        btnCancelar = vista.findViewById(R.id.btn_cancel_add_food)
        return vista
    }

    override fun onStart() {
        super.onStart()

        btnAgregar.setOnClickListener() {
            agregarComida()
            clear()
        }

        btnCancelar.setOnClickListener() {
            clear()
            val goToBack = AddFoodFragmentDirections.actionAddFoodFragmentToListFoodFragment()
            vista.findNavController().navigate(goToBack)
        }

    }

    /**
     * Método privado que comprueba que los inputs no se encuentren vacíos, si no lo están: crea un
     * nuevo registro con los datos ingresados por el usuario, asignándole un id y cargándolo a la
     * base de datos correspondiente, caso contrario emite un alerta avisando..
     */
    private fun agregarComida() {
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        val comidaPrueba = Food(-1,nombre.text.toString(),descripcion.text.toString(),tipoComida.text.toString(),urlImagen.text.toString(),calorias.text.toString().toInt())
        val newFood = hashMapOf(
            "usuario" to  usuario,
            "idComida" to comidaPrueba.idComida,
            "nombre" to comidaPrueba.nombre,
            "tipoComida" to comidaPrueba.tipoComida,
            "calorias" to comidaPrueba.calorias,
            "descripcion" to comidaPrueba.descripcion,
            "urlImagen" to comidaPrueba.urlImagen,
            "fechaRealizada" to Timestamp.from(Instant.now())
        )
        db.collection("almuerzosYcenas")
            .add(newFood)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"DocumentSnapshot written with ID: ${documentReference.id}")
                showAlert("La comida se cargó en la BBDD")
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "ERROR writing document", e)
                showAlert("Entró al ERROR")
            }
    }

    /**
     * Método privado que recibe un mensaje a mostrar en formato de ventana alert
     */
    private fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Hola")
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
        tipoComida.setText("")
        urlImagen.setText("")

    }
}