package com.example.motomoapp.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreditCardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aquí van tus tarjetas de crédito"
    }
    val text: LiveData<String> = _text
}