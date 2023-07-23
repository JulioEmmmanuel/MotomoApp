package com.example.motomoapp.view.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.R
import com.example.motomoapp.adapters.CartRecyclerAdapter
import com.example.motomoapp.databinding.ActivityCartSummaryBinding
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.view.gps.GpsActivity
import com.example.motomoapp.view.payment.MyCreditCards
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.navigation.NavigationView
import es.dmoral.toasty.Toasty

class CartSummaryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityCartSummaryBinding
    private lateinit var pedidoViewModel: PedidoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartSummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        transitionIn()

        setUpAppbar()

        setUpRecyclerView()

        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)

        setObservers()

        binding.navView.setNavigationItemSelectedListener(this)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpAppbar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }

    //make transition
    private fun transitionIn(){
        // transición al iniciar activity
        val transition = Slide(Gravity.END).apply {
            duration = 700
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transition
    }

    //set up observers with the viewModel
    private fun setObservers(){
        pedidoViewModel.price.observe(this, Observer {
            binding.tvTotal.text = "Total: $${String.format("%.2f", it)}"
        })

        pedidoViewModel.items.observe(this, Observer {
            if(it.isNotEmpty()){
                binding.btnPagar.visibility = View.VISIBLE
                binding.btnPagar.setOnClickListener {
                    val intent = Intent(this, GpsActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.recyclerViewCart.adapter = CartRecyclerAdapter(pedidoViewModel.getElements(), pedidoViewModel)
        })

        pedidoViewModel.errorMessage.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                Toasty.error(this, it, Toast.LENGTH_SHORT, true).show()
            }
        })

    }


    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {
        // indicamos que tiene un tamaño fijo
        binding.recyclerViewCart.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
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