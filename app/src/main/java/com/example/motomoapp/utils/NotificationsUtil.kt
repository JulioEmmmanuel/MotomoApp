package com.example.motomoapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.OrderActivity
import com.example.motomoapp.R
import com.example.motomoapp.receivers.NotificationReceiver

val GRUPO_PEDIDO = "GRUPO_PEDIDO"
const val ACTION_RECEIVED = "action_received"

@SuppressLint("MissingPermission")
fun OrderNotification(context: Context){
    with(context) {
        val notification = NotificationCompat.Builder(this, MotomoApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.mottomologo)
            .setColor(getColor(R.color.colorPrimary))
            .setContentTitle(getString(R.string.pedido_title))
            .setContentText(getString(R.string.pedido_string))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroup(GRUPO_PEDIDO)
            .build()

        //lanzamos la notificación
        NotificationManagerCompat
            .from(this)
            .notify(20, notification)
    }
}

@SuppressLint("MissingPermission")
fun TouchNotification(activity: Activity){

    val intent = Intent(activity, OrderActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        activity,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(activity, MotomoApp.CHANNEL_ID)
        .setSmallIcon(R.drawable.mottomologo)
        .setColor(activity.getColor(R.color.colorPrimary))
        .setContentTitle(activity.getString(R.string.action_title))
        .setContentText(activity.getString(R.string.action_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setGroup(GRUPO_PEDIDO)
        .setAutoCancel(true)
        .build()

        //lanzamos la notificación
    NotificationManagerCompat
            .from(activity)
            .notify(30, notification)
}

@SuppressLint("MissingPermission")
fun ReceiverNotification(activity: Activity){

    val acceptIntent = Intent(activity, NotificationReceiver::class.java).apply {
        action = ACTION_RECEIVED
    }

    val acceptPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(activity, 0, acceptIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(activity, MotomoApp.CHANNEL_ID)
        .setSmallIcon(R.drawable.mottomologo)
        .setColor(activity.getColor(R.color.colorPrimary))
        .setContentTitle(activity.getString(R.string.button_title))
        .setContentText(activity.getString(R.string.button_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(R.drawable.mottomologo, activity.getString(R.string.button_text), // agregamos la acción
            acceptPendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(activity)) {
        notify(40, builder.build())
    }

}