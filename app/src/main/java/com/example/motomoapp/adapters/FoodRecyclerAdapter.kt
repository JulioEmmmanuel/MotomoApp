package com.example.motomoapp.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.databinding.FoodItemCardBinding
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.viewmodels.MenuViewModel
import java.io.InputStream
import java.net.URL


class FoodRecyclerAdapter(
    private val foodItems: List<FoodItem>,
    private val viewModel: MenuViewModel): RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItems.get(position)
        holder.bind(foodItem, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(FoodItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return foodItems.size
    }

    class ViewHolder(val binding: FoodItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        //"atando" los datos a las Views

        fun bind(foodItem: FoodItem, viewModel: MenuViewModel){
            binding.foodItem = foodItem
            binding.viewModel = viewModel
            binding.price = "$" + String.format("%.2f", foodItem.price.toDouble())

            // Load the image from a URL in a background thread using Kotlin coroutines
            // val bitmap = BitmapFactory.decodeStream(URL(foodItem.url).content as InputStream)
            // binding.imgFoodItem.setImageBitmap(bitmap)
        }
    }
}
