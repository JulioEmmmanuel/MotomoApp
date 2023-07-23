package com.example.motomoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.R
import com.example.motomoapp.databinding.FoodItemCardBinding
import com.example.motomoapp.databinding.ProductCardBinding
import com.example.motomoapp.models.PedidoItem
import com.example.motomoapp.viewmodels.PedidoViewModel
import com.squareup.picasso.Picasso

class CartRecyclerAdapter(
    private val cartItems: MutableList<PedidoItem>,
    private val viewModel: PedidoViewModel): RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems.get(position)
        holder.bind(cartItem, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return cartItems.size
    }

    private fun deleteItem(position: Int) {
        cartItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cartItems.size)
    }

    class ViewHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root) {
        //"atando" los datos a las Views
        fun bind(pedidoItem: PedidoItem, viewModel: PedidoViewModel){
            binding.pedidoItem = pedidoItem
            binding.amount = pedidoItem.amount.toString()
            binding.price = "Precio: $" + String.format("%.2f", pedidoItem.unitaryPrice)
            binding.subtotal = "Subtotal: $" + String.format("%.2f", pedidoItem.amount * pedidoItem.unitaryPrice)
            binding.viewModel = viewModel
        }
    }
}
