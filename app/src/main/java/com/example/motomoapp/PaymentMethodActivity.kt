package com.example.motomoapp

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

        paymentFormContainer = findViewById(R.id.paymentFormContainer)
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
        val formLayoutId = when (paymentMethod) {
            "Tarjeta de crédito" -> R.layout.activity_credit_card
            "PayPal" -> R.layout.activity_gift_card
            "Pago en efectivo" -> R.layout.activity_cash
            else -> return
        }

        val inflater = LayoutInflater.from(this)
        val formView = inflater.inflate(formLayoutId, paymentFormContainer, false)

        // Si el formulario ya estaba mostrado, lo reemplazamos
        paymentFormContainer.removeAllViews()
        paymentFormContainer.addView(formView)
    }
}
