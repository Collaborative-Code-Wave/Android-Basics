package code.wave.chapter12.network

import code.wave.chapter12.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {
  private const val BASE_URL = "https://api.github.com/"

  fun getApiClient(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(provideOkHttpClient(AppInterceptor()))
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
      = OkHttpClient.Builder().run {
    addInterceptor(interceptor)
    build()
  }

  class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
      val newRequest = request().newBuilder()
        .addHeader("Authorization", "Bearer ${BuildConfig.github_token}")
        .build()
      proceed(newRequest)
    }
  }
}