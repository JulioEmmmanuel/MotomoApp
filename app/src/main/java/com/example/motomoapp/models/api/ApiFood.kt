package com.example.motomoapp.models.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFood {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://tiny-blue-vulture-shoe.cyclic.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val endpoint = retrofit.create(FoodService::class.java)
}