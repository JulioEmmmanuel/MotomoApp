package com.example.motomoapp.view.inicio


import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.motomoapp.R
import com.example.motomoapp.databinding.FragmentLogInBinding
import com.example.motomoapp.view.OrderActivity
import com.example.motomoapp.view.PaymentMethodActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

//@Suppress("UNREACHABLE_CODE")
class LogInFragment : Fragment() {

    //Aplicación de sharedPreferences
    private val ISLOGGED_KEY = "islogged_key"

    private lateinit var binding: FragmentLogInBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.getRoot()
        barrelRoll()
        fadeIn()

        auth = Firebase.auth

        return view


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.acceptLoginButton.setOnClickListener {
            if (!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text
                    .isNullOrBlank()
            ) {
                login(binding.userInput.text.toString(), binding.passwordInput.text.toString())
            } else {
                Toasty.error(requireActivity(), "Debes llenar los campos", Toast.LENGTH_SHORT, true).show()
            }
        }
    }

    //create account with firebase
    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()){
                    task ->
                if(task.isSuccessful){
                    Log.d("login", "signInUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user, null)
                } else {
                    Log.w("login", "signInUserWithEmail:failure", task.exception)
                    updateUI(null, task.exception)
                }
            }
    }

    //show message based on authentication response
    private fun updateUI(user: FirebaseUser?, exception: Exception?) {
        if (exception != null) {
            Toasty.error(requireActivity(), exception.message ?: "Ocurrió un problema con el inicio de sesión", Toast.LENGTH_SHORT, true).show()
        } else {
            Toasty.success(requireActivity(), "Se inició sesión correctamente. Cargando el menú...", Toast.LENGTH_SHORT, true).show()
            val intent = Intent(requireActivity(), OrderActivity::class.java)
            requireActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
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


}