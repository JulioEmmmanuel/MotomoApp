package com.example.motomoapp.viewmodels.creditcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.entities.CreditCard
import com.example.motomoapp.models.repositories.CreditCardRepository
import kotlinx.coroutines.launch

class CreditCardListViewModel(
    private val creditCardRepository: CreditCardRepository
): ViewModel() {
    val _creditCards = creditCardRepository.getCreditCards()
    val creditCards = _creditCards

    val _updated = MutableLiveData<Boolean>()
    val updated = _updated

    init {
        updated.postValue(false)
    }

    fun processPayment() = viewModelScope.launch {
        updated.postValue(true)
    }
}