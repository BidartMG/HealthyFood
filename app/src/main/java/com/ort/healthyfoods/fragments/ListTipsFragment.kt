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
import com.ort.healthyfoods.entities.Tip
import com.ort.healthyfoods.holders.FoodHolder

class ListTipsFragment : Fragment() {

    lateinit var vista: View
    lateinit var recConsejos: RecyclerView
    //lateinit var btnAdd: FloatingActionButton

    var consejosList: MutableList<Tip> = arrayListOf()

   // private lateinit var adapter: FirestoreRecyclerAdapter<Tip, TipHolder>

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var linearLayoutManager: LinearLayoutManager /** */
    private lateinit var foodListAdapter: FoodListAdapter /** */

    companion object {
        fun newInstance() = ListTipsFragment()
    }

    private lateinit var viewModel: ListTipsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initTipsList()
        viewModel.cargarTips_Base()

        //TIPS
        db.collection("TipsYConsejos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Tip::class.java)
                    consejosList.add(myObject)
                }

//                foodListAdapter = FoodListAdapter(consejosList,requireContext()){position -> onItemClick(position)}/** ESTO */
//                recConsejos.adapter  = foodListAdapter

            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_tips_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListTipsViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.initTipsList()
        viewModel.cargarTips_Base()
    }





    fun onItemClick(position:Int) {
        //val goToDetail = ListFoodFragmentDirections.actionListFoodFragmentToDetailFragment(consejosList[position])
        //vista.findNavController().navigate(goToDetail)
    }

}