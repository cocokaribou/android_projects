package com.example.youngin.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

interface MyAPI {
    @POST("/app/intro_beauty.siv")
    fun getIntro(): Call<ResponseBody>

}
