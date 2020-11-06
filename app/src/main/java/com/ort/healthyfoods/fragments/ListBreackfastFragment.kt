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

class ListBreackfastFragment : Fragment() {
    private var viewModel: ListBreackfastViewModel = ListBreackfastViewModel()
    lateinit var vista: View // zip
    lateinit var recDesayunos: RecyclerView
    lateinit var btnAdd: FloatingActionButton

    var desayunoList: MutableList<Food> = arrayListOf()

    private lateinit var adapter: FirestoreRecyclerAdapter<Food, FoodHolder> // NO SE USA

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //ver que onda estas dos siguientes
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var foodListAdapter: FoodListAdapter

    companion object {
        fun newInstance() = ListBreackfastFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         viewModel.initDesayYMeriendas()
         viewModel.cargarDes_Mer_Base()
        // DESAYUNOS
        db.collection("desayunosYmeriendas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    desayunoList.add(myObject)
                }
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_breackfast_fragment, container, false)
        btnAdd = vista.findViewById(R.id.btn_add_breackfast)
        recDesayunos = vista.findViewById(R.id.rec_desayunos_list)
        recDesayunos.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        recDesayunos.layoutManager = linearLayoutManager

        return vista
    }
    // Dejo esta llamada para probar
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListBreackfastViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        // TODO VER POR QUÉ DEMORA EN MOSTRAR
        foodListAdapter = FoodListAdapter(desayunoList,requireContext()){position -> onItemClick(position)}
        recDesayunos.adapter  = foodListAdapter

        btnAdd.setOnClickListener() {
            val goToAddBreackfast = ListBreackfastFragmentDirections.actionListBreackfastFragmentToAddBreakfastFragment()
            vista.findNavController().navigate(goToAddBreackfast)
        }
    }


    fun onItemClick(position:Int) {
        val goToDetail = ListBreackfastFragmentDirections.actionListBreackfastFragmentToDetailBreakfastFragment(desayunoList[position])
        vista.findNavController().navigate(goToDetail)
    }



}