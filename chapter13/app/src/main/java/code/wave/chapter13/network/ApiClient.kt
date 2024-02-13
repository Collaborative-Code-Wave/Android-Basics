package code.wave.chapter13.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object ApiClient {

  private const val BASE_URL = "https://news.google.com/"

  fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(TikXmlConverterFactory.create(
        TikXml.Builder()
          .exceptionOnUnreadXml(false)
          .build()
      ))
      .build()
  }
}