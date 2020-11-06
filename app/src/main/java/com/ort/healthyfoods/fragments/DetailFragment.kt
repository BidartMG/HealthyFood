package com.ort.healthyfoods.fragments

import android.content.ContentValues
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


class DetailFragment : Fragment() {
    private lateinit var vista: View
    lateinit var image: ImageView
    private lateinit var titulo: TextView
    private lateinit var calorias: TextView
    private lateinit var tipoComida: TextView
    private lateinit var descripcion: TextView
    private lateinit var comida: Food
    private lateinit var btnVolver: Button
    private lateinit var btnSeleccionar: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_detail, container, false)
        image = vista.findViewById(R.id.img_detail_food)
        titulo = vista.findViewById(R.id.txt_name_item_food)
        calorias = vista.findViewById(R.id.txt_detail_calorias)
        tipoComida = vista.findViewById(R.id.txt_detail_tipo)
        descripcion = vista.findViewById(R.id.txt_detail_descrip)
        btnSeleccionar = vista.findViewById(R.id.btn_seleccionar_food)
        btnVolver = vista.findViewById(R.id.btn_volver_lista)
        return vista
    }

    override fun onStart() {
        super.onStart()
        comida = DetailFragmentArgs.fromBundle(requireArguments()).comida// Esta es la comida que quiero guardar si la selecciono
        setupUI()
        btnVolver.setOnClickListener() {// Vuelve a la lista de comidas
            val valor = DetailFragmentDirections.actionDetailFragmentToListFoodFragment()
            vista.findNavController().navigate(valor)
        }
        btnSeleccionar.setOnClickListener() {
// Tenemos que guardar la referencia de la comida para cargarla en la lista de mis comidas
            agregarComidaRealizadaABase()
            // TODO Esto después se tiene que mover a otro acceso
            vista.findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToListRealizadasFragment())

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
        val comidaRealizada = comida
        val newFood = hashMapOf(
            "idComida" to comidaRealizada.idComida,
            "nombre" to comidaRealizada.nombre,
            "tipoComida" to comidaRealizada.tipoComida,
            "calorias" to comidaRealizada.calorias,
            "descripcion" to comidaRealizada.descripcion,
            "urlImagen" to comidaRealizada.urlImagen
        )
        db.collection("comidasRealizadas")
            .add(newFood)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}")
                showAlert("La comida se cargó en la BBDD de Realizadas")
            }
            .addOnFailureListener {
                    e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
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
}


