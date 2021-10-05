package com.cocokaribou.recycler_view_expandable_item.api

import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.create
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyApi {

    @GET("/api/shop/initShopBest100Json.action")
    fun getBestProducts(
        @Query("ldisp_ctg_no") ctg: String
    ): Call<ResponseBody>

    @GET("/v2/list?page=1&limit=100")
    fun getRandomPhotos(
    ): Call<ResponseBody>

    companion object {
        fun getElandApiService(): MyApi {
            val okBuilder = OkHttpClient.Builder()

            okBuilder
                .dispatcher(Dispatcher())
                .addInterceptor(Interceptor {
                    val request = it.request().newBuilder().build()
                    return@Interceptor it.proceed(request)
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl("http://m.elandmall.com")
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }


        fun getApiService(): MyApi {
            val okBuilder = OkHttpClient.Builder()

            okBuilder
                .dispatcher(Dispatcher())
                .addInterceptor(Interceptor {
                    val request = it.request().newBuilder().build()
                    return@Interceptor it.proceed(request)
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl("https://picsum.photos")
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }
    }
}