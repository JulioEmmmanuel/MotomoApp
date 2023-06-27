package com.example.motomoapp

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.databinding.ActivitySplashScreenProcessingPaymentBinding

class SplashScreenProcessingPayment : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenProcessingPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashScreenProcessingPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        spin()
        blinking()

        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreenProcessingPayment, PaymentDone::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)

    }

    //Animación giro
    private fun spin() = binding.processingPaymentImg.animate().rotation(720f).setDuration(3000).start()

    //Animación parpadeo
    private fun blinking() {
        AnimatorInflater.loadAnimator(this, R.animator.blinking).apply{
            setTarget(binding.processingPaymentTxt)
            start()
        }
    }
}
