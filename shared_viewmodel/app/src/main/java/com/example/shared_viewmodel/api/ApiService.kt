package com.example.shared_viewmodel.api

import com.example.shared_viewmodel.model.HomeResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {
    @POST("app/mainHome.siv")
    suspend fun mainHome(): HomeResponse

    companion object {
        fun getApiService(): ApiService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://dev05-m.sivillage.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit.create()
        }

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder().addHeader("User-Agent", "SIV_SEARCH: SIV_MOBILE: 11.0: AOS:")
                chain.proceed(request.build())
            })
    }
}