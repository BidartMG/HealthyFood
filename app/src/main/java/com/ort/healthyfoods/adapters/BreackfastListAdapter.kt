package com.ort.healthyfoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.holders.BreackfastHolder

class BreackfastListAdapter (private var breackfastList : MutableList<Food>, var context: Context, val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<BreackfastHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreackfastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)
        return (BreackfastHolder(view))
    }
    override fun getItemCount(): Int {
        return breackfastList.size
    }

    override fun onBindViewHolder(holder: BreackfastHolder, position: Int) {
        holder.setName(breackfastList[position].nombre)
        holder.getCardLayout().setOnClickListener() {
            onItemClick(position)
        }

        Glide
            .with(context)
            .load(breackfastList[position].urlImagen)
            .centerInside()
            .into(holder.getImageView())
    }
}