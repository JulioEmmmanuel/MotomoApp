package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenProcessingPayment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_processing_payment)

        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreenProcessingPayment, PaymentDone::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)

    }
}