package com.example.web_view2.webview

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebView
import android.widget.Toast
import com.example.web_view2.common.CommonConst
import com.example.web_view2.common.RequestCode
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.HashMap

class PaymentModule() {
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

    constructor(context: Context) : this() {
        this.context = context
    }

    fun processPaymentQuery(wv: WebView, url: String?): Boolean {
        return paymentLogic(wv, url)
    }


    private fun paymentLogic(wv: WebView, url: String?): Boolean {
        var intent: Intent
        if (url!!.startsWith(RUN_ISP_MOBILE)) {
            // 웹뷰에서 ispmobile 실행한 경우...
            return if (isPackageInstalled(NAME_ISP_PKG, context!!)) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context!!.startActivity(intent)
                true
            } else {
                installISP(wv)
                true
            }
        } else if (url.startsWith(RUN_KFTC_BANKPAY)) {
            // 웹뷰에서 계좌이체를 실행한 경우...
            return if (isPackageInstalled(NAME_KFTC_PKG, context!!)) {
                val requestInfo = "requestInfo"
                var reqParam = url.substring(SCHEME_KFTC.length)
                try {
                    reqParam = URLDecoder.decode(reqParam, ENCODER_UTF_8)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
                reqParam = makeBankPayData(reqParam)
                intent = Intent(Intent.ACTION_MAIN)
                intent.component = ComponentName(NAME_KFTC_PKG, NAME_KFTC_CLS)
                intent.putExtra(requestInfo, reqParam)
                (context as Activity?)!!.startActivityForResult(intent, RequestCode.KTFC_REQUEST_BANK)
                true
            } else {
                installKFTC(wv)
                true
            }
        } else if (url != null && isSafeClickScheme(url)) {
            // 웹뷰에서 안심클릭을 실행한 경우...
            try {
                intent = try {
                    Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                } catch (ex: URISyntaxException) {
                    return false
                }
                /*
                롯데앱카드 : intent://lottecard/data?acctid=120160628150229605882165497397&apptid=964241&IOS_RETURN_APP=#Intent;scheme=lotteappcard;package=com.lcacApp;end
                현대앱카드 : intent:hdcardappcardansimclick://appcard?acctid=201606281503270019917080296121#Intent;package=com.hyundaicard.appcard;end;
                삼성앱카드 : intent://xid=4752902#Intent;scheme=mpocket.online.ansimclick;package=kr.co.samsungcard.mpocket;end;
                NH 앱카드 : intent://appcard?ACCTID=201606281507175365309074630161&P1=1532151#Intent;scheme=nhappcardansimclick;package=nh.smart.mobilecard;end;
                KB 앱카드  : intent://pay?srCode=0613325&kb-acp://#Intent;scheme=kb-acp;package=com.kbcard.cxh.appcard;end;
                하나(모비페이) : intent://?tid=2238606309025172#Intent;scheme=cloudpay;package=com.hanaskcard.paycla;end;*/
                if (url.startsWith(SAFE_CLICK_INTENT) || url.startsWith(SAFE_CLICK_INTENT_HYUNDAE)) { //현대카드는 스킴구조가 다르다보니 따로 예외처리
                    // chrome 버젼 방식
                    if (context!!.packageManager.resolveActivity(intent, 0) == null) {
                        val packageName = intent.getPackage()
                        if (packageName != null) {
                            val uri = Uri.parse(PLAY_STOER_PATH + packageName)
                            intent = Intent(Intent.ACTION_VIEW, uri)
                            context!!.startActivity(intent)
                            return true
                        }
                    }
                    val uri = Uri.parse(intent.dataString)
                    intent = Intent(Intent.ACTION_VIEW, uri)
                    context!!.startActivity(intent)
                    return true
                } else {
                    // 구 방식
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context!!.startActivity(intent)
                }
            } catch (e: Exception) {
                return false
            }
        } else if (url.startsWith(CommonConst.SCHEME_BRIDGE)) {
            // ispmobile에서 결제 완료후 스마트주문 앱을 호출하여 결제결과를 전달하는 경우
            val thisUrl: String = url.substring(CommonConst.SCHEME_BRIDGE.length)
            wv.clearView()
            wv.loadUrl(thisUrl)
            return true
        } else {
            return false
        }
        return true
    }

    /**
     * ISP 설치 진행 안내
     */
    private fun installISP(wv: WebView) {
        val d = AlertDialog.Builder(context)
        d.setMessage("ISP결제를 하기 위해서는 ISP앱이 필요합니다.\n설치 페이지로  진행하시겠습니까?")
        d.setTitle("ISP 설치")
        val postData = ""

        d.setPositiveButton("확인") { dialog, which ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_ISP_STORE))
            context!!.startActivity(intent)
        }
        d.setNegativeButton("아니요") { dialog, which ->
            dialog.cancel()
            // 결제 초기 화면을 요청합니다.
            wv.postUrl(ORDER_COMPLETE_URL, postData.toByteArray())
        }
        d.show()
    }

    /**
     * 계좌이체 데이터를 파싱한다.
     *
     * @param str
     * @return
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
    private fun installKFTC(wv: WebView) {
        val d = AlertDialog.Builder(context)
        d.setMessage("계좌이체 결제를 하기 위해서는 BANKPAY 앱이 필요합니다.\n설치 페이지로  진행하시겠습니까?")
        d.setTitle("계좌이체 BANKPAY 설치")

        val postData = ""

        d.setPositiveButton("확인") { dialog, which ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_KFTC_STORE))
            context!!.startActivity(intent)
        }
        d.setNegativeButton("아니요") { dialog, which ->
            dialog.cancel()
            wv.postUrl(ORDER_COMPLETE_URL, postData.toByteArray())
        }
        d.show()
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
    }

    /**
     * KTFC 결제 결과 처리
     */
    fun ktfcPaymentResultProcess(wv: WebView, data: Intent) {
        val resVal = data.extras!!.getString(TAG_BANKPAY_VAL)
        val resCode = data.extras!!.getString(TAG_BANKPAY_CODE) ?: return
        lateinit var toast:Toast
        when (resCode) {
            KFTC_CD_CANCELED_BANKPAY -> {toast= Toast.makeText(context!!, "인증 오류\n계좌이체 결제를 취소하였습니다.", Toast.LENGTH_LONG)}
            KFTC_CD_TIME_OUT -> {toast= Toast.makeText(context!!, "인증 오류\n타임아웃", Toast.LENGTH_LONG)}
            KFTC_CD_SIGN_FAILED -> {toast= Toast.makeText(context!!, "인증 오류\n전자서명 실패", Toast.LENGTH_LONG)}
            KFTC_CD_OTP_FAILED -> {toast= Toast.makeText(context!!, "인증 오류\nOTP/보안카드 처리 실패", Toast.LENGTH_LONG)}
            KFTC_CD_INIT_ERROR -> {toast= Toast.makeText(context!!, "인증 오류\n인증모듈 초기화 오류", Toast.LENGTH_LONG)}
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
        toast.show()
    }

    private fun isPackageInstalled(packageName: String, context: Context): Boolean {
        val pm: PackageManager = context.packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}