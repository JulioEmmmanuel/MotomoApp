package com.example.motomoapp.viewmodels

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
}