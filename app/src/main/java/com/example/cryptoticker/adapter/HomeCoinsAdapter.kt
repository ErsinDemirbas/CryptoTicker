package com.example.cryptoticker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoticker.R
import com.example.cryptoticker.model.CryptoModel
import com.example.cryptoticker.databinding.ItemCoinsBinding
import com.example.cryptoticker.view.fragments.HomeFragmentDirections

class HomeCoinsAdapter : RecyclerView.Adapter<HomeCoinsAdapter.CoinsViewHolder>() {

    private var coinsList = arrayListOf<CryptoModel>()

    //Data Binding
    inner class CoinsViewHolder(val view : ItemCoinsBinding) : RecyclerView.ViewHolder(view.root) //.root for normal view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCoinsBinding>(inflater,R.layout.item_coins,parent,false)
        return CoinsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {

        holder.view.viewModel = coinsList[position]

        holder.itemView.setOnClickListener {
            val action = coinsList[position].id?.let { coinId ->
                HomeFragmentDirections.homeFragmentToDetailFragment(
                    coinId,
                    false
                )
            }
            if (action != null) {
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = coinsList.size

    fun setData(itemList : List<CryptoModel>){
        coinsList.clear()
        coinsList.addAll(itemList)
        notifyDataSetChanged()
    }


}