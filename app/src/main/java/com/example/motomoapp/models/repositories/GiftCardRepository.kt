package com.example.motomoapp.models.repositories

import androidx.lifecycle.LiveData
import com.example.motomoapp.models.databases.GiftCardDao
import com.example.motomoapp.models.entities.GiftCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GiftCardRepository(
    private val giftCardDao: GiftCardDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getGiftCards(): LiveData<List<GiftCard>> = giftCardDao.getGiftCards()

    suspend fun insertGiftCard(giftCard: GiftCard) = withContext(ioDispatcher) {
        giftCardDao.insertGiftCard(giftCard)
    }
}