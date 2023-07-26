package com.example.motomoapp.models.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.motomoapp.models.entities.CreditCard
import com.example.motomoapp.models.entities.GiftCard

@Database(entities = [GiftCard::class, CreditCard::class], version = 2)
abstract class MotomoDb: RoomDatabase() {

    abstract fun giftCardDao(): GiftCardDao
    abstract fun creditCardDao(): CreditCardDao

    companion object {
        @Volatile
        private var dbInstance: MotomoDb? = null

        private const val DB_NAME = "motomo_db"

        fun getInstance(context: Context) : MotomoDb {
            return dbInstance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MotomoDb::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                dbInstance = instance

                instance
            }
        }
    }
}