package com.example.motomoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.view.menu.ItemDetalleActivity
import com.example.motomoapp.R
import com.example.motomoapp.models.FoodItem
import com.squareup.picasso.Picasso

class FoodRecyclerAdapter(
    private val context: Context,
    private val foodItems: List<FoodItem>): RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItems.get(position)
        holder.bind(foodItem, context)
        holder.view.setOnClickListener{
            val intent = Intent(context, ItemDetalleActivity::class.java)
            intent.putExtra("FoodItem",foodItem)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.food_item_card, parent, false))
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        //obteniendo las referencias a las Views
        val itemName = view.findViewById(R.id.tvNombreItem) as TextView
        val description = view.findViewById(R.id.tvDescripcionItem) as TextView
        val price = view.findViewById(R.id.tvPrecioItem) as TextView
        val image = view.findViewById(R.id.imgFoodItem) as ImageView

        //"atando" los datos a las Views
        fun bind(foodItem: FoodItem, context: Context){
            itemName.text = foodItem.name
            description.text = foodItem.description
            price.text = "$" + String.format("%.2f", foodItem.price.toDouble())
            Picasso.get().load(foodItem.idImage).into(image)
        }
    }
}
