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
    val menuRepository: MenuRepository
):ViewModel() {
    var _mainDishes = MutableLiveData<List<FoodItem>>()
    var _beverages = MutableLiveData<List<FoodItem>>()
    var _ramen = MutableLiveData<List<FoodItem>>()
    var _errorMessage = MutableLiveData<String>()
    var _showDetail = MutableLiveData<Boolean>()
    var _selectedElement = MutableLiveData<FoodItem>()

    val mainDishes = _mainDishes
    val beverages = _beverages
    val ramen = _ramen
    val errorMessage = _errorMessage
    val showDetail = _showDetail
    val selectedElement = _selectedElement

    init {
        fetchMainDishes()
        fetchRamen()
        fetchBeverages()
        showDetail.postValue(false)
    }

    fun onRefresh() {
        fetchMainDishes()
        fetchRamen()
        fetchBeverages()
    }

    //obtenemos los datos de la API
    private fun fetchMainDishes() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getMainDishes()
                if (result.isSuccessful) {
                    var list = result.body()
                    if (list?.size!! > 10) {
                        list = list.subList(0, 10)
                    }
                    _mainDishes.postValue(list!!)
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
    private fun fetchBeverages() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getBeverages()
                if (result.isSuccessful) {
                    var list = result.body()
                    if (list?.size!! > 10) {
                        list = list.subList(0, 10)
                    }
                    _beverages.postValue(list!!)
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
    private fun fetchRamen() {
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        viewModelScope.launch {
            try {
                val result = menuRepository.getRamen()
                if (result.isSuccessful) {
                    var list = result.body()
                    if (list?.size!! > 10) {
                        list = list.subList(0, 10)
                    }
                    _ramen.postValue(list!!)
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