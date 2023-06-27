package com.example.motomoapp

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.transition.Slide
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.motomoapp.adapters.ViewPagerAdapter
import com.example.motomoapp.databinding.ActivityOrderBinding
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.models.FoodItem
import com.example.motomoapp.models.MyGiftCards
import com.example.motomoapp.repositories.MenuRepository
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout


class OrderActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Aplicación de sharedPreferences
    private val PREFS_NAME = "sharedpreferences"
    private val USERNAME_KEY = "username_key"
    private val ISLOGGED_KEY = "islogged_key"

    private lateinit var preferences: SharedPreferences

    private lateinit var binding: ActivityOrderBinding
    private lateinit var menu: MenuRepository
/*
    //Shared Preferences
    private val PREFS_NAME = "sharedPreferences"
    private val CANTIDAD_KEY = "cantidad_string_key"
    private val FOOD_ITEM_KEY = "food_item_string_key"

    private lateinit var preferences: SharedPreferences

 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // transición al iniciar activity
        val transition = Slide(Gravity.TOP).apply {
            duration = 500
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transition

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        setupDrawer(appBar)

        menu = MenuRepository()

        //setValues()
        updateCart()

        setTabs()

        binding.btnCarrito.setOnClickListener {
            val intent = Intent(this, CartSummaryActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        binding.navView.setNavigationItemSelectedListener(this)

        binding.bttnLogOut.setOnClickListener(){
            preferences.edit().putBoolean(ISLOGGED_KEY, false).apply()
            val intent = Intent(this, MenuInicioActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    override fun onResume() {
        super.onResume()

        if(Carrito.Orden.getSize() > 0){
            binding.btnCarrito.visibility = View.VISIBLE
            binding.btnCarrito.text = "Ver carrito (${Carrito.Orden.getTotalItems()})"
        }
    }
//Menu tab de las categorias de comida
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


//actualizar el carrito
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
    private fun getYakimeshiItems(): List<FoodItem>{
        // Get the menu from the API through the Repository
        // Using the square brackets we will be able to call directly the method get
        val items:List<FoodItem> = menu["Entries"]

        items.sortedBy { it.id }

        items[0].apply { price = "$95"; idImage = R.drawable.sopa_miso }
        items[1].apply { price = "$85"; idImage = R.drawable.sushi_camaron }
        items[2].apply { price = "$90"; idImage = R.drawable.pulpo }
        items[3].apply { price = "$110"; idImage = R.drawable.yakimeshi }

        return items
    }


    //generamos datos dummy con este método
    private fun getRamenItems(): List<FoodItem>{
        val items:List<FoodItem> = menu["Main"]

        items.sortedBy { it.id }

        items[0].apply { price = "$95"; idImage = R.drawable.ramencerdo }
        items[1].apply { price = "$85"; idImage = R.drawable.ramenvegano }
        items[2].apply { price = "$90"; idImage = R.drawable.ramenpollo }
        items[3].apply { price = "$110"; idImage = R.drawable.ramenmariscos }

        return items
    }

    //generamos datos dummy con este método
    private fun getBebidasItems(): List<FoodItem>{
        val items:List<FoodItem> = menu["Beverages"]

        items.sortedBy { it.id }

        items[0].apply { price = "$50"; idImage = R.drawable.cafe }
        items[1].apply { price = "$60"; idImage = R.drawable.limonada }
        items[2].apply { price = "$90"; idImage = R.drawable.aguafresa }
        items[3].apply { price = "$55"; idImage = R.drawable.teverde }

        return items
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
