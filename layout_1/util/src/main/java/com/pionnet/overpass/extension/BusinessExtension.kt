package com.pionnet.overpass.extension

import android.content.Context
import android.content.pm.PackageManager
import java.util.*

/**
 * 업데이트 유무
 * @param appVer : 앱 프로젝트 버전
 * @param newVer : 서버에서 받는 앱 버전
 * @return true : 업데이트 진행, false : 업데이트 무시
 */
fun isUpdate(appVer: String, newVer: String) : Boolean {
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
fun setSplashImgURL(imgList: Array<String>, isRandom: Boolean): String {
    if (imgList.isEmpty()) return ""

    var index = 0
    if (isRandom) {
        index = Random().nextInt(imgList.size)
    }

    if (!imgList[index].startsWith("http")) {
        return "https:" + imgList[index]
    }

    return imgList[index]
}

/**
 * 프로젝트 앱 버전
 */
fun getAppVersion(context: Context): String {
    var versionName = ""
    try {
        versionName =
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return versionName
}