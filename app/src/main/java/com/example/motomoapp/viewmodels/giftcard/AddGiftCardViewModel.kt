package com.example.motomoapp.viewmodels.giftcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.entities.GiftCard
import com.example.motomoapp.models.repositories.CreditCardRepository
import com.example.motomoapp.models.repositories.GiftCardRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class AddGiftCardViewModel(
    private val giftCardRepository: GiftCardRepository
): ViewModel() {
    var cardNumber: String? = null
    var cardAmount: String? = null

    var _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage
    val _added = MutableLiveData<Boolean>()
    val added = _added

    init {
        added.postValue(false)
    }

    fun addCard() = viewModelScope.launch {
        if(cardNumber.isNullOrBlank() || cardAmount.isNullOrBlank()){
            errorMessage.postValue("Favor de llenar todos los campos")
            return@launch
        }

        val amount = cardAmount!!.toDouble()

        if(cardNumber!!.length != 10){
            errorMessage.postValue("El número de la tarjeta debe tener 10 dígitos")
            return@launch
        }

        if(amount <= 0.0){
            errorMessage.postValue("El monto de la tarjeta debe ser mayor a 0")
            return@launch
        }

        try {
            giftCardRepository.insertGiftCard(
                GiftCard(
                    giftNumber = cardNumber,
                    giftAmount = amount
                )
            )
            added.postValue(true)
        } catch(error: Throwable){
            FirebaseCrashlytics.getInstance().recordException(error)
            errorMessage.postValue("No se pudo agregar la tarjeta, revisa que no la hayas agregado ya previamente")
        }
    }

    fun setCardNumber(s: CharSequence, start:Int, before: Int, count:Int) {
        cardNumber = s.toString()
    }

    fun setCardAmount(s: CharSequence, start:Int, before: Int, count:Int) {
        cardAmount = s.toString()
    }

}