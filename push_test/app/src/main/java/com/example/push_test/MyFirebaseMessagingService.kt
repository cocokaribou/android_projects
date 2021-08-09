package com.example.push_test

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("FCM Log", "Refreshed token: $token")
    }

    override fun onMessageReceived(rMessage: RemoteMessage) {
        Log.e("FCM Log", "----------------")
        Log.e("message", "data: ${rMessage.data}")
        Log.e("message", "type: ${rMessage.messageType}")
        Log.e("message", "from: ${rMessage.from}")
        Log.e("message", "to: ${rMessage.to}")
        Log.e("message.notification", "body: ${rMessage.notification?.body}")
        Log.e("message.notification", "imageUrl: ${rMessage.notification?.imageUrl}")
        Log.e("message.notification", "title: ${rMessage.notification?.title}")
        Log.e("message.notification", "tag: ${rMessage.notification?.tag}")
        Log.e("message.notification", "channelId: ${rMessage.notification?.channelId}")
        Log.e(
            "message.notification",
            "notiPriority: ${rMessage.notification?.notificationPriority}"
        )

        rMessage.notification?.let {
            val msgBody = rMessage.notification!!.body
            val msgTitle = rMessage.notification!!.title
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val channelId = "Test Channel Id"
            val bmp = BitmapFactory.decodeResource(resources, R.mipmap.sym_def_app_icon)
            val notiBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bmp)
            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 오레오 이상은 알림채널 생성
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "Test Channel Name"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notiManager.createNotificationChannel(channel)
            }
            notiManager.notify(0, notiBuilder.build())
        }
        super.onMessageReceived(rMessage)
    }
}