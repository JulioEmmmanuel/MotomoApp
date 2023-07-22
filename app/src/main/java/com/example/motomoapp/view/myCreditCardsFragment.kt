package com.example.motomoapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.motomoapp.databinding.FragmentCardsItemListBinding

class myCreditCardsFragment: Fragment() {

    private lateinit var binding: FragmentCardsItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCardsItemListBinding.inflate(inflater, container, false)
        val view = binding.getRoot()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}