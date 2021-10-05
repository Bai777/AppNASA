package view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appnasa.R
import view.picture.PODFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppNASA)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SplashFragment.newInstance()).commit()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, PODFragment.newInstance())
//            .commit()
//    }
}