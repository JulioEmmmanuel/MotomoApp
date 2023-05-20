package com.example.motomoapp.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyorderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aquí va tu orden"
    }
    val text: LiveData<String> = _text
}