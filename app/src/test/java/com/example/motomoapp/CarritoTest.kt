package com.example.motomoapp

import androidx.annotation.DisplayContext
import androidx.core.view.ViewCompat.NestedScrollType
import com.example.motomoapp.Carrito.Orden.addItem
import com.example.motomoapp.Carrito.Orden.clear
import com.example.motomoapp.Carrito.Orden.getItems
import com.example.motomoapp.Carrito.Orden.getTotalItems
import com.example.motomoapp.Carrito.Orden.removeOne
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class CarritoTest{
    val foodItem_list= listOf(
        FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", 1),
        FoodItem(2, "coca cola", "600 ml", "$30", 2),
                FoodItem(2, "Sprite", "600 ml", "$30", 2)
    )
    @Test
    fun validate_add_to_cart(){
        val fooditem_item=FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", 1)

        val foodItem_list = addItem(fooditem_item,1)

    }

    @Test
    fun validate_clear_cart(){
        clear()

    }

    @Test
    fun remove_from_cart(){
        removeOne(1)

    }

    @Test
    fun validate_cart_items(){
        getItems()
        getTotalItems()
    }
}