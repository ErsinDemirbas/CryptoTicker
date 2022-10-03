package com.example.cryptoticker.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cryptoticker.R
import com.example.cryptoticker.databinding.FragmentDetailBinding
import com.example.cryptoticker.viewModel.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private lateinit var navController: NavController
    private var cryptoId = ""
    private var isFavoriteCoins = true
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        arguments?.let {
            cryptoId = DetailFragmentArgs.fromBundle(it).id
            isFavoriteCoins = DetailFragmentArgs.fromBundle(it).isFavorite
            viewModel.getDataFromApi(cryptoId)
        }

        observeLiveData()

        actions()

    }

    private fun observeLiveData(){

        viewModel.cryptoCoin.observe(viewLifecycleOwner, {
            it?.let { response ->
                binding.viewModel = response
                binding.detailProgressBar.visibility = View.GONE
                binding.relativeLayout.visibility = View.VISIBLE
            }
        })

        viewModel.cryptoDetailProgressBar.observe(viewLifecycleOwner, {
            it?.let { progressBar ->
                if (progressBar){
                    binding.relativeLayout.visibility = View.GONE
                    binding.detailProgressBar.visibility = View.VISIBLE
                }else{
                    binding.detailProgressBar.visibility = View.GONE
                    binding.relativeLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun actions(){
        binding.btnFavorite.setOnClickListener {
            viewModel.setDataToFirestore(navController ,cryptoId)
        }

        if (isFavoriteCoins) {
            binding.btnFavorite.visibility=View.GONE
        } else {
            binding.btnFavorite.visibility=View.VISIBLE
        }

    }

}