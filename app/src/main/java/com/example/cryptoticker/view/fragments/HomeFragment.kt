package com.example.cryptoticker.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoticker.R
import com.example.cryptoticker.adapter.HomeCoinsAdapter
import com.example.cryptoticker.viewModel.HomeViewModel
import com.example.cryptoticker.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var navController: NavController
    private lateinit var layoutManager: LinearLayoutManager
    private val coinsAdapter : HomeCoinsAdapter by lazy { HomeCoinsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        navController = findNavController()

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.layoutManager = layoutManager

        homeViewModel.getAllDataFromApi()
        observeLiveData()

        binding.recyclerView.adapter = coinsAdapter

        actions()

    }

    private fun actions(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refreshData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.floatingBtnFavorite.setOnClickListener {
            navController.navigate(R.id.favoriteFragment)
        }

        binding.floatingBtnExit.setOnClickListener {
            homeViewModel.signOutFirebase(it.context)
            requireActivity().finish()
        }
    }

    private fun observeLiveData(){
        homeViewModel.cryptoCoins.observe(viewLifecycleOwner,{
            it?.let { response ->
                coinsAdapter.setData(response)
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
        homeViewModel.cryptoProgressBar.observe(viewLifecycleOwner, {
            it?.let { progressBar ->
                if (progressBar){
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val search = menu.findItem(R.id.search_menu)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query : String){
        val searchQuery = "%$query%"

        homeViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, {
            it?.let { response ->
                coinsAdapter.setData(response)
            }
        })
    }

}