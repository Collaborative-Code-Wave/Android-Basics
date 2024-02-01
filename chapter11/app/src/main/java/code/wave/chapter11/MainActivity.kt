package code.wave.chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import code.wave.chapter11.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val client = OkHttpClient()

    val request= Request.Builder()
      .url("http://10.0.2.2:8080")
      .build()

    val callback = object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        Log.e("Client", e.toString())
      }
      override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful){
          Log.e("Client", "${response.body?.string()}")
        }
      }
    }

    // execute 동기
    // APP 에서는 Main UI Thread 에서 Http 호출을 할 수 없다
    // because : 동기화를 통해 HTTP 통신 진행중 에러 발생으로 UI 노출시 오류가 발생할 수 있기 때문이다.
    // enqueue 비동기
    // 비동기 작업시 callback 을 구현해야 한다.
    client.newCall(request).enqueue(callback)

//    Thread {
//      try{기
//        val socket = Socket("10.0.2.2", 8080)
//        val printer = PrintWriter(socket.getOutputStream())
//        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//        printer.println("GET / HTTP/1.1")
//        printer.println("Host: 127.0.0.1:8080")
//        printer.println("User-Agent: android")
//        printer.println("\r\n")
//        printer.flush()
//
//        var input: String? = "-1"
//        while (input != null) {
//          input = reader.readLine()
//          if (input != null) {
//            Log.e("Client", input)
//          }
//        }
//
//        reader.close()
//        printer.close()
//        socket.close()
//      } catch (e : Exception){
//        Log.e("Client", e.toString())
//      }
//    }.start()
  }
}