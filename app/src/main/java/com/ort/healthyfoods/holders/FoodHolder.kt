package com.ort.healthyfoods.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ort.healthyfoods.R

class FoodHolder (view:View) : RecyclerView.ViewHolder(view) {
    private var vista: View

    init {
        this.vista = view
    }

    fun setName (name: String) {
        val txt: TextView = vista.findViewById(R.id.txt_name_item_food)
        txt.text = name
    }

    fun getCardLayout () : CardView {
        return vista.findViewById(R.id.card_package_item_food)
    }

    fun setDetail(detail: String) {
        val txt: TextView = vista.findViewById(R.id.txt_detail_descrip)// verificar de donde lo traigo, puede fallar
        txt.text = detail
    }

    fun getImageView(): ImageView {
        return vista.findViewById(R.id.img_item_food)
    }
}


