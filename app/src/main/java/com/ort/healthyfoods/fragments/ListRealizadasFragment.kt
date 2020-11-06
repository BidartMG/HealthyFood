package com.ort.healthyfoods.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.FoodListAdapter
import com.ort.healthyfoods.entities.Food
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_realizadas_fragment.*

class ListRealizadasFragment : Fragment() {

    companion object {
        fun newInstance() = ListRealizadasFragment()
    }

    private lateinit var viewModel: ListRealizadasViewModel
    private lateinit var vista: View
    private lateinit var recRealizadas: RecyclerView
    private lateinit var caloriasConsumidas: TextView
    private lateinit var caloriasSemanales: TextView
    var comidasRealizadasList: MutableList<Food> = arrayListOf()

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var realizadasListAdapter: FoodListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_realizadas_fragment, container, false)
        recRealizadas = vista.findViewById(R.id.recRealizadas)
        caloriasConsumidas = vista.findViewById(R.id.edt_calorias_consumidas)
        caloriasSemanales = vista.findViewById(R.id.edt_calorias_semana)
        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListRealizadasViewModel::class.java)
        // TODO: Use the ViewModel -  Ver de ponerlo sino en onCreate
        db.collection("almuerzosYcenas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    comidasRealizadasList.add(myObject)
                }
                realizadasListAdapter = FoodListAdapter(comidasRealizadasList,requireContext()){position -> onItemClick(position)}
                /** ESTO */
                recRealizadas.adapter  = realizadasListAdapter
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }

    override fun onStart() {
        super.onStart()
        // Cargar en los TextView los datos acumulados de calorias consumidas

    }

    fun onItemClick(position:Int) {
        //val goToDetail = ListFoodFragmentDirections.actionListFoodFragmentToDetailFragment(comidaList[position])
        //vista.findNavController().navigate(goToDetail)
        Snackbar.make(frameLayoutRealizadas,"Esssssssssssssssssssstamos en OnItemClick",Snackbar.LENGTH_SHORT).show()
    }
}