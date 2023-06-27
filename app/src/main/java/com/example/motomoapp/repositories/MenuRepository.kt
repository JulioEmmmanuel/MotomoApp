package com.example.motomoapp.repositories

import com.example.motomoapp.models.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class MenuRepository {

    fun getMenu(): List<FoodItem> {
        val categories = getCategories()
        val menu : MutableList<FoodItem> = mutableListOf()
        if (categories != null) {
            for (category in categories){
                menu.addAll(this[category])
            }
        }
        return menu.toList()
    }

    private fun getCategories(): List<String>? {
        val request =
            Request.Builder().url("https://motomo-api.vercel.app/api/v1/categories").build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body
        // body.toString() returns a string representing the object and not the body itself, probably
        // kotlins fault when using third party libraries. Use byteStream() and convert it to a String
        val content = body!!.bytes()
        val json = String(content)
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    // Let us to override the square brackets operator
    operator fun get(category: String) : MutableList<FoodItem> {
        val request =
            Request.Builder().url("https://motomo-api.vercel.app/api/v1/menu/$category").build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body
        // body.toString() returns a string representing the object and not the body itself, probably
        // kotlins fault when using third party libraries. Use byteStream() and convert it to a String
        val content = body!!.bytes()
        val json = String(content)
        val typeToken = object : TypeToken<MutableList<FoodItem>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

}