package com.example.motomoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.motomoapp.databinding.ActivityMyCreditCartsBinding
class MyCreditCards : AppCompatActivity() {

    //Variables Globales

    private lateinit var binding: ActivityMyCreditCartsBinding
    private lateinit var creditCardFragment: myCreditCardsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCreditCartsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        creditCardFragment = myCreditCardsFragment()

        setCurrentFragment(creditCardFragment)

    }
/*
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

 */

    private fun setCurrentFragment(fragment: Fragment){
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction()
            .replace(R.id.creditCardsFragment, fragment)
            .commit()
    }


}