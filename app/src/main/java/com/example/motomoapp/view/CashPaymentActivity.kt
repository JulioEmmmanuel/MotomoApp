package com.example.motomoapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.R
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.utils.OrderNotification
import com.example.motomoapp.utils.ReceiverNotification
import com.example.motomoapp.utils.executeOrRequestPermission
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

        Carrito.clear()
    }
}