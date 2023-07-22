package com.example.motomoapp.view.menu

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.motomoapp.R
import com.example.motomoapp.adapters.ViewPagerAdapter
import com.example.motomoapp.databinding.ActivityOrderBinding
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.MyGiftCards
import com.example.motomoapp.models.api.ApiFood
import com.example.motomoapp.models.repositories.MenuRepository
import com.example.motomoapp.view.MyCreditCards
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.view.inicio.MenuInicioActivity
import com.example.motomoapp.viewmodels.MenuViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class OrderActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var preferences: SharedPreferences

    private lateinit var viewModel: MenuViewModel

    // Initializing the ViewPagerAdapter
    val adapter = ViewPagerAdapter(supportFragmentManager)
    private lateinit var tabs:TabLayout

    companion object {
        val MAIN_TYPE = "Best Food"
        val DRINKS = "Drinks"
        val DESSERTS = "Desserts"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        transitionIn()
        setUpAppbar()

        preferences = getSharedPreferences(MenuInicioActivity.PREFS_NAME, AppCompatActivity.MODE_PRIVATE)

        binding.navView.setNavigationItemSelectedListener(this)

        //setValues()
        updateCart()
        setButtons()

        viewModel = MenuViewModel((applicationContext as MotomoApp).menuRepository)

        tabs = findViewById(R.id.tabs)
        setupObservers()

    }

    private fun setButtons(){
        binding.btnCarrito.setOnClickListener {
            val intent = Intent(this, CartSummaryActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        binding.bttnLogOut.setOnClickListener(){
            preferences.edit().putBoolean(MenuInicioActivity.PREFS_NAME, false).apply()
            val intent = Intent(this, MenuInicioActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun transitionIn(){
        // transición al iniciar activity
        val transition = Slide(Gravity.TOP).apply {
            duration = 500
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transition
    }

    //set up app bar
    private fun setUpAppbar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }

    override fun onResume() {
        super.onResume()

        if(Carrito.getSize() > 0){
            binding.btnCarrito.visibility = View.VISIBLE
            binding.btnCarrito.text = "Ver carrito (${Carrito.Orden.getTotalItems()})"
        }
    }

    //Menu tab de las categorias de comida
    private fun setupObservers(){
        viewModel.bestFoods.observe(this, Observer {
            if(it.isNotEmpty()){
                val g = GridFragment()
                g.setFoodItems(it)
                adapter.addFragment(g, MAIN_TYPE)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        })

        viewModel.drinks.observe(this, Observer {
            if(it.isNotEmpty()){
                val g = GridFragment()
                g.setFoodItems(it)
                adapter.addFragment(g, DRINKS)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        })

        viewModel.desserts.observe(this, Observer {
            if(it.isNotEmpty()){
                val g = GridFragment()
                g.setFoodItems(it)
                adapter.addFragment(g, DESSERTS)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            if(it.isNotEmpty()){
                Toasty.error(this@OrderActivity,  it, Toast.LENGTH_SHORT, true).show()
            }
        })
    }



    //actualizar el carrito
    private fun updateCart(){
        val foodItem = intent.getParcelableExtra<FoodItem>("FoodSelected")
        val cantidad = intent.getIntExtra("Cantidad", 0)

       if(foodItem != null){
            Carrito.addItem(foodItem, cantidad)
        }

        if(Carrito.getSize() > 0){
            binding.btnCarrito.visibility = View.VISIBLE
            binding.btnCarrito.text = "Ver carrito (${Carrito.Orden.getTotalItems()})"
        }
    }

    //set up drawer
    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }

    //acceso a los elementos del menu
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
