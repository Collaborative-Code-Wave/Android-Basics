package code.wave.chapter10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.button1.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainer, WebViewFragment())
        commit()
      }
    }

    binding.button2.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragmentContainer, BFragment())
        commit()
      }
    }
  }

  override fun onBackPressed() {
    val current = try {
      supportFragmentManager.fragments.first { fragment -> fragment is WebViewFragment } as WebViewFragment
    } catch (e: NoSuchElementException) {
      null
    }
    if (current != null && current.canGoBack()) {
      current.goBack()
    } else {
      super.onBackPressed()
    }
  }


}