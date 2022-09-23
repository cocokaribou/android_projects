package kr.co.pionnet.SampleProject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.webkit.URLUtil
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    lateinit var msgTitle: String
    lateinit var msgBody: String
    lateinit var msgImage: String

    override fun onNewToken(token: String) {
        Logger.e("Refreshed Token: $token")
    }

    // noti message일 경우 앱이 foreground 일 때만 호출
    override fun onMessageReceived(rMessage: RemoteMessage) {
        super.onMessageReceived(rMessage)

        Log.e("message", "----------------------------------------------------------")
        Log.e("message", "data       : ${rMessage.data}")
        Log.e("message", "noti.title : ${rMessage.notification?.title}")
        Log.e("message", "noti.body  : ${rMessage.notification?.body}")
        Log.e("message", "noti.img   : ${rMessage.notification?.imageUrl}")
        Log.e("message", "from       : ${rMessage.from}")
        Log.e("message", "to         : ${rMessage.to}")
        Log.e("message", "----------------------------------------------------------")

        val intent = Intent(this, MainActivity::class.java)

        // 앱이 켜져있을때, 꺼져있을때 처리
        if(CommonConst.isAppRunning){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        // 서버에서 올 땐 항상 캐멀케이스
        intent.putExtra(CommonConst.transactionId, rMessage.data[CommonConst.transactionId])
        intent.putExtra(CommonConst.linkUrl, rMessage.data[CommonConst.linkUrl])

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // notification title, body
        msgTitle = rMessage.notification?.title.toString()
        msgBody = rMessage.notification?.body.toString()
        msgImage = rMessage.notification?.imageUrl.toString()

        // 오레오 이상은 알림채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // BigPicture 이미지 처리
            val bigBmp = if (URLUtil.isNetworkUrl(msgImage)) getBitmapFromURL(msgImage)!! else null

            // 알림메시지 빌더
            val notiBuilder = NotificationCompat.Builder(this, CommonConst.noti_channelId)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon)

            // 알림메시지에 이미지링크 있으면 BigPictureStyle 적용
            notiBuilder.apply{
                when{
                    bigBmp != null -> {
                        setStyle(
                            NotificationCompat
                                .BigPictureStyle()
                                .bigLargeIcon(bigBmp)
                                .bigPicture(bigBmp)
                        )
                        setLargeIcon(bigBmp)
                    }
                }
            }

            // 알림채널 빌더
            val channel = NotificationChannel(
                CommonConst.noti_channelId,
                CommonConst.noti_channelNm,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // 알림채널과 알림메시지 등록
            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notiManager.createNotificationChannel(channel)
            notiManager.notify(0, notiBuilder.build())
        }
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }
}
