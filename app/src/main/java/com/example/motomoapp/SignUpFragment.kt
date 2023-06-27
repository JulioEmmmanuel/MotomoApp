package com.example.motomoapp

import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.motomoapp.databinding.FragmentSignUpBinding
import com.example.motomoapp.BuildConfig

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.getRoot()
        barrelRoll()
        fadeIn()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.acceptSignupButton.setOnClickListener{

            if(!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text.isNullOrBlank()){
                val intent = Intent(requireActivity(), PaymentMethodActivity::class.java)
                requireActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
            } else {
                Toast.makeText(requireActivity(), "Llena todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
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
