package com.example.motomoapp

class giftCardClass(
    val giftNumber: String,
    var giftAmount: Float
    )
{
    //Return true if gift card number and amount are ok
    fun isValid(): Boolean{
        val validGiftNumber = validateGiftNumber(giftNumber)
        val validGiftAmount = validateGiftAmount(giftAmount)

        return validGiftAmount && validGiftNumber
    }
//Valid gift digits number
    private fun validateGiftNumber(giftNumber: String): Boolean{
        if(giftNumber.length == 10) {
            return true
        }
        return false
    }
//Valid gift amount
    private fun validateGiftAmount(giftAmount: Float): Boolean{
        if(giftAmount.toFloat() > 0.0){
            return true
        }
        return false
    }
}