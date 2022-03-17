package com.example.udp_server_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.net.*

class ServerActivity : AppCompatActivity() {

    private var udpServerThread: UdpServerThread? = null


    lateinit var tvIp: TextView
    lateinit var tvPort: TextView
    lateinit var tvState: TextView
    lateinit var tvPrompt: TextView

    companion object {
        const val UdpServerPort = 4445
        lateinit var instance: ServerActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        instance = this

        tvIp = findViewById(R.id.tv_ip)
        tvPort = findViewById(R.id.tv_port)
        tvState = findViewById(R.id.tv_state)
        tvPrompt = findViewById(R.id.tv_prompt)

        tvIp.text = getIpAddress()
        tvPrompt.text = UdpServerPort.toString()
    }

    override fun onStart() {
        udpServerThread = UdpServerThread(UdpServerPort)
        udpServerThread?.start()
        super.onStart()
    }

    override fun onStop() {
        udpServerThread?.let {
            it.running = false
        }
        udpServerThread = null
        super.onStop()
    }

    fun updateState(msg: String) {
        runOnUiThread {
            tvState.text = msg
        }
    }

    fun updatePrompt(msg: String) {
        runOnUiThread {
            tvPrompt.text = msg
        }
    }

    private fun getIpAddress(): String {
        var ip = ""
//        try {
//            val enumNetworkInterface = NetworkInterface.getNetworkInterfaces()
//            while (enumNetworkInterface.hasMoreElements()) {
//                val networkInterface = enumNetworkInterface.nextElement()
//                val enumInetAddress = networkInterface.inetAddresses
//
//                while (enumInetAddress.hasMoreElements()) {
//                    val inetAddress = enumInetAddress.nextElement()
//
//                    if (inetAddress.isSiteLocalAddress) {
//                        ip += "${inetAddress.hostAddress}\n"
//                    }
//                }
//            }
//        } catch (e: SocketException) {
//            Logger("SocketException ${e.stackTrace}")
//
//        }


        NetworkInterface.getNetworkInterfaces().iterator().forEach { networkInterface ->
            networkInterface.inetAddresses.iterator().forEach {
                if (!it.isLoopbackAddress && it.isIPv4Address()) {
                    ip = it.hostAddress
                }
            }
        }

        return ip
    }

    private fun InetAddress.isIPv4Address(): Boolean {
        return if (this.hostAddress.isNullOrEmpty()) {
            false
        } else try {
            InetAddress.getByName(this.hostAddress) is Inet4Address
        } catch (e: UnknownHostException) {
            false
        }
    }
}

