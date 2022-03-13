package com.example.udp_client_app

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.*

class UdpClientHandler : Handler(Looper.myLooper()!!) {

    companion object {
        const val UPDATE_STATE = 0;
        const val UPDATE_MSG = 1;
        const val UPDATE_END = 2;
    }

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            UPDATE_STATE -> {
                MainActivity.instance.updateState(msg.obj as String)
            }
            UPDATE_MSG -> {
                MainActivity.instance.updateReceivedMsg(msg.obj as String)
            }
            UPDATE_END -> {
                MainActivity.instance.clientEnd()
            }
            else -> {
                super.handleMessage(msg)
            }
        }
    }
}