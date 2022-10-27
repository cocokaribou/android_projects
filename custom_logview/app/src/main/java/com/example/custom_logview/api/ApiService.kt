package com.example.custom_logview.api

import android.text.TextUtils
import com.example.custom_logview.BuildConfig
import com.example.custom_logview.CustomLog
import com.example.custom_logview.model.MainGnbResponse
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    //메인 헤더
    @GET("/api/main/mainTabJsonNew.action")
    suspend fun getNewGnbMenu(): MainGnbResponse

    companion object {
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

        private val interceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()

            val request: Request = requestBuilder.build()
            CustomLog.a(request)

            val response = chain.proceed(request)
            CustomLog.a(response)

            return@Interceptor response
        }

        private val dispatcher = Dispatcher().apply {
            maxRequests = 8
            maxRequestsPerHost = 8
        }

        fun service() : ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(logging)
                .dispatcher(dispatcher)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://m.elandmall.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}
