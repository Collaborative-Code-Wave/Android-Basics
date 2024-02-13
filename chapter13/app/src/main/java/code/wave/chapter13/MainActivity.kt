package code.wave.chapter13

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import code.wave.chapter13.adapter.NewsAdapter
import code.wave.chapter13.databinding.ActivityMainBinding
import code.wave.chapter13.model.NewsRss
import code.wave.chapter13.model.transform
import code.wave.chapter13.network.ApiClient
import code.wave.chapter13.service.NewsService
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val list = response.body()?.channel?.items.orEmpty().transform()

        newsAdapter.submitList(list)

        list.forEachIndexed { index, newsModel ->
          Thread {
            val jsoup = Jsoup.connect(newsModel.link).get()
            val elements = jsoup.select("a[jsname=tljFtd]")
            val anchor = elements.first()

            val link = anchor?.attr("href")
            val jsoupNews = link?.let { Jsoup.connect(it).get() }
            val ogMetaElements = jsoupNews?.select("meta[property^=og:]")
            val ogImageNode = ogMetaElements?.find { node ->
              node.attr("property") == "og:image"
            }

            newsModel.imageUrl = ogImageNode?.attr("content")
            runOnUiThread {
              newsAdapter.notifyItemChanged(index)
            }
          }.start()
        }
      }
      override fun onFailure(call: Call<NewsRss>, t: Throwable) {
        t.printStackTrace()
      }
    })
  }

}