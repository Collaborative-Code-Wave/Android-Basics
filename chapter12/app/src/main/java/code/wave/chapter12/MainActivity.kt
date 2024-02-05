package code.wave.chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import code.wave.chapter12.databinding.ActivityMainBinding
import code.wave.chapter12.model.Repo
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val githubService = ApiClient.getApiClient().create(GithubService::class.java)

    githubService.listRepos("quedevel").enqueue(object : Callback<List<Repo>>{
      override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
        Log.e("MainActivity", "List repos: ${ response.body().toString() }")
      }
      override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
      }
    })

    githubService.searchUsers("qued").enqueue(object : Callback<UserDto>{
      override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
        Log.e("MainActivity", "Search User: ${ response.body().toString() }")
      }
      override fun onFailure(call: Call<UserDto>, t: Throwable) {
      }
    })
  }
}