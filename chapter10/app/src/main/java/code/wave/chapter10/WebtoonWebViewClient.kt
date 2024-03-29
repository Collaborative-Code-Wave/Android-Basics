package code.wave.chapter10

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import code.wave.chapter10.databinding.FragmentWebviewBinding

class WebtoonWebViewClient(
  private val binding: FragmentWebviewBinding,
  private val saveData: (String) -> Unit,
): WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
    if (request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")){
      saveData.invoke(request.url.toString())
    }
    return super.shouldOverrideUrlLoading(view, request)
  }

  override fun onPageFinished(view: WebView?, url: String?) {
    super.onPageFinished(view, url)
    binding.progressBar.visibility = View.GONE
  }

  override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
    super.onPageStarted(view, url, favicon)
    binding.progressBar.visibility = View.VISIBLE
  }
}