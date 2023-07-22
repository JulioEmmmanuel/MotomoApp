package com.example.motomoapp.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.transition.Visibility
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityCashBinding
import com.example.motomoapp.utils.TouchNotification
import com.example.motomoapp.utils.executeOrRequestPermission

class CashActivity: AppCompatActivity() {

    private lateinit var binding:ActivityCashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // transicion al abrir activity
        val transition = Fade(Visibility.MODE_IN).apply {
            duration = 700
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transition

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        binding.btnContinuar.setOnClickListener{
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        executeOrRequestPermission(this@CashActivity) {
            TouchNotification(this@CashActivity)
        }


    }
}