package com.example.udp_server_app.ui.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.udp_server_app.Logger
import com.example.udp_server_app.R

class ClientActivity : AppCompatActivity() {
    var udpClientThread: UdpClientThread? = null


    lateinit var btnConnect: Button
    lateinit var etAddress: EditText
    lateinit var etPort: EditText
    lateinit var tvState: TextView
    lateinit var tvReceived: TextView

    companion object {
        lateinit var instance: ClientActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        instance = this

        btnConnect = findViewById(R.id.btn_connect)
        btnConnect.setOnClickListener(clickListener)

        etAddress = findViewById(R.id.et_address)
        etPort = findViewById(R.id.et_port)
        tvState = findViewById(R.id.tv_state)
        tvReceived = findViewById(R.id.tv_received)

    }

    private val clickListener = View.OnClickListener {
        val address = if (etAddress.text.isEmpty()) "0.0.0.0" else etAddress.text.toString()
        val port = if (etPort.text.isEmpty()) 0 else Integer.parseInt(etPort.text.toString())

        udpClientThread = UdpClientThread(
            address,
            port
        )
        udpClientThread?.start()
        btnConnect.isEnabled = false
    }

    fun updateState(msg: String) {
        tvState.text = msg
    }

    fun updateReceivedMsg(msg: String) {
        tvReceived.append("$msg\n")
    }

    fun clientEnd() {
        udpClientThread = null
        tvState.text = "client end"
        btnConnect.isEnabled = true
    }

    override fun onDestroy() {
        Logger("ClientActivity destroyed!")
        super.onDestroy()
    }
}