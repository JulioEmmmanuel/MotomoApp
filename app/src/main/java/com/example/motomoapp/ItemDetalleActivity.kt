package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.databinding.ActivityItemDetalleBinding

class ItemDetalleActivity : AppCompatActivity() {

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
        }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }
}