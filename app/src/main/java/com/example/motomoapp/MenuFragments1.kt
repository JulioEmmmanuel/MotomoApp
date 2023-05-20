package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.motomoapp.databinding.ActivityMainBinding
import com.example.motomoapp.databinding.ActivityMenuFragments1Binding

class MenuFragments1 : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMenuFragments1Binding

    val logInFragment = LogInFragment()
    val signUpFragment = SignUpFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_fragments1)

        binding = ActivityMenuFragments1Binding.inflate(layoutInflater)
        setContentView(binding.root)

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
                R.id.signIn-> {
                    setCurrentFragment(signUpFragment)
                    it.actionView?.clearFocus()
                    true
                }
                else -> false
            }
        }
//        bottomNavigationView.getOrCreateBadge(R.id.nav_home).apply {
//            isVisible=true
//            number=8
//        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView,fragment)
            commit()
        }
    }
}