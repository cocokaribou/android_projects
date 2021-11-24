package com.pionnet.overpass.module

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.webkit.CookieManager
import androidx.core.content.edit

object DataManager {

    /**
     * isTesting : 테스트용 로직 적용시
     * isDebug : 루팅체크시
     * DefaultDomain : 디폴트 도메인 주소
     * DefatulType : 디폴트 도메인 타입(int)
     * CookieDomain : 쿠키도메인
     */
    const val isTesting: Boolean = false
    const val isDebug: Boolean = false
    private val DefaultDomain = "https://m.sivillage.com" //TODO
    private val DefaultType = 0 //dev-m
    private val CookieDomain = "https://.sivillage.com" //TODO

    private lateinit var preferences: SharedPreferences

    // 스플래쉬 이미지 프리로드 패스 (저장 O)
    private val SPLASH_PATH = "SPLASH_PATH"
    private val SPLASH_POINT = "SPLASH_POINT"
    private val SERVER_TYPE = "SERVER_TYPE"
    private val SERVER_DOMAIN = "SERVER_DOMAIN"

    // 로그인 관련 정보 (저장 O)
    const val USER_MBRNO = "mbrNo"
    const val USER_ENCMBRNO = "encMbrNo"
    const val USER_PASSWD = "passwd"
    const val USER_AUTO_LOGIN_FL = "auto_login_fl"
    const val USER_PCID = "PCID"
    const val USER_PCKEY = "pckey"
    const val USER_JSESSIONID = "JSESSIONID"
    const val USER_SIVJAJUPRD = "sivJajuPrd"
    const val USER_FIRST_LOGIN_PUSH = "USER_FIRST_LOGIN_PUSH"

    // 브로드캐스트 리시버 인텐트
    const val INTENT_LOGIN = "login"
    const val INTENT_LOGOUT = "logout"
    const val INTENT_PUSH_UNREAD = "pushUnread"
    const val INTENT_PUSH_READ = "pushRead"
    const val INTENT_SPLASH = "splash"

    // 로그인 관련 쿠키 정보 (저장 X)
    const val USER_INPUT_ID = "userInputId"
    const val USER_ID_SAVE = "id_save"
    const val KAKAO_DOMAIN = "*.kakao.com"
    const val FACEBOOK_DOMAIN = "*.facebook.com"
    const val NAVER_DOMAIN = "*.naver.com"

    const val ALL_COOKIES = "ALLCOOKIES"

    const val LANDING_NO = 0
    const val LANDING_DEEPLINK = 1
    const val LANDING_PUSH = 2
    const val PARAM_LANDING_INFO = "landing_info"
    const val PARAM_LANDING_TYPE = "landing_type"


    // 앱 글로벌 데이타
    var serverAppVersion = ""
    var isAppRunning = false
    var isSplashSet = true
    var hasCalledSslAlert = false
    var tempDomain = "https://m.sivillage.com" //TODO
    var schemeBridge = "dumobile" //TODO
    var deeplink = ""
    var pushlink = ""
    var isWhiteDomain = false
    var isSettingIntent = false

    var deviceTotalDpWidth = 0
    var blockDeeplinks = emptyList<String>()
    var whiteDomainlinks = emptyList<String>()
    var photoUri: Uri? = null

    fun init(context: Context, ) {
        tempDomain = ""
        preferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        when (serverType) {
            0 -> serverDomain = "https://m.sivillage.com"
            1 -> serverDomain = "https://dev-m.sivillage.com"
            2 -> serverDomain = "https://dev02-m.sivillage.com"
            3 -> serverDomain = "https://dev03-m.sivillage.com"
            4 -> serverDomain = "https://dev04-m.sivillage.com"
            5 -> serverDomain = "https://dev05-m.sivillage.com"
            6 -> serverDomain = "https://dev06-m.sivillage.com"
            7 -> serverDomain = "https://dev07-m.sivillage.com"
            8 -> serverDomain = "https://dev08-m.sivillage.com"
            9 -> serverDomain = "https://dev09-m.sivillage.com"
            10 -> serverDomain = "https://dev10-m.sivillage.com"
            11 -> serverDomain = "https://loc-m.sivillage.com"
            12 -> serverDomain = "https://crm-dev-m.sivillage.com"
            13 -> serverDomain = "https://stg-m.sivillage.com"
        }
    }

