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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.FoodListAdapter
import com.ort.healthyfoods.entities.Food

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ComidasRealizadasFragment : Fragment() {

    private lateinit var vista: View
    private lateinit var listaComidas: RecyclerView
    lateinit var btnVolver: FloatingActionButton

    private lateinit var linearLayoutManager: LinearLayoutManager

    var comidasRealizadasList: MutableList<Food> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_comidas_realizadas, container, false)

        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!
        listaComidas = vista.findViewById(R.id.recyclerViewComidas)
        listaComidas.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        listaComidas.layoutManager = linearLayoutManager

        btnVolver = vista.findViewById(R.id.btnVolver)

        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .orderBy("fechaRealizada")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val food = document.toObject(Food::class.java)
                    comidasRealizadasList.add(food)
                }
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

        return vista
    }


    override fun onStart() {
        super.onStart()
    }
}