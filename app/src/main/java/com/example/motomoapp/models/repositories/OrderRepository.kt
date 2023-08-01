package com.example.motomoapp.models.repositories

import com.example.motomoapp.models.api.FoodService
import com.example.motomoapp.models.api.OrderService
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderService: OrderService) {
    suspend fun pushOrder(order: String) {
        orderService.pushOrder(order)
    }
}