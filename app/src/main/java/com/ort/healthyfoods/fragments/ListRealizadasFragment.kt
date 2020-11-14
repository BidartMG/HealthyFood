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
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.FoodListAdapter
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.holders.FoodHolder
import kotlinx.android.synthetic.main.list_realizadas_fragment.*

class ListRealizadasFragment : Fragment() {


    private lateinit var adapter: FirestoreRecyclerAdapter<Food, FoodHolder> // OJO NO SE USA

    companion object {
        fun newInstance() = ListRealizadasFragment()
    }

    private lateinit var viewModel: ListRealizadasViewModel
    private lateinit var vista: View
    private lateinit var recRealizadas: RecyclerView
    private lateinit var caloriasConsumidas: TextView
    private lateinit var caloriasSemanales: TextView
    private lateinit var btnVolver: Button

    var comidasRealizadasList: MutableList<Food> = arrayListOf()

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var acumuladorCalorias: Int = 0

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var realizadasListAdapter: FoodListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_realizadas_fragment, container, false)

        caloriasConsumidas = vista.findViewById(R.id.edt_calorias_consumidas)
        caloriasSemanales = vista.findViewById(R.id.edt_calorias_semana)
        btnVolver = vista.findViewById(R.id.btn_volver_realizadas)

        recRealizadas = vista.findViewById(R.id.recRealizadas)
        recRealizadas.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(context)
        recRealizadas.layoutManager = linearLayoutManager
        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .get()
            .addOnSuccessListener { result ->
                //realizadasListAdapter = FoodListAdapter(comidasRealizadasList,requireContext()){position -> onItemClick(position)}
                //recRealizadas.adapter  = realizadasListAdapter
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    comidasRealizadasList.add(myObject)
                    acumuladorCalorias += myObject.calorias
                }
                caloriasConsumidas.setText(acumuladorCalorias.toString())

                //caloriasConsumidas.setText(acumuladorCalorias.toString() + "*************")
                // Cargar en los TextView los datos acumulados de calorias consumidas
                caloriasSemanales.setText("Estas dentro del límite semanal")


            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListRealizadasViewModel::class.java)
        // TODO: Use the ViewModel -  Ver de ponerlo sino en onCreate
        /*db.collection("comidasRealizadas")
            .get()
            .addOnSuccessListener { result ->
                realizadasListAdapter = FoodListAdapter(comidasRealizadasList,requireContext()){position -> onItemClick(position)}
                recRealizadas.adapter  = realizadasListAdapter
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    comidasRealizadasList.add(myObject)
                    acumuladorCalorias += myObject.calorias

                }

            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }*/


    }

    override fun onStart() {
        super.onStart()


        btnVolver.setOnClickListener {
            vista.findNavController().navigate(R.id.action_listRealizadasFragment_to_principalFragment)

        }

    }

    fun onItemClick(position:Int) {
        //val goToDetail = ListRealizadasFragmentDirections. ListFoodFragmentDirections.actionListFoodFragmentToDetailFragment(comidaList[position])
        //vista.findNavController().navigate(goToDetail)
        //Snackbar.make(frameLayoutRealizadas,"Esssssssssssssssssssstamos en OnItemClick",Snackbar.LENGTH_SHORT).show()
    }

    fun contarCalorias(): Int {
        var acumulador = 0
        for(comida in comidasRealizadasList) {
            acumulador += comida.calorias.toInt()
        }
        return acumulador
    }
}