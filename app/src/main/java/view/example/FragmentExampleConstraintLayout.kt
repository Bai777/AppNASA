package view.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import chips.SettingsFragment
import com.example.appnasa.R
import com.example.appnasa.databinding.FragmentExampleConstraintLayoutBinding
import com.example.appnasa.databinding.FragmentSettingsBinding

class FragmentExampleConstraintLayout: Fragment() {
    var _binding: FragmentExampleConstraintLayoutBinding? = null
    val binding: FragmentExampleConstraintLayoutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExampleConstraintLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = FragmentExampleConstraintLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}