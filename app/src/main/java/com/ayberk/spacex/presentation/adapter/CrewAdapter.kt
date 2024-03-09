package com.ayberk.spacex.presentation.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.data.models.crew.CrewFavorite
import com.ayberk.spacex.databinding.ItemCrewBinding
import com.ayberk.spacex.data.models.crew.CrewItem
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.models.rockets.RocketsItem
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.data.room.crewRoom.CrewRoomDAO
import com.ayberk.spacex.usecase.event.CrewEvent
import com.ayberk.spacex.usecase.event.RocketEvent
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrewAdapter(
    private val event: (CrewEvent) -> Unit,
    private val CrewdataDao: CrewRoomDAO
) : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    private var crewList: List<com.ayberk.spacex.data.models.crew.CrewItem>? = null

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
        crewList?.let { crews ->
            if (crews.isNotEmpty()) {
                val rocket = crews[position]
                holder.bind(rocket)

                CoroutineScope(Dispatchers.IO).launch {
                    val id = rocket.id
                    val isFavorite = CrewdataDao.checkIfDataExists(id) > 0
                    withContext(Dispatchers.Main) {
                        if (isFavorite) {
                            holder.bindFavoriteState(R.drawable.crewfavorite, false)
                        } else {
                            holder.bindFavoriteState(R.drawable.crewaddfavorite, true)
                        }
                    }
                }
            }
        }
    }

    inner class CrewViewHolder (private val binding: ItemCrewBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(crew: com.ayberk.spacex.data.models.crew.CrewItem) {
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

                binding.imgCrewFavorite.setOnClickListener {
                    val id = crew.id
                    val imageUrl = crew.image
                    val name = crew.name
                    val agency = crew.agency
                    event(CrewEvent.UpsertDeleteCrew(CrewFavorite(id, imageUrl, name,agency)))

                    binding.lottieAnimationCrewView.visibility = View.VISIBLE
                    binding.lottieAnimationCrewView.playAnimation()
                    binding.lottieAnimationCrewView.speed = 2f
                    // Animasyon için bir AnimatorListener ekleyin
                    binding.lottieAnimationCrewView.addAnimatorListener(object :
                        AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // Animasyon tamamlandığında ImageView'ı gizle
                            binding.lottieAnimationCrewView.visibility = View.GONE

                            // Favori durumunu güncelleyin
                            CoroutineScope(Dispatchers.IO).launch {
                                val isFavorite = CrewdataDao.checkIfDataExists(id) > 0
                                withContext(Dispatchers.Main) {
                                    if (isFavorite) {
                                        bindFavoriteState(R.drawable.crewfavorite, false)
                                    } else {
                                        bindFavoriteState(R.drawable.crewaddfavorite, true)
                                    }
                                }
                            }
                        }
                    })
                }
            }
        }
        fun bindFavoriteState(imageResource: Int, isClickable: Boolean) {
            binding.imgCrewFavorite.setImageResource(imageResource)
            binding.imgCrewFavorite.isClickable = isClickable
        }
    }

    fun setcrewList(newList: List<com.ayberk.spacex.data.models.crew.CrewItem>) {
        crewList = newList
        notifyDataSetChanged()
    }
}