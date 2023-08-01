package com.example.motomoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motomoapp.databinding.FragmentGiftcardItemBinding
import com.example.motomoapp.databinding.FragmentGiftcardViewonlyBinding
import com.example.motomoapp.models.entities.GiftCard
import com.example.motomoapp.viewmodels.giftcard.GiftCardListViewModel

class GiftCardVORecyclerAdapter(
    private val giftCardList: List<GiftCard>,
    private val viewModel: GiftCardListViewModel
): RecyclerView.Adapter<GiftCardVORecyclerAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val giftCard = giftCardList.get(position)
        holder.bind(giftCard, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
        ViewHolder(FragmentGiftcardViewonlyBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount(): Int {
        return giftCardList.size
    }

    class ViewHolder(val binding: FragmentGiftcardViewonlyBinding) : RecyclerView.ViewHolder(binding.root) {
        //"atando" los datos a las Views
        fun bind(giftCard: GiftCard, viewModel: GiftCardListViewModel){
            binding.giftCard = giftCard
            binding.viewModel = viewModel
            binding.price = "$ ${String.format("%.2f", giftCard.giftAmount)}"
        }
    }
}
