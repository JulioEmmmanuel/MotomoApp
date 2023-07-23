package com.example.motomoapp.models

import com.google.gson.annotations.SerializedName

data class PedidoItem (
    val id: String,
    val name: String,
    var unitaryPrice: Double,
    var amount: Int,
    val idImage: String
)