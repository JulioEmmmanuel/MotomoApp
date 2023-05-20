package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val gridFragment = supportFragmentManager.findFragmentById(R.id.fragmentGrid) as GridFragment
    }
}