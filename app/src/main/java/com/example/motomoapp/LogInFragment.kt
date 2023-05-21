package com.example.motomoapp


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.motomoapp.databinding.FragmentLogInBinding
import com.google.android.material.button.MaterialButton


class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.getRoot()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.acceptLoginButton.setOnClickListener{
            val intent = Intent(requireActivity(), OrderActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }
}