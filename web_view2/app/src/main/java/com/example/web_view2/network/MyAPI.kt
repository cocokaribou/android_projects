package com.example.web_view2.network

import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyAPI {
    @FormUrlEncoded
    @POST("/app/intro_beauty.siv")
    fun getIntro(@Field("appHash") appHash: String): Call<ResponseBody>


}