package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.motomoapp.databinding.ActivityItemDetalleBinding
import com.example.motomoapp.databinding.ActivityMenuInicioBinding

class MenuInicio : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMenuInicioBinding

    private val logInFragment:LogInFragment  = LogInFragment()
    private val signUpFragment:SignUpFragment = SignUpFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)

        //Lógica de programación
        setCurrentFragment(signUpFragment)
        createFragments()
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }

    private fun createFragments() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.logIn->{
                    setCurrentFragment(logInFragment)
                    it.actionView?.clearFocus()
                    true
                }
                R.id.signUp-> {
                    setCurrentFragment(signUpFragment)
                    it.actionView?.clearFocus()
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.containerView,fragment, "fragmento")
            .commit()
        }
}