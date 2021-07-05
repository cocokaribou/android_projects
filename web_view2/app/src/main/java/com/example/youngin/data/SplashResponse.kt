package com.example.youngin.data

import com.google.gson.annotations.SerializedName

data class SplashResponse(

    @field:SerializedName("introImgs") var splashImgList: ArrayList<SplashImg>,
    @field:SerializedName("header") var header: HeaderInfo? = null,
    @field:SerializedName("verInfo") var verInfo: VerInfo? = null,
    @field:SerializedName("settingInfo") var settingInfo: SettingInfo? = null,


    ) {

    inner class SplashImg(
        @field:SerializedName("imgPath") var imageUrl: String
    )

    inner class HeaderInfo(
        @field:SerializedName("r_code") var rCode: String
    )

    inner class VerInfo(
        @field:SerializedName("appVer") var appVer: String,
        @field:SerializedName("appTp") var appTp: String,
        @field:SerializedName("sys_chk_msg") var msg: String
    )

    inner class SettingInfo(
        @field:SerializedName("cs_telno") var csTelno: String
    )

    fun getSplashImage(): ArrayList<SplashImg> {
        return splashImgList
    }

    companion object {
        private var splashResponse: SplashResponse? = null

        fun getSplashResponse(): SplashResponse? {
            if (splashResponse != null) {
                return splashResponse
            }
            return null
        }

        fun setSplashResponse(splashResponse: SplashResponse?) {
            this.splashResponse = splashResponse
        }

    }
}
