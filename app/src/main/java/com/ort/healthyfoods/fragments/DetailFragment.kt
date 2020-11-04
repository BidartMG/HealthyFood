package com.ort.healthyfoods.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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
        return vista
    }

    override fun onStart() {
        super.onStart()
        comida = DetailFragmentArgs.fromBundle(requireArguments()).comida
        setupUI()
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
}


