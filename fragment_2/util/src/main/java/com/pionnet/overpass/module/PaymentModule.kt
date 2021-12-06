package com.pionnet.overpass.module

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.pionnet.overpass.customView.dialog.CustomDialog
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*

/**
 * 결제 모듈
 * - 파일째로 사용하기 위해 문자열 하드코딩
 */
object PaymentModule {
    private const val ENCODER_EUC_KR = "euc-kr"
    private const val ENCODER_UTF_8 = "utf-8"

    // ISP
    private const val RUN_ISP_MOBILE = "ispmobile"
    private const val NAME_ISP_PKG = "kvp.jjy.MispAndroid320"
    private const val LINK_ISP_STORE = "market://details?id=kvp.jjy.MispAndroid320" // ISP 설치 링크

    // KTFC
    private const val RUN_KFTC_BANKPAY = "kftc-bankpay"
    private const val NAME_KFTC_PKG = "com.kftc.bankpay.android"
    private const val NAME_KFTC_CLS = "com.kftc.bankpay.android.activity.MainActivity"
    private const val SCHEME_KFTC = "kftc-bankpay://eftpay?"
    private const val LINK_KFTC_STORE = "market://details?id=com.kftc.bankpay.android" // 금융결제원 설치 링크

    private var KFTC_NICE_BANK_URL: String? = "" // 계좌이체 인증후 거래 요청 URL
    private var KFTC_BANK_TID: String? = ""
    private var KTFC_RESULT_INTENT: Intent? = null // 계좌이체 성공후 데이터

    private const val TAG_BANKPAY_VAL = "bankpay_value"
    private const val TAG_BANKPAY_CODE = "bankpay_code"
    private const val KFTC_CD_CANCELED_BANKPAY = "091"
    private const val KFTC_CD_TIME_OUT = "060"
    private const val KFTC_CD_SIGN_FAILED = "050"
    private const val KFTC_CD_OTP_FAILED = "040"
    private const val KFTC_CD_INIT_ERROR = "030"
    private const val KFTC_CD_SUCCESS = "000"

    // safe click(백신)
    private const val SAFE_CLICK_V_GUARD = "vguard"
    private const val SAFE_CLICK_DROID_ANTI = "droidxantivirus"
    private const val SAFE_CLICK_LOTTE_SMART = "lottesmartpay"
    private const val SAFE_CLICK_SHINHAN_USIM = "smshinhancardusim://"
    private const val SAFE_CLICK_SHINHAN_ANSIM = "shinhan-sr-ansimclick"
    private const val SAFE_CLICK_INTENT_HYUNDAE = "intent:hdcardappcardansimclick://"
    private const val SAFE_CLICK_V3_MOBILE = "v3mobile"
    private const val SAFE_CLICK_APK = ".apk"
    private const val SAFE_CLICK_SMART_WALL = "smartwall://"
    private const val SAFE_CLICK_APP_FREE = "appfree://"
    private const val SAFE_CLICK_MARKET_SCHEME = "market://"
    private const val SAFE_CLICK_ANSIM_CLICK = "ansimclick://"
    private const val SAFE_CLICK_ANSIM_CLICK_S_CARD = "ansimclickscard"
    private const val SAFE_CLICK_ANSIM = "ansim://"
    private const val SAFE_CLICK_M_POCKET = "mpocket"
    private const val SAFE_CLICK_MVACCINE = "mvaccine"
    private const val SAFE_CLICK_MARKET_PKG = "market.android.com"
    private const val SAFE_CLICK_INTENT = "intent://"
    private const val SAFE_CLICK_SAMSUNG_PAY = "samsungpay"
    private const val SAFE_CLICK_DROID_3_WEB = "droidx3web://"
    private const val SAFE_CLICK_KAKAO_PAY = "kakaopay"
    private const val SAFE_CLICK_AHNLAB = "http://m.ahnlab.com/kr/site/download"
    private const val SAFE_CLICK_S_CARD_CERI_APP = "scardcertiapp://"
    private const val SAFE_CLICK_CLOUD_PAY = "cloudpay://"
    private const val SAFE_CLICK_KB_ACP = "kb-acp://"
    private const val SAFE_CLICK_NAVER = "nidlogin://"

    private var context: Context? = null
    private lateinit var webview: WebView
    private lateinit var listener: OnKtfcListener

    interface OnKtfcListener {
        fun onResultReady()
    }

    private fun setOnKtfcListener(listener:OnKtfcListener){
        this.listener = listener
    }

    fun init(context: Context) {
        this.context = context
    }

    fun processPaymentQuery(wv: WebView, url: String?, schemeBridge:String): Boolean {
        webview = wv
        url ?: return true
        return paymentLogic(wv, url, schemeBridge)
    }

