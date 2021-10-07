package view.example

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.example.appnasa.R
import com.example.appnasa.databinding.FragmentExampleConstraintLayoutBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener


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

    var flag = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButtonVisibleText()
        scaleTypeImageView()
        startAnimationAlongCurve()
        startFragmentAnimationsActivityBonus()
        startFragmentRecycleView()
        movieAnimationByttons()

        val firstRun:Boolean = requireActivity().getSharedPreferences("preferences", MODE_PRIVATE).getBoolean("firstrun", true)
        if(firstRun) {
            startHintButtons()
            requireActivity().getSharedPreferences("preferences", MODE_PRIVATE).edit().putBoolean("firstrun", false)
                .apply()
        }
    }

    private fun startHintButtons() {
        val builder = GuideView.Builder(context)
            .setTitle("ВНИМАНИЕ! ПЛОХОЙ ПОДХОД")
            .setContentText("Здесь мы выделили ВАЖНУЮ кнопку размером текста, так лучше не делать")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.selfView)
            .setTargetView(binding.buttonSix)
            .setDismissType(DismissType.anywhere)
            .setGuideListener(object : GuideListener {
                override fun onDismiss(view: View?) {
                    val builder = GuideView.Builder(context)
                        .setTitle("ВНИМАНИЕ! ПРАВИЛЬНЫЙ ПОДХОД")
                        .setContentText("Здесь мы выделили ВАЖНУЮ кнопку стилем OutlinedButton, это хорошая практика")
                        .setGravity(Gravity.center)
                        .setDismissType(DismissType.selfView)
                        .setTargetView(binding.fabExample)
                        .setDismissType(DismissType.anywhere)
                        .setGuideListener(object : GuideListener {
                            override fun onDismiss(view: View?) {

                            }
                        })
                    builder.build().show()
                }
            })
        builder.build().show()
    }

    private fun movieAnimationByttons() {
        binding.fabExample.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.exampleConstraintLayout)

            if (flag) {
                constraintSet.connect(
                    R.id.buttonOne,
                    ConstraintSet.END,
                    R.id.guidelineThreeVertical,
                    ConstraintSet.START,
                    0
                )
                constraintSet.connect(
                    R.id.buttonOne,
                    ConstraintSet.START,
                    R.id.guidelineThreeVertical,
                    ConstraintSet.START,
                    0
                )

                constraintSet.connect(
                    R.id.buttonThree,
                    ConstraintSet.END,
                    R.id.guidelineFirstVertical,
                    ConstraintSet.START,
                    0
                )
                constraintSet.connect(
                    R.id.buttonThree,
                    ConstraintSet.START,
                    R.id.guidelineFirstVertical,
                    ConstraintSet.START,
                    0
                )
            } else {
                constraintSet.connect(
                    R.id.buttonOne,
                    ConstraintSet.END,
                    R.id.guidelineFirstVertical,
                    ConstraintSet.START,
                    0
                )
                constraintSet.connect(
                    R.id.buttonOne,
                    ConstraintSet.START,

                    R.id.guidelineFirstVertical,
                    ConstraintSet.START,
                    0
                )

                constraintSet.connect(
                    R.id.buttonThree,
                    ConstraintSet.END,
                    R.id.guidelineThreeVertical,
                    ConstraintSet.START,
                    0
                )
                constraintSet.connect(
                    R.id.buttonThree,
                    ConstraintSet.START,
                    R.id.guidelineThreeVertical,
                    ConstraintSet.START,
                    0
                )
            }
            flag = !flag
            val transition = android.transition.ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(2.0f)
            transition.duration = 1000
            android.transition.TransitionManager.beginDelayedTransition(
                binding.exampleConstraintLayout,
                transition
            )
            constraintSet.applyTo(binding.exampleConstraintLayout)
        }
    }

    private fun startFragmentRecycleView() {
        binding.buttonThree.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out
                    )
                .replace(R.id.container, FragmentRecyclerView.newInstance())
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
                .setCustomAnimations(
                    R.anim.slide_out,
                    R.anim.slide_out
                )
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
            TransitionManager.beginDelayedTransition(binding.exampleConstraintLayout, Slide())
            textIsVisible = !textIsVisible
            binding.textViewExample.visibility = if (textIsVisible) View.VISIBLE else View.INVISIBLE
        }
    }

}