    //    var user_first_login_push: Boolean
//        get() = preferences.getBoolean(USER_FIRST_LOGIN_PUSH, true)
//        set(value) = preferences.edit {
//            putBoolean(USER_FIRST_LOGIN_PUSH, value)
//        }
//    var is_guideview_showed: Boolean
//        get() = preferences.getBoolean(IS_GUIDEVIEW_SHOWED, false)
//        set(value) = preferences.edit {
//            putBoolean(IS_GUIDEVIEW_SHOWED, value)
//        }

    var isFirstLaunch: Boolean
        get() = preferences.getBoolean("isFirstLaunch", true)
        set(value) = preferences.edit {
            putBoolean("isFirstLaunch", value)
        }

    var isPushReceived: Boolean
        get() = preferences.getBoolean("isPushReceived", false)
        set(value) = preferences.edit {
            putBoolean("isPushReceived", value)
        }


    var allcookies: String
        get() = preferences.getString(ALL_COOKIES, "") ?: ""
        set(value) = preferences.edit {
            putString(ALL_COOKIES, value)
        }

    var splashPath: String
        get() = preferences.getString(SPLASH_PATH, "") ?: ""
        set(value) = preferences.edit {
            putString(SPLASH_PATH, value)
        }

    var splashPoint: Int
        get() = preferences.getInt(SPLASH_POINT, 0)
        set(value) = preferences.edit {
            putInt(SPLASH_POINT, value)
        }

    var serverType: Int
        get() = preferences.getInt(SERVER_TYPE, DefaultType)
        set(value) = preferences.edit{
            putInt(SERVER_TYPE, value)
        }

    var serverDomain: String
        get() = preferences.getString(SERVER_DOMAIN, DefaultDomain) ?: DefaultDomain
        set(value) = preferences.edit {
            putString(SERVER_DOMAIN, value)
        }

//    var oneDayOffDateJaJu:String
//        get() = preferences.getString(ONEDAY_SEEOFF_JAJU, "") ?: ""
//        set(value) = preferences.edit {
//            putString(ONEDAY_SEEOFF_JAJU, value)
//        }
//    var oneDayOffDateBanner:String
//        get() = preferences.getString(ONEDAY_SEEOFF_BANNER, "") ?: ""
//        set(value) = preferences.edit {
//            putString(ONEDAY_SEEOFF_BANNER, value)
//        }

