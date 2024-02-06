package code.wave.chapter13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import code.wave.chapter13.databinding.ActivityMainBinding
import code.wave.chapter13.model.NewsRss
import code.wave.chapter13.network.ApiClient
import code.wave.chapter13.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val newsService = ApiClient.getRetrofit().create(NewsService::class.java)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    requestMainFeed()
  }

  private fun requestMainFeed(){
    newsService.mainFeed().enqueue(object : Callback<NewsRss>{
      override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
        Log.e("MainActivity", "${response.body()?.channel?.items}")
      }
      override fun onFailure(call: Call<NewsRss>, t: Throwable) {
        t.printStackTrace()
      }
    })
  }

}