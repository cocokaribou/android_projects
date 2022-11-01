package com.example.elandmall_kotlin.api

import com.example.elandmall_kotlin.base.BaseResponse
import com.example.elandmall_kotlin.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/app/getSignedKeyJson.action")
    suspend fun checkSigned(
        @Field("app_cd") app_cd: String?,
        @Field("mall_cd") mall_cd: String?,
        @Field("SignedKey") SignedKey: String?
    ): SignedKeyResponse

    @GET("/app/initAppStartJsonV2.action")
    suspend fun preLoadV2(
        @Query("app_cd") app_cd: String?,
        @Query("mall_cd") mall_cd: String?,
        @Query("is_first_yn") is_first_yn: String?
    ): PreloadResponse

    //메인 헤더
    @GET("/api/main/mainTabJsonNew.action")
    suspend fun getNewGnbMenu(): MainGnbResponse

    //푸터정보
    @GET("/api/main/footerTabJson.action")
    suspend fun getFooterData(): FooterTabResponse

    //스플래쉬 이미지
    @GET("/api/common/splashListJson.action")
    suspend fun getSplashImage(
        @Query("disp_mall_no") disp_mall_no: String?
    ): SplashListResponse

    //공지사항 업데이트용
    @GET("/api/common/searchPopNotiListMulti.action?noti_clss_cd=100")
    suspend fun getNotiMulti(
        @Query("disp_mall_no") disp_mall_no: String?
    ): SearchPopNotiResponse

    //장바구니 cnt
    @GET("/api/goods/getCartListCnt.action")
    suspend fun getCartCnt(): CartCountResponse

    //홈 정보 전체
    @GET("/api/main/mainContentTotalJson.action")
    suspend fun getHomeData(): HomeResponse

    //슬라이드 메뉴 카테고리
    @GET("/api/dispctg/searchGnbAllCategoryListElandJson.action?cate_expand_yn=Y")
    suspend fun getCategoryList(): CategoryResponse
}