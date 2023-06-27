package com.example.motomoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.adapters.CartRecyclerAdapter
import com.example.motomoapp.databinding.ActivityCartSummaryBinding
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.models.CartItem
import com.example.motomoapp.models.MyGiftCards
import com.google.android.material.navigation.NavigationView

class CartSummaryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityCartSummaryBinding

    private lateinit var adapter: CartRecyclerAdapter
    private lateinit var recyclerItems: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartSummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // transición al iniciar activity
        val transitionIn = Slide(Gravity.RIGHT).apply {
            duration = 700
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transitionIn

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        recyclerItems = findViewById(R.id.recyclerViewCart)
        setUpRecyclerView()

        binding.tvTotal.text = "Total: $${Carrito.getPrice()}"

        if (Carrito.getSize() > 0) {
            binding.btnPagar.visibility = View.VISIBLE
             binding.btnPagar.setOnClickListener {
                val intent = Intent(this, GpsActivity::class.java)



                startActivity(intent)
            }

            binding.btnBack.setOnClickListener {
                finish()
            }
        }

        binding.navView.setNavigationItemSelectedListener(this)
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {
        // indicamos que tiene un tamaño fijo
        recyclerItems.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recyclerItems.layoutManager = LinearLayoutManager(this)
        //seteando el Adapter
        adapter = CartRecyclerAdapter(getItems(), binding.tvTotal)
        //asignando el Adapter al RecyclerView
        recyclerItems.adapter = adapter
    }

    private fun getItems(): MutableList<CartItem> {
        return Carrito.getItems()
    }
    private fun setupDrawer(toolbar: Toolbar) {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }
    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        // Handle navigation view item clicks here.

        when(item.itemId){
            R.id.credit_card -> {
                val intent = Intent(this, MyCreditCards::class.java)
                startActivity(intent)
            }
            R.id.giftcard -> {
                val intent = Intent(this, MyGiftCards::class.java)
                startActivity(intent)
            }
            R.id.order -> {
                val intent = Intent(this, CartSummaryActivity::class.java)
                startActivity(intent)
            }
        }
        return true;
    }
}