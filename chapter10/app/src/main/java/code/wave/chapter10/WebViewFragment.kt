package code.wave.chapter10

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import code.wave.chapter10.databinding.FragmentWebviewBinding

class WebViewFragment(private val position: Int) : Fragment() {

  private lateinit var binding: FragmentWebviewBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentWebviewBinding.inflate(inflater)
    return binding.root
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.webView.apply {
      webViewClient = WebtoonWebViewClient(binding) { url ->
        activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)?.edit {
          putString("tab$position", url)
        }
      }
      settings.javaScriptEnabled = true
      loadUrl("https://comic.naver.com/webtoon/detail?titleId=819217&no=20&week=mon")
    }

    binding.backToLastButton.setOnClickListener {
      val sharedPreferences = activity?.getSharedPreferences("WEB_HISTORY", Context.MODE_PRIVATE)
      val url = sharedPreferences?.getString("tab$position", "")
      if (!url.isNullOrEmpty()){
        binding.webView.loadUrl(url)
      } else {
        Toast.makeText(context, "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
      }
    }
  }

  fun canGoBack(): Boolean {
    return binding.webView.canGoBack()
  }

  fun goBack() {
    binding.webView.goBack()
  }
}