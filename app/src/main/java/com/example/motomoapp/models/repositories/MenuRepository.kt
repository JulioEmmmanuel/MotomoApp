package com.example.motomoapp.models.repositories

import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.api.FoodService
import retrofit2.Response
import javax.inject.Inject

class MenuRepository @Inject constructor(private val foodService: FoodService) {

    suspend fun getMainDishes(): Response<List<FoodItem>> {
        return foodService.getMainDishes()
    }

    suspend fun getBeverages(): Response<List<FoodItem>> {
        return foodService.getBeverages()
    }

    suspend fun getRamen(): Response<List<FoodItem>> {
        return foodService.getRamen()
    }

}