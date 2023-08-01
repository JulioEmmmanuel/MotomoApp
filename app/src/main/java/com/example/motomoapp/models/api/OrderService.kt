package com.example.motomoapp.models.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OrderService {
    @POST("api/v1/orders")
    @Headers("Content-Type: application/json")
    suspend fun pushOrder(@Body order: String)
}