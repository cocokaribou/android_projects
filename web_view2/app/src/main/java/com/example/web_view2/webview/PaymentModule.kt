package com.example.web_view2.webview

import android.content.Context
import android.content.pm.PackageManager

class PaymentModule() {
    //from beauty
    private val PLAY_STOER_PATH = "market://search?q=pname:"
    private val ORDER_COMPLETE_URL = "http://m.sivillage.com/order/orderCompleteData"
    private val ENCODER_EUC_KR = "euc-kr"
    private val ENCODER_UTF_8 = "utf-8"

    private val RUN_ISP_MOBILE = "ispmobile"
    private val NAME_ISP_PKG = "kvp.jjy.MispAndroid320"
    private val LINK_ISP_STORE = "market://details?id=kvp.jjy.MispAndroid320" // ISP 설치 링크


    private val RUN_KFTC_BANKPAY = "kftc-bankpay"
    private val NAME_KFTC_PKG = "com.kftc.bankpay.android"
    private val NAME_KFTC_CLS = "com.kftc.bankpay.android.activity.MainActivity"
    private val SCHEME_KFTC = "kftc-bankpay://eftpay?"
    private val LINK_KFTC_STORE = "market://details?id=com.kftc.bankpay.android" //금융결제원 설치 링크

    private var KFTC_NICE_BANK_URL: String? = "" // 계좌이체  인증후 거래 요청 URL

    private var KFTC_BANK_TID: String? = ""
    val TAG_BANKPAY_VAL = "bankpay_value"
    val TAG_BANKPAY_CODE = "bankpay_code"
    val KFTC_CD_CANCELED_BANKPAY = "091"
    val KFTC_CD_TIME_OUT = "060"
    val KFTC_CD_SIGN_FAILED = "050"
    val KFTC_CD_OTP_FAILED = "040"
    val KFTC_CD_INIT_ERROR = "030"
    val KFTC_CD_SUCCESS = "000"

    private val SAFE_CLICK_V_GUARD = "vguard"
    private val SAFE_CLICK_DROID_ANTI = "droidxantivirus"
    private val SAFE_CLICK_LOTTE_SMART = "lottesmartpay"
    private val SAFE_CLICK_SHINHAN_USIM = "smshinhancardusim://"
    private val SAFE_CLICK_SHINHAN_ANSIM = "shinhan-sr-ansimclick"
    private val SAFE_CLICK_INTENT_HYUNDAE = "intent:hdcardappcardansimclick://"
    private val SAFE_CLICK_V3_MOBILE = "v3mobile"
    private val SAFE_CLICK_APK = ".apk"
    private val SAFE_CLICK_SMART_WALL = "smartwall://"
    private val SAFE_CLICK_APP_FREE = "appfree://"
    private val SAFE_CLICK_MARKET_SCHEME = "market://"
    private val SAFE_CLICK_ANSIM_CLICK = "ansimclick://"
    private val SAFE_CLICK_ANSIM_CLICK_S_CARD = "ansimclickscard"
    private val SAFE_CLICK_ANSIM = "ansim://"
    private val SAFE_CLICK_M_POCKET = "mpocket"
    private val SAFE_CLICK_MVACCINE = "mvaccine"
    private val SAFE_CLICK_MARKET_PKG = "market.android.com"
    private val SAFE_CLICK_INTENT = "intent://"
    private val SAFE_CLICK_SAMSUNG_PAY = "samsungpay"
    private val SAFE_CLICK_DROID_3_WEB = "droidx3web://"
    private val SAFE_CLICK_KAKAO_PAY = "kakaopay"
    private val SAFE_CLICK_AHNLAB = "http://m.ahnlab.com/kr/site/download"
    private val SAFE_CLICK_S_CARD_CERI_APP = "scardcertiapp://"
    private val SAFE_CLICK_CLOUD_PAY = "cloudpay://"
    private val SAFE_CLICK_KB_ACP = "kb-acp://"

    private var context: Context? = null

    constructor(context: Context):this(){
        this.context = context
    }
    private fun isPackageInstalled(packageName:String, context: Context):Boolean{
        val pm: PackageManager = context.packageManager
        return try{
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        }catch(e: PackageManager.NameNotFoundException){
            false
        }
    }

}