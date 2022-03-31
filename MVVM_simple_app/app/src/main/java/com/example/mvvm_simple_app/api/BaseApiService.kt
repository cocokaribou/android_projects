package com.example.mvvm_simple_app.api

import com.example.mvvm_simple_app.local.Constants.API_KEY
import com.example.mvvm_simple_app.model.WeatherInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BaseApiService {

    @GET
    fun getWeatherInfo(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("appid") appid : String = API_KEY
    ): WeatherInfo

    companion object {
        fun getAPIService(): BaseApiService {
            val okBuilder = OkHttpClient.Builder()

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            okBuilder.addInterceptor(loggingInterceptor)

            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BaseApiService::class.java)
        }
    }
}