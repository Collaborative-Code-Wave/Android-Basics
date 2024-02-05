package code.wave.chapter12.network

import code.wave.chapter12.model.Repo
import code.wave.chapter12.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

  @Headers("Authorization: Bearer ")
  @GET("users/{username}/repos")
  fun listRepos(@Path("username") username: String): Call<List<Repo>>

  @Headers("Authorization: Bearer ")
  @GET("search/users")
  fun searchUsers(@Query("q") query: String): Call<UserDto>
}