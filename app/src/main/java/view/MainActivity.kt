package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.appnasa.R
import view.example.FABFloatOnScroll
import view.picture.PODFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PODFragment.newInstance())
                .commit()
        }
        val bottom_app_bar = findViewById<ConstraintLayout>(R.id.bottom_app_bar)
        val fABFloatOnScroll = FABFloatOnScroll()

        (bottom_app_bar.layoutParams as CoordinatorLayout.LayoutParams).behavior = fABFloatOnScroll
    }
}