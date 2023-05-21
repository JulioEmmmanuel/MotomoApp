package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.motomoapp.databinding.ActivityCashBinding

class CashActivity: AppCompatActivity() {

    private lateinit var binding:ActivityCashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        binding.btnContinuar.setOnClickListener{
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
    }
}