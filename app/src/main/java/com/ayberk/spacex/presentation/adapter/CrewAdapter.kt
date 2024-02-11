package com.ayberk.spacex.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.ItemCrewBinding
import com.ayberk.spacex.presentation.models.crew.CrewItem
import com.ayberk.spacex.presentation.models.rockets.RocketsItem
import com.bumptech.glide.Glide

class CrewAdapter : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    private var crewList: List<CrewItem>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CrewAdapter.CrewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCrewBinding.inflate(inflater, parent, false)
        return CrewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crewList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        crewList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    inner class CrewViewHolder (private val binding: ItemCrewBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(crew: CrewItem) {
            binding.apply {

                txtCrew.text = crew.name
                Glide.with(imgCrew)
                    .load(crew.image)
                    .error(R.drawable.astronaut)
                    .centerCrop()
                    .into(imgCrew)

                when(crew.agency){
                    "NASA" ->  imgAgency.setImageResource(R.drawable.nasa)
                    "Axiom Space"-> imgAgency.setImageResource(R.drawable.axiomspace)
                    "SpaceX"-> imgAgency.setImageResource(R.drawable.spacex)
                    "ESA"-> imgAgency.setImageResource(R.drawable.esa)
                    "JAXA"-> imgAgency.setImageResource(R.drawable.jaxa)

                }
            }
        }
    }

    fun setcrewList(newList: List<CrewItem>) {
        crewList = newList
        notifyDataSetChanged()
    }

}