package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PaymentDone : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_done)

        newOrderButton = findViewById(R.id.newOrderButton)

        newOrderButton.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            this.startActivity(intent)
        }

        Carrito.clear()

    }
}
