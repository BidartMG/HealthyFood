package com.ort.healthyfoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.holders.FoodHolder

class FoodListAdapter (private var foodList : MutableList<Food>, var context: Context, val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<FoodHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)
        return (FoodHolder(view))
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.setName(foodList[position].nombre)
        holder.getCardLayout().setOnClickListener() {
            onItemClick(position)
        }

        Glide
            .with(context)
            .load(foodList[position].urlImagen)
            .centerInside()
            .into(holder.getImageView())
    }
}