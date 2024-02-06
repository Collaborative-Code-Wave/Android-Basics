package code.wave.chapter13.service

import code.wave.chapter13.model.NewsRss
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

  @GET("/rss?hl=ko&gl=KR&ceid=KR:ko")
  fun mainFeed(): Call<NewsRss>
}