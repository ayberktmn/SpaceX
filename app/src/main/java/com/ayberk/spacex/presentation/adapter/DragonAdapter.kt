package com.ayberk.spacex.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.ItemDragonsBinding
import com.ayberk.spacex.presentation.models.crew.CrewItem
import com.ayberk.spacex.presentation.models.dragons.DragonsItem
import com.bumptech.glide.Glide

class DragonAdapter : RecyclerView.Adapter<DragonAdapter.DragonViewHolder>() {

    private var dragonList: List<DragonsItem>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DragonAdapter.DragonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDragonsBinding.inflate(inflater, parent, false)
        return DragonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dragonList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DragonAdapter.DragonViewHolder, position: Int) {
        dragonList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    inner class DragonViewHolder (private val binding: ItemDragonsBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(dragon: DragonsItem) {
            binding.apply {

                val tempInCelsius = dragon.heat_shield.temp_degrees - 273.15 // Kelvin'i Celsius'a dönüştürmek için 273.15'i çıkarın
                val formattedTemp = "%.2f".format(tempInCelsius) // Sıcaklık değerini iki ondalık basamakla formatlayın

                txtname.text = dragon.name
                Glide.with(imgDragon)
                    .load(dragon.flickr_images[0])
                    .error(R.drawable.astronaut)
                    .centerCrop()
                    .into(imgDragon)

                txtKg.text = "Kilogram: " + dragon.dry_mass_kg.toString()
                txtHeight.text = "Height: " + dragon.launch_payload_mass.lb.toString()
                txtDegrees.text = "Sıcaklık: $formattedTemp"
                txtCrewCapacity.text = "Crew Capacity: " + dragon.crew_capacity.toString()

                when(dragon.heat_shield.dev_partner){
                    "NASA" ->  imgDragon.setImageResource(R.drawable.nasa)
                }
            }
        }
    }

    fun setDragonList(newList: List<DragonsItem>) {
        dragonList = newList
        notifyDataSetChanged()
    }
}