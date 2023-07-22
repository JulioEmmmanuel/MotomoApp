package com.example.motomoapp.models

import android.util.Log

class Carrito {

    companion object Orden {
        private val items = mutableListOf<FoodItem>()
        private val amounts = mutableListOf<Int>()

        private var price = 0
        private var totalItems = 0

        public fun addItem(foodItem: FoodItem, amount:Int){
            val index = items.indexOfFirst {
                it.id == foodItem.id
            }

            if(index == -1){
                items.add(foodItem)
                amounts.add(amount)
            } else {
                amounts[index] += amount;
            }

            price += amount * foodItem.price.substring(1).toInt()
            totalItems += amount

            Log.d("Aviso", "Precio: $price")
            Log.d("Aviso", "Elementos en carrito: $totalItems")
        }

        public fun addOne(id:String){
            val index = items.indexOfFirst {
                it.id == id
            }

            amounts[index]++;

            price += items[index].price.substring(1).toInt()
            totalItems++

            Log.d("Aviso", "Precio: $price")
            Log.d("Aviso", "Elementos en carrito: $totalItems")

        }

        public fun removeOne(id:String){
            val index = items.indexOfFirst {
                it.id == id
            }

            amounts[index]--;

            price -= items[index].price.substring(1).toInt()
            totalItems--

            if(amounts[index] == 0){
                items.removeAt(index)
                amounts.remove(index)
            }

            Log.d("Aviso", "Precio: $price")
            Log.d("Aviso", "Elementos en carrito: $totalItems")

        }

        fun clear(){
            items.clear()
            amounts.clear()
            price = 0
            totalItems = 0
        }

        public fun getItem(index:Int): FoodItem {
            return items[index]
        }

        public fun getItems(): MutableList<CartItem> {

            val cartItems =  items.mapIndexed { index, it ->
                CartItem(
                    it.id,
                    it.name,
                    it.price,
                    (amounts[index] * it.price.substring(1).toInt()).toString(),
                    it.idImage,
                    amounts[index])
            }
            return cartItems.toMutableList()
        }

        public fun getSize():Int{
            return items.size
        }

        public fun getTotalItems():Int{
            return totalItems
        }

        public fun getPrice():Int{
            return price
        }

    }

}