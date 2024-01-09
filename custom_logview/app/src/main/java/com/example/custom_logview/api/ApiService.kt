package com.example.custom_logview.api

import com.example.custom_logview.BuildConfig
import com.example.custom_logview.model.Data
import com.example.custom_logview.ui.CustomLog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

val service by lazy { ApiService.service() }
interface ApiService {

    @GET("posts")
    suspend fun mockData1(): List<Data>
    @GET("failingApi")
    suspend fun failingData(): List<Data>

    @GET("albums")
    suspend fun mockData2(): List<Data>
    @GET("photos")
    suspend fun mockData3(): List<Data>
    @GET("todos")
    suspend fun mockData4(): List<Data>
    @GET("users")
    suspend fun mockData5(): List<Data>



    companion object {
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
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
                .addInterceptor(logging)
                .dispatcher(dispatcher)
                .connectTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}
