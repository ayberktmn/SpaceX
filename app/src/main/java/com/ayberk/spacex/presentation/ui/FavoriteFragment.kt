package com.ayberk.spacex.presentation.ui

import RocketFavoriteAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.databinding.FragmentFavoriteBinding
import com.ayberk.spacex.presentation.viewmodel.viewmodelfav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: RocketFavoriteAdapter

    private val viewModelfav: viewmodelfav by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }

        setupRecyclerView()
        initObservers()

        viewModelfav.getAllFavoriteRockets()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = RocketFavoriteAdapter()

        binding.rcyclerRocketFav.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rcyclerRocketFav.layoutManager = layoutManager
        binding.rcyclerRocketFav.adapter = favoriteAdapter
    }

    private fun initObservers() {
        viewModelfav.favoriteRocketsLiveData.observe(viewLifecycleOwner) { rockets ->
            rockets?.let {
                favoriteAdapter.updateList(it)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
