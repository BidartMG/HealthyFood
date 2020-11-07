package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food


class AddSnacksFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var vista: View

    lateinit var nombre: EditText
    lateinit var calorias: EditText
    lateinit var descripcion: EditText // En este caso se puede tomar como la porción sugerida
    lateinit var urlImagen: EditText
    lateinit var btnAgregar: Button
    lateinit var btnCancelar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_add_snacks, container, false)
        nombre = vista.findViewById(R.id.edt_name_snack)
        calorias = vista.findViewById(R.id.edt_calorias_snack)
        descripcion = vista.findViewById(R.id.edt_descrip_snack)
        urlImagen = vista.findViewById(R.id.edt_urlImagen_snack)
        btnCancelar = vista.findViewById(R.id.btn_cancel_snack)
        btnAgregar = vista.findViewById(R.id.btn_add_snack)

        return vista
    }

    override fun onStart() {
        super.onStart()

        btnAgregar.setOnClickListener {
            agregarColación()
            clear()
        }
        btnCancelar.setOnClickListener {
            clear()
        }
    }

    /**
     * Método privado que comprueba que los inputs no se encuentren vacíos, si no lo están: crea un
     * nuevo registro con los datos ingresados por el usuario, asignándole un id y cargándolo a la
     * base de datos correspondiente, caso contrario emite un alerta avisando..
     */
    //TODO verificar que el identificador sea único y se autoincremente, validar que no haya platos
    // repetidos.
    private fun agregarColación() {
        if(nombre.text.isNotEmpty() && descripcion.text.isNotEmpty() && urlImagen.text.isNotEmpty() && calorias.text.isNotEmpty()) {//////
            val colacion = Food(525600,nombre.text.toString(),descripcion.text.toString(),"",urlImagen.text.toString(),calorias.text.toString().toInt())
            val newFood = hashMapOf(
                "idComida" to colacion.idComida,
                "nombre" to colacion.nombre,
                "tipoComida" to colacion.tipoComida,
                "calorias" to colacion.calorias,
                "descripcion" to colacion.descripcion,
                "urlImagen" to colacion.urlImagen
            )
            db.collection("colaciones")
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
    /*
    // COLACIONES
    db.collection("colaciones")
    .get()
    .addOnSuccessListener { result ->
        for (document in result) {
            val myObject = document.toObject(Food::class.java)
            comidaList.add(myObject)
        }
    }
    .addOnFailureListener {exception ->
        Log.d(ContentValues.TAG, "Error getting documents: ")
    }
*/
    /**
     * Método privado que recibe un mensaje a mostrar en formato de ventana alert, se puede crear
     * la variante con dos strings como parámetro para asignar también el título de la ventana
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