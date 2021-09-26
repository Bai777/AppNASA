package view.example

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appnasa.databinding.FragmentRecyclerBinding
import view.ViewBindingFragment
import view.recycler.Data
import view.recycler.ItemTouchHelperViewHolder
import view.recycler.OnListItemClickListener
import view.recycler.RecyclerActivityAdapter
import java.util.*

class FragmentRecyclerView :
    ViewBindingFragment<FragmentRecyclerBinding>(FragmentRecyclerBinding::inflate) {
    lateinit var itemTouchHelper: ItemTouchHelper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: MutableList<Pair<Data, Boolean>> = ArrayList()
        repeat(10) {
            if (it % 2 == 0) {
                // data.add(Data("Earth"))
            } else {
                data.add(Pair(Data("Mars", ""), false))
            }
        }


        data.add(0, Pair(Data("Header", ""), false))

        val adapter = RecyclerActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(date: Data) {
                    val myDialogFragment = MyDialogFragment()
                    val manager = requireActivity().supportFragmentManager
                    myDialogFragment.show(manager, "dialog")
                }
            }, object: RecyclerActivityAdapter.OnStartDragListener{
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                        itemTouchHelper.startDrag(viewHolder)
                    }
            }, data
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    companion object {
        fun newInstance() = FragmentRecyclerView()
    }
}

class ItemTouchHelperCallback(private val adapter: RecyclerActivityAdapter) :
    ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(source.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
        itemViewHolder.onItemClear()
    }
}