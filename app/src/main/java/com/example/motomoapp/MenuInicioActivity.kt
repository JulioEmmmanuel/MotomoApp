package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.motomoapp.databinding.ActivityMenuInicioBinding

class MenuInicioActivity : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMenuInicioBinding

    private lateinit var logInFragment:LogInFragment
    private lateinit var signUpFragment:SignUpFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "Emisor"

        val transitionXml = TransitionInflater
            .from(this).inflateTransition(R.transition.transition_menu_inicio).apply {
                duration = 700
                excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
            }

        window.exitTransition = transitionXml

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        logInFragment = LogInFragment()
        signUpFragment = SignUpFragment()

        //Lógica de programación
        setCurrentFragment(logInFragment)
        createFragments()
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
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }


}