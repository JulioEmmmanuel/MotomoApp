package com.example.motomoapp.models.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.motomoapp.models.entities.CreditCard

@Dao
interface CreditCardDao {
    @Insert
    suspend fun insertCreditCard (creditCard: CreditCard)

    @Query("SELECT * FROM CreditCard")
    fun getCreditCards(): LiveData<List<CreditCard>>
}