package com.example.shared_viewmodel.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    val homeMainBanner: List<HomeMainBanner>? = null,
) : BaseResponse() {

    override fun isSuccessful(): Boolean {
        return resultCd == "0000"
    }
}

@Keep
data class HomeMainBanner(
    val brandNm: String?,
    val imgPath: String?,
    val linkUrl: String?,
    val brandCnt: Int,
    val subTxt: String?,
    val txt: String?,
    val txtDispYn: String?
) {
    val isShowTxtDisp: Boolean
        get() = txtDispYn == "Y"
}

abstract class BaseResponse {
    @SerializedName("resultCd") val resultCd: String = "200"
    @SerializedName("resultMsg") val resultMsg: String = ""
    @SerializedName("userSession") var userSession: UserSession? = null // todo: check whether is needed.

    abstract fun isSuccessful(): Boolean

    // todo: delete, if useless.
    data class UserSession(
        val loginId: String?,
        val loginType: String?,
        val empTp: String?,
        val mbrCertDiviCd: String?,
        val intgMbrGrade: String?,
        val mbrTypeCd: String?,
        val ageGroup: String?,
        val intgPushRcvYn: String?,
        val intgMbrGradeNm: String?,
        val marketingChk: String?,
        val gendCd: String?,
        val mbrNo: String?,
        val staffYn: String?,
        val mbrNm: String?,
        val jajuClubGrade: String?,
        val jajuClubGradeNm: String?,
        val certTp: String?,
        val jajuNChk: String?,
        val genTp: String?,
        val loginYn: String?,
        val compDiviCd: String?,
        val age: String?,
        val empYn: String?,
    )
}
