package com.example.motomoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.databinding.FragmentCardItemBinding
import com.example.motomoapp.models.entities.CreditCard
import com.example.motomoapp.viewmodels.creditcard.CreditCardListViewModel

class CreditCardRecyclerAdapter(
    private val creditCardList: List<CreditCard>,
    private val viewModel: CreditCardListViewModel
): RecyclerView.Adapter<CreditCardRecyclerAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creditCard = creditCardList.get(position)
        holder.bind(creditCard, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(FragmentCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int {
        return creditCardList.size
    }

    class ViewHolder(val binding: FragmentCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //"atando" los datos a las Views
        fun bind(creditCard: CreditCard, viewModel: CreditCardListViewModel){
            binding.cardNumber = "******" + creditCard.cardNumber!!.takeLast(4)
            binding.viewModel = viewModel
        }
    }
}
