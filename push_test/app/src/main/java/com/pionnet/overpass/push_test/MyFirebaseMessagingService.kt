package com.pionnet.overpass.push_test

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

    override fun onNewToken(token: String) {
        Log.e("FCM log", "Refreshed Token: $token")
    }

    // firebase console로 보낼 경우 앱이 foreground 일 때만 호출
    override fun onMessageReceived(rMessage: RemoteMessage) {
        Log.e("FCM Log", "----------------------------------------------------------")
        Log.e("message", "data:\t${rMessage.data}")
        Log.e("message", "noti.channelId:\t${rMessage.notification?.channelId}")
        Log.e("message", "from:\t${rMessage.from}")
        Log.e("message", "to:\t${rMessage.to}")
        Log.e("message", "type:\t${rMessage.messageType}")
        Log.e("FCM Log", "----------------------------------------------------------")

        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // data payload
        msgTitle = try {
            rMessage.data.getValue("title")
        } catch (e: Exception) {
            "default title"
        }
        msgBody = try {
            rMessage.data.getValue("body")
        } catch (e: Exception) {
            "default body message"
        }

        // 오레오 이상은 알림채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // BigPicture 이미지 처리
            var bigBmp = BitmapFactory.decodeResource(resources, R.drawable.icon)
            try {
                val imageUrl = rMessage.data.getValue("imageUrl")
                bigBmp = if (URLUtil.isNetworkUrl(imageUrl)) {
                    val bigIcon: Bitmap = getBitmapFromURL(imageUrl)!!
                    bigIcon

                } else {
                    BitmapFactory.decodeResource(resources, R.drawable.icon)
                }
            } catch (e: Exception) {
            }

            val channelId = try {
                rMessage.data.getValue("channelId")
            } catch (e: Exception) {
                "Default"
            }

            val notiBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(bigBmp)

            notiBuilder.apply {
                when (channelId) {
                    "BigPictureStyle" -> {
                        setStyle(
                            NotificationCompat
                                .BigPictureStyle()
                                .bigLargeIcon(bigBmp)
                                .bigPicture(bigBmp)
                                .setBigContentTitle("big content title")
                                .setSummaryText("summary text")
                        )
                    }
                    "BigTextStyle" -> {
                        setStyle(
                            NotificationCompat
                                .BigTextStyle()
                                .bigText(msgBody)
                                .setBigContentTitle("big content title")
                                .setSummaryText("summary text")
                        )
                    }
                    "InboxStyle" -> {
                        setStyle(
                            NotificationCompat
                                .InboxStyle()
                                .addLine("add line")
                                .setBigContentTitle("big content title")
                                .setSummaryText("summary text")
                        )
                    }
                }
            }
            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                channelId, // channel id
                channelId, // channel name
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notiManager.createNotificationChannel(channel)
            notiManager.notify(0, notiBuilder.build())
        }
        super.onMessageReceived(rMessage)
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }
}
