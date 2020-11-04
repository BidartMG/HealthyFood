package com.ort.healthyfoods.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food


class PrincipalFragment : Fragment() {

    lateinit var vista: View

    lateinit var img_desMer: ImageView
    lateinit var img_almCen: ImageView
    lateinit var img_colac: ImageView
    lateinit var titleDes: TextView
    lateinit var titleAlm: TextView
    lateinit var titleCol: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_principal, container, false)

        img_almCen = vista.findViewById(R.id.img_alm_cen)
        img_colac = vista.findViewById(R.id.img_colac)
        img_desMer = vista.findViewById(R.id.img_des_mer)
        titleAlm = vista.findViewById(R.id.txt_title_alm)
        titleCol = vista.findViewById(R.id.txt_title_colac)
        titleDes = vista.findViewById(R.id.txt_title_des)
        return vista
    }

    override fun onStart() {
        super.onStart()
        titleDes.setText("Desayunos y Meriendas")
        titleCol.setText("Colaciones Saludables")
        titleAlm.setText("Almuerzos y Cenas")

        img_desMer.setOnClickListener {
            //showAlert("ESTOY EN LOS DESAYUNOS Y  MERIENDAS")
            val goToListBreackfast = PrincipalFragmentDirections.actionPrincipalFragmentToListBreackfastFragment()
            vista.findNavController().navigate(goToListBreackfast)
        }
        img_colac.setOnClickListener {
            //showAlert("ESTOY EN LAS COLACIONES")
            val goToListFood = PrincipalFragmentDirections.actionPrincipalFragmentToListFoodFragment()
            vista.findNavController().navigate(goToListFood)
        }
        img_almCen.setOnClickListener {
            //showAlert("ESTOY EN LOS ALMUERZOS Y CENAS")
            val goToListFood = PrincipalFragmentDirections.actionPrincipalFragmentToListFoodFragment()
            vista.findNavController().navigate(goToListFood)
        }
    }
}

