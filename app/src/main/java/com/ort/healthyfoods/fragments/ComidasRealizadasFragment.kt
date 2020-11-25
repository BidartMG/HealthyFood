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
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.FoodListAdapter
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.holders.FoodHolder


class ComidasRealizadasFragment : Fragment() {

    private lateinit var vista: View
    private lateinit var listaComidas: RecyclerView
    lateinit var btnVolver: FloatingActionButton

    private lateinit var linearLayoutManager: LinearLayoutManager

    var comidasRealizadasList: MutableList<Food> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var viewModel: ListFoodViewModel = ListFoodViewModel()
    private lateinit var foodListAdapter: FoodListAdapter

    companion object {
        fun newInstance() = ComidasRealizadasFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .orderBy("fechaRealizada")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val food = document.toObject(Food::class.java)
                    comidasRealizadasList.add(food)
                }
                foodListAdapter = FoodListAdapter(comidasRealizadasList,requireContext()){position -> onItemClick(position)}
                listaComidas.adapter  = foodListAdapter
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.fragment_comidas_realizadas, container, false)

        listaComidas = vista.findViewById(R.id.recyclerViewComidas)
        btnVolver = vista.findViewById(R.id.btnVolver)

        listaComidas.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        listaComidas.layoutManager = linearLayoutManager

        return vista
    }

    override fun onStart() {
        super.onStart()

        btnVolver.setOnClickListener {
            val volver = ComidasRealizadasFragmentDirections.actionComidasRealizadasFragmentToPresentacionFragment()
            vista.findNavController().navigate(volver)
        }
    }

    fun onItemClick(position:Int) {
        //val goToDetail = ComidasRealizadasFragmentDirections.actionComidasRealizadasFragmentToDetailFragment(listaComidas[position])
        //vista.findNavController().navigate(goToDetail)
    }
}