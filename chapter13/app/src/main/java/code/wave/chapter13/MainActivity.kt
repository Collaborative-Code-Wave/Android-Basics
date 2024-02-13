package code.wave.chapter13

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
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
import java.lang.Exception

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var newsAdapter: NewsAdapter
  private val newsService = ApiClient.getRetrofit().create(NewsService::class.java)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    newsAdapter = NewsAdapter {
      val intent = Intent(this, WebViewActivity::class.java)
      intent.putExtra("url", it.link)
      startActivity(intent)
    }

    binding.newsRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = newsAdapter
    }

    binding.feedChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.feedChip.isChecked = true

      newsService.mainFeed().submitList()
    }
    binding.politicsChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.politicsChip.isChecked = true

      newsService.politicsNews().submitList()
    }
    binding.economyChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.economyChip.isChecked = true

      newsService.economyNews().submitList()
    }
    binding.societyChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.societyChip.isChecked = true

      newsService.societyNews().submitList()
    }
    binding.itChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.itChip.isChecked = true

      newsService.itNews().submitList()
    }
    binding.sportChip.setOnClickListener {
      binding.chipGroup.clearCheck()
      binding.sportChip.isChecked = true

      newsService.sportNews().submitList()
    }

    binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH){
        binding.chipGroup.clearCheck()

        binding.searchEditText.clearFocus()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)

        newsService.search(binding.searchEditText.text.toString()).submitList()

        return@setOnEditorActionListener true
      }
      return@setOnEditorActionListener false
    }

    requestMainFeed()
  }

  private fun requestMainFeed(){
    newsService.mainFeed().submitList()
  }

  private fun Call<NewsRss>.submitList() {
    this.enqueue(object : Callback<NewsRss>{
      override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
        val list = response.body()?.channel?.items.orEmpty().transform()
        newsAdapter.submitList(list)
        binding.notFoundView.isVisible = list.isEmpty()
        list.forEachIndexed { index, newsModel ->
          Thread {
            try {
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
            } catch (e: Exception) {
              e.printStackTrace()
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