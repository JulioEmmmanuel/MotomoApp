package com.example.motomoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class GiftCardActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var giftNumber: EditText
    private lateinit var giftAmount: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_card)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        backButton = findViewById(R.id.btnBack)
        giftNumber = findViewById(R.id.tiGiftNumber)
        giftAmount = findViewById(R.id.tiMonto)
        continueButton = findViewById(R.id.btnAgregar)

        backButton.setOnClickListener {
            finish()
        }

        continueButton.setOnClickListener {
            if( giftAmount.text.isEmpty() || giftNumber.text.isEmpty() ){
                Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val cardClass = GiftCard(
                giftNumber.text.toString(),
                giftAmount.text.toString().toFloat()
            )
            if( cardClass.isValid() ) {
                Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, CashActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error con los datos de la tarjeta", Toast.LENGTH_SHORT)
                    .show()            }
        }
    }
}