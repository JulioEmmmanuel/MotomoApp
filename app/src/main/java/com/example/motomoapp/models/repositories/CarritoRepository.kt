package com.example.motomoapp.models.repositories

import androidx.lifecycle.MutableLiveData
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.PedidoItem

class CarritoRepository() {
    private var items = mutableMapOf<String, PedidoItem>()
    private var price = 0.0
    private var totalItems = 0

    companion object {
        val MAX_AMOUNT = 10
    }


    fun getItems():MutableMap<String, PedidoItem>{
        return items
    }

    fun getPrice():Double{
        return price
    }

    fun getTotalItems():Int{
        return totalItems
    }

    //add one item to the map
    fun addItem(foodItem: FoodItem, amount:Int) {
        if (!items.containsKey(foodItem.id)) {
            val item = PedidoItem(
                foodItem.id,
                foodItem.name,
                foodItem.price.toDouble(),
                amount,
                foodItem.idImage
            )
            items[foodItem.id] = item
        } else {
            if(items[foodItem.id]!!.amount + amount > MAX_AMOUNT){
                throw Error("No puedes agregar más de $MAX_AMOUNT de este alimento")
            }
            items[foodItem.id]!!.amount += amount
        }
        val newPrice = price + amount * (items[foodItem.id]?.unitaryPrice ?: 0.0)
        price = newPrice
        totalItems += amount
    }

    //add one to the amount of an item present in the map
    fun addOne(id:String){
        if(items[id]!!.amount + 1 > MAX_AMOUNT){
            throw Error("No puedes agregar más de $MAX_AMOUNT de este alimento")
        }
        items[id]!!.amount++
        price += (items[id]?.unitaryPrice ?: 0.0)
        totalItems++
    }

    //remove one to the amount of an item present in the map
    fun removeOne(id:String) {
        if(items.containsKey(id)) {
            items[id]!!.amount--
            price -= (items[id]?.unitaryPrice ?: 0.0)
            totalItems--
            if (items[id]?.amount == 0) {
                items.remove(id)
            }
        }

    }

    fun clear(){
        items.clear()
        price = 0.0
        totalItems = 0
    }

    fun getElements(): MutableList<PedidoItem> {
        return items.values.toMutableList()
    }


}