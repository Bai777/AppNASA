package view.example

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.appnasa.databinding.FragmentExampleConstraintLayoutBinding
import com.example.appnasa.databinding.FragmentIsTopAnimationBinding

class FragmentIsTopAnimation: Fragment() {
    var _binding: FragmentIsTopAnimationBinding? = null
    val binding: FragmentIsTopAnimationBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIsTopAnimationBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance() = FragmentIsTopAnimation()
        private var isTopAnimation = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
    }

    private fun startAnimation() {
        binding.buttonAnimation.setOnClickListener {
            isTopAnimation = !isTopAnimation
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = 2000
            TransitionManager.beginDelayedTransition(binding.myContainer, changeBounds)
            val params = binding.buttonAnimation.layoutParams as FrameLayout.LayoutParams
            if (isTopAnimation) {
                params.gravity = Gravity.END or Gravity.TOP
            } else {
                params.gravity = Gravity.BOTTOM or Gravity.START
            }
            binding.buttonAnimation.layoutParams = params
        }
    }
}