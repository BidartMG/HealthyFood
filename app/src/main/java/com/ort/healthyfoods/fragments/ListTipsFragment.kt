package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.R
import com.ort.healthyfoods.adapters.TipListAdapter
import com.ort.healthyfoods.entities.Tip

class ListTipsFragment : Fragment() {
    lateinit var vista: View
    lateinit var recConsejos: RecyclerView

    var consejosList: MutableList<Tip> = arrayListOf()

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var foodListAdapter: TipListAdapter

    companion object {
        fun newInstance() = ListTipsFragment()
    }

    private lateinit var viewModel: ListTipsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db.collection("TipsYConsejos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Tip::class.java)
                    consejosList.add(myObject)
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
        return inflater.inflate(R.layout.list_tips_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListTipsViewModel::class.java)

        viewModel.initTipsList()
        viewModel.cargarTips_Base()
    }
}