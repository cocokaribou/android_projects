package com.youngin.lunch

import com.youngin.lunch.model.StoreDataList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface BaseApiService {
    @FormUrlEncoded
    @POST("StoreList.api")
    suspend fun getStoreList(
        @Field("listType") listType: String = "c",
        @Field("cateNo") cateNo: Int,
        @Field("page") page: Int = 1
    ): StoreDataList

    companion object{
        fun getApiService(): BaseApiService{
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val interceptor = Interceptor { chain ->
                val request: Request.Builder = chain.request().newBuilder()
                    .addHeader("mbrNo", "10")
                    .addHeader("stoPosX", "")
                    .addHeader("stoPosY", "")
                chain.proceed(request.build())
            }

            val client = OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(httpInterceptor)
                .addNetworkInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://app-dl.pionnet.co.kr/LunchMenu/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BaseApiService::class.java)
        }
    }
}