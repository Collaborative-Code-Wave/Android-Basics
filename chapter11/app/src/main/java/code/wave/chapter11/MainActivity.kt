package code.wave.chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import code.wave.chapter11.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    Thread {
      try{
        val socket = Socket("10.0.2.2", 8080)
        val printer = PrintWriter(socket.getOutputStream())
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

        printer.println("GET / HTTP/1.1")
        printer.println("Host: 127.0.0.1:8080")
        printer.println("User-Agent: android")
        printer.println("\r\n")
        printer.flush()

        var input: String? = "-1"
        while (input != null) {
          input = reader.readLine()
          if (input != null) {
            Log.e("Client", input)
          }
        }

        reader.close()
        printer.close()
        socket.close()
      } catch (e : Exception){
        Log.e("Client", e.toString())
      }
    }.start()
  }
}