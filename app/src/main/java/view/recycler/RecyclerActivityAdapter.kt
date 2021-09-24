package view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnasa.databinding.FragmentRecyclerItemHeaderBinding
import com.example.appnasa.databinding.FragmentRecyclerViewEarthBinding
import com.example.appnasa.databinding.FragmentRecyclerViewMarsBinding

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding: FragmentRecyclerViewEarthBinding =
                    FragmentRecyclerViewEarthBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EarthViewHolder(binding.root)
            }
            TYPE_MARS -> {
                val binding: FragmentRecyclerViewMarsBinding =
                    FragmentRecyclerViewMarsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                MarsViewHolder(binding.root)
            }
            else -> {
                val binding: FragmentRecyclerItemHeaderBinding =
                    FragmentRecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                HeaderViewHolder(binding.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(data[position])
            }
            TYPE_MARS -> {
                (holder as MarsViewHolder).bind(data[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class EarthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerViewEarthBinding.bind(itemView).apply {
                descriptionTextView.text = data.someDescription
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerViewMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    companion object {
        private const val TYPE_EARTH = 0
        private const val TYPE_MARS = 1
        private const val TYPE_HEADER = 2
    }

}