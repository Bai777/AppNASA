package view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.appnasa.R
import com.example.appnasa.databinding.ActivitySplashBinding
import view.picture.PODFragment

class SplashFragment : ViewBindingFragment<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
   lateinit var handler: Handler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.animate().rotationBy(1000f)
            .setInterpolator(LinearInterpolator()).duration = 3000

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commit()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}