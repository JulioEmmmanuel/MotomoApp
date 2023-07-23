package com.example.motomoapp.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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