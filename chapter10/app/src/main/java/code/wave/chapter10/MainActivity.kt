package code.wave.chapter10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import code.wave.chapter10.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.viewPager.adapter = ViewPagerAdapter(this)

    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      run {
        val textView = TextView(this@MainActivity)
        textView.text = "position $position"
        textView.gravity = Gravity.CENTER

        tab.customView = textView
//        tab.text = "position $position"
      }
    }.attach()
  }

  @Deprecated("Deprecated in Java")
  override fun onBackPressed() {
    val current = supportFragmentManager.fragments[binding.viewPager.currentItem]
    if (current is WebViewFragment && current.canGoBack()) {
      current.goBack()
    } else {
      super.onBackPressed()
    }
  }


}