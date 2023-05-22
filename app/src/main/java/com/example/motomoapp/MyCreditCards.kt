package com.example.motomoapp

import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.motomoapp.databinding.ActivityMyCreditCartsBinding
import com.google.android.material.navigation.NavigationView

class MyCreditCards : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Variables Globales

    private lateinit var binding: ActivityMyCreditCartsBinding
    private lateinit var creditCardFragment: myCreditCardsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCreditCartsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        creditCardFragment = myCreditCardsFragment()
        binding.cardItem.selectCard.setOnClickListener {
            val intent = Intent(this, SplashScreenProcessingPayment::class.java)
            this.startActivity(intent)
        }
        binding.navView.setNavigationItemSelectedListener(this)
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