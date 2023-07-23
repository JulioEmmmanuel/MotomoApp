package com.example.motomoapp.view.payment

import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityMyCreditCartsBinding
import com.example.motomoapp.view.addpayment.CreditCardActivity
import com.example.motomoapp.view.menu.CartSummaryActivity
import com.google.android.material.navigation.NavigationView

class MyCreditCards : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Variables Globales

    private lateinit var binding: ActivityMyCreditCartsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCreditCartsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        binding.cardItem.selectCard.setOnClickListener {
            val intent = Intent(this, SplashScreenProcessingPayment::class.java)
            this.startActivity(intent)
        }

        binding.btnAgregar.setOnClickListener {
            val intent = Intent(this, CreditCardActivity::class.java)
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