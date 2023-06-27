package com.example.motomoapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.motomoapp.MainActivity
import com.example.motomoapp.MenuInicioActivity
import com.example.motomoapp.utils.ACTION_RECEIVED


class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == ACTION_RECEIVED){
            val i = Intent(context, MenuInicioActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context!!.startActivity(i)
        }
    }
}