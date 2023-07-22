package com.example.motomoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.R
import com.example.motomoapp.models.Carrito
import com.example.motomoapp.models.CartItem
import com.squareup.picasso.Picasso

class CartRecyclerAdapter(
    private val cartItems: MutableList<CartItem>,
    private val tvTotal: TextView): RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems.get(position)
        holder.bind(cartItem, tvTotal, fun():Unit {
            deleteItem(position)
        } )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.product_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    private fun deleteItem(position: Int) {
        cartItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cartItems.size)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        //obteniendo las referencias a las Views
        val itemName = view.findViewById(R.id.tvNombreItem) as TextView
        val precio = view.findViewById(R.id.tvPrecioItem) as TextView
        val subtotal = view.findViewById(R.id.tvSubtotalItem) as TextView
        val image = view.findViewById(R.id.imgFoodItem) as ImageView
        val btnMas = view.findViewById(R.id.btnMas) as Button
        val btnMenos = view.findViewById(R.id.btnMenos) as Button
        val cantidad = view.findViewById(R.id.tvCantidad) as TextView

        //"atando" los datos a las Views
        fun bind(cartItem: CartItem, tvTotal: TextView, delete: () -> Unit){
            itemName.text = cartItem.name
            precio.text = "Precio: $" + String.format("%.2f", cartItem.precio.toDouble())
            subtotal.text = "Subtotal: $" + String.format("%.2f", cartItem.subtotal.toDouble())
            cantidad.text = cartItem.cantidad.toString()
            Picasso.get().load(cartItem.idImagen).into(image)

            btnMas.setOnClickListener{
                val cantidadNum = (cantidad.text as String).toInt()
                val subtotalNum = (subtotal.text.substring(11) as String).toInt()
                val precioNum = (precio.text.substring(9) as String).toInt()
                if(cantidadNum < 10){
                    cantidad.text = (cantidadNum+1).toString();
                    subtotal.text = "Subtotal: $${subtotalNum+precioNum}"
                    Carrito.addOne(cartItem.id)
                    tvTotal.text = "Total: $${Carrito.getPrice()}"
                }
            }

            btnMenos.setOnClickListener{
                val cantidadNum = (cantidad.text as String).toInt()
                val subtotalNum = (subtotal.text.substring(11) as String).toInt()
                val precioNum = (precio.text.substring(9) as String).toInt()
                if(cantidadNum > 1){
                    cantidad.text = (cantidadNum-1).toString();
                    subtotal.text = "Subtotal: $${subtotalNum-precioNum}"
                    Carrito.removeOne(cartItem.id)
                    tvTotal.text = "Total: $${Carrito.getPrice()}"
                } else if(cantidadNum == 0) {
                    Carrito.removeOne(cartItem.id)
                    tvTotal.text = "Total: $${Carrito.getPrice()}"
                    delete()
                }
            }
        }
    }
}
