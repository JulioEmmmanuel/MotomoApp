package com.example.motomoapp

class creditCardClass (
    var cardNumber: String,
    var expiryMonth: Int,
    var expiryYear: String,
    var amountCard: Float,
    var limitCard: Float
)
{

    //Return true if credit card number and amount are ok
    fun isValid(): Boolean{
        val validCardNumber = validateCardNumber(cardNumber)
        val validAmountCard = validateCardAmount(amountCard)
        val validExpMonth = validateExpMonth(expiryMonth)
        val validExpYear = validateExpYear(expiryYear)
        val validLimit = validateLimitCard(limitCard)

        return validCardNumber && validAmountCard && validExpMonth && validExpYear && validLimit
    }

    private fun validateCardNumber(cardNumber: String): Boolean {
        if(cardNumber.length == 16){
            return true
        }
        return false
    }

    private fun validateCardAmount(amountCard: Float): Any {
        if(amountCard > 0.0){
            return true
        }
        return false
    }

    private fun validateExpMonth(expiryMonth: Int): Boolean {
        if(expiryMonth >= 1 && expiryMonth <=12){
            return true
        }
        return false
    }

    private fun validateExpYear(expiryYear: String): Boolean {
        if(expiryYear.length == 4){
            return true
        }
        return false
    }

    private fun validateLimitCard(limitCard: Float): Any {
        if(limitCard.toFloat() > 0.0){
            return true
        }
        return false
    }

}