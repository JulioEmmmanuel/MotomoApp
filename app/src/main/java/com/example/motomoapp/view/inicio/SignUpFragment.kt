package com.example.motomoapp.view.inicio

import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.motomoapp.R
import com.example.motomoapp.databinding.FragmentSignUpBinding
import com.example.motomoapp.view.PaymentMethodActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.dmoral.toasty.Toasty

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        barrelRoll()
        fadeIn()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.acceptSignupButton.setOnClickListener{

            if(!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text.isNullOrBlank()){
                createAccount(binding.userInput.text.toString(), binding.passwordInput.text.toString())
            } else {
                Toasty.error(requireActivity(), "The account could not be created", Toast.LENGTH_SHORT, true).show()
            }
        }

        auth = Firebase.auth
    }

    //create account with firebase
    private fun createAccount(email: String, password: String){
        activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        Log.d("signup", "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user, null)
                    } else {
                        Log.w("signup", "createUserWithEmail:failure", task.exception)
                        updateUI(null, task.exception)
                    }
                }
        }
    }

    //show message based on authentication response
    private fun updateUI(user: FirebaseUser?, exception: Exception?) {
        if (exception != null) {
            Toasty.error(requireActivity(), exception.message ?: "La cuenta no pudo ser creada", Toast.LENGTH_SHORT, true).show()
        } else {
            Toasty.success(requireActivity(), "Se creó la cuenta con éxito", Toast.LENGTH_SHORT, true).show()
            val intent = Intent(requireActivity(), PaymentMethodActivity::class.java)
            requireActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
        }
    }

    //Animación logo
    private fun barrelRoll() {
        val valueAnimator = ValueAnimator.ofFloat(0f, -720f)

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
    fun fadeIn() {

        with(binding) {
            signInText.visibility = View.VISIBLE
            logInUser.visibility = View.VISIBLE
            logInPassword.visibility = View.VISIBLE
            acceptSignupButton.visibility = View.VISIBLE
        }
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

        binding.signInText.startAnimation(animation)
        binding.logInUser.startAnimation(animation)
        binding.logInPassword.startAnimation(animation)
        binding.acceptSignupButton.startAnimation(animation)
    }
}
