package com.example.motomoapp.view.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.motomoapp.R
import com.example.motomoapp.models.api.ApiFood
import com.example.motomoapp.models.api.FoodService
import com.example.motomoapp.models.repositories.CarritoRepository
import com.example.motomoapp.models.repositories.MenuRepository

class MotomoApp:Application() {

    val menuRepository: MenuRepository
        get() = MenuRepository(
            ApiFood
        )

    val carritoRepository = CarritoRepository()

    companion object {
        const val CHANNEL_ID = "Notificaciones pedido"
    }

    override fun onCreate() {
        super.onCreate()
        // Para android Oreo en adelante, s obligatorio registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val name = getString(R.string.main_channel)
        val descriptionText = getString(R.string.main_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}