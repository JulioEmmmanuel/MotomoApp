package com.example.motomoapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.repositories.MenuRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository
):ViewModel() {
    var _bestFoods = MutableLiveData<List<FoodItem>>()
    var _drinks = MutableLiveData<List<FoodItem>>()
    var _desserts = MutableLiveData<List<FoodItem>>()
    var _errorMessage = MutableLiveData<String>()
    var _showDetail = MutableLiveData<Boolean>()
    var _selectedElement = MutableLiveData<FoodItem>()

    val bestFoods = _bestFoods
    val drinks = _drinks
    val desserts = _desserts
    val errorMessage = _errorMessage
    val showDetail = _showDetail
    val selectedElement = _selectedElement

    init {
        fetchBestFoods()
        fetchDrinks()
        fetchDesserts()
        showDetail.postValue(false)
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
                FirebaseCrashlytics.getInstance().recordException(error)
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
                FirebaseCrashlytics.getInstance().recordException(error)
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
                FirebaseCrashlytics.getInstance().recordException(error)
                _errorMessage.postValue(error.message)
            }
        }
    }

    fun toggleShowDetail(foodItem: FoodItem?){
        if(foodItem != null){
            selectedElement.postValue(foodItem)
            showDetail.postValue(true)
        } else {
            showDetail.postValue(false)
        }
    }


}