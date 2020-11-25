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
import com.ort.healthyfoods.entities.Tip
import kotlinx.android.synthetic.main.fragment_detail.*


class PrincipalFragment : Fragment() {

    lateinit var vista: View
    lateinit var img_desMer: ImageView
    lateinit var img_almCen: ImageView
    lateinit var img_colac: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_principal, container, false)
        img_almCen = vista.findViewById(R.id.img_alm_cen)
        img_colac = vista.findViewById(R.id.img_colac)
        img_desMer = vista.findViewById(R.id.img_des_mer)

        return vista
    }

    override fun onStart() {
        super.onStart()

        var tip = obtenerTip()
        if(tip.idTip != "-1") {
            showAlert(tip.titulo, tip.descripcion)
        } else {
            showAlert("Este NO es un TIP SALUDABLE","Nada Saludable")
        }


        img_desMer.setOnClickListener {
            val goToListBreackfast = PresentacionFragmentDirections.actionPresentacionFragmentToListBreackfastFragment()
            //val goToListBreackfast = PrincipalFragmentDirections.actionPrincipalFragmentToListBreackfastFragment()
            vista.findNavController().navigate(goToListBreackfast)
        }
        img_almCen.setOnClickListener {
            val goToListFood = PresentacionFragmentDirections.actionPresentacionFragmentToListFoodFragment()
            //val goToListFood = PrincipalFragmentDirections.actionPrincipalFragmentToListFoodFragment()
            vista.findNavController().navigate(goToListFood)
        }
        img_colac.setOnClickListener {
            val goToListSnacks = PresentacionFragmentDirections.actionPresentacionFragmentToListColacionesFragment()
            //val goToListSnacks = PrincipalFragmentDirections.actionPrincipalFragmentToListColacionesFragment()
            vista.findNavController().navigate(goToListSnacks)
        }
    }

    private fun showAlert(title:String, message:String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }

    private fun obtenerTip() : Tip {
        var viewModelTip: ListTipsViewModel=ListTipsViewModel()

        var tipLeido: Tip = Tip("-1","","","")

        tipLeido = viewModelTip.obtenerTip()

        return tipLeido
    }
}

