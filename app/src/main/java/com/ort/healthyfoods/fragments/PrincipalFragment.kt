package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.entities.Tip
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*


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
        obtenerTip()

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

    fun obtenerTip() {
        var consejos: MutableList<Tip> = arrayListOf()
        val db = FirebaseFirestore.getInstance()

        var consejo: Tip //= Tip(1,"TIP 100: Variedad, equilibrio y moderación en tu alimentación.","La importancia de una buena alimentación radica en estos tres pilares. Por eso, es interesante comer de todos los colores, de todos los grupos de alimentos sanos y en las cantidades saludables que se ajusten a tus necesidades. No te olvides de tomar pescados, carnes blancas, huevos, legumbres, frutas y verduras…","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/DIETA-VARIADA-300x225.jpg.webp")
        var random = Random()
        var indice = random.nextInt(10)// uno mas porque devuelve hasta el anterior
        db.collection("TipsYConsejos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Tip::class.java)
                    consejos.add(myObject)
                }
                consejo = consejos[indice]
                if(consejo.idTip != (indice-1) ) {
                    showAlert(consejo.titulo, consejo.descripcion)
                } else {
                    showAlert("Este NO es un TIP SALUDABLE","Nada Saludable")
                }
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }




}

