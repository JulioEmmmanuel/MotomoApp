package com.example.motomoapp

import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.repositories.CarritoRepository
import org.junit.Test

internal class CarritoTest {
    private val cart = CarritoRepository()

    @Test
    fun validate_add_to_cart() {
        val fooditem_item = FoodItem("1", "Sopa Misa", "Nuestra mejor sopa", "50", "1")

        cart.addItem(fooditem_item, 1)

    }

    @Test
    fun validate_clear_cart() {
        cart.clear()

    }

    @Test
    fun remove_from_cart() {
        cart.removeOne("1")

    }

    @Test
    fun validate_cart_items() {
        cart.getItems()
        cart.getTotalItems()
    }

    @Test
    fun serialize_order() {
        assert(cart.serialize() != "")
    }

    @Test
    fun validate_price() {
        cart.getPrice()
    }
}