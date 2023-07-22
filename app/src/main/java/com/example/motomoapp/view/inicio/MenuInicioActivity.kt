package com.example.motomoapp.view.inicio

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityMenuInicioBinding
import com.example.motomoapp.utils.executeOrRequestPermission
import com.example.motomoapp.view.OrderActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MenuInicioActivity : AppCompatActivity() {

    //Variables Globales
    private lateinit var binding: ActivityMenuInicioBinding

    private lateinit var logInFragment: LogInFragment
    private lateinit var signUpFragment: SignUpFragment

    //Aplicación de sharedPreferences
    private val PREFS_NAME = "sharedpreferences"
    private val ISLOGGED_KEY = "islogged_key"
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuInicioBinding.inflate(layoutInflater)
        val view = binding.root

        isLogged()
        setContentView(view)
        setUpTransition()

        //set appBar
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        //set up fragments
        logInFragment = LogInFragment()
        signUpFragment = SignUpFragment()
        setCurrentFragment(logInFragment)
        createFragments()

        connectToFirebase()

    }

    private fun setUpTransition(){
        //create transtion
        val transitionXml = TransitionInflater
            .from(this).inflateTransition(R.transition.transition_menu_inicio).apply {
                duration = 700
                excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
            }

        window.exitTransition = transitionXml
    }

    private fun connectToFirebase(){
        FirebaseApp.initializeApp(this)

        executeOrRequestPermission(this) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Error", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result

                Log.d("FCM_TOKEN", token)
            })
        }
    }

    //change selected fragment
    private fun createFragments() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.logIn ->{
                    setCurrentFragment(logInFragment)
                    it.actionView?.clearFocus()
                    true
                }
                R.id.signUp -> {
                    setCurrentFragment(signUpFragment)
                    it.actionView?.clearFocus()
                    true
                }
                else -> false
            }
        }
    }

    //display fragment
    private fun setCurrentFragment(fragment: Fragment){
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    //check whether the user is logged
   fun isLogged(){
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
       val isLogged = preferences.getBoolean(ISLOGGED_KEY, false)

       if (isLogged){
           val intent = Intent(this, OrderActivity::class.java)
           intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           this.startActivity(
               intent,
               ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
           )
           }
       }
   }

