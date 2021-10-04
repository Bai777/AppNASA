package view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.appnasa.databinding.ActivitySplashBinding

class SplashFragment : ViewBindingFragment<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
   lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(context, MainActivity::class.java))
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