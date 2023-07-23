package com.example.motomoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.PedidoItem
import com.example.motomoapp.models.repositories.CarritoRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class PedidoViewModel(val repository: CarritoRepository):ViewModel() {
    private var _items = MutableLiveData<MutableMap<String, PedidoItem>>()
    private var _price = MutableLiveData<Double>()
    private var _totalItems = MutableLiveData<Int>()
    private var _errorMessage = MutableLiveData<String>()

    val items = _items
    val price = _price
    val totalItems = _totalItems
    val errorMessage = _errorMessage

    init {
        updateValues()
    }

    //add one item to the map
    fun addItem(foodItem: FoodItem, amount:Int) {
        try {
            repository.addItem(foodItem, amount)
            updateValues()
        } catch(error: Throwable){
            FirebaseCrashlytics.getInstance().recordException(error)
            errorMessage.postValue(error.message)
        }
    }

    //add one to the amount of an item present in the map
    fun addOne(id:String){
        try {
            repository.addOne(id)
            updateValues()
        } catch(error: Throwable){
            FirebaseCrashlytics.getInstance().recordException(error)
            errorMessage.postValue(error.message)
        }
    }

    //remove one to the amount of an item present in the map
    fun removeOne(id:String) {
        repository.removeOne(id)
        updateValues()
    }

    fun clear(){
        repository.clear()
        updateValues()
    }

    fun getElements(): MutableList<PedidoItem> {
        return repository.getElements()
    }

    fun updateValues(){
        items.postValue(repository.getItems())
        price.postValue(repository.getPrice())
        totalItems.postValue(repository.getTotalItems())
        errorMessage.postValue(null)
    }

}