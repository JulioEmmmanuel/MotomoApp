package com.example.motomoapp.utils

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.motomoapp.R
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.view.menu.ItemDetalleActivity
import com.example.motomoapp.view.menu.OrderActivity
import com.squareup.picasso.Picasso

//binding adapter to set up picasso image
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .placeholder(R.drawable.food)
        .into(view)
}

//binding adapter to see detail of an item
@BindingAdapter("detailClick")
fun goToDetail(view: View, foodItem: FoodItem) {
    view.setOnClickListener{
        val intent = Intent(view.context, ItemDetalleActivity::class.java)
        intent.putExtra("FoodItem",foodItem)
        view.context.startActivity(intent)
    }
}