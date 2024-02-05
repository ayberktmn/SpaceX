package com.ayberk.spacex.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.FragmentHomeBinding
import com.google.android.material.bottomappbar.BottomAppBar
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        with(binding){
            bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
                override fun onTabSelected(
                    lastIndex: Int,
                    lastTab: AnimatedBottomBar.Tab?,
                    newIndex: Int,
                    newTab: AnimatedBottomBar.Tab
                ) {
                    when (newIndex) {
                        0 -> childFragmentManager.primaryNavigationFragment?.findNavController()
                            ?.navigate(R.id.rocketsFragment)
                        1 ->
                        childFragmentManager.primaryNavigationFragment?.findNavController()
                            ?.navigate(R.id.crewFragment)
                        // Diğer sekmeler için gerekirse daha fazla durumu ekleyin
                    }
                    Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")
                }

                // An optional method that will be fired whenever an already selected tab has been selected again.
                override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                    Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
                }
            })
        }

        return binding.root
    }

    private fun replaceFragment(actionId: Int) {
        val navController = findNavController()
        navController.navigate(actionId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}