package com.example.motomoapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.R
import com.google.android.material.navigation.NavigationView
import com.example.motomoapp.databinding.ActivitySelectPaymentMethodBinding
import com.example.motomoapp.models.MyGiftCards
import com.example.motomoapp.view.menu.CartSummaryActivity

class SelectPaymentMethodActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySelectPaymentMethodBinding

    private lateinit var creditCardButton: Button
    private lateinit var giftCardButton: Button
    private lateinit var cashButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPaymentMethodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        creditCardButton = findViewById(R.id.creditCardPaymentButton)
        giftCardButton = findViewById(R.id.giftCardPaymentButton)
        cashButton = findViewById(R.id.cashPaymentButton)
 //       acceptPaymentButton = findViewById(R.id.acceptPaymentButton)

        creditCardButton.setOnClickListener() {
            val intent = Intent(this, MyCreditCards::class.java)
            this.startActivity(intent)
        }

        giftCardButton.setOnClickListener() {
            val intent = Intent(this, MyGiftCards::class.java)
            this.startActivity(intent)
        }

        cashButton.setOnClickListener() {
            val intent = Intent(this, CashPaymentActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }


        binding.navView.setNavigationItemSelectedListener(this)
    }
    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        // Handle navigation view item clicks here.

        when(item.itemId){
            R.id.credit_card -> {
                val intent = Intent(this, MyCreditCards::class.java)
                this.startActivity(intent)
            }
            R.id.giftcard -> {
                val intent = Intent(this, MyGiftCards::class.java)
                this.startActivity(intent)
            }
            R.id.order -> {
                val intent = Intent(this, CartSummaryActivity::class.java)
                this.startActivity(intent)
            }
        }
        return true;
    }
}