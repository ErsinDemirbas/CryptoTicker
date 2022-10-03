package com.example.cryptoticker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoticker.R
import com.example.cryptoticker.databinding.ItemCoinsBinding
import com.example.cryptoticker.model.CryptoModel
import com.example.cryptoticker.view.fragments.FavoriteFragmentDirections

class FavoriteCoinsAdapter : RecyclerView.Adapter<FavoriteCoinsAdapter.FavoriteViewHolder>() {

    private var favoriteCoinsList = arrayListOf<CryptoModel>()

    inner class FavoriteViewHolder(val view : ItemCoinsBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCoinsBinding>(inflater,
            R.layout.item_coins,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.view.viewModel = favoriteCoinsList[position]


        holder.itemView.setOnClickListener {
            val action = favoriteCoinsList[position].id?.let { coinId ->
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                    coinId,
                    true
                )
            }
            if (action != null) {
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = favoriteCoinsList.size

    fun setData(itemList : List<CryptoModel>){
        favoriteCoinsList.clear()
        favoriteCoinsList.addAll(itemList)
        notifyDataSetChanged()
    }

}