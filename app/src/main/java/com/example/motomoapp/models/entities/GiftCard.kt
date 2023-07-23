package com.example.motomoapp.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["gift_number"], unique=true)]
)
data class GiftCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "gift_number") val giftNumber: String?,
    @ColumnInfo(name = "gift_amount") val giftAmount: Double?
) {
}