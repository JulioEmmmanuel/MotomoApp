package com.example.motomoapp.view.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityPaymentDoneBinding
import com.example.motomoapp.utils.OrderNotification
import com.example.motomoapp.utils.executeOrRequestPermission
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.view.menu.OrderActivity
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.button.MaterialButton

class PaymentDone : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton
    private lateinit var binding: ActivityPaymentDoneBinding
    private lateinit var pedidoViewModel: PedidoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fadeIn()

        binding.newOrderButton.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            this.startActivity(intent)
        }

        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)
        pedidoViewModel.clear()

        executeOrRequestPermission(this@PaymentDone) {
            OrderNotification(this@PaymentDone)
            executeOrRequestPermission(this@PaymentDone) {
                OrderNotification(this@PaymentDone)
            }
        }

    }

    // Animación Fade in
    private fun fadeIn() {
        binding.newOrderButton.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.newOrderButton.startAnimation(animation)

    }
}