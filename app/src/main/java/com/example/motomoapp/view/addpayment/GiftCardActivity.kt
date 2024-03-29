package com.example.motomoapp.view.addpayment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.transition.Visibility
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityGiftCardBinding
import com.example.motomoapp.MotomoApp
import com.example.motomoapp.view.inicio.MenuInicioActivity
import com.example.motomoapp.view.options.MyGiftCardsVO
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.viewmodels.giftcard.AddGiftCardViewModel
import es.dmoral.toasty.Toasty

class GiftCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGiftCardBinding

    private lateinit var giftCardViewModel: AddGiftCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityGiftCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        giftCardViewModel = AddGiftCardViewModel(
            (applicationContext as MotomoApp).giftCardRepository
        )

        setTransition()
        setAppBar()
        setButtonListeners()
        setObservers()
    }


    //set observers for card creation
    private fun setObservers(){
        binding.viewmodel = giftCardViewModel
        giftCardViewModel.errorMessage.observe(this, Observer {
            Toasty.error(this, it, Toast.LENGTH_SHORT, true).show()
        })
        giftCardViewModel.added.observe(this, Observer {
            if(it) {
                Toasty.success(this, "Se agregó correctamente la tarjeta", Toast.LENGTH_SHORT, true)
                    .show()
                val from = intent.extras?.getString("from")
                if(from == "mycards"){
                    val i = Intent(this, MyGiftCards::class.java)
                    startActivity(i)
                } else if(from == "mycardsvo") {
                    val i = Intent(this, MyGiftCardsVO::class.java)
                    startActivity(i)
                } else {
                    val i = Intent(this, MenuInicioActivity::class.java)
                    startActivity(i)
                }

            }
        })
    }

    //set button listeners
    private fun setButtonListeners(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAgregar.setOnClickListener {
            giftCardViewModel.addCard()
        }
    }

    private fun setTransition(){

        // transicion al abrir activity
        val transition = Fade(Visibility.MODE_IN).apply {
            duration = 700
            excludeTarget(window.decorView.findViewById<View>(androidx.transition.R.id.action_bar_container), true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = transition
    }

    private fun setAppBar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
    }

}