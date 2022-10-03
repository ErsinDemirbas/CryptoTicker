package com.example.cryptoticker.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoticker.R
import com.example.cryptoticker.adapter.FavoriteCoinsAdapter
import com.example.cryptoticker.databinding.FragmentFavoriteBinding
import com.example.cryptoticker.viewModel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private val favoriteCoinsAdapter : FavoriteCoinsAdapter by lazy { FavoriteCoinsAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel : FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvFavoriteCoins.layoutManager = layoutManager

        viewModel.getAllDataFromFireStore(binding.root.context)
        observeLiveData()

        binding.rvFavoriteCoins.adapter = favoriteCoinsAdapter

        actions()
    }

    private fun actions(){
        binding.favoriteRefreshLayout.setOnRefreshListener {
            viewModel.getAllDataFromFireStore(binding.root.context)
            binding.favoriteRefreshLayout.isRefreshing = false
        }
    }

    private fun observeLiveData(){
        viewModel.favoriteCoins.observe(viewLifecycleOwner, {
            it?.let { response ->
                favoriteCoinsAdapter.setData(response)
                binding.pbFavoriteCoins.visibility = View.GONE
                binding.rvFavoriteCoins.visibility = View.VISIBLE
            }
        })

        viewModel.favoriteProgressBar.observe(viewLifecycleOwner, {
            it?.let { progressBar ->
                if (progressBar){
                    binding.rvFavoriteCoins.visibility = View.GONE
                    binding.pbFavoriteCoins.visibility = View.VISIBLE
                }else{
                    binding.pbFavoriteCoins.visibility = View.GONE
                    binding.rvFavoriteCoins.visibility = View.VISIBLE
                }
            }
        })
    }

}