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
    private var data: MutableList<Pair<Data, Boolean>>
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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
            data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
            else -> TYPE_EARTH
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(pair: Pair<Data, Boolean>) {
            FragmentRecyclerViewEarthBinding.bind(itemView).apply {
                descriptionTextView.text = pair.first.someDescription
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(pair.first)
                }
            }
        }
    }

    fun appendItem() {
        data.add(Pair(generateItem(), false))
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Data("Mars", "")

    inner class MarsViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(pair: Pair<Data, Boolean>) {
            FragmentRecyclerViewMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(pair.first)
                }
                addItemImageView.setOnClickListener { addItem() }
                removeItemImageView.setOnClickListener { removeItem() }
                moveItemUp.setOnClickListener { moveUp() }
                moveItemDown.setOnClickListener { moveDown() }
                marsTextView.setOnClickListener { toogleText() }
                marsDescriptionTextView.visibility = if (pair.second) View.VISIBLE else View.GONE

            }
        }

        private fun toogleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also {
                data.removeAt(it).apply {
                    data.add(it - 1, this)
                }
                notifyItemMoved(it, it - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < itemCount - 1 }?.also {
                data.removeAt(it).apply {
                    data.add(it + 1, this)
                }
                notifyItemMoved(it, it + 1)
            }
        }

        private fun addItem() {
            data.add(layoutPosition, Pair(generateItem(), false))
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }



    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(pair: Pair<Data, Boolean>) {
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(pair.first)
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