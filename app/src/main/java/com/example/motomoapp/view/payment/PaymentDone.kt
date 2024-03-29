package com.example.motomoapp.view.payment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityPaymentDoneBinding
import com.example.motomoapp.utils.OrderNotification
import com.example.motomoapp.utils.executeOrRequestPermission
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.menu.OrderActivity
import com.example.motomoapp.viewmodels.OrderViewModel
import com.example.motomoapp.viewmodels.PedidoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentDone : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentDoneBinding
    private lateinit var pedidoViewModel: PedidoViewModel
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fadeIn()

        binding.newOrderButton.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            this.startActivity(intent)
        }

        pushOrder()

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

    //push order to the API
    private fun pushOrder() {
        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)
        orderViewModel.pushOrder(pedidoViewModel.serialize())
        pedidoViewModel.clear()
    }
}
