package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.databinding.ActivityPaymentDoneBinding
import com.google.android.material.button.MaterialButton

class PaymentDone : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton
    private lateinit var binding: ActivityPaymentDoneBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fadeIn()

        binding.newOrderButton.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            this.startActivity(intent)
        }

        Carrito.clear()

    }

    // Animación Fade in
    private fun fadeIn() {

        binding.newOrderButton.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.newOrderButton.startAnimation(animation)

    }
}
