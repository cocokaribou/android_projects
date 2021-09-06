package com.example.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.broadcast_receiver.databinding.ActivityMainBinding
import java.util.*

/**
 * Broadcast Receiver 예제
 * 브로드캐스트 리시버는 액티비티 생명주기를 이용한다.
 * 출처 : https://50billion-dollars.tistory.com/entry/Android-%EB%B8%8C%EB%A1%9C%EB%93%9C%EC%BA%90%EC%8A%A4%ED%8A%B8-%EB%A6%AC%EC%8B%9C%EB%B2%84-Broadcast-Receiver
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mReceiver: BroadcastReceiver

    // timer
    private var running = false
    private var seconds = 0

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        running = true
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSendButton()

        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    Log.e("intent action", "${intent.action}")
                    when (intent.action) {
                        Intent.ACTION_POWER_CONNECTED -> {
                            binding.txtBattery.text = "전원 연결됨"
                        }
                        Intent.ACTION_POWER_DISCONNECTED -> {
                            binding.txtBattery.text = "전원 연결안됨"
                        }
                        Intent.ACTION_TIME_TICK -> {
                            count++
                            binding.txtTimeSpent.text = "앱을 켠지 ${count}분 경과"
                        }
                        "BUTTON CLICKED" -> {
                            AlertDialog.Builder(this@MainActivity)
                                    .setMessage("버튼을 눌렀다!\n인텐트를 브로캐스트 리시버로 받았다!")
                                    .setPositiveButton("와! 신기해!"){ _, _ ->

                                    }
                                    .show()
                        }
                    }

                }
            }

        }
    }

    private fun initSendButton() {
        // 버튼 눌렀을때 리시버로 인텐트 발송
        binding.btnSend.setOnClickListener {
            val intent = Intent("BUTTON CLICKED")
            sendBroadcast(intent)
        }
    }

    // 액티비티가 포커스 받을 때
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_TIME_TICK)
        filter.addAction("BUTTON CLICKED")

        // 로컬로 등록
        registerReceiver(mReceiver, filter)
    }

    // 액티비티가 포커스 잃었을 때
    // onCreate - onDestroy 에서 한다면?
    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

}