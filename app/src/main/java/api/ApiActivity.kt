package api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appnasa.databinding.ActivityApiBinding

class ApiActivity: AppCompatActivity() {

    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}