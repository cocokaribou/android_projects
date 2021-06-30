package com.example.youngin.network

import com.example.youngin.base.BaseApplication
import com.example.youngin.common.CommonConst

object HttpUrl {
    val serverUrl: String
        get() {
            when (BaseApplication.getServerType()) {
                CommonConst.SERVER_TYPE_DEV -> return ServerInfo.DEV_HTTPS.url()
                CommonConst.SERVER_TYPE_DEV02 -> return ServerInfo.DEV_HTTPS_02.url()
                CommonConst.SERVER_TYPE_DEV03 -> return ServerInfo.DEV_HTTPS_03.url()
                CommonConst.SERVER_TYPE_DEV04 -> return ServerInfo.DEV_HTTPS_04.url()
                CommonConst.SERVER_TYPE_DEV05 -> return ServerInfo.DEV_HTTPS_05.url()
                CommonConst.SERVER_TYPE_DEV06 -> return ServerInfo.DEV_HTTPS_06.url()
                CommonConst.SERVER_TYPE_DEV07 -> return ServerInfo.DEV_HTTPS_07.url()
                CommonConst.SERVER_TYPE_DEV08 -> return ServerInfo.DEV_HTTPS_08.url()
                CommonConst.SERVER_TYPE_DEV09 -> return ServerInfo.DEV_HTTPS_09.url()
                CommonConst.SERVER_TYPE_DEV10 -> return ServerInfo.DEV_HTTPS_10.url()
                CommonConst.SERVER_TYPE_LOC -> return ServerInfo.LOC_HTTPS.url()
                CommonConst.SERVER_TYPE_CRM_DEV -> return ServerInfo.DEV_HTTPS_CRM.url()
                CommonConst.SERVER_TYPE_CRM_STG -> return ServerInfo.STG_HTTPS_CRM.url()
                CommonConst.SERVER_TYPE_TOBE -> return ServerInfo.HTTPS.url()
            }
            return ServerInfo.HTTPS.url()
        }

    private enum class ServerInfo(private val host: String) {
        HTTPS("https://m.sivillage.com"),
        DEV_HTTPS("https://dev-m.sivillage.com"),
        DEV_HTTPS_02("https://dev02-m.sivillage.com"),
        DEV_HTTPS_03("https://dev03-m.sivillage.com"),
        DEV_HTTPS_04("https://dev04-m.sivillage.com"),
        DEV_HTTPS_05("https://dev05-m.sivillage.com"),
        DEV_HTTPS_06("https://dev06-m.sivillage.com"),
        DEV_HTTPS_07("https://dev07-m.sivillage.com"),
        DEV_HTTPS_08("https://dev08-m.sivillage.com"),
        DEV_HTTPS_09("https://dev09-m.sivillage.com"),
        DEV_HTTPS_10("https://dev10-m.sivillage.com"),
        LOC_HTTPS("https://loc-m.sivillage.com"),
        DEV_HTTPS_CRM("https://crm-dev-m.sivillage.com"),
        STG_HTTPS_CRM("https://stg-m.sivillage.com")
        ;

        internal fun url(): String {
            return host
        }
    }
}