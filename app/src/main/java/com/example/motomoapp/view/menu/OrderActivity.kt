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
import com.example.motomoapp.R
import com.example.motomoapp.adapters.ViewPagerAdapter
import com.example.motomoapp.databinding.ActivityOrderBinding
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.inicio.MenuInicioActivity
import com.example.motomoapp.view.options.MyCreditCardsVO
import com.example.motomoapp.view.options.MyGiftCardsVO
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
    private var tabFragments : HashMap<String, GridFragment> = HashMap()

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
                tabFragments[MAIN_TYPE] = g
                if (tabFragments.size == 3){
                    addFragments()
                }
            }
        }

        menuViewModel.ramen.observe(this) {
            if (it.isNotEmpty()) {
                val g = GridFragment()
                g.setFoodItems(it)
                g.setViewModel(menuViewModel)
                tabFragments[RAMEN] = g
                if (tabFragments.size == 3){
                    addFragments()
                }
            }
        }

        menuViewModel.beverages.observe(this) {
            if (it.isNotEmpty()) {
                val g = GridFragment()
                g.setFoodItems(it)
                g.setViewModel(menuViewModel)
                tabFragments[BEVERAGES] = g
                if (tabFragments.size == 3){
                    addFragments()
                }
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

    //add fragments to the adapter and set it up
    private fun addFragments(){
        tabFragments[MAIN_TYPE]?.let { it1 -> adapter.addFragment(it1, MAIN_TYPE) }
        tabFragments[RAMEN]?.let { it1 -> adapter.addFragment(it1, RAMEN) }
        tabFragments[BEVERAGES]?.let { it1 -> adapter.addFragment(it1, BEVERAGES) }
        binding.viewPager.adapter = adapter
        tabs.setupWithViewPager(binding.viewPager)
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
