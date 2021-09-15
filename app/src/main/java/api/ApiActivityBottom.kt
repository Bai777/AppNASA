package api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appnasa.R
import com.example.appnasa.databinding.ActivityApiBottomBinding
import view.picture.PODFragment

class ApiActivityBottom : AppCompatActivity() {

    lateinit var binding: ActivityApiBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment())
                        .commit()
                    true
                }
                R.id.action_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment())
                        .commit()
                    true
                }
                R.id.action_system -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SystemFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.action_earth
        // создаём badge
        binding.bottomNavigationView.getOrCreateBadge(R.id.action_mars).
                apply {
                    number = 100
                }
    }
}