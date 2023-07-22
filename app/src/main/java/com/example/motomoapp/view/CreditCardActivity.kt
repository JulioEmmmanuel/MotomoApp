package com.example.motomoapp.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityCreditCardBinding
import com.example.motomoapp.models.CreditCard

//formulario de registro de tarjeta de credito
class CreditCardActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCreditCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAgregar.setOnClickListener {
//validacion de los elementos del formulario
            if(binding.tiCardNumber.text.isNullOrBlank() ||
                binding.tiExpiryMonth.text.isNullOrBlank() ||
                binding.tiExpiryYear.text.isNullOrBlank() ||
                binding.tiAmountCard.text.isNullOrBlank() ||
                binding.tiLimite.text.isNullOrBlank()
                    ){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val cardClass = CreditCard(
                binding.tiCardNumber.text.toString(),
                Integer.parseInt(binding.tiExpiryMonth.text.toString()),
                binding.tiExpiryYear.text.toString(),
                binding.tiAmountCard.text.toString().toFloat(),
                binding.tiLimite.text.toString().toFloat()
            )
            if( cardClass.isValid() ) {
                Toast.makeText(this, "Se registró la tarjeta", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, CashActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error con los datos de la tarjeta", Toast.LENGTH_SHORT)
                    .show()            }
        }
    }
}
