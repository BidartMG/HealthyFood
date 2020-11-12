package com.ort.healthyfoods.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.ort.healthyfoods.R

class PresentacionFragment : Fragment() {
    private lateinit var vista: View
    private lateinit var txtListados: TextView
    private lateinit var txtMisDatos: TextView
    private lateinit var txtMisComidas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_presentacion, container, false)
        txtListados = vista.findViewById(R.id.txt_listados)
        txtMisDatos = vista.findViewById(R.id.txt_mis_datos)
        txtMisComidas = vista.findViewById(R.id.txt_title_comrealiz)
        return vista
    }

    override fun onStart() {
        super.onStart()
        txtMisComidas.setOnClickListener {
            val goToMisComidas = PresentacionFragmentDirections.actionPresentacionFragmentToListRealizadasFragment()
            vista.findNavController().navigate(goToMisComidas)
        }

        txtListados.setOnClickListener {
            val goToListados = PresentacionFragmentDirections.actionPresentacionFragmentToPrincipalFragment()
            vista.findNavController().navigate(goToListados)
        }

        txtMisDatos.setOnClickListener {
            // TODO
        }

    }
}