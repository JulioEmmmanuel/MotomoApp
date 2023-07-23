package com.example.motomoapp.viewmodels.giftcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.entities.GiftCard
import com.example.motomoapp.models.repositories.GiftCardRepository
import kotlinx.coroutines.launch

class GiftCardListViewModel(
    private val giftCardRepository: GiftCardRepository
):ViewModel() {
    val _giftCards = giftCardRepository.getGiftCards()
    val giftCards = _giftCards

    private var payAmount: Double = 0.0
    var _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage
    val _updated = MutableLiveData<Boolean>()
    val updated = _updated

    init {
        updated.postValue(false)
    }

    fun setPayAmount(amount: Double){
        payAmount = amount
    }

    fun processPayment(giftCard: GiftCard) = viewModelScope.launch {
        if(payAmount <= giftCard.giftAmount!!){
            val newCard = GiftCard(giftCard.id, giftCard.giftNumber, giftCard.giftAmount - payAmount)
            giftCardRepository.updateGiftCard(newCard)
            updated.postValue(true)
        } else {
            errorMessage.postValue("Los fondos de esta tarjeta son insuficientes.")
        }
    }
}