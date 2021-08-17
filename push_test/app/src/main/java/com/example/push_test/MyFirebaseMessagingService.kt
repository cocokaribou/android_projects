package com.example.push_test

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.RingtoneManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("FCM Log", "Refreshed token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMessageReceived(rMessage: RemoteMessage) {
        // custom 설정은 data에 넣어보내서 처리해주는구나..

        Log.e("FCM Log", "----------------")
        Log.e("message", "data:\t${rMessage.data}")
        Log.e("message", "type:\t${rMessage.messageType}")
        Log.e("message", "from:\t${rMessage.from}")
        Log.e("message", "to:\t${rMessage.to}")
        Log.e("message.notification", "title:\t${rMessage.notification?.title}")
        Log.e("message.notification", "body:\t\t${rMessage.notification?.body}")
        Log.e("message.notification", "image:\t${rMessage.notification?.imageUrl}")
        Log.e("message.notification", "tag:\t\t${rMessage.notification?.tag}")
        Log.e("message.notification", "chnl:\t${rMessage.notification?.channelId}")
        Log.e(
            "message.notification",
            "notiPriority:\t${rMessage.notification?.notificationPriority}"
        )
        Log.e("message.notification", "clickAction:\t${rMessage.notification?.clickAction}")

        rMessage.notification?.let {
            val msgBody = rMessage.notification!!.body
            val msgTitle = rMessage.notification!!.title
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val channelId = "Test Channel Id"
            var bmp = BitmapFactory.decodeResource(resources, R.mipmap.sym_def_app_icon)

            // RemoteMessage.data.imageUrl있을 경우
            if(!rMessage.data.getValue("imageUrl").isNullOrEmpty()){
                val source = ImageDecoder.createSource(contentResolver, rMessage.notification?.imageUrl!!)
                bmp = ImageDecoder.decodeBitmap(source)
            }

            // RemoteMessage.notification.imageUrl 있을 경우
            if(rMessage.notification?.imageUrl.toString().isNotEmpty()){
                val source = ImageDecoder.createSource(contentResolver, rMessage.notification?.imageUrl!!)
                bmp = ImageDecoder.decodeBitmap(source)
            }

            // NotificationCompat
            val notiStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(bmp)
            // BigPicture
            // BigText
            // Inbox
            // Messaging
            // Decorated Custom View Style

            // NotificationCompat.Builder
            val notiBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setLargeIcon(bmp)
                .setStyle(notiStyle)

            val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 오레오 이상은 알림채널 생성
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "Test Channel Name"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = "Test Channel 입니다. 여기로 지정하지 않으면 기타로 날아옵니다."
                notiManager.createNotificationChannel(channel)
            }
            notiManager.notify(0, notiBuilder.build())
        }
        super.onMessageReceived(rMessage)
    }
}