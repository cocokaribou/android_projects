package com.example.web_view2.data

import com.google.gson.annotations.SerializedName

data class SplashResponse(
    @field:SerializedName("introImgs") var splashImgList: ArrayList<SplashImg>

) {
    data class SplashImg(
        @field:SerializedName("imgPath") var imageUrl: String
    )

    companion object {
        private var splashResponse: SplashResponse?=null

        fun getSplashResponse(): SplashResponse? {
            if(splashResponse != null){
                return splashResponse
            }
            return null
        }

        fun setSplashResponse(splashResponse: SplashResponse?){
            this.splashResponse = splashResponse
        }

    }
}
