package view.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.appnasa.databinding.FragmentExampleConstraintLayoutBinding

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

    private var textIsVisible = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSix.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.exampleConstraintLayout)
            textIsVisible = !textIsVisible
            binding.textViewExample.visibility = if (textIsVisible) View.VISIBLE else View.INVISIBLE
        }

    }

}