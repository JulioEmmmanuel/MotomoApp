package com.example.motomoapp.models.api

import com.example.motomoapp.models.FoodItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface FoodService {
    @GET("best-foods")
    suspend fun getBestFoods(): Response<List<FoodItem>>

    @GET("drinks")
    suspend fun getDrinks(): Response<List<FoodItem>>

    @GET("desserts")
    suspend fun getDesserts(): Response<List<FoodItem>>
}