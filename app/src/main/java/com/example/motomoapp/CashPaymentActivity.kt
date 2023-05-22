package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class CashPaymentActivity : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_payment)

        newOrderButton = findViewById(R.id.newOrderButton)

        newOrderButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            this.startActivity(intent)
        }

        Carrito.clear()
    }
}