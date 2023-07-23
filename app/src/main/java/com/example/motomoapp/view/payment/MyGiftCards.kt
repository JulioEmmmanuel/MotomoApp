package com.example.motomoapp.view.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motomoapp.view.menu.CartSummaryActivity
import com.example.motomoapp.view.addpayment.GiftCardActivity
import com.example.motomoapp.R
import com.example.motomoapp.adapters.GiftCardRecyclerAdapter
import com.example.motomoapp.databinding.ActivityMyGiftCardsBinding
import com.example.motomoapp.models.entities.GiftCard
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.viewmodels.GiftCardListViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyGiftCards : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMyGiftCardsBinding
    private lateinit var giftCardViewModel: GiftCardListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGiftCardsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        giftCardViewModel = GiftCardListViewModel(
            (applicationContext as MotomoApp).giftCardRepository
        )

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
            this.startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setupObserver(){
        giftCardViewModel.giftCards.observe(this, Observer{
            val adapter = GiftCardRecyclerAdapter(it)
            binding.recyclerViewCards.adapter = adapter
        })
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {
        // indicamos que tiene un tamaño fijo
        binding.recyclerViewCards.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        binding.recyclerViewCards.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCards.adapter =
            giftCardViewModel.giftCards.value?.let { GiftCardRecyclerAdapter(it) }
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