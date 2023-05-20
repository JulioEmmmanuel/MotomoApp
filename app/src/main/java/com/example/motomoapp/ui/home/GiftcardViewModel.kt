package com.example.motomoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GiftcardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aquí van tus tarjetas de regalo disponibles"
    }
    val text: LiveData<String> = _text
}