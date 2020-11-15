package com.ort.healthyfoods.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
            if(nombre.text.toString().isEmpty()) {
                nombre.error = "Campo 'Nombre' no puede ser vacío"
                nombre.requestFocus()
            } else if (calorias.text.toString().isEmpty()){
                calorias.error = "Campo 'Calorias' no puede ser vacío"
                calorias.requestFocus()
            } else if (descripcion.text.toString().isEmpty()) {
                descripcion.error = "Campo 'Descripción' no puede ser vacío"
                descripcion.requestFocus()
            } else if (tipoComida.text.toString().isEmpty()) {
                tipoComida.error = "Campo 'Tipo comida' no puede ser vacío"
                tipoComida.requestFocus()
            } else if (urlImagen.text.toString().isEmpty()) {
                urlImagen.error = "Campo 'Url Imagen' no puede ser vacío"
                urlImagen.requestFocus()
            } else {
                agregarComida()
                //clear()
                vista.findNavController().navigate(R.id.action_addFoodFragment_to_listFoodFragment)
            }

        }
        btnCancelar.setOnClickListener() {
            clear()
            vista.findNavController().navigate(R.id.action_addFoodFragment_to_listFoodFragment)
        }

    }

    private fun agregarComida() {
        val comidaPrueba = Food(525600,nombre.text.toString(),descripcion.text.toString(),tipoComida.text.toString(),urlImagen.text.toString(),calorias.text.toString().toInt())
        val newFood = hashMapOf(
            "idComida" to comidaPrueba.idComida,
            "nombre" to comidaPrueba.nombre,
            "tipoComida" to comidaPrueba.tipoComida,
            "calorias" to comidaPrueba.calorias,
            "descripcion" to comidaPrueba.descripcion,
            "urlImagen" to comidaPrueba.urlImagen
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


    private fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Hola")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }
    private fun clear() {
        nombre.setText("")
        calorias.setText("")
        descripcion.setText("")
        tipoComida.setText("")
        urlImagen.setText("")

    }
}