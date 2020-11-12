package com.ort.healthyfoods.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food


class DetailSnackFragment : Fragment() {
    private lateinit var vista: View
    private lateinit var btnVolver: Button
    private lateinit var btnEliminar: Button
    private lateinit var image: ImageView
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
        vista = inflater.inflate(R.layout.fragment_detail_snack, container, false)
        btnEliminar = vista.findViewById(R.id.btn_seleccionar_snack)
        btnVolver = vista.findViewById(R.id.btn_volver_alMenu)
        image = vista.findViewById(R.id.imageViewUnderConst)
        titulo = vista.findViewById(R.id.txt_name_snack)
        calorias = vista.findViewById(R.id.txt_calorias_snack)
        tipoComida = vista.findViewById(R.id.txt_tipo_snack)
        descripcion = vista.findViewById(R.id.txt_detalle_snack)

        return vista
    }

    override fun onStart() {
        super.onStart()
        comida = DetailSnackFragmentArgs.fromBundle(
            requireArguments()
        ).snack
        // cargarlo en la vista
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