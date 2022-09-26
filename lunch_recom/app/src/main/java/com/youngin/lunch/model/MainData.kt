package com.youngin.lunch.model

import com.google.gson.annotations.SerializedName
import com.youngin.lunch.BaseApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

data class MainData(
    @field:SerializedName("todayRecommend") var todayRecommend: TodayRecommend?,
) {
    data class TodayRecommend(
        @field:SerializedName("stoNo") var stoNo: Int?,
        @field:SerializedName("stoName") var stoName: String?,
        @field:SerializedName("stoGps") var stoGPS: String?,
        @field:SerializedName("stoAddr") var stoAddr: String?,
        @field:SerializedName("stoPNumber") var stoPNum: String?,
        @field:SerializedName("stoCont") var stoContents: String?,
        @field:SerializedName("stoImgUrl") var stoImgUrl: String?,
        @field:SerializedName("stoScore") var stoScore: Int?,
    )
}