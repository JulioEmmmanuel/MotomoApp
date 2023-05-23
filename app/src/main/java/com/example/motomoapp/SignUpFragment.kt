package com.example.motomoapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.acceptSignupButton.setOnClickListener{

            if(!binding.userInput.text.isNullOrBlank() && !binding.passwordInput.text.isNullOrBlank()){
                if(BuildConfig.IS_PAID){
                    val intent = Intent(requireActivity(), PaymentMethodActivity::class.java)
                    requireActivity().startActivity(intent)
                } else {
                    Toast.makeText(requireActivity(), "Cargando el menú. Espera un momento...", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(requireActivity(), OrderActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            } else {
                Toast.makeText(requireActivity(), "Llena todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    
}