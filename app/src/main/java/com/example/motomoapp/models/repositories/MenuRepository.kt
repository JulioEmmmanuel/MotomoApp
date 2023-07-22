package com.example.motomoapp.models.repositories

import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.api.ApiFood
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class MenuRepository(private val apiFood: ApiFood) {

    suspend fun getBestFoods(): Response<List<FoodItem>> {
        return apiFood.endpoint.getBestFoods()
    }

    suspend fun getDrinks(): Response<List<FoodItem>> {
        return apiFood.endpoint.getDrinks()
    }

    suspend fun getDesserts(): Response<List<FoodItem>> {
        return apiFood.endpoint.getDesserts()
    }

}