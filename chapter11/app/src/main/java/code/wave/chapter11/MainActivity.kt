package code.wave.chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import code.wave.chapter11.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private val client = OkHttpClient()

  private var serverHost = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.confirmButton.setOnClickListener {
      connectServer()
    }

    binding.serverHostEditText.addTextChangedListener {
      serverHost = it.toString()
    }
  }

  private fun connectServer(){
    val request= Request.Builder()
      .url("http://$serverHost:8080")
      .build()

    val callback = object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        runOnUiThread { Toast.makeText(this@MainActivity, "수신에 실패하였습니다.", Toast.LENGTH_SHORT).show() }

      }
      override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful){
          val body = response.body?.string()
          runOnUiThread {
            binding.informationTextView.text = body
            binding.informationTextView.isVisible = true
            binding.serverHostEditText.isVisible = false
            binding.confirmButton.isVisible = false
          }
        } else {
          runOnUiThread { Toast.makeText(this@MainActivity, "수신에 실패하였습니다.", Toast.LENGTH_SHORT).show() }
        }
      }
    }

    // execute 동기
    // APP 에서는 Main UI Thread 에서 Http 호출을 할 수 없다
    // because : 동기화를 통해 HTTP 통신 진행중 에러 발생으로 UI 노출시 오류가 발생할 수 있기 때문이다.
    // enqueue 비동기
    // 비동기 작업시 callback 을 구현해야 한다.
    client.newCall(request).enqueue(callback)
  }
}