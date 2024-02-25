package com.ayberk.spacex.presentation.ui

import RocketFavoriteAdapter
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.databinding.FragmentFavoriteBinding
import com.ayberk.spacex.presentation.viewmodel.viewmodelfav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: RocketFavoriteAdapter

    private val viewModelfav: viewmodelfav by viewModels()

    @Inject
    lateinit var spaceRoomDAO: SpaceRoomDAO

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
        clearRockets()
        deleteRocket()

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


    fun clearRockets(){
        binding.imgClear.setOnClickListener {
            showConfirmationDialog()
        }
    }

    fun deleteRocket(){
        favoriteAdapter.onDeleteClickListener = { rocket ->
            viewModelfav.deleteRockets(rocket)
            favoriteAdapter.updateList(favoriteAdapter.rocketsfavoriteList?.filter { it != rocket } ?: emptyList())

        }
    }


    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Favori Roketleri Sil")
        builder.setMessage("Tüm favori roketleri silmek istediğinize emin misiniz?")
        builder.setPositiveButton("Evet") { dialog, _ ->
            viewModelfav.clearRoomIfNotEmpty()
            observeFavoriteRockets()
            dialog.dismiss()
        }
        builder.setNegativeButton("Hayır") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            // Pozitif düğme rengini değiştirelim
            positiveButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }

        dialog.show()
    }

    private fun observeFavoriteRockets() {
        viewModelfav.favoriteRocketsLiveData.observe(viewLifecycleOwner) {
                // Eğer roket listesi boşsa, RecyclerView'ı güncelleyin
                favoriteAdapter.updateList(emptyList())
                Toast.makeText(requireContext(), "Rocket Listeniz Boş", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
