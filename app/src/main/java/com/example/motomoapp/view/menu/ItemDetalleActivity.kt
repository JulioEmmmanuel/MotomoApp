package com.example.motomoapp.view.menu

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityItemDetalleBinding
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.MyGiftCards
import com.example.motomoapp.view.MyCreditCards
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ItemDetalleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityItemDetalleBinding
    private var cantidad:Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetalleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpAppbar()
        updateUI()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    //updateUI with selected food item
    private fun updateUI(){
        val foodItem = intent.getParcelableExtra<FoodItem>("FoodItem")
        if(foodItem != null){
            binding.tvHeader.text = foodItem.name;
            binding.tvDescription.text = foodItem.description;
            binding.tvPrice.text = "$" + String.format("%.2f", foodItem.price.toDouble())

            Picasso
                .get()
                .load(foodItem.idImage)
                .placeholder(R.drawable.food)
                .into(binding.imgDescripcion)

            binding.btnMas.setOnClickListener{
                if(cantidad < 10){
                    cantidad++;
                    binding.tvCantidad.text = cantidad.toString()
                    binding.btnAgregar.text = "Agregar $cantidad al carrito"
                }
            }

            binding.btnMenos.setOnClickListener{
                if(cantidad > 1){
                    cantidad--;
                    binding.tvCantidad.text = cantidad.toString()
                    binding.btnAgregar.text = "Agregar $cantidad al carrito"
                }
            }

            binding.btnBack.setOnClickListener{
                finish()
            }
            binding.btnAgregar.setOnClickListener(){
                Carrito.addItem(foodItem, cantidad)
                finish()
            }
        }
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