package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class giftCardActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var giftNumber: EditText
    private lateinit var giftAmount: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_card)

        backButton = findViewById(R.id.backButton)
        giftNumber = findViewById(R.id.giftNumber)
        giftAmount = findViewById(R.id.giftQty)
        continueButton = findViewById(R.id.continueButton)

        backButton.setOnClickListener {
            finish()
        }

        continueButton.setOnClickListener {
            val cardClass = giftCardClass(giftNumber.text.toString(),giftAmount.text.toString())
           if( cardClass.isValid() )
            {
                Toast.makeText(this, "Mostrando formulario de pago en efectivo", Toast.LENGTH_SHORT)
                    .show()
                print("Tarjeta de regalo registrada")
            }else{
                print("Error con los datos de tarjeta de regalo")
        }
        }
    }
}