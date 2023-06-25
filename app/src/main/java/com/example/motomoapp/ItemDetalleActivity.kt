package com.example.motomoapp

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.databinding.ActivityItemDetalleBinding
import com.google.android.material.navigation.NavigationView

class ItemDetalleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityItemDetalleBinding
    private var cantidad:Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetalleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        val foodItem = intent.getParcelableExtra<FoodItem>("FoodItem")
        if(foodItem != null){
            binding.tvHeader.text = foodItem.name;
            binding.tvDescription.text = foodItem.description;
            binding.tvPrice.text = foodItem.price;
            binding.imgDescripcion.setImageResource(foodItem.idImage);

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

            binding.btnAgregar.setOnClickListener{
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("Cantidad", cantidad)
                intent.putExtra("FoodSelected", foodItem)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                finish()
            }
        }
        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
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