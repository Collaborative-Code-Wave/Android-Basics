package code.wave.chapter10

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import code.wave.chapter10.databinding.FragmentWebviewBinding

class WebViewFragment: Fragment() {

  private lateinit var binding: FragmentWebviewBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentWebviewBinding.inflate(inflater)
    return binding.root
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.webView.apply {
      webViewClient = WebViewClient()
      settings.javaScriptEnabled = true
      loadUrl("https://google.com")
    }
  }
}