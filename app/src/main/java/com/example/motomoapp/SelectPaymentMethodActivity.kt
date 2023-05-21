package com.example.motomoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

class SelectPaymentMethodActivity : AppCompatActivity() {

    private lateinit var creditCardButton: Button
    private lateinit var giftCardButton: Button
    private lateinit var cashButton: Button
    // private lateinit var acceptPaymentButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_payment_method)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        creditCardButton = findViewById(R.id.creditCardPaymentButton)
        giftCardButton = findViewById(R.id.giftCardPaymentButton)
        cashButton = findViewById(R.id.cashPaymentButton)
 //       acceptPaymentButton = findViewById(R.id.acceptPaymentButton)

        creditCardButton.setOnClickListener() {
            val intent = Intent(this, SplashScreenProcessingPayment::class.java)
            this.startActivity(intent)
        }

        giftCardButton.setOnClickListener() {
            val intent = Intent(this, SplashScreenProcessingPayment::class.java)
            this.startActivity(intent)
        }

        cashButton.setOnClickListener() {
            val intent = Intent(this, SplashScreenProcessingPayment::class.java)
            this.startActivity(intent)
        }
    }
    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }
}