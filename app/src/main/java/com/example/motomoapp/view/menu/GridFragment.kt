package com.example.motomoapp.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.R
import com.example.motomoapp.adapters.FoodRecyclerAdapter
import com.example.motomoapp.models.FoodItem

class GridFragment: Fragment() {

    private lateinit var adapter: FoodRecyclerAdapter
    private lateinit var recyclerItems:RecyclerView
    private var foodItems:List<FoodItem> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // infla el layout para este Fragment
        return inflater.inflate(R.layout.items_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerItems = requireView().findViewById(R.id.recyclerProducts)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

    public fun setFoodItems(items: List<FoodItem>){
        foodItems = items;
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        // indicamos que tiene un tamaño fijo
        recyclerItems.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recyclerItems.layoutManager = GridLayoutManager(activity, 2)
        //seteando el Adapter
        adapter = FoodRecyclerAdapter( requireActivity(), foodItems)
        //asignando el Adapter al RecyclerView
        recyclerItems.adapter = adapter
    }

}