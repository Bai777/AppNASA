package view.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.appnasa.R
import com.example.appnasa.databinding.FragmentRecyclerBinding
import view.ViewBindingFragment
import view.recycler.Data
import view.recycler.OnListItemClickListener
import view.recycler.RecyclerActivityAdapter
import java.util.ArrayList

class FragmentRecyclerView :
    ViewBindingFragment<FragmentRecyclerBinding>(FragmentRecyclerBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data: MutableList<Data> = ArrayList()
        repeat(10) {
            if (it % 2 == 0) {
                // data.add(Data("Earth"))
            } else {
                data.add(Data("Mars", ""))
            }
        }
        data.add(0, Data("Header"))

        val adapter = RecyclerActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(date: Data) {
                    val myDialogFragment = MyDialogFragment()
                    val manager = requireActivity().supportFragmentManager
                    myDialogFragment.show(manager, "dialog")
                }
            }, data
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
    }

    companion object {
        fun newInstance() = FragmentRecyclerView()
    }


}