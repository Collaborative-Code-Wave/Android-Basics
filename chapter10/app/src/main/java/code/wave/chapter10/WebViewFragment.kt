package code.wave.chapter10

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import code.wave.chapter10.databinding.FragmentWebviewBinding

class WebViewFragment(private val position: Int, private val webViewUrl: String) : Fragment() {

  var listener: OnTabLayoutNameChanged? = null

  private lateinit var binding: FragmentWebviewBinding

  companion object {
    private const val SHARED_PREFERENCE = "WEB_HISTORY"
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentWebviewBinding.inflate(inflater)
    return binding.root
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.webView.apply {
      webViewClient = WebtoonWebViewClient(binding) { url ->
        activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
          putString("tab$position", url)
        }
      }
      settings.javaScriptEnabled = true
      loadUrl(webViewUrl)
    }

    binding.backToLastButton.setOnClickListener {
      val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
      val url = sharedPreferences?.getString("tab$position", "")
      if (!url.isNullOrEmpty()){
        binding.webView.loadUrl(url)
      } else {
        Toast.makeText(context, "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
      }
    }

    binding.changeTabNameButton.setOnClickListener {
      val dialog = AlertDialog.Builder(context)
      val editText = EditText(context)

      dialog.setView(editText)
        .setNegativeButton("취소") { dialogInterface, _ ->
          dialogInterface.cancel()
        }
        .setPositiveButton("저장") { _, _ ->
          activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
            putString("tab${position}_name",editText.text.toString())
            listener?.nameChanged(position, editText.text.toString())
          }
        }
        .show()

    }
  }

  fun canGoBack(): Boolean {
    return binding.webView.canGoBack()
  }

  fun goBack() {
    binding.webView.goBack()
  }
}

interface OnTabLayoutNameChanged {
  fun nameChanged(position: Int, name: String)
}