package com.example.custom_logview.api

import com.example.custom_logview.BuildConfig
import com.example.custom_logview.model.BaseResponse
import com.example.custom_logview.ui.CustomLog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

val service by lazy { ApiService.service() }
interface ApiService {
    @GET("/api/main/mainTabJsonNew.action")
    suspend fun getNewGnbMenu(): BaseResponse

    @GET("/failingApi")
    suspend fun getFooterData(): BaseResponse

    @GET("/api/shop/initPlanShopMainJson.action")
    suspend fun getPlanShopList(): BaseResponse

    @GET("/api/shop/initPlanShopMainJson.action?list_only_yn=Y")
    suspend fun getPlanShopListMore(@Query("page_idx") page_idx: String?): BaseResponse

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
                .baseUrl("http://m.elandmall.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }
}
