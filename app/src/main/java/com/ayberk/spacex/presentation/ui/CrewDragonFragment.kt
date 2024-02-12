package com.ayberk.spacex.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.spacex.databinding.FragmentCrewdragonBinding
import com.ayberk.spacex.presentation.adapter.CrewAdapter
import com.ayberk.spacex.presentation.adapter.DragonAdapter
import com.ayberk.spacex.data.models.crew.CrewItem
import com.ayberk.spacex.data.models.dragons.DragonsItem
import com.ayberk.spacex.presentation.viewmodel.CrewViewModel
import com.ayberk.spacex.presentation.viewmodel.DragonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrewDragonFragment : Fragment() {

    private var _binding: FragmentCrewdragonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CrewViewModel by viewModels()
    private lateinit var crewAdapter: CrewAdapter

    private val viewModelDragon: DragonViewModel by viewModels()
    private lateinit var dragonAdapter: DragonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCrewdragonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCrews()
        viewModelDragon.getDragons()
        initObserver()
    }

    private fun initObserver() {
        viewModel.crewState.observe(viewLifecycleOwner) { state ->
            state.crewList?.let { crewList ->
                if (crewList.isNotEmpty()) {
                    setupRecyclerView(crewList)
                } else {
                    println("crewList boş veya null.")
                }
            }
            state.errorMessage?.let {
                showErrorToast(it)
                println("Recycler error")
            }
        }
        viewModelDragon.dragonState.observe(viewLifecycleOwner) { state ->
            state.dragonList?.let { dragonList ->
                if (dragonList.isNotEmpty()) {
                    setupDragonRecyclerView(dragonList)
                } else {
                    println("dragonList boş veya null.")
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


    private fun setupRecyclerView(rocketsList: List<com.ayberk.spacex.data.models.crew.CrewItem>) {
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

    private fun setupDragonRecyclerView(dragonList: List<com.ayberk.spacex.data.models.dragons.DragonsItem>) {
        // RecyclerView'a adapter atanır
        dragonAdapter = DragonAdapter()
        binding.rcyclerDragon.adapter = dragonAdapter

        // RecyclerView'in boyutunu değiştirmeyecek şekilde sabitlenir
        binding.rcyclerDragon.setHasFixedSize(true)

        // LinearLayoutManager atanır (dikey düzen)
        val lmHVertical = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.rcyclerDragon.layoutManager = lmHVertical

        // Adapter'a veri atanır
        dragonAdapter.setDragonList(dragonList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}