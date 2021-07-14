package com.pionnet.overpass.extension

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.webkit.CookieManager
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.util.*
import kotlin.jvm.Throws

/**
 * 업데이트 유무
 * @param appVer : 앱 프로젝트 버전
 * @param newVer : 서버에서 받는 앱 버전
 * @return true : 업데이트 진행, false : 업데이트 무시
 */
fun isUpdate(appVer: String, newVer: String): Boolean {
    if (newVer == null) return false

    var mAppVer = appVer
    var mNewVer = newVer

    if (appVer.length == newVer.length) return mAppVer < mNewVer

    if (appVer.length < newVer.length) {
        mAppVer = "$appVer.0"
    } else {
        mNewVer = "$newVer.0"
    }

    return mAppVer < mNewVer
}

/**
 * 스플래시 데이터 출력
 * @param imgList : 이미지 데이터 값들
 * @param isRandom : 이미지 데이터 중 랜덤으로 출력할지 말지에 대한 플래그
 */
fun setSplashImgURL(imgList: List<String>, isRandom: Boolean): String {
    if (imgList.isEmpty()) return ""

    var index = 0
    if (isRandom) {
        index = Random().nextInt(imgList.size)
    }

    return imgList[index]
}

/**
 * 프로젝트 앱 버전
 */
fun getAppVersion(context: Context): String {
    var versionName = ""
    @Throws(PackageManager.NameNotFoundException::class)
    versionName =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    return versionName
}

/**
 * permission 체크
 */


/**
 * byte -> Hex(String)
 */
fun bytesToHex(bytes: ByteArray): String {
    val hexArray = charArrayOf(
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F'
    )
    val hexChars = CharArray(bytes.size * 2)
    var v: Int
    for (j in bytes.indices) {
        v = bytes[j].toInt() and 0xff
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0f]
    }
    return hexChars.toString()
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
    for(cookie in cookieArr){
        if(cookie.contains(name!!)){
            cookieArr = cookie.split("=".toRegex(), 2).toTypedArray()
            cookieVal = if(cookieArr.size >1){
                cookieArr[1]
            }else{
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

fun logout() {
    Log.e("hyuk", "logout")
    var userId = getCookieForName("https://.sivillage.com", "userInputId")
    userId = userId ?: ""

    var saveId = getCookieForName("https://.sivillage.com", "id_save")
    saveId = saveId ?: ""

    var pcKey = getCookieForName("https://.sivillage.com", "pckey")
    pcKey = pcKey ?: ""

    Log.d("allCookie", getCookies("https://.sivillage.com"))
    if (Build.VERSION.SDK_INT >= 21) {
        CookieManager.getInstance().removeAllCookies(null);
    } else {
        CookieManager.getInstance().removeAllCookie();
    }

    if (!userId.isNullOrEmpty()) {
        CookieManager.getInstance().setCookie("https://.sivillage.com", "userInputId=$userId")
    }

    if (!saveId.isNullOrEmpty()) {
        CookieManager.getInstance().setCookie("https://.sivillage.com", "id_save=$saveId")
    }

    if (!pcKey.isNullOrEmpty()) {
        CookieManager.getInstance().setCookie("https://.sivillage.com", "pckey=$pcKey")
    }
}