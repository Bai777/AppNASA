package view.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.appnasa.R
import com.example.appnasa.databinding.ActivityAnimationsBonusStartBinding


class FragmentAnimationsActivityBonus: Fragment() {
    var _binding: ActivityAnimationsBonusStartBinding? = null
    val binding: ActivityAnimationsBonusStartBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityAnimationsBonusStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimations()
    }

    private fun startAnimations() {
        binding.backgroundImage.setOnClickListener {
            show = !show
            val constraintSet = ConstraintSet()
            val transition = ChangeBounds()
            if (show) {
                constraintSet.clone(context, R.layout.activity_animations_bonus_end)
                transition.interpolator = AnticipateOvershootInterpolator(2.0f)
                transition.duration = 1000
            } else {
                constraintSet.clone(context, R.layout.activity_animations_bonus_start)
                transition.interpolator = AnticipateOvershootInterpolator(2.0f)
                transition.duration = 1000
            }
            TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
            constraintSet.applyTo(binding.constraintContainer)
        }
    }

    companion object {
        fun newInstance() = FragmentAnimationsActivityBonus()
        private var show = false
    }
}