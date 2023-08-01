package com.example.motomoapp.view.options

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motomoapp.R
import com.example.motomoapp.adapters.GiftCardVORecyclerAdapter
import com.example.motomoapp.databinding.ActivityMyGiftCardsBinding
import com.example.motomoapp.view.addpayment.GiftCardActivity
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.menu.CartSummaryActivity
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.example.motomoapp.viewmodels.giftcard.GiftCardListViewModel
import com.google.android.material.navigation.NavigationView

class MyGiftCardsVO: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var binding: ActivityMyGiftCardsBinding
    private lateinit var giftCardViewModel: GiftCardListViewModel
    private lateinit var pedidoViewModel: PedidoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGiftCardsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        giftCardViewModel = GiftCardListViewModel((applicationContext as MotomoApp).giftCardRepository)
        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)

        setUpAppbar()
        setUpButtonListeners()
        setUpRecyclerView()
        setupObserver()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    //setup app bar
    private fun setUpAppbar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }

    //setup button listeners
    private fun setUpButtonListeners(){
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(this, GiftCardActivity::class.java)
            intent.putExtra("from", "mycardsvo")
            this.startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setupObserver(){
        giftCardViewModel.giftCards.observe(this, Observer{
            val adapter = GiftCardVORecyclerAdapter(it, giftCardViewModel)
            binding.recyclerViewCards.adapter = adapter
        })

    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {
        // indicamos que tiene un tamaño fijo
        binding.recyclerViewCards.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        binding.recyclerViewCards.layoutManager = LinearLayoutManager(this)
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
                val intent = Intent(this, MyCreditCardsVO::class.java)
                this.startActivity(intent)
            }
            R.id.giftcard -> {
                val intent = Intent(this, MyGiftCardsVO::class.java)
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