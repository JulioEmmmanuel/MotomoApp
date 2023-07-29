package com.example.motomoapp.view.menu

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.Slide
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
import com.example.motomoapp.R
import com.example.motomoapp.adapters.ViewPagerAdapter
import com.example.motomoapp.databinding.ActivityOrderBinding
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.view.payment.MyCreditCards
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.view.inicio.MenuInicioActivity
import com.example.motomoapp.viewmodels.MenuViewModel
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class OrderActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var preferences: SharedPreferences

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var pedidoViewModel: PedidoViewModel

    // Initializing the ViewPagerAdapter
    val adapter = ViewPagerAdapter(supportFragmentManager)
    private lateinit var tabs:TabLayout

    companion object {
        const val MAIN_TYPE = "Plato Principal"
        const val BEVERAGES = "Bebidas"
        const val RAMEN = "Ramen"
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

        setButtons()

        val motomoApp = applicationContext as MotomoApp
        motomoApp.menuViewModel = menuViewModel
        pedidoViewModel = PedidoViewModel(motomoApp.carritoRepository)

        tabs = findViewById(R.id.tabs)
        setupObservers()

    }

    private fun setButtons(){
        binding.btnCarrito.setOnClickListener {
            val intent = Intent(this, CartSummaryActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        binding.bttnLogOut.setOnClickListener(){
            preferences.edit().putBoolean(MenuInicioActivity.ISLOGGED_KEY, false).apply()
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
        pedidoViewModel.updateValues()
    }

    //Menu tab de las categorias de comida
    private fun setupObservers(){
        menuViewModel.mainDishes.observe(this) {
            if (it.isNotEmpty()) {
                val g = GridFragment()
                g.setFoodItems(it)
                g.setViewModel(menuViewModel)
                adapter.addFragment(g, MAIN_TYPE)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        }

        menuViewModel.ramen.observe(this) {
            if (it.isNotEmpty()) {
                val g = GridFragment()
                g.setFoodItems(it)
                g.setViewModel(menuViewModel)
                adapter.addFragment(g, RAMEN)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        }

        menuViewModel.beverages.observe(this) {
            if (it.isNotEmpty()) {
                val g = GridFragment()
                g.setFoodItems(it)
                g.setViewModel(menuViewModel)
                adapter.addFragment(g, BEVERAGES)
                binding.viewPager.adapter = adapter
                tabs.setupWithViewPager(binding.viewPager)
            }
        }

        menuViewModel.errorMessage.observe(this) {
            if (!it.isNullOrEmpty()) {
                Toasty.error(this@OrderActivity, it, Toast.LENGTH_SHORT, true).show()
            }
        }

        menuViewModel.showDetail.observe(this) {
            if (it) {
                val intent = Intent(this, ItemDetalleActivity::class.java)
                intent.putExtra("FoodItem", menuViewModel.selectedElement.value)
                intent.putExtra("url", menuViewModel.selectedElement.value?.url)
                startActivity(intent)
            }
        }

        pedidoViewModel.totalItems.observe(this) {
            if (it > 0) {
                binding.btnCarrito.visibility = View.VISIBLE
                binding.btnCarrito.text = "Ver carrito (${it})"
            } else {
                binding.btnCarrito.visibility = View.INVISIBLE
            }
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
