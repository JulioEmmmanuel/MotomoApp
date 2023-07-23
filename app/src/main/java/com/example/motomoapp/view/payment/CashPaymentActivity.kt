package com.example.motomoapp.view.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.motomoapp.R
import com.example.motomoapp.utils.OrderNotification
import com.example.motomoapp.utils.ReceiverNotification
import com.example.motomoapp.utils.executeOrRequestPermission
import com.example.motomoapp.view.menu.OrderActivity
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.button.MaterialButton

class CashPaymentActivity : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_payment)

        newOrderButton = findViewById(R.id.newOrderButton)

        newOrderButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            executeOrRequestPermission(this@CashPaymentActivity) {
                ReceiverNotification(this@CashPaymentActivity)
            }
            this.startActivity(intent)
        }

        executeOrRequestPermission(this@CashPaymentActivity) {
            OrderNotification(this@CashPaymentActivity)
        }

    }
}