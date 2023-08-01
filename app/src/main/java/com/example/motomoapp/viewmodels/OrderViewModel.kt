package com.example.motomoapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(val orderRepository: OrderRepository): ViewModel() {
    fun pushOrder(json: String?){
        viewModelScope.launch {
            if(json != null){
                orderRepository.pushOrder(json)
            }
        }
    }
}