    var mbrNo: String
        get() = preferences.getString(USER_MBRNO, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_MBRNO, value)
        }
    var encMbrNo: String
        get() = preferences.getString(USER_ENCMBRNO, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_ENCMBRNO, value)
        }
    var pwdToken: String
        get() = preferences.getString(USER_PASSWD, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_PASSWD, value)
        }
    var auto_Login_fl: String
        get() = preferences.getString(USER_AUTO_LOGIN_FL, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_AUTO_LOGIN_FL, value)
        }
    var pcid: String
        get() = preferences.getString(USER_PCID, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_PCID, value)
        }
    var pckey: String
        get() = preferences.getString(USER_PCKEY, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_PCKEY, value)
        }
    var jsessionid: String
        get() = preferences.getString(USER_JSESSIONID, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_JSESSIONID, value)
        }
    var sivJajuPrd: String
        get() = preferences.getString(USER_SIVJAJUPRD, "") ?: ""
        set(value) = preferences.edit {
            putString(USER_SIVJAJUPRD, value)
        }

    fun logout() {
        mbrNo = ""
        encMbrNo = ""
        pwdToken = ""
        auto_Login_fl = "N"

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val userInputId = getCookieForName(USER_INPUT_ID)
        val id_save = getCookieForName(USER_ID_SAVE)
        val pcid = getCookieForName(USER_PCID)
        val pckey = getCookieForName(USER_PCKEY)
        val jsessionid = null
        val sivJajuPrd = getCookieForName(USER_SIVJAJUPRD)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies { value ->
            }
        } else {
            cookieManager.removeAllCookie()
        }

        userInputId?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_INPUT_ID=$userInputId")
        }
        id_save?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_ID_SAVE=$id_save")
        }
        pcid?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_PCID=$pcid")
        }
        pckey?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_PCKEY=$pckey")
        }
        jsessionid?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_JSESSIONID=$jsessionid")
        }
        sivJajuPrd?.let {
            CookieManager.getInstance().setCookie(CookieDomain, "$USER_SIVJAJUPRD=$sivJajuPrd")
        }

        val all_cookies: String? =
            CookieManager.getInstance().getCookie(serverDomain)
        if (!all_cookies.isNullOrEmpty()) {
            allcookies = all_cookies
            CookieManager.getInstance().flush()
        }

    }

    fun login(_mbrNo: String, _encMbrNo: String, _pwdToken: String, _auto_Login_fl: String) {
        mbrNo = _mbrNo
        encMbrNo = _encMbrNo
        pwdToken = _pwdToken
        auto_Login_fl = _auto_Login_fl

        val all_cookies: String? =
            CookieManager.getInstance().getCookie(CookieDomain)
        if (!all_cookies.isNullOrEmpty()) {
            allcookies = all_cookies
            CookieManager.getInstance().flush()
        }
    }


    private fun getCookieForName(name: String?): String? {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val cookies = cookieManager.getCookie(CookieDomain)
        var getVal: String? = null
        if (cookies == null) {
            return null
        }
        val temp = cookies.split(";".toRegex()).toTypedArray()
        for (ar1 in temp) {
            if (ar1.contains(name!!)) {
                val temp1 = ar1.split("=".toRegex(), 2).toTypedArray()
                getVal = if (temp1.size > 1) {
                    temp1[1]
                } else {
                    ""
                }
                break
            }
        }
        return getVal
    }

    private fun getCookieForNameDev(name: String?): String? {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val cookies = cookieManager.getCookie(DefaultDomain)
        var getVal: String? = null
        if (cookies == null) {
            return null
        }
        val temp = cookies.split(";".toRegex()).toTypedArray()
        for (ar1 in temp) {
            if (ar1.contains(name!!)) {
                val temp1 = ar1.split("=".toRegex(), 2).toTypedArray()
                getVal = if (temp1.size > 1) {
                    temp1[1]
                } else {
                    ""
                }
                break
            }
        }
        return getVal
    }

    fun getCookies(url: String): String {
        val cookieManager = CookieManager.getInstance()
        var cookies: String? = null

        try {
            cookies = cookieManager.getCookie(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (cookies.isNullOrEmpty()) cookies = ""
        return cookies
    }

    /**
     * cookie 확인
     * 입력받은 name을 key 값으로 갖는 value가 있는지
     */

    fun getCookieForName(domain: String, name: String?): String? {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val cookies = cookieManager.getCookie(domain)
        var cookieVal: String? = null
        if (cookies.isNullOrEmpty()) {
            return null
        }

        var cookieArr = cookies.split(";".toRegex()).toTypedArray()
        for (cookie in cookieArr) {
            if (cookie.contains(name!!)) {
                cookieArr = cookie.split("=".toRegex(), 2).toTypedArray()
                cookieVal = if (cookieArr.size > 1) {
                    cookieArr[1]
                } else {
                    ""
                }
                break
            }
        }
        return cookieVal

        //쿠키는 키 밸류 값, 그리고 '; '로 구분됨
        //아이디저장
        //id_save=Y||N
        //AUTO_LOGIN_YN=Y||N

    }

}