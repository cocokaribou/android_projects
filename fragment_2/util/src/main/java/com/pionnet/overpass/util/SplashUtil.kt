import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import com.pionnet.overpass.module.DataManager
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.security.MessageDigest

/**
 * Splash에서 쓰이는 함수들
 * - TODO 사용시 입력해야될 사항 [checkForNoti], [checkWhiteLink], [checkBlockLink]


 * 랜딩 정보 처리
 * 1. 랜딩 주소를 webview에 바로 로드
 * 2. 랜딩 주소를 DataManager에 저장해서 WebViewClient::onPageStarted에서 로드
 *
 * @param intent : 랜딩 정보 들어있는 인텐트
 * @param isNewIntent : isNewIntent에서 호출할 때 true 입력, 앱이 켜져있는 상태에서 처리
 */
fun checkForNoti(intent: Intent?, isNewIntent: Boolean) {
    var mLandingType = 0
    var mLandingInfo = ""

    intent?.let {
        mLandingType = intent.getIntExtra(
            DataManager.PARAM_LANDING_TYPE,
            DataManager.LANDING_NO
        )
        if (mLandingType > DataManager.LANDING_NO) {
            mLandingInfo = intent.getStringExtra(DataManager.PARAM_LANDING_INFO).toString()
        }
        when (mLandingType) {
            // 딥링크 처리
            DataManager.LANDING_DEEPLINK -> {
                if (mLandingInfo.isNotEmpty()) {
                    if (mLandingInfo.startsWith("dumobile://deeplink")) { //TODO 딥링크 도메인
                        try {
                            mLandingInfo = URLDecoder.decode(mLandingInfo, "utf-8")
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }

                        var returnUrl = ""
                        val deeps: Array<String> =
                            mLandingInfo.split("dumobile://deeplink/").toTypedArray()
                        if (1 < deeps.size && 1 < deeps[1].length) {
                            returnUrl = deeps[1]
                            // full url
                            if (returnUrl.startsWith("?")) {
                                returnUrl = returnUrl.substring(1, returnUrl.length)
                            } else {
                                // url without domain
                                if (!returnUrl.contains(".siv")) {
                                    returnUrl = "https://$returnUrl"
                                } else {
                                    returnUrl = "${DataManager.serverDomain}/$returnUrl"
                                }
                            }
                        }
                        mLandingInfo = returnUrl

                        //block link
                        if (!checkBlockLink(mLandingInfo)) {
                            DataManager.deeplink = mLandingInfo
                        } else {
                            mLandingInfo = ""
                        }

                        //white link
                        if (checkWhiteLink(mLandingInfo)) {
                            DataManager.isWhiteDomain = true
                            if (!mLandingInfo.startsWith("http")) {
                                mLandingInfo = "https://$mLandingInfo"
                                DataManager.deeplink = mLandingInfo
                            }
                        } else {
                            DataManager.deeplink = ""
                            mLandingInfo = ""
                        }
                    }
                }
            }
            // 핑거푸시 처리
            DataManager.LANDING_PUSH -> {
                if (mLandingInfo.isNotEmpty()) {
                    DataManager.pushlink = mLandingInfo
                } else {
                    // TODO 푸시링크 없을 때 처리
                }
            }
        }

        // 앱이 켜져있는 상태에서 링크로 이동
        // 앱이 꺼져있는 상태에서는 WebViewClient::onPageStarted에서 DataManager에 저장된 링크로 이동 (히스토리 쌓이게 하기 위함)
        if (isNewIntent) {
            if (mLandingInfo.isNotEmpty()) {
                DataManager.deeplink = ""
                DataManager.pushlink = ""
//                webFragment.setNotiUrl(mLandingInfo) TODO 웹뷰에서 이동 코드
                mLandingInfo = ""
            }
        }
    }
}

/**
 * 화이트링크 처리
 */
fun checkWhiteLink(url: String): Boolean {
    var isWhiteDomain = false

    //TODO whiteList api에서 초기화
    val whiteList = listOf<String>()

    if (!whiteList.isNullOrEmpty()) {
        for (i in whiteList.indices) {
            if (url.contains(whiteList[i])) {
                isWhiteDomain = true
                break
            }
        }
    }
    return isWhiteDomain
}

/**
 * 블락링크 처리
 */
fun checkBlockLink(url: String): Boolean {
    var isBlockDomain = false

    //TODO blockList api에서 초기화
    val blockList = listOf<String>()

    if (!blockList.isNullOrEmpty()) {
        for (i in blockList.indices) {
            if (url.contains(blockList[i])) {
                isBlockDomain = true
                break
            }
        }
    }
    return isBlockDomain
}

/**
 * 루팅 체크
 * @return false -> 앱 실행시키기 || true -> 앱 종료시키기
 */
fun checkRooting(): Boolean {
    if (!DataManager.isDebug) {
        val isRooting: Boolean = try {
            Runtime.getRuntime().exec("su")
            true
        } catch (e: Exception) {
            Debug.isDebuggerConnected()
        }

        var isRooting2 = false
        val paths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/system/app/SuperUser.apk",
            "/system/app/su.apk",
            "/system/app/Spapasu.apk"
        )
        for (path in paths) {
            if (File(path).exists()) {
                isRooting2 = true
            }
        }
        return isRooting2 || isRooting
    } else {
        return false
    }
}

/**
 * 해시코드 생성
 */
fun getApplicationSignature(context:Context, packageName:String):List<String> {
    val signatureList: List<String>
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // New signature
            val sig = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            ).signingInfo
            signatureList = if (sig.hasMultipleSigners()) {
                // Send all with apkContentsSigners
                sig.apkContentsSigners.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            } else {
                // Send one with signingCertificateHistory
                sig.signingCertificateHistory.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }
        } else {
            val sig = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            ).signatures
            signatureList = sig.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                bytesToHex(digest.digest())
            }
        }

        return signatureList
    } catch (e: Exception) {
        // Handle error
    }
    return emptyList()
}

private fun bytesToHex(bytes: ByteArray): String {
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
        v = bytes[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
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