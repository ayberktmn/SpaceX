import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.spacex.R
import com.ayberk.spacex.databinding.ItemRocketsBinding
import com.ayberk.spacex.presentation.models.rockets.Rockets
import com.ayberk.spacex.presentation.models.rockets.RocketsItem
import com.bumptech.glide.Glide

class RocketAdapter(  private val onDetailsClick: (RocketsItem) -> Unit,) : RecyclerView.Adapter<RocketAdapter.RocketViewHolder>() {

    private var rocketsList: List<RocketsItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRocketsBinding.inflate(inflater, parent, false)
        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        rocketsList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return rocketsList?.size ?: 0
    }

    inner class RocketViewHolder(private val binding: ItemRocketsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rocket: RocketsItem) {
            binding.apply {
                linearRocket.setOnClickListener {
                    onDetailsClick(rocket)
                }
                txtRocket.text = rocket.name
                Glide.with(imgRocket)
                    .load(rocket.links?.patch?.large)
                    .error(R.drawable.rocket)
                    .into(imgRocket)
            }
        }
    }

    fun setrocketsList(newList: List<RocketsItem>) {
        rocketsList = newList
        notifyDataSetChanged()
    }
}
