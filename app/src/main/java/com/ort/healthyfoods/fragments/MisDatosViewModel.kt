package com.ort.healthyfoods.fragments

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.User

class MisDatosViewModel : ViewModel() {
    lateinit private var usuario: User

    private val db = FirebaseFirestore.getInstance()
    fun initDatosPersonales () {

    }
}