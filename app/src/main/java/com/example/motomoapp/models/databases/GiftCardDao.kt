package com.example.motomoapp.models.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.motomoapp.models.entities.GiftCard

@Dao
interface GiftCardDao {
    @Insert
    suspend fun insertGiftCard(giftCard: GiftCard)

    @Update
    suspend fun updateGiftCard(giftCard: GiftCard)

    @Delete
    suspend fun removeGiftCard(giftCard: GiftCard)

    @Query("SELECT * FROM GiftCard")
    fun getGiftCards(): LiveData<List<GiftCard>>
}