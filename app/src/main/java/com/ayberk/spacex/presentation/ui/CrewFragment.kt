package com.ayberk.spacex.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.FragmentCrewBinding
import com.ayberk.spacex.databinding.FragmentRocketsBinding
import com.ayberk.spacex.presentation.adapter.CrewAdapter
import com.ayberk.spacex.presentation.models.crew.CrewItem
import com.ayberk.spacex.presentation.models.rockets.RocketsItem
import com.ayberk.spacex.presentation.viewmodel.CrewViewModel
import com.ayberk.spacex.presentation.viewmodel.RocketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrewFragment : Fragment() {

    private var _binding: FragmentCrewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CrewViewModel by viewModels()
    private lateinit var crewAdapter: CrewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCrewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCrews()
        initObserver()
    }

    private fun initObserver() {
        viewModel.crewState.observe(viewLifecycleOwner) { state ->
            state.crewList?.let { crewList ->
                if (crewList.isNotEmpty()) {
                    setupRecyclerView(crewList)
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

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }


    private fun setupRecyclerView(rocketsList: List<CrewItem>) {
        // RecyclerView'a adapter atanır
        crewAdapter = CrewAdapter()
        binding.rcyclerCrew.adapter = crewAdapter

        // RecyclerView'in boyutunu değiştirmeyecek şekilde sabitlenir
        binding.rcyclerCrew.setHasFixedSize(true)

        // LinearLayoutManager atanır (dikey düzen)
        val lmHorizontal = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.rcyclerCrew.layoutManager = lmHorizontal

        // Adapter'a veri atanır
        crewAdapter.setcrewList(rocketsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}