package com.example.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

open class MyReceiver : BroadcastReceiver() {

    private var count = 0
    companion object {
        // 액션명
        val MyAction = "com.example.broadcast_receiver.ACTION_MY_BROADCAST"
    }

    // intent.action으로 브로드캐스트를 수신
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "전원 연결", Toast.LENGTH_SHORT).show()
            }
            MyAction -> {
                Toast.makeText(context, "방송", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_TIME_TICK -> {
                count++
                Toast.makeText(context, "$count", Toast.LENGTH_SHORT).show()
            }
        }
    }
}