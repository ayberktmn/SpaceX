package com.ayberk.spacex.presentation.ui

import RocketAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.databinding.FragmentRocketsBinding
import com.ayberk.spacex.data.models.rockets.Rockets
import com.ayberk.spacex.data.models.rockets.RocketsItem
import com.ayberk.spacex.presentation.viewmodel.RocketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RocketViewModel by viewModels()
    private lateinit var rocketAdapter: RocketAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Add onBackPressed callback logic here if needed
        }
        viewModel.getRockets()
        initObservers()
    }

    private fun initObservers() {
        viewModel.rocketState.observe(viewLifecycleOwner) { state ->
            if (state.isLoading) {
                showLoadingIndicator()
            } else {
                hideLoadingIndicator()
                state.rocketsList?.let { rocketsList ->
                    if (rocketsList.isNotEmpty()) {
                        setupRecyclerView(rocketsList)
                    } else {
                        println("rocketsList boş veya null.")
                    }
                }
                state.errorMessage?.let {
                    showErrorToast(it)
                    println("Recycler error")
                }
            }
        }
    }

    private fun showLoadingIndicator() {
        // Show loading indicator
        binding.lottieAnimationView.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        // Hide loading indicator
        binding.lottieAnimationView.visibility = View.GONE
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }


    private fun setupRecyclerView(rocketsList: List<com.ayberk.spacex.data.models.rockets.RocketsItem>) {
        // RecyclerView'a adapter atanır
        rocketAdapter = RocketAdapter(
            onDetailsClick = {
                val action = RocketsFragmentDirections.actionRocketsFragmentToDetailsFragment(it)
                findNavController().navigate(action)
                println("gönderilen rocket list: ${it}")
            },
        )
        binding.rcyclerRockets.adapter = rocketAdapter

        // RecyclerView'in boyutunu değiştirmeyecek şekilde sabitlenir
        binding.rcyclerRockets.setHasFixedSize(true)

        // LinearLayoutManager atanır (dikey düzen)
        val layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.rcyclerRockets.layoutManager = layoutManager

        // Adapter'a veri atanır
        rocketAdapter.setrocketsList(rocketsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}