package com.example.motomoapp.models.api

import com.example.motomoapp.models.FoodItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FoodService {
    @GET("api/v1/menu/Main")
    suspend fun getMainDishes(): Response<List<FoodItem>>

    @GET("api/v1/menu/Beverages")
    suspend fun getBeverages(): Response<List<FoodItem>>

    @GET("api/v1/menu/Ramen")
    suspend fun getRamen(): Response<List<FoodItem>>
}