package com.example.motomoapp.models

data class CartItem (
    val id:Int,
    val name: String,
    val precio: String,
    val subtotal: String,
    val idImagen: Int,
    val cantidad: Int
)