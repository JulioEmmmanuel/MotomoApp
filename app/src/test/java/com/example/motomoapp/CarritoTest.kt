package com.example.motomoapp

import androidx.core.view.ViewCompat.NestedScrollType
import com.example.motomoapp.Carrito.Orden.addItem
import com.example.motomoapp.Carrito.Orden.getItems
import com.example.motomoapp.Carrito.Orden.getTotalItems
import com.example.motomoapp.Carrito.Orden.removeOne
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class CarritoTest{
    @Test
    fun validate_add_to_cart(){
        val fooditem_test=FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", 1)

        val item_added= addItem(fooditem_test,1)

        assertThat(item_added).isEqualTo(1)
    }

    @Test
    fun validate_remove_from_cart(){
        val fooditem_test= listOf(FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", 1),
        FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", 2))

        val item_added= removeOne(1)

        assertThat(item_added).isEqualTo(1)
    }

    @Test
    fun validate_cart_items(){

        val fooditem_test= listOf(CartItem(1, "Sopa Misa", "$70.0", "$140.0", 1,2),
            CartItem(1, "CocaCola", "$30.0", "$30.0", 2,1),
            CartItem(1, "Sprite", "$20.0", "$40.0", 2,2)
        )
        getItems()
        getTotalItems()
    }
}