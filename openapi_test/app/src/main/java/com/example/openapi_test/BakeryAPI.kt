package com.example.openapi_test

import com.example.openapi_test.Data.DataVO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BakeryAPI {

    @GET("{key}/json/LOCALDATA_072218_GS/{page}/{total}")
    fun getBakery(
        @Path("key") key:String = KEY,
        @Path("page") page : String? = "1",
        @Path("total") total : String? = "100"
    ):Call<DataVO>

    companion object {
        private const val KEY = BuildConfig.API_KEY
        fun create(): BakeryAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed((request))
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BakeryAPI::class.java)
        }
    }
}