package com.ort.healthyfoods.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.entities.Tip
import com.ort.healthyfoods.holders.FoodHolder
import com.ort.healthyfoods.holders.TipHolder

class TipListAdapter (private var tipList : MutableList<Tip>, var context: Context, val onItemClick : (Int) -> Unit) : RecyclerView.Adapter<TipHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)// TODO item_tip
        return (TipHolder(view))
    }

    override fun getItemCount(): Int {
        return tipList.size
    }

    override fun onBindViewHolder(holder: TipHolder, position: Int) {
        holder.setName(tipList[position].titulo)
        holder.getCardLayout().setOnClickListener() {
            onItemClick(position)
    }
        Glide
            .with(context)
            .load(tipList[position].urlImagen)
            .centerInside()
            .into(holder.getImageView())
}
}