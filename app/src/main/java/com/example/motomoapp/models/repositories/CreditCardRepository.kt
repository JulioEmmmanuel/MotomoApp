package com.example.motomoapp.models.repositories

import androidx.lifecycle.LiveData
import com.example.motomoapp.models.databases.CreditCardDao
import com.example.motomoapp.models.entities.CreditCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreditCardRepository(
    private val creditCardDao: CreditCardDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getCreditCards(): LiveData<List<CreditCard>> = creditCardDao.getCreditCards()

    suspend fun insertCreditCard(creditCard: CreditCard) = withContext(ioDispatcher) {
        creditCardDao.insertCreditCard(creditCard)
    }

}