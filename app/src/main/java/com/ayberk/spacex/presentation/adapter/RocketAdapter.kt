import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.data.models.rockets.RocketsItem
import com.ayberk.spacex.data.room.SpaceRoomDAO
import com.ayberk.spacex.databinding.ItemRocketsBinding
import com.ayberk.spacex.presentation.ui.RocketEvent
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RocketAdapter(
    private val onDetailsClick: (RocketsItem) -> Unit,
    private val event: (RocketEvent) -> Unit,
    private val dataDao: SpaceRoomDAO
) : RecyclerView.Adapter<RocketAdapter.RocketViewHolder>(), Filterable {

    private var rocketsList: List<RocketsItem>? = null
    private var rocketsFilteredList: List<RocketsItem>? = null

    init {
        rocketsFilteredList = rocketsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRocketsBinding.inflate(inflater, parent, false)
        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        rocketsFilteredList?.let { rockets ->
            if (rockets.isNotEmpty()) {
                val rocket = rockets[position]
                holder.bind(rocket)

                CoroutineScope(Dispatchers.IO).launch {
                    val id = rocket.id
                    val isFavorite = dataDao.checkIfDataExists(id) > 0
                    withContext(Dispatchers.Main) {
                        if (isFavorite) {
                            holder.bindFavoriteState(R.drawable.mark, false)
                        } else {
                            holder.bindFavoriteState(R.drawable.favorite, true)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return rocketsFilteredList?.size ?: 0
    }

    inner class RocketViewHolder(private val binding: ItemRocketsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rocket: RocketsItem) {
            binding.apply {
                cardRocket.setOnClickListener {
                    onDetailsClick(rocket)
                }

                txtRocket.text = rocket.name

                Glide.with(imgRocket)
                    .load(rocket.links?.patch?.large)
                    .error(R.drawable.rocket)
                    .into(imgRocket)

                binding.imgFavorite.setOnClickListener {
                    val id = rocket.id
                    val imageUrl = rocket.links?.patch?.large
                    val name = rocket.name
                    event(RocketEvent.UpsertDeleteRocket(FavoriteRockets(id, imageUrl, name)))

                    // Animasyonu görünür hale getirin ve oynatın
                    binding.lottieRocketFavorite.visibility = View.VISIBLE
                    binding.lottieRocketFavorite.playAnimation()
                    binding.lottieRocketFavorite.speed = 2f
                    // Animasyon için bir AnimatorListener ekleyin
                    binding.lottieRocketFavorite.addAnimatorListener(object :
                        AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // Animasyon tamamlandığında ImageView'ı gizle
                            binding.lottieRocketFavorite.visibility = View.GONE

                            // Favori durumunu güncelleyin
                            CoroutineScope(Dispatchers.IO).launch {
                                val isFavorite = dataDao.checkIfDataExists(id) > 0
                                withContext(Dispatchers.Main) {
                                    if (isFavorite) {
                                        bindFavoriteState(R.drawable.mark, false)
                                    } else {
                                        bindFavoriteState(R.drawable.favorite, true)
                                    }
                                }
                            }
                        }
                    })
                }
            }
        }

        fun bindFavoriteState(imageResource: Int, isClickable: Boolean) {
            binding.imgFavorite.setImageResource(imageResource)
            binding.imgFavorite.isClickable = isClickable
        }
    }

    fun setRocketsList(newList: List<RocketsItem>) {
        rocketsList = newList
        rocketsFilteredList = newList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<RocketsItem>()

                val searchText = constraint?.toString()?.toLowerCase()?.trim() ?: ""

                rocketsList?.let { list ->
                    for (rocket in list) {
                        if (searchText.isEmpty() || rocket.name?.toLowerCase()?.contains(searchText) == true) {
                            filteredList.add(rocket)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                rocketsFilteredList = results?.values as List<RocketsItem>?
                if (constraint.isNullOrBlank()) {
                    rocketsFilteredList = rocketsList // Arama kriteri boşsa, tüm roketleri göster
                }
                notifyDataSetChanged()
            }
        }
    }
}
