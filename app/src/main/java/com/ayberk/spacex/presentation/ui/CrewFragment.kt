
package com.ayberk.spacex.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.FragmentCrewBinding
import com.ayberk.spacex.databinding.FragmentRocketsBinding


class CrewFragment : Fragment() {

    private var _binding: FragmentCrewBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("aa")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crew, container, false)
    }

}