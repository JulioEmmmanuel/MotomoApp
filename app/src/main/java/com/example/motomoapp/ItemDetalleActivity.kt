package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.motomoapp.databinding.ActivityItemDetalleBinding

class ItemDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetalleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val foodItem = intent.getParcelableExtra<FoodItem>("FoodItem")
        if(foodItem != null){
            binding.tvHeader.text = foodItem.name;
            binding.tvDescription.text = foodItem.description;
            binding.tvPrice.text = foodItem.price;
            binding.imgDescripcion.setImageResource(foodItem.idImage);
        }
    }
}