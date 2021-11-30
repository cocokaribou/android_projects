package com.pionnet.overpass.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import com.pionnet.overpass.BuildConfig
import java.io.File
import java.security.MessageDigest

/**
 * Splash에서 쓰이는 함수들
 * - TODO 사용시 입력해야될 사항, [checkWhiteLink], [checkBlockLink]


 * 화이트링크 처리
 */
fun checkWhiteLink(url: String, whiteList: List<String>): Boolean {
    var isWhiteDomain = false

    for (i in whiteList.indices) {
        if (url.contains(whiteList[i])) {
            isWhiteDomain = true
            break
        }
    }
    return isWhiteDomain
}

/**
 * 블락링크 처리
 */
fun checkBlockLink(url: String, blockList: List<String>): Boolean {
    var isBlockDomain = false

    for (i in blockList.indices) {
        if (url.contains(blockList[i])) {
            isBlockDomain = true
            break
        }
    }
    return isBlockDomain
}

/**
 * 루팅 체크
 * @return false -> 앱 실행시키기 || true -> 앱 종료시키기
 */
fun checkRooting(): Boolean {
    if (!BuildConfig.DEBUG) {
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
fun getApplicationSignature(context: Context, packageName: String): List<String> {
    val signatureList: List<String>
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // New signature
            val sig = context.packageManager.getPackageInfo( packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo
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
@Throws(PackageManager.NameNotFoundException::class)
fun getAppVersion(context: Context): String {
    var versionName = ""
    versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
    return versionName
}