package com.example.udp_server_app.ui.server

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import androidx.core.app.NotificationCompat
import com.example.udp_server_app.Logger
import com.example.udp_server_app.R
import com.example.udp_server_app.ui.client.TcpClientHandler
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

class TcpServerService : Service() {
    private var serverSocket: ServerSocket? = null
    private val running = AtomicBoolean(true)
    private val runnable = Runnable {
        var socket: Socket? = null
        try {
            serverSocket = ServerSocket(ServerActivity.PORT)
            while (running.get()) {
                if (serverSocket != null) {
                    socket = serverSocket!!.accept()
                    Logger("New client: $socket")

                    val dataInputStream = DataInputStream(socket.getInputStream())
                    val dataOutputStream = DataOutputStream(socket.getOutputStream())

                    // Use threads for each client to communicate with them simultaneously
                    val t: Thread = TcpClientHandler(dataInputStream, dataOutputStream)
                    t.start()
                } else {
                    Logger("Couldn't create ServerSocket!")
                }
            }
        } catch (e: IOException) {
            Logger("${e.message}, ${e.stackTrace}")
            try {
                socket?.close()
            } catch (e: IOException) {
                Logger("${e.message}, ${e.stackTrace}")
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        startMeForeground()
        Thread(runnable).start()
    }

    override fun onDestroy() {
        running.set(false)
    }

    private fun startMeForeground() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val NOTIFICATION_CHANNEL_ID = packageName
            val channelName = "Tcp Server Background Service"
            val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE)
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            val notification = notificationBuilder
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()

            startForeground(2, notification)
        } else {
            startForeground(1, Notification())
        }
    }
}