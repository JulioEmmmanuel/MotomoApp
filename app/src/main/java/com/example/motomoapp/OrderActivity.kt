package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        val gridFragment = supportFragmentManager.findFragmentById(R.id.itemsGrid) as GridFragment
        gridFragment.setFoodItems(getFoodItems());
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }

    //generamos datos dummy con este método
    private fun getFoodItems(): MutableList<FoodItem>{
        var items:MutableList<FoodItem> = ArrayList()

        items.add(FoodItem("Sopa Misa", "Nuestra mejor sopa", "$50", R.drawable.sopa_miso))
        items.add(FoodItem("Sushi de camarón", "Nuestro mejor sushi", "$70", R.drawable.sushi_camaron))
        items.add(FoodItem("Pulpo asado", "Nuevo platillo", "$45", R.drawable.pulpo))
        items.add(FoodItem("Yakimeshi", "Con verduras", "$30", R.drawable.yakimeshi))

        return items
    }

}