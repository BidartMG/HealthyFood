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
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import java.sql.Timestamp
import java.time.Instant


class DetailBreakfastFragment : Fragment() {
    private lateinit var vista: View
    private lateinit var image: ImageView
    private lateinit var titulo: TextView
    private lateinit var calorias: TextView
    private lateinit var tipoComida: TextView
    private lateinit var descripcion: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnSeleccionar: Button

    private lateinit var comida: Food
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_detail_breakfast, container, false)
        image = vista.findViewById(R.id.img_detail_food)
        titulo = vista.findViewById(R.id.txt_name_item_food)
        calorias = vista.findViewById(R.id.txt_detail_calorias)
        tipoComida = vista.findViewById(R.id.txt_detail_tipo)
        descripcion = vista.findViewById(R.id.txt_detail_descrip)
        btnSeleccionar = vista.findViewById(R.id.btn_seleccionar_desayuno)
        btnVolver = vista.findViewById(R.id.btn_volver_lista_desayunos)
        return vista
    }

    override fun onStart() {
        super.onStart()
        comida = DetailBreakfastFragmentArgs.fromBundle(requireArguments()).comida
        setupUI()
        btnVolver.setOnClickListener {
            val goToListBreackfast = DetailBreakfastFragmentDirections.actionDetailBreakfastFragmentToListBreackfastFragment()
            vista.findNavController().navigate(goToListBreackfast)
        }
        btnSeleccionar.setOnClickListener {
            agregarComidaRealizadaABase()
            val goToMenu = DetailBreakfastFragmentDirections.actionDetailBreakfastFragmentToPresentacionFragment()
            vista.findNavController().navigate(goToMenu)
        }
    }

    private fun setupUI () {
        Glide
            .with(requireContext())
            .load(comida.urlImagen)
            .centerInside()
            .into(image)
        titulo.text = comida.nombre
        tipoComida.text = "Tipo: " +comida.tipoComida
        calorias.text =  comida.calorias.toString() + " calorías"
        descripcion.text = comida.descripcion
    }

    private fun agregarComidaRealizadaABase() {
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        val comidaRealizada = comida
        val newFood = hashMapOf(
            "usuario" to  usuario,
            "idComida" to comidaRealizada.idComida,
            "nombre" to comidaRealizada.nombre,
            "tipoComida" to comidaRealizada.tipoComida,
            "calorias" to comidaRealizada.calorias,
            "descripcion" to comidaRealizada.descripcion,
            "urlImagen" to comidaRealizada.urlImagen,
            "fechaRealizada" to Timestamp.from(Instant.now())
        )
        db.collection("comidasRealizadas")
            .add(newFood)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}")
                showAlert("Carga Exitosa")
            }
            .addOnFailureListener {
                    e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                showAlert("Entró al ERROR")
            }
    }

    fun showAlert(message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Hola")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }

}