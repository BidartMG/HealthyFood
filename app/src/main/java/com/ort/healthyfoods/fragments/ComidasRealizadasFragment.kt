package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.FoodListAdapter
import com.ort.healthyfoods.entities.Food
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit


class ComidasRealizadasFragment : Fragment() {

    private lateinit var vista: View
    private lateinit var recListaComidas: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var foodListAdapter: FoodListAdapter


    var listaComidasRealizadas: MutableList<Food> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        fun newInstance() = ComidasRealizadasFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_comidas_realizadas, container, false)

        recListaComidas = vista.findViewById(R.id.recyclerViewComidas)
        recListaComidas.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        recListaComidas.layoutManager = linearLayoutManager

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        var today = Instant.now().truncatedTo(ChronoUnit.DAYS)


        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .whereGreaterThan("fechaRealizada",Timestamp.from(today))
            .get()
            .addOnSuccessListener { result ->
                foodListAdapter = FoodListAdapter(listaComidasRealizadas,requireContext()){}
                recListaComidas.adapter  = foodListAdapter
                for (document in result) {
                    val food = document.toObject(Food::class.java)
                    listaComidasRealizadas.add(food)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

    }


    override fun onStart() {
        super.onStart()
    }
}