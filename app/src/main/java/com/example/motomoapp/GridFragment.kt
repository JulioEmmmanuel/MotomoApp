package com.example.motomoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridFragment: Fragment() {

    private lateinit var adapter:FoodRecyclerAdapter
    private lateinit var recyclerItems:RecyclerView
    private var listener: (FoodItem) -> Unit = {}


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

    //generamos datos dummy con este método
    private fun getFoodItems(): MutableList<FoodItem>{
        var items:MutableList<FoodItem> = ArrayList()

        items.add(FoodItem("Sopa Miso", "Nuestra mejor sopa", "$50", R.drawable.sopa_miso))
        items.add(FoodItem("Sushi de camarón", "Nuestro mejor sushi", "$70", R.drawable.sushi_camaron))
        items.add(FoodItem("Pulpo asado", "Nuevo platillo", "$45", R.drawable.pulpo))
        items.add(FoodItem("Yakimeshi", "Con verduras", "$30", R.drawable.yakimeshi))

        return items
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        // indicamos que tiene un tamaño fijo
        recyclerItems.setHasFixedSize(true)
        // indicamos el tipo de layoutManager
        recyclerItems.layoutManager = GridLayoutManager(activity, 2)
        //seteando el Adapter
        adapter = FoodRecyclerAdapter( requireActivity(), getFoodItems())
        //asignando el Adapter al RecyclerView
        recyclerItems.adapter = adapter
    }

}