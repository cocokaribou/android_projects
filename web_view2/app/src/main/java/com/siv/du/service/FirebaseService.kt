package com.siv.du.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.siv.du.R

class FirebaseService:FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("Firebase", "FirebaseInstanceIDService: $p0")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if(message.data.isNotEmpty()){
            sendNotification(message)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage){
        val title:String = remoteMessage.data["title"]!!
        val message:String = remoteMessage.data["message"]!!

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = "test channel"
            val channelNm = "테스트 채널"

            val notiChannel = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelMessage = NotificationChannel(channel, channelNm, NotificationManager.IMPORTANCE_DEFAULT)

            channelMessage.description = "테스트용 채널입니다"
            channelMessage.enableLights(true)
            channelMessage.enableVibration(true)
            channelMessage.setShowBadge(true)
            channelMessage.vibrationPattern = longArrayOf(300, 200, 100)
            notiChannel.createNotificationChannel(channelMessage)

            val notiBuilder = NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .
        }


    }
}