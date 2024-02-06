package code.wave.chapter12

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.wave.chapter12.adapter.RepoAdapter
import code.wave.chapter12.databinding.ActivityRepoBinding
import code.wave.chapter12.model.Repo
import code.wave.chapter12.network.ApiClient
import code.wave.chapter12.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRepoBinding
  private lateinit var repoAdapter: RepoAdapter
  private var page = 0
  private var hasMore = true

  private val githubService = ApiClient.getApiClient().create(GithubService::class.java)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRepoBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val username = intent.getStringExtra("username") ?: return
    binding.usernameTextView.text = username

    repoAdapter = RepoAdapter {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
      startActivity(intent)
    }

    binding.repoRecyclerView.apply {
      val linearLayoutManager = LinearLayoutManager(this@RepoActivity)
      layoutManager = linearLayoutManager
      adapter = repoAdapter
      addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
          super.onScrolled(recyclerView, dx, dy)
          val totalCount = linearLayoutManager.itemCount
          val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

          if (lastVisiblePosition >= totalCount - 1 && hasMore) {
            page += 1
            listRepo(username, page)
          }
        }
      })
    }
    listRepo(username, 0)
  }

  private fun listRepo(username: String, page: Int) {
    githubService.listRepos(username, page).enqueue(object : Callback<List<Repo>> {
      override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
        hasMore = response.body()?.count() == 30
        repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
      }
      override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
      }
    })
  }
}