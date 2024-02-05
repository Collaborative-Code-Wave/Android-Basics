package code.wave.chapter12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import code.wave.chapter12.adapter.UserAdapter
import code.wave.chapter12.databinding.ActivityMainBinding
import code.wave.chapter12.model.Repo
import code.wave.chapter12.model.User
import code.wave.chapter12.model.UserDto
import code.wave.chapter12.network.ApiClient
import code.wave.chapter12.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var userAdapter: UserAdapter

  private val githubService = ApiClient.getApiClient().create(GithubService::class.java)

  private val handler = Handler(Looper.getMainLooper())
  private var condition = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

//    githubService.listRepos("quedevel").enqueue(object : Callback<List<Repo>>{
//      override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
//        Log.e("MainActivity", "List repos: ${ response.body().toString() }")
//      }
//      override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
//      }
//    })

    userAdapter = UserAdapter {
      val intent = Intent(this@MainActivity, RepoActivity::class.java)
      intent.putExtra("username", it.username)
      startActivity(intent)
    }

    binding.userRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = userAdapter
    }

    val runnable = Runnable {
      searchUser()
    }

    binding.searchEditText.addTextChangedListener {
      condition = it.toString()
      handler.removeCallbacks(runnable)
      handler.postDelayed(
        runnable,
        300,
      )
    }
  }

  private fun searchUser() {
    githubService.searchUsers(condition).enqueue(object : Callback<UserDto> {
      override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
        userAdapter.submitList(response.body()?.items)
      }

      override fun onFailure(call: Call<UserDto>, t: Throwable) {
        Toast.makeText(this@MainActivity, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
        t.printStackTrace()
      }
    })
  }
}