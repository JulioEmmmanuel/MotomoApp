package com.example.motomoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.motomoapp.databinding.ActivityOrderBinding
import com.google.android.material.tabs.TabLayout

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        updateCart()

        setTabs()

        binding.btnCarrito.setOnClickListener {
            val intent = Intent(this, CartSummaryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setTabs(){
        val pager = findViewById<ViewPager>(R.id.viewPager)
        val tab = findViewById<TabLayout>(R.id.tabs)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        val gridFragment1:GridFragment = GridFragment()
        gridFragment1.setFoodItems(getYakimeshiItems());

        val gridFragment2:GridFragment = GridFragment()
        gridFragment2.setFoodItems(getRamenItems());

        val gridFragment3:GridFragment = GridFragment()
        gridFragment3.setFoodItems(getBebidasItems());

        adapter.addFragment(gridFragment1, "Plato principal")
        adapter.addFragment(gridFragment2, "Ramen")
        adapter.addFragment(gridFragment3, "Bebidas")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)
    }

    private fun updateCart(){
        val foodItem = intent.getParcelableExtra<FoodItem>("FoodSelected")
        val cantidad = intent.getIntExtra("Cantidad", 0)
        if(foodItem != null){
            Carrito.Orden.addItem(foodItem, cantidad)
        }

        if(Carrito.Orden.getSize() > 0){
            binding.btnCarrito.visibility = View.VISIBLE
            binding.btnCarrito.text = "Ver carrito (${Carrito.Orden.getTotalItems()})"
        }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }

    //generamos datos dummy con este método
    private fun getYakimeshiItems(): MutableList<FoodItem>{
        var items:MutableList<FoodItem> = ArrayList()

        items.add(FoodItem(1, "Sopa Misa", "Nuestra mejor sopa", "$50", R.drawable.sopa_miso))
        items.add(FoodItem(2, "Sushi de camarón", "Nuestro mejor sushi", "$70", R.drawable.sushi_camaron))
        items.add(FoodItem(3, "Pulpo asado", "Nuevo platillo", "$45", R.drawable.pulpo))
        items.add(FoodItem(4, "Yakimeshi", "Con verduras", "$30", R.drawable.yakimeshi))

        return items
    }


    //generamos datos dummy con este método
    private fun getRamenItems(): MutableList<FoodItem>{
        var items:MutableList<FoodItem> = ArrayList()

        items.add(FoodItem(11, "Ramen de cerdo ahumado", "Sabroso y ahumado", "$95", R.drawable.ramencerdo))
        items.add(FoodItem(12, "Ramen vegano picante", "Especiado y reconfortante", "$85", R.drawable.ramenvegano))
        items.add(FoodItem(13, "Ramen de pollo teriyaki", "Dulce y satisfactorio", "$90", R.drawable.ramenpollo))
        items.add(FoodItem(14, "Rame de mariscos frescos", "Delicioso y abundante", "$110", R.drawable.ramenmariscos))

        return items
    }

    //generamos datos dummy con este método
    private fun getBebidasItems(): MutableList<FoodItem>{
        var items:MutableList<FoodItem> = ArrayList()

        items.add(FoodItem(21, "Café expresso", "Intenso y aromático", "$50", R.drawable.cafe))
        items.add(FoodItem(22, "Limonada refrescante", "Cítrica y revitalizante", "$60", R.drawable.limonada))
        items.add(FoodItem(23, "Agua de fresa", "Dulce y cremoso", "$90", R.drawable.aguafresa))
        items.add(FoodItem(24, "Té verde frío", "Refrescante y saludable", "$55", R.drawable.teverde))

        return items
    }
}