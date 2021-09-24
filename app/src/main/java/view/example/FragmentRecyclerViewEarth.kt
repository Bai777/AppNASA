package view.example

import android.os.Bundle
import android.view.View
import com.example.appnasa.databinding.FragmentRecyclerViewEarthBinding
import view.ViewBindingFragment

class FragmentRecyclerViewEarth: ViewBindingFragment<FragmentRecyclerViewEarthBinding>(FragmentRecyclerViewEarthBinding::inflate){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = FragmentRecyclerViewEarth()
    }
}