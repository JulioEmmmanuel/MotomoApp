package com.example.motomoapp.models

data class CartItem (
    val id:String,
    val name: String,
    val precio: String,
    val subtotal: String,
    val idImagen: String,
    val cantidad: Int
)