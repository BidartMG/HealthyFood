package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Food

class ListRealizadasViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    var comidasRealizadas: MutableList<Food> = arrayListOf()
    lateinit var comidaRecibida: Food

    fun cargarRealizadasABase() {
        for (elemento in comidasRealizadas){
            comidaRecibida = elemento
            var newFood = hashMapOf(
                "idComida" to comidaRecibida.idComida,
                "nombre" to comidaRecibida.nombre,
                "tipoComida" to comidaRecibida.tipoComida,
                "calorias" to comidaRecibida.calorias,
                "descripcion" to comidaRecibida.descripcion,
                "urlImagen" to comidaRecibida.urlImagen
            )

            db.collection("comidasRealizadas")
                .add(newFood)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"La comida se cargó en la BBDD\"")
                }
                .addOnFailureListener {
                        e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                }
        }
    }
}
