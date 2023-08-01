package com.example.motomoapp.view.gps

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.motomoapp.R
import com.example.motomoapp.databinding.ActivityGpsBinding
import com.example.motomoapp.view.payment.MyGiftCards
import com.example.motomoapp.view.payment.MyCreditCards
import com.example.motomoapp.view.payment.SelectPaymentMethodActivity
import com.example.motomoapp.view.menu.CartSummaryActivity
import com.example.motomoapp.view.options.MyCreditCardsVO
import com.example.motomoapp.view.options.MyGiftCardsVO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import es.dmoral.toasty.Toasty


class GpsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityGpsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var btnSi: Button
    private lateinit var btnNo: Button

    companion object {
        const val PERMISSION_ID=33
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGpsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAppBar()

        //Obtención de la localización
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnSi = binding.acceptHomeDelivery
        btnNo = binding.denyHomeDelivery

        btnSi.setOnClickListener(){
            getLocation()
        }

        btnNo.setOnClickListener(){
            val intent = Intent(this, SelectPaymentMethodActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.navView.setNavigationItemSelectedListener(this)

    }

    private fun setAppBar(){
        val appBar = findViewById<Toolbar>(R.id.motomoToolbar)
        this.setSupportActionBar(appBar)
        setupDrawer(appBar)
    }

    //obtiene la localización al activar los permisos
    @SuppressLint("MissingSuperCall")

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toasty.error(this,"Aun requieres permiso", Toast.LENGTH_SHORT, true).show()
            }
        }
    }

    //Consulta del estatus de permiso de ubicación
    private fun checkGranted(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    //Revisa si los permisos existen
    private fun checkPermissions() =
        checkGranted(android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                checkGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)

    //Solicita el permiso de ubicación
    private fun requestPermissions() {
        Toasty.info(this,"Es necesario conocer tu ubicación",
            Toast.LENGTH_SHORT, true).show()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }
    //Obtención de la localización
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->
                Toasty.success(this,"Se ha guardado tu ubicación con exito",
                    Toast.LENGTH_SHORT, true).show()
                val intent = Intent(this, SelectPaymentMethodActivity::class.java)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        // Handle navigation view item clicks here.

        when(item.itemId){
            R.id.credit_card -> {
                val intent = Intent(this, MyCreditCardsVO::class.java)
                this.startActivity(intent)
            }
            R.id.giftcard -> {
                val intent = Intent(this, MyGiftCardsVO::class.java)
                this.startActivity(intent)
            }
            R.id.order -> {
                val intent = Intent(this, CartSummaryActivity::class.java)
                this.startActivity(intent)
            }
        }
        return true;
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }
}