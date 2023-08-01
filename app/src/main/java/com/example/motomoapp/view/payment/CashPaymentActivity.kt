package com.example.motomoapp.view.payment

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.R
import com.example.motomoapp.utils.OrderNotification
import com.example.motomoapp.utils.ReceiverNotification
import com.example.motomoapp.utils.executeOrRequestPermission
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.menu.OrderActivity
import com.example.motomoapp.viewmodels.OrderViewModel
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CashPaymentActivity : AppCompatActivity() {

    private lateinit var newOrderButton: MaterialButton
    private lateinit var pedidoViewModel: PedidoViewModel
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_payment)

        newOrderButton = findViewById(R.id.newOrderButton)

        newOrderButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            executeOrRequestPermission(this@CashPaymentActivity) {
                ReceiverNotification(this@CashPaymentActivity)
            }
            this.startActivity(intent)
        }

        pushOrder()

        executeOrRequestPermission(this@CashPaymentActivity) {
            OrderNotification(this@CashPaymentActivity)
        }

    }

    //push order to the API
    private fun pushOrder() {
        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)
        orderViewModel.pushOrder(pedidoViewModel.serialize())
        pedidoViewModel.clear()
    }

}