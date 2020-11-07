package com.ort.healthyfoods.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

class ListColacionesFragment : Fragment() {
    private lateinit var viewModel: ListColacionesViewModel
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    lateinit var vista: View
    lateinit var recColaciones: RecyclerView
    lateinit var btnAdd: FloatingActionButton

    var colacionesList: MutableList<Food> = arrayListOf()

    private lateinit var adapter: FirestoreRecyclerAdapter<Food, FoodHolder> // OJO NO SE USA


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var foodListAdapter: FoodListAdapter

    companion object {
        fun newInstance() = ListColacionesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_colaciones_fragment, container, false)
        btnAdd = vista.findViewById(R.id.btn_add_snack)
        recColaciones = vista.findViewById(R.id.recColaciones)
        recColaciones.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recColaciones.layoutManager = linearLayoutManager
        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListColacionesViewModel::class.java)
        // TODO: Use the ViewModel
        //viewModel.initColaciones()
        //viewModel.cargar_Colaciones_Base()
        // COLACIONES
        db.collection("colaciones")
            .get()
            .addOnSuccessListener { result ->
                foodListAdapter = FoodListAdapter(colacionesList,requireContext()){position -> onItemClick(position)}
                recColaciones.adapter  = foodListAdapter
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    colacionesList.add(myObject)
                }
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

    }

    override fun onStart() {
        super.onStart()
        btnAdd.setOnClickListener {

        }
    }
    fun onItemClick(position:Int) {
        val goToDetail = ListBreackfastFragmentDirections.actionListBreackfastFragmentToDetailBreakfastFragment(colacionesList[position])
        vista.findNavController().navigate(goToDetail)
    }

}