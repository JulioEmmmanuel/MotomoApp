package com.example.motomoapp.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.motomoapp.NotificationApp
import com.example.motomoapp.R

@SuppressLint("MissingPermission")
fun OrderNotification(context: Context){
    with(context) {
        val builder = NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.mottomologo)
            .setColor(getColor(R.color.colorPrimary))
            .setContentTitle(getString(R.string.pedido_title))
            .setContentText(getString(R.string.pedido_string))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //lanzamos la notificación
        NotificationManagerCompat
            .from(this)
            .notify(20, builder.build())
    }
}