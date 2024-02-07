package com.ayberk.spacex.presentation.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.fragment.navArgs
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)

        detailsRocket()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun detailsRocket(){
        val details = args.rocketId

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateTime = LocalDateTime.parse(details.date_utc, formatter)

        val date = dateTime.format(DateTimeFormatter.ofPattern("dd - MM - yyyy"))
        val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))


        with(binding){
            txtName.text = details.name
            txtDetails.text = details.details
            txtTime.text = time
            txtDate.text = date

            if (txtDetails.text.isBlank()){
                txtDetails.text = "Details Blank"
            }

            Glide.with(imgRocketDetails)
                .load(details.links?.patch?.large)
                .into(imgRocketDetails)

        }
    }
}