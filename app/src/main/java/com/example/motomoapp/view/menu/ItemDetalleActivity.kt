package com.example.motomoapp.view.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityItemDetalleBinding
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.models.repositories.CarritoRepository
import com.example.motomoapp.view.payment.MyCreditCards
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.navigation.NavigationView
import es.dmoral.toasty.Toasty

class ItemDetalleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityItemDetalleBinding
    private var cantidad:Int = 1
    private lateinit var foodItem:FoodItem
    private lateinit var pedidoViewModel: PedidoViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetalleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpAppbar()

        pedidoViewModel = PedidoViewModel((applicationContext as MotomoApp).carritoRepository)

        updateUI()
        setUpObservers()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    //updateUI with selected food item
    private fun updateUI(){
        foodItem = intent.getParcelableExtra<FoodItem>("FoodItem")!!
        foodItem.url = intent.getStringExtra("url")!!
        binding.foodItem = foodItem
        binding.amount = "1"
        binding.price = "$${String.format("%.2f", foodItem.price.toDouble())}"

        binding.btnMas.setOnClickListener{
            if(cantidad < CarritoRepository.MAX_AMOUNT){
                cantidad++;
                binding.tvCantidad.text = cantidad.toString()
                binding.btnAgregar.text = "Agregar $cantidad al carrito"
            } else {
                Toasty.error(this, "No puedes agregar más de 10 veces este alimento", Toast.LENGTH_SHORT, true).show()
            }
        }

        binding.btnMenos.setOnClickListener{
            if(cantidad > 1){
                cantidad--;
                binding.tvCantidad.text = cantidad.toString()
                binding.btnAgregar.text = "Agregar $cantidad al carrito"
            } else {
                Toasty.error(this, "No puedes agregar menos de 1 vez este alimento", Toast.LENGTH_SHORT, true).show()
            }
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
        binding.btnAgregar.setOnClickListener(){
            if(foodItem != null) {
                pedidoViewModel.addItem(foodItem!!, cantidad)
                finish()
            }
        }

    }

    private fun setUpObservers(){
        pedidoViewModel.errorMessage.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                Toasty.error(this, it, Toast.LENGTH_SHORT, true).show()
            }
        })
        pedidoViewModel.items.observe(this, Observer {
            cantidad = it[foodItem.id]?.amount ?: 1
            binding.amount = cantidad.toString()
        })
    }

    //set up app bar
    private fun setUpAppbar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }

    //set up drawer
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