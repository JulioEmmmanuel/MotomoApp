package com.example.motomoapp.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
import com.example.motomoapp.R
import com.example.motomoapp.adapters.ViewPagerAdapter
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.api.ApiFood
import com.example.motomoapp.models.repositories.MenuRepository
import com.example.motomoapp.view.menu.GridFragment
import com.example.motomoapp.view.menu.OrderActivity
import com.google.android.material.tabs.TabLayout
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MenuViewModel(private val menuRepository: MenuRepository):ViewModel() {
    var _bestFoods = MutableLiveData<List<FoodItem>>()
    var _drinks = MutableLiveData<List<FoodItem>>()
    var _desserts = MutableLiveData<List<FoodItem>>()
    var _errorMessage = MutableLiveData<String>()

    val bestFoods = _bestFoods
    val drinks = _drinks
    val desserts = _desserts
    val errorMessage = _errorMessage

    init {
        fetchBestFoods()
        fetchDrinks()
        fetchDesserts()
    }

    fun onRefresh() {
        fetchBestFoods()
        fetchDrinks()
        fetchDesserts()
    }

    //obtenemos los datos de la API
    private fun fetchBestFoods() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getBestFoods()
                if (result.isSuccessful) {
                    _bestFoods.postValue(result.body()?.subList(0, 10))
                } else {
                    _errorMessage.postValue("No se pudieron obtener los datos")
                }
            } catch(error: Throwable){
                _errorMessage.postValue(error.message)
            }
        }
    }

    //obtenemos los datos de la API
    private fun fetchDrinks() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getDrinks()
                if (result.isSuccessful) {
                    _drinks.postValue(result.body()?.subList(0, 10))
                } else {
                    _errorMessage.postValue("No se pudieron obtener los datos")
                }
            } catch(error: Throwable){
                _errorMessage.postValue(error.message)
            }
        }
    }

    //obtenemos los datos de la API
    private fun fetchDesserts() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getDesserts()
                if (result.isSuccessful) {
                    _desserts.postValue(result.body()?.subList(0, 10))
                } else {
                    _errorMessage.postValue("No se pudieron obtener los datos")
                }
            } catch(error: Throwable){
                _errorMessage.postValue(error.message)
            }
        }
    }


}