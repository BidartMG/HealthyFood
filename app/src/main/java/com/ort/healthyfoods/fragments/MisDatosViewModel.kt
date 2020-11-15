package com.ort.healthyfoods.fragments

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.entities.User

class MisDatosViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var colaciones: MutableList<Food> = arrayListOf()

    lateinit private var usuario: User

    private val db = FirebaseFirestore.getInstance()
    fun initDatosPersonales () {

    }
}