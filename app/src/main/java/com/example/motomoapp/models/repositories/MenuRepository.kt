package com.example.motomoapp.models.repositories

import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.api.FoodService
import retrofit2.Response
import javax.inject.Inject

class MenuRepository @Inject constructor(private val foodService: FoodService) {

    suspend fun getBestFoods(): Response<List<FoodItem>> {
        return foodService.getBestFoods()
    }

    suspend fun getDrinks(): Response<List<FoodItem>> {
        return foodService.getDrinks()
    }

    suspend fun getDesserts(): Response<List<FoodItem>> {
        return foodService.getDesserts()
    }

}