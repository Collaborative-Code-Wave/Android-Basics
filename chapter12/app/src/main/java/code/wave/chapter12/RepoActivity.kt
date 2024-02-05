package code.wave.chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
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

  private val githubService = ApiClient.getApiClient().create(GithubService::class.java)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRepoBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val username = intent.getStringExtra("username") ?: return
    binding.usernameTextView.text = username

    repoAdapter = RepoAdapter()

    binding.repoRecyclerView.apply {
      layoutManager = LinearLayoutManager(this@RepoActivity)
      adapter = repoAdapter
    }

    listRepo(username)
  }

  private fun listRepo(username: String) {
    githubService.listRepos(username).enqueue(object : Callback<List<Repo>> {
      override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
        repoAdapter.submitList(response.body())
      }
      override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
      }
    })
  }
}