    private fun paymentLogic(wv: WebView, url: String, schemeBridge:String): Boolean {
        if (url.startsWith(RUN_ISP_MOBILE) || url.startsWith(RUN_KFTC_BANKPAY)) {
            gotoAppPackage(wv, url)
            return true
        } else if (isSafeClickScheme(url)) {
            gotoAppPackage(wv, url)
            return true
        } else if (url.startsWith(schemeBridge)) {
            // ispmobile에서 결제 완료후 스마트주문 앱을 호출하여 결제결과를 전달하는 경우
            val thisUrl: String = url.substring(schemeBridge.length)
            wv.clearView()
            wv.loadUrl(thisUrl)
            return true
        }

        return false
    }

    /**
     * ISP 설치 진행 안내
     */
    private fun installISP() {
        CustomDialog.CustomDialogBuilder()
            .setDescription("ISP결제를 하기 위해서는 ISP앱이 필요합니다.\n설치 페이지로  진행하시겠습니까?")
            .setTitle("ISP 설치")

            .setPositiveBtnText("확인") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_ISP_STORE))
                context?.startActivity(intent)
            }
            .setNegativeBtnText("아니요") {
                // 결제 초기 화면을 요청합니다.
            }
            .create()
            .show((context as FragmentActivity).supportFragmentManager, "tag")
    }

    /**
     * 계좌이체 데이터를 파싱한다.
     *
     * @param str
     * @return String
     */
    private fun makeBankPayData(str: String): String {
        val userKey = "user_key"
        val callbackParams = "callbackparam1"
        val arr = str.split("&".toRegex()).toTypedArray()
        var parse_temp: Array<String>
        val tempMap = HashMap<String, String>()
        for (i in arr.indices) {
            try {
                parse_temp = arr[i].split("=".toRegex()).toTypedArray()
                tempMap[parse_temp[0]] = parse_temp[1]
            } catch (e: Exception) {
            }
        }
        KFTC_BANK_TID = tempMap[userKey]
        KFTC_NICE_BANK_URL = tempMap[callbackParams]
        return str
    }

    /**
     * 계좌이체 BANKPAY 설치 진행 안내
     */
    private fun installKFTC() {
        CustomDialog.CustomDialogBuilder()
            .setDescription("계좌이체 결제를 하기 위해서는 BANKPAY 앱이 필요합니다.\n설치 페이지로  진행하시겠습니까?")
            .setTitle("계좌이체 BANKPAY 설치")
            .setPositiveBtnText("확인") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_KFTC_STORE))
                context!!.startActivity(intent)
            }
            .setNegativeBtnText("아니요") {
            }
            .create()
            .show((context as FragmentActivity).supportFragmentManager, "tag")
    }

    /***
     * 안심클릭 관련 스키마.
     */
    private fun isSafeClickScheme(scheme: String): Boolean {
        return (scheme.contains(SAFE_CLICK_V_GUARD)
                || scheme.contains(SAFE_CLICK_DROID_ANTI)
                || scheme.contains(SAFE_CLICK_LOTTE_SMART)
                || scheme.contains(SAFE_CLICK_SHINHAN_USIM)
                || scheme.contains(SAFE_CLICK_SHINHAN_ANSIM)
                || scheme.contains(SAFE_CLICK_V3_MOBILE)
                || scheme.endsWith(SAFE_CLICK_APK)
                || scheme.contains(SAFE_CLICK_SMART_WALL)
                || scheme.contains(SAFE_CLICK_APP_FREE)
                || scheme.contains(SAFE_CLICK_MARKET_SCHEME)
                || scheme.contains(SAFE_CLICK_ANSIM_CLICK)
                || scheme.contains(SAFE_CLICK_ANSIM_CLICK_S_CARD)
                || scheme.contains(SAFE_CLICK_ANSIM)
                || scheme.contains(SAFE_CLICK_M_POCKET)
                || scheme.contains(SAFE_CLICK_MVACCINE)
                || scheme.contains(SAFE_CLICK_MARKET_PKG)
                || scheme.startsWith(SAFE_CLICK_INTENT)
                || scheme.contains(SAFE_CLICK_SAMSUNG_PAY)
                || scheme.contains(SAFE_CLICK_DROID_3_WEB)
                || scheme.contains(SAFE_CLICK_KAKAO_PAY)
                || scheme.contains(SAFE_CLICK_AHNLAB)
                || scheme.contains(SAFE_CLICK_S_CARD_CERI_APP)
                || scheme.contains(SAFE_CLICK_CLOUD_PAY)
                || scheme.contains(SAFE_CLICK_KB_ACP))
                || scheme.contains(SAFE_CLICK_NAVER)
    }

    /**
     * KTFC 결제 결과 처리
     */
    fun ktfcPaymentResultProcess(wv: WebView, data: Intent?) {
        data ?: return
        val resVal = data.extras!!.getString(TAG_BANKPAY_VAL)
        val resCode = data.extras!!.getString(TAG_BANKPAY_CODE) ?: return
        when (resCode) {
            KFTC_CD_CANCELED_BANKPAY -> {
                val toast = Toast.makeText(context!!, "인증 오류\n계좌이체 결제를 취소하였습니다.", Toast.LENGTH_LONG)
                toast.show()
            }
            KFTC_CD_TIME_OUT -> {
                val toast = Toast.makeText(context!!, "인증 오류\n타임아웃", Toast.LENGTH_LONG)
                toast.show()
            }
            KFTC_CD_SIGN_FAILED -> {
                val toast = Toast.makeText(context!!, "인증 오류\n전자서명 실패", Toast.LENGTH_LONG)
                toast.show()
            }
            KFTC_CD_OTP_FAILED -> {
                val toast = Toast.makeText(context!!, "인증 오류\nOTP/보안카드 처리 실패", Toast.LENGTH_LONG)
                toast.show()
            }
            KFTC_CD_INIT_ERROR -> {
                val toast = Toast.makeText(context!!, "인증 오류\n인증모듈 초기화 오류", Toast.LENGTH_LONG)
                toast.show()
            }
            KFTC_CD_SUCCESS -> {
                var postData: String? = null
                val param1: String
                val param2: String
                var param3: String? = null
                try {
                    //postData = URLEncoder.encode("callbackparam2=" + KFTC_BANK_TID + "&bankpay_code=" + resCode + "&bankpay_value=" + resVal, ENCODER_EUC_KR);
                    param1 = URLEncoder.encode(KFTC_BANK_TID, ENCODER_EUC_KR)
                    param2 = URLEncoder.encode(resCode, ENCODER_EUC_KR)
                    param3 = URLEncoder.encode(resVal, ENCODER_EUC_KR)
                    postData = "callbackparam2=$param1&bankpay_code=$param2&bankpay_value=$param3"
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
                if (!TextUtils.isEmpty(postData)) {
                    wv.postUrl(KFTC_NICE_BANK_URL!!, postData!!.toByteArray())
                }
            }
        }
    }

    private fun gotoAppPackage(wv: WebView, url: String) {
        var intent: Intent? = null
        val context = wv.context

        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (e: URISyntaxException) {
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (context?.packageManager?.resolveActivity(intent, 0) == null) {
                if (url.startsWith(RUN_ISP_MOBILE)) {
                    installISP()
                    return
                }

                if (url.startsWith(RUN_KFTC_BANKPAY)) {
                    installKFTC()
                    return
                }

                activityNotFoundProcess(intent, wv)
            } else {
                if (url.startsWith(RUN_KFTC_BANKPAY)) {
                    processBankPayData(url, listener)
                    return
                }
                context.startActivity(intent)
            }
        } else {
            try {
                if (url.startsWith(RUN_KFTC_BANKPAY)) {
                    processBankPayData(url, listener)
                    return
                }

                context?.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                if (url.startsWith(RUN_ISP_MOBILE)) {
                    installISP()
                    return
                }

                if (url.startsWith(RUN_KFTC_BANKPAY)) {
                    installKFTC()
                    return
                }

                activityNotFoundProcess(intent, wv)
            }
        }
    }

    private fun activityNotFoundProcess(intent: Intent, view: WebView) {
        var intent = intent
        val context = view.context
        val fallbackUrl = intent.getStringExtra("browser_fallback_url")
        if (fallbackUrl != null) {
            view.loadUrl(fallbackUrl)
            return
        }
        val pkgName = intent.getPackage()
        if (pkgName != null) {
            val uri = Uri.parse("market://details?id=$pkgName")
            intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

    private fun goMarket(packageName: String) {
        val uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context?.startActivity(intent)
    }

    private fun processBankPayData(url: String, listener:OnKtfcListener) {
        val requestInfo = "requestInfo"
        var reqParam = url.substring(SCHEME_KFTC.length)
        try {
            reqParam = URLDecoder.decode(reqParam, ENCODER_UTF_8)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        reqParam = makeBankPayData(reqParam)

        val intent = Intent(Intent.ACTION_MAIN)
        intent.component = ComponentName(NAME_KFTC_PKG, NAME_KFTC_CLS)
        intent.putExtra(requestInfo, reqParam)

        //intent 이동
//
//        if(KTFC_RESULT_INTENT == null){
//            KTFC_RESULT_INTENT = intent
//            listener.onResultReady()
//        }
    }

    fun getBankPayData():Intent {
        return if(KTFC_RESULT_INTENT != null){
            KTFC_RESULT_INTENT!!
        } else {
            Intent()
        }
    }
}