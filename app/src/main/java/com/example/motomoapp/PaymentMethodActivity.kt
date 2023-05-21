package com.example.motomoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class PaymentMethodActivity : AppCompatActivity() {

    private val paymentMethods = arrayOf("Pago con tarjeta", "Tarjeta de regalo", "Efectivo")
    private lateinit var listView: ListView
    private lateinit var paymentFormContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        val listView = findViewById<ListView>(R.id.paymentListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethods)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedMethod = paymentMethods[position]
                showPaymentForm(selectedMethod)
            }
    }

    private fun showPaymentForm(paymentMethod: String) {
        when (paymentMethod) {
            "Pago con tarjeta" -> {
                val intent = Intent(this, CreditCardActivity::class.java)
                startActivity(intent)
            }
            "Tarjeta de regalo" -> {
                val intent = Intent(this, GiftCardActivity::class.java)
                startActivity(intent)
            }
            "Efectivo" -> {
                val intent = Intent(this, CashActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
