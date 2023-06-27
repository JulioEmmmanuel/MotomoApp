package com.example.motomoapp

import android.animation.AnimatorInflater
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.transition.Scene
import androidx.core.widget.addTextChangedListener
import com.example.motomoapp.databinding.FragmentLogInBinding
import com.google.android.material.textfield.TextInputEditText

//@Suppress("UNREACHABLE_CODE")
class LogInFragment : Fragment() {

    //Aplicación de sharedPreferences
    private val PREFS_NAME = "sharedpreferences"
    private val USERNAME_KEY = "username_key"
    private val ISLOGGED_KEY = "islogged_key"

    private lateinit var binding: FragmentLogInBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.getRoot()
        barrelRoll()
        fadeIn()
        return view



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setValue()

        binding.userInput.addTextChangedListener{
            val userName = binding.userInput.text.toString()
            preferences.edit().putString(USERNAME_KEY, userName).apply()
        }

        binding.acceptLoginButton.setOnClickListener {
            if (!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text
                    .isNullOrBlank()
            ) { if (binding.userInput.text.toString() == "irvinbsu" &&
                    binding.passwordInput.text.toString() == "hola123") {

                val userName = binding.userInput.text.toString()

                preferences.edit().putString(USERNAME_KEY, userName).apply()
                preferences.edit().putBoolean(ISLOGGED_KEY, true).apply()

                Toast.makeText(
                    requireActivity(),
                    "Cargando el menú. Espera un momento...",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val intent = Intent(requireActivity(), OrderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                requireActivity().startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
                )
            }else {
                Toast.makeText(requireActivity(), "El nombre de usuario y/o contraseña está incorrecto", Toast.LENGTH_SHORT)
                    .show()
            }

            } else {
                Toast.makeText(requireActivity(), "Llena todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    //Animación logo
    private fun barrelRoll() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)

        valueAnimator.run {
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.mottomologo.rotationY = value
            }

            interpolator = AccelerateDecelerateInterpolator()
            duration = 1_000
            start()
        }
    }

    // Animación fade in
    private fun fadeIn() {

        with(binding) {
            logInUser.visibility = View.VISIBLE
            logInPassword.visibility = View.VISIBLE
            acceptLoginButton.visibility = View.VISIBLE
        }
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        binding.logInText.startAnimation(animation)
        binding.logInUser.startAnimation(animation)
        binding.logInPassword.startAnimation(animation)
        binding.acceptLoginButton.startAnimation(animation)
    }

    private fun setValue(){
        val userName = preferences.getString(USERNAME_KEY, "")
        val isLogged = preferences.getBoolean(ISLOGGED_KEY, false)
        if(userName == null || userName == "") {
            Toast.makeText(
                requireActivity(),
                "Ingresa tu usuario y contraseña",
                Toast.LENGTH_SHORT
            ).show()

        } else if (isLogged){

        }
        else {binding.userInput.setText(userName)
            Toast.makeText(
                requireActivity(),
                "Se ha cerrado tu sesión, ingresa de nuevo tu contraseña",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}
