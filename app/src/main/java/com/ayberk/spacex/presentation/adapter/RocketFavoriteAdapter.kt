import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.recursiveFetchArrayMap
import com.ayberk.spacex.R
import com.ayberk.spacex.data.models.rockets.FavoriteRockets
import com.ayberk.spacex.databinding.ItemRocketfavBinding
import com.ayberk.spacex.presentation.ui.RocketEvent
import com.bumptech.glide.Glide

class RocketFavoriteAdapter() : RecyclerView.Adapter<RocketFavoriteAdapter.RocketFavoriteViewHolder>() {

    var rocketsfavoriteList: List<com.ayberk.spacex.data.models.rockets.FavoriteRockets>? = null
    var onDeleteClickListener: ((FavoriteRockets) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRocketfavBinding.inflate(inflater, parent, false)
        return RocketFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketFavoriteViewHolder, position: Int) {
        rocketsfavoriteList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return rocketsfavoriteList?.size ?: 0
    }

    inner class RocketFavoriteViewHolder(private val binding: ItemRocketfavBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rocketFav: com.ayberk.spacex.data.models.rockets.FavoriteRockets) {
            binding.apply {

                cardrocketfav.setOnClickListener {
                    // onClick implementation
                }

                txtrocketfav.text = rocketFav.name
                Glide.with(imgrocketfav)
                    .load(rocketFav.image)
                    .error(R.drawable.rocket)
                    .into(imgrocketfav)

                imgDelete.setOnClickListener {
                    onDeleteClickListener?.invoke(rocketFav)
                }
            }
        }
    }

    fun updateList(list: List<FavoriteRockets>) {
        rocketsfavoriteList = list
        notifyDataSetChanged()
    }
}
