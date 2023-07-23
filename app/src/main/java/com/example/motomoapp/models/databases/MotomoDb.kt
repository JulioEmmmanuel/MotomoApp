package com.example.motomoapp.models.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.motomoapp.models.entities.GiftCard

@Database(entities = [GiftCard::class], version = 1)
abstract class MotomoDb: RoomDatabase() {

    abstract fun giftCardDao(): GiftCardDao

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
                ).build()
                dbInstance = instance

                instance
            }
        }
    }
}