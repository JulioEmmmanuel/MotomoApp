package com.example.motomoapp.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["card_number"], unique=true)]
)
data class CreditCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "card_number") val cardNumber: String?,
    @ColumnInfo(name = "card_token") val cardToken: String?,
) {
}