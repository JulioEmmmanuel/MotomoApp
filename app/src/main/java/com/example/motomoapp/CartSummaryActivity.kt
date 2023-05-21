package com.example.motomoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.databinding.ActivityCartSummaryBinding
import com.example.motomoapp.databinding.ActivityItemDetalleBinding

class CartSummaryActivity : AppCompatActivity() {

    private lateinit var adapter:CartRecyclerAdapter
    private lateinit var binding: ActivityCartSummaryBinding
    private lateinit var recyclerItems: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartSummaryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        recyclerItems = findViewById(R.id.recyclerViewCart)
        setUpRecyclerView()

        binding.tvTotal.text = "Total: $${Carrito.getPrice()}"

        if(Carrito.getSize() > 0){
            binding.btnPagar.visibility = View.VISIBLE
            binding.btnPagar.setOnClickListener {
                val intent = Intent(this, MetodoPago::class.java)
                startActivity(intent)
            }

        binding.btnPagar.setOnClickListener {
            val intent = Intent(this, SelectPaymentMethodActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        // indicamos que tiene un tamaño fijo
        recyclerItems.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recyclerItems.layoutManager = LinearLayoutManager(this)
        //seteando el Adapter
        adapter = CartRecyclerAdapter(getItems(), binding.tvTotal)
        //asignando el Adapter al RecyclerView
        recyclerItems.adapter = adapter
    }

    private fun getItems():MutableList<CartItem>{
        return Carrito.getItems()
    }
}