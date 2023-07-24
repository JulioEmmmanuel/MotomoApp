package com.example.motomoapp.viewmodels.creditcard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motomoapp.models.entities.CreditCard
import com.example.motomoapp.models.repositories.CreditCardRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.conekta.conektasdk.Card
import io.conekta.conektasdk.Token
import kotlinx.coroutines.launch

class AddCreditCardViewModel(
    private val creditCardRepository: CreditCardRepository
): ViewModel() {
    var cardNumber: String? = null
    var cardName: String? = null
    var monthExp: String? = null
    var yearExp: String? = null
    var cvv: String? = null

    var _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage
    val _added = MutableLiveData<Boolean>()
    val added = _added

    init {
        added.postValue(false)
    }

    fun setCardNumber(s: CharSequence, start:Int, before: Int, count:Int) {
        cardNumber = s.toString()
    }

    fun setCardName(s: CharSequence, start:Int, before: Int, count:Int) {
        cardName = s.toString()
    }

    fun setMonthExp(s: CharSequence, start:Int, before: Int, count:Int) {
        monthExp = s.toString()
    }

    fun setYearExp(s: CharSequence, start:Int, before: Int, count:Int) {
        yearExp = s.toString()
    }

    fun setCvv(s: CharSequence, start:Int, before: Int, count:Int) {
        cvv = s.toString()
    }

    fun validateData():Boolean {
        if(cardNumber.isNullOrBlank() || cardName.isNullOrBlank() || yearExp.isNullOrBlank() || monthExp.isNullOrBlank() || cvv.isNullOrBlank()){
            errorMessage.postValue("Favor de llenar todos los campos")
            return false
        }

        if(cardNumber!!.length != 16){
            errorMessage.postValue("El número de la tarjeta debe tener 16 dígitos")
            return false
        }

        if(monthExp!!.length != 2 || yearExp!!.length != 4){
            errorMessage.postValue("El formato de la fecha de expiración es incorrecto")
            return false
        }

        if(cvv!!.length != 3){
            errorMessage.postValue("El cvv debe tener 3 dígitos")
            return false
        }

        return true
    }

    //register card using Conekta token
    fun createCard(token: Token) = viewModelScope.launch {
        if(!validateData()){
            return@launch
        }

        val card = Card(cardName!!, cardNumber!!, cvv!!, monthExp!!, yearExp!!)
        token.onCreateTokenListener {
            if(it.has("id")){
                val creditCard = CreditCard(cardNumber=cardNumber!!, cardToken = it.getString("id"))
                addCard(creditCard)
            } else {
                errorMessage.postValue(it.getString("message"))
            }
        }
        token.create(card)
    }

    private fun addCard(creditCard: CreditCard) = viewModelScope.launch {
        try {
            creditCardRepository.insertCreditCard(creditCard)
            added.postValue(true)
        }  catch(error: Throwable){
            FirebaseCrashlytics.getInstance().recordException(error)
            errorMessage.postValue("Error al agregar la tarjeta")
        }
    }



}