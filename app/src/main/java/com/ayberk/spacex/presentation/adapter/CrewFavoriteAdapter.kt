package com.ayberk.spacex.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.databinding.ItemCrewfavBinding
import com.bumptech.glide.Glide

class CrewFavoriteAdapter: RecyclerView.Adapter<CrewFavoriteAdapter.CrewFavoriteViewHolder>() {

    var crewfavoriteList: List<CrewFavorite>? = null
    var onDeleteClickListener: ((FavoriteRockets) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CrewFavoriteAdapter.CrewFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCrewfavBinding.inflate(inflater, parent, false)
        return CrewFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CrewFavoriteAdapter.CrewFavoriteViewHolder,
        position: Int,
    ) {
        crewfavoriteList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return crewfavoriteList?.size ?: 0
    }

    inner class CrewFavoriteViewHolder(private val binding: ItemCrewfavBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crewFav: CrewFavorite) {
            binding.apply {
                txtfavCrew.text = crewFav.name
                Glide.with(imgfavcrew)
                    .load(crewFav.image)
                    .error(R.drawable.astronaut)
                    .centerCrop()
                    .into(imgfavcrew)

                when(crewFav.agency){

                    "NASA" ->  imgfavAgency.setImageResource(R.drawable.nasa)
                    "Axiom Space"-> imgfavAgency.setImageResource(R.drawable.axiomspace)
                    "SpaceX"-> imgfavAgency.setImageResource(R.drawable.spacex)
                    "ESA"-> imgfavAgency.setImageResource(R.drawable.esa)
                    "JAXA"-> imgfavAgency.setImageResource(R.drawable.jaxa)

                }

            }
        }
    }

    fun updateCrewList(list: List<CrewFavorite>) {
        crewfavoriteList = list
        notifyDataSetChanged()
    }

}