package view.example

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.example.appnasa.R
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
        private var textIsVisible = false
        private var isExpanded = false
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButtonVisibleText()
        scaleTypeImageView()
        startAnimationAlongCurve()
        startFragmentAnimationsActivityBonus()
        binding.buttonThree.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FragmentRecyclerViewEarth.newInstance())
                .addToBackStack("").commit()
        }
    }

    private fun startFragmentAnimationsActivityBonus() {
        binding.buttonTwo.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FragmentAnimationsActivityBonus.newInstance())
                .addToBackStack("").commit()
        }
    }


    private fun startAnimationAlongCurve() {
        binding.buttonOne.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FragmentIsTopAnimation.newInstance())
                .addToBackStack("").commit()
        }
    }

    private fun scaleTypeImageView() {
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            val set = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.exampleConstraintLayout, set)
            binding.imageView.scaleType = if (isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
        }
    }

    private fun clickButtonVisibleText() {
        binding.buttonSix.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.exampleConstraintLayout, Slide(Gravity.END))
            textIsVisible = !textIsVisible
            binding.textViewExample.visibility = if (textIsVisible) View.VISIBLE else View.INVISIBLE
        }
    }

}