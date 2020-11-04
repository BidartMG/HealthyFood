package com.ort.healthyfoods.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.healthyfoods.R

class UserHolder (view: View) : RecyclerView.ViewHolder(view){
    private var vista: View

    init {
        this.vista = view
    }

    fun setName (name: String) {
        val txt: TextView = vista.findViewById(R.id.txt_name_item_user)
        txt.text = name
    }

    fun getCardLayout () : CardView {
        return vista.findViewById(R.id.card_package_item_user)
    }
}

