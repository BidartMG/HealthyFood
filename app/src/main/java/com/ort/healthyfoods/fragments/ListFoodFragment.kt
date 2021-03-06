package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
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

class ListFoodFragment : Fragment() {

    private var viewModel: ListFoodViewModel = ListFoodViewModel()
    private var viewModelTip: ListTipsViewModel=ListTipsViewModel()

    lateinit var vista: View
    lateinit var recComidas: RecyclerView
    lateinit var btnAdd: FloatingActionButton

    var comidaList: MutableList<Food> = arrayListOf()

    private lateinit var adapter: FirestoreRecyclerAdapter<Food, FoodHolder> // OJO NO SE USA

    val db: FirebaseFirestore = FirebaseFirestore.getInstance() // zip

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var foodListAdapter: FoodListAdapter

    companion object { // zip
        fun newInstance() = ListFoodFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         //viewModel.initTestList()
         //viewModel.cargarAlm_Cen_Base()
        //viewModelTip.initTipsList()
        //viewModelTip.cargarTips_Base()

        db.collection("almuerzosYcenas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    comidaList.add(myObject)
                }

                foodListAdapter = FoodListAdapter(comidaList,requireContext()){position -> onItemClick(position)}
                recComidas.adapter  = foodListAdapter

            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_food_fragment, container, false)

        btnAdd = vista.findViewById(R.id.btn_add_listFood)

        recComidas = vista.findViewById(R.id.rec_comidas_listFood)
        recComidas.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        recComidas.layoutManager = linearLayoutManager

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListFoodViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        btnAdd.setOnClickListener() {
            val goToAddFood = ListFoodFragmentDirections.actionListFoodFragmentToAddFoodFragment()
            vista.findNavController().navigate(goToAddFood)
        }
    }

    fun onItemClick(position:Int) {
        val goToDetail = ListFoodFragmentDirections.actionListFoodFragmentToDetailFragment(comidaList[position])
        vista.findNavController().navigate(goToDetail)
    }

}

/**
 * Obtener algunos documentos de la colección
db.collection("cities")
.whereEqualTo("capital", true)
.get()
.addOnSuccessListener { documents ->
for (document in documents) {
Log.d(TAG, "${document.id} => ${document.data}")
}
}
.addOnFailureListener { exception ->
Log.w(TAG, "Error getting documents: ", exception)
}
 */