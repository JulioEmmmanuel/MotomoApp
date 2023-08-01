package com.example.motomoapp.view.addpayment


import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.transition.Fade
import android.transition.Visibility
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityCreditCardBinding
import com.example.motomoapp.view.app.MotomoApp
import com.example.motomoapp.view.inicio.MenuInicioActivity
import com.example.motomoapp.view.options.MyCreditCardsVO
import com.example.motomoapp.view.payment.MyCreditCards
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.viewmodels.creditcard.AddCreditCardViewModel
import es.dmoral.toasty.Toasty
import io.conekta.conektasdk.Conekta
import io.conekta.conektasdk.Token

//formulario de registro de tarjeta de credito
class CreditCardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCreditCardBinding
    private lateinit var creditCardViewModel: AddCreditCardViewModel

    companion object {
        private val PUBLIC_KEY = "key_NHMT2LSYle4m4DsCVnXCNXA"
        private val API_VERSION = "1.0.0"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        creditCardViewModel = AddCreditCardViewModel(
            (applicationContext as MotomoApp).creditCardRepository
        )

        setTransition()
        setAppBar()
        setButtonListeners()
        setObservers()
    }

    private fun setObservers(){
        binding.viewmodel = creditCardViewModel
        creditCardViewModel.errorMessage.observe(this, Observer {
            Toasty.error(this, it, Toast.LENGTH_SHORT, true).show()
        })
        creditCardViewModel.added.observe(this, Observer {
            if(it) {
                Toasty.success(this, "Se agregó correctamente la tarjeta", Toast.LENGTH_SHORT, true)
                    .show()
                val from = intent.extras?.getString("from")
                if(from == "mycards"){
                    val i = Intent(this, MyCreditCards::class.java)
                    startActivity(i)
                } else if(from == "mycardsvo"){
                    val i = Intent(this, MyCreditCardsVO::class.java)
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
            if(hasInternetConnection()) {
                creditCardViewModel.createCard(createToken())
            } else {
                Toasty.error(this, "Debes estar conectado a internet", Toast.LENGTH_SHORT, true).show()
            }
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

    //set up Conekta and create token
    fun createToken():Token{
        Conekta.setPublicKey(PUBLIC_KEY)
        Conekta.setApiVersion(API_VERSION)
        Conekta.collectDevice(this)
        val token = Token(this)
        return token
    }

    private fun hasInternetConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
