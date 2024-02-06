package code.wave.chapter13

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import code.wave.chapter13.adapter.NewsAdapter
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
  private lateinit var newsAdapter: NewsAdapter
  private val newsService = ApiClient.getRetrofit().create(NewsService::class.java)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    newsAdapter = NewsAdapter {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
      startActivity(intent)
    }

    binding.newsRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = newsAdapter
    }

    requestMainFeed()
  }

  private fun requestMainFeed(){
    newsService.mainFeed().enqueue(object : Callback<NewsRss>{
      override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
        Log.e("MainActivity", "${response.body()?.channel?.items}")
        newsAdapter.submitList(response.body()?.channel?.items.orEmpty())
      }
      override fun onFailure(call: Call<NewsRss>, t: Throwable) {
        t.printStackTrace()
      }
    })
  }

}