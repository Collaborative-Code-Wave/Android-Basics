package code.wave.chapter13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import code.wave.chapter13.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
  private lateinit var binding: ActivityWebViewBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWebViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val url = intent.getStringExtra("url")

    binding.webView.apply {
      webViewClient = WebViewClient()
      settings.javaScriptEnabled = true

      if (url.isNullOrEmpty()){
        Toast.makeText(this@WebViewActivity, "잘못된 URL 입니다.", Toast.LENGTH_SHORT).show()
        finish()
      } else {
        loadUrl(url)
      }
    }

  }
}