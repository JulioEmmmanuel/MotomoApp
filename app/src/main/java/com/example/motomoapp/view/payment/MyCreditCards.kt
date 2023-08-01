package com.example.motomoapp.view.payment

import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motomoapp.R
import com.example.motomoapp.adapters.CreditCardRecyclerAdapter
import com.example.motomoapp.databinding.ActivityMyCreditCartsBinding
import com.example.motomoapp.view.addpayment.CreditCardActivity
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.menu.CartSummaryActivity
import com.example.motomoapp.view.options.MyCreditCardsVO
import com.example.motomoapp.view.options.MyGiftCardsVO
import com.example.motomoapp.viewmodels.creditcard.CreditCardListViewModel
import com.google.android.material.navigation.NavigationView

class MyCreditCards : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Variables Globales

    private lateinit var binding: ActivityMyCreditCartsBinding
    private lateinit var creditCardViewModel: CreditCardListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCreditCartsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        creditCardViewModel = CreditCardListViewModel((applicationContext as MotomoApp).creditCardRepository)


        setUpAppbar()
        setUpRecyclerView()
        setupButtonListeners()
        setupObservers()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setupButtonListeners(){
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(this, CreditCardActivity::class.java)
            intent.putExtra("from", "mycards")
            this.startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setupObservers(){
        creditCardViewModel.creditCards.observe(this, Observer{
            val adapter = CreditCardRecyclerAdapter(it, creditCardViewModel)
            binding.recyclerViewCards.adapter = adapter
        })

        creditCardViewModel.updated.observe(this, Observer {
            if(it) {
                val i = Intent(this, SplashScreenProcessingPayment::class.java)
                startActivity(i)
            }
        })
    }

    //setup app bar
    private fun setUpAppbar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }


    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView() {
        // indicamos que tiene un tamaño fijo
        binding.recyclerViewCards.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        binding.recyclerViewCards.layoutManager = LinearLayoutManager(this)
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