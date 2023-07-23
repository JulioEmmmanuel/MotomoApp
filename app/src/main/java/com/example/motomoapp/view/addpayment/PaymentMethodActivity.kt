package com.example.motomoapp.view.addpayment

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.R

class PaymentMethodActivity : AppCompatActivity() {
//set de los elementos de pago
    private val paymentMethods = arrayOf("Pago con tarjeta", "Tarjeta de regalo", "Efectivo")
    private lateinit var listView: ListView
    private lateinit var paymentFormContainer: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        // transición al iniciar activity
        val transitionIn = Slide(Gravity.BOTTOM).apply {
            duration = 600
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transitionIn


        // transición al salir del activity
        val transitionOut = Slide(Gravity.BOTTOM).apply {
            duration = 700
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.exitTransition = transitionOut

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
//encontrar el id de List View y usar el adapter para colocar los elementos de la lista
        val listView = findViewById<ListView>(R.id.paymentListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethods)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedMethod = paymentMethods[position]
                showPaymentForm(selectedMethod)
            }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }
//muestra el metodo de pago dependiendo de la seleccion de la lista
    private fun showPaymentForm(paymentMethod: String) {
        when (paymentMethod) {
            "Pago con tarjeta" -> {
                val intent = Intent(this, CreditCardActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            "Tarjeta de regalo" -> {
                val intent = Intent(this, GiftCardActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            "Efectivo" -> {
                val intent = Intent(this, CashActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }
}
