package kr.co.pionnet.SampleProject

object CommonConst {
    var isAppRunning = false

    const val api_baseurl = "http://10.74.105.66:7401"

    const val biz_id = "sohaldev"
    const val app_id = "000001"
    const val target_app = "002"
    const val content_type = "application/x-www-form-urlencoded"

    // push 수신시 data payload 키값은 캐멀케이스
    const val transactionId = "transactionId"
    const val linkUrl = "linkUrl"

    const val noti_channelId = "test_channel"
    const val noti_channelNm = "test_channel